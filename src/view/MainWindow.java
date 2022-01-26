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
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import data.NetworkHandler;

import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JSeparator;
import java.awt.Button;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import logic.Game;
import logic.Players;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author Leon Kelle
 * @class AGB\AH911
 * @project TicTacToe
 */

public class MainWindow
{
	private static volatile MainWindow instance;
	public JFrame frmTicTacToe;
	private JTextField txtYourIp;
	private JTable tblGameStats;
	private JTextField txtWinCount;
	private JTextField txtLooseCount;
	private JTextField txtDrawCount;
	private JLabel lblConnectionState;
	private JLabel lblGameState;
	private JLabel lblGameField00;
	private JLabel lblGameField01;
	private JLabel lblGameField02;
	private JLabel lblGameField10;
	private JLabel lblGameField11;
	private JLabel lblGameField12;
	private JLabel lblGameField20;
	private JLabel lblGameField21;
	private JLabel lblGameField22;
	private JLabel lblPlayerInfo;

	/**
	 * Create the application.
	 */
	private MainWindow()
	{
		initialize();
	}


	/*
	 * Singleton
	 */
	public static MainWindow getInstance()
	{
		if (instance == null)
		{
			synchronized (Game.class)
			{
				if (instance == null)
				{
					instance = new MainWindow();
				}
			}
		}
		return instance;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmTicTacToe = new JFrame();
		frmTicTacToe.setMinimumSize(new Dimension(650, 400));
		frmTicTacToe.setTitle("TicTacToe");
		frmTicTacToe.setIconImage(Toolkit.getDefaultToolkit()
			.getImage(MainWindow.class.getResource("/org/eclipse/jface/dialogs/images/title_banner.png")));
		frmTicTacToe.setBounds(100, 100, 548, 384);
		frmTicTacToe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane pneNavBar = new JTabbedPane(JTabbedPane.TOP);
		pneNavBar.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmTicTacToe.getContentPane().add(pneNavBar, BorderLayout.CENTER);

		JPanel pnlTabGame = new JPanel();
		pnlTabGame.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlTabGame.setToolTipText("");
		pnlTabGame.setName("");
		pneNavBar.addTab("Game",
			new ImageIcon(MainWindow.class.getResource("/org/eclipse/jface/fieldassist/images/contassist_ovr@2x.png")),
			pnlTabGame, null);
		pnlTabGame.setLayout(new BorderLayout(0, 0));

		JPanel pnlTopRow = new JPanel();
		pnlTabGame.add(pnlTopRow, BorderLayout.NORTH);
		pnlTopRow.setLayout(new BorderLayout(0, 0));

		JPanel pnlLeftCol = new JPanel();
		pnlTopRow.add(pnlLeftCol);

		JLabel lblOpponentsIp = new JLabel("Opponent's IP:");
		pnlLeftCol.add(lblOpponentsIp);

		JTextField txtOpponentsIp = new JTextField();
		pnlLeftCol.add(txtOpponentsIp);
		txtOpponentsIp.setColumns(10);

		JComboBox cbPlayerSelector = new JComboBox();
		cbPlayerSelector.setModel(new DefaultComboBoxModel(Players.values()));
		cbPlayerSelector.setSelectedIndex(0);
		pnlLeftCol.add(cbPlayerSelector);

		JButton btnConnect = new JButton("Connect");
		btnConnect.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (txtOpponentsIp.getText().isEmpty() == false)
				{
					Game.getInstance().startGame((Players) cbPlayerSelector.getSelectedItem(), txtOpponentsIp.getText());
					
					lblOpponentsIp.setEnabled(false);
					txtOpponentsIp.setEnabled(false);
					cbPlayerSelector.setEnabled(false);
					btnConnect.setEnabled(false);
				}
			}
		});
		pnlLeftCol.add(btnConnect);

		JPanel pnlRightCol = new JPanel();
		pnlTopRow.add(pnlRightCol, BorderLayout.EAST);

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

		lblConnectionState = new JLabel("ConnectionState");
		pnlLeftOuterCol.add(lblConnectionState);

		JPanel pnlRightOuterCol = new JPanel();
		pnlBottomRow.add(pnlRightOuterCol, BorderLayout.EAST);

		lblGameState = new JLabel("GameState");
		pnlRightOuterCol.add(lblGameState);

		JPanel pnlMiddleRow = new JPanel();
		pnlTabGame.add(pnlMiddleRow, BorderLayout.CENTER);
		pnlMiddleRow.setLayout(new BorderLayout(0, 0));

		JPanel pnlCenterContainer = new JPanel();
		pnlCenterContainer.setBorder(new EmptyBorder(25, 25, 25, 25));
		pnlMiddleRow.add(pnlCenterContainer, BorderLayout.CENTER);
		pnlCenterContainer.setLayout(new BorderLayout(0, 0));

		JPanel pnlGameField = new JPanel();
		pnlGameField.setBorder(new EmptyBorder(0, 25, 0, 25));
		pnlCenterContainer.add(pnlGameField, BorderLayout.WEST);
		pnlGameField.setLayout(new GridLayout(3, 3, 50, 0));

		lblGameField00 = new JLabel("0");
		lblGameField00.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Game.getInstance().applyPlayerMove(0, 0);
			}
		});
		lblGameField00.setHorizontalAlignment(SwingConstants.CENTER);
		pnlGameField.add(lblGameField00);

		lblGameField01 = new JLabel("0");
		lblGameField01.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Game.getInstance().applyPlayerMove(0, 1);
			}
		});
		pnlGameField.add(lblGameField01);

		lblGameField02 = new JLabel("0");
		lblGameField02.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Game.getInstance().applyPlayerMove(0, 2);
			}
		});
		lblGameField02.setHorizontalAlignment(SwingConstants.CENTER);
		pnlGameField.add(lblGameField02);

		lblGameField10 = new JLabel("0");
		lblGameField10.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Game.getInstance().applyPlayerMove(1, 0);
			}
		});
		lblGameField10.setHorizontalAlignment(SwingConstants.CENTER);
		pnlGameField.add(lblGameField10);

		lblGameField11 = new JLabel("0");
		lblGameField11.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Game.getInstance().applyPlayerMove(1, 1);
			}
		});
		lblGameField11.setHorizontalAlignment(SwingConstants.CENTER);
		pnlGameField.add(lblGameField11);

		lblGameField12 = new JLabel("0");
		lblGameField12.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Game.getInstance().applyPlayerMove(1, 2);
			}
		});
		lblGameField12.setHorizontalAlignment(SwingConstants.CENTER);
		pnlGameField.add(lblGameField12);

		lblGameField20 = new JLabel("0");
		lblGameField20.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Game.getInstance().applyPlayerMove(2, 0);
			}
		});
		lblGameField20.setHorizontalAlignment(SwingConstants.CENTER);
		pnlGameField.add(lblGameField20);

		lblGameField21 = new JLabel("0");
		lblGameField21.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Game.getInstance().applyPlayerMove(2, 1);
			}
		});
		lblGameField21.setHorizontalAlignment(SwingConstants.CENTER);
		pnlGameField.add(lblGameField21);

		lblGameField22 = new JLabel("0");
		lblGameField22.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Game.getInstance().applyPlayerMove(2, 2);
			}
		});
		lblGameField22.setHorizontalAlignment(SwingConstants.CENTER);
		pnlGameField.add(lblGameField22);

		JPanel pnlGameInfo = new JPanel();
		pnlCenterContainer.add(pnlGameInfo, BorderLayout.EAST);
		pnlGameInfo.setLayout(new BorderLayout(0, 0));

		lblPlayerInfo = new JLabel("Your Turn!");
		lblPlayerInfo.setHorizontalAlignment(SwingConstants.CENTER);
		pnlGameInfo.add(lblPlayerInfo);

		JPanel pnlTabStats = new JPanel();
		pnlTabStats.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pneNavBar.addTab("Stats",
			new ImageIcon(MainWindow.class.getResource("/org/eclipse/jface/fieldassist/images/info_ovr@2x.png")),
			pnlTabStats, null);
		pnlTabStats.setLayout(new BorderLayout(0, 0));

		JPanel pnlTopMain = new JPanel();
		pnlTabStats.add(pnlTopMain, BorderLayout.NORTH);
		pnlTopMain.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblWinCount = new JLabel("Win Count:");
		pnlTopMain.add(lblWinCount);

		txtWinCount = new JTextField();
		txtWinCount.setEditable(false);
		pnlTopMain.add(txtWinCount);
		txtWinCount.setColumns(10);

		JLabel lblLooseCount = new JLabel("Loose Count:");
		pnlTopMain.add(lblLooseCount);

		txtLooseCount = new JTextField();
		txtLooseCount.setEditable(false);
		pnlTopMain.add(txtLooseCount);
		txtLooseCount.setColumns(10);

		JLabel lblDrawCount = new JLabel("Draw Count:");
		pnlTopMain.add(lblDrawCount);

		txtDrawCount = new JTextField();
		txtDrawCount.setEditable(false);
		pnlTopMain.add(txtDrawCount);
		txtDrawCount.setColumns(10);

		JPanel pnlCenterMain = new JPanel();
		pnlTabStats.add(pnlCenterMain);
		pnlCenterMain.setLayout(new BorderLayout(0, 0));

		JScrollPane scrlStatsTable = new JScrollPane();
		scrlStatsTable.setViewportBorder(null);
		pnlCenterMain.add(scrlStatsTable);

		tblGameStats = new JTable();
		tblGameStats.setModel(
			new DefaultTableModel(new Object[][] { { null, null, null }, { null, null, null }, { null, null, null }, },
				new String[] { "Game Number", "Match Result", "Opponent IP" }));
		tblGameStats.getColumnModel().getColumn(0).setPreferredWidth(94);
		tblGameStats.getColumnModel().getColumn(1).setPreferredWidth(91);
		tblGameStats.getColumnModel().getColumn(2).setPreferredWidth(109);
		scrlStatsTable.setViewportView(tblGameStats);
	}

	public void setTblGameStats()
	{
		//TODO addTableRow / setTblGameStats
	}
	
	public void setTxtYourIpText(String txtYourIpText)
	{
		this.txtYourIp.setText(txtYourIpText);
	}

	public void setTxtWinCountText(String txtWinCountText)
	{
		this.txtWinCount.setText(txtWinCountText);
	}

	public void setTxtLooseCountText(String txtLooseCountText)
	{
		this.txtLooseCount.setText(txtLooseCountText);
	}

	public void setTxtDrawCountText(String txtDrawCountText)
	{
		this.txtDrawCount.setText(txtDrawCountText);
	}

	public void setLblConnectionStateText(String lblConnectionStateText)
	{
		this.lblConnectionState.setText(lblConnectionStateText);
	}

	public void setLblGameStateText(String lblGameStateText)
	{
		this.lblGameState.setText(lblGameStateText);
	}

	public void setLblGameField00Text(String lblGameField00Text)
	{
		this.lblGameField00.setText(lblGameField00Text);
	}

	public void setLblGameField01Text(String lblGameField01Text)
	{
		this.lblGameField01.setText(lblGameField01Text);
	}

	public void setLblGameField02Text(String lblGameField02Text)
	{
		this.lblGameField02.setText(lblGameField02Text);
	}

	public void setLblGameField10Text(String lblGameField10Text)
	{
		this.lblGameField10.setText(lblGameField10Text);
	}

	public void setLblGameField11Text(String lblGameField11Text)
	{
		this.lblGameField11.setText(lblGameField11Text);
	}

	public void setLblGameField12Text(String lblGameField12Text)
	{
		this.lblGameField12.setText(lblGameField12Text);
	}

	public void setLblGameField20Text(String lblGameField20Text)
	{
		this.lblGameField20.setText(lblGameField20Text);
	}

	public void setLblGameField21Text(String lblGameField21Text)
	{
		this.lblGameField21.setText(lblGameField21Text);
	}

	public void setLblGameField22Text(String lblGameField22Text)
	{
		this.lblGameField22.setText(lblGameField22Text);
	}

	public void setLblPlayerInfoText(String lblPlayerInfoText)
	{
		this.lblPlayerInfo.setText(lblPlayerInfoText);
	}
}
