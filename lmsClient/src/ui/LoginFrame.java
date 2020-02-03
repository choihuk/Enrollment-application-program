package ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import main.CSocket;


public class LoginFrame extends JFrame{
	private static final long serialVersionUID = 1L;

		
	public LoginFrame(CSocket cSocket) {
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setSize(600,600);
		layeredPane.setLayout(null);
		
		LoginPanel panel = new LoginPanel(this,cSocket);
		
		panel.setSize(600,600);
		layeredPane.add(panel);
		
		this.setLayout(null);
		this.add(layeredPane);
		
		Dimension screen =Toolkit.getDefaultToolkit().getScreenSize();
		double w = screen.getWidth();
		double h = screen.getHeight();

		this.setLocation((int) (w/2)-(588/2),(int) (h/2)-(584/2));
		this.setSize(588,584);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

}
