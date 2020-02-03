package view;

import main.CSocket;
public class VBasket {

	private CSocket cSocket;
	
	public VBasket(CSocket cSocket){
		this.cSocket = cSocket;
	}
	
	public boolean run(String line) {
		boolean isWrite = false;
		isWrite = cSocket.writeBasketFile("writeBasketFile,"+line);
		return isWrite;
	}

}
