package dao;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import entity.EDirectory;

public class DAODirectory {

	String userName = "root";
	String passWord = "0518love!";
	String URL1 = "jdbc:mysql://localhost:3306/";
	String URL2 = "?serverTimezone=UTC";
	
	public Vector<EDirectory> getItems(String fileName) throws FileNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String queryString = "SELECT * FROM user";
			Connection connection = DriverManager.getConnection(URL1+fileName+URL2,userName,passWord);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(queryString);
			Vector<EDirectory> basket = new Vector<EDirectory>();
			while(resultSet.next()) {
				EDirectory eDirectory = new EDirectory();
				eDirectory.setNumber(resultSet.getInt("number"));
				eDirectory.setName(resultSet.getString("name"));
				eDirectory.setHyperLink(resultSet.getString("hyperLink"));
				basket.add(eDirectory);
			}
			return basket;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
