package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import entity.ELecture;
import entity.ESincheong;

public class DAOSincheong {

	public Vector<ESincheong> getItems() throws FileNotFoundException {

		Scanner scanner = new Scanner(new File("basket/basket.txt"));
		Vector<ESincheong> items = new Vector<ESincheong>();
		
		while(scanner.hasNext()) {
			ESincheong eSincheong = new ESincheong();
			eSincheong.read(scanner);
			items.add(eSincheong);
		}
		return items;
	}

}
