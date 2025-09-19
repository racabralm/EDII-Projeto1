/*
 * Grupo:
 * Bruna Amorim Maia - 10431883
 * Rafael Araujo Cabral Moreira - 10441919
 * Rute Willemann - 10436781
 */
public class No implements Comparable<No> {

    // --- Atributos ---
    // TODO 1 (Pessoa 1): Declare os atributos finais para:
    // - char caractere;
    // - int frequencia;
    // - No esquerda;
    // - No direita;


    // --- Construtores ---
    /**
     * Construtor para nós FOLHA (que representam um caractere).
     */
    public No(char caractere, int frequencia) {
        // TODO 2 (Pessoa 1): Inicialize os atributos para um nó folha.
        // Os filhos (esquerda e direita) devem ser nulos.
    }

    /**
     * Construtor para nós INTERNOS (junção de dois nós).
     */
    public No(int frequencia, No esquerda, No direita) {
        // TODO 3 (Pessoa 1): Inicialize os atributos para um nó interno.
        // O caractere pode ser um valor nulo como '\0'.
    }

    // --- Métodos ---
    /**
     * Verifica se o nó é uma folha (não tem filhos).
     * @return true se for uma folha, false caso contrário.
     */
    public boolean isFolha() {
        // TODO 4 (Pessoa 1): Implemente a lógica para verificar se o nó é uma folha.
        // Dica: verifique se os filhos da esquerda e direita são nulos.
        return false; // Linha temporária
    }

    /**
     * Compara este nó com outro nó baseado na frequência.
     * Este método é o "cérebro" da fila de prioridades (Min-Heap).
     * @param outroNo O nó a ser comparado.
     * @return um valor < 0 se a frequência deste nó for menor,
     * 0 se forem iguais,
     * um valor > 0 se for maior.
     */
    @Override
    public int compareTo(No outroNo) {
        // TODO 5 (Pessoa 1): Implemente a lógica de comparação de frequência.
        // Dica: a fórmula é `this.frequencia - outroNo.frequencia`.
        return 0; // Linha temporária
    }
}
