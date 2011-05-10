/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.uci.ics.crawler4j.example.simple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collections;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * @author Yasser Ganjisaffar <yganjisa at uci dot edu>
 */

public class MyCrawler extends WebCrawler {

	static Map<String, Set<String>> map = Collections.synchronizedMap(new HashMap<String, Set<String>>());
	
	Pattern filters = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g"
			+ "|png|tiff?|mid|mp2|mp3|mp4" + "|wav|avi|mov|mpeg|ram|m4v|pdf"
			+ "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	public MyCrawler() {
	}

	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		if (filters.matcher(href).matches()) {
			return false;
		}
//		if (href.startsWith("http://users.csc.calpoly.edu/") ||
//				href.startsWith("http://users.cpe.calpoly.edu/")) {
//			return true;
//		}
//		return false;
		if(!href.startsWith("http://users.csc.calpoly.edu/~gfisher"))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public void visit(Page page) {
		Pattern pattern;
		Matcher matcher;
		String instructorId = "NOT FOUND";
		
		int docid = page.getWebURL().getDocid();
        
		//pick instructor ids out of url
		String url = page.getWebURL().getURL();
		if(url.contains("calpoly"))
        {
			pattern = Pattern.compile("~[a-zA-Z]{0,8}"); // hopefully 0 times never occurs
	        matcher = pattern.matcher(url);
	        instructorId = matcher.find() ? matcher.group().substring(1) : instructorId;
	        if(!instructorId.equals("NOT FOUND")){
	        	Set<String> set = map.get(instructorId);
	        	if(set==null) {
	        		set = Collections.synchronizedSet(new TreeSet<String>());
	        		map.put(instructorId, set);
	        	}
	        }
        }
        System.out.println("Instructor Id: " + instructorId);
        
        // Get class names
        String strPageText = page.getText();        
        pattern = Pattern.compile("C[S|P][S|E][ |-]{0,3}[0-9]{3}");
        matcher = pattern.matcher(strPageText);
                
        System.out.println("Classes found:");
        while(matcher.find())
        {
        	String classname = matcher.group();
        	System.out.println(classname);
        	if(map.containsKey(instructorId))
        	{
        		map.get(instructorId).add(classname);
        	}
        }
        System.out.println();
        
        List<WebURL> links = page.getURLs();
		int parentDocid = page.getWebURL().getParentDocid();
		
		if(instructorId.equals("gfisher"))
		{
			System.out.println("*************************************************");
			System.out.println(page.getHTML());
			System.out.println("*************************************************");
			System.out.println(page.getText());
			System.out.println("*************************************************");
		}
		System.out.println("Docid: " + docid);
		System.out.println("URL: " + url);
		//System.out.println("Text length: " + text.length());
		System.out.println("Number of links: " + links.size());
		System.out.println("Docid of parent page: " + parentDocid);
		System.out.println("=============");
		
		System.out.println(map.toString());
	}	
}
