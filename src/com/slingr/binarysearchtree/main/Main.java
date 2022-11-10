package com.slingr.binarysearchtree.main;

import org.w3c.dom.Node;


public class Main {

	public static void main(String[] args) {

		Integer[] array = {1,31,3,2,314,23,123};
		
		System.out.println("creating tree with array: {1,31,3,2,314,23,123}");
		BinarySearchTree tree = new BinarySearchTree(array);
		printInOrder(tree);
		
		System.out.println("adding 4");
		tree.add(4);
		printInOrder(tree);
		
		System.out.println("adding 23");
		tree.add(23);
		printInOrder(tree);
		
		System.out.println("adding 0");
		tree.add(0);
		printInOrder(tree);
		
		System.out.println("adding 4000");
		tree.add(4000);
		printInOrder(tree);
		
		System.out.println("removing all occurrences of 4000: "  + tree.remove(4000));
		printInOrder(tree);
		
		System.out.println("removing all occurrences of 4: " + tree.remove(4));
		printInOrder(tree);
		
		System.out.println("removing all occurrences of 23: " + tree.remove(23));
		printInOrder(tree);
		
		System.out.println("removing all occurrences of 999: " + tree.remove(999));
		printInOrder(tree);
		
	}
	
	private static void printInOrder(BinarySearchTree tree) {
	}
	


}
