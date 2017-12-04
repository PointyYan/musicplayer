/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playlist;

import java.util.*;
import java.io.File;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
//import ui.MainView;


/**
 *
 * @author yjq97
 */
public class PlayList extends JList<Object>{
    
    // 保存所有音乐文件
	private ArrayList<File> allFile = new ArrayList<File>();
	// 保存名称
	private ArrayList<String> musicName = new ArrayList<String>();
        // 记录列表选择的位置 与上面两个list对应
	private int chooseIndex;
        
        public JList<String> musicList;
        
        

        
        public void initList(){
            
            musicName.clear();
            allFile.clear();
            File f = new File("Music");
            //如果这个文件夹不存在就创建文件夹
            if (!f.exists()) {
			f.mkdirs();
		} else {
			//得到文件夹里的所有文件
			File fa[] = f.listFiles();
			for (int i = 0; i < fa.length; i++) {
				File fs = fa[i];
				if (!fs.isDirectory()) {
					//得到文件名和文件类型后缀
					String name = fs.getName().substring(0, fs.getName().indexOf("."));
					String type = fs.getName().substring(fs.getName().indexOf(".") + 1);
					if (type.toLowerCase().equals("mp3") || type.toLowerCase().equals("wav")) {
						musicName.add(name);
						allFile.add(fs);
					}
				}
			}

		}
        }
        
    
}
