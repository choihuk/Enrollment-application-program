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

public class SignUpFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private ActionHandler actionHandler;
	private JTextField tf1;
	private JTextField tf2;
	private JTextField tf3;
	private JTextField tf4;
	private SignUpFrame signUpFrame;
	private CSocket cSocket;
	
	public SignUpFrame(CSocket cSocket) {
		this.cSocket=cSocket;
		this.signUpFrame = this;
		this.actionHandler = new ActionHandler();
		this.setSize(50, 200);
		Dimension screen =Toolkit.getDefaultToolkit().getScreenSize();
		double w = screen.getWidth();
		double h = screen.getHeight();
		this.setLocation((int) (w/2)+(588/2),(int) (h/2)-(584/2));
		this.setResizable(false);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255,255,255));

		JLabel lb1 = new JLabel("이름");
		panel.add(lb1);
		this.tf1 = new JTextField(11);
		panel.add(tf1);

		JLabel lb2 = new JLabel("아이디");
		panel.add(lb2);
		this.tf2 = new JTextField(10);
		panel.add(tf2);

		JLabel lb3 = new JLabel("비밀번호");
		panel.add(lb3);
		this.tf3 = new JTextField(8);
		panel.add(tf3);

		JLabel lb4 = new JLabel("학번");
		panel.add(lb4);
		this.tf4 = new JTextField(11);
		panel.add(tf4);

		JButton bt1 = new JButton("회원가입");
		bt1.addActionListener(actionHandler);
		bt1.setActionCommand("bt1");
		panel.add(bt1);

		this.add(panel);
	}
	private class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			run(e);
		}

	}
	@SuppressWarnings("resource")
	private void run(ActionEvent e) {
		if(e.getActionCommand().equals("bt1")) {

			String newName = tf1.getText();
			String newId = tf2.getText();
			String newPw = tf3.getText();
			String newSN = tf4.getText();
			if(!newName.equals("")&&!newId.equals("")&&!newPw.equals("")&&!newSN.equals("")) {
				cSocket.signUp("runSignUp,"+newName+"-"+newId+"-"+newPw+"-"+newSN);

				JOptionPane.showConfirmDialog(null,"회원가입이 완료되었습니다.","메시지", JOptionPane.PLAIN_MESSAGE  );
				signUpFrame.dispose();
			}else {
				JOptionPane.showConfirmDialog(null,"빈칸에 정보를 입력해주시기 바랍니다.","메시지", JOptionPane.PLAIN_MESSAGE  );
			}
		}
	}
}
