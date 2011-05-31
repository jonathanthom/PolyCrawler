/**
 * Licensedto the Apache Software Foundation (ASF) under one or more
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

public class MyRatingsCrawler extends WebCrawler {

	private MyContainers containers = MyContainers.getInstance();
	private final int NUMTOKES = 4;

	public MyRatingsCrawler() {
	}
	
	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		if (href.startsWith("http://polyratings.com/list.phtml")) {
			return true;
		}
		return false;
	}
	
	public void visit(Page page) {
		String strHtml = page.getHTML();
		Pattern pattern = 
			Pattern.compile("http://polyratings.com/eval.phtml\\Q?\\Eprofid=[0-9]{0,5}\">[a-zA-Z]+,[ a-zA-Z]+"
					+"</a></td><td bgcolor=\"#[0-9A-Za-z]*\"[ ]*align=\"center\">&nbsp;[A-Z]*<");
		Matcher matcher = pattern.matcher(strHtml);
		System.out.println("Start");
		while(matcher.find()) {
			String htmlchunk = matcher.group();
			String strSepped = htmlchunk.replaceAll("(,|\"|</a>|</td>|&nbsp;|<(.*?)>|<|>)", " ");
			Vector<String> tokens = new Vector<String>(Arrays.asList(strSepped.split("[ ]+")));
			for(int i = tokens.size(); i < NUMTOKES; i++)
			{
				tokens.add("UNKNOWN");
			}
			System.out.printf("Url:%-50s Name:%-15s, %-15sDept:%s" , tokens.get(0),
					tokens.get(1),tokens.get(2),tokens.get(3));
			System.out.println();
		}
		System.out.println("End");
	}	
}
