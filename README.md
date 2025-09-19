# Projeto 1: Compressor de Arquivos com Algoritmo de Huffman

Implementação do algoritmo de compressão sem perdas de David Huffman, desenvolvida como parte da disciplina de Estrutura de Dados II. 

O programa é capaz de comprimir e descomprimir arquivos de texto através da linha de comando, utilizando estruturas de dados fundamentais como Fila de Prioridades (Min-Heap) e Árvore Binária.

---

## 🚀 Estruturas de Dados Utilizadas

O núcleo do projeto reside na implementação e utilização eficiente das seguintes estruturas de dados, construídas do zero sem o uso de bibliotecas de coleções do Java:

* **Fila de Prioridades (Min-Heap):** Utilizada para gerenciar os nós da árvore durante sua construção. A estrutura garante que os dois nós de menor frequência sejam sempre acessados de forma eficiente (complexidade logarítmica), o que é essencial para a montagem correta da Árvore de Huffman. A implementação foi feita sobre um `ArrayList`.

* **Árvore de Huffman:** Uma árvore binária onde cada folha representa um caractere e seu peso (frequência). Os nós internos representam a soma das frequências de seus filhos. A estrutura da árvore é otimizada para que caracteres mais frequentes tenham um caminho mais curto a partir da raiz, resultando em códigos binários menores. 

## ✨ Funcionalidades

* **Compressão:** Lê um arquivo de texto, analisa a frequência de cada caractere, constrói la Árvore de Huffman e gera um arquivo binário `.huff` contendo o cabeçalho (tabela de frequências) e os dados comprimidos. 
* **Descompressão:** Lê um arquivo `.huff`, reconstrói a Árvore de Huffman a partir do cabeçalho e utiliza a árvore para decodificar os dados, restaurando o arquivo original com perfeição.
* **Interface de Linha de Comando:** A interação com o programa é feita via terminal, permitindo a automação e o uso em scripts. 

---

## ⚙️ Como Executar

### Compilação

Navegue até a pasta raiz do projeto e execute o seguinte comando para compilar todos os arquivos `.java`:

```bash
javac Huffman.java No.java MinHeap.java
