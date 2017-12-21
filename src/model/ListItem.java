package model;

public class ListItem implements Comparable<ListItem>{
	protected String path="";
	protected String lrc="";
	protected String name="";
	public ListItem(String Path,String name){
		this.path=Path;
		this.name=name;
		this.lrc=this.path.substring(0,(this.path.lastIndexOf("."))==-1?this.path.length():this.path.lastIndexOf(".")+1)+"lrc";
	}
	public String getPath(){
		return this.path;
	}
	public String getName(){
		return this.name;
	}
	public String getLrc(){
		return this.lrc;
	}
	public String toString(){
		return this.name;
	}
	@Override
	public int compareTo(ListItem o) {
		// TODO Auto-generated method stub
		return this.path.compareTo(o.getPath());
	}
}
