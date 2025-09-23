#!/bin/bash
clear

echo ""
echo "    =================================="
echo "         Estrutura de Dados II"
echo "         Prof. Dr. Jean M. Laine"
echo "               Projeto 1"
echo "    =================================="
echo ""
read -p "Pressione ENTER para continuar..."

echo "--- LIMPANDO ARQUIVOS ANTIGOS ---"
rm -f *.class
rm -f huffman.jar
rm -f teste.huff
rm -f teste_restaurado.txt

echo ""
echo "--- COMPILANDO ARQUIVOS JAVA ---"
javac Huffman.java No.java MinHeap.java
# Verifica se a compilação falhou
if [ $? -ne 0 ]; then
    echo ""
    echo "*** ERRO NA COMPILACAO! ***"
    read -p "Pressione ENTER para sair..."
    exit 1
fi

echo ""
echo "--- CRIANDO O ARQUIVO JAR ---"
jar cfe huffman.jar Huffman *.class

echo ""
echo "--- EXECUTANDO PROGRAMA ---"
echo "--- Comprimindo arq_de_teste.txt..."
java -jar huffman.jar -c arq_de_teste.txt teste.huff

echo ""
echo ""
echo ""
read -p "Pressione ENTER para continuar..."
echo ""
echo "--- Descomprimindo teste.huff..."
echo ""
java -jar huffman.jar -d teste.huff teste_restaurado.txt

echo ""
echo "--- VERIFICANDO RESULTADO ---"
echo ""
# O comando 'cmp -s' é o equivalente silencioso do 'fc /B' no Linux
if cmp -s "arq_de_teste.txt" "teste_restaurado.txt"; then
  echo "*** SUCESSO: O arquivo original e o restaurado sao IDENTICOS. ***"
else
  echo "*** FALHA: O arquivo original e o restaurado sao DIFERENTES. ***"
fi

echo ""
echo "Limpando arquivos temporarios..."
rm -f teste.huff
rm -f teste_restaurado.txt

echo ""
