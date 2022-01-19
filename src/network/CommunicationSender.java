package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Leon Kelle
 * @class AGB\AH911
 * @project TicTacToe
 */

public class CommunicationSender implements Runnable
{

	private static volatile CommunicationSender instance; // used for singleton

	private CommunicationSender()
	{
		// thread call constructor
	}

	/*
	 * Singleton
	 */
	public static CommunicationSender getInstance()
	{
		if (instance == null)
		{
			synchronized (CommunicationSender.class)
			{
				if (instance == null)
				{
					instance = new CommunicationSender();
				}
			}
		}
		return instance;
	}

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @param inetAddress target IP-address
	 * @param message     message to send
	 * @param port        destination port
	 */
	public void sendMessage(String message, String ipadress, int port)
	{
		// get target IP from field and convert to InetAddress
		InetAddress inetAddress = null; // initialize InetAddress
		try
		{
			inetAddress = InetAddress.getByName(ipadress);
		} catch (UnknownHostException e)
		{
			e.printStackTrace();
		}

		// convert String to bytes array
		byte[] messageBytes = message.getBytes();

		// build and send UDP package
		try
		{
			DatagramPacket packet = new DatagramPacket(messageBytes, messageBytes.length, inetAddress, port);
			DatagramSocket socket = new DatagramSocket(port); //TODO move static
					
			socket.send(packet);
			socket.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
