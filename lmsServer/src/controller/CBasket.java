package controller;

import java.io.FileNotFoundException;
import java.util.Scanner;

import dao.DAOBasket;

public class CBasket {

	private DAOBasket daoBasket;
	
	public CBasket() {
		this.daoBasket = new DAOBasket();
	}
	public Scanner getItems() throws FileNotFoundException {
		Scanner scanner = this.daoBasket.getItems();
		return scanner;
	}
}
