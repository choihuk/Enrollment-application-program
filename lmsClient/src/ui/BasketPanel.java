package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import main.CSocket;

public class BasketPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private BasketTable basketTable;
	private SincheongTable sincheongTable;
	private JTextField textField1;
	private JTextField textField2;
	private JTextField textField3;
	private String userId;
	private CSocket cSocket;

	public BasketPanel(String userId, CSocket cSocket) {
		this.userId = userId;
		this.cSocket=cSocket;
		ActionHandler actionHandler = new ActionHandler(); 

		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		JPanel p5 = new JPanel();
		JPanel p6 = new JPanel();

		p1.setBackground(new Color(255,255,255));
		p2.setBackground(new Color(255,255,255));
		p3.setBackground(new Color(255,255,255));
		p4.setBackground(new Color(255,255,255));
		p5.setBackground(new Color(255,255,255));
		p6.setBackground(new Color(255,255,255));

		p2.setLayout(new BorderLayout());
		this.basketTable = new BasketTable(userId,cSocket);
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.setViewportView(basketTable);
		scrollpane.setOpaque(false); 
		scrollpane.getViewport().setOpaque(false);
		p2.add(scrollpane,"Center");

		JLabel label1 = new JLabel("책가방 신청개수");
		p1.add(label1);
		this.textField1 = new JTextField(2);
		this.textField1.setEnabled(false);
		String a = basketTable.basketRowCount();
		textField1.setText(a);
		p1.add(textField1);

		JButton bt4 = new JButton("새로고침");
		bt4.addActionListener(actionHandler);
		bt4.setActionCommand("bt4");
		p1.add(bt4);

		JButton bt1 = new JButton("담기 취소");
		bt1.addActionListener(actionHandler);
		bt1.setActionCommand("bt1");
		p3.add(bt1);

		JButton bt2 = new JButton("수강신청");
		bt2.addActionListener(actionHandler);
		bt2.setActionCommand("bt2");
		p3.add(bt2);


		p5.setLayout(new BorderLayout());
		this.sincheongTable = new SincheongTable(userId,cSocket);
		scrollpane = new JScrollPane();
		scrollpane.setViewportView(sincheongTable);
		scrollpane.setOpaque(false); 
		scrollpane.getViewport().setOpaque(false);
		p5.add(scrollpane,"Center");

		JLabel label2 = new JLabel("수강신청 개수");
		p4.add(label2);
		this.textField2 = new JTextField(2);
		this.textField2.setEnabled(false);
		String b = sincheongTable.sinRowCount();
		textField2.setText(b);
		p4.add(textField2);
		JLabel label3 = new JLabel("수강신청 학점");
		p4.add(label3);
		this.textField3 = new JTextField(2);
		this.textField3.setEnabled(false);
		String c = sincheongTable.credit();
		textField3.setText(c);
		p4.add(textField3);

		JButton bt3 = new JButton("신청 취소");
		bt3.addActionListener(actionHandler);
		bt3.setActionCommand("bt3");
		p6.add(bt3);


		this.add(p1);
		this.add(p2);
		this.add(p3);
		this.add(p4);
		this.add(p5);
		this.add(p6);
	}
	
	private class ActionHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("bt1")) {
				run1();
				refreshB();
			}else if(e.getActionCommand().equals("bt2")) {
				basketTable.MouseClickedAction(sincheongTable);
				refreshS();
			}else if(e.getActionCommand().equals("bt3")) {
				run2();
				refreshS();
			}else if(e.getActionCommand().equals("bt4")) {
				refreshB();
				refreshS();
			}
		}
	}
	private void refreshS() {
		sincheongTable.paint();
		String b = sincheongTable.sinRowCount();
		textField2.setText(b);
		String c = sincheongTable.credit();
		textField3.setText(c);
	}
	private void refreshB() {
		basketTable.paint();
		String a = basketTable.basketRowCount();
		textField1.setText(a);
	}
	private void run1() {
		Vector<Integer> count = new Vector<Integer>();
		for(int a=0;basketTable.getRowCount()>=a;a++) {
			if(basketTable.isRowSelected(a)) {
				count.add(a);
			}
		}
		for(int b=0;count.size()>b;b++) {
			int row=count.get(b);
			if(row!=-1) {
				cSocket.reBasket("reBasket,"+row+"-basket/"+userId+"basket.txt");
			}
		}
	}
	private void run2() {

			Vector<Integer> count = new Vector<Integer>();
			for(int a=0;sincheongTable.getRowCount()>=a;a++) {
				if(sincheongTable.isRowSelected(a)) {
					count.add(a);
				}
			}
			for(int b=0;count.size()>b;b++) {
				int row=count.get(b);
				if(row!=-1) {
					cSocket.reBasket("reBasket,"+row+"-basket/"+userId+"sincheong.txt");
				}
			}
		
	}
}
