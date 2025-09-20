/*
 * Grupo:
 * Bruna Amorim Maia - 10431883
 * Rafael Araujo Cabral Moreira - 10441919
 * Rute Willemann - 10436781
 */

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MinHeap {

    private final ArrayList<No> heap;

    public MinHeap() {
        this.heap = new ArrayList<>();
    }

    // Insere um novo nó no heap, mantendo a propriedade de heap mínimo
    public void inserir(No no) {
        heap.add(no); // Adiciona no final
        heapifyParaCima(heap.size() - 1); // Reorganiza para cima
    }

    // Remove e retorna o nó com a menor frequência (a raiz do heap)
    public No remover() {
        if (tamanho() == 0) {
            throw new NoSuchElementException("O heap está vazio");
        }
        
        No raiz = heap.get(0); // O menor elemento
        
        // Se houver mais de um elemento, move o último para a raiz e reorganiza
        if (tamanho() > 1) {
            heap.set(0, heap.remove(heap.size() - 1));
            heapifyParaBaixo(0);
        } else {
            heap.remove(0); // Apenas remove a raiz se for o único elemento
        }
        
        return raiz;
    }
    
    // Retorna o número de elementos no heap
    public int tamanho() {
        return heap.size();
    }

    // --- Métodos Auxiliares (privados) ---

    // Reorganiza o heap de baixo para cima a partir de um índice
    private void heapifyParaCima(int indice) {
        int pai = (indice - 1) / 2;
        while (indice > 0 && heap.get(indice).compareTo(heap.get(pai)) < 0) {
            trocar(indice, pai);
            indice = pai;
            pai = (indice - 1) / 2;
        }
    }

    // Reorganiza o heap de cima para baixo a partir de um índice
    private void heapifyParaBaixo(int indice) {
        int menorFilho;
        while (true) {
            int esquerdo = 2 * indice + 1;
            int direito = 2 * indice + 2;
            menorFilho = indice;

            // Encontra o menor entre o pai e os filhos
            if (esquerdo < tamanho() && heap.get(esquerdo).compareTo(heap.get(menorFilho)) < 0) {
                menorFilho = esquerdo;
            }
            if (direito < tamanho() && heap.get(direito).compareTo(heap.get(menorFilho)) < 0) {
                menorFilho = direito;
            }

            // Se o menor ainda é o pai, a estrutura está correta
            if (menorFilho == indice) {
                break;
            }
            
            trocar(indice, menorFilho);
            indice = menorFilho;
        }
    }

    // Troca dois elementos de posição no ArrayList
    private void trocar(int i, int j) {
        No temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
    
    // Representação em String para facilitar a depuração
    @Override
    public String toString() {
        return heap.toString();
    }
}
