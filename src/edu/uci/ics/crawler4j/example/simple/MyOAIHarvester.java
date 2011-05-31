package edu.uci.ics.crawler4j.example.simple;

import javax.xml.*;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Element;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;

import se.kb.oai.OAIException;
import se.kb.oai.pmh.OaiPmhServer;
import se.kb.oai.pmh.Record;
import se.kb.oai.pmh.RecordsList;
import com.sun.tools.javac.util.Pair;

public class MyOAIHarvester {

	MyContainers containers = MyContainers.getInstance();
	OaiPmhServer oaiSrv;
	
	public MyOAIHarvester() {
		
	}

	public void init(String xmlURI) throws Exception
	{
		oaiSrv = new OaiPmhServer(xmlURI);
	}
	
	public void runListRecords(String from, String until, String set) throws OAIException
	{
		RecordsList oaiRecords = oaiSrv.listRecords("qualified-dublin-core", from, until, set);
		List<Record> oaiRL = oaiRecords.asList();
		for( Record r : oaiRL )
		{
			Iterator<Element> creatIter = r.getMetadata().elementIterator("creator");
			while(creatIter.hasNext())
			{
				String[] name = creatIter.next().getText().split("[, | ]");
				Pair<String,String> nPair = new Pair<String,String>(name[0].toLowerCase(), name[2].toLowerCase());
				Element title = r.getMetadata().element("title");
				Element description = r.getMetadata().element("description.abstract");
				Element ident = r.getMetadata().element("identifier");
				if(title != null && ident != null && description != null && name.length != 0)
				{
					MyContainers.AddOAI(nPair, title, description, ident);
				}
			}
		}
	}	
}
