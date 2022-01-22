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
import javax.swing.border.BevelBorder;

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

		JTabbedPane pneNavBar = new JTabbedPane(JTabbedPane.TOP);
		pneNavBar.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmTicTacToe.getContentPane().add(pneNavBar, BorderLayout.CENTER);

		JPanel pnlTabGame = new JPanel();
		pnlTabGame.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlTabGame.setToolTipText("");
		pnlTabGame.setName("");
		pneNavBar.addTab("Game", new ImageIcon(MainWindow.class.getResource("/org/eclipse/jface/fieldassist/images/contassist_ovr@2x.png")), pnlTabGame, null);
		pnlTabGame.setLayout(new BorderLayout(0, 0));

		JPanel pnlTopRow = new JPanel();
		pnlTabGame.add(pnlTopRow, BorderLayout.NORTH);
		pnlTopRow.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel pnlLeftCol = new JPanel();
		pnlTopRow.add(pnlLeftCol);

		JLabel lblOpponentsIp = new JLabel("Opponent's IP:");
		pnlLeftCol.add(lblOpponentsIp);

		txtOpponentsIp = new JTextField();
		pnlLeftCol.add(txtOpponentsIp);
		txtOpponentsIp.setColumns(10);

		JButton btnConnect = new JButton("Connect");
		pnlLeftCol.add(btnConnect);

		JPanel pnlRightCol = new JPanel();
		pnlTopRow.add(pnlRightCol);

		JLabel lblYourIp = new JLabel("Your IP:");
		pnlRightCol.add(lblYourIp);

		txtYourIp = new JTextField();
		txtYourIp.setEditable(false);
		pnlRightCol.add(txtYourIp);
		txtYourIp.setColumns(10);

		JPanel pnlBottomRow = new JPanel();
		pnlBottomRow.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlTabGame.add(pnlBottomRow, BorderLayout.SOUTH);
		pnlBottomRow.setLayout(new BorderLayout(0, 0));

		JPanel pnlLeftOuterCol = new JPanel();
		pnlBottomRow.add(pnlLeftOuterCol, BorderLayout.WEST);

		JLabel lblConnectionState = new JLabel("ConnectionState");
		pnlLeftOuterCol.add(lblConnectionState);

		JPanel pnlRightOuterCol = new JPanel();
		pnlBottomRow.add(pnlRightOuterCol, BorderLayout.EAST);

		JLabel lblGameState = new JLabel("GameState");
		pnlRightOuterCol.add(lblGameState);

		JPanel pnlMiddleRow = new JPanel();
		pnlTabGame.add(pnlMiddleRow, BorderLayout.CENTER);
		pnlMiddleRow.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlRightOuterContainer = new JPanel();
		pnlRightOuterContainer.setBorder(new EmptyBorder(0, 5, 0, 5));
		pnlMiddleRow.add(pnlRightOuterContainer, BorderLayout.EAST);
		pnlRightOuterContainer.setLayout(new BorderLayout(0, 0));
		
		JLabel lblCallPlayerAction = new JLabel("Your Turn!");
		pnlRightOuterContainer.add(lblCallPlayerAction, BorderLayout.CENTER);
		
		JPanel pnlCenterContainer = new JPanel();
		pnlMiddleRow.add(pnlCenterContainer, BorderLayout.CENTER);
		
		JPanel pnlTabStats = new JPanel();
		pnlTabStats.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pneNavBar.addTab("Stats", new ImageIcon(MainWindow.class.getResource("/org/eclipse/jface/fieldassist/images/info_ovr@2x.png")), pnlTabStats, null);
	}
}
