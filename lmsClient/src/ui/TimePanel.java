package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class TimePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	public TimePanel() {
		this.setBackground(new Color(255,255,255));
		this.setLayout(new GridLayout(2,0));
		this.setPreferredSize(new Dimension(140,80));
		run();
	}
	public void run() {
		Calendar t = Calendar.getInstance();
		String year =  Integer.toString(t.get(Calendar.YEAR));
		String month = Integer.toString(t.get(Calendar.MONTH)+1);
		String data = Integer.toString(t.get(Calendar.DATE));
		String hour = Integer.toString(t.get(Calendar.HOUR_OF_DAY));
		String min = Integer.toString(t.get(Calendar.MINUTE));
		String sec = Integer.toString(t.get(Calendar.SECOND));
		
		Font font = new Font("digital-7", Font.BOLD, 35);
		JLabel lb1 = new JLabel();
		lb1.setText(year+"."+month+"."+data);
		lb1.setFont(font);
		JLabel lb2 = new JLabel();
		lb2.setText(hour+":"+min+":"+sec);
		lb2.setFont(font);
		this.add(lb1);
		this.add(lb2);

	}

}
