import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DBExam {
	Connection connection;
	Statement statement;
	ResultSet resultSet;


	String userName = "root";
	String passWord = "";
	String URL1 = "jdbc:mysql://localhost:3306/";
	String URL2 = "?serverTimezone=UTC";

	public DBExam() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	public void productSelectOne() {
		try {
			String queryString = "SELECT * FROM user";
			connection = DriverManager.getConnection(URL1+"arab"+URL2,userName,passWord);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(queryString);

			while(resultSet.next()) {
				System.out.println(resultSet.getInt("number")+" "+resultSet.getString("name")
				+" "+resultSet.getString("professorName")+" "+resultSet.getString("credit")+" "+resultSet.getString("time"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeDatabase();
		}

	}
	public void insert() {
		try {
			Scanner sc = new Scanner(new File("data/basicArchitecture"));
			while(sc.hasNext()) {
				try {
					String queryString = "INSERT INTO user VALUES (?,?,?,?,?)";
					connection = DriverManager.getConnection(URL1+"basicArchitecture"+URL2,userName,passWord);
					PreparedStatement pstmt = connection.prepareStatement(queryString);
					ELecture eLecture = new ELecture();
					eLecture.read(sc);
					pstmt.setInt(1,eLecture.getNumber());
					pstmt.setString(2, eLecture.getName());
					pstmt.setString(3,eLecture.getProfessorName());
					pstmt.setString(4,eLecture.getCredit());
					pstmt.setString(5,eLecture.getTime());
					System.out.println(pstmt.executeUpdate());
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					closeDatabase();
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	public void insert1() {
		try {
			Scanner sc = new Scanner(new File("data/yongin "));
			while(sc.hasNext()) {
				try {
					String queryString = "INSERT INTO user VALUES (?,?,?)";
					connection = DriverManager.getConnection(URL1+"yongin "+URL2,userName,passWord);
					PreparedStatement pstmt = connection.prepareStatement(queryString);
					EDirectory eDirectory = new EDirectory();
					eDirectory.read(sc);
					pstmt.setInt(1,eDirectory.getNumber());
					pstmt.setString(2, eDirectory.getName());
					pstmt.setString(3,eDirectory.getHyperLink());
					System.out.println(pstmt.executeUpdate());
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					closeDatabase();
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	private void closeDatabase() {
		try
		{
			if( resultSet != null )
			{
				// resultSet ´Ý±â
				resultSet.close();
			}

			if( statement != null )
			{
				// statement ´Ý±â
				statement.close();
			}

			if( connection != null )
			{
				// connection ´Ý±â
				connection.close();
			}
		}
		catch (SQLException e)
		{
			System.out.println("[´Ý±â ¿À·ù]\n" + e.getStackTrace());
		}
	}
}
