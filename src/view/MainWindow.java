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
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import logic.Game;
import logic.Players;

/**
 * @author Leon Kelle
 * @class AGB\AH911
 * @project TicTacToe
 */

public class MainWindow
{
	private static volatile MainWindow instance;
	public JFrame frmTicTacToe;
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
	private JTextField txtYourIp;

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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize()
	{
		frmTicTacToe = new JFrame();
		frmTicTacToe.setMinimumSize(new Dimension(500, 400));
		frmTicTacToe.setTitle("TicTacToe");
		frmTicTacToe.setIconImage(Toolkit.getDefaultToolkit()
			.getImage(MainWindow.class.getResource("/org/eclipse/jface/dialogs/images/title_banner.png")));
		frmTicTacToe.setBounds(100, 100, 500, 400);
		frmTicTacToe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

		JPanel pnlTopCol = new JPanel();
		pnlTopRow.add(pnlTopCol);

		JLabel lblOpponentsIp = new JLabel("Opponent's IP:");
		pnlTopCol.add(lblOpponentsIp);

		JTextField txtOpponentsIp = new JTextField();
		pnlTopCol.add(txtOpponentsIp);
		txtOpponentsIp.setColumns(15);

		JComboBox cbPlayerSelector = new JComboBox();
		cbPlayerSelector.setMaximumRowCount(10);
		cbPlayerSelector.setModel(new DefaultComboBoxModel(Players.values()));
		cbPlayerSelector.setSelectedIndex(0);
		pnlTopCol.add(cbPlayerSelector);

		JButton btnConnect = new JButton("Connect");
		btnConnect.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (txtOpponentsIp.getText().isEmpty() == false)
				{
					Game.getInstance().startGame((Players) cbPlayerSelector.getSelectedItem(),
						txtOpponentsIp.getText());

					lblOpponentsIp.setEnabled(false);
					txtOpponentsIp.setEnabled(false);
					cbPlayerSelector.setEnabled(false);
					btnConnect.setEnabled(false);
				}
			}
		});
		pnlTopCol.add(btnConnect);

		JPanel pnlBottomRow = new JPanel();
		pnlBottomRow.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlTabGame.add(pnlBottomRow, BorderLayout.SOUTH);
		pnlBottomRow.setLayout(new BorderLayout(0, 0));

		JPanel pnlLeftCol = new JPanel();
		pnlBottomRow.add(pnlLeftCol, BorderLayout.WEST);

		lblConnectionState = new JLabel("Not connected");
		pnlLeftCol.add(lblConnectionState);

		JPanel pnlRightCol = new JPanel();
		pnlBottomRow.add(pnlRightCol, BorderLayout.EAST);

		lblGameState = new JLabel("GameState");
		pnlRightCol.add(lblGameState);

		JPanel pnlCenterCol = new JPanel();
		pnlBottomRow.add(pnlCenterCol, BorderLayout.CENTER);

		JLabel lblYourIp = new JLabel("Your IP:");
		pnlCenterCol.add(lblYourIp);

		JTextField txtYourIp = new JTextField();
		txtYourIp.setText(NetworkHandler.getInstance().getLocalIp());
		txtYourIp.setEditable(false);
		pnlCenterCol.add(txtYourIp);
		txtYourIp.setColumns(12);

		JPanel pnlMiddleRow = new JPanel();
		pnlTabGame.add(pnlMiddleRow, BorderLayout.CENTER);
		pnlMiddleRow.setLayout(new BorderLayout(0, 0));

		JPanel pnlCenterContainer = new JPanel();
		pnlCenterContainer.setBorder(new EmptyBorder(25, 25, 25, 25));
		pnlMiddleRow.add(pnlCenterContainer, BorderLayout.CENTER);
		pnlCenterContainer.setLayout(new BorderLayout(0, 0));

		JPanel pnlGameField = new JPanel();
		pnlGameField.setBorder(new EmptyBorder(0, 25, 0, 25));
		pnlCenterContainer.add(pnlGameField, BorderLayout.CENTER);
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
		lblGameField01.setHorizontalAlignment(SwingConstants.CENTER);
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
			new DefaultTableModel(new Object[][] {}, new String[] { "Game Number", "Match Result", "Opponent IP" }));
		tblGameStats.getColumnModel().getColumn(0).setPreferredWidth(94);
		tblGameStats.getColumnModel().getColumn(1).setPreferredWidth(91);
		tblGameStats.getColumnModel().getColumn(2).setPreferredWidth(109);
		scrlStatsTable.setViewportView(tblGameStats);
	}

	public void addTblGameStatsRows(ArrayList<String[]> statistics)
	{
		DefaultTableModel tableModel = new DefaultTableModel(new Object[][] {},
			new String[] { "Game Number", "Match Result", "Opponent IP" });

		for (String[] sessionInfo : statistics)
		{
			tableModel.addRow(new Object[] { sessionInfo[0], sessionInfo[1], sessionInfo[2] });
		}

		this.tblGameStats.setModel(tableModel);
		this.tblGameStats.paintImmediately(tblGameStats.getVisibleRect());
	}

	public void setTxtWinCountText(String txtWinCountText)
	{
		this.txtWinCount.setText(txtWinCountText);
		this.txtWinCount.paintImmediately(txtWinCount.getVisibleRect());
	}

	public void setTxtLooseCountText(String txtLooseCountText)
	{
		this.txtLooseCount.setText(txtLooseCountText);
		this.txtLooseCount.paintImmediately(txtLooseCount.getVisibleRect());
	}

	public void setTxtDrawCountText(String txtDrawCountText)
	{
		this.txtDrawCount.setText(txtDrawCountText);
		this.txtDrawCount.paintImmediately(txtDrawCount.getVisibleRect());
	}

	public void setLblConnectionStateText(String lblConnectionStateText)
	{
		this.lblConnectionState.setText(lblConnectionStateText);
		this.lblConnectionState.paintImmediately(lblConnectionState.getVisibleRect());
	}

	public void setLblGameStateText(String lblGameStateText)
	{
		this.lblGameState.setText(lblGameStateText);
		this.lblGameState.paintImmediately(lblGameState.getVisibleRect());
	}

	public void setLblGameField00Text(String lblGameField00Text)
	{
		this.lblGameField00.setText(lblGameField00Text);
		this.lblGameField00.paintImmediately(lblGameField00.getVisibleRect());
	}

	public void setLblGameField01Text(String lblGameField01Text)
	{
		this.lblGameField01.setText(lblGameField01Text);
		this.lblGameField01.paintImmediately(lblGameField01.getVisibleRect());
	}

	public void setLblGameField02Text(String lblGameField02Text)
	{
		this.lblGameField02.setText(lblGameField02Text);
		this.lblGameField02.paintImmediately(lblGameField02.getVisibleRect());
	}

	public void setLblGameField10Text(String lblGameField10Text)
	{
		this.lblGameField10.setText(lblGameField10Text);
		this.lblGameField00.paintImmediately(lblGameField00.getVisibleRect());
	}

	public void setLblGameField11Text(String lblGameField11Text)
	{
		this.lblGameField11.setText(lblGameField11Text);
		this.lblGameField11.paintImmediately(lblGameField11.getVisibleRect());
	}

	public void setLblGameField12Text(String lblGameField12Text)
	{
		this.lblGameField12.setText(lblGameField12Text);
		this.lblGameField12.paintImmediately(lblGameField12.getVisibleRect());
	}

	public void setLblGameField20Text(String lblGameField20Text)
	{
		this.lblGameField20.setText(lblGameField20Text);
		this.lblGameField20.paintImmediately(lblGameField20.getVisibleRect());
	}

	public void setLblGameField21Text(String lblGameField21Text)
	{
		this.lblGameField21.setText(lblGameField21Text);
		this.lblGameField21.paintImmediately(lblGameField21.getVisibleRect());
	}

	public void setLblGameField22Text(String lblGameField22Text)
	{
		this.lblGameField22.setText(lblGameField22Text);
		this.lblGameField22.paintImmediately(lblGameField22.getVisibleRect());
	}
}
