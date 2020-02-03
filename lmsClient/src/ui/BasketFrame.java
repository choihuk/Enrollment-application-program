package ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.CSocket;

public class BasketFrame extends JFrame{
	private static final long serialVersionUID = 1L;

	public BasketFrame(String userId, CSocket cSocket) {
		this.setSize(600, 600);
		this.setLocation(1000, 200);
		
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		
		BasketPanel basketPanel = new BasketPanel(userId,cSocket);
		basketPanel.setSize(600,600);
		
		this.setBackground(new Color(255,255,255));
		p1.setBackground(new Color(255,255,255));
		p2.setBackground(new Color(255,255,255));
		p3.setBackground(new Color(255,255,255));
		p4.setBackground(new Color(255,255,255));
		
		this.setLayout(new BorderLayout());
		this.add(basketPanel,"Center");
		this.add(p1,"North");
		this.add(p2,"West");
		this.add(p3,"East");
		this.add(p4,"South");
		
	}

}
