package common_interface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import database.*;
import common_interface.*;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class display_user {

	JFrame f1;


	public void display_user(String username)
	{

		f1=new JFrame(username);
		f1.setLayout(null);
		f1.setVisible(true);
		f1.setSize(400, 400);

		JLabel l2=new JLabel("User details ",JLabel.CENTER);
		l2.setFont(new Font("Brush Script M", Font.BOLD, 15));
		add_compo(f1, l2, 0, 05, 400, 20);

		JLabel l1=new JLabel(new ImageIcon(".\\dp\\"+username+".jpg"),JLabel.CENTER);
		add_compo(f1, l1, 130, 30, 150, 150);

		Map	user_detail=JDBC.user_detail(username);

		JLabel l3=new JLabel("Name");
		l3.setFont(new Font("Brush Script M", Font.BOLD, 12));
		add_compo(f1, l3, 100,200, 100, 20);

		JLabel l7=new JLabel(user_detail.get("name").toString());
		l7.setFont(new Font("Brush Script M", Font.BOLD, 12));
		add_compo(f1, l7, 200,200, 100, 20);

		JLabel l4=new JLabel("Username");
		l4.setFont(new Font("Brush Script M", Font.BOLD, 12));
		add_compo(f1, l4, 100,230, 100, 20);

		JLabel l8=new JLabel(username);
		l8.setFont(new Font("Brush Script M", Font.BOLD, 12));
		add_compo(f1, l8, 200,230, 100, 20);

		JLabel l5=new JLabel("Phone no");
		l5.setFont(new Font("Brush Script M", Font.BOLD, 12));
		add_compo(f1, l5, 100,260, 100, 20);

		JLabel l9=new JLabel(user_detail.get("phone").toString());
		l9.setFont(new Font("Brush Script M", Font.BOLD, 12));
		add_compo(f1, l9, 200,260, 100, 20);

		JLabel l6=new JLabel("Email-id");
		l6.setFont(new Font("Brush Script M", Font.BOLD, 12));
		add_compo(f1, l6, 100,290, 100, 20);

		JLabel l11=new JLabel(user_detail.get("email").toString());
		l11.setFont(new Font("Brush Script M", Font.BOLD, 12));
		add_compo(f1, l11, 200,290, 200, 20);

		JButton b1=new JButton("Exit");
		add_compo(f1, b1, 70,320, 250, 25);

		JLabel lim=new JLabel(new ImageIcon(".\\images\\wel.jpg"));
		add_compo(f1, lim, 0,-20, 400, 400);

		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}});
	}
	
	public static void add_compo(Container c,Component p,int x,int y,int w,int h)
	{
		p.setBounds(x,y,w,h);
		c.add(p);
	}


	public static void main(String[] args) 
	{
		display_user a=new display_user();
		a.display_user("asd");
	}

}
