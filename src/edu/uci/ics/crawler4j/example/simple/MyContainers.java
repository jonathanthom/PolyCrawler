package edu.uci.ics.crawler4j.example.simple;

import java.util.Map;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.Vector;

import org.dom4j.Element;

import com.sun.tools.javac.util.Pair;


public class MyContainers {

	private static MyContainers instance = null;
	// Getters.. Setters.. Forgetta 'bout it..
	// Contains userid as Key and Set of courses taught as value
	public static Map<String, Set<String>> idcoursemap = Collections.synchronizedMap(new HashMap<String, Set<String>>());
	
	// Contains Last Name, First Name as a Pair key and the string url for their ratings
	public static Map<Pair<String,String>, String> nameratemap = Collections.synchronizedMap(new HashMap<Pair<String,String>,String>());
	
	// Contains Last Name, First Name as a Pair key and the dept they work for as acronym
	public static Map<Pair<String,String>, String> namedepmap = Collections.synchronizedMap(new HashMap<Pair<String,String>,String>());
	
	// Contains Last Name, First Name as a Pair key and a vector of publications
	public static Map<Pair<String,String>, Map<String, OAITuple>> oaimap = Collections.synchronizedMap(new HashMap<Pair<String,String>, Map<String, OAITuple>>());
	//	public static Database database = null;
	
	public static MyContainers getInstance() {
		if (instance == null) {
			instance = new MyContainers();
		}
		return instance;
	}
	
	/*
	 * FOR USE WITH IDCOURSEMAP
	 */
	public static void AddInstructorId(String instructorId) {
    	Set<String> set = idcoursemap.get(instructorId);
    	if(set==null) {
    		set = Collections.synchronizedSet(new TreeSet<String>());
    		idcoursemap.put(instructorId, set);
    	}
	}
	
	public static void AddCourse(String instructorId, String course) {
  		Set<String> set = idcoursemap.get(instructorId);
  		if(set!=null) {
  			set.add(course);
  		}
	}
	
	public static void AddRateUrl(Pair<String,String> namepair, String url) {
		if(nameratemap.containsKey(namepair) && nameratemap.get(namepair).isEmpty()) {
			nameratemap.put(namepair, url);
		}
	}
	
	public static void AddDept(Pair<String, String> nPair, String dept)
	{
		namedepmap.put(nPair, dept);
	}
	
	public static void AddOAI(Pair<String, String> nPair, Element title,
			Element description, Element ident) {
		if(!oaimap.containsKey(nPair))
		{
			oaimap.put(nPair, new HashMap<String, OAITuple>());
		}
		
		if(!oaimap.get(nPair).containsKey(title.getText()))
		{
			oaimap.get(nPair).put(title.getText(), new OAITuple(title.getText(),description.getText(),
					ident.getText()));
		}
	}
}