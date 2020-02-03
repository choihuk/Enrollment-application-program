package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DAOBasket {

	public Scanner getItems() throws FileNotFoundException {
	
			Scanner scanner = new Scanner(new File("basket/basket.txt"));

		return scanner;
	}

	
}
