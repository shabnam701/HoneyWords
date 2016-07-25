package common_interface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream.GetField;
import java.net.ServerSocket;
import java.net.Socket;
import database.*;

import javax.crypto.CipherOutputStream;
import javax.swing.*;

public class Admin {

	static DefaultListModel model2=new DefaultListModel();
	static JTextArea t2=new JTextArea();
	public 	static JList t1;
	static ServerSocket server;
	static Socket client;
	static DataInputStream in;
	static DataOutputStream out;
	static int count=0;
	static JFrame f1;
	static Admin a=new Admin();

	public void initi() {

		JDBC.ConnectDatabase();
		f1=new JFrame("Admin::Honeywords: Making Password-Cracking Detectable");
		f1.setVisible(true);
		f1.setLayout(null);
		f1.setSize(500, 500);

		JLabel l2=new JLabel("Honeywords: Making Password",JLabel.CENTER);
		l2.setFont(new Font("Brush Script M", Font.BOLD, 23));
		add_compo(f1, l2, 0, 10, 500, 25);

		JLabel l3=new JLabel("Cracking Detectable",JLabel.CENTER);
		l3.setFont(new Font("Brush Script M", Font.BOLD, 22));
		add_compo(f1, l3, 0, 35, 500, 25);

		JLabel l4=new JLabel("Admin",JLabel.CENTER);
		l4.setFont(new Font("Brush Script M", Font.BOLD, 18));
		add_compo(f1, l4, 0, 60, 500, 40);

		JLabel l5=new JLabel("Users");
		l5.setFont(new Font("Brush Script M",Font.BOLD, 14));
		add_compo(f1, l5, 30, 100, 100, 20);

		JLabel l6=new JLabel("Status history");
		l6.setFont(new Font("Brush Script M",Font.BOLD, 14));
		add_compo(f1, l6, 250, 100, 100, 20);

		t1=new JList();
		t1.setFont(new Font("Brush Script M",Font.BOLD,15));
		JScrollPane s1=new JScrollPane(t1);
		add_compo(f1,s1,20,130,200,250);
		t1.setBorder(BorderFactory.createEtchedBorder(new Color(153,204,255),new Color(153,204,255)));


		//t2.setFont(new Font("Brush Script M",Font.BOLD,15));
		t2.setLineWrap(true);
		JScrollPane s2=new JScrollPane(t2);
		t2.setEditable(false);
		t2.setBorder(BorderFactory.createEtchedBorder(new Color(153,204,255),new Color(153,204,255)));
		add_compo(f1,s2,250,130,200,250);

		JButton b1=new JButton("User detail");
		b1.setToolTipText("select a user in user list to view details");
		//add_compo(f1, b1, 40, 400, 150, 30);

		JButton b2=new JButton("exit");
		add_compo(f1, b2, 300, 400, 100, 30);

		JLabel l1=new JLabel(new ImageIcon("images/a.jpg"));
		l1.setBounds(0, 0, 500, 500);
		f1.add(l1);



		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}});

		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int index = t1.getSelectedIndex();
				String s = (String) t1.getSelectedValue();
				System.out.println("Value Selected: " + s);
				display_user a=new display_user();
				a.display_user(s);
			}});

		model2=JDBC.get_users();
		t1.setModel(model2);

		Runnable rn = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					ServerSocket serer = new ServerSocket(enums.ports.admin.get_port());
					while(true)
					{
						System.out.println("Server waiting @ "+serer.getLocalPort());
						Socket client = serer.accept();
						DataOutputStream out = new DataOutputStream(client.getOutputStream());
						DataInputStream in = new DataInputStream(client.getInputStream());
						String type = in.readUTF();
						String uname = "", msg = "";
						if(type.equals("register"))
						{
							uname = in.readUTF();
							msg = in.readUTF();
							model2=JDBC.get_users();
							t1.setModel(model2);
							t2.setText("new user registered\n"+uname+" "+msg+"\n------------"+t2.getText());
						}
						else
						{
							uname = in.readUTF();
							t2.setText("Found some attacker trying to login\nusername : "+uname+"\npassword has been resetted"+"\n------------"+t2.getText());
						}
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		};
		new Thread(rn).start();
	}

	public static void add_compo(Container c,Component p,int x,int y,int w,int h)
	{
		p.setBounds(x,y,w,h);
		c.add(p);
	}

	public static void main(String[] args) throws Exception
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
		a.initi();

	}

}
