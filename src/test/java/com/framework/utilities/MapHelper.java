package com.framework.utilities;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapHelper {

/*	HashMap: uses a hash table as the underlying data structure. Allows null values and one null key. 
 * This class is roughly equivalent to Hashtable but it is not synchronized and permits nulls.
 * HashMap does not guarantee the order of its key-value elements. 
 * Therefore, consider to use a HashMap when order does not matter and nulls are acceptable. */	
	public Map<Integer, String> hashMap = new HashMap<>();
	
/*	LinkedHashMap: uses hash table and linked list, thus the order is predictable (insertion-order is the default order). 
 *  Allows nulls like HashMap. Consider using when you want a Map with its key-value pairs are sorted by their insertion order.	*/	
	public Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
	
/*  TreeMap: uses red-black tree as the underlying data structure. Sorted according to the natural ordering of its keys, or by a Comparator provided at creation time. 
 *  Does not allow nulls. Using TreeMap when you want to sort its key-value pairs by the natural order(e.g. alphabetic order or numeric order), 
 *  or by a custom order you specify.*/	
	public Map<Integer, String> treeMap = new TreeMap<>();

	
	public void putHashMap (int key, String value) {
		hashMap.put(key, value);
	}
	public void putLinkedHashMap (int key, String value) {
		linkedHashMap.put(key, value);
	}
	public void putTreeMap (int key, String value) {
		treeMap.put(key, value);
	}
	
	public String getHashMap (int key) {
		return hashMap.get(key);
	}
	public String getLinkedHashMap (int key) {
		return linkedHashMap.get(key);
	}
	public String getTreeMap (int key) {
		return treeMap.get(key);
	}
	
	public boolean containsKey (int key) {
		return treeMap.containsKey(key);
	}
	
	public boolean containsKValue (int value) {
		return treeMap.containsValue(value);
	}
	
	public String removeKey (int key) {
		return treeMap.remove(key);
	}
	
	public String replaceValue (int key, String value) {
		return treeMap.replace(key, value);	
	}
	
	public int getMapsize () {
		return treeMap.size();	
	}	
	
	public void clearMap () {
		treeMap.clear();	
	}	
}
