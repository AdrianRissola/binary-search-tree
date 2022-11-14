package com.slingr.binarysearchtree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BinarySearchTreeTest {
	
	private BinarySearchTree tree = null;

	@BeforeEach                                         
	void setUp() {
		Integer[] array = {4, 2, 1, 3, 6, 5 ,7};
		this.tree = new BinarySearchTree(array);
	}

	// tests constructor //
	@Test
	void testBinarySearchTree() {
		BinarySearchTree tree = new BinarySearchTree();
		tree.add(5);
		tree.add(2);
		tree.add(10);
		assertEquals(tree.getSize(), 3);
		tree.remove(5);
		assertEquals(tree.getSize(), 2);
	}
	
	@Test
	void testBinarySearchTree_From_Array() {
		Integer[] array = {98234, 98235, 3945, 239};
		BinarySearchTree tree = new BinarySearchTree(array);
		assertEquals(tree.getSize(), array.length);
	}
	
	@Test
	void testBinarySearchTree_From_Array_With_Some_Null_Values() {
		Integer[] array = {98234, null, 3945, null};
		BinarySearchTree tree = new BinarySearchTree(array);
		assertEquals(tree.getSize(), array.length-2);
	}
	
	@Test
	void testBinarySearchTree_From_Null_Array_throws_RuntimeException() {
		Integer[] array = null;
		RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
			new BinarySearchTree(array);
		}, "RuntimeException was expected");
		assertNotNull(thrown.getMessage());
	}
	
	@Test
	void testBinarySearchTree_From_Empty_Array_throws_RuntimeException() {
		Integer[] array = {};
		RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
			new BinarySearchTree(array);
		}, "RuntimeException was expected");
		assertNotNull(thrown.getMessage());
	}
	
	@Test
	void testBinarySearchTree_With_Null_Array_throws_RuntimeException() {
		Integer[] array = {null, null};
		RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
			new BinarySearchTree(array);
		}, "RuntimeException was expected");
		assertNotNull(thrown.getMessage());
	}
	
	// tests exist value //
	@Test
	void testExist_node_value_return_true() {
		assertTrue(this.tree.exists(6));
	}
	
	@Test
	void testExist_root_value_return_true() {
		assertTrue(this.tree.exists(4));
	}
	
	@Test
	void testExist_leaf_value_return_true() {
		assertTrue(this.tree.exists(7));
	}
	
	@Test
	void testExist_return_false() {
		assertFalse(this.tree.exists(999999));
	}
	
	@Test
	void testExist_with_null_return_false() {	
		assertFalse(this.tree.exists(null));
	}
	
	// tests add value //
	@Test
	void testAdd() {
		long size = this.tree.getSize();
		Integer value = this.tree.add(123);
		assertTrue(this.tree.exists(123));
		assertNotNull(value);
		assertEquals(value.intValue(), 123);
		assertEquals(size+1, this.tree.getSize());
	}
	
	@Test
	void testAdd_duplicate_return_null() {
		assertNull(this.tree.add(7));
	}
	
	@Test
	void testAdd_null_throws_RuntimeException() {
		RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
			this.tree.add(null);
		}, "RuntimeException was expected");
		assertNotNull(thrown.getMessage());
	}

	// tests remove value //
	@Test
	void testRemove_root_value_return_true() {
		long size = this.tree.getSize();
		boolean removed = this.tree.remove(4);
		assertTrue(removed);
		assertEquals(size-1, this.tree.getSize());
	}
	
	@Test
	void testRemove_tree_with_only_root_return_true() {
		
		BinarySearchTree bst = new BinarySearchTree();
		bst.add(5);
		long initialSize = bst.getSize();
		boolean removed = bst.remove(5);
		assertTrue(removed);
		assertEquals(initialSize-1, bst.getSize());
		
		Integer[] array = {10};
		BinarySearchTree bstFromArray = new BinarySearchTree(array);
		initialSize = bstFromArray.getSize();
		removed = bstFromArray.remove(10);
		assertTrue(removed);
		assertEquals(initialSize-1, bstFromArray.getSize());
	}
	
	@Test
	void testRemove_left_sun_return_true() {
		
		BinarySearchTree bst = new BinarySearchTree();
		bst.add(5);
		bst.add(1);
		long initialSize = bst.getSize();
		boolean removed = bst.remove(1);
		assertTrue(removed);
		assertEquals(initialSize-1, bst.getSize());
		
		Integer[] array = {10, 2};
		BinarySearchTree bstFromArray = new BinarySearchTree(array);
		initialSize = bstFromArray.getSize();
		removed = bstFromArray.remove(2);
		assertTrue(removed);
		assertEquals(initialSize-1, bstFromArray.getSize());
	}
	
	@Test
	void testRemove_right_sun_return_true() {
		
		BinarySearchTree bst = new BinarySearchTree();
		bst.add(5);
		bst.add(100);
		long initialSize = bst.getSize();
		boolean removed = bst.remove(100);
		assertTrue(removed);
		assertEquals(initialSize-1, bst.getSize());
		
		Integer[] array = {10, 200};
		BinarySearchTree bstFromArray = new BinarySearchTree(array);
		initialSize = bstFromArray.getSize();
		removed = bstFromArray.remove(200);
		assertTrue(removed);
		assertEquals(initialSize-1, bstFromArray.getSize());
	}
	
	@Test
	void testRemove_internal_value_return_true() {
		long size = this.tree.getSize();
		boolean removed = this.tree.remove(6);
		assertTrue(removed);
		assertEquals(size-1, this.tree.getSize());
	}
	
	@Test
	void testRemove_leaf_value_return_true() {
		long size = this.tree.getSize();
		boolean removed = this.tree.remove(5);
		assertTrue(removed);
		assertEquals(size-1, this.tree.getSize());
	}
	
	@Test
	void testRemove_non_existing_value_return_false() {
		long size = this.tree.getSize();
		boolean removed = this.tree.remove(999999);
		assertFalse(removed);
		assertEquals(size, this.tree.getSize());
	}
	
	@Test
	void testRemove_all_inorder() {
		this.tree.getValuesInorder().forEach( val -> {
			long size = this.tree.getSize();
			boolean removed = this.tree.remove(val);
			assertTrue(removed);
			assertEquals(size, this.tree.getSize()+1);
			if(this.tree.getSize()>0) {
				assertFalse(this.tree.getValuesInorder().isEmpty());
				assertFalse(this.tree.getValuesPostorder().isEmpty());
				assertFalse(this.tree.getValuesPreorder().isEmpty());
			}
		});
		assertTrue(this.tree.getSize()==0);
	}
	
	@Test
	void testRemove_all_inorder_reverse() {
		List<Integer> valuesInorder = this.tree.getValuesInorder();
		Collections.reverse(valuesInorder);
		valuesInorder.forEach( val -> {
			long size = this.tree.getSize();
			boolean removed = this.tree.remove(val);
			assertTrue(removed);
			assertEquals(size, this.tree.getSize()+1);
			if(this.tree.getSize()>0) {
				assertFalse(this.tree.getValuesInorder().isEmpty());
				assertFalse(this.tree.getValuesPostorder().isEmpty());
				assertFalse(this.tree.getValuesPreorder().isEmpty());
			}
		});
		assertTrue(this.tree.getSize()==0);
	}
	
	@Test
	void testRemove_all_preorder() {
		this.tree.getValuesPreorder().forEach( val -> {
			long size = this.tree.getSize();
			boolean removed = this.tree.remove(val);
			assertTrue(removed);
			assertEquals(size, this.tree.getSize()+1);
			if(this.tree.getSize()>0) {
				assertFalse(this.tree.getValuesInorder().isEmpty());
				assertFalse(this.tree.getValuesPostorder().isEmpty());
				assertFalse(this.tree.getValuesPreorder().isEmpty());
			}
		});
		assertTrue(this.tree.getSize()==0);
	}
	
	@Test
	void testRemove_all_preorder_reverse() {
		List<Integer> valuesPreorder = this.tree.getValuesPreorder();
		Collections.reverse(valuesPreorder);
		valuesPreorder.forEach( val -> {
			long size = this.tree.getSize();
			boolean removed = this.tree.remove(val);
			assertTrue(removed);
			assertEquals(size, this.tree.getSize()+1);
			if(this.tree.getSize()>0) {
				assertFalse(this.tree.getValuesInorder().isEmpty());
				assertFalse(this.tree.getValuesPostorder().isEmpty());
				assertFalse(this.tree.getValuesPreorder().isEmpty());
			}
		});
		assertTrue(this.tree.getSize()==0);
	}
	
	@Test
	void testRemove_all_postorder() {
		this.tree.getValuesPostorder().forEach( val -> {
			long size = this.tree.getSize();
			boolean removed = this.tree.remove(val);
			assertTrue(removed);
			assertEquals(size, this.tree.getSize()+1);
			if(this.tree.getSize()>0) {
				assertFalse(this.tree.getValuesInorder().isEmpty());
				assertFalse(this.tree.getValuesPostorder().isEmpty());
				assertFalse(this.tree.getValuesPreorder().isEmpty());
			}
		});
		assertTrue(this.tree.getSize()==0);
	}
	
	@Test
	void testRemove_all_postorder_reverse() {
		List<Integer> valuesPostorder = this.tree.getValuesPostorder();
		Collections.reverse(valuesPostorder);
		valuesPostorder.forEach( val -> {
			long size = this.tree.getSize();
			boolean removed = this.tree.remove(val);
			assertTrue(removed);
			assertEquals(size, this.tree.getSize()+1);
			if(this.tree.getSize()>0) {
				assertFalse(this.tree.getValuesInorder().isEmpty());
				assertFalse(this.tree.getValuesPostorder().isEmpty());
				assertFalse(this.tree.getValuesPreorder().isEmpty());
			}
		});
		assertTrue(this.tree.getSize()==0);
	}
	
	@Test
	void testRemove_stress_test() {
		
		for(int i=1 ; i<=100 ; i++) {
			BinarySearchTree bst = this.getRandomBigTree();
			assertTrue(bst.remove(500));
			assertTrue(bst.remove(250));
			assertTrue(bst.remove(750));
			assertTrue(bst.remove(1000));
			assertTrue(bst.remove(-1000));
			
			List<Integer> values = bst.getValuesInorder();
			assertNotNull(values);
			assertFalse(values.isEmpty());
			Integer val = null;
			for(Integer next: values) {
			    if (val != null) {
			        assertTrue(val<next);
			    }
			    val = next;
			}
		}

	}
	
	@Test
	void testRemove_all_stress_test() {
		BinarySearchTree bst = this.getRandomBigTree();
		for(int i=-1000 ; i<=1000 ; i++) {
			assertTrue(bst.remove(i));
			
			List<Integer> values = bst.getValuesInorder();
			if(!values.isEmpty()) {
				Integer val = null;
				for(Integer next: values) {
				    if (val != null) {
				        assertTrue(val<next);
				    }
				    val = next;
				}
			}
		}
		
		assertTrue(bst.getSize()==0);

	}
	
	// test Get Values Inorder //
	@Test
	void testGetValuesInorder() {
		List<Integer> values = this.tree.getValuesInorder();
		assertNotNull(values);
		assertFalse(values.isEmpty());
		Integer val = null;
		for(Integer next: values) {
		    if (val != null) {
		        assertTrue(val<next);
		    }
		    val = next;
		}
	}
	
	// getDepth tests
	@Test
	void testGetDepth_only_root() {
		BinarySearchTree bst = new BinarySearchTree();
		bst.add(2);
		assertEquals(bst.getDepth(), 0);
	}
	
	@Test
	void testGetDepth_six_nodes_depth_3() {
		Integer[] array = {12, 11, 90};
		BinarySearchTree bst = new BinarySearchTree(array);
		assertEquals(bst.getDepth(), 1);
		bst.add(82); bst.add(7); bst.add(9);
		assertEquals(bst.getDepth(), 3);
	}
	
	@Test
	void testGetDepth_five_nodes_depth_2() {
		BinarySearchTree bst = new BinarySearchTree();
		bst.add(26); bst.add(82); bst.add(16); 
		assertEquals(bst.getDepth(), 1);
		bst.add(92); bst.add(33);
		assertEquals(bst.getDepth(), 2);
	}
	
	@Test
	void testGetDepth_stress_test() {
		
		BinarySearchTree bst = getRandomBigTree();
		
		long startTime = System.nanoTime();
		long firsCall = bst.getDepth();
		long endTime = System.nanoTime();
		long durationFirstExec = endTime - startTime;
		
		startTime = System.nanoTime();
		long secondCall = bst.getDepth();
		endTime = System.nanoTime();
		long durationSecondExec = endTime - startTime;
		
		assertTrue(firsCall == secondCall);
		assertTrue(durationFirstExec>durationSecondExec);
		
	}
	
	// getDeepest tests
	@Test
	void testGetDeepest_only_root() {
		BinarySearchTree bst = new BinarySearchTree();
		bst.add(5);
		assertEquals(bst.getDeepest().size(), 1);
		assertEquals(bst.getDeepest().get(0).intValue(), 5);
	}
	
	@Test
	void testGetDeepest_six_nodes_depth_3() {
		BinarySearchTree bst = new BinarySearchTree();
		
		bst.add(12); bst.add(11); bst.add(90);
		assertEquals(bst.getDeepest().size(), 2);
		assertEquals(bst.getDeepest().get(0).intValue(), 11);
		assertEquals(bst.getDeepest().get(1).intValue(), 90);
		
		bst.add(82); bst.add(7); bst.add(9);
		assertEquals(bst.getDeepest().size(), 1);
		assertEquals(bst.getDeepest().get(0).intValue(), 9);
	}
	
	@Test
	void testGetDeepest_five_nodes_depth_2() {
		BinarySearchTree bst = new BinarySearchTree();
		
		bst.add(26); bst.add(82); bst.add(16);
		assertEquals(bst.getDeepest().size(), 2);
		assertEquals(bst.getDeepest().get(0).intValue(), 16);
		assertEquals(bst.getDeepest().get(1).intValue(), 82);
		
		bst.add(92); bst.add(33);
		assertEquals(bst.getDeepest().size(), 2);
		assertEquals(bst.getDeepest().get(0).intValue(), 33);
		assertEquals(bst.getDeepest().get(1).intValue(), 92);
	}
	
	
	@Test
	void testGetDeepest_stress_test() {
		
		BinarySearchTree bst = getRandomBigTree();
		
		long startTime = System.nanoTime();
		List<Integer> firsCall = bst.getDeepest();
		long endTime = System.nanoTime();
		long durationFirstExec = endTime - startTime;
		
		startTime = System.nanoTime();
		List<Integer> secondCall = bst.getDeepest();
		endTime = System.nanoTime();
		long durationSecondExec = endTime - startTime;
		
		assertTrue(firsCall.equals(secondCall));
		assertTrue(durationFirstExec>durationSecondExec);
		
	}
	
	private BinarySearchTree getRandomBigTree() {
		BinarySearchTree bst = new BinarySearchTree();
		Integer min = -1000;
		Integer max = 1000;
		long numberOfNodes = (int) Math.floor(Math.random()*1000001);
		for(int i=0 ; i<numberOfNodes ; i++) {
			Integer random_int = (int) Math.floor(Math.random()*(max-min+1)+min);
			bst.add(random_int);
		}
		bst.add(500);
		bst.add(250);
		bst.add(750);
		bst.add(1000);
		bst.add(-1000);
		return bst;
	}
	
	

}
