package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.FileNotFoundException;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.CSocket;

public class DirectoryPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	private DirectoryList campusList;
	private DirectoryList collegeList; //data segment
	private DirectoryList departmentList;

	public DirectoryPanel(ListSelectionListener listSelectionHandler, CSocket cSocket) {

		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		

		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		JPanel p5 = new JPanel();

		JScrollPane scrollpane = new JScrollPane();
		this.campusList = new DirectoryList(listSelectionHandler,"root",cSocket);
		scrollpane.setViewportView(this.campusList);
		p1.setLayout(new BorderLayout());
		p1.add(scrollpane);

		p4.setBackground(new Color(255,255,255));
		p4.setPreferredSize(new Dimension(30,30));
		
		scrollpane = new JScrollPane();
		this.collegeList = new DirectoryList(listSelectionHandler, "yongin",cSocket);
		scrollpane.setViewportView(this.collegeList);
		p2.setLayout(new BorderLayout());
		p2.add(scrollpane);

		p5.setBackground(new Color(255,255,255));
		p5.setPreferredSize(new Dimension(30,30));
		
		scrollpane = new JScrollPane();
		this.departmentList = new DirectoryList(listSelectionHandler, "generalY",cSocket);
		scrollpane.setViewportView(this.departmentList);
		p3.setLayout(new BorderLayout());
		p3.add(scrollpane);
		


		this.add(p1);
		this.add(p4);
		this.add(p2);
		this.add(p5);
		this.add(p3);



	}
	public String refresh(ListSelectionEvent event) {

		try {
			if(event.getSource() == campusList) {
				String fileName = campusList.getSelectedFileName();
				fileName = collegeList.refresh(fileName);
				return null;
			}else if(event.getSource() == collegeList) {
				String fileName = collegeList.getSelectedFileName();
				fileName = departmentList.refresh(fileName);
				return null;
			}else if(event.getSource() == departmentList) {
				String fileName = departmentList.getSelectedFileName();
				return fileName;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}return null;

	}

}
