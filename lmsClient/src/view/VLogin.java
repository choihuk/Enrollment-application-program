package view;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import main.AES256Util;
import main.CSocket;

public class VLogin {
	//component
	private boolean isTrue;
	private CSocket cSocket;
	public VLogin(CSocket cSocket) {
		this.cSocket = cSocket;
	}

	public boolean authenticate(String userIdText, String passwordText) {

		String userId = userIdText;
		String password = passwordText;
		try {
			AES256Util util = new AES256Util();
			String encoded = util.encrypt(userId+"+"+password,"12345678987654321");
			String line = "dao.DAOLogin,"+encoded;
			this.isTrue = this.cSocket.runLogin(line);
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		}
		return isTrue;
	}
}
