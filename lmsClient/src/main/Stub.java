package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import ui.LoginFrame;

public class Stub {
	private String IP;
	private int portNumber;
	private Socket socket;
	private OutputStream outputStream;
	private PrintWriter printWriter;
	private InputStream inputStream;
	private ObjectInputStream objectInputStream;
	public Stub() {
		this.IP = Constant.IP;
		this.portNumber = Constant.PORTNUMBER;
	}
	public void initialize() {
		try {
			this.socket = new Socket(this.IP, this.portNumber); // 서버의 정보
			this.outputStream = this.socket.getOutputStream();
			this.printWriter = new PrintWriter(new OutputStreamWriter(this.outputStream));
			this.inputStream = this.socket.getInputStream();
			this.objectInputStream = new ObjectInputStream(this.inputStream);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void finalize() {
		try {
			this.printWriter.close();
			this.objectInputStream.close();
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	protected Object invoke(String line) {
		try {
			this.printWriter.println(line);
			this.printWriter.flush();
			boolean quit = (boolean)this.objectInputStream.readObject();
			if(quit) {
				finalize();
			}
			return this.objectInputStream.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return null;	
	}
	protected void invoke2(String line) {
		try {
			this.printWriter.println(line);
			this.printWriter.flush();
			boolean quit = (boolean)this.objectInputStream.readObject();
			if(quit) {
				finalize();
			}
		} catch (ClassNotFoundException | IOException e) {
		}
	}
	protected File getImage(String line) {
		try {
			this.printWriter.println(line);
			this.printWriter.flush();
			
			File file = new File("userImg.jpg");
			FileOutputStream fileOutputStream = new FileOutputStream(file);
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
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	protected void reImage2(String line, File loadFile) {
		this.printWriter.println(line);
		this.printWriter.flush();
		try {
			byte buffer[] = new byte[2048];
			File imgfile = loadFile;
			String flen = String.valueOf(imgfile.length());
			String header = "0000000000".substring(0, 10-flen.length()) + flen;
			FileInputStream fileInputStream = new FileInputStream(imgfile);
			// send header
			this.outputStream.write(header.getBytes());
			// send body
			while (fileInputStream.available() > 0) {
				int readsz = fileInputStream.read(buffer);
				this.outputStream.write(buffer, 0, readsz);
			}
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
