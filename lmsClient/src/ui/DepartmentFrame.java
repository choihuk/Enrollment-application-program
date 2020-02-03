package ui;

import java.awt.Color;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.CSocket;

public class DepartmentFrame extends JFrame{
	
	public DepartmentFrame(CSocket cSocket, String studentDepartment) {
		this.setSize(220,270);
		this.setLocation(1400,300);
		this.setResizable(false);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255,255,255));
		String grades = cSocket.webScroll(studentDepartment);
		
		Scanner sc = new Scanner(grades);
		JLabel label[] = new JLabel[14]; 
		for (int i = 0;sc.hasNext(); i++) {
			label[i] = new JLabel(sc.next());
			panel.add(label[i]);
		}
		this.add(panel);
	}

}
