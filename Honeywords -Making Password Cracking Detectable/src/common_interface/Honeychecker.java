package common_interface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import utils.Utils;


import database.JDBC;


public class Honeychecker {

	static DefaultListModel model2=new DefaultListModel();
	static JTextArea t2=new JTextArea();
	public static JFrame f1;
	static final int k = 20;
	public static Set services = new HashSet();


	public static void init()
	{

		JDBC.ConnectDatabase();
		JDBC.clearData();
		Honeyword.generateHoneywords();

		f1=new JFrame("Honeychecker::Honeywords: Making Password-Cracking Detectable");
		f1.setVisible(true);
		f1.setLayout(null);
		f1.setSize(500, 500);

		JLabel l2=new JLabel("Honeywords: Making Password",JLabel.CENTER);
		l2.setFont(new Font("Brush Script M", Font.BOLD, 20));
		add_compo(f1, l2, 0, 10, 500, 25);

		JLabel l3=new JLabel("Cracking Detectable",JLabel.CENTER);
		l3.setFont(new Font("Brush Script M", Font.BOLD, 22));
		add_compo(f1, l3, 0, 35, 500, 25);

		JLabel l4=new JLabel("Honeychecker",JLabel.CENTER);
		l4.setFont(new Font("Brush Script M", Font.BOLD, 18));
		add_compo(f1, l4, 0, 60, 500, 40);

		JLabel l6=new JLabel("Status history");
		l6.setFont(new Font("Brush Script M",Font.BOLD, 14));
		add_compo(f1, l6, 15, 100, 100, 20);

		//t2.setFont(new Font("Brush Script M",Font.BOLD,15));
		t2.setLineWrap(true);
		JScrollPane s2=new JScrollPane(t2);
		t2.setEditable(false);
		t2.setBorder(BorderFactory.createEtchedBorder(new Color(153,204,255),new Color(153,204,255)));
		add_compo(f1,s2,25,130,420,200);



		JButton exit=new JButton("Back");
		add_compo(f1, exit, 270, 400, 150, 30);

		JLabel l1=new JLabel(new ImageIcon("images/lbs_back.jpg"));
		l1.setBounds(0, 0, 500, 500);
		f1.add(l1);


		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}});

		Runnable run = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					ServerSocket serer = new ServerSocket(enums.ports.honeychecker.get_port());
					while(true)
					{
						System.out.println("Server waiting @ "+serer.getLocalPort());
						Socket client = serer.accept();
						DataOutputStream out = new DataOutputStream(client.getOutputStream());
						DataInputStream in = new DataInputStream(client.getInputStream());
						String type = in.readUTF();

						if(type.equals("upload"))
						{
							int filesize=2022386; 
							int bytesRead;
							int currentTot = 0;
							String file_name = in.readUTF();
							int chunkSize = 0,fileSize = 0;
							String file_path_to_store = "resources//"+file_name;
							t2.setText("recieved request for uploading file\nfile name : "+file_name+"\n-------------\n"+t2.getText());
							byte [] bytearray  = new byte [filesize];
							InputStream is = client.getInputStream();
							FileOutputStream fos = new FileOutputStream(file_path_to_store);
							BufferedOutputStream bos = new BufferedOutputStream(fos);
							bytesRead = is.read(bytearray,0,bytearray.length);
							currentTot = bytesRead;

							do {
								bytesRead =
										is.read(bytearray, currentTot, (bytearray.length-currentTot));
								if(bytesRead >= 0) currentTot += bytesRead;
							} while(bytesRead > -1);
							String dta = new String(bytearray).trim();
							System.out.println(dta);
							bos.write(bytearray, 0 , currentTot);
							bos.flush();

						}
						else
						{
							String fileName = in.readUTF();
							t2.setText("recieved request for downloading file\nfile name : "+fileName+"\n-------------\n"+t2.getText());
							File transferFile = new File(fileName);
							String file_name = transferFile.getName();
							System.out.println(file_name);
							out.writeUTF(file_name);
							byte [] bytearray  = new byte [(int)transferFile.length()];
							FileInputStream fin = new FileInputStream(transferFile);
							BufferedInputStream bin = new BufferedInputStream(fin);
							bin.read(bytearray,0,bytearray.length);
							OutputStream os = client.getOutputStream();
							System.out.println("Sending Files...");
							os.write(bytearray,0,bytearray.length);
							os.flush();
							System.out.println("File sent sucessfully...");
							out.close();
							client.close();
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}};
			new Thread(run).start();

	}


	public static void add_compo(Container c,Component p,int x,int y,int w,int h)
	{

		p.setBounds(x,y,w,h);
		c.add(p);

	}

	public static void main(String[] args) 
	{
		init();
	}
}
