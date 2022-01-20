package data;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class NetworkHandler
{
	private static volatile NetworkHandler instance;
	private DatagramSocket networkSocket;
	private InetAddress hostAdress;
	private int port = 18911;

	private NetworkHandler()
	{
	}

	/*
	 * Singleton
	 */
	public static NetworkHandler getInstance()
	{
		if (instance == null)
		{
			synchronized (NetworkHandler.class)
			{
				if (instance == null)
				{
					instance = new NetworkHandler();
				}
			}
		}
		return instance;
	}

	public void newCommunication(String ipAdress)
	{
		try
		{
			networkSocket = new DatagramSocket(port);
			hostAdress = InetAddress.getByName(ipAdress);
		} catch (SocketException | UnknownHostException e)
		{
			e.printStackTrace();
		}
	}

	public void sendArray(int[][] gameField)
	{
		String message = convertIntArrayToString(gameField);

		byte[] messageBytes = message.getBytes();

		try
		{
			networkSocket.send(new DatagramPacket(messageBytes, messageBytes.length, hostAdress, port));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public int[][] receiveArray()
	{
		int[][] gameField;

		while (true)
		{
			DatagramPacket datagramPacket = new DatagramPacket(new byte[512], 512);

			try
			{
				datagramPacket.setAddress(InetAddress.getLocalHost());

				networkSocket.receive(datagramPacket);

				String data = new String(datagramPacket.getData()).trim();
				System.out.println(data);
				gameField = convertStringToIntArray(data);

				break;
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return gameField;
	}

	private String convertIntArrayToString(int[][] arr)
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

	private int[][] convertStringToIntArray(String str)
	{
		String[] strings = str.split(",");
		int[][] integers = new int[3][3];

		int i = 0;
		for (int n = 0; n < 3; n++)
		{
			for (int m = 0; m < 3; m++)
			{
				integers[n][m] = Integer.parseInt(strings[i]);
				i++;
			}
		}

		return integers;
	}
}
