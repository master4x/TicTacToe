package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

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
			InetAddress senderAdresse = datagramPacket.getAddress();
			String sender = senderAdresse.getHostAddress().trim();
			String message = new String(datagramPacket.getData()).trim();

			// use view to display message
			//MainWindow.textAreaChat.append(sender + ": " + message + "\n");
		}
	}
}
