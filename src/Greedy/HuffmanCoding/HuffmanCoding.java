package Greedy.HuffmanCoding;

import java.util.Comparator;
import java.util.PriorityQueue;

public class HuffmanCoding {

	//Going up the tree till root node is reached.
    public static String encode(Node node) {
        String result = "";
        Node currNode = node;
        while(currNode.getParent() != null) {
            if (currNode.getParent().getLeftChild() == currNode) result = "0" + result;
            else if (currNode.getParent().getRightChild() == currNode) result = "1" + result;
            currNode = currNode.getParent();
        }

        return result;
    }

    public static Node buildHuffman(int n, char[] characters, double[] frequencies) {
        return properTreeBuilding(n, characters, frequencies);
    }

    public static Node properTreeBuilding(int n, char[] characters, double[] frequencies) {
        PriorityQueue<Node> pq = new PriorityQueue<>(new NodeComparator());
        for(int i = 1; i <= n; i++) pq.add(new Node(characters[i], frequencies[i]));

        while(true) {
            Node smallestFreq = pq.poll();
            if (pq.isEmpty()) return smallestFreq;
            Node secondSmallest = pq.poll();
            Node newNode = new Node(smallestFreq, secondSmallest);
            smallestFreq.setParent(newNode);
            secondSmallest.setParent(newNode);
            pq.add(newNode);
        }
    }

    public static String decode(Node root, String binaryString) {
        String answer = "";
        Node curr = root;
        for(int i = 0; i < binaryString.length(); i++) {
            if (binaryString.charAt(i) == '0') curr = curr.getLeftChild();
            if (binaryString.charAt(i) == '1') curr = curr.getRightChild();
            if (curr.getLeftChild() == null && curr.getRightChild() == null) {
                answer += curr.getSymbol();
                curr = root;
            }
        }
        return answer;
    }

    public static void main(String[] args) {
        int n = 5;
        char[] c = {0, 'p','e','a','r','l'};
        double[] f = {0, 0.2, .35 , .08 , .12, .25};
        Node root = buildHuffman(n, c, f);
        System.out.println(decode(root, "11"));
    }

    static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node node, Node node2) {
            return Double.compare(node.frequency, node2.frequency);
        }
    }

    static class Node {

        char symbol;
        double frequency;

        Node parent;

        Node leftChild;

        Node rightChild;

        public Node(char symbol, double frequency) {
            this.symbol = symbol;
            this.frequency = frequency;
        }

        public Node(char symbol, double frequency, Node parent) {
            this(symbol, frequency);
            this.parent = parent;
        }

        public Node(char symbol, double frequency, Node leftChild, Node rightChild) {
            this(symbol, frequency);
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }

        public Node(Node child1, Node child2) {
            this.symbol = 0;
            this.frequency = child1.frequency + child2.frequency;
            this.leftChild = child2;
            this.rightChild = child1;
            this.parent = null;
        }

        public char getSymbol() {
            return symbol;
        }

        public double getFrequency() {
            return frequency;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public Node getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(Node leftChild) {
            this.leftChild = leftChild;
        }

        public Node getRightChild() {
            return rightChild;
        }

        public void setRightChild(Node rightChild) {
            this.rightChild = rightChild;
        }
    }
}
