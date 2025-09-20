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
        // TODO 1 (Pessoa 3): Implementar a lógica principal para linha de comando
    }

    // --- LÓGICA DE COMPRESSÃO ---
    public static void comprimir(String arqOrigem, String arqDestino) throws IOException {
        // Chama os métodos implementados
        byte[] bytesArquivo = lerArquivo(arqOrigem);
        int[] tabelaFrequencia = construirTabelaFrequencia(bytesArquivo);
        No raiz = construirArvoreHuffman(tabelaFrequencia);
        String[] tabelaCodigos = new String[TAMANHO_ASCII];
        gerarTabelaCodigos(raiz, "", tabelaCodigos);
        byte[] dadosComprimidos = codificarDados(bytesArquivo, tabelaCodigos);
        escreverArquivoComprimido(arqDestino, tabelaFrequencia, dadosComprimidos);
    }

    // Constrói a tabela de frequência dos caracteres
    private static int[] construirTabelaFrequencia(byte[] dados) {
        int[] freq = new int[TAMANHO_ASCII];
        for (byte b : dados) {
            freq[b & 0xFF]++; // Trata o byte como valor de 0 a 255
        }
        return freq;
    }

    // Constrói a Árvore de Huffman usando o MinHeap
    private static No construirArvoreHuffman(int[] freq) {
        MinHeap minHeap = new MinHeap();
        for (int i = 0; i < TAMANHO_ASCII; i++) {
            if (freq[i] > 0) {
                minHeap.inserir(new No((char) i, freq[i]));
            }
        }

        // Combina os nós até sobrar apenas a raiz
        while (minHeap.tamanho() > 1) {
            No esquerda = minHeap.remover();
            No direita = minHeap.remover();
            int novaFrequencia = esquerda.frequencia + direita.frequencia;
            No pai = new No(novaFrequencia, esquerda, direita);
            minHeap.inserir(pai);
        }
        return minHeap.remover();
    }

    // Gera a tabela de códigos binários recursivamente
    private static void gerarTabelaCodigos(No no, String codigo, String[] tabela) {
        if (no == null) {
            return;
        }
        // Se é uma folha, achou um caractere
        if (no.isFolha()) {
            tabela[no.caractere] = codigo;
        } else {
            // Continua o percurso
            gerarTabelaCodigos(no.esquerda, codigo + "0", tabela);
            gerarTabelaCodigos(no.direita, codigo + "1", tabela);
        }
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
    
    // Lê um arquivo e retorna seu conteúdo como um array de bytes
    private static byte[] lerArquivo(String caminho) throws IOException {
        File arquivo = new File(caminho);
        byte[] bytesArquivo = new byte[(int) arquivo.length()];
        try (FileInputStream fis = new FileInputStream(arquivo)) {
            fis.read(bytesArquivo);
        }
        return bytesArquivo;
    }

    // Escreve o arquivo comprimido final (.huff)
    private static long escreverArquivoComprimido(String caminho, int[] freq, byte[] dados) throws IOException {
        long totalBits = 0;
        try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(caminho)))) {
            // Escreve o cabeçalho com as frequências
            for (int f : freq) {
                out.writeInt(f);
            }

            // Escreve um byte de padding (nesta implementação, sempre 0)
            out.write(0);
            
            // Escreve os dados comprimidos
            out.write(dados);
        }
        return totalBits;
    }

    // --- PARTE DA DESCOMPRESSÃO ---
    
    public static void descomprimir(String arqOrigem, String arqDestino) throws IOException {
        // TODO 12 (Pessoa 3): Implementar o fluxo de descompressão
    }

    private static void decodificarDados(String arqDestino, No raiz, byte[] dados, long totalChars) throws IOException {
        // TODO 13 (Pessoa 3): Implementar a decodificação
    }
}
