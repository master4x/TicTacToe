package controller;

import java.awt.EventQueue;

import data.FileIOHandler;
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
		// Read File // TODO runnable?
		FileIOHandler.getInstance().readCSVFile();
		FileIOHandler.getInstance().writeCSVFile(); // TODO TEST removeme
		
		// Start MainWindow Thread
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainWindow.getInstance().frmTicTacToe.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
