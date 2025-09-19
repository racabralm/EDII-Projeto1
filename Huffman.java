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
        // TODO 1 (Pessoa 3): Lógica principal para linha de comando.
        // 1. Verifique se `args.length` é igual a 3. Se não for, imprima uma mensagem de erro.
        // 2. Extraia o modo ('-c' ou '-d'), o arquivo de origem e o de destino de `args`.
        // 3. Chame o método `comprimir` se o modo for '-c'.
        // 4. Chame o método `descomprimir` se o modo for '-d'.
        // 5. Se o modo for inválido, imprima uma mensagem de erro.
    }

    // ===================================================================================
    // --- PARTE DA COMPRESSÃO (PESSOA 2) ---
    // ===================================================================================

    public static void comprimir(String arqOrigem, String arqDestino) throws IOException {
        // TODO 2 (Pessoa 3): Chamar o método para ler os bytes do arquivo de origem.
        byte[] bytesArquivo = null; // = lerArquivo(arqOrigem); // Pessoa 3 implementa `lerArquivo`

        // TODO 3 (Pessoa 2): Chamar o método para construir a tabela de frequências.
        int[] tabelaFrequencia = null; // = construirTabelaFrequencia(bytesArquivo);

        // TODO 4 (Pessoa 2): Chamar o método para construir a Árvore de Huffman.
        No raiz = null; // = construirArvoreHuffman(tabelaFrequencia);

        // TODO 5 (Pessoa 2): Chamar o método para gerar a tabela de códigos.
        String[] tabelaCodigos = new String[TAMANHO_ASCII];
        // gerarTabelaCodigos(raiz, "", tabelaCodigos);
        
        // TODO 6 (Pessoa 2): Chamar o método para codificar os dados.
        byte[] dadosComprimidos = null; // = codificarDados(bytesArquivo, tabelaCodigos);

        // TODO 7 (Pessoa 3): Chamar o método para escrever o arquivo comprimido final.
        // escreverArquivoComprimido(arqDestino, tabelaFrequencia, dadosComprimidos);
    }

    private static int[] construirTabelaFrequencia(byte[] dados) {
        // TODO 8 (Pessoa 2): Implemente a contagem de frequência.
        // 1. Crie um `int[] freq` de tamanho `TAMANHO_ASCII`.
        // 2. Percorra o array `dados` (byte[]).
        // 3. Para cada byte `b`, incremente `freq[b & 0xFF]`. A máscara `& 0xFF` é
        //    importante para tratar o byte como um valor positivo de 0 a 255.
        // 4. Retorne o array de frequências.
        return new int[TAMANHO_ASCII]; // Linha temporária
    }

    private static No construirArvoreHuffman(int[] freq) {
        // TODO 9 (Pessoa 2): Implemente a construção da árvore.
        // 1. Crie uma instância de `MinHeap`.
        // 2. Percorra o array `freq`. Se a frequência de um caractere for > 0,
        //    crie um `No` para ele e insira no Min-Heap.
        // 3. Enquanto o tamanho do heap for > 1:
        //    a. Remova os dois nós de menor frequência.
        //    b. Crie um novo nó interno com a soma das frequências.
        //    c. Insira este novo nó de volta no heap.
        // 4. No final, remova o único nó restante (a raiz) e retorne-o.
        return null; // Linha temporária
    }

    private static void gerarTabelaCodigos(No no, String codigo, String[] tabela) {
        // TODO 10 (Pessoa 2): Implemente a geração de códigos recursivamente.
        // 1. Se o nó for nulo, retorne.
        // 2. Se o nó for uma folha, armazene o `codigo` atual em `tabela[no.caractere]`.
        // 3. Se não for folha, chame recursivamente para a esquerda (adicionando "0" ao código)
        //    e para a direita (adicionando "1" ao código).
    }

    private static byte[] codificarDados(byte[] dadosOriginais, String[] tabelaCodigos) {
        // TODO 11 (Pessoa 2): Implemente a codificação.
        // 1. Use um `StringBuilder` para concatenar os códigos de Huffman de todos
        //    os bytes do arquivo original.
        // 2. Converta a string de bits resultante em um `BitSet`.
        // 3. Converta o `BitSet` em um array de bytes (`.toByteArray()`) e retorne-o.
        return new byte[0]; // Linha temporária
    }
    
    // ===================================================================================
    // --- PARTE DA DESCOMPRESSÃO E ARQUIVOS (PESSOA 3) ---
    // ===================================================================================

    public static void descomprimir(String arqOrigem, String arqDestino) throws IOException {
        // TODO 12 (Pessoa 3): Implemente o fluxo de descompressão.
        // Use um `try-with-resources` com `DataInputStream` para ler o arquivo de origem.
        // 1. Leia o cabeçalho: Percorra um loop de 0 a 255 e leia cada `int` da frequência
        //    para reconstruir a `tabelaFrequencia`.
        // 2. Reconstrua a Árvore de Huffman chamando `construirArvoreHuffman` (feito pela Pessoa 2).
        // 3. Leia o byte de padding (ainda que não usado, ele deve ser lido).
        // 4. Leia o restante dos bytes do arquivo (`.readAllBytes()`).
        // 5. Chame o método `decodificarDados` para finalizar.
    }

    private static void decodificarDados(String arqDestino, No raiz, byte[] dados, long totalChars) throws IOException {
        // TODO 13 (Pessoa 3): Implemente a decodificação.
        // 1. Converta o `dados` (byte[]) em uma string de "0"s e "1"s.
        // 2. Use um `FileOutputStream` para escrever no arquivo de destino.
        // 3. Percorra a string de bits:
        //    a. Comece com `noAtual = raiz`.
        //    b. Para cada bit, navegue para a esquerda ('0') ou direita ('1').
        //    c. Se `noAtual` se tornar uma folha, escreva o caractere no arquivo,
        //       e redefina `noAtual = raiz`.
        // 4. Continue até que o número esperado de caracteres (`totalChars`) seja decodificado.
    }

    private static byte[] lerArquivo(String caminho) throws IOException {
        // TODO 14 (Pessoa 3): Implemente a leitura de um arquivo para um byte[].
        // Dica: use `FileInputStream` e `readAllBytes()` ou um loop de leitura.
        return new byte[0]; // Linha temporária
    }

    private static long escreverArquivoComprimido(String caminho, int[] freq, byte[] dados) throws IOException {
        // TODO 15 (Pessoa 3): Implemente a escrita do arquivo comprimido.
        // Use um `DataOutputStream`.
        // 1. Escreva o cabeçalho: Percorra o array `freq` e escreva cada `int`.
        // 2. Escreva um byte de padding (pode ser 0).
        // 3. Escreva o array de `dados` comprimidos.
        // Retorne o total de bits dos dados comprimidos para o resumo.
        return 0; // Linha temporária
    }
}
