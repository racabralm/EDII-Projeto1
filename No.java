/*
 * Grupo:
 * Bruna Amorim Maia - 10431883
 * Rafael Araujo Cabral Moreira - 10441919
 * Rute Willemann - 10436781
 */

public class No implements Comparable<No> {

    // Atributos do Nó
    public final char caractere;
    public final int frequencia;
    public final No esquerda;
    public final No direita;

    // Construtor para nós FOLHA (que representam um caractere)
    public No(char caractere, int frequencia) {
        this.caractere = caractere;
        this.frequencia = frequencia;
        this.esquerda = null;
        this.direita = null;
    }

    // Construtor para nós INTERNOS (junção de dois nós)
    public No(int frequencia, No esquerda, No direita) {
        this.caractere = '\0'; // Caractere nulo para nós internos
        this.frequencia = frequencia;
        this.esquerda = esquerda;
        this.direita = direita;
    }

    // Verifica se o nó é uma folha (não tem filhos)
    public boolean isFolha() {
        return this.esquerda == null && this.direita == null;
    }

    // Compara este nó com outro nó baseado na frequência
    @Override
    public int compareTo(No outroNo) {
        return this.frequencia - outroNo.frequencia;
    }

    // Representação em String para facilitar a depuração
    @Override
    public String toString() {
        if (isFolha()) {
            return "No('" + this.caractere + "', " + this.frequencia + ")";
        }
        return "No(interno, " + this.frequencia + ")";
    }
}
