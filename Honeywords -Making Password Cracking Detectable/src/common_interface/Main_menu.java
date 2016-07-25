package common_interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Font;
import common_interface.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.acl.LastOwnerException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import common_interface.*;
import database.*;
import javax.swing.*;

import database.*;
import enums.IpAddress;
import enums.ports;

import javax.swing.table.TableColumn;

import utils.Utils;


public class Main_menu extends Thread{


	public static String username;
	public static JTextArea t2;
	public static JButton b1;
	public static JFrame f1;
	private static Map<String, BigInteger> keys=new HashMap<String, BigInteger>();
	private static Map<String, String> relationship=new HashMap<String, String>();
	public static boolean temp=true;
	static JComboBox option;
	static String selectedFile = "";
	static JPanel	topPanel;
	static JTable	table;
	static JScrollPane scrollPane;
	static ServerSocket socket;
	static	Socket clientSocket;
	static DataInputStream dis ;
	static	DataOutputStream dout;


	public static void main(String[] args) throws Exception{
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
		display("qwe","qwe");
	}

	public static void add_compo(Container c,Component p,int x,int y,int w,int h)
	{
		p.setBounds(x,y,w,h);
		c.add(p);
	}

	public static void display(String user, String data_time) throws Exception {

		username=user;

		f1=new JFrame(username+" main menu");
		f1.setVisible(true);
		f1.setSize(500, 500);
		f1.setLayout(null);

		JLabel l2=new JLabel("Honeywords: Making Password",JLabel.CENTER);
		l2.setFont(new Font("Brush Script M", Font.BOLD, 23));
		add_compo(f1, l2, 0, 10, 500, 25);

		JLabel l3=new JLabel("Cracking Detectable",JLabel.CENTER);
		l3.setFont(new Font("Brush Script M", Font.BOLD, 22));
		add_compo(f1, l3, 0, 35, 500, 25);

		JLabel l4=new JLabel(username+"'s main menu",JLabel.CENTER);
		l4.setFont(new Font("Brush Script M", Font.BOLD, 18));
		add_compo(f1, l4, 0, 60, 500, 40);

		JLabel l1=new JLabel("Select your option");
		add_compo(f1,l1,30,110,200,25);

		JLabel l5=new JLabel("Status");
		add_compo(f1,l5,260,110,200,25);

		t2=new JTextArea();
		t2.setLineWrap(true);
		JScrollPane s2=new JScrollPane(t2);
		t2.setEditable(false);
		t2.setBorder(BorderFactory.createEtchedBorder(new Color(153,204,255),new Color(153,204,255)));
		add_compo(f1,s2,250,140,200,250);

		b1=new JButton("Browse");
		add_compo(f1,b1,20, 150, 200, 25);

		JButton b2=new JButton("Upload");
		add_compo(f1,b2,20, 200, 200, 25);

		JButton b3=new JButton("download");
		add_compo(f1,b3,20, 250, 200, 25);

		JButton b4=new JButton("Open download folder");
		add_compo(f1,b4,20, 300, 200, 25);

		JButton b5=new JButton("change password");
		add_compo(f1,b5,20, 350, 200, 25);

		JButton b7=new JButton("Main menu");
		add_compo(f1,b7,250, 400, 200, 25);

		JLabel img=new JLabel(new ImageIcon("images/menu_back.jpg"));
		img.setBounds(0, 0, 500, 500);
		f1.add(img);

		t2.setText("Signed in \n Date: "+data_time);

		b2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//code to upload dataset to server
				try {
					Socket client = new Socket(enums.IpAddress.honeychecker.get_ip(),enums.ports.honeychecker.get_port());
					DataOutputStream out = new DataOutputStream(client.getOutputStream());

					out.writeUTF("upload");
					File transferFile = new File(selectedFile);
					System.out.println(selectedFile);
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

				


				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}});

		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				DefaultListModel files = new DefaultListModel();
				File list_of_file[] = new File("resources").listFiles();

				for(Object f:list_of_file)
					files.addElement(f);

				final JFrame jf1 = new JFrame();
				jf1.setLayout(null);

				ImageIcon icon2= new ImageIcon("images/server_info.jpg");
				JLabel l6 = new JLabel(icon2,JLabel.CENTER);

				JLabel l1 = new JLabel("List of files:",JLabel.CENTER);
				l1.setFont(new Font("Serif",Font. BOLD,20));

				final JList ta10 = new JList();
				JScrollPane jsp = new JScrollPane(ta10,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
				ta10.setModel(files);

				final JButton b11 = new JButton("Exit");
				final JButton b12 = new JButton("download file");

				addcomponent(jf1,l1,10,20,300,20);
				addcomponent(jf1,jsp,10,50,250,180);
				addcomponent(jf1,b12,20,235,150,25);
				addcomponent(jf1,b11,180,235,80,25);
				addcomponent(jf1,l6,0,0,300,300);
				jf1.setSize(300, 300);
				jf1.setVisible(true);

				b12.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						try 
						{
							String selectedFile = ta10.getSelectedValue().toString();
							System.out.println(selectedFile);
							Socket cli = new Socket(IpAddress.honeychecker.get_ip(),ports.honeychecker.get_port());
							DataInputStream in = new DataInputStream(cli.getInputStream());
							DataOutputStream out = new DataOutputStream(cli.getOutputStream());
							out.writeUTF("download");
							out.writeUTF(selectedFile);

							int filesize=2022386; 
							int bytesRead;
							int currentTot = 0;
							String file_name = in.readUTF();
							int chunkSize = 0,fileSize = 0;
							String file_path_to_store = "DownloadFiles//"+file_name;
							byte [] bytearray  = new byte [filesize];
							InputStream is = cli.getInputStream();
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
							JOptionPane.showMessageDialog(null,"File downloaded sucessfully");
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}});

				b12.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						jf1.setVisible(false);
					}});

			}});

		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try {
					Desktop.getDesktop().open(new File("DownloadFiles"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}});

		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				JFileChooser fc=new JFileChooser("abc");
				int option = fc.showOpenDialog(null);
				if(option == JFileChooser.APPROVE_OPTION)
				{
					try{

						String File_path = fc.getSelectedFile().getAbsolutePath();
						File file = new File(File_path);
						t2.setText("Selected File info\nPath = "+File_path+"\n File name = "+file.getName()+"\n----------------------\n"+t2.getText());
						selectedFile  = file.getAbsolutePath();
					}
					catch (Exception ee)
					{
						JOptionPane.showMessageDialog(null,"Reload File Again..");
					}
				}	
			}});

		b7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f1.setVisible(false);

				new welcome(username);
			}});
		

		Main_menu obj=new Main_menu();
		obj.start();
	}


	public static String current_time()
	{
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(cal.getTime());
	}

	private static void addcomponent(Container cp,Component c, int startx, int starty, int width, int height) {
		// TODO Auto-generated method stub
		c.setBounds(startx,starty,width,height);
		cp.add(c);

	}

	public static String current_date()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}