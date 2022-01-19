package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

/**
 * @author Leon Kelle
 * @class AGB\AH911
 * @project TicTacToe
 */

public class CommunicationReceiver implements Runnable
{
	private DatagramSocket socket; //TODO move static

	public CommunicationReceiver(DatagramSocket socket)
	{
		this.socket = socket;
	}

	@Override
	public void run()
	{
		while (true)
		{
			// empty package with size of 512 byte
			DatagramPacket datagramPacket = new DatagramPacket(new byte[512], 512);

			try
			{
				datagramPacket.setAddress(InetAddress.getLocalHost());
				// wait until receiving a message
				socket.receive(datagramPacket);
			} catch (IOException e)
			{
				e.printStackTrace();
			}

			// get information about the sender	
			String data = new String(datagramPacket.getData()).trim();

			// use view to display message
			System.out.println("GOT:  " + data); //TODO TEST
			
			
			String[] strings = data.split(",");
			int[][] integers = new int[3][3];
			
			int i = 0;
			for (int n=0; n<3; n++)
			{
				for (int m=0; m<3; m++)
				{
					integers[n][m] = Integer.parseInt(strings[i]);
					i++;
				}
			}
			
			
		}
	}
}
