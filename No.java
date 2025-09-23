/*
 * Grupo:
 * Bruna Amorim Maia - 10431883
 * Rafael Araujo Cabral Moreira - 10441919
 * Rute Willemann - 10436781
 */
public class No implements Comparable<No> {

    // --- ATRIBUTOS ---
    // O caractere que este nó representa (se for uma folha)
    public final char caractere;
    // A frequência do caractere ou a soma das frequências dos filhos
    public final int frequencia;
    // Referência para o filho da esquerda na árvore (bit 0)
    public final No esquerda;
    // Referência para o filho da direita na árvore (bit 1)
    public final No direita;

    // --- CONSTRUTORES ---

    // Construtor para nós do tipo FOLHA
    // Nós folha representam os caracteres do arquivo
    public No(char caractere, int frequencia) {
        // Armazena o caractere e sua frequência
        this.caractere = caractere;
        this.frequencia = frequencia;
        // Nós folha não têm filhos, então as referências são nulas
        this.esquerda = null;
        this.direita = null;
    }

    // Construtor para nós do tipo INTERNO
    // Nós internos são usados para construir a árvore, unindo dois nós
    public No(int frequencia, No esquerda, No direita) {
        // Um nó interno não representa um caractere específico
        // '\0' é um caractere nulo, usado como placeholder
        this.caractere = '\0';
        // A frequência é a soma das frequências dos seus filhos
        this.frequencia = frequencia;
        // Armazena as referências para os filhos
        this.esquerda = esquerda;
        this.direita = direita;
    }

    // --- MÉTODOS ---

    // Verifica se o nó é uma folha na árvore
    // Uma folha é identificada por não ter filhos
    public boolean isFolha() {
        return this.esquerda == null && this.direita == null;
    }

    // Método de comparação, essencial para a Fila de Prioridades (MinHeap)
    // A interface Comparable exige a implementação deste método
    @Override
    public int compareTo(No outroNo) {
        // A comparação é baseada na frequência
        // Isso garante que o MinHeap sempre removerá o nó de menor frequência
        // Se o resultado for negativo, este nó vem antes do outroNo
        // Se for positivo, vem depois
        // Se for zero, são considerados iguais em prioridade
        return this.frequencia - outroNo.frequencia;
    }

    // Representação do nó em formato de String
    // Extremamente útil para depurar e visualizar a estrutura
    @Override
    public String toString() {
        // Se for uma folha, mostra o caractere e a frequência
        if (isFolha()) {
            return "No('" + this.caractere + "', " + this.frequencia + ")";
        }
        // Se for um nó interno, indica isso e mostra a frequência somada
        return "No(interno, " + this.frequencia + ")";
    }
}
