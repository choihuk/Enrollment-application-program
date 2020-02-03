package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Vector;

import entity.EDirectory;
import entity.ELecture;
import main.AES256Util;

public class Session extends Thread {
	private Socket clientSocket;

	private InputStream inputStream;
	private BufferedReader bufferedReader;
	private OutputStream outputStream;
	private ObjectOutputStream objectOutputStream;
	private boolean isGo = true;
	private boolean quit = false;
	String userName = "root";
	String passWord = "0518love!";
	String URL1 = "jdbc:mysql://localhost:3306/";
	String URL2 = "?serverTimezone=UTC";
	
	public Session(long time) {
		if(time>300000) {
			this.quit=true;
		}
	}
	public void start(Socket clientSocket) {
		try {
			// associate clientSocket
			this.clientSocket = clientSocket;
			// create buffered reader
			this.inputStream = clientSocket.getInputStream();
			this.bufferedReader = new BufferedReader(new InputStreamReader(this.inputStream));
			// create print writer
			this.outputStream = clientSocket.getOutputStream();
			this.objectOutputStream = new ObjectOutputStream(this.outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void finish() {
		try {
			this.bufferedReader.close();
			this.objectOutputStream.close();
			this.clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isGo() {
		return isGo;
	}
	public void run() {
		try {
			// read data from client
			String line = this.bufferedReader.readLine();
			String dataName = line.substring(line.lastIndexOf(",")+1);	//내가 만든 프로토콜에 따라 데이터를 나눈다.
			String className = line.substring(0,line.lastIndexOf(","));
			System.out.println(className+"||"+dataName);
			if(className.equals("dao.DAOLecture")) {
				runELecture(className,dataName);
			}else if(className.equals("dao.DAOLogin")) {
				runLogin(className,dataName);
			}else if(className.equals("getStudent")) {
				getStudentName(dataName);
			}else if(className.equals("dao.DAODirectory")) {
				runEDirectory(className,dataName);
			}else if(className.equals("getFile")) {
				getFile(className,dataName);
			}else if(className.equals("writeBasketFile")) {
				writeBasketFile(className,dataName);
			}else if(className.equals("reBasket")) {
				reBasket(dataName);
			}else if(className.equals("reFile")){
				reFile(dataName);
			}else if(className.equals("runSignUp")) {
				runSignUp(dataName);
			}else if(className.equals("runSearchId")) {
				runSearchId(dataName);
			}else if(className.equals("runSearchPw")) {
				runSearchPw(dataName);
			}else if(className.equals("getImg")) {
				getImg(dataName);
			}else if(className.equals("reImage")) {
				reImage(dataName);
			}else if(className.equals("getTime")) {
				getTime();
			}else if(className.equals("findLecture")) {
				findLecture(dataName);
			}else if(className.equals("findProfessor")) {
				findProfessor(dataName);
			}else if(className.equals("killServer")) {
				isGo=false;
			}
			finish();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void findProfessor(String dataName) {
		try {
			File file = new File("lectureCollection");
			Scanner sc = new Scanner(file);
			Vector<String> lecture = new Vector<String>();
			Class.forName("com.mysql.cj.jdbc.Driver");
			while(sc.hasNext()) {
				String a = sc.next();
				ResultSet resultSet = DriverManager.getConnection(URL1+a+URL2,userName,passWord).createStatement().executeQuery("SELECT * FROM user");
				while(resultSet.next()) {
					if(resultSet.getString("professorName").equals(dataName)) {
						lecture.add(resultSet.getString("name"));
					}
				}
			}
			this.objectOutputStream.writeObject(quit);
			this.objectOutputStream.writeObject(lecture);
			this.objectOutputStream.flush();
		} catch (SQLException | IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void findLecture(String dataName) {
		try {
			File file = new File("lectureCollection");
			Scanner sc = new Scanner(file);
			String goLecture="null";
			Class.forName("com.mysql.cj.jdbc.Driver");
			while(sc.hasNext()) {
				String lecture = sc.next();
				ResultSet resultSet = DriverManager.getConnection(URL1+lecture+URL2,userName,passWord).createStatement().executeQuery("SELECT * FROM user");
				while(resultSet.next()) {
					if(resultSet.getString("name").equals(dataName)) {
						goLecture=lecture;
					}
				}
			}
			this.objectOutputStream.writeObject(quit);
			this.objectOutputStream.writeObject(goLecture);
			this.objectOutputStream.flush();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}

	}

	private void getTime() {
		try {
			int[] startTime = {2019,12,9,10,13};
			int[] endTime = {2019,12,12,18,30};
			Object[] object = {startTime,endTime};
			this.objectOutputStream.writeObject(quit);
			this.objectOutputStream.writeObject(object);
			this.objectOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void reImage(String dataName) {
		try {
			File file = new File(dataName+"image.jpg");
			FileOutputStream fileOutputStream;
			fileOutputStream = new FileOutputStream(file);
			byte buffer[] = new byte[2048];
			// read header(10 bytes)
			this.inputStream.read(buffer, 0, 10);
			String header = new String(buffer, 0, 10);
			int bodysize = Integer.parseInt(header);
			int readsize = 0;
			// read body
			while (readsize < bodysize) {
				int rsize = this.inputStream.read(buffer);
				fileOutputStream.write(buffer, 0, rsize);
				readsize += rsize;
			}
			fileOutputStream.close();
			Class.forName("com.mysql.cj.jdbc.Driver");
			String queryString = "SELECT * FROM user";
			ResultSet resultSet = DriverManager.getConnection(URL1+"studentImage"+URL2,userName,passWord).createStatement().executeQuery(queryString);
			boolean isWrite=true;
			while(resultSet.next()) {
				if(resultSet.getString("number").equals(dataName)) {
					isWrite=false;
				}
			}
			if(isWrite) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				queryString = "INSERT INTO user VALUES (?,?)";
				PreparedStatement pstmt = DriverManager.getConnection(URL1+"studentImage"+URL2,userName,passWord).prepareStatement(queryString);
				pstmt.setInt(1,Integer.parseInt(dataName));
				pstmt.setString(2,dataName+"image.jpg");
				System.out.println(pstmt.executeUpdate());
			}
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void getImg(String dataName) {
		try {
			String imgName = null;
			Class.forName("com.mysql.cj.jdbc.Driver");
			String queryString = "SELECT * FROM user";
			ResultSet resultSet = DriverManager.getConnection(URL1+"studentImage"+URL2,userName,passWord).createStatement().executeQuery(queryString);
			while(resultSet.next()) {
				if(resultSet.getString("number").equals(dataName)) {
					imgName = resultSet.getString("image");
				}
			}
			if(imgName == null) {
				imgName = "numimage.jpg";
			}
			byte buffer[] = new byte[2048];
			File imgfile = new File(imgName);
			String flen = String.valueOf(imgfile.length());
			String header = "0000000000".substring(0, 10-flen.length()) + flen;
			FileInputStream fileInputStream = new FileInputStream(imgfile);
			// send header
			this.outputStream.write(header.getBytes());
			// send body
			while (fileInputStream.available() > 0) {
				int readsz = fileInputStream.read(buffer);
				outputStream.write(buffer, 0, readsz);
			}
			fileInputStream.close();
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void runSearchPw(String dataName) {
		String arr[] = dataName.split("-");
		String line;
		String pw="error";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String queryString = "SELECT * FROM user";
			Connection connection = DriverManager.getConnection(URL1+"studentNumber"+URL2,userName,passWord);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(queryString);
			while(resultSet.next()) {
				line = resultSet.getString("id")+" "+resultSet.getString("number");
				if(line.equals(arr[0]+" "+arr[1])) {
					resultSet = DriverManager.getConnection(URL1+"login"+URL2,userName,passWord).createStatement().executeQuery(queryString);
					while(resultSet.next()) {
						if(resultSet.getString("id").equals(arr[0])) {
							pw = resultSet.getString("password");
						}
					}
				}
			}
			this.objectOutputStream.writeObject(quit);
			this.objectOutputStream.writeObject(pw);
			this.objectOutputStream.flush();
		} catch (IOException | ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}

	}

	private void runSearchId(String dataName) {
		String arr[] = dataName.split("-");
		String Id1="_";
		String Id2="-";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String queryString = "SELECT * FROM user";
			Connection connection = DriverManager.getConnection(URL1+"student"+URL2,userName,passWord);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(queryString);
			while(resultSet.next()) {
				if(resultSet.getString("name").equals(arr[0])) {
					Id1 = resultSet.getString("id");
				}
			}
			connection = DriverManager.getConnection(URL1+"studentNumber"+URL2,userName,passWord);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(queryString);
			while(resultSet.next()) {
				if(resultSet.getString("number").equals(arr[1])) {
					Id2 = resultSet.getString("id");
				}
			}
			this.objectOutputStream.writeObject(quit);
			this.objectOutputStream.writeObject(Id1+","+Id2);
			this.objectOutputStream.flush();
		} catch (IOException | ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}

	}

	private void runSignUp(String dataName) {
		String arr[] = dataName.split("-");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("basket/"+arr[1]+"basket.txt"));
			bw = new BufferedWriter(new FileWriter("basket/"+arr[1]+"sincheong.txt"));
			bw.close();

			Class.forName("com.mysql.cj.jdbc.Driver");
			String queryString = "INSERT INTO user VALUES (?,?)";
			PreparedStatement pstmt = DriverManager.getConnection(URL1+"login"+URL2,userName,passWord).prepareStatement(queryString);
			pstmt.setInt(1,Integer.parseInt(arr[1]));
			pstmt.setString(2,arr[2]);
			System.out.println(pstmt.executeUpdate());
			pstmt = DriverManager.getConnection(URL1+"student"+URL2,userName,passWord).prepareStatement(queryString);
			pstmt.setInt(1,Integer.parseInt(arr[1]));
			pstmt.setString(2,arr[0]);
			System.out.println(pstmt.executeUpdate());
			pstmt = DriverManager.getConnection(URL1+"studentNumber"+URL2,userName,passWord).prepareStatement(queryString);
			pstmt.setInt(1,Integer.parseInt(arr[1]));
			pstmt.setInt(2,Integer.parseInt(arr[3]));
			System.out.println(pstmt.executeUpdate());
			this.objectOutputStream.writeObject(quit);
		} catch (IOException | ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void reFile(String dataName) {
		try {
			String arr[] = dataName.split("-");
			Class.forName("com.mysql.cj.jdbc.Driver");
			String queryString = null;
			if(arr[0].equals("login")) {
				queryString = "update user set password=? where id=?";
			}else if(arr[0].equals("student")) {
				queryString = "update user set name=? where id=?";
			}else if(arr[0].equals("studentNumber")){
				queryString = "update user set number=? where id=?";
			}else if(arr[0].equals("studentDepartment")){
				queryString = "update user set department=? where id=?";
			}

			PreparedStatement pstmt =  DriverManager.getConnection(URL1+arr[0]+URL2,userName,passWord).prepareStatement(queryString);
			pstmt.setString(1,arr[2]);
			pstmt.setInt(2,Integer.parseInt(arr[1]));
			pstmt.executeUpdate();
			this.objectOutputStream.writeObject(quit);
		} catch (SQLException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}


	}

	private void reBasket(String dataName) {
		try {
			String row1 = dataName.substring(0,dataName.lastIndexOf("-"));
			String data = dataName.substring(dataName.lastIndexOf("-")+1);
			int row = Integer.parseInt(row1);
			String msg;
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(new File(data)));
			Vector<String> listdata = new Vector<String>();
			int a;
			for(a=0;(msg=br.readLine())!=null;a++) {
				if(row!=a) {
					listdata.add(msg);
				}
			}
			FileWriter fw = new FileWriter(new File(data),false);
			for(int c=0;a-1>c;c++){
				fw.write(listdata.get(c)+"\n");
			}
			fw.close();
			this.objectOutputStream.writeObject(quit);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	private void writeBasketFile(String className, String dataName) {
		try {
			String arr[]=dataName.split("_");
			File file = new File(arr[0]);
			Scanner sc = new Scanner(file);
			boolean isWrite = false;
			while(sc.hasNext()) {
				if(sc.next().contains(arr[1])) {
					isWrite=true;
				}
			}
			if(isWrite==false) {
				FileWriter fw = new FileWriter(file,true);
				System.out.println("0"+" "+arr[1]+" "+arr[2]+" "+arr[3]+" "+arr[4]);
				fw.write("0"+" "+arr[1]+" "+arr[2]+" "+arr[3]+" "+arr[4]+"\n");
				fw.close();
			}
			this.objectOutputStream.writeObject(quit);
			this.objectOutputStream.writeObject(isWrite);
			this.objectOutputStream.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void getFile(String className, String dataName) {
		try {
			Scanner sc = new Scanner(new File(dataName));
			Vector<ELecture> basket = new Vector<ELecture>();
			while(sc.hasNext()) {
				ELecture eLecture = new ELecture();
				eLecture.read(sc);
				basket.add(eLecture);
			}
			this.objectOutputStream.writeObject(quit);
			this.objectOutputStream.writeObject(basket);
			this.objectOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void runEDirectory(String className, String dataName) {
		try {
			Class cls = Class.forName(className);		//클래스를 생성한다.
			Method method = cls.getMethod("getItems",String.class);	//클래스 안의 메소드에 접근한다.
			Vector<EDirectory> length = (Vector) method.invoke(cls.newInstance(),dataName);//메소드를 실행시켜 결과값을 벡터에 넣는다.
			this.objectOutputStream.writeObject(quit);
			this.objectOutputStream.writeObject(length);//직렬화된 데이터를 보낸다.
			this.objectOutputStream.flush();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void getStudentName(String dataName) {
		try {
			String name=null;
			String number = null;
			String department = null;
			Class.forName("com.mysql.cj.jdbc.Driver");
			String queryString = "SELECT * FROM user";
			ResultSet resultSet = DriverManager.getConnection(URL1+"student"+URL2,userName,passWord).createStatement().executeQuery(queryString);
			while(resultSet.next()) {
				if(resultSet.getString("id").equals(dataName)) {
					name = resultSet.getString("name");
				}
			}
			resultSet = DriverManager.getConnection(URL1+"studentNumber"+URL2,userName,passWord).createStatement().executeQuery(queryString);
			while(resultSet.next()) {
				if(resultSet.getString("id").equals(dataName)) {
					number = resultSet.getString("number");
				}
			}
			resultSet = DriverManager.getConnection(URL1+"studentDepartment"+URL2,userName,passWord).createStatement().executeQuery(queryString);
			while(resultSet.next()) {
				if(resultSet.getString("id").equals(dataName)) {
					department = resultSet.getString("department");
				}
			}
			this.objectOutputStream.writeObject(quit);
			this.objectOutputStream.writeObject(name+","+number+","+department);
			this.objectOutputStream.flush();
		} catch (IOException | ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void runLogin(String className, String dataName) {
		try {
			AES256Util util = new AES256Util();
			String decoded = util.decrypt(dataName, "12345678987654321");
			String userId = decoded.substring(0,decoded.lastIndexOf("+"));
			String password = decoded.substring(decoded.lastIndexOf("+")+1);
			Class cls = Class.forName(className);		//클래스를 생성한다.
			Method method = cls.getMethod("authenticate",String.class,String.class);	//클래스 안의 메소드에 접근한다.
			boolean length = (boolean) method.invoke(cls.newInstance(),userId,password);//메소드를 실행시켜 결과값을 벡터에 넣는다.
			this.objectOutputStream.writeObject(quit);
			this.objectOutputStream.writeObject(length);//직렬화된 데이터를 보낸다.
			this.objectOutputStream.flush();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}

	}

	private void runELecture(String className, String dataName) {
		try {
			Class cls = Class.forName(className);		//클래스를 생성한다.
			Method method = cls.getMethod("getItems",String.class);	//클래스 안의 메소드에 접근한다.
			Vector<ELecture> length = (Vector) method.invoke(cls.newInstance(),dataName);//메소드를 실행시켜 결과값을 벡터에 넣는다.
			this.objectOutputStream.writeObject(quit);
			this.objectOutputStream.writeObject(length);//직렬화된 데이터를 보낸다.
			this.objectOutputStream.flush();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
