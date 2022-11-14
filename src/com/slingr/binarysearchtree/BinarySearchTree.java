package com.slingr.binarysearchtree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BinarySearchTree {
	
	private Node root;
	private List<Integer> values;
	private Map<Long, List<Integer>> levelToValues;
	private static final String INORDER = "INORDER";
	private static final String PREORDER = "PREORDER";
	private static final String POSTORDER = "POSTORDER";
	private long depth = -1;
	private List<Integer> deepest;
	private boolean wasModified = true;
	
	public BinarySearchTree() {
		this.initialize();
	}
	
	public BinarySearchTree(Integer[] array) {
		this.initialize();
		this.validate(array);
		this.create(array);
	}
	
	private void initialize() {
		this.levelToValues = new HashMap<>();
		this.deepest = new LinkedList<>();
		this.values = new LinkedList<>();
	}
		
	private long findDepth(Node node, long level) {
		level++;
		if(this.isLeaf(node))
			this.collect(node, level);
		long leftDepth = 0;
		long rightDepth = 0;  
		if(node.getLeft() != null)  
			leftDepth = findDepth(node.getLeft(), level);  
		if(node.getRight() != null)  
			rightDepth = findDepth(node.getRight(), level);
		long max = (leftDepth >= rightDepth) ? leftDepth : rightDepth;
		long depth = max + 1;
		return depth;
	}
	
	private void collect(Node node, Long level) {
		List<Integer> values = this.levelToValues.get(level);
		if(values==null)
			values = new ArrayList<>();
		values.add(node.getValue());
		this.levelToValues.put(level, values);
	}
	
	public long getDepth() {
		this.sync();
		return this.depth;
	}

	public List<Integer> getDeepest() {
		this.sync();
		return this.deepest;
	}
	
	private void sync() {
		if(this.wasModified) {
			this.update();
			this.wasModified = false;
		}
	}

	private void update() {
		this.depth = this.findDepth(this.root, -1)-1;
		this.deepest = this.levelToValues.get(this.depth);
	}

	private void validate(Integer[] array) {
		if(this.isNullOrEmpty(array)) throw new RuntimeException("Null or empty array is not allowed");
		boolean allValuesAreNull = true;
		for(Integer value : array) {
			if(value!=null) allValuesAreNull = false;
		}
		if (allValuesAreNull) throw new RuntimeException("Array with only null is not allowed");
	}
	
	public long getSize() {
		return this.values.size();
	}
	
	private void create(Integer[] array) {
		this.values = new LinkedList<>();
		for(Integer value : array) {
			if(value!=null) {
				this.values.add(value);
				this.add(value, this.root);
			}
		}
	}
	
	public Integer add(Integer value) {
		this.wasModified = true;
		this.validateNull(value);
		if(this.exist(value)) return null;
		this.values.add(value);
		Node node = this.add(value, this.root);
		return node.getValue();
	}

	public boolean remove(Integer value) {
		this.wasModified = true;
		boolean wasRemoved = this.values.remove(value);
		if(wasRemoved) {
			// rebuilding tree from list
			this.root = null;
			this.create(this.values.toArray(new Integer[0]));
		}
		return wasRemoved;
	}
	
	private Node add(Integer value, Node node) {
		if(this.root==null) {
			this.root = new Node(value);
			return this.root;
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
		return exist(value, this.root);
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
		this.treeToList(this.root, list, traversalMode);
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
	
	private boolean isLeaf(Node node) {
		return node.getRight()==null && node.getLeft()==null;
	}

	private boolean isNullOrEmpty(Integer[] array) {
		return array==null || array.length == 0;
	}
	
	private void validateNull(Object value) {
		if(value==null) throw new RuntimeException("Null value is not allowed");
	}

}
