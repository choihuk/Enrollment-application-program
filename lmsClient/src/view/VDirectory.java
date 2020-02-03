package view;

import java.util.Vector;

import entity.EDirectory;
import main.CSocket;

public class VDirectory {

	private CSocket cSocket;
	private Vector<EDirectory> edirectories;
	
	public VDirectory(CSocket cSocket){
		this.cSocket = cSocket;
	}
	
	public Vector<EDirectory> run(String fileName) {
		this.edirectories = this.cSocket.runDirectory("dao.DAODirectory,"+fileName);
		return this.edirectories;
	}
}
