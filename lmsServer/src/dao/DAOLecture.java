package dao;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import entity.ELecture;

public class DAOLecture {

	String userName = "root";
	String passWord = "0518love!";
	String URL1 = "jdbc:mysql://localhost:3306/";
	String URL2 = "?serverTimezone=UTC";
	
	public Vector<ELecture> getItems(String fileName) throws FileNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String queryString = "SELECT * FROM user";
			Connection connection = DriverManager.getConnection(URL1+fileName+URL2,userName,passWord);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(queryString);
			Vector<ELecture> basket = new Vector<ELecture>();
			while(resultSet.next()) {
				ELecture eLecture = new ELecture();
				eLecture.setNumber(resultSet.getInt("number"));
				eLecture.setName(resultSet.getString("name"));
				eLecture.setProfessorName(resultSet.getString("professorName"));
				eLecture.setCredit(resultSet.getString("credit"));
				eLecture.setTime(resultSet.getString("time"));
				basket.add(eLecture);
			}
			return basket;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
