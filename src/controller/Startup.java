package controller;

import java.awt.EventQueue;

import data.NetworkHandler;
import view.MainWindow;

/**
 * @author Leon Kelle
 * @class AGB\AH911
 * @project TicTacToe
 */

public class Startup
{
	public static void main(String[] args)
	{
		// Start MainWindow Thread
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainWindow window = new MainWindow();
					window.frmTicTacToe.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});

		// TEST networking
		int[][] testArr = { { 9, 8, 7 }, { 6, 5, 4 }, { 3, 2, 1 } };
		NetworkHandler.getInstance().newNetworkSocket("127.0.0.1");
		NetworkHandler.getInstance().sendArray(testArr);
		NetworkHandler.getInstance().receiveArray();
	}
}
