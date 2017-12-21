/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Temp    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
lates
 * and open the template in the editor.
 */
package ui;

//import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
//import javax.media.ControllerEvent;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Controller.MyController;
import model.ListItem;
//import model.lrcreader;

/*import javax.sound.midi.*;
import javax.sound.sampled.*;
import javax.media.*;*/



/**
 *
 * @author yjq97
 */
public class MainView extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int index;
    String flag;
    String tt;
    //static int newtime = 0;
    //int ischanging = 0; //������Ƕ��α���е��������ֵҲ��ı�
    //int ispressing = 0; //�ж�����Ƿ���α���е��
    //int countSecond; //��ȡ���ֵ���ʱ��ֵ
    
    /**
     * Creates new form MainView
     */
    public MainView() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {

        //restart = new javax.swing.JButton();
        pre = new javax.swing.JButton();
        progress = new javax.swing.JSlider();
        //information = new javax.swing.JLabel();
        pricture = new javax.swing.JLabel();
        start = new javax.swing.JButton();
        stop = new javax.swing.JButton();
        next = new javax.swing.JButton();
        volumeOOO = new javax.swing.JToggleButton();
        jsVolume = new javax.swing.JSlider();
        jlLeft = new javax.swing.JLabel();
        jlRight = new javax.swing.JLabel();
        jBList = new javax.swing.JButton();
        jpList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        
        jList = new javax.swing.JList<model.ListItem>(mlm);
        get = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        jPanel1 = new model.LyricDisplayer();
        
        contr= new MyController(jList,jPanel1,mlm,progress,jlLeft,jlRight);
        contr.addMediaEndListener(new ControllerListener(){
        	@Override
			public void controllerUpdate(ControllerEvent ce) {
        		//System.out.println("test end event: " + contr.getstate());
        		if (ce instanceof EndOfMediaEvent){
        			//System.out.println("test end event: " + contr.getstate());
        			updateframe();
					//autonext();
					//System.out.println("test end event: " + state);
				}
        	}
        });
        //test help
        //mlm.addElement(new ListItem("d:\\abc.mp3","abc.mp3"));
        //SwingUtilities.updateComponentTreeUI(jList);
        //self add test end
        
        jPanel1.setBackGroundImagePath(Resdir+"background1.jpg");
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        pre.setActionCommand("pre");
        pre.addActionListener(this);
        pre.setBorderPainted(false);
        pre.setIcon(new ImageIcon(this.Resdir+"pre.png"));
        
        //jBList.setText("˳��");
        jBList.setActionCommand("loopmode");
        jBList.setIcon(new ImageIcon(Resdir+"none-list.png"));
        jBList.addActionListener(this);

        progress.setValue(0);
        progress.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	
                //progressMousePressed(evt);
                //System.out.println("test pro mouse1");
            }
        });
        progress.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent arg0){
                //ispressing = 1;
                //System.out.println("test pro mouse2");
            }
            public void mouseReleased(MouseEvent arg0){
                //ispressing = 0;
                //System.out.println("test pro mouse3");
            	int po=arg0.getX();
            	int poa=progress.getWidth();
                contr.progress(progress.getMaximum()*po/poa);
                updateframe();
            }
        });
        
        //ToolKit kit = Toolkit.getDefaultToolkit();
        
        this.setLocation(200, 200);
        
        this.addWindowListener(new WindowAdapter() {
            
            public void windowClosing(WindowEvent e) {
                //super.windowClosing(e);
            }
            public void windowClosed(WindowEvent e) {
                //super.windowClosed(e);
            	contr.exit();
            }
        });


        //information.setBackground(new java.awt.Color(0, 255, 255));
        //information.setText(" ");
        //information.setOpaque(true);

        pricture.setBackground(new java.awt.Color(0, 153, 204));
        //pricture.setText("jLabel1");
        pricture.setOpaque(true);

        //start.setText("��ʼ");
        start.setActionCommand("start");
        start.addActionListener(this);
        start.setBorderPainted(false);
        start.setIcon(new ImageIcon(this.Resdir+"play.png"));

        //stop.setText("ֹͣ");
        stop.setActionCommand("stop");
        stop.addActionListener(this);
        stop.setBorderPainted(false);
        stop.setIcon(new ImageIcon(this.Resdir+"stop.png"));
        
        
        //next.setText("��һ��");
        next.setActionCommand("next");
        next.addActionListener(this);
        next.setBorderPainted(false);
        next.setIcon(new ImageIcon(this.Resdir+"next.png"));

        volumeOOO.setText("V");
        volumeOOO.setActionCommand("btnvoluem");
        volumeOOO.setBorderPainted(false);

        jsVolume.setValue(40);
        jsVolume.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent event){
                int vol=jsVolume.getValue();
                //System.out.println(vol);
                contr.volchange(vol);
            }
        });

        jlLeft.setBackground(new java.awt.Color(204, 255, 255));
        jlLeft.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlLeft.setText("00:00");
        jlLeft.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jlLeft.setOpaque(true);

        jlRight.setBackground(new java.awt.Color(204, 0, 102));
        jlRight.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlRight.setText("00:00");
        jlRight.setOpaque(true);

        jList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                jList.getSelectedIndex();
                delete.setEnabled(true);
            }
        });
        jList.addMouseListener(new MouseAdapter(){
        	public void mouseClicked(MouseEvent e){
        		if(e.getClickCount()==2){
        			contr.doubleclick();
        			updateframe();
        		}
        	}
        });
        jScrollPane1.setViewportView(jList);

        get.setText("���Ӹ���");
        get.addActionListener((ActionEvent e) -> {
            Object obj = e.getSource();
            if(obj==get){
                JFileChooser chooser = new JFileChooser();
                myFileFilter filter= new myFileFilter();
                //����ѡ���� ֻ��ѡ����������(������mp3 �� wav)
                chooser.setMultiSelectionEnabled(true);
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                chooser.showDialog(new JLabel(),"ѡ��");
                chooser.addChoosableFileFilter(filter);
                File file = chooser.getSelectedFile();
                if (file!=null&&file.isDirectory()) {
                	model.FileTraversal FT=new model.FileTraversal(mlm);
                	FT.Traversal(file);
                } else if (file!=null&&file.isFile()) {
                	mlm.addElement(new ListItem(file.getAbsolutePath(),file.getName()));
                	
                }
                SwingUtilities.updateComponentTreeUI(jList);
            }
        });
        

        delete.setText("ɾ������");
        delete.addActionListener((ActionEvent e) -> {
            Object obj = e.getSource();
            //������ɾ����ť
            if (obj == delete) {
                //����ѡ����±�õ��ļ�
                mlm.remove(index);
                SwingUtilities.updateComponentTreeUI(jList);
            }
        });

        javax.swing.GroupLayout jpListLayout = new javax.swing.GroupLayout(jpList);
        jpList.setLayout(jpListLayout);
        jpListLayout.setHorizontalGroup(
            jpListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpListLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpListLayout.createSequentialGroup()
                        .addComponent(get)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(delete))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpListLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jpListLayout.setVerticalGroup(
            jpListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpListLayout.createSequentialGroup()
                .addGroup(jpListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(get)
                    .addComponent(delete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 574, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        //jBList.setText("˳�򲥷�");
        jBList.setBorderPainted(false);
        jBList.setBorderPainted(false);
        //jBList.setIcon(new ImageIcon(Resdir+"none-list.png"));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jlRight, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(pricture, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(90, 90, 90)
                            .addComponent(pre, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(stop, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(start, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(next, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(91, 91, 91)
                            .addComponent(jBList, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(12, 12, 12)
                            .addComponent(volumeOOO, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jsVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jpList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlRight, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pricture, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(pre, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(start, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(next, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(stop, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jBList, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(volumeOOO, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jsVolume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );


        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            //java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            //java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            //java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            //java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainView().setVisible(true);
            }
        });
//        lrcreader lr=new lrcreader("d:\\1258.lrc");
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String cmd = e.getActionCommand();
        System.out.println(cmd);
        if(cmd=="start"){
        	{contr.startclick();this.updateframe();}
        }
        if(cmd=="stop"){
        	contr.stopclick();
        	updateframe();
        }
          if(cmd=="pre"){
        	  //System.out.println("testpre");
        	  contr.preclick();
        	  updateframe();}
          if (cmd=="next") {
        	  contr.nextclick();
        	  updateframe();
          }
          if (cmd=="loopmode")
        	  {contr.setloopmode();this.updateframe();}
    }
    
    /**
     * �����ļ�ѡ����ѡ�������
     */
    class myFileFilter extends javax.swing.filechooser.FileFilter{
    	

		public String getDescription() {
			return "*.mp3;*.wav";
		}

		public boolean accept(File file) {
			String name = file.getName();
			return  file.isDirectory()||name.toLowerCase().endsWith(".mp3") || name.toLowerCase().endsWith(".wav");
			
		}
	}
    
    @SuppressWarnings("serial")
	class MusicFileChooser extends JFileChooser {
    }
    private void updateframe(){
    	int state=this.contr.getstate();
    	if(state>0)
    		this.jPanel1.displayLyric(0);
    	ImageIcon ICs;
    	String loopmode=this.contr.getloopmode();
    	ImageIcon ICl;
    	switch (state){
    		case 0:
    		case 1:
    			ICs=new ImageIcon(this.Resdir+"play.png");
    			break;
    		case 2:
    			ICs=new ImageIcon(this.Resdir+"pause.png");
    			break;
    		default:
    			ICs=new ImageIcon(this.Resdir+"play.png");
    	}
    	switch(loopmode){
    	case "none":
    		ICl=new ImageIcon(this.Resdir+"none.png");
    		break;
    	case "single":
    		ICl=new ImageIcon(this.Resdir+"single.png");
    		break;
    	case "none-list":
    		ICl=new ImageIcon(this.Resdir+"none-list.png");
    		break;
    	case "list":
    		ICl=new ImageIcon(this.Resdir+"list.png");
    		break;
    	case "random":
    		ICl=new ImageIcon(this.Resdir+"random.png");
    		break;
    	default:
    		ICl=new ImageIcon(this.Resdir+"none-list.png");
    		break;
    	}
    	this.start.setIcon(ICs);
    	this.jBList.setIcon(ICl);
    	if (this.contr.getstate()>0)
    		this.jPanel1.displayLyric(this.contr.gettime());
    	if (this.contr.getstate()==0)
    		this.progress.setValue(0);
    	//System.out.println("loopmode :"+this.contr.getloopmode()+ " state:" +this.contr.getstate() +" index:"+this.contr.getindex());
    	//SwingUtilities.updateComponentTreeUI(this.jList);
    	int a=contr.getindex();
    	if(a!=-1)
    		this.pricture.setText(this.mlm.getElementAt(a).getName());;
    }
    

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton delete;
    private javax.swing.JButton get;
    //private javax.swing.JLabel information;
    private JList<ListItem> jList;
    //private javax.swing.JPanel jPanel1;
    private model.LyricDisplayer jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jlLeft;
    private javax.swing.JLabel jlRight;
    private javax.swing.JPanel jpList;
    private javax.swing.JSlider jsVolume;
    private javax.swing.JButton next;
    private javax.swing.JButton pre;
    private javax.swing.JLabel pricture;
    private javax.swing.JSlider progress;
    //private javax.swing.JButton restart;
    private javax.swing.JButton start;
    private javax.swing.JButton stop;
    private javax.swing.JToggleButton volumeOOO;
    private MyController contr =null;
    private javax.swing.JButton jBList;
    private String Resdir=System.getProperty("user.dir")+File.separator+"resources"+File.separator;
    private model.MyListModel mlm =new model.MyListModel();
    // End of variables declaration//GEN-END:variables
}
