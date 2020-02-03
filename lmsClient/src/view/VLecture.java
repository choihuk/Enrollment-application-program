package view;

import java.util.Vector;

import entity.ELecture;
import main.CSocket;

public class VLecture {

	private CSocket cSocket;
	private Vector<ELecture> eLectures;
	
	public VLecture(CSocket cSocket) {
		this.cSocket =cSocket;
		
	}

	public Vector<ELecture> run(String fileName) {
		this.eLectures = this.cSocket.runLecture("dao.DAOLecture,"+fileName);
		return this.eLectures;
	}

	
}
