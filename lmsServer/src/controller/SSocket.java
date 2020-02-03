package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import main.Constant;

public class SSocket {

	private ServerSocket serverSocket;
	public SSocket() {
		
	}

	public void start() {
		try {
			this.serverSocket = new ServerSocket(Constant.PORTNUMBER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void finish() {
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		try {
			// wait & create session
			boolean bRunning = true;
			while (bRunning) {
				// client request for connection
				long time1 = System.currentTimeMillis();
				Socket clientSocket = this.serverSocket.accept();
				long time2 = System.currentTimeMillis();
				// create service for client
					Session session = new Session(time2-time1);
					session.start(clientSocket);
					// let service processsRequest
					session.start();
					// close connection
					session.join();
					bRunning = session.isGo();
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
