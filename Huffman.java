/*
 * Grupo:
 * Bruna Amorim Maia - 10431883
 * Rafael Araujo Cabral Moreira - 10441919
 * Rute Willemann - 10436781
 */

import java.io.*;
import java.util.BitSet;

public class Huffman {

    private static final int TAMANHO_ASCII = 256;

    public static void main(String[] args) throws IOException {
        // Verifica se o usuario passou os parametros corretos
        if (args.length < 3) {
            System.out.println("Uso:");
            System.out.println("  Para comprimir:   java -jar huffman.jar -c <entrada.txt> <saida.huff>");
            System.out.println("  Para descomprimir: java -jar huffman.jar -d <entrada.huff> <saida.txt>");
            return;
        }

        String opcao = args[0];      // -c ou -d
        String entrada = args[1];    // nome do arquivo de entrada
        String saida = args[2];      // nome do arquivo de saida

        // Decide se vai comprimir ou descomprimir 
        if (opcao.equals("-c")) {
            comprimir(entrada, saida);
            System.out.println("Arquivo comprimido gerado: " + saida);
        } else if (opcao.equals("-d")) {
            descomprimir(entrada, saida);
            System.out.println("Arquivo descomprimido gerado: " + saida);
        } else {
            System.out.println("Opcao invalida: " + opcao);
            System.out.println("Use '-c' para comprimir ou '-d' para descomprimir.");
        }
    }

    public static void comprimir(String arqOrigem, String arqDestino) throws IOException {
        byte[] bytesArquivo = lerArquivo(arqOrigem);
        int[] tabelaFrequencia = construirTabelaFrequencia(bytesArquivo);
        No raiz = construirArvoreHuffman(tabelaFrequencia);
        String[] tabelaCodigos = gerarTabelaCodigos(raiz);
        byte[] dadosComprimidos = codificarDados(bytesArquivo, tabelaCodigos);
        
        escreverArquivoComprimido(arqDestino, tabelaFrequencia, bytesArquivo.length, dadosComprimidos);
    }

    // Le um arquivo e retorna seu conteudo como um array de bytes
    private static byte[] lerArquivo(String caminho) throws IOException {
        File arquivo = new File(caminho);
        byte[] bytesArquivo = new byte[(int) arquivo.length()];
        try (FileInputStream fis = new FileInputStream(arquivo)) {
            fis.read(bytesArquivo);
        }
        return bytesArquivo;
    }

    // Constroi a tabela de frequencia dos caracteres
    private static int[] construirTabelaFrequencia(byte[] dados) {
        int[] freq = new int[TAMANHO_ASCII];
        for (byte b : dados) {
            freq[b & 0xFF]++;
        }
        return freq;
    }

    // Constroi a Arvore de Huffman usando o MinHeap
    private static No construirArvoreHuffman(int[] freq) {
        MinHeap minHeap = new MinHeap();
        for (int i = 0; i < TAMANHO_ASCII; i++) {
            if (freq[i] > 0) {
                minHeap.inserir(new No((char) i, freq[i]));
            }
        }

        while (minHeap.tamanho() > 1) {
            No esquerda = minHeap.remover();
            No direita = minHeap.remover();
            int novaFrequencia = esquerda.frequencia + direita.frequencia;
            No pai = new No(novaFrequencia, esquerda, direita);
            minHeap.inserir(pai);
        }
        return minHeap.remover();
    }

    // Gera a tabela de codigos binarios
    private static String[] gerarTabelaCodigos(No raiz) {
        String[] tabela = new String[TAMANHO_ASCII];
        gerarCodigosRecursivo(raiz, "", tabela);
        return tabela;
    }
    
    // Funcao auxiliar recursiva para gerar os codigos
    private static void gerarCodigosRecursivo(No no, String codigo, String[] tabela) {
        if (no.isFolha()) {
            tabela[no.caractere] = codigo;
            return;
        }
        gerarCodigosRecursivo(no.esquerda, codigo + "0", tabela);
        gerarCodigosRecursivo(no.direita, codigo + "1", tabela);
    }

    // Codifica os dados do arquivo original em um array de bytes comprimido
    private static byte[] codificarDados(byte[] dadosOriginais, String[] tabelaCodigos) {
        StringBuilder sb = new StringBuilder();
        for (byte b : dadosOriginais) {
            sb.append(tabelaCodigos[b & 0xFF]);
        }
        
        String bitString = sb.toString();
        BitSet bitSet = new BitSet(bitString.length());
        for(int i = 0; i < bitString.length(); i++) {
            if(bitString.charAt(i) == '1') {
                bitSet.set(i);
            }
        }
        
        return bitSet.toByteArray();
    }

    // Escreve o arquivo comprimido final (.huff)
    private static void escreverArquivoComprimido(String caminho, int[] freq, long totalChars, byte[] dados) throws IOException {
        try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(caminho)))) {
            // Escreve o cabecalho com as frequencias
            for (int f : freq) {
                out.writeInt(f);
            }

            // Escreve o número total de bytes do arquivo original
            out.writeLong(totalChars);

            // Escreve os dados comprimidos
            out.write(dados);
        }
    }

    public static void descomprimir(String arqOrigem, String arqDestino) throws IOException {
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(arqOrigem)))) {

            // Le a tabela de frequencias
            int[] freq = new int[TAMANHO_ASCII];
            for (int i = 0; i < TAMANHO_ASCII; i++) {
                freq[i] = in.readInt();
            }
            
            // Lê o número total de bytes que o arquivo final deve ter
            long totalChars = in.readLong();

            // Reconstroi a arvore de Huffman
            No raiz = construirArvoreHuffman(freq);

            // Le todos os bytes comprimidos restantes no arquivo
            byte[] dadosComprimidos = in.readAllBytes();

            // Decodifica os dados e escreve no arquivo de saida
            decodificarDados(arqDestino, raiz, dadosComprimidos, totalChars);
        }
    }

    private static void decodificarDados(String arqDestino, No raiz, byte[] dados, long totalChars) throws IOException {
        // Converte os bytes comprimidos em um conjunto de bits
        BitSet bitSet = BitSet.valueOf(dados);

        // Abre o arquivo de saida para escrita de BYTES PUROS, evitando problemas de encoding
        try(FileOutputStream fos = new FileOutputStream(arqDestino)) {
            No atual = raiz;    // comeca na raiz da arvore
            long contagem = 0;  // conta quantos caracteres ja foram decodificados

            // Percorre os bits ate reconstruir todos os caracteres
            for (int i = 0; contagem < totalChars; i++) {
                // Caminha na arvore conforme o bit
                if (bitSet.get(i)) {
                    atual = atual.direita;  // bit 1 -> vai para direita
                } else {
                    atual = atual.esquerda; // bit 0 → vai para esquerda
                }

                if (atual.isFolha()) {
                    // Escreve o byte decodificado diretamente no arquivo
                    fos.write((byte) atual.caractere);
                    atual = raiz;   // volta para o topo da arvore
                    contagem++;     // incrementa o numero de caracteres ja restaurados
                }
            }
        }
    }
}