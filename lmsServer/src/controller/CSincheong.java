package controller;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import dao.DAOSincheong;
import entity.ELecture;
import entity.ESincheong;

public class CSincheong {

	private DAOSincheong daoSincheong;
	
	public CSincheong() {
		this.daoSincheong = new DAOSincheong();
	}
	public Vector<ESincheong> getItems() throws FileNotFoundException {
		return this.daoSincheong.getItems(); 
	}
	
}
