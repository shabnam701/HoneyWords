package database;

import java.awt.Component;
import java.awt.List;
import java.io.File;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketImpl;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import common_interface.Honeyword;

public class JDBC {

	public static void ConnectDatabase()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","system");
			System.out.println("Connected to Database...");
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

	static Connection con;

	static String query;

	public static boolean authenticate(String uname, String pass) {
		query =
				"select NAME from Honeyword_user_data where USERNAME=? AND PASSWORD=?";
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1,uname);
			stmt.setString(2,pass);
			//stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				return true;
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e ) {
			e.printStackTrace();
			return false;
		} 
		return false;
	}

	public static boolean user_available(String uname) {
		int count = 0;

		query = "SELECT count(USERNAME) FROM Honeyword_user_data where USERNAME=?";

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1,uname);
			//stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery();

			while(rs.next())
			{

				count=rs.getInt(1);
			}
			rs.close();
			stmt.close();
		}

		catch (SQLException e ) {
			e.printStackTrace();
			return true;
		} 
		if(count==0)return true;
		else		
			return false;

	}

	public static void register_user(String name, String username, String password,
			String phone_no, String email_id) throws Exception{

		String update_string = "insert into  Honeyword_user_data values (?,?,?,?,?,?)";
		Component fram = null;
		try
		{
			String ip=Inet4Address.getLocalHost().getHostAddress().toString();
			PreparedStatement stmt = con.prepareStatement(update_string);
			stmt.setString(1,name);
			stmt.setString(2, username);
			stmt.setString(3,password);
			stmt.setString(4, phone_no);
			stmt.setString(5,email_id);
			stmt.setString(6,ip);
			stmt.executeUpdate();
			System.out.println("updated  successfully");
		} catch (SQLException e) {
			JFrame f1 = null;
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(f1,"Error in registering user"," ",JOptionPane.WARNING_MESSAGE);

		}

	}


	public static Map user_detail(String username) {
		HashMap<String, String> hm = new HashMap<String, String>();
		query = "SELECT name,username,phone,id FROM Honeyword_user_data where USERNAME=?";

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1,username);
			//stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery();

			while(rs.next())
			{
				hm.put("name", rs.getString(1));
				hm.put("username", rs.getString(2));
				hm.put("phone", rs.getString(3));
				hm.put("email", rs.getString(4));
			}
			rs.close();
			stmt.close();
		}

		catch (SQLException e ) {
			e.printStackTrace();

		} 

		return hm;
	}

	public static DefaultListModel get_users() {

		query = "SELECT count(username) FROM Honeyword_user_data";
		int count=0;

		PreparedStatement stmt;
		ResultSet rs;
		try {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			while(rs.next())
			{
				count=rs.getInt(1);
			}
			rs.close();
			stmt.close();

		}
		catch (SQLException e ) {
			e.printStackTrace();
		} 
		DefaultListModel model2=new DefaultListModel();
		if(count!=0)
		{
			query = "SELECT username FROM Honeyword_user_data";

			try {
				stmt = con.prepareStatement(query);
				rs = stmt.executeQuery();
				while(rs.next())
				{
					model2.addElement(rs.getString(1));
				}
				rs.close();
				stmt.close();
			}
			catch (SQLException e ) {
				e.printStackTrace();
			} 
			return model2;
		}
		else
		{

			return model2;
		}

	}

	public static void clearData() {
		// TODO Auto-generated method stub
		Honeyword.ClearHoneywords();
		query = "delete FROM Honeyword_user_data";

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.executeQuery();
			stmt.close();
			System.out.println("table deleted sucessfully");
		}

		catch (SQLException e ) {
			e.printStackTrace();

		} 
	}

	public static boolean checkIPInDatabase(String user, String ip) {
		// TODO Auto-generated method stub
		int count = 0;
		query =
				"select count(*) from Honeyword_user_data where USERNAME=? AND ip=?";
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1,user);
			stmt.setString(2,ip);
			//stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery();

			while(rs.next())
			{
				count = rs.getInt(1);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e ) {
			e.printStackTrace();
			return false;
		} 
		if(count ==1)
			return true;
		else
			return false;
	}

	public static boolean containsIP(String user) {
		// TODO Auto-generated method stub
		String ip = "";
		query =
				"select ip from Honeyword_user_data where USERNAME=?";
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1,user);
			ResultSet rs = stmt.executeQuery();

			while(rs.next())
			{
				ip = rs.getString(1);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e ) {
			e.printStackTrace();
			return false;
		} 
		if(!ip.equals("reset"))
			return true;
		else
			return false;

	}

	public static void resetPasswordNextTime(String user) {
		// TODO Auto-generated method stub

		query = "update Honeyword_user_data set ip=? where username=?";

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1,"reset");
			stmt.setString(2,user);
			//stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery();

			rs.close();
			stmt.close();
		}

		catch (SQLException e ) {
			e.printStackTrace();

		} 
	}

	public static boolean checkNameUsername(String name, String username) {
		// TODO Auto-generated method stub
		int ip = 0;
		query =
				"select count(*) from Honeyword_user_data where USERNAME=? AND name=?";
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1,username);
			stmt.setString(2,name);
			ResultSet rs = stmt.executeQuery();

			while(rs.next())
			{
				ip = rs.getInt(1);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e ) {
			e.printStackTrace();
			return false;
		} 
		if(ip==1)
			return true;
		else
			return false;

	}

	public static void updateIP(String user) throws Exception{
		// TODO Auto-generated method stub
		query = "update Honeyword_user_data set ip=? where username=?";

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1,Inet4Address.getLocalHost().getHostAddress().toString());
			stmt.setString(2,user);
			//stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery();

			rs.close();
			stmt.close();
		}

		catch (SQLException e ) {
			e.printStackTrace();

		} 
	}
}