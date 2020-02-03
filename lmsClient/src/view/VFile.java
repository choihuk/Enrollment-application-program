package view;

import main.CSocket;

public class VFile {
	private CSocket cSocket;
	
	public VFile(CSocket cSocket){
		this.cSocket = cSocket;
	}
	public void run(String line) {
		cSocket.reFile(line);
	}
}
