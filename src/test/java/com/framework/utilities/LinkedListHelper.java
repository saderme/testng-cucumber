package com.framework.utilities;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class LinkedListHelper {

	/*
	 * Can contain duplicate elements. Maintains insertion order. Is non
	 * synchronized. Manipulation is fast because no shifting needs to be occurred.
	 */

	public final LinkedList<Integer> elements = new LinkedList<>();

	public static void main(final String[] args) {
		final LinkedList<Integer> elements = new LinkedList<>();
		System.out.println(elements.size());
		
	    final Set<Integer> set = new HashSet<>();
	    set.add(1);
	    set.add(2);
	    set.add(3);
	    //Converting Set to linkedlist
	    final LinkedList<Integer> linkedList = new LinkedList<>(set);
	    System.out.println(linkedList);

	    //Creating replica of existing LinkedList
	    final LinkedList<Integer> linkedList2 = new LinkedList<>(linkedList);
	    System.out.println(linkedList2);

	    final Integer[] intArray = {1, 2, 3, 4, 5};
	    //Converting Array to LinkedList
	    final LinkedList<Integer> linkedList3 = new LinkedList<>();
	    System.out.println(linkedList3.size());

	    final LinkedList<String> list = new LinkedList<>();
	    list.add("Audi");
	    list.add("BMW");
	    System.out.println(list);
	    // Add an extra element at index 0.
	    // This will move all the existing following elements to make room.
	    list.add(0, "Benz");
	    System.out.println(list);
	    
	    final LinkedList<String> duplicate = new LinkedList<>();
	    duplicate.addAll(list);
	    System.out.println("Duplicate LinkedList: " + duplicate);  
	
	    System.out.println("LinkedList before Set operation: " + list);    
	    //Replace 0th element which is Audi with Honda
	    list.set(0, "Honda");
	    System.out.println("LinkedList After Set operation: " + list); 
	    
	    // Get 2nd element which is BMW. We have to use index 1 as list index starts from 0.
	    System.out.println("2nd Element of the LinkedList: " + list.get(1));
	    
	    // print the current size of the LinkedList
	    System.out.println("The size of the LinkedList: " + list.size());
	    
	    System.out.println("Is list contains Audi: " + list.contains("Audi"));  

	    // print the current size of the LinkedList
	    System.out.println("Printing index of BWM: " + list.indexOf("BMW"));
	    
	    // print the current size of the LinkedList
	    System.out.println("LinkedList before removing BMW which is 2nd element: " + list);
	    // Removing BMW which is 2nd element. We have to use index 1 to for 2nd element as list index
	    // starts from 0;
	    list.remove(1);
	    System.out.println("LinkedList after removing BMW which is 2nd element: " + list); 

	    // Converting LinkedList to an Array    
	    Object[] array = list.toArray();

	    printLinkedList(list);
	    
	    // print the current size of the LinkedList
	    System.out.println("The size of the LinkedList: " + list.size());
	    // Reset the list using clear method
	    list.clear();
	    System.out.println("Is LinkedList empty: " + list.isEmpty());  
	    
	}

	
	  public static void printLinkedList(final LinkedList<String> linkedList) {
		    for (final String item : linkedList) {
		      System.out.println(item);
		    }
		  }
}
