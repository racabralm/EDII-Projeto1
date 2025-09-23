/*
 * Grupo:
 * Bruna Amorim Maia - 10431883
 * Rafael Araujo Cabral Moreira - 10441919
 * Rute Willemann - 10436781
 */
import java.io.*;
import java.util.BitSet;
import java.text.DecimalFormat;

// Classe principal que orquestra todo o processo de compressão e descompressão
public class Huffman {

    // Define o tamanho da tabela ASCII (256 caracteres, de 0 a 255)
    private static final int TAMANHO_ASCII = 256;

    // Método principal, ponto de entrada do programa
    public static void main(String[] args) throws IOException {
        // Valida se os argumentos da linha de comando foram fornecidos corretamente
        if (args.length < 3) {
            // Se não, exibe a forma correta de usar o programa
            System.out.println("Uso:");
            System.out.println("  Para comprimir:   java -jar huffman.jar -c <entrada.txt> <saida.huff>");
            System.out.println("  Para descomprimir: java -jar huffman.jar -d <entrada.huff> <saida.txt>");
            return;
        }

        // Armazena os argumentos em variáveis
        String opcao = args[0];      // A opção: -c (comprimir) ou -d (descomprimir)
        String entrada = args[1];    // O nome do arquivo de entrada
        String saida = args[2];      // O nome do arquivo de saida

        // Chama o método apropriado com base na opção fornecida
        if (opcao.equals("-c")) {
            comprimir(entrada, saida);
        } else if (opcao.equals("-d")) {
            descomprimir(entrada, saida);
            System.out.println("\nArquivo descomprimido gerado: " + saida);
        } else {
            // Se a opção for inválida, informa o usuário
            System.out.println("Opcao invalida: " + opcao);
        }
    }

    // --- LÓGICA DE COMPRESSÃO ---

    // Orquestra todas as etapas do processo de compressão
    public static void comprimir(String arqOrigem, String arqDestino) throws IOException {
        // ETAPA 0: Lê todos os bytes do arquivo de origem
        byte[] bytesArquivo = lerArquivo(arqOrigem);

        // ETAPA 1: Conta a frequência de cada byte no arquivo
        int[] tabelaFrequencia = construirTabelaFrequencia(bytesArquivo);
        imprimirTabelaFrequencia(tabelaFrequencia); // Exibe no console

        // ETAPA 2: Cria o Min-Heap com os nós folha (caracteres)
        MinHeap minHeap = construirMinHeapInicial(tabelaFrequencia);
        imprimirMinHeapInicial(minHeap); // Exibe no console
        
        // ETAPA 3: Usa o Min-Heap para construir a Árvore de Huffman
        No raiz = construirArvoreHuffman(minHeap);
        imprimirArvoreHuffman(raiz); // Exibe no console

        // ETAPA 4: Percorre a árvore para gerar os códigos binários de cada caractere
        String[] tabelaCodigos = gerarTabelaCodigos(raiz);
        imprimirTabelaCodigos(tabelaCodigos); // Exibe no console

        // ETAPA 5: Codifica os dados e escreve o arquivo comprimido
        byte[] dadosComprimidos = codificarDados(bytesArquivo, tabelaCodigos);
        escreverArquivoComprimido(arqDestino, tabelaFrequencia, bytesArquivo.length, dadosComprimidos);
        
        // Imprime o resumo final da compressão
        imprimirResumo(bytesArquivo.length, dadosComprimidos.length, arqDestino);
    }

    // --- MÉTODOS DO ALGORITMO ---

    // Lê um arquivo do disco e retorna seu conteúdo como um array de bytes
    private static byte[] lerArquivo(String caminho) throws IOException {
        File arquivo = new File(caminho);
        byte[] bytesArquivo = new byte[(int) arquivo.length()];
        // try-with-resources garante que o FileInputStream será fechado
        try (FileInputStream fis = new FileInputStream(arquivo)) {
            fis.read(bytesArquivo);
        }
        return bytesArquivo;
    }

    // Cria a tabela de frequência
    private static int[] construirTabelaFrequencia(byte[] dados) {
        // Cria um array de inteiros para armazenar as contagens (inicia com zeros)
        int[] freq = new int[TAMANHO_ASCII];
        // Para cada byte lido do arquivo
        for (byte b : dados) {
            // Incrementa o contador na posição correspondente ao valor do byte
            // O operador '& 0xFF' converte o byte (que pode ser negativo) para um int de 0-255
            freq[b & 0xFF]++;
        }
        return freq;
    }
    
    // Cria um Min-Heap e o popula com nós folha para cada caractere que aparece no texto
    private static MinHeap construirMinHeapInicial(int[] freq) {
        MinHeap minHeap = new MinHeap();
        // Percorre toda a tabela ASCII
        for (int i = 0; i < TAMANHO_ASCII; i++) {
            // Se um caractere apareceu ao menos uma vez
            if (freq[i] > 0) {
                // Cria um nó folha para ele e o insere no heap
                minHeap.inserir(new No((char) i, freq[i]));
            }
        }
        return minHeap;
    }

    // Constrói a Árvore de Huffman a partir do Min-Heap
    private static No construirArvoreHuffman(MinHeap minHeap) {
        // O processo continua enquanto houver mais de um nó no heap
        while (minHeap.tamanho() > 1) {
            // Remove os dois nós de menor frequência
            No esquerda = minHeap.remover();
            No direita = minHeap.remover();
            // Cria um novo nó interno cuja frequência é a soma dos filhos
            int novaFrequencia = esquerda.frequencia + direita.frequencia;
            No pai = new No(novaFrequencia, esquerda, direita);
            // Insere o novo nó interno de volta no heap
            minHeap.inserir(pai);
        }
        // O último nó que resta no heap é a raiz da árvore completa
        return minHeap.remover();
    }

    // Método de fachada para iniciar a geração da tabela de códigos
    private static String[] gerarTabelaCodigos(No raiz) {
        String[] tabela = new String[TAMANHO_ASCII];
        // Chama a função recursiva que faz o trabalho
        gerarCodigosRecursivo(raiz, "", tabela);
        return tabela;
    }
    
    // Percorre a árvore recursivamente para mapear cada caractere ao seu código
    private static void gerarCodigosRecursivo(No no, String codigo, String[] tabela) {
        // Se o nó é uma folha, encontramos um caractere
        if (no.isFolha()) {
            // Armazena o código binário acumulado na posição do caractere
            tabela[no.caractere] = codigo;
            return;
        }
        // Se não é folha, continua a descida na árvore
        // Adiciona '0' ao código ao ir para a esquerda
        gerarCodigosRecursivo(no.esquerda, codigo + "0", tabela);
        // Adiciona '1' ao código ao ir para a direita
        gerarCodigosRecursivo(no.direita, codigo + "1", tabela);
    }

    // Converte os bytes originais em uma sequência de bits comprimidos
    private static byte[] codificarDados(byte[] dadosOriginais, String[] tabelaCodigos) {
        // StringBuilder é eficiente para concatenar muitas strings
        StringBuilder sb = new StringBuilder();
        // Para cada byte do arquivo original
        for (byte b : dadosOriginais) {
            // Busca seu código de Huffman na tabela e o anexa ao StringBuilder
            sb.append(tabelaCodigos[b & 0xFF]);
        }
        
        // Converte o StringBuilder para uma única String de '0's e '1's
        String bitString = sb.toString();
        // BitSet é uma estrutura otimizada para manipular uma sequência de bits
        BitSet bitSet = new BitSet(bitString.length());
        // Preenche o BitSet com base na string de bits
        for(int i = 0; i < bitString.length(); i++) {
            if(bitString.charAt(i) == '1') {
                bitSet.set(i); // Define o bit na posição i como true (1)
            }
        }
        // Converte o BitSet para um array de bytes, que é o dado comprimido final
        return bitSet.toByteArray();
    }

    // Escreve o arquivo comprimido final no formato .huff
    private static void escreverArquivoComprimido(String caminho, int[] freq, long totalChars, byte[] dados) throws IOException {
        // DataOutputStream permite escrever tipos primitivos do Java (int, long)
        try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(caminho)))) {
            // 1- Escreve o cabeçalho: a tabela de frequência completa (256 inteiros)
            for (int f : freq) {
                out.writeInt(f);
            }
            // 2- Escreve o número total de bytes do arquivo original
            // Isso é crucial para a descompressão saber quando parar
            out.writeLong(totalChars);

            // 3- Escreve os dados comprimidos (o array de bytes do BitSet)
            out.write(dados);
        }
    }

    // --- LÓGICA DE DESCOMPRESSÃO ---

    // Orquestra todas as etapas do processo de descompressão
    public static void descomprimir(String arqOrigem, String arqDestino) throws IOException {
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(arqOrigem)))) {
            // 1- Lê o cabeçalho: a tabela de frequência
            int[] freq = new int[TAMANHO_ASCII];
            for (int i = 0; i < TAMANHO_ASCII; i++) {
                freq[i] = in.readInt();
            }
            // 2- Lê o número total de caracteres do arquivo original
            long totalChars = in.readLong();
            // 3- Reconstrói a Árvore de Huffman a partir da tabela de frequência
            // É essencial que este processo seja idêntico ao da compressão
            No raiz = construirArvoreHuffman(construirMinHeapInicial(freq));
            // 4- Lê o resto do arquivo, que são os dados comprimidos
            byte[] dadosComprimidos = in.readAllBytes();
            // 5- Decodifica os dados e escreve o arquivo original de volta
            decodificarDados(arqDestino, raiz, dadosComprimidos, totalChars);
        }
    }

    // Decodifica a sequência de bits e escreve o arquivo original
    private static void decodificarDados(String arqDestino, No raiz, byte[] dados, long totalChars) throws IOException {
        // Converte o array de bytes lido de volta para um BitSet
        BitSet bitSet = BitSet.valueOf(dados);
        // Usa FileOutputStream para escrever bytes puros, evitando problemas com acentuação
        try(FileOutputStream fos = new FileOutputStream(arqDestino)) {
            // Começa a navegação a partir da raiz da árvore
            No atual = raiz;
            // Contador para saber quantos caracteres já foram escritos
            long contagem = 0;
            // Percorre os bits um por um até que o número necessário de caracteres seja escrito
            for (int i = 0; contagem < totalChars; i++) {
                // Se o bit for 1 (true), vai para o filho da direita
                if (bitSet.get(i)) {
                    atual = atual.direita;
                } else {
                    // Se o bit for 0 (false), vai para o filho da esquerda
                    atual = atual.esquerda;
                }
                // Se o nó atual for uma folha, encontramos um caractere
                if (atual.isFolha()) {
                    // Escreve o byte do caractere encontrado no arquivo de saída
                    fos.write((byte) atual.caractere);
                    // Retorna para a raiz para começar a decodificar o próximo caractere
                    atual = raiz;
                    // Incrementa o contador de caracteres escritos
                    contagem++;
                }
            }
        }
    }
    
    // --- MÉTODOS PARA IMPRESSÃO NO CONSOLE ---

    // Imprime a tabela de frequência de forma legível
    private static void imprimirTabelaFrequencia(int[] freq) {
        System.out.println("\n--- ETAPA 1: Tabela de Frequencia ---");
        for (int i = 0; i < TAMANHO_ASCII; i++) {
            if (freq[i] > 0) {
                // Trata caracteres de controle para não quebrar a formatação do console
                String caractere;
                if (i == '\n') caractere = "\\n";
                else if (i == '\r') caractere = "\\r";
                else if (i == '\t') caractere = "\\t";
                else if (i == ' ') caractere = "' '";
                else caractere = String.valueOf((char) i);
                System.out.println(caractere + ": " + freq[i]);
            }
        }
    }

    // Imprime o conteúdo inicial do Min-Heap
    private static void imprimirMinHeapInicial(MinHeap minHeap) {
        System.out.println("\n--- ETAPA 2: Min-Heap Inicial ---");
        // O método toString() do ArrayList já fornece uma boa visualização
        System.out.println(minHeap);
    }

    // Imprime uma representação visual da Árvore de Huffman
    private static void imprimirArvoreHuffman(No raiz) {
        System.out.println("\n--- ETAPA 3: Arvore de Huffman ---");
        // Chama o método recursivo para imprimir a árvore
        imprimirArvoreRecursivo(raiz, "", true);
    }
    
    // Método auxiliar recursivo para imprimir a árvore com indentação
    private static void imprimirArvoreRecursivo(No no, String prefixo, boolean isEsquerda) {
        if (no == null) return;
    
        System.out.print(prefixo);
        // Usa caracteres especiais para desenhar as ramificações
        System.out.print(isEsquerda ? "├──" : "└──" );
    
        // Se for folha, imprime o caractere
        if (no.isFolha()) {
            System.out.println("'" + no.caractere + "' (" + no.frequencia + ")");
        } else {
            // Se for nó interno, imprime sua frequência somada
            System.out.println("Int(" + no.frequencia + ")");
        }
        
        // Prepara o prefixo para a próxima chamada recursiva
        String novoPrefixo = prefixo + (isEsquerda ? "│   " : "    ");
        imprimirArvoreRecursivo(no.esquerda, novoPrefixo, true);
        imprimirArvoreRecursivo(no.direita, novoPrefixo, false);
    }

    // Imprime a tabela final de códigos binários
    private static void imprimirTabelaCodigos(String[] tabela) {
        System.out.println("\n--- ETAPA 4: Tabela de Codigos ---");
        for (int i = 0; i < tabela.length; i++) {
            if (tabela[i] != null) {
                String caractere;
                if (i == '\n') caractere = "\\n";
                else if (i == '\r') caractere = "\\r";
                else if (i == '\t') caractere = "\\t";
                else if (i == ' ') caractere = "' '";
                else caractere = String.valueOf((char) i);
                System.out.println(caractere + ": " + tabela[i]);
            }
        }
    }
    
    // Imprime o resumo final com as estatísticas de compressão
    private static void imprimirResumo(long tamanhoOriginal, long tamanhoComprimido, String arqDestino) {
        System.out.println("\n--- ETAPA 5: Resumo da Compressao ---");
        System.out.println("Arquivo de saida gerado: " + arqDestino);
        System.out.println("Tamanho original: " + tamanhoOriginal + " bytes");
        System.out.println("Tamanho comprimido: " + tamanhoComprimido + " bytes");

        // Calcula a taxa de compressão
        double taxa = 100.0 * (tamanhoOriginal - tamanhoComprimido) / tamanhoOriginal;
        // Formata para exibir com duas casas decimais
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("Taxa de compressao: " + df.format(taxa) + "%");
    }
}
