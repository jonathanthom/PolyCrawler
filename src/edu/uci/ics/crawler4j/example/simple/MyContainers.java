package edu.uci.ics.crawler4j.example.simple;

import java.util.Map;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashMap;

import com.sun.tools.javac.util.Pair;

public class MyContainers {

	private static MyContainers instance = null;
	// Getters.. Setters.. Forgetta 'bout it..
	// Contains userid as Key and Set of courses taught as value
	public static Map<String, Set<String>> idcoursemap = Collections.synchronizedMap(new HashMap<String, Set<String>>());
	
	// Contains Last Name, First Name as a Pair key and the string url for their ratings
	public static Map<Pair<String,String>, String> nameratemap = Collections.synchronizedMap(new HashMap<Pair<String,String>,String>());

	public static MyContainers getInstance() {
		if (instance == null) {
			instance = new MyContainers();
		}
		return instance;
	}
	
	/*
	 * FOR USE WITH IDCOURSEMAP
	 */
	public void AddInstructorId(String instructorId) {
    	Set<String> set = idcoursemap.get(instructorId);
    	if(set==null) {
    		set = Collections.synchronizedSet(new TreeSet<String>());
    		idcoursemap.put(instructorId, set);
    	}
	}
	
	public void AddCourse(String instructorId, String course) {
  		Set<String> set = idcoursemap.get(instructorId);
  		if(set!=null) {
  			set.add(course);
  		}
	}
	
	/* 
	 * FOR USE WITH NAMERATEMAP 
	 */
	public void AddName(Pair<String,String> namepair)	{
		String url = nameratemap.get(namepair);
		if(url.isEmpty()) {
			nameratemap.put(namepair, "");
		}
	}
	
	public void AddRateUrl(Pair<String,String> namepair, String url) {
		if(nameratemap.containsKey(namepair) && nameratemap.get(namepair).isEmpty()) {
			nameratemap.put(namepair, url);
		}
	}
}