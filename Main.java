package Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
	 
	static final String DB_URL = "jdbc:mysql://localhost:3306/MySQL?useSSL=false&serverTimezone=UTC";
	static final String USER = "root";
    static final String PASS = "123456";

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		//ShowMenuUsingFile();
		//ShowMenuUsingDatabase();
	}
	
	static void ShowMenuUsingDatabase() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection c = DriverManager.getConnection(DB_URL,USER,PASS);
		System.out.println("连接成功，获取连接对象： " + c);
		Statement s = c.createStatement();
        System.out.println("获取Statement对象： " + s);
        
        ResultSet rs = c.getMetaData().getTables(null, null, "SQLHomework1", null);
        if (!rs.next()) {
        	s.executeUpdate( "CREATE TABLE SQLHomework1 " +
                    "(PRIMARY KEY idNum VARCHAR(255) not NULL, " +
                    " name VARCHAR(255), " + 
                    " gender VARCHAR(255), " +
                    " address VARCHAR(255), " +
                    " phoneNum VARCHAR(255), " +
                    " PRIMARY KEY (idNum))");
        	System.out.println("未查询到表，将建立新表");
        }
        
		while(true) {
			System.out.println("/******************************************************************************/");
			System.out.println("输入1输入信息，输入2查询信息:");
			Scanner sc = new Scanner(System.in);
			int re = sc.nextInt();
			if(re == 1) {
				InputInformToSQL(s);
			}
			else if(re == 2){
				SearchInformFromSQL(s);
			}
		}
	}
	
	static void InputInformToSQL(Statement s) throws SQLException {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入学号:");
		String idNum = sc.nextLine();
		System.out.println("请输入姓名:");
		String name = "'" + sc.nextLine() + "'";
		System.out.println("请输入性别:");
		String gender = "'" + sc.nextLine() + "'";
		System.out.println("请输入地址:");
		String address = "'" + sc.nextLine() + "'";
		System.out.println("请输入电话号码:");
		String phoneNum = sc.nextLine();
		String sql = "INSERT INTO SQLHomework1 VALUES ("+idNum+","+name+","+gender+","+address+","+phoneNum+")";
		s.executeUpdate(sql);
		System.out.println("数据已输入");
	}
	
	static void SearchInformFromSQL(Statement s) throws SQLException {
		ResultSet rs = s.executeQuery("SELECT idNum, name, gender, address, phoneNum FROM SQLHomework1");
		while(rs.next()){
			String idNum  = rs.getString("idNum");
			String name = rs.getString("name");
	        String gender = rs.getString("gender");
	        String address = rs.getString("address");
	        String phoneNum = rs.getString("phoneNum");
	         
	        System.out.println("学号为:" + idNum);
	        System.out.println("姓名为:" + name);
			System.out.println("性别为:" + gender);
			System.out.println("地址为:" + address);
			System.out.println("电话号码为:" + phoneNum);
			System.out.println("");
	         
		}
	    rs.close();
	}
	
	public static void ShowMenuUsingFile() throws IOException {
		while(true) {
			System.out.println("/******************************************************************************/");
			System.out.println("输入1输入信息，输入2查询信息:");
			Scanner sc = new Scanner(System.in);
			int re = sc.nextInt();
			if(re == 1) {
				InputInform();
			}
			else if(re == 2){
				OutputInform();
			}
		}
	}
	
	public static void InputInform() throws IOException {
		Scanner sc = new Scanner(System.in);
		File f = new File("data.txt");
		if(!f.exists()) {
			f.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(f,true));
			bw.write("\r\n");
			bw.flush();
			bw.close();
		}
		BufferedWriter out = new BufferedWriter(new FileWriter(f,true));
		System.out.println("请输入学号:");
		String idNum = sc.nextLine();
		out.write(idNum + "+");
		System.out.println("请输入姓名:");
		String name = sc.nextLine();
		out.write(name + "+");
		System.out.println("请输入性别:");
		String gender = sc.nextLine();
		out.write(gender + "+");
		System.out.println("请输入地址:");
		String address = sc.nextLine();
		out.write(address + "+");
		System.out.println("请输入电话号码:");
		String phoneNum = sc.nextLine();
		out.write(phoneNum + "\r\n");
		out.flush();
		out.close();
	}
	
	static void OutputInform() throws IOException {
		File f = new File("data.txt");
		if(!f.exists()) {
			System.out.println("不存在任何记录！");
			return;
		}
		InputStreamReader reader = new InputStreamReader(new FileInputStream(f));
		BufferedReader br = new BufferedReader(reader);
		String line = "";
		line = br.readLine();
		String[] temp = new String[5];
		while (line != null) {
			line = br.readLine();
			if(line != null) {
				temp = line.split("\\+");
				System.out.println("学号为:" + temp[0]);
				System.out.println("姓名为:" + temp[1]);
				System.out.println("性别为:" + temp[2]);
				System.out.println("地址为:" + temp[3]);
				System.out.println("电话号码为:" + temp[4]);
				System.out.println("");
			}
		}
		br.close();
	}

}
