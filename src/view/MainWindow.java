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
import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import data.FileIOHandler;
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
	private JButton btnConnect;
	private JButton[][] btnGameField = new JButton[3][3];
	private JComboBox cbPlayerSelector;
	private JLabel lblConnectionState;
	private JLabel lblGameState;
	private JLabel lblOpponentIp;
	private JTable tblGameStats;
	private JTextField txtDrawCount;
	private JTextField txtLooseCount;
	private JTextField txtOpponentIp;
	private JTextField txtWinCount;

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

	/*
	 * Launch application
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainWindow window = MainWindow.getInstance();
					window.frmTicTacToe.setVisible(true);

					FileIOHandler.getInstance().readCSVFile();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
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

		lblOpponentIp = new JLabel("Opponent's IP:");
		pnlTopCol.add(lblOpponentIp);

		txtOpponentIp = new JTextField();
		pnlTopCol.add(txtOpponentIp);
		txtOpponentIp.setColumns(15);

		cbPlayerSelector = new JComboBox();
		cbPlayerSelector.setMaximumRowCount(10);
		cbPlayerSelector.setModel(new DefaultComboBoxModel(Players.values()));
		cbPlayerSelector.setSelectedIndex(0);
		pnlTopCol.add(cbPlayerSelector);

		btnConnect = new JButton("Connect");
		btnConnect.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (txtOpponentIp.getText().isEmpty() == false)
				{
					deactivateInputs();
					
					Game.getInstance().startGame((Players) cbPlayerSelector.getSelectedItem(),
						txtOpponentIp.getText());
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

		lblGameState = new JLabel("NoGameActive"); // TODO get by param
		pnlRightCol.add(lblGameState);

		JPanel pnlCenterCol = new JPanel();
		pnlBottomRow.add(pnlCenterCol, BorderLayout.CENTER);

		JLabel lblYourIp = new JLabel("Your IP:");
		pnlCenterCol.add(lblYourIp);

		JTextField txtYourIp = new JTextField();
		txtYourIp.setHorizontalAlignment(SwingConstants.CENTER);
		txtYourIp.setText(NetworkHandler.getInstance().getLocalIp());
		txtYourIp.setEditable(false);
		pnlCenterCol.add(txtYourIp);
		txtYourIp.setColumns(9);

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
		pnlGameField.setLayout(new GridLayout(3, 3, 0, 0));

		btnGameField[0][0] = new JButton();
		btnGameField[0][0].addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Game.getInstance().applyPlayerMove(0, 0);
			}
		});
		btnGameField[0][0].setHorizontalAlignment(SwingConstants.CENTER);
		pnlGameField.add(btnGameField[0][0]);

		btnGameField[0][1] = new JButton();
		btnGameField[0][1].setHorizontalAlignment(SwingConstants.CENTER);
		btnGameField[0][1].addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Game.getInstance().applyPlayerMove(0, 1);
			}
		});
		pnlGameField.add(btnGameField[0][1]);

		btnGameField[0][2] = new JButton();
		btnGameField[0][2].addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Game.getInstance().applyPlayerMove(0, 2);
			}
		});
		btnGameField[0][2].setHorizontalAlignment(SwingConstants.CENTER);
		pnlGameField.add(btnGameField[0][2]);

		btnGameField[1][0] = new JButton();
		btnGameField[1][0].addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Game.getInstance().applyPlayerMove(1, 0);
			}
		});
		btnGameField[1][0].setHorizontalAlignment(SwingConstants.CENTER);
		pnlGameField.add(btnGameField[1][0]);

		btnGameField[1][1] = new JButton();
		btnGameField[1][1].addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Game.getInstance().applyPlayerMove(1, 1);
			}
		});
		btnGameField[1][1].setHorizontalAlignment(SwingConstants.CENTER);
		pnlGameField.add(btnGameField[1][1]);

		btnGameField[1][2] = new JButton();
		btnGameField[1][2].addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Game.getInstance().applyPlayerMove(1, 2);
			}
		});
		btnGameField[1][2].setHorizontalAlignment(SwingConstants.CENTER);
		pnlGameField.add(btnGameField[1][2]);

		btnGameField[2][0] = new JButton();
		btnGameField[2][0].addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Game.getInstance().applyPlayerMove(2, 0);
			}
		});
		btnGameField[2][0].setHorizontalAlignment(SwingConstants.CENTER);
		pnlGameField.add(btnGameField[2][0]);

		btnGameField[2][1] = new JButton();
		btnGameField[2][1].addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Game.getInstance().applyPlayerMove(2, 1);
			}
		});
		btnGameField[2][1].setHorizontalAlignment(SwingConstants.CENTER);
		pnlGameField.add(btnGameField[2][1]);

		btnGameField[2][2] = new JButton();
		btnGameField[2][2].addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Game.getInstance().applyPlayerMove(2, 2);
			}
		});
		btnGameField[2][2].setHorizontalAlignment(SwingConstants.CENTER);
		pnlGameField.add(btnGameField[2][2]);

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
		txtWinCount.setHorizontalAlignment(SwingConstants.CENTER);
		txtWinCount.setEditable(false);
		pnlTopMain.add(txtWinCount);
		txtWinCount.setColumns(5);

		JLabel lblLooseCount = new JLabel("Loose Count:");
		pnlTopMain.add(lblLooseCount);

		txtLooseCount = new JTextField();
		txtLooseCount.setHorizontalAlignment(SwingConstants.CENTER);
		txtLooseCount.setEditable(false);
		pnlTopMain.add(txtLooseCount);
		txtLooseCount.setColumns(5);

		JLabel lblDrawCount = new JLabel("Draw Count:");
		pnlTopMain.add(lblDrawCount);

		txtDrawCount = new JTextField();
		txtDrawCount.setHorizontalAlignment(SwingConstants.CENTER);
		txtDrawCount.setEditable(false);
		pnlTopMain.add(txtDrawCount);
		txtDrawCount.setColumns(5);

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

	private void deactivateInputs()
	{
		this.lblOpponentIp.setEnabled(false);
		this.txtOpponentIp.setEnabled(false);
		this.cbPlayerSelector.setEnabled(false);
		this.btnConnect.setEnabled(false);
	}
	
	public void activateInputs()
	{
		this.lblOpponentIp.setEnabled(true);
		this.txtOpponentIp.setEnabled(true);
		this.cbPlayerSelector.setEnabled(true);
		this.btnConnect.setEnabled(true);
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

	public void setBtnGameFieldText(int row, int column, String btnGameFieldText)
	{	
		this.btnGameField[row][column].setText(btnGameFieldText);
		this.btnGameField[row][column].paintImmediately(btnGameField[row][column].getVisibleRect());  // TODO Refresh Bug
	}
}
