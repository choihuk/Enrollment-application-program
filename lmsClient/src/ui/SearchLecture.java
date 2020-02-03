package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.CSocket;

@SuppressWarnings("serial")
public class SearchLecture extends JFrame{
	private ActionHandler actionHandler;
	private JOptionPane jOptionPane;
	private CSocket cSocket;
	private JTextField tf1;
	private JTextField tf2;

	public SearchLecture(CSocket cSocket) {
		this.actionHandler=new ActionHandler();
		this.jOptionPane=new JOptionPane();
		this.cSocket=cSocket;
		this.setSize(230,270);
		this.setLocation(1400,500);
		this.setResizable(false);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255,255,255));

		JLabel lb = new JLabel("---------���� �̸����� ���� ã��---------- ");
		panel.add(lb);

		JLabel lb1 = new JLabel("���� �̸�");
		panel.add(lb1);
		this.tf1 = new JTextField(10);
		panel.add(tf1);

		JButton bt1 = new JButton("���� ã��");
		bt1.addActionListener(actionHandler);
		bt1.setActionCommand("bt1");
		panel.add(bt1);
		this.add(panel);
		JLabel lb3 = new JLabel("----�ش� ������ �������� ���� ã��---- ");
		panel.add(lb3);

		JLabel lb4 = new JLabel("������ ����");
		panel.add(lb4);
		this.tf2 = new JTextField(10);
		panel.add(tf2);

		JButton bt2 = new JButton("���� ã��");
		bt2.addActionListener(actionHandler);
		bt2.setActionCommand("bt2");
		panel.add(bt2);
		this.add(panel);


	}
	private class ActionHandler implements ActionListener{
		@SuppressWarnings("static-access")
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("bt1")) {
				String name = tf1.getText();
				String lecture = cSocket.findLecture("findLecture,"+name);
				if(lecture.equals("null")) {
					jOptionPane.showConfirmDialog(null,"�Է��Ͻ� ���´� �������� �ʴ� �����Դϴ�.","����", jOptionPane.PLAIN_MESSAGE );
				}else {
					jOptionPane.showConfirmDialog(null,"�ش� ���´� "+lecture+"���� �����Դϴ�.","���� ã��!", jOptionPane.PLAIN_MESSAGE );
				}
			}else if(e.getActionCommand().equals("bt2")) {
				String professerName = tf2.getText();
				Vector<String> lecture = cSocket.findProfessor("findProfessor,"+professerName);
				if(lecture.isEmpty()) {
					jOptionPane.showConfirmDialog(null,"�Է��� �߸��Ǿ����ϴ�.","����", jOptionPane.PLAIN_MESSAGE );
				}else {
					String allLecture = null;
					for (int i = 0; i < lecture.size(); i++) {
						if(allLecture==null) {
							allLecture = lecture.get(i);
						}else {
							allLecture = allLecture + ", "+lecture.get(i);
						}
					}
					jOptionPane.showConfirmDialog(null,professerName+"�������� ���´� "+allLecture+"�Դϴ�.","���� ã��!", jOptionPane.PLAIN_MESSAGE );
				}
			}
		}
	}
}
