package com.github.kkrauss8.huff4j;

public class HuffNode implements Comparable<HuffNode> {
	
	private char element;
	private int weight;
	
	private HuffNode left;
	private HuffNode right;
	
	public HuffNode(char element, int weight, HuffNode left, HuffNode right) {
		this.element = element;
		this.weight = weight;
		this.left = left;
		this.right = right;
	}
	
	public char getElement() {
		return element;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public HuffNode getLeftChild() {
		return left;
	}

	public HuffNode getRightChild() {
		return right;
	}
	
	@Override
	public int compareTo(HuffNode o) {
		return Integer.compare(weight, o.weight);
	}
	
}
