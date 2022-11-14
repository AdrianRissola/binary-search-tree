package com.slingr.binarysearchtree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BinarySearchTree {
	
	private Node root;
	private Map<Long, List<Integer>> levelToValues;
	private static final String INORDER = "INORDER";
	private static final String PREORDER = "PREORDER";
	private static final String POSTORDER = "POSTORDER";
	private long depth = -1;
	private List<Integer> deepest;
	private boolean wasModified = false;
	private long size = 0;
	
	/**
     * Constructs an empty {@code BinarySearchTree}
     */
	public BinarySearchTree() {
		this.initialize();
	}
	
	  /**
     * Constructs a tree containing the elements of the specified
     * array
     * @param  array whose elements are to be placed into this tree
     */
	public BinarySearchTree(Integer[] array) {
		this.initialize();
		this.validate(array);
		this.create(array);
	}
	
	private void initialize() {
		this.levelToValues = new HashMap<>();
		this.deepest = new LinkedList<>();
	}
		
	private long findDepth(Node node, long level) {
		level++;
		if(node.isLeaf())
			this.collect(node, level);
		long leftDepth = 0;
		long rightDepth = 0;  
		if(node.hasLeft())  
			leftDepth = findDepth(node.getLeft(), level);  
		if(node.hasRight())  
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
	
	/**
     * @return the depth of the tree starting from 0, return -1 when the tree is empty
     */
	public long getDepth() {
		this.sync();
		return this.depth;
	}

	/**
     * @return the list of values from deepest nodes of the tree
     */
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
	
	/**
     * @return the number of elements in this tree
     */
	public long getSize() {
		return this.size;
	}
	
	private void create(Integer[] array) {
		for(Integer value : array) {
			if(value!=null) {
				this.add(value, this.root);
				this.size++;
			}
		}
		this.wasModified = true;
	}
	
	/**
	 * @param value {@code Integer} to be added to this tree
	 * @return value {@code Integer} the added element
	 */
	public Integer add(Integer value) {
		this.validateNull(value);
		if(this.exists(value)) return null;
		this.wasModified = true;
		this.size++;
		Node node = this.add(value, this.root);
		return node.getValue();
	}
	
	/**
	 * @param value {@code Integer} to be removed from the tree, if present
	 * @return {@code true} if this tree contained the specified element
	 */
	public boolean remove(Integer value) {
		Node node = null;
		if(value!=null)
			node = this.find(value, this.root, null, true);
		if(node!=null) {
			this.wasModified = true;
			this.size--;
		}
		return node!=null;
	}
	
//	public boolean remove(Integer value) {
//		this.wasModified = true;
//		boolean wasRemoved = this.values.remove(value);		
//		if(wasRemoved) {
//			// rebuilding tree from list
//			this.root = null;
//			this.create(this.values.toArray(new Integer[0]));
//		}
//		return wasRemoved;
//	}
	
	private Node add(Integer value, Node node) {
		if(this.root==null) {
			this.root = new Node(value);
			return this.root;
		}
		Node newNode = null;
		if(value.compareTo(node.getValue())>0) {
			if(!node.hasRight()) {
				newNode = new Node(value);
				node.setRight(newNode);
			} else {
				newNode = this.add(value, node.getRight());
			}
		} else {
			if(!node.hasLeft()) {
				newNode = new Node(value);
				node.setLeft(newNode);
			} else {
				newNode = this.add(value, node.getLeft());
			}
		}
		return newNode;
	}
	
	/**
	 * @param value {@code Integer} whose presence in this list is to be tested
	 * @return {@code true} is the given value is present in the tree, {@code false} otherwise
	 */
	public boolean exists(Integer value) {
		boolean exists = false;
		if(value!=null)
			exists = this.find(value, this.root, null, false) != null ? true : false;
		return exists;
	}
	
	private Node find(Integer value, Node node, Node itsFather, boolean isDeletion) {
		Node found = null;
		if(node!=null) {
			if(value.equals(node.getValue())) {
				if(isDeletion) this.deletion(node, itsFather);
				found = node;
			} else {
				if(value.compareTo(node.getValue())>0)
					found = find(value, node.getRight(), node, isDeletion);
				else found = find(value, node.getLeft(), node, isDeletion);
			}
		}  
		return found;
	}
	
	private void deletion(Node nodeToRemove, Node itsFather) {
		if(itsFather==null)
			this.removeRoot(nodeToRemove);
		else
			this.removeNonRootNode(nodeToRemove, itsFather);
	}

	private void removeNonRootNode(Node nodeToRemove, Node itsFather) {
		if(itsFather.hasRight(nodeToRemove)) {
			if(nodeToRemove.hasRight()) {
				itsFather.setRight(nodeToRemove.getRight());
				this.joinOrphanLeftSubTree(nodeToRemove.getLeft(), itsFather.getRight());
			} else {
				itsFather.setRight(nodeToRemove.getLeft());
			}
		}
		if(itsFather.hasLeft(nodeToRemove)) {
			if(nodeToRemove.hasLeft()) {
				itsFather.setLeft(nodeToRemove.getLeft());
				this.joinOrphanRightSubTree(nodeToRemove.getRight(), itsFather.getLeft());
			} else {
				itsFather.setLeft(nodeToRemove.getRight());
			}
		}
	}

	private void removeRoot(Node root) {
		if(root.isLeaf()) {
			this.root = null;
		} else {
			if(root.hasRight()) {
				this.root = root.getRight();
				this.joinOrphanLeftSubTree(root.getLeft(), this.root);
			} else 
				if(root.hasLeft()) {
					this.root = root.getLeft();
					this.joinOrphanRightSubTree(root.getRight(), this.root);
				}
		}
	}

	private void joinOrphanLeftSubTree(Node leftOrphan, Node father) {
		if(leftOrphan!=null) {
			while(father.hasLeft()) {
				father = father.getLeft();
			}
			father.setLeft(leftOrphan);
		}
	}
	
	private void joinOrphanRightSubTree(Node rightOrphan, Node father) {
		if(rightOrphan!=null) {
			while(father.hasRight()) {
				father = father.getRight();
			}
			father.setRight(rightOrphan);
		}
	}
	
	/**
	 * print the tree in order
	 */
	public void printInorder() {
		System.out.println(this.getValues(INORDER));
	}
	
	/**
	 * print the tree in preorder
	 */
	public void printPreorder() {
		System.out.println(this.getValues(PREORDER));
	}
	
	/**
	 * print the tree in postorder
	 */
	public void printPostorder() {
		System.out.println(this.getValues(POSTORDER));
	}
	
	/**
	 * @return {@code List<Integer>} with tree values in order
	 */
	public List<Integer> getValuesInorder() {
		return getValues(INORDER);
	}
	
	/**
	 * @return {@code List<Integer>} with tree values in preorder
	 */
	public List<Integer> getValuesPreorder() {
		return getValues(PREORDER);
	}
	
	/**
	 * @return {@code List<Integer>} with tree values in postorder
	 */
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

	private boolean isNullOrEmpty(Integer[] array) {
		return array==null || array.length == 0;
	}
	
	private void validateNull(Object value) {
		if(value==null) throw new RuntimeException("Null value is not allowed");
	}
	
	private void validate(Integer[] array) {
		if(this.isNullOrEmpty(array)) throw new RuntimeException("Null or empty array is not allowed");
		boolean allValuesAreNull = true;
		for(Integer value : array) {
			if(value!=null) allValuesAreNull = false;
		}
		if (allValuesAreNull) throw new RuntimeException("Array with only null values is not allowed");
	}

}
