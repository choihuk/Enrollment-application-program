package controller;

public class CLecture {

	public CLecture() {
		SSocket sSocket = new SSocket();
		sSocket.start();
		sSocket.run();
		sSocket.finish();
	}
}
