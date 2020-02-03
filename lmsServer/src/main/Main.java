package main;

import controller.SSocket;

public class Main {
	public Main() {

	}
	//여기부터
	private void run() {
		SSocket sSocket = new SSocket();
		sSocket.start();
		sSocket.run();
		sSocket.finish();

	}


	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}
	//여기까지 고정.


}
