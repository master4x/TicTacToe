package view;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.Dimension;
import javax.swing.ImageIcon;

/**
 * @author Leon Kelle
 * @class AGB\AH911
 * @project TicTacToe
 */

public class MainWindow
{

	public JFrame frmTicTacToe;
	private JTextField txtOpponentsIp;
	private JTextField txtYourIp;

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
		frmTicTacToe = new JFrame();
		frmTicTacToe.setMinimumSize(new Dimension(620, 420));
		frmTicTacToe.setTitle("TicTacToe");
		frmTicTacToe.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/org/eclipse/jface/dialogs/images/title_banner.png")));
		frmTicTacToe.setBounds(100, 100, 600, 400);
		frmTicTacToe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane navBar = new JTabbedPane(JTabbedPane.TOP);
		navBar.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmTicTacToe.getContentPane().add(navBar, BorderLayout.CENTER);

		JPanel tabGame = new JPanel();
		tabGame.setToolTipText("");
		tabGame.setName("");
		navBar.addTab("Game", new ImageIcon(MainWindow.class.getResource("/org/eclipse/jface/fieldassist/images/contassist_ovr@2x.png")), tabGame, null);
		tabGame.setLayout(new BorderLayout(0, 0));

		JPanel topRow = new JPanel();
		tabGame.add(topRow, BorderLayout.NORTH);
		topRow.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel leftCol = new JPanel();
		topRow.add(leftCol);

		JLabel lblOpponentsIp = new JLabel("Opponent's IP:");
		leftCol.add(lblOpponentsIp);

		txtOpponentsIp = new JTextField();
		leftCol.add(txtOpponentsIp);
		txtOpponentsIp.setColumns(10);

		JButton btnConnect = new JButton("Connect");
		leftCol.add(btnConnect);

		JPanel rightCol = new JPanel();
		topRow.add(rightCol);

		JLabel lblYourIp = new JLabel("Your IP:");
		rightCol.add(lblYourIp);

		txtYourIp = new JTextField();
		txtYourIp.setEditable(false);
		rightCol.add(txtYourIp);
		txtYourIp.setColumns(10);

		JPanel bottomRow = new JPanel();
		tabGame.add(bottomRow, BorderLayout.SOUTH);
		bottomRow.setLayout(new BorderLayout(0, 0));

		JPanel leftOuterCol = new JPanel();
		bottomRow.add(leftOuterCol, BorderLayout.WEST);

		JLabel lblConnectionState = new JLabel("ConnectionState");
		leftOuterCol.add(lblConnectionState);

		JPanel rightOuterCol = new JPanel();
		bottomRow.add(rightOuterCol, BorderLayout.EAST);

		JLabel lblGameState = new JLabel("GameState");
		rightOuterCol.add(lblGameState);

		JPanel middleRow = new JPanel();
		tabGame.add(middleRow, BorderLayout.CENTER);
		middleRow.setLayout(new BorderLayout(0, 0));
		
		JPanel rightOuterContainer = new JPanel();
		rightOuterContainer.setBorder(new EmptyBorder(0, 5, 0, 5));
		middleRow.add(rightOuterContainer, BorderLayout.EAST);
		rightOuterContainer.setLayout(new BorderLayout(0, 0));
		
		JLabel lblCallPlayerAction = new JLabel("Your Turn!");
		rightOuterContainer.add(lblCallPlayerAction, BorderLayout.CENTER);
		
		JPanel centerContainer = new JPanel();
		middleRow.add(centerContainer, BorderLayout.CENTER);
		
		JPanel tabStats = new JPanel();
		navBar.addTab("Stats", new ImageIcon(MainWindow.class.getResource("/org/eclipse/jface/fieldassist/images/info_ovr@2x.png")), tabStats, null);
	}
}
