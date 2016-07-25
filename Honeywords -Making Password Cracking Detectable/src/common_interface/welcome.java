package common_interface;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

import utils.Utils;

import common_interface.sign_in;
import database.JDBC;




public class welcome implements Runnable {

	JFrame f1;
	JLabel l1,l2;
	private final JLabel t3=new JLabel();
	Thread t = null;  
	int hours=0, minutes=0, seconds=0;  
	String timeString = "";  
	String user_name;
	String data_time;
	public static List<Double> latitude=new ArrayList<Double>();
	public static List<Double> longitude=new ArrayList<Double>();
	public static List<Integer> width=new ArrayList<Integer>();
	public static List<Integer> height=new ArrayList<Integer>();
	public static List<Integer> zoom=new ArrayList<Integer>();
	public static List<String> place_name=new ArrayList<String>();
	public static List<String> time=new ArrayList<String>();
	public static List<String> date=new ArrayList<String>();

	public welcome(String user) {
		
	
		user_name=user;
		f1=new JFrame("Welcome");
		f1.setLayout(null);
		f1.setVisible(true);
		f1.setBounds(50,50,500, 550);
		
		l1=new JLabel("");
		l1.setBounds(80, 28, 250, 25);
		l1.setFont(new Font("Serif",Font.BOLD,20));
		l1.setForeground(Color.white);
		f1.add(l1);

		JLabel l3=new JLabel(new ImageIcon(".\\dp\\"+user+".jpg"));
		l3.setBounds(100, 100, 150, 150);
		f1.add(l3);

		l2=new JLabel("Welcome "+user);
		l2.setFont(new Font("arial black",Font.BOLD,24));
		l2.setBounds(110, 240, 280, 40);
		f1.add(l2);


		JButton b1=new JButton("Go to Main Menu");
		b1.setBounds(100, 370, 250, 40);
		f1.add(b1);
		
		t3.setBounds(5, 1, 150, 25);
		t3.setBackground(new Color(0,0,0,0));
		t3.setFont(new Font("Serif",Font.BOLD,18));
		t3.setForeground(Color.white);
		t3.setBorder(null);
		f1.add(t3);

		JButton b3=new JButton("Logout");
		b3.setBounds(100, 430, 250, 40);
		f1.add(b3);


		l1=new JLabel(new ImageIcon("images/a.jpg"));
		l1.setBounds(0,0,500,550);
		f1.add(l1);


		data_time=Utils.current_date();

		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f1.setVisible(false);

				try {
					Main_menu.display(user_name,data_time);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}});
	
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f1.setVisible(false);
				new sign_in("");
				
			}});

		t = new Thread( this );  
		t.start();  

	}
	public void run() {  
		try {  
			while (true) {  

				Calendar cal = Calendar.getInstance();  
				hours = cal.get( Calendar.HOUR_OF_DAY );  
				if ( hours > 12 ) hours -= 12;  
				minutes = cal.get( Calendar.MINUTE );  
				seconds = cal.get( Calendar.SECOND );  

				SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");  
				Date date = cal.getTime();  
				timeString = formatter.format( date );  

				t3.setText(timeString);
				t.sleep( 1000 );  // interval given in milliseconds  
			}  
		}  
		catch (Exception e) { }  
	}  

	public static void main(String[] args)
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try
		{
			UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		}
		catch (Exception ex)

		{
			System.out.println("Failed loading L&F: ");
			System.out.println(ex);
		}
		new welcome("qwe");
	}

}
