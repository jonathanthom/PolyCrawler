package edu.uci.ics.crawler4j.example.simple;

public class OAITuple implements Comparable<OAITuple> {

	private String title = "None";
	private String desc = "None";
	private String uri = "None";
	
	public OAITuple(String title, String desc, String uri)
	{
		this.title = title;
		this.desc = desc;
		this.uri = uri;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public String getDesc()
	{
		return this.desc;
	}
	
	public String getURI()
	{
		return this.uri;
	}
	
	public String toString()
	{
		return "Title:"+this.title+"\nAbstract:"+this.desc+"\nURI:"+this.uri+"\n";
	}

	@Override
	public int compareTo(OAITuple o) {
		if(!(o instanceof OAITuple))
			throw new ClassCastException("An OAITuple was expected");
		return this.title.compareTo(o.title);
	}
}
