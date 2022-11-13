package com.slingr.binarysearchtree.main;


import com.slingr.binarysearchtree.BinarySearchTree;


public class Main {

	public static void main(String[] args) {
		
		System.out.println("creating empty tree");
		BinarySearchTree bst = new BinarySearchTree();
		System.out.println("adding: 12, 11, 90, 82, 7, 9");
		bst.add(12); bst.add(11); bst.add(90); 
		bst.add(82); bst.add(7); bst.add(9);
		print(bst);

		Integer[] array = {26, 82, 16, 92, 33};
		System.out.println("creating tree with array: {26, 82, 16, 92, 33}");
		BinarySearchTree tree = new BinarySearchTree(array);
		print(tree);

	}
	
	private static void print(BinarySearchTree tree) {
		System.out.print("Inorder: ");
		tree.printInorder();
		System.out.print("Preorder: ");
		tree.printPreorder();
		System.out.print("Postorder: ");
		tree.printPostorder();
		System.out.println();
	}
	


}
