package data;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import logic.Game;
import view.MainWindow;

/**
 * @author Leon Kelle
 * @class AGB\AH911
 * @project TicTacToe
 */

public class NetworkHandler implements Runnable
{
	private static volatile NetworkHandler instance;
	public Thread networkHandlerThread;
	private DatagramSocket networkSocket;
	private InetAddress hostAdress;
	private String opponentIp;
	private int port = 18911;

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

	public void createNetworkThread()
	{
		networkHandlerThread = new Thread(NetworkHandler.getInstance());
	}

	public void newNetworkSocket(String ipAdress)
	{
		try
		{
			this.opponentIp = ipAdress;
			this.hostAdress = InetAddress.getByName(ipAdress);
			this.networkSocket = new DatagramSocket(this.port);

			MainWindow.getInstance().setLblConnectionStateText(ipAdress);
		}
		catch (SocketException | UnknownHostException e)
		{
			e.printStackTrace();
		}
	}

	public void closeNetworkSocket() // TODO execute on close
	{
		this.networkSocket.close();
		this.hostAdress = null;
		this.opponentIp = null;

		MainWindow.getInstance().setLblConnectionStateText("Not Connected");
	}

	public void sendArray(int[][] gameField)
	{
		String message = convertIntArrayToString(gameField);

		byte[] messageBytes = message.getBytes();

		try
		{
			networkSocket.send(new DatagramPacket(messageBytes, messageBytes.length, hostAdress, port));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		Game.getInstance().setGameField(receiveArray());
	}

	public int[][] receiveArray()
	{
		DatagramPacket datagramPacket;
		String data;
		int[][] gameField;

		while (true)
		{
			datagramPacket = new DatagramPacket(new byte[512], 512);

			try
			{
				datagramPacket.setAddress(InetAddress.getLocalHost());

				networkSocket.receive(datagramPacket);

				data = new String(datagramPacket.getData()).trim();
				gameField = convertStringToIntArray(data);

				break;
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return gameField;
	}

	private String convertIntArrayToString(int[][] arr)
	{
		String result = new String();

		for (int n = 0; n < 3; n++)
		{
			for (int m = 0; m < 3; m++)
			{
				result += arr[n][m] + ",";
			}
		}

		return result;
	}

	private int[][] convertStringToIntArray(String str)
	{
		String[] input = str.split(",");
		int[][] result = new int[3][3];

		int i = 0;
		for (int n = 0; n < 3; n++)
		{
			for (int m = 0; m < 3; m++)
			{
				result[n][m] = Integer.parseInt(input[i]);
				i++;
			}
		}

		return result;
	}

	public String getOpponentIp()
	{
		return opponentIp;
	}
}
