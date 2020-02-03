package controller;
import java.io.FileNotFoundException;

import dao.DAOLogin;
import dao.DAOLogin.InvalidUserException;


public class CLogin {
	private DAOLogin eLogin;
	
	public CLogin() {
		this.eLogin =  new DAOLogin();
	}

	public boolean authenticate(String userId, String password) throws FileNotFoundException, InvalidUserException {
		boolean validUser = eLogin.authenticate(userId, password);
		return validUser;
		
	}

}
