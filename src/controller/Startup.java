package controller;

import java.awt.EventQueue;
import network.CommunicationHandler;
import network.CommunicationSender;
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
					window.frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});

		CommunicationHandler communicationHandler = new CommunicationHandler();
		communicationHandler.startThreads("192.168.178.75");
		
		CommunicationSender communicationSender = CommunicationSender.getInstance();
		communicationSender.sendMessage("0,8,0,0,5,6,5,0,0,0");
		int[][] testarr = {{9,8,7},{6,5,4},{3,2,1}};
		System.out.println(communicationSender.convertIntArrayToString(testarr));
		
	}
}
