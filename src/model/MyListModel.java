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
	public void add(int index, ListItem element){}//��ָ��λ�ò�����Ŀ

	public void addElement(ListItem element){
		all.add(element);
	}//������������Ŀ

	public boolean removeElement(ListItem element){
		return all.remove(element);
	}//ɾ������ƥ�����Ŀ

	public ListItem remove(int index){
		return all.remove(index);
	}//ɾ��ָ��λ�õ���Ŀ

	public ListItem set(int index, ListItem element) {
		return all.set(index, element);
	}//�޸�ָ��λ�õ���Ŀ
}
