package entity;

import java.util.Scanner;

public class ESincheong {
	private int number;
	private String name;
	
	
	public void read(Scanner scanner) {
		this.number = scanner.nextInt();
		this.name = scanner.next();
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
