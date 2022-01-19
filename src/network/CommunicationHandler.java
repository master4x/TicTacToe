package network;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @author Leon Kelle
 * @class AGB\AH911
 * @project TicTacToe
 */

public class CommunicationHandler
{
	private DatagramSocket networkSocket; // TODO move static + down
	private int port;
	
	public void startThreads()
	{
		// update datagramSocket
		try
		{
			networkSocket = new DatagramSocket(port);
		} catch (SocketException e)
		{
			e.printStackTrace();
		}

		// start receiver thread
		Thread receiverThread = new Thread(new CommunicationReceiver(networkSocket));
		receiverThread.start();

		// start sender thread with singleton
		CommunicationSender senderInstance = CommunicationSender.getInstance();
		Thread senderThread = new Thread(senderInstance);
		senderThread.start();
	}
}
