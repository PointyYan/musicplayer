package Controller;
//import java.net.MalformedURLException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
//import java.util.HashSet;
import java.util.Iterator;

import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.Player;
import javax.media.Time;
import javax.swing.JLabel;
import javax.swing.JList;

public class MyController {
	protected int state=0; //0:stopped 1:paused 2:playing
	protected String loopmode="none-list"; //none,single,none-list,list,random
	protected model.LyricDisplayer LD;
	protected int targetvol=40;
	protected int currentplay=-1;
	protected JList<model.ListItem> Jlist1;
	protected model.MyListModel mlm;
	protected DisplayThread DPT=null;
	private Collection<ControllerListener> listeners;
	//protected model.MyPlayer Player=new model.MyPlayer();
	private Player audioPlayer = null;//建立一个播放接口  
	public void addMediaEndListener(ControllerListener controllerListener) {
        if (listeners == null) 
        	listeners = new HashSet<ControllerListener>();
        listeners.add(controllerListener);
        //listener.
    }
	public void open(URL url) throws Exception{//创建一个准备Player,准备好播放  
    	if (this.audioPlayer!=null)
    		this.end();
    	audioPlayer = Manager.createRealizedPlayer(url);
    	audioPlayer.addControllerListener(new ControllerListener(){
			@Override
			public void controllerUpdate(ControllerEvent ce) {
				// TODO Auto-generated method stub
				if (ce instanceof EndOfMediaEvent){
					autonext();
					//System.out.println("test end event: " + state);
				}
				if (listeners!=null){
				Iterator<ControllerListener> iter = listeners.iterator();
		        while (iter.hasNext()) {
		            ControllerListener listener = (ControllerListener) iter.next();
		            listener.controllerUpdate(ce);
		        }}
			}
    	});
    	this.DPT.newmp3init(this.mlm.getElementAt(currentplay).getLrc(), audioPlayer);
    }
    public void open(File file) throws MalformedURLException, Exception{//将本地文件改为URL  
        this.open(file.toURI().toURL());  
    }
    public void open(String path) throws MalformedURLException, Exception{
    	this.open(new File(path));
    }
    public void play(){//直接调用播放方法就可以  
        audioPlayer.start();  
    }
    public long gettime(){
    	return this.audioPlayer.getMediaNanoseconds()/10000000;
    }
    public void setvol(int vol){
    	vol=vol==0?0:(int)((float)vol*5.0/10.0+50.0);
    	if(this.audioPlayer!=null)
    		this.audioPlayer.getGainControl().setDB((float) (86.0/100.0*vol-80.0));
    }
    public int getState(){ 
    	return    this.audioPlayer.getState();}
    public boolean setMediaTime(Time time){
    	this.audioPlayer.setMediaTime(time);
    	return true;
    }
    public Time getPosition(){
    	return this.audioPlayer.getMediaTime();
    }
    public Time getduration(){
    	return this.audioPlayer.getDuration();
    }
    
    public void stop(){//停止的时候一定要释放资源  
        audioPlayer.stop();
    } 
    public void end(){
    	if(this.audioPlayer!=null){
    	this.stop();
    	this.audioPlayer.close();}
    	this.audioPlayer=null;
    }
	public MyController(JList<model.ListItem> Jlist1,model.LyricDisplayer LD,model.MyListModel mlm,javax.swing.JSlider progress,JLabel Jlleft,JLabel Jlright){
		this.Jlist1=Jlist1;
		this.LD=LD;
		this.mlm=mlm;
		this.DPT=new DisplayThread(LD, progress, Jlleft, Jlright);
		DPT.start();
	}
	private int handle(int target){
		//System.out.println(this.state+""+target);
		if (this.state==0||this.state==target)
			return target;
		else if(target==0)
			{this.end();this.setstate(0);return target;}
		else if(this.state==1&&target==2)
			{this.play();this.setstate(2);this.setvol(this.targetvol);return target;}
		else if(this.state==2&&target==1)
			{this.stop();this.setstate(1);this.setvol(this.targetvol);return target;}
		return -1;
	}
	public void startclick(){
		if(this.state==0)
			this.doubleclick();
		else if(this.state==1)
			this.handle(2);
		else if(this.state==2)
			this.handle(1);
		//System.out.println(this.state);
	}
	public int getindex(){
		return this.currentplay;
	}
	public void stopclick(){
		this.handle(0);
	}
	private int getnextindex(int con){
		int size=mlm.getSize();
		if (size==0||this.currentplay==-1)
			return -1;
		if (size==1)
			return 0;
		if (size>1)
			switch(loopmode){
				case "none":
					//return -2;
				case "single":
					//return this.currentplay;
				case "none-list":
				case "list":
					return con>0?(this.currentplay>=size-1?0:this.currentplay+1):((this.currentplay>size-1||this.currentplay==0)?size-1:this.currentplay-1);
				case "random":
					return (int)(Math.random()*5000)%size;
				default:
					return this.currentplay;
			}
		return -1;
	}
	private int getnextindex(){
		return this.getnextindex(1);
	}
	private void autonext(){
		if (this.loopmode=="none")
			{this.setMediaTime(new Time(0));this.handle(0);return;}
		if (this.currentplay==this.mlm.getSize()-1&&this.loopmode=="none-list")
			{this.setMediaTime(new Time(0));this.currentplay=0;this.Jlist1.setSelectedIndex(0);this.handle(0);return;}
		if (this.loopmode=="single")
			{this.setMediaTime(new Time(0));this.doubleclick(this.currentplay);return;}
		this.nextclick();
	}
	public void preclick(){
		int a=this.getnextindex(-1);
		System.out.println("test"+a);
		System.out.println(this.doubleclick(a));
	}
	public void nextclick(){
		this.doubleclick(this.getnextindex());
	}
	public void exit(){
		this.handle(0);
		this.DPT.setstatus(false);
		//TODO
	}
	public void progress(int prog){
		//long tmp = 100000000000L;
		if(this.state>0)
			this.audioPlayer.setMediaTime(new Time((long)prog * 1000000000L));
	}
	public void volchange(int vol){
		this.targetvol=vol;
		if(this.state>0)
			this.setvol(vol);
	}
	public int doubleclick(int index){
		this.handle(0);
		int tmp=0;
		System.out.println("doubleclick "+index);
		if(index<0||index>=mlm.getSize())
			return -2;
		try {
			tmp=this.currentplay;
			this.currentplay=index;
			this.open(mlm.getElementAt(index).getPath());
			this.setstate(1);
			this.handle(2);
			this.Jlist1.setSelectedIndex(currentplay);}
		catch (Exception e) {
			this.currentplay=tmp;
			e.printStackTrace();
			return -1;
			// TODO Auto-generated catch block
			
		}
		return 0;
	}
	public int doubleclick(){
		int index=this.Jlist1.getSelectedIndex();
		if (index==-1&&mlm.getSize()==0)
			{currentplay=-1;return -1;}
		else if(index==-1)
			{return this.doubleclick(0);}
		else	
			{return this.doubleclick(index);}
		
	}
	public int getstate(){
		return this.state;
	}
	public String getloopmode(){
		return this.loopmode;
	}
	public boolean setloopmode(){
		switch (this.loopmode){
		case "none":
			this.setloopmode("single");
			return true;
		case "single":
			this.setloopmode("none-list");
			return true;
		case "none-list":
			this.setloopmode("list");
			return true;
		case "list":
			this.setloopmode("random");
			return true;
		case "random":
			this.setloopmode("none");
			return true;
		default:
			return false;
		}
	}
	public boolean setloopmode(String loopmode){
		
		boolean flag=true;
		switch (loopmode){
			case "none":
				this.loopmode="none";
				flag=true;
				break;
			case "single":
				this.loopmode="single";
				flag=true;
				break;
			case "none-list":
				this.loopmode="none-list";
				flag=true;
				break;
			case "list":
				this.loopmode="list";
				flag=true;
				break;
			case "random":
				this.loopmode="random";
				flag=true;
				break;
			default:
				flag=false;
				break;
		}
		return flag;
	}
	public boolean setstate(int target){
		if (target>=0&&target<=2)
			{this.state=target;System.out.println("Test Contr1 "+target);this.DPT.setstatus(target);return true;}
		return false;
	}
	public boolean setstate(String state){
		boolean flag=false;
		int tmp=0;
		switch (state){
			case "stopped":
				tmp=0;
				flag = true;
				break;
			case "paused":
				tmp=1;
				flag = true;
				break;
			case "playing":
				tmp=2;
				flag = true;
				break;
			default:
				flag = false;
				break;
		}
		if(flag)
			this.setstate(tmp);
		return flag;
	}
}
