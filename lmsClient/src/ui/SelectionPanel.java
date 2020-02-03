package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import entity.ELecture;
import main.CSocket;
import view.VBasket;

public class SelectionPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private DirectoryPanel departmentSelection;
	private LectureTable lectureSelection;
	private Vector<ELecture> eLecture;
	private String fileName;
	private JOptionPane jOptionPane;
	private int row;
	private String Name;
	private String userId;
	private CSocket cSocket;
	private String studentDepartment;
	public SelectionPanel(String userId, String studentDepartment, CSocket cSocket) {
		this.userId = userId;
		this.cSocket=cSocket;
		this.studentDepartment=studentDepartment;
		ListSelectionListener listSelectionHandler = new ListSelectionHandler();
		SelectionMouseHandler mouseHandler = new SelectionMouseHandler();
		ActionHandler actionHandler = new ActionHandler();
		this.jOptionPane = new JOptionPane();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();

		JButton basketBt = new JButton("담기");
		basketBt.setToolTipText("선택한 강좌을 담습니다. 만약 선택한 강좌가 없으면 에러가 발생합니다.");
		basketBt.addActionListener(actionHandler);
		basketBt.setActionCommand("basketBt");
		p1.add(basketBt);

		JButton basket = new JButton("책가방");
		basket.setToolTipText("책가방 화면을 띄웁니다.");
		basket.addActionListener(actionHandler);
		basket.setActionCommand("basket");
		p1.add(basket);

		JButton search = new JButton("강좌 찾기");
		search.addActionListener(actionHandler);
		search.setActionCommand("search");
		p1.add(search);

		JButton grades = new JButton("수강 학점");
		grades.addActionListener(actionHandler);
		grades.setActionCommand("grades");
		p1.add(grades);

		this.add(p2);
		this.add(p1);
		this.add(p3);

		p2.setLayout(new BorderLayout());
		this.departmentSelection = new DirectoryPanel(listSelectionHandler,cSocket);
		p2.add(this.departmentSelection,"Center");

		p3.setLayout(new BorderLayout());
		this.lectureSelection = new LectureTable(mouseHandler,cSocket);
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.setViewportView(this.lectureSelection);
		scrollpane.setOpaque(false); 
		scrollpane.getViewport().setOpaque(false); 
		p3.add(scrollpane,"Center");

		this.setBackground(new Color(255,255,255));
		p1.setBackground(new Color(255,255,255));
		p2.setBackground(new Color(255,255,255));
		p3.setBackground(new Color(255,255,255));

	}
	private class ListSelectionHandler implements ListSelectionListener{
		@Override
		public void valueChanged(ListSelectionEvent event) {
			fileName = departmentSelection.refresh(event);
			lectureSelection.refresh(fileName);
		}
	}
	class SelectionMouseHandler implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			MouseClickedAction(e);
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}
	private void MouseClickedAction(MouseEvent e) {
		if(e.getClickCount()==2) {
			BtRun1();
		}
	}
	private class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("basketBt")) {
				BtRun2();
			}else if(e.getActionCommand().equals("basket")) {
				BasketFrame basketFrame = new BasketFrame(userId,cSocket);
				basketFrame.setVisible(true);
			}else if(e.getActionCommand().equals("search")) {
				SearchLecture searchLecture = new SearchLecture(cSocket);
				searchLecture.setVisible(true);
			}else if(e.getActionCommand().equals("grades")) {
				DepartmentFrame departmentFrame = new DepartmentFrame(cSocket,studentDepartment);
				departmentFrame.setVisible(true);
			}

		}
	}
	private void BtRun1() {
		this.row = lectureSelection.getSelectedRow();
		eLecture = lectureSelection.eLecture();
		String Name = eLecture.get(row).getName();
		@SuppressWarnings("static-access")
		int msg1 = jOptionPane.showConfirmDialog(null, Name+" 강좌를 선택하시겠습니까?",
				"선택창", jOptionPane.OK_CANCEL_OPTION );
		if(msg1==0) {
			String ProfessorName = eLecture.get(row).getProfessorName();
			String Credit = eLecture.get(row).getCredit();
			String Time = eLecture.get(row).getTime();
			String line = "basket/"+userId+"basket.txt_"+Name+"_"+ProfessorName+"_"+Credit+"_"+Time;
			VBasket vBasket = new VBasket(cSocket);
			vBasket.run(line);
			
			@SuppressWarnings("static-access")
			int msg2 = jOptionPane.showConfirmDialog(null,Name+" 강좌가 선택되었습니다. 책가방으로 가시겠습니까?",
					"선택창", jOptionPane.OK_CANCEL_OPTION );
			if(msg2==0) {
				BasketFrame basketFrame = new BasketFrame(userId,cSocket);
				basketFrame.setVisible(true);
			}
		}
	}
	private void BtRun2() {
		Vector<Integer> count = new Vector<Integer>();
		for(int a=0;lectureSelection.getRowCount()>=a;a++) {
			if(lectureSelection.isRowSelected(a)) {
				count.add(a);
			}
		}
		for(int b=0;count.size()>b;b++) {
			row=count.get(b);
			if(row==-1) {
				JOptionPane.showConfirmDialog(null,"선택된 강좌가 없습니다.","메시지", JOptionPane.PLAIN_MESSAGE  );
			}else {
				eLecture = lectureSelection.eLecture();
				Name = eLecture.get(row).getName();
				String ProfessorName = eLecture.get(row).getProfessorName();
				String Credit = eLecture.get(row).getCredit();
				String Time = eLecture.get(row).getTime();
				String line = "basket/"+userId+"basket.txt_"+Name+"_"+ProfessorName+"_"+Credit+"_"+Time;
				VBasket vBasket = new VBasket(cSocket);
				vBasket.run(line);
			}
			JOptionPane.showConfirmDialog(null,"책가방에  "+Name+" 강좌를 담았습니다.","메시지", JOptionPane.PLAIN_MESSAGE  );
		}
	}
}
