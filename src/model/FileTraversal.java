package model;

import java.io.File;

public class FileTraversal {
	protected model.MyListModel mlm;
	public FileTraversal(model.MyListModel m){
		this.mlm=m;
	}
	public void Traversal(File f){
		File[] list=f.listFiles();
    	for (File file:list)
    		if (file.isDirectory())
    			this.Traversal(file);
    		else {
                String s = file.getPath().toLowerCase();
                if(s.endsWith(".mp3")||s.endsWith(".wav")){
                    mlm.addElement(new ListItem(file.getPath(),file.getName()));
                }
                
            }
	}
}
