package Controller;

//import javax.media.Time;
import javax.swing.JLabel;

public class DisplayThread extends Thread{
	protected model.LyricDisplayer LD=null;
	protected javax.swing.JSlider progress=null;
	protected model.lrcreader lr=null;
	protected javax.media.Player player=null;
	protected boolean runit=true;
	protected int status=0;
	protected long duration=0;
	protected long mediatime=0;
	protected String timestring;
	protected int progressnum;
	protected JLabel JlLeft;
	protected JLabel Jlright;
	
	public DisplayThread(model.LyricDisplayer LD,javax.swing.JSlider progress,JLabel Jlleft,JLabel Jlright){
		this.LD=LD;
		this.progress=progress;
		this.JlLeft=Jlleft;
		this.Jlright=Jlright;
	}
	public synchronized void setstatus(int status){
		//System.out.println("testDPT 3"+status);
		this.status=status;
	}
	public synchronized void setstatus(boolean flag){
		this.runit=flag;
	}
	public synchronized void newmp3init(String lrc,javax.media.Player player){
		this.player=player;
		try {
        	
			model.lrcreader lr=new model.lrcreader(lrc);
			LD.prepareDisplay(lr.getLyrics());
		} catch (Exception e1) {
			System.out.println("lrc file open error"+lrc);
			LD.prepareDisplay(null);
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		player.getState();
		this.duration = player.getDuration().getNanoseconds()/1000000; // ms
		//Time time=new Time(this.duration);
		progress.setMinimum(0);
		progress.setMaximum((int) (this.duration/1000));
		this.Jlright.setText(this.duration/1000/60+":"+(this.duration/1000)%60);
	}
	@SuppressWarnings("static-access")
	@Override
	public void run(){
		String tmpstr;
		int tmpint=-1;
		while (this.runit){
			if(this.status<2)
				try {
					//System.out.println("testDPT 1");
					this.sleep(150);
				} catch (InterruptedException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else{
				try {
					this.sleep(10);
				} catch (InterruptedException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
				}
				long mediatime=this.player.getMediaNanoseconds()/10000000; 	//10ms
				//System.out.println("testDPT 2"+mediatime);
				LD.displayLyric(mediatime);
				tmpstr=mediatime/100/60+":"+(mediatime/100)%60;
				tmpint = (int)(mediatime/100);
				if (this.timestring!=tmpstr)
					{this.JlLeft.setText(tmpstr);this.timestring=tmpstr;}
				if (this.progressnum!=tmpint)
					{this.progress.setValue(tmpint);this.progressnum=tmpint;}
			}
			
		}
		
	}

}
