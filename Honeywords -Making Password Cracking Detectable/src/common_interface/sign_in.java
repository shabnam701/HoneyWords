package common_interface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import database.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;




public class sign_in implements Runnable {

	JFrame f1;
	JLabel l1,l2,l3;
	String username,password = null;
	JTextField t1;
	private final JLabel t3=new JLabel();
	JPasswordField p1;
	JButton b1,b2; 
	static String data;
	Thread t = null;  
	int hours=0, minutes=0, seconds=0;  
	String timeString = "";  

	private final	JPanel jp=new JPanel();

	public  sign_in(String user) {
		
		JDBC.ConnectDatabase();

		f1=new JFrame("Sign in");
		f1.setLayout(null);
		f1.setVisible(true);
		f1.setSize(500, 550);

		t1=new JTextField(user);
		t1.setBounds(110, 118, 247, 50);
		t1.setFont(new Font("Serif",Font.BOLD,24));
		f1.add(t1);

		p1=new JPasswordField();
		p1.setBounds(110, 185, 247, 50);
		f1.add(p1);

		b1=new JButton(new ImageIcon("images/button.jpg"));
		b1.setBounds(24, 255, 332, 45);
		f1.add(b1);

		b2=new JButton(new ImageIcon("images/signup.jpg"));
		b2.setBounds(24, 305, 332, 45);
		f1.add(b2);

		JButton b3 = new JButton(new ImageIcon("images/near.jpg"));
		b3.setBounds(24, 355, 332, 45);
		f1.add(b3);

		l1=new JLabel(" Name: ");
		l1.setBounds(20, 130, 100, 25);
		l1.setFont(new Font("Serif",Font.BOLD,20));
		l1.setForeground(Color.black);
		f1.add(l1);
		
		l3=new JLabel("Password:");
		l3.setBounds(20, 200, 100, 25);
		l3.setFont(new Font("Serif",Font.BOLD,20));
		l3.setForeground(Color.black);
		f1.add(l3);

		l1=new JLabel(new ImageIcon("images/a.jpg"));
		l1.setBounds(0,0,500,500);
		f1.add(l1);

		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){

				String user=t1.getText(),password=p1.getText();
				if(!user.equals("")&&!password.equals(""))
				{
					if(JDBC.containsIP(user))
					{
						String passwordConvertedToHash = Honeyword.hashing(password);
						if(Honeyword.checkPasswordIsPotentialPassword(passwordConvertedToHash,user))
						{
							if(Honeyword.isPasswordOrHoneyword(passwordConvertedToHash,user))
							{
								f1.setVisible(false);
								new welcome(user);
							}
							else
							{
								//entered password is a honeyword
								String ip = JOptionPane.showInputDialog("Enter the IP address of machine you registered");
								if(JDBC.checkIPInDatabase(user,ip))
								{
									f1.setVisible(false);
									new welcome(user);
								}
								else
								{
									JDBC.resetPasswordNextTime(user);
									JOptionPane.showMessageDialog(null, "Failed to authenticate\n Reset to password to use next");
								}

							}
						}
						else
						{
							Component fram = null;
							JOptionPane.showMessageDialog(fram,"username/password did't match"," ",JOptionPane.WARNING_MESSAGE);
						}
					}
					else
						JOptionPane.showMessageDialog(null, "You have to reset your password");
				}
				else
				{
					Component fram = null;
					JOptionPane.showMessageDialog(fram,"username/password fields can't be blank"," ",JOptionPane.WARNING_MESSAGE);
				}
			}});
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f1.setVisible(false);
				new signup();
			}});
		
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				String name = JOptionPane.showInputDialog("Enter your name");
				String username = JOptionPane.showInputDialog("Enter your username");
				if(JDBC.checkNameUsername(name,username))
				{
					Honeyword.ResetPassword(username);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "name/username is not correct");
				}
				
			}});


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

	public static void main(String[] a)
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
		new sign_in("");
		//	data="asd";
		//Service_provider.insert_text();
	}

}
