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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collections;

import com.sun.tools.javac.util.Pair;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * @author Yasser Ganjisaffar <yganjisa at uci dot edu>
 */

public class MyWebCrawler extends WebCrawler {

	MyContainers containers = MyContainers.getInstance();
	private final int NUMTOKES = 4;
	
	public MyWebCrawler() {
	}

	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		if (href.startsWith("http://users.csc.calpoly.edu/") ||
				href.startsWith("http://users.cpe.calpoly.edu/") ||
				(href.startsWith("http://polyratings.com/list.phtml")  && MyContainers.nameratemap.isEmpty())) {
			return true;
		}
		return false;
	}
	
	public void visit(Page page) {
		if(page.getWebURL().getURL().startsWith("http://polyratings.com/list.phtml"))
		{
			String strHtml = page.getHTML();
			Pattern pattern = 
				Pattern.compile("http://polyratings.com/eval.phtml\\Q?\\Eprofid=[0-9]{0,5}\">[a-zA-Z]+,[ a-zA-Z]+"
						+"</a></td><td bgcolor=\"#[0-9A-Za-z]*\"[ ]*align=\"center\">&nbsp;[A-Z]*<");
			Matcher matcher = pattern.matcher(strHtml);
			System.out.println("Start Ratings scrape");
			while(matcher.find()) {
				String htmlchunk = matcher.group();
				String strSepped = htmlchunk.replaceAll("(,|\"|</a>|</td>|&nbsp;|<(.*?)>|<|>)", " ");
				Vector<String> tokens = new Vector<String>(Arrays.asList(strSepped.split("[ ]+")));
				for(int i = tokens.size(); i < NUMTOKES; i++)
				{
					tokens.add("UNKNOWN");
				}
				Pair<String,String> nPair = new Pair<String,String>(tokens.get(1),tokens.get(2));
				containers.AddRateUrl(nPair, tokens.get(0));
				containers.AddDept(nPair, tokens.get(3));
				//System.out.printf("Url:%-50s Name:%-15s, %-15sDept:%s" , tokens.get(0),
				//		tokens.get(1),tokens.get(2),tokens.get(3));
				//System.out.println();
			}
			System.out.println("End Ratings Scrape");
		}
		else
		{
			Pattern pattern;
			Matcher matcher;
			String instructorId = "NOT FOUND";
			
			int docid = page.getWebURL().getDocid();
	        
			//pick instructor ids out of url
			String url = page.getWebURL().getURL();
			pattern = Pattern.compile("users.[a-zA-z]{2,4}.calpoly.edu");
			matcher = pattern.matcher(url);
			if(matcher.find()) // ~ prefixed
	        {
				pattern = Pattern.compile("~[a-zA-Z]{0,8}"); // hopefully 0 times never occurs
		        matcher = pattern.matcher(url);
		        instructorId = matcher.find() ? matcher.group().substring(1) : instructorId;
		        if(!instructorId.equals("NOT FOUND")){
		        	 containers.AddInstructorId(instructorId);
		        }
	        }
	        
			System.out.println("Instructor page Id: " + instructorId);
	        
	        // Get class names
	        String strPageText = page.getText();        
	        //pattern = Pattern.compile("C[S|P][S|E][ |-]{0,3}[0-9]{3}");
	        pattern = Pattern.compile("[C|c][S|s|P|p][C|c|E|e][ |-]{0,3}[0-9]{3}");
	        matcher = pattern.matcher(strPageText);                
	        //System.out.println("Classes found:");
	        while(matcher.find())
	        {
	        	String classname = matcher.group();
	        	//System.out.print(" "+classname);
	        	containers.AddCourse(instructorId, classname.toLowerCase());
	        }
	        //System.out.println();
	        
	        List<WebURL> links = page.getURLs();
			int parentDocid = page.getWebURL().getParentDocid();
			/*
			System.out.println("Docid: " + docid);
			System.out.println("URL: " + url);
			System.out.println("Text length: " + strPageText.length());
			System.out.println("Number of links: " + links.size());
			System.out.println("Docid of parent page: " + parentDocid);
			System.out.println("====================================================");
			*/
		}
	}	
}
