package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.CSocket;

public class SearchFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private ActionHandler actionHandler;
	private JTextField tf1;
	private JTextField tf2;
	private JTextField tf3;
	private JTextField tf4;
	private CSocket cSocket;
	
	public SearchFrame(CSocket cSocket){
		this.cSocket=cSocket;
		this.actionHandler = new ActionHandler();
		this.setSize(50, 270);
		Dimension screen =Toolkit.getDefaultToolkit().getScreenSize();
		double w = screen.getWidth();
		double h = screen.getHeight();
		this.setLocation((int) (w/2)+(588/2),(int) (h/2));
		
		this.setResizable(false);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255,255,255));

		JLabel lb = new JLabel("--------------Id ã��------------- ");
		panel.add(lb);

		JLabel lb1 = new JLabel("�̸�");
		panel.add(lb1);
		this.tf1 = new JTextField(10);
		panel.add(tf1);

		JLabel lb2 = new JLabel("�й�");
		panel.add(lb2);
		this.tf2 = new JTextField(10);
		panel.add(tf2);

		JButton bt1 = new JButton("Id ã��");
		bt1.addActionListener(actionHandler);
		bt1.setActionCommand("bt1");
		panel.add(bt1);

		JLabel lb3 = new JLabel("--------------Pw ã��------------- ");
		panel.add(lb3);

		JLabel lb4 = new JLabel("���̵�");
		panel.add(lb4);
		this.tf3 = new JTextField(9);
		panel.add(tf3);

		JLabel lb5 = new JLabel("�й�");
		panel.add(lb5);
		this.tf4 = new JTextField(10);
		panel.add(tf4);

		JButton bt2 = new JButton("Pw ã��");
		bt2.addActionListener(actionHandler);
		bt2.setActionCommand("bt2");
		panel.add(bt2);
		this.add(panel);
	}
	private class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("bt1")) {
				String name = tf1.getText();
				String studentNumber = tf2.getText();
				String result = cSocket.runSearchId("runSearchId,"+name+"-"+studentNumber);
				String arr[]=result.split(",");
				String Id1 = arr[0];
				String Id2 = arr[1];

				if(Id1.equals(Id2)) {
					JOptionPane.showConfirmDialog(null,name+"���� ���̵�� "+Id1+" �Դϴ�.","�޽���", JOptionPane.PLAIN_MESSAGE  );
				}else if(Id1.equals(null)||Id2.equals(null)){
					JOptionPane.showConfirmDialog(null,"ȸ�������� ���ų� �߸� �Է��ϼ̽��ϴ�.","�޽���", JOptionPane.PLAIN_MESSAGE  );
				}else {
					JOptionPane.showConfirmDialog(null,"ȸ�������� ���ų� �߸� �Է��ϼ̽��ϴ�.","�޽���", JOptionPane.PLAIN_MESSAGE  );
				}
			}else if(e.getActionCommand().equals("bt2")) {
				String id = tf3.getText();
				String studentNumber = tf4.getText();
				String pw = cSocket.runSearchPw("runSearchPw,"+id+"-"+studentNumber);
				if(!pw.equals("error")) {
					JOptionPane.showConfirmDialog(null,id+"�� ��й�ȣ�� "+pw+" �Դϴ�.","�޽���", JOptionPane.PLAIN_MESSAGE  );
				}else {
					JOptionPane.showConfirmDialog(null,"ȸ�������� ���ų� �߸� �Է��ϼ̽��ϴ�.","�޽���", JOptionPane.PLAIN_MESSAGE  );
				}
			}
		}
	}
}
