package view;

import java.util.Vector;

import entity.ELecture;
import main.CSocket;

public class VSincheong {

	private CSocket cSocket;
	public VSincheong(CSocket cSocket) {
		this.cSocket = cSocket;
	}

	public Vector<ELecture> run(String line) {
		Vector<ELecture> a = new Vector<ELecture>();
		a = cSocket.getFile(line);
		return a;
	}
}








