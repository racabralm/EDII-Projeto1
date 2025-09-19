/*
 * Grupo:
 * Bruna Amorim Maia - 10431883
 * Rafael Araujo Cabral Moreira - 10441919
 * Rute Willemann - 10436781
 */
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MinHeap {

    private ArrayList<No> heap;

    public MinHeap() {
        // TODO 1 (Pessoa 1): Inicialize o ArrayList do heap.
    }

    /**
     * Insere um novo nó no heap, mantendo a propriedade de heap mínimo.
     * @param no O nó a ser inserido.
     */
    public void inserir(No no) {
        // TODO 2 (Pessoa 1): Implemente a inserção.
        // 1. Adicione o novo nó ao final do ArrayList.
        // 2. Chame o método heapifyParaCima a partir do índice do último elemento.
    }

    /**
     * Remove e retorna o nó com a menor frequência (a raiz do heap).
     * @return O nó de menor frequência.
     */
    public No remover() {
        // TODO 3 (Pessoa 1): Implemente a remoção.
        // 1. Verifique se o heap não está vazio. Se estiver, lance uma exceção.
        // 2. Salve a raiz (elemento no índice 0) para retorná-la no final.
        // 3. Remova o último elemento do heap e coloque-o na posição da raiz (índice 0).
        // 4. Chame o método heapifyParaBaixo a partir da raiz (índice 0) para reorganizar.
        // 5. Retorne a raiz original.
        throw new NoSuchElementException("Heap está vazio!"); // Linha temporária
    }
    
    public int tamanho() {
        // TODO: Implementar.
        return 0; // Linha temporária
    }

    // --- Métodos Auxiliares ---

    private void heapifyParaCima(int indice) {
        // TODO 4 (Pessoa 1): Implemente a lógica do heapify para cima.
        // Enquanto o nó atual for menor que seu pai, troque-os de lugar.
    }

    private void heapifyParaBaixo(int indice) {
        // TODO 5 (Pessoa 1): Implemente a lógica do heapify para baixo.
        // Encontre o menor dos filhos. Se o filho for menor que o pai, troque-os.
        // Repita o processo até que a propriedade do heap seja restaurada.
    }

    private void trocar(int i, int j) {
        // TODO 6 (Pessoa 1): Implemente a troca de dois elementos no ArrayList.
    }
}
