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

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * @author Yasser Ganjisaffar <yganjisa at uci dot edu>
 */

public class Controller {

		public static void main(String[] args) throws Exception {
			if (args.length < 2) {
				System.out.println("Please specify 'root folder' and 'number of crawlers'.");
				return;
			}
			
			/*
			 * rootfolder is a folder where intermediate crawl data is
			 * stored. 
			 */
			String rootFolder = args[0];
			
			/*
			 * numberOfCrawlers shows the number of concurrent threads
			 * that should be initiated for crawling.
			 */
			int numberOfCrawlers = Integer.parseInt(args[1]);
			
			/*
			 * Instantiate the controller for this crawl. Note that if you want
			 * your crawl to be resumable (meaning that you can resume the crawl
			 * from a previously interrupted/crashed crawl) you can either set
			 * crawler.enable_resume to true in crawler4j.properties file or you
			 * can use the second parameter to the CrawlController constructor.
			 * 
			 * Note: if you enable resuming feature and want to start a fresh
			 * crawl, you need to delete the contents of rootFolder manually.
			 */
			CrawlController controller = new CrawlController(rootFolder);
			
			/*
			 * For each crawl, you need to add some seed urls.
			 * These are the first URLs that are fetched and
			 * then the crawler starts following links which
			 * are found in these pages
			 */
			controller.addSeed("http://www.ics.uci.edu/~yganjisa/");
			controller.addSeed("http://www.ics.uci.edu/~lopes/");
			controller.addSeed("http://www.ics.uci.edu/");
			
			/*
			 * Be polite:
			 * Make sure that we don't send more than 5 requests per 
			 * second (200 milliseconds between requests).
			 */
			controller.setPolitenessDelay(200);
			
			/*
			 * Optional:
			 * You can set the maximum crawl depth here.
			 * The default value is -1 for unlimited depth
			 */
			controller.setMaximumCrawlDepth(2);
			
			/*
			 * Optional:
			 * You can set the maximum number of pages to crawl.
			 * The default value is -1 for unlimited depth
			 */
			controller.setMaximumPagesToFetch(500);
			
			/*
			 * Do you need to set a proxy?
			 * If so, you can use: 
			 * controller.setProxy("proxyserver.example.com", 8080);
			 * OR
			 * controller.setProxy("proxyserver.example.com", 8080, username, password);
			 */
			
			/*
			 * Note: you can configure several other parameters by modifying 
			 * crawler4j.properties file
			 */
			
			/*
			 * Start the crawl. This is a blocking operation, meaning
			 * that your code will reach the line after this only when
			 * crawling is finished.
			 */
			controller.start(MyCrawler.class, numberOfCrawlers);
		}

}

