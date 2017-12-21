package model;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

public class MyListModel extends AbstractListModel<ListItem>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<ListItem> all = new ArrayList<ListItem>();
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return all.size();
	}
	@Override
	public ListItem getElementAt(int index) {
		// TODO Auto-generated method stub
		return all.get(index);
	}
	public void add(int index, ListItem element){}//在指定位置插入项目

	public void addElement(ListItem element){
		all.add(element);
	}//在最后面插入项目

	public boolean removeElement(ListItem element){
		return all.remove(element);
	}//删除最先匹配的项目

	public ListItem remove(int index){
		return all.remove(index);
	}//删除指定位置的项目

	public ListItem set(int index, ListItem element) {
		return all.set(index, element);
	}//修改指定位置的项目
}
