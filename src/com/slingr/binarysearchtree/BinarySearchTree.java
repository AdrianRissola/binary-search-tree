package com.slingr.binarysearchtree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BinarySearchTree {
	
	private Node root;
	private List<Integer> values;
	private static final String INORDER = "INORDER";
	private static final String PREORDER = "PREORDER";
	private static final String POSTORDER = "POSTORDER";
	
	public BinarySearchTree() {
		this.values = new LinkedList<>();
	}
	
	public BinarySearchTree(Integer[] array) {
		this.validate(array);
		this.create(array);
	}

	private void validate(Integer[] array) {
		if(this.isNullOrEmpty(array)) throw new RuntimeException("Null or empty array is not allowed");
		boolean allValuesAreNull = true;
		for(Integer value : array) {
			if(value!=null) allValuesAreNull = false;
		}
		if (allValuesAreNull) throw new RuntimeException("Array with only null is not allowed");
	}

	private Node getRoot() {
		return this.root;
	}

	private void setRoot(Node root) {
		this.root = root;
	}
	
	public long getSize() {
		return this.values.size();
	}
	
	private void create(Integer[] array) {
		this.values = new LinkedList<>();
		this.values.add(array[0]);
		this.setRoot(new Node(array[0]));
		for(int i=1 ; i<array.length ; i++) {
			if(array[i]!=null) {
				this.values.add(array[i]);
				this.add(array[i], this.getRoot());
			}
		}
	}
	
	public Integer add(Integer value) {
		this.validateNull(value);
		this.values.add(value);
		Node node = this.add(value, this.getRoot());
		return node.getValue();
	}

	public boolean remove(Integer value) {
		boolean wasRemoved = this.values.remove(value);
		if(wasRemoved)
			// rebuilding tree from list
			this.create(this.values.toArray(new Integer[0]));
		return wasRemoved;
	}
	
	private Node add(Integer value, Node node) {
		if(node!=null && node.getValue()!=null && node.getValue().equals(value)) throw new RuntimeException("Duplicates are not allowed");
		if(this.getRoot()==null) {
			this.setRoot(new Node(value));
			return this.getRoot();
		}
		Node newNode = null;
		if(value>node.getValue()) {
			if(node.getRight()==null) {
				newNode = new Node(value);
				node.setRight(newNode);
			} else {
				newNode = this.add(value, node.getRight());
			}
		} else {
			if(node.getLeft()==null) {
				newNode = new Node(value);
				node.setLeft(newNode);
			} else {
				newNode = this.add(value, node.getLeft());
			}
		}
		return newNode;
	}
	
	public boolean exist(Integer value) {
		this.validateNull(value);
		return exist(value, this.getRoot());
	}
	
	public void printInorder() {
		System.out.println(this.getValues(INORDER));
	}
	
	public void printPreorder() {
		System.out.println(this.getValues(PREORDER));
	}
	
	public void printPostorder() {
		System.out.println(this.getValues(POSTORDER));
	}
	
	public List<Integer> getValuesInorder() {
		return getValues(INORDER);
	}
	
	public List<Integer> getValuesPreorder() {
		return getValues(PREORDER);
	}
	
	public List<Integer> getValuesPostorder() {
		return getValues(POSTORDER);
	}
	
	private List<Integer> getValues(String traversalMode) {
		List<Integer> list = new ArrayList<>();
		this.treeToList(this.getRoot(), list, traversalMode);
		return list;
	}
	
	private void treeToList(Node node, List<Integer> list, String traversalMode) {
		if(node != null) {
			if(PREORDER.equals(traversalMode)) list.add(node.getValue());
			treeToList(node.getLeft(), list, traversalMode);
			if(INORDER.equals(traversalMode)) list.add(node.getValue());
			treeToList(node.getRight(), list, traversalMode);
			if(POSTORDER.equals(traversalMode)) list.add(node.getValue());
		}
	}
	
	private boolean exist(Integer value, Node node) {
		if(!this.isNullOrEmpty(node) && value!=null) {
			if(value.equals(node.getValue()))
					return true;
			else {
				if(value>node.getValue())
					return exist(value, node.getRight());
					else return exist(value, node.getLeft());
			}
		} else 
			return false;
	}
	
	private boolean isNullOrEmpty(Node node) {
		return node==null || node.getValue()==null;
	}

	private boolean isNullOrEmpty(Integer[] array) {
		return array==null || array.length == 0;
	}
	
	private void validateNull(Object value) {
		if(value==null) throw new RuntimeException("Null value is not allowed");
	}

}
