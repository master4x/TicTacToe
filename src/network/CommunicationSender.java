package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * @author Leon Kelle
 * @class AGB\AH911
 * @project TicTacToe
 */

public class CommunicationSender implements Runnable
{

	private DatagramSocket networkSocket;
	private InetAddress hostAdress; // TODO move static
	private int port;
	private static volatile CommunicationSender instance; // used for singleton

	private CommunicationSender(DatagramSocket networkSocket, InetAddress hostAdress, int port)
	{
		this.networkSocket = networkSocket;
		this.hostAdress = hostAdress;
		this.port = port;
	}

	// TODO deleteme
	private CommunicationSender()
	{

	}

	/*
	 * Singleton
	 */
	public static CommunicationSender getInstance(DatagramSocket networkSocket, InetAddress hostAdress, int port)
	{
		if (instance == null)
		{
			synchronized (CommunicationSender.class)
			{
				if (instance == null)
				{
					instance = new CommunicationSender(networkSocket, hostAdress, port);
				}
			}
		}
		return instance;
	}

	// TODO deleteme
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

	public String convertIntArrayToString(int[][] arr)
	{
		ArrayList<String> buffer = new ArrayList<String>();

		for (int n = 0; n < 3; n++)
		{
			for (int m = 0; m < 3; m++)
			{
				buffer.add(Integer.toString(arr[n][m]));
			}
		}

		String result = new String();

		for (String number : buffer)
		{
			result += number + ",";
		}

		return result;
	}

	public void sendMessage(String message)
	{
		System.out.println("SEND: " + message); // TODO TEST

		byte[] messageBytes = message.getBytes();

		try
		{
			networkSocket.send(new DatagramPacket(messageBytes, messageBytes.length, hostAdress, port));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
