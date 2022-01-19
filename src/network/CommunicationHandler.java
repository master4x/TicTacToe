package network;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author Leon Kelle
 * @class AGB\AH911
 * @project TicTacToe
 */

public class CommunicationHandler
{
	private DatagramSocket networkSocket; // TODO move static + down
	private InetAddress hostAdress;
	private int port = 18911;

	public void startThreads(String ipAdress)
	{
		try
		{
			networkSocket = new DatagramSocket(port);
			hostAdress = InetAddress.getByName(ipAdress);
		} catch (SocketException | UnknownHostException e)
		{
			e.printStackTrace();
		}

		// start receiver thread
		Thread receiverThread = new Thread(new CommunicationReceiver(networkSocket));
		receiverThread.start();

		// start sender thread	
		Thread senderThread = new Thread(CommunicationSender.getInstance(networkSocket, hostAdress, port));
		senderThread.start();
	}
}
