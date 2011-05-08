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

package edu.uci.ics.crawler4j.example.imagecrawler;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * @author Yasser Ganjisaffar <yganjisa at uci dot edu>
 */

/*
* IMPORTANT: Make sure that you update crawler4j.properties file and 
*            set crawler.include_images to true           
*/

public class Controller {

	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			System.out.println("Needed parameters: ");
			System.out.println("\t rootFolder (it will contain intermediate crawl data)");
			System.out.println("\t numberOfCralwers (number of concurrent threads)");
			System.out.println("\t storageFolder (a folder for storing downloaded images)");
			return;
		}
		String rootFolder = args[0];
		int numberOfCrawlers = Integer.parseInt(args[1]);
		String storageFolder = args[2];

		String[] crawlDomains = new String[] { "http://uci.edu/" };

		CrawlController controller = new CrawlController(rootFolder);
		for (String domain : crawlDomains) {
			controller.addSeed(domain);
		}

		// Be polite:
		// Make sure that we don't send more than 5 requests per second (200
		// milliseconds between requests).0
		controller.setPolitenessDelay(200);

		// Do you need to set a proxy?
		// If so, you can uncomment the following line
		// controller.setProxy("proxyserver.example.com", 8080);
		// OR
		// controller.setProxy("proxyserver.example.com", 8080, username,
		// password);

		MyImageCrawler.configure(crawlDomains, storageFolder);

		controller.start(MyImageCrawler.class, numberOfCrawlers);
	}

}
