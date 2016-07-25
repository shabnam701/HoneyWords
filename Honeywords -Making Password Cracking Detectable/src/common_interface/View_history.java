package common_interface;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import common_interface.*;

public class View_history extends Thread
{

	@Override
	public void run() {
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
		JPanel	topPanel;
		JTable	table;
		JScrollPane scrollPane;

		JFrame f=new JFrame();
		f.setTitle( "Traveling history " );
		f.setSize( 500, 420 );
		f.setLayout(null);
		f.setVisible(true);
		f.setBackground( Color.gray );


		JLabel travel=new JLabel("Traveling histroy",JLabel.CENTER);
		travel.setFont(new Font("Brush Script M", Font.BOLD, 18));
		add_compo(f, travel, 0, 10, 500, 25);

		topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		topPanel.setBounds(10, 50, 480, 250);
		f.add( topPanel );
		JLabel jl=null;


		String columnNames[] = {"Latitude","Longititude","Place","date","time" };

		String dataValues[][]=new String[welcome.latitude.size()][5];

		for(int i=0;i<welcome.latitude.size();i++)
		{
			dataValues[i][0]=Float.valueOf(welcome.latitude.get(i).toString()).toString();
			dataValues[i][1]=Float.valueOf(welcome.longitude.get(i).toString()).toString();
			dataValues[i][2]=welcome.place_name.get(i).toString();
			dataValues[i][3]=welcome.time.get(i).toString();
			dataValues[i][4]=welcome.date.get(i).toString();
		}

//		dataValues=reverse_array_rows(dataValues);
		table = new JTable( dataValues, columnNames );		
		scrollPane = new JScrollPane( table );
		topPanel.add( scrollPane, BorderLayout.CENTER );
		table.setEnabled(false);
		
		JTableHeader jtableHeader = table.getTableHeader();
		DefaultTableCellRenderer rend = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
		rend.setHorizontalAlignment(JLabel.CENTER);
		jtableHeader.setDefaultRenderer(rend);

		JPanel jp=new JPanel();
		jp.setLayout(null);

		jp.setBounds(0,180,500, 200);
		f.add(jp);			

		JLabel l1=new JLabel(new ImageIcon("images/s.jpg"));
		l1.setBounds(0, -20, 500, 450);
		f.add(l1);

		while(true)
		{
			jl=new JLabel(new ImageIcon("images/gng.png"));
			for(int i=-10;i<400;i++)
			{
				jl.setBounds(i,70,120,200);				
				jp.add(jl);
				try {
					Thread.currentThread().sleep(6);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			jp.removeAll();

			jl=new JLabel(new ImageIcon("images/i_back.png"));
			for(int i=400;i>-10;i--)
			{
				jl.setBounds(i,70,120,200);			
				jp.add(jl); 
				try {
					Thread.currentThread().sleep(6);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			jp.removeAll();
		}
	}
	
	public static void add_compo(Container c,Component p,int x,int y,int w,int h)
	{
		p.setBounds(x,y,w,h);
		c.add(p);
	}

	public View_history(String username) throws InterruptedException
	{


	}



	public static void main( String args[] ) throws InterruptedException
	{
		View_history mainFrame	= new View_history("asd");
		mainFrame.start();
	}
}