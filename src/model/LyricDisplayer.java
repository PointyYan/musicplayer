package model;

import java.awt.AlphaComposite;  
import java.awt.Color;  
import java.awt.Dimension;  
import java.awt.Font;  
import java.awt.FontMetrics;  
import java.awt.Graphics;  
import java.awt.Graphics2D;  
import java.awt.Image;  
import java.awt.RenderingHints;  
import java.io.File;  
import java.io.IOException;  
//import java.net.URISyntaxException;  
//import java.net.URL;  
import java.util.List;  
//import java.util.logging.Level;  
//import java.util.logging.Logger;  
import javax.imageio.ImageIO;  
import javax.swing.JPanel;  
//import musicbox.model.lyric.LyricStatement;  
  

public class LyricDisplayer extends JPanel {  
  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final Color CURRENT_LINE_COLOR = Color.green;  
    protected final Color OTHER_LINE_COLOR = Color.GRAY;  
    //the lines other than the current line to be displayed   
    protected final int UP_DOWN_LINES = 8;  
    //the list of lyric statements to be displayed   
    protected List<Lyric> statements;  
    //the index of next statement to be dispalyed in the statements   
    protected int index=-1;  
    protected Image backgroundImage = null;  
    private String backGroundImagePath = null;  
    protected Image bufferImage = null;  
    //the size when the bufferImage is drawn   
    private Dimension bufferedSize;  
  
    public String getBackGroundImagePath() {  
    return backGroundImagePath;  
    }  
  
    public void setBackGroundImagePath(String backGroundImagePath) {  
    this.backGroundImagePath = backGroundImagePath;  
    }  
  
    /** 
     * get ready to display 
     * @param statements  
     */  
    public void prepareDisplay(List<Lyric> statements) {  
    	this.statements = statements;  
    	this.index = -1;  
    	this.setFont(new Font("微软雅黑", Font.PLAIN, 20));  
    }  
    public int getindex(long notmileseconds){
    	int parsedindex=0;
    	if (this.statements==null)
    		return -1;
    	for (Lyric a : statements){
    		if(notmileseconds<a.getTime()){
    			break;
    		}
    		parsedindex++;
    	}
    	return (parsedindex==0)?0:parsedindex-1;
    }
    /** 
     * display a lyric by the index 
     * @param index  
     */  
    public int displayLyric(long notmileseconds) {  
    	//if(this.statements==null||this.statements.size()==0)
    		//return -1;
    	int temp = this.getindex(notmileseconds);
    	//System.out.println(temp);
    	//System.out.println(this.statements.size());
    	if ((temp==this.index&&this.index!=-1)||(this.index==0&&temp==-1))
    		return 1;
    	this.index=temp;
    	this.drawBufferImage();  
//  	System.out.println("draw " + index + " " + this.statements.get(index).getLyric());   
    	this.paint(this.getGraphics());
    	return 0;
    }  
  
    /** 
     * draw a line of lyric in the middle of the Graphics2D 
     * @param lyric 
     * @param g2d  
     */  
    protected void drawLineInMiddle(int height, String lyric, Graphics2D g2d, Color color) {  
    int width = this.getWidth();  
    FontMetrics fm = g2d.getFontMetrics();  
    g2d.setColor(color);  
    int x = (width - fm.stringWidth(lyric)) / 2;  
    g2d.drawString(lyric, x, height);  
    }  
  
    /** 
     * Draw the buffered image. Used to realize the double-buffering. 
     */  
    protected void drawBufferImage() {  
    Image tempBufferedImage = this.createImage(this.getWidth(), this.getHeight());  
    this.bufferedSize = this.getSize();  
    if (this.backgroundImage == null) {  
        //get background image   
        //URL url = getClass().getResource(this.backGroundImagePath);  
        File a=new File(this.backGroundImagePath);
        try {  
        backgroundImage = ImageIO.read(a);  
        //缩放图片   
        backgroundImage = backgroundImage.getScaledInstance(this.getWidth(), this.getHeight(), 20);  
        } catch (IOException ex) {  
        ex.printStackTrace();  
        }  
  
  
    }  
    Graphics2D g2d = (Graphics2D) tempBufferedImage.getGraphics();  
    g2d.setFont(new Font("楷体", Font.PLAIN, 25));  
    g2d.drawImage(this.backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);  
  
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  
        RenderingHints.VALUE_ANTIALIAS_ON);  
    if(this.index==-1)
		this.index=0;
    if (this.statements != null && this.statements.size() != 0) {  
        //draw current line  
        g2d.setFont(new Font("楷体", Font.PLAIN, 35));  
        this.drawLineInMiddle(this.getHeight() / 2,  
            this.statements.get(index).getLyric(), g2d, this.CURRENT_LINE_COLOR);  
        int perHeight = g2d.getFontMetrics().getHeight() + 5;  
        g2d.setFont(new Font("楷体", Font.PLAIN, 25));  
        //draw down lines   
        for (int i = index - UP_DOWN_LINES; i < index; i++) {  
        if (i < 0) {  
            continue;  
        }  
        if (index - i > UP_DOWN_LINES / 2) {  
            //set transparance   
            float ratio = (float) (i - index + UP_DOWN_LINES) / (UP_DOWN_LINES / 2) / 1.2f;  
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,  
                ratio));  
        } else {  
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,  
                1.0f));  
        }  
        this.drawLineInMiddle(this.getHeight() / 2 - (index - i) * perHeight,  
            this.statements.get(i).getLyric(), g2d, this.OTHER_LINE_COLOR);  
        }  
        //draw up lines   
        for (int i = index + 1; i < index + UP_DOWN_LINES; i++) {  
        if (i >= this.statements.size()) {  
            break;  
        }  
        if (i - index > UP_DOWN_LINES / 2) {  
            //set transparance   
            float ratio = (float) (index + UP_DOWN_LINES - i) / (UP_DOWN_LINES / 2) / 1.2f;  
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,  
                ratio));  
        } else {  
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,  
                1.0f));  
        }  
        this.drawLineInMiddle(this.getHeight() / 2 + (i - index) * perHeight,  
            this.statements.get(i).getLyric(), g2d, this.OTHER_LINE_COLOR);  
        }  
    } else {  
        //statements is empty   
        this.drawLineInMiddle(this.getHeight() / 2,  
            "未找到相应的歌词文件", g2d, this.CURRENT_LINE_COLOR);  
    }  
  
    //copyt the buffered image   
    this.bufferImage = tempBufferedImage;  
    }  
  
    /** 
     * This method is override in order to display the lyric in the panel 
     * @param g  
     */  
    @Override  
    public void paint(Graphics g) {  
    if (this.isVisible() == false) {  
        return;  
    }  
    super.paint(g);  
  
    //draw buffered image    
    if (this.bufferImage == null || this.getWidth() != this.bufferedSize.getWidth()  
        || this.getHeight() != this.bufferedSize.getHeight()) {  
        this.drawBufferImage();  
    }  
    //copy the double buffer   
    g.drawImage(bufferImage, 0, 0, null);  
  
    }  
}