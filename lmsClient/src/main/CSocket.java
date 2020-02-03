package main;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import entity.EDirectory;
import entity.ELecture;

public class CSocket extends Stub{
	
	public CSocket() {
		super();
	}
	
	public void initialize() {
		super.initialize();
	}
	
	public void finalize() {
		super.finalize();
	}
	
	public Vector<ELecture> runLecture(String line) {
		initialize();
		return (Vector<ELecture>) invoke(line);
	}

	public boolean runLogin(String line) {
		initialize();
		return (boolean) invoke(line);
	}

	public String getName(String userId) {
		initialize();
		return (String) invoke("getStudent,"+userId);
	}

	public Vector<EDirectory> runDirectory(String line) {
		initialize();
		return (Vector<EDirectory>)invoke(line);
	}

	public Vector<ELecture> getFile(String line) {
		initialize();
		return (Vector)invoke(line);
	}

	public boolean writeBasketFile(String line) {
		initialize();
		return (boolean)invoke(line);
	}

	public void reBasket(String line) {
		initialize();
		invoke2(line);
	}

	public void reFile(String line) {
		initialize();
		invoke2(line);
	}

	public void signUp(String line) {
		initialize();
		invoke2(line);
	}

	public String runSearchId(String line) {
		initialize();
		return (String)invoke(line);
	}

	public String runSearchPw(String line) {
		initialize();
		return (String)invoke(line);
	}

	public File getImg(String line) {
		initialize();
		return getImage(line);
	}

	public void reImage(String line, File loadFile) {
		initialize();
		reImage2(line, loadFile);
	}

	public Object[] getTime(String line) {
		initialize();
		return (Object[])invoke(line);
	}

	public String findLecture(String line) {
		initialize();
		return (String)invoke(line);
	}

	public Vector<String> findProfessor(String line) {
		initialize();
		return (Vector<String>)invoke(line);
	}
	@SuppressWarnings("static-access")
	public String webScroll(String studentDepartment) {
		try {
			String URL=null;
			if(studentDepartment.equals("software")) {
				URL = "http://www.mju.ac.kr/user/introduction_mju/info/college/department_list.jsp?deptCd=18520&id=mjukr_020210020100"; 
				Document doc = Jsoup.connect(URL).get();
				Elements elem = doc.select(".mb30");
				String str = elem.select("tr").text(); 
				str = str.substring(str.length()-55, str.length());
				return str;
			}else if(studentDepartment.equals("digitalContents")) {
				URL = "http://www.mju.ac.kr/user/introduction_mju/info/college/department_list.jsp?deptCd=18510&id=mjukr_020210010100";
				Document doc = Jsoup.connect(URL).get();
				Elements elem = doc.select(".mb30");
				String str = elem.select("tr").text(); 
				str = str.substring(str.length()-55, str.length());
				return str;
			}else {
				JOptionPane jOptionPane = new JOptionPane();
				jOptionPane.showConfirmDialog(null,"본 학과의 수강 학점은 조회되지 않습니다.","죄송합니다!", jOptionPane.PLAIN_MESSAGE );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null; 
	}

	public void killServer() {
		initialize();
		invoke2("killServer,null");
	}
}
