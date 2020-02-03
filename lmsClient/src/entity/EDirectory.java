package entity;

import java.io.Serializable;
import java.util.Scanner;

public class EDirectory implements Serializable{

	private int number;
	private String name;
	private String hyperLink;
	
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
	public String getHyperLink() {
		return hyperLink;
	}

	public void setHyperLink(String hyperLink) {
		this.hyperLink = hyperLink;
	}

	public void read(Scanner scanner) {
		this.number = scanner.nextInt();
		this.name = scanner.next();
		this.hyperLink = scanner.next();
	}
	
}
