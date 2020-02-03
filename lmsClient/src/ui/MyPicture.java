package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.CSocket;

public class MyPicture extends JPanel{
	private static final long serialVersionUID = 1L;
	private BufferedImage img = null;

	public MyPicture(String userId, CSocket cSocket) {
		try {
			this.setPreferredSize(new Dimension(100,100));
			File file = cSocket.getImg("getImg,"+userId);
			cSocket.finalize();
			this.img = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}
}
