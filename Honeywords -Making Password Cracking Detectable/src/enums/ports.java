package enums;

public enum ports
{
	 honeychecker(5000), admin(5001);

	private int port;

	private ports(int port_no)
	{

		port = port_no;	

	}

	public int get_port()
	{

		return port;

	}

}
