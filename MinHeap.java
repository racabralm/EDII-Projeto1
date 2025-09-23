/*
 * Grupo:
 * Bruna Amorim Maia - 10431883
 * Rafael Araujo Cabral Moreira - 10441919
 * Rute Willemann - 10436781
 */
import java.util.ArrayList;
import java.util.NoSuchElementException;

// Implementa uma Fila de Prioridades Mínima (Min-Heap)
// Esta estrutura é a peça central para construir a árvore de Huffman eficientemente
public class MinHeap {

    // O heap é armazenado em um ArrayList de Nós
    private final ArrayList<No> heap;

    // Construtor inicializa um heap vazio
    public MinHeap() {
        this.heap = new ArrayList<>();
    }

    // --- MÉTODOS PÚBLICOS ---

    // Insere um novo nó no heap
    public void inserir(No no) {
        // O novo elemento é sempre adicionado no final do ArrayList
        heap.add(no);
        // Em seguida, a propriedade do heap é restaurada "subindo" o elemento
        heapifyParaCima(heap.size() - 1);
    }

    // Remove e retorna o elemento de menor prioridade (a raiz do heap)
    public No remover() {
        // Verifica se o heap está vazio para evitar erros
        if (tamanho() == 0) {
            throw new NoSuchElementException("O heap está vazio");
        }
        
        // O menor elemento está sempre na primeira posição (índice 0)
        No raiz = heap.get(0);
        
        // Se houver mais de um elemento no heap
        if (tamanho() > 1) {
            // Pega o último elemento e o move para a raiz
            heap.set(0, heap.remove(heap.size() - 1));
            // Restaura a propriedade do heap "descendo" o novo elemento da raiz
            heapifyParaBaixo(0);
        } else {
            // Se for o único elemento, apenas o remove
            heap.remove(0);
        }
        
        // Retorna a raiz original, que era o menor elemento
        return raiz;
    }
    
    // Retorna a quantidade de elementos atualmente no heap
    public int tamanho() {
        return heap.size();
    }

    // --- MÉTODOS AUXILIARES PRIVADOS (HEAPIFY) ---

    // Restaura a propriedade do heap movendo um elemento para cima
    private void heapifyParaCima(int indice) {
        // Calcula o índice do nó pai
        int pai = (indice - 1) / 2;
        // Enquanto o nó atual não for a raiz e for menor que seu pai
        while (indice > 0 && heap.get(indice).compareTo(heap.get(pai)) < 0) {
            // Troca o nó atual com seu pai
            trocar(indice, pai);
            // Atualiza o índice para a posição do pai para continuar a subida
            indice = pai;
            pai = (indice - 1) / 2;
        }
    }

    // Restaura a propriedade do heap movendo um elemento para baixo
    private void heapifyParaBaixo(int indice) {
        int menorFilho;
        // Loop continua enquanto o nó tiver filhos para verificar
        while (true) {
            // Calcula os índices dos filhos esquerdo e direito
            int esquerdo = 2 * indice + 1;
            int direito = 2 * indice + 2;
            menorFilho = indice;

            // Verifica se o filho esquerdo existe e é menor que o nó atual
            if (esquerdo < tamanho() && heap.get(esquerdo).compareTo(heap.get(menorFilho)) < 0) {
                menorFilho = esquerdo;
            }
            // Verifica se o filho direito existe e é menor que o menor encontrado até agora
            if (direito < tamanho() && heap.get(direito).compareTo(heap.get(menorFilho)) < 0) {
                menorFilho = direito;
            }

            // Se o menor elemento entre o nó e seus filhos for o próprio nó, a estrutura está correta
            if (menorFilho == indice) {
                break; // Encerra o loop
            }
            
            // Caso contrário, troca o nó com seu menor filho
            trocar(indice, menorFilho);
            // Atualiza o índice para a posição do filho para continuar a descida
            indice = menorFilho;
        }
    }

    // Método auxiliar para trocar a posição de dois elementos no ArrayList
    private void trocar(int i, int j) {
        No temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
    
    // Representação do heap em formato de String para depuração
    @Override
    public String toString() {
        return heap.toString();
    }
}
