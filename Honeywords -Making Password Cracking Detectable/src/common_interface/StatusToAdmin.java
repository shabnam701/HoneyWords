package common_interface;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import enums.IpAddress;
import enums.ports;

public class StatusToAdmin {
	
	public static void sendStatus(String uname, String type, String info)
	{
		try {
			Socket cli = new Socket(IpAddress.admin.get_ip(),ports.admin.get_port());
			DataOutputStream in = new DataOutputStream(cli.getOutputStream());
			in.writeUTF(type);
			in.writeUTF(uname);
			in.writeUTF(info);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
