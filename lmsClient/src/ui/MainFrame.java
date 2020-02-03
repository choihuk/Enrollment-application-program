package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.CSocket;
import view.VFile;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private SelectionPanel selectionPannel;
	private String userId;
	private String password;
	private boolean a = true;
	private MyInfoPanel myInfoPanel;
	private MainFrame mainFrame;
	private String studentNumber = null;
	private String studentName = null;
	private String studentDepartment = null;
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	private JPanel p4;
	private LeftPanel leftPanel;
	private TimePanel timePanel;
	private CSocket cSocket;

	public MainFrame(String userId, String password, CSocket cSocket) {
		this.userId=userId;
		this.password = password;
		this.cSocket=cSocket;
		this.mainFrame =this;
		//attributes 
		this.setSize(800, 600);
		Dimension screen =Toolkit.getDefaultToolkit().getScreenSize();
		double w = screen.getWidth();
		double h = screen.getHeight();

		this.setLocation((int) (w/2)-(800/2),(int) (h/2)-(600/2));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		paint();
	}
	private void paint() {

		ActionHandler actionHandler = new ActionHandler();

		//components
		this.p1 = new JPanel();
		this.p2 = new JPanel();
		this.p3 = new JPanel();
		this.p4 = new JPanel();

		JMenu menu = new JMenu("menu");
		JMenuBar menuBar = new JMenuBar();
		JMenuItem menuItem = new JMenuItem("logout");
		JMenuItem menuItem1 = new JMenuItem("exit");
		JMenuItem menuItem2 = new JMenuItem("homepage");

		menuItem.addActionListener(actionHandler);
		menuItem.setActionCommand("logout");
		menuItem1.addActionListener(actionHandler);
		menuItem1.setActionCommand("exit");
		menuItem2.addActionListener(actionHandler);
		menuItem2.setActionCommand("homepage");

		menuBar.add(menu);
		menu.add(menuItem);
		menu.add(menuItem1);
		menu.add(menuItem2);
		this.setJMenuBar(menuBar);


		JLabel title = new JLabel("명지대학교 수강신청");
		title.setFont(new Font("문체부 쓰기 정체", Font.BOLD, 40));
		p1.add(title);

		String data = null;
		data = this.cSocket.getName(userId);
		String arr[] = data.split(",");
		this.studentName = arr[0];
		this.studentNumber = arr[1];
		this.studentDepartment = arr[2];
		this.leftPanel = new LeftPanel(actionHandler,this.studentName,this.userId,this.cSocket);


		p2.setPreferredSize(new Dimension(150,500));
		p2.add(leftPanel);

		this.timePanel = new TimePanel();
		p2.add(timePanel);


		JButton time = new JButton("새로고침");
		time.setActionCommand("time");
		time.addActionListener(actionHandler);
		p2.add(time);


		this.setLayout(new BorderLayout());
		if(a) {
			this.selectionPannel = new SelectionPanel(userId,this.studentDepartment,cSocket);
			this.add(this.selectionPannel,"Center");
		}else {
			this.myInfoPanel = new MyInfoPanel(userId,password,this.studentName,this.studentNumber,this.studentDepartment,actionHandler,cSocket);
			this.add(myInfoPanel,"Center");
		}
		p1.setBackground(new Color(255,255,255));
		p2.setBackground(new Color(255,255,255));
		p3.setBackground(new Color(255,255,255));
		p4.setBackground(new Color(255,255,255));
		leftPanel.setBackground(new Color(255,255,255));
		this.add(p1,"North");
		this.add(p2,"West");
		this.add(p3,"East");
		this.add(p4,"South");


	}
	private class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("mybt")) {
				a=false;
				getContentPane().remove(selectionPannel);
				getContentPane().remove(p1);
				getContentPane().remove(p2);
				getContentPane().remove(p3);
				getContentPane().remove(p4);
				paint();
				myInfoPanel.updateUI();
			}else if(e.getActionCommand().equals("homebt")) {
				a=true;
				getContentPane().remove(myInfoPanel);
				getContentPane().remove(p1);
				getContentPane().remove(p2);
				getContentPane().remove(p3);
				getContentPane().remove(p4);
				paint();
				selectionPannel.updateUI();
			}else if(e.getActionCommand().equals("logout")) {
				mainFrame.dispose();
				LoginFrame login = new LoginFrame(cSocket);
				login.setVisible(true);
			}else if(e.getActionCommand().equals("bt1")) {
				myInfoPanel.activation();
			}else if(e.getActionCommand().equals("bt2")){
				reFile();
				reName();
				reStudentNunber();
				reStudentDepartment();
				myInfoPanel.disactivation();
			}else if (e.getActionCommand().equals("bt3")){
				reImage();
			}else if(e.getActionCommand().equals("exit")) {
				mainFrame.dispose();
			}else if(e.getActionCommand().equals("time")) {
				getContentPane().remove(selectionPannel);
				getContentPane().remove(p1);
				getContentPane().remove(p2);
				getContentPane().remove(p3);
				getContentPane().remove(p4);
				paint();
				selectionPannel.updateUI();
			}else if(e.getActionCommand().equals("homepage")) {
				try {
					Desktop.getDesktop().browse(new URI("http://www.mju.ac.kr/"));
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}

			}
		}

	}
	private void reImage() {
		JFileChooser chooser = new JFileChooser("Icons/"); // 객체 생성
		int ret = chooser.showOpenDialog(null); // 열기창 정의
		if (ret != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "경로를 선택하지 않았습니다.", "경로 선택 오류", JOptionPane.WARNING_MESSAGE);
		}else {
			File loadFile = chooser.getSelectedFile();
			cSocket.reImage("reImage,"+userId,loadFile);
		}
	}
	private void reFile() {
		String rePw = myInfoPanel.getTfPw();
		VFile vFile = new VFile(cSocket);
		vFile.run("reFile,login-"+userId+"-"+rePw);

	}
	private void reName() {
		String reName = myInfoPanel.getTfName();
		VFile vFile = new VFile(cSocket);
		vFile.run("reFile,student-"+userId+"-"+reName);
	}
	private void reStudentNunber() {
		String reSN = myInfoPanel.getTfSN();
		VFile vFile = new VFile(cSocket);
		vFile.run("reFile,studentNumber-"+userId+"-"+reSN);
	}
	private void reStudentDepartment() {
		String reDM = myInfoPanel.getTfDM();
		VFile vFile = new VFile(cSocket);
		vFile.run("reFile,studentDepartment-"+userId+"-"+reDM);
		JOptionPane.showConfirmDialog(null,"저장이 완료되었습니다.","메시지", JOptionPane.PLAIN_MESSAGE  );
	}
}
