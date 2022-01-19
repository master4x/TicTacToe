package Controller;

import java.awt.EventQueue;

import View.MainWindow;

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
    }
}
