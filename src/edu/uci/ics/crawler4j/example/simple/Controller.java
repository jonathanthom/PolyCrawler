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
import com.sun.tools.javac.util.Pair;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Database;

/**
 * @author Yasser Ganjisaffar <yganjisa at uci dot edu>
 */

public class Controller {

	public static void main(String[] args) throws Exception {
		if (args.length < 4) {
			System.out
					.println("Please specify 'root folder' and 'number of crawlers' and 'db login' and 'db password'.");
			return;
		}
		
		/*
		 * MySQL database login and password
		 */
		String dblogin = args[2];
		String dbpassword = args[3];
		//Begin Database here!!!
		Database db = new Database(dblogin, dbpassword);

		/*
		 * rootfolder is a folder where intermediate crawl data is stored.
		 */
		String rootFolder = args[0];

		/*
		 * numberOfCrawlers shows the number of concurrent threads that should
		 * be initiated for crawling.
		 */
		int numberOfCrawlers = Integer.parseInt(args[1]);

		/*
		 * Instantiate the controller for this crawl. Note that if you want your
		 * crawl to be resumable (meaning that you can resume the crawl from a
		 * previously interrupted/crashed crawl) you can either set
		 * crawler.enable_resume to true in crawler4j.properties file or you can
		 * use the second parameter to the CrawlController constructor.
		 * 
		 * Note: if you enable resuming feature and want to start a fresh crawl,
		 * you need to delete the contents of rootFolder manually.
		 */
		CrawlController controller = new CrawlController(rootFolder);

		/*
		 * For each crawl, you need to add some seed urls. These are the first
		 * URLs that are fetched and then the crawler starts following links
		 * which are found in these pages
		 */

		// Get from data base all 'seed' instructor names and concatonate
		// them to the user
		controller.addSeed("http://polyratings.com/list.phtml");
/*		controller.addSeed("http://users.csc.calpoly.edu/~bellardo/");
		controller.addSeed("http://users.csc.calpoly.edu/~ivakalis/");
		controller.addSeed("http://users.csc.calpoly.edu/~buckalew/");
		controller.addSeed("http://users.csc.calpoly.edu/~akeen/");
		controller.addSeed("http://users.csc.calpoly.edu/~clupo/");
		controller.addSeed("http://users.csc.calpoly.edu/~csturner/");
		controller.addSeed("http://users.csc.calpoly.edu/~jdalbey/");
		controller.addSeed("http://users.csc.calpoly.edu/~jworkman/");
		controller.addSeed("http://users.csc.calpoly.edu/~zwood/");
		controller.addSeed("http://users.csc.calpoly.edu/~gfisher/");*/
		controller.addSeed("http://users.csc.calpoly.edu/~fkurfess/");
		controller.addSeed("http://users.csc.calpoly.edu/~cmclark/");
		//controller.addSeed("http://users.csc.calpoly.edu/~djanzen/");
		//controller.addSeed("http://users.csc.calpoly.edu/~pnico/");

		/*
		 * Be polite: Make sure that we don't send more than 5 requests per
		 * second (200 milliseconds between requests).
		 */
		controller.setPolitenessDelay(100);

		/*
		 * Optional: You can set the maximum crawl depth here. The default value
		 * is -1 for unlimited depth
		 */
		controller.setMaximumCrawlDepth(5);

		/*
		 * Optional: You can set the maximum number of pages to crawl. The
		 * default value is -1 for unlimited depth
		 */
		controller.setMaximumPagesToFetch(50);

		/*
		 * Do you need to set a proxy? If so, you can use:
		 * controller.setProxy("proxyserver.example.com", 8080); OR
		 * controller.setProxy("proxyserver.example.com", 8080, username,
		 * password);
		 */

		/*
		 * Note: you can configure several other parameters by modifying
		 * crawler4j.properties file
		 */

		/*
		 * Start the crawl. This is a blocking operation, meaning that your code
		 * will reach the line after this only when crawling is finished.
		 */
		controller.start(MyWebCrawler.class, numberOfCrawlers);

		MyOAIHarvester oaiHarv = new MyOAIHarvester();
		oaiHarv.init("http://digitalcommons.calpoly.edu/cgi/oai2.cgi");
		oaiHarv.runListRecords("2010-08-11", "2011-05-31", "publication:csse_5Ffac");

		
		// Here instead of dump push into database
		// dump Instructor Container
		System.out.println("Id and Courses container");
		for(String s : MyContainers.idcoursemap.keySet())
		{
//			db.addInstructor("testing", 1, "testing", "testing", "testing", 1);
			System.out.println("Instructor ID:"+s);
			System.out.println(MyContainers.idcoursemap.get(s).toString());
		}
		// dump Ratings Container
		System.out.println("Ratings link and name container");
		for(Pair<String,String> p : MyContainers.nameratemap.keySet())
		{
			System.out.printf("Name:%s, %s RatingUrl:%s\n", p.fst, p.snd, MyContainers.nameratemap.get(p));
		}
		
		// dump Dept Container
		System.out.println("Name and dept container");
		for(Pair<String,String> p : MyContainers.namedepmap.keySet())
		{
			System.out.printf("Name:%s, %s Dept:%s\n", p.fst, p.snd, MyContainers.namedepmap.get(p));
		}
		
		// dump OAI Container
		System.out.println("Publications map");
		for(Pair<String,String> p : MyContainers.oaimap.keySet())
		{
			for(String s : MyContainers.oaimap.get(p).keySet())
			{
				OAITuple tmpTuple = MyContainers.oaimap.get(p).get(s);
				System.err.printf("Name: %s, %s\n", p.fst, p.snd);
				//System.out.printf(tmpTuple.toString());
				System.err.printf("Title:%s\n", tmpTuple.getTitle());
				System.err.printf("Abstract:%s\n", tmpTuple.getDesc());
				System.err.printf("URI:%s\n", tmpTuple.getURI());
				
			}
		}
	}
}