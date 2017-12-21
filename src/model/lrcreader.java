package model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.io.InputStreamReader; 
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Collections;
//import java.util.Comparator;
import java.util.List; 
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  
/** 
 * 
 * @author rainple 
 */
public class lrcreader {
	private List<String> reuse=new ArrayList<String>();			//Lyric reuse cache
	//private String[] IDtags=new String[10];			//Lyric ID tags cache
	public String artist = "";                                  //演唱者   
    public String album = "";                                   //专辑   
    public String lrcMaker = "";                                //歌词制作者
    public String title = "";
    public String offset = "";
	private String Lyric=null;						//Lyric cache
	BufferedReader br=null;							//File
	List<Lyric> Lyrics = new ArrayList<Lyric>(); //Lyrics
	
	public lrcreader(String path_name)throws Exception{
		String codeString=lrcreader.codeString(path_name);
		FileInputStream fis = new FileInputStream(path_name);
		this.br = new BufferedReader(new InputStreamReader(fis, codeString));
		this.ParseLyrics();
	}
	public List<Lyric> getLyrics() {  
	    return Lyrics;
	}
	public static String codeString(String fileName) throws Exception{
        BufferedInputStream bin = new BufferedInputStream(
        new FileInputStream(fileName));
        int p = (bin.read() << 8) + bin.read();
        String code = null;
        //其中的 0xefbb、0xfffe、0xfeff、0x5c75这些都是这个文件的前面两个字节的16进制数
        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            case 0x5c75:
                code = "ANSI|ASCII" ;
                break ;
            default:
                code = "GBK";
        }
         bin.close();
        return code;
    }
	private int ParseLyrics()throws IOException{
		Lyrics.clear();
		String strLine;
		int endindex=0;
		while (null != (strLine = br.readLine())) {
			if ("".equals(strLine.trim())) {  
		        continue;  
			}
			boolean istimer=false;
			Pattern patternall = Pattern.compile("(\\[.+?:.+?\\])");
			Pattern patterntimer = Pattern.compile("(\\[\\d+:\\d{2}\\.\\d{2,3}\\])");
			Matcher matcher = patternall.matcher(strLine);
			for(;matcher.find();){
				String t=matcher.group();
				Matcher timer = patterntimer.matcher(t);
				istimer=timer.find();
				//System.out.println(i);
				if (istimer){
					//System.out.println(t+i+timer.group());
					reuse.add(matcher.group());
					endindex=matcher.end();
				}
				else
				{
					if(t.substring(1,6).equalsIgnoreCase("offset"))
						this.offset=t.substring(8,t.length()-1);
					else if(t.substring(1,2).equalsIgnoreCase("al"))
						this.album=t.substring(4,t.length()-1);
					else if(t.substring(1,2).equalsIgnoreCase("ar"))
						this.artist=t.substring(4,t.length()-1);
					else if(t.substring(1,2).equalsIgnoreCase("ti"))
						this.title=t.substring(4,t.length()-1);
					else if(t.substring(1,2).equalsIgnoreCase("by"))
						this.lrcMaker=t.substring(4,t.length()-1);
				}
			}
			if(istimer){
				//System.out.println(endindex);
				this.Lyric=strLine.substring(endindex,strLine.length());
				while(!reuse.isEmpty()){
					Lyrics.add(new Lyric(reuse.get(0).substring(1,9),this.Lyric));
					reuse.remove(0);
				}
			}
		}
		Collections.sort(Lyrics);
		/*for(int k=0;k<Lyrics.size();k++){
			Lyrics.get(k).printLyric();			
		}*/
		return 0;
	}
}
