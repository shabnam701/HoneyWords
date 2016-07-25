package common_interface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.net.Inet4Address;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import database.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;



public class signup implements Runnable{
	JFrame f1;
	JLabel l1,l2,l10;
	private final JLabel t3=new JLabel();
	Thread t = null;  
	JTextField n,un,ei,ph;
	JPasswordField p,cp;
	int hours=0, minutes=0, seconds=0;  
	String timeString = ""; 
	boolean name_sts=false,username_sts=false,pass_sts=false,phone_sts=false,email_id_sts=false,photo_sts=false;

	public signup() 
	{
		JDBC.ConnectDatabase();
		f1=new JFrame("Sign up");
		f1.setLayout(null);
		f1.setVisible(true);
		f1.setBounds(50,50,500, 550);

		l1=new JLabel("Honey Words: Password Detection");
		l1.setBounds(0, 28, 350, 25);
		l1.setFont(new Font("Serif",Font.BOLD,20));
		l1.setForeground(Color.black);
		f1.add(l1);

		JLabel l3=new JLabel("Registeration form");
		l3.setForeground(Color.black);
		l3.setFont(new Font("Serif",Font.BOLD,17));
		add_compo(f1, l3, 100, 70, 200, 25);

		JLabel l2=new JLabel("Name");
		l2.setForeground(Color.black);
		l2.setFont(new Font("Serif",Font.BOLD,14));
		add_compo(f1, l2, 10, 120, 100, 25);

		n=new JTextField();
		add_compo(f1,n, 140, 120, 190, 25);



		JLabel l4=new JLabel("Username");
		l4.setForeground(Color.black);
		l4.setFont(new Font("Serif",Font.BOLD,14));
		add_compo(f1, l4, 10, 160, 100, 25);

		un=new JTextField();
		add_compo(f1,un, 140, 160, 190, 25);

		JLabel l5=new JLabel("Password");
		l5.setForeground(Color.black);
		l5.setFont(new Font("Serif",Font.BOLD,14));
		add_compo(f1, l5, 10, 200, 100, 25);

		p=new JPasswordField();
		add_compo(f1,p, 140, 200, 190, 25);

		JLabel l6=new JLabel("Confirm  Password");
		l6.setForeground(Color.black);
		l6.setFont(new Font("Serif",Font.BOLD,14));
		add_compo(f1, l6, 10, 240, 120, 25);

		cp=new JPasswordField();
		add_compo(f1,cp, 140, 240, 190, 25);

		JLabel l7=new JLabel("Phone");
		l7.setForeground(Color.black);
		l7.setFont(new Font("Serif",Font.BOLD,14));
		add_compo(f1, l7, 10, 280, 100, 25);

		ph=new JTextField();
		add_compo(f1,ph, 140, 280, 190, 25);

		JLabel l8=new JLabel("Email id");
		l8.setForeground(Color.black);
		l8.setFont(new Font("Serif",Font.BOLD,14));
		add_compo(f1, l8, 10, 320, 100, 25);

		ei=new JTextField();
		add_compo(f1,ei, 140, 320, 190, 25);

		JLabel l9=new JLabel("Photo");
		l9.setForeground(Color.black);
		l9.setFont(new Font("Serif",Font.BOLD,14));
		//add_compo(f1, l9, 10, 360, 100, 25);

		JButton photo=new JButton("browse");
		//add_compo(f1,photo, 140, 360, 100, 25);

		l10=new JLabel("");
		l10.setForeground(Color.black);
		add_compo(f1,l10, 20, 390, 500, 25);

		JButton b1=new JButton("Sign up");
		add_compo(f1,b1, 40, 430, 100, 25);

		JButton b2=new JButton("Clear");
		add_compo(f1,b2, 180, 430, 100, 25);

		l1=new JLabel(new ImageIcon("images/a.jpg"));
		l1.setBounds(0,0,500,550);
		f1.add(l1);



		photo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				JFileChooser fc=new JFileChooser("");
				int option = fc.showOpenDialog(null);
				Component fram=null;
				if(option == JFileChooser.APPROVE_OPTION)
				{
					try{
						int b;
						String msg="";
						String sf=fc.getSelectedFile().getAbsolutePath();
						String extension = sf.substring(sf.lastIndexOf('.'));
						if(extension.equals(".jpg")||extension.equals(".png")||extension.equals(".jpeg"))
						{
							l10.setText(sf);
							photo_sts=true;
						}
						else
							JOptionPane.showMessageDialog(fram,"select png/jpg file"," ",JOptionPane.WARNING_MESSAGE);
					}
					catch(Exception e1){

						JOptionPane.showMessageDialog(fram,"reload file again"," ",JOptionPane.WARNING_MESSAGE);
					}
				}
			}});


		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				n.setText(null);un.setText(null);p.setText(null);cp.setText(null);ei.setText(null);l10.setText(null);ph.setText(null);
				name_sts=false;
				username_sts=false;pass_sts=false;phone_sts=false;email_id_sts=false;
			}});
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				Component fram = null;
				if(name_sts&&username_sts&&phone_sts&&email_id_sts)
				{
					if(p.getText().equals(cp.getText()))
					{

						try {
							Honeyword.generatePotentialPassword(p.getText(),un.getText());
							JDBC.register_user(n.getText(),un.getText(),p.getText(),ph.getText(),ei.getText());
							ImageResize.image_re(un.getText(), l10.getText());
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						f1.setVisible(false);
						try
						{
							StatusToAdmin.sendStatus(un.getText(),"register","Registered sucessfully");
							JOptionPane.showMessageDialog(fram,"user registered sucessfully\n Your IP Address ="+Inet4Address.getLocalHost().getHostAddress().toString()+"\n" +"Username="+un.getText()+"\nRemember username for resetting password"," ",JOptionPane.WARNING_MESSAGE);
						}
						catch (Exception e2) {
							// TODO: handle exception
							e2.printStackTrace();
						}
						new sign_in(un.getText());						
					}
					else
						JOptionPane.showMessageDialog(fram,"password/conform password did't match"," ",JOptionPane.WARNING_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(fram,"fill mandatory fields"," ",JOptionPane.WARNING_MESSAGE);

			}});

		t = new Thread( this );  
		t.start();  	

		FocusAdapter fl = new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent fe) {
				super.focusGained(fe);
				((JTextComponent) fe.getSource()).setBackground(new Color(175,238,238));
			}

			@Override
			public void focusLost(FocusEvent fe) {
				super.focusLost(fe);
				if (fe.getSource().equals(n)) {
					validationForname(n);}
				else if (fe.getSource().equals(un)) {
					validateUser(un);}
				else if (fe.getSource().equals(ei)) {
					validationForEmail(ei);}
				else if (fe.getSource().equals(ph)) {
					validationForNumber(ph);}
			}};

			n.addFocusListener(fl);
			un.addFocusListener(fl);
			ei.addFocusListener(fl);
			ph.addFocusListener(fl);

	}
	public void validationForNumber(JTextComponent comp) {
		String text = comp.getText();
		if (text.matches("[0-9]{10}$")) {
			setGreen(comp);
			phone_sts=true;
		} else {
			comp.setText(null);
			setRed(comp);
		}
	}

	public static void add_compo(Container c,Component p,int x,int y,int w,int h)
	{
		p.setBounds(x,y,w,h);
		c.add(p);
	}

	public void validationForEmail(JTextComponent comp) {
		String text = comp.getText();
		if (text.matches("[^@]+@([^.]+\\.)+[^.]+")) {
			setGreen(comp);
			//comp.setBackground(Color.GREEN);
			email_id_sts=true;
		} 
		else 
		{
			setRed(comp);
		}
	}
	public void validateUser(JTextComponent comp) {
		String u_name = comp.getText();
		if (JDBC.user_available(u_name)) {
			setGreen(comp);
			//comp.setBackground(Color.GREEN);
			username_sts= true;
		} 
		else {
			JOptionPane.showMessageDialog(f1,"username Exists"," ",JOptionPane.INFORMATION_MESSAGE);
			comp.setText(null);
			setRed(comp);

		}
	}
	private void validationForname(JTextField comp) {
		// TODO Auto-generated method stub
		String text = comp.getText();
		if (text.matches("[A-Za-z][A-Za-z]*")) {
			setGreen(comp);
			name_sts=true;
		} 
		else 
		{
			setRed(comp);
			Component fram = null;
			JOptionPane.showMessageDialog(fram,"can't be blank/enter number in name column"," ",JOptionPane.WARNING_MESSAGE);

		}
	}
	private void setRed(JTextComponent comp) {
		comp.setBackground(Color.RED);
	}

	private void setGreen(JTextComponent comp) {
		comp.setBackground(Color.GREEN);

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

	public static void main(String[] args) {
		new signup();

	}

}