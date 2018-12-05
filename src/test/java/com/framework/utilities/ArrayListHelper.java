package com.framework.utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ArrayListHelper {

	/*
	 * Implements all optional list operations, permits all elements, including
	 * null. Created with an initial size. When this size is exceeded, collection is
	 * automatically enlarged. When objects are removed, the array may be shrunk.
	 */
	public final ArrayList<Integer> element = new ArrayList<>();

	public final Set<Integer> set = new HashSet<>();

	public static void main(final String[] args) {
		final Set<Integer> set = new HashSet<>();
		set.add(1);
		set.add(2);
		set.add(3);
		// Converting Set to arraylist
		final ArrayList<Integer> arrayList = new ArrayList<>(set);
		System.out.println(arrayList);

		// Creating replica of existing arraylist
		final ArrayList<Integer> arrayList2 = new ArrayList<>(arrayList);
		System.out.println(arrayList2);

		final Integer[] intArray = { 1, 2, 3, 4, 5 };
		// Converting Array to ArrayList
		ArrayList<Integer> array2 = new ArrayList<>(5);
		System.out.println(array2.size());

		final ArrayList<String> list = new ArrayList<>();
		list.add("Audi");
		list.add("BMW");
		System.out.println(list);

		// Add an extra element at index 0.
		// This will move all the existing following elements to make room.
		list.add(0, "Benz");
		System.out.println(list);

		// Let's create a duplicate ArrayList of above using addAll method
		final ArrayList<String> duplicate = new ArrayList<>();
		duplicate.addAll(list);
		System.out.println("Duplicate ArrayList: " + duplicate);

		System.out.println("ArrayList before Set operation: " + list);
		// Replace 0th element which is Audi with Honda
		list.set(0, "Honda");
		System.out.println("ArrayList After Set operation: " + list);

		// Get 2nd element which is BMW. We have to use index 1 as list index starts
		// from 0.
		System.out.println("2nd Element of the ArrayList: " + list.get(1));

		// print the current size of the ArrayList
		System.out.println("The size of the ArrayList: " + list.size());

		System.out.println("Is list contains Audi: " + list.contains("Audi"));

		// Removing Audi which is 2nd element. We have to use index 1 to for 2nd element
		// as list index
		// starts from 0;
		list.remove(1);
		System.out.println("ArrayList after removing Audi which is 2nd element: " + list);

		// print the current size of the ArrayList
		System.out.println("Printing index of BWM: " + list.indexOf("BMW"));

		// Converting ArrayList to an Array
		Object[] array = list.toArray();
		
	    printArrayList(list);
		// print the current size of the ArrayList
		System.out.println("The size of the ArrayList: " + list.size());
		// Reset the list using clear method
		list.clear();
		System.out.println("Is ArrayList empty: " + list.isEmpty());

	}

	public static void printArrayList(final ArrayList<String> arrayList) {
		for (final String item : arrayList) {
			System.out.println(item);
		}
	}
}
