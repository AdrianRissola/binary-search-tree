package com.slingr.binarysearchtree;

public class Node {
	
	private Node right;
	private Node left;
	private Integer value;
	
	public Node() {
	}
	
	public Node(Integer value) {
		this.setValue(value);
	}
	
	public Node getRight() {
		return right;
	}
	
	public void setRight(Node right) {
		this.right = right;
	}
	
	public Node getLeft() {
		return left;
	}
	
	public void setLeft(Node left) {
		this.left = left;
	}
	
	public Integer getValue() {
		return value;
	}
	
	public void setValue(Integer value) {
		this.value = value;
	}
	
	public boolean hasRight() {
		return this.right!=null;
	}
	
	public boolean hasLeft() {
		return this.left!=null;
	}
	
	public boolean isLeaf() {
		return !this.hasLeft() && !this.hasRight();
	}
	
	public boolean hasRight(Node node) {
		return this.hasRight() && this.right.equals(node);
	}
	
	public boolean hasLeft(Node node) {
		return this.hasLeft() && this.left.equals(node);
	}
	
	@Override
	public String toString() {
		String left = this.left!=null ? this.left.getValue().toString() : "null";
		String right = this.right!=null ? this.right.getValue().toString() : "null";
		return "value: " + value + " left: " + left + " right: " + right;
	}
	
	

}
