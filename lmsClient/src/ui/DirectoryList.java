package ui;

import java.io.FileNotFoundException;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import entity.EDirectory;
import main.CSocket;
import view.VDirectory;

public class DirectoryList extends JList<String> {
	private static final long serialVersionUID = 1L;

	private Vector<EDirectory> edirectories;
	private CSocket cSocket;
	public DirectoryList(ListSelectionListener listSelectionHandler,String fileName, CSocket cSocket) {
		this.cSocket=cSocket;
		VDirectory vDirectory = new VDirectory(cSocket);
		edirectories = vDirectory.run(fileName);
		Vector<String> listData = new Vector<String>();
		for(EDirectory eDirectory : edirectories) {
			listData.add(eDirectory.getName());
		}
		this.setListData(listData);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.addListSelectionListener(listSelectionHandler);
	}
	
	public String getSelectedFileName() {
		int selectedIndex = this.getSelectedIndex();
		if(selectedIndex != -1) {
			return this.edirectories.get(selectedIndex).getHyperLink();
		}
		return this.edirectories.get(0).getHyperLink();
	}
	
	public String refresh(String fileName) throws FileNotFoundException {
		VDirectory vDirectory = new VDirectory(cSocket);
		edirectories = vDirectory.run(fileName);
		Vector<String> listData = new Vector<String>();
		for(EDirectory eDirectory : edirectories) {
			listData.add(eDirectory.getName());
		}
		this.setListData(listData);
		this.setSelectedIndex(0);
		return this.edirectories.get(0).getHyperLink();
	}


}
