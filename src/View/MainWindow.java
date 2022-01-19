package View;

import javax.swing.JFrame;

/**
 * @author Leon Kelle
 * @class AGB\AH911
 * @project TicTacToe
 */

public class MainWindow
{

    public JFrame frame;

    /**
     * Create the application.
     */
    public MainWindow()
    {
	initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
	frame = new JFrame();
	frame.setBounds(100, 100, 450, 300);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
