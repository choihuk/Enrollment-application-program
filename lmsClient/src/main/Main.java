package main;
import ui.LoginFrame;
public class Main {
	//components
	private LoginFrame login;
	//constructor
	public Main() {
		CSocket cSocket = new CSocket();
		this.login = new LoginFrame(cSocket);
	}
	private void run() {
		this.login.setVisible(true);
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}

}
