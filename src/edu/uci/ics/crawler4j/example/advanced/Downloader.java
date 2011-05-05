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

package edu.uci.ics.crawler4j.example.advanced;

import edu.uci.ics.crawler4j.crawler.HTMLParser;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.PageFetchStatus;
import edu.uci.ics.crawler4j.crawler.PageFetcher;
import edu.uci.ics.crawler4j.url.WebURL;

public class Downloader {

	// This class is not currently used and is only a demonstration of how 
	// the crawler4j infrastructure can be used to download a single page
	// and extract its title and text
	
	private HTMLParser htmlParser = new HTMLParser();

	public Page download(String url) {
		WebURL curURL = new WebURL();
		curURL.setURL(url);
		Page page = new Page(curURL);
		int statusCode = PageFetcher.fetch(page, true);
		if (statusCode == PageFetchStatus.OK) {
			try {
				htmlParser.parse(page.getHTML(), curURL.getURL());
				page.setText(htmlParser.getText());
				page.setTitle(htmlParser.getTitle());
				return page;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		Downloader myDownloader = new Downloader();
		Page page = myDownloader.download("http://ics.uci.edu");
		if (page != null) {
			System.out.println(page.getText());
		}
	}
}
