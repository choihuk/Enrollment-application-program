package dao;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOLogin {
	
	String userName = "root";
	String passWord = "0518love!";
	String URL1 = "jdbc:mysql://localhost:3306/";
	String URL2 = "?serverTimezone=UTC";
	
//	private void read(Scanner scanner) {
//		this.rUserId = scanner.next();
//		this.rPassword = scanner.next();
//	}


	public boolean authenticate(String userId, String password) throws FileNotFoundException, InvalidUserException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String queryString = "SELECT * FROM user";
			Connection connection = DriverManager.getConnection(URL1+"login"+URL2,userName,passWord);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(queryString);
			while(resultSet.next()) {
				if(resultSet.getString("id").equals(userId)&& resultSet.getString("password").equals(password)) {
					return true;
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
//		Scanner scanner = new Scanner(new File("data/login"));	
//		while(scanner.hasNext()) {
//			this.read(scanner);
//			if(this.rUserId.equals(userId) && this.rPassword.equals(password)) {
//				scanner.close();
//				return true;
//			}
//		}
//		scanner.close();
		InvalidUserException invalidUserException = new InvalidUserException();
		throw invalidUserException;
	}
	
	public class InvalidUserException extends Exception{
		private static final long serialVersionUID = 1L;
		public InvalidUserException() {
			super("잘 못 된 사용자 입 니 다.");
		}
	}

}