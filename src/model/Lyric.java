package model;

public class Lyric implements Comparable<Lyric>{
	private long time = 0;//时间, 单位为10ms  
    private String lyric = "";//歌词  
  
    /* 
     * 获取时间 
     */  
    public long getTime() {  
    return time;  
    }  
    /* 
     * 设置时间 
     * time: 被设置成的时间 
     */  
  
    public void setTime(int time) {  
    this.time = time;  
    }  
    /* 
     * 设置时间 
     * time: 被设置成的时间字符串, 格式为mm:ss.ms 
     */  
  
    public void setTime(String time) {
    	//System.out.println(time);
    String str[] = time.split(":|\\.");  
    this.time = Integer.parseInt(str[0]) * 6000 + Integer.parseInt(str[1]) * 100 +   
        Integer.parseInt(str[2]);  
    }  
    /* 
     * 获取歌词 
     */  
  
    public String getLyric() {  
    return lyric;  
    }  
    /* 
     * 设置歌词 
     */  
  
    public void setLyric(String lyric) {  
    this.lyric = lyric;  
    }  
    /* 
     * 打印歌词 
     */  
  
    public void printLyric() {  
    System.out.println(time + ": " + lyric);  
    }
    public int compareTo(Lyric l){
    	return ((this.time == l.getTime() )? 0 : ((this.time>l.getTime()) ? 1 : -1));
    }
    public Lyric(String time,String lyric){
    	this.setTime(time);
    	this.setLyric(lyric);
    }

}
