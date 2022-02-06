package logic;

import data.FileIOHandler;
import data.NetworkHandler;
import view.MainWindow;

/**
 * @author Leon Kelle
 * @class AGB\AH911
 * @project TicTacToe
 */

public class Game
{
	private static volatile Game instance;
	private int[][] gameField = new int[3][3];
	private GameState gameState;
	private Players player;

	private Game()
	{
		setGameState(GameState.NoGameActive);
	}

	/*
	 * Singleton
	 */
	public static Game getInstance()
	{
		if (instance == null)
		{
			synchronized (Game.class)
			{
				if (instance == null)
				{
					instance = new Game();
				}
			}
		}
		return instance;
	}

	public void startGame(Players player, String ipAdress)
	{
		this.player = player;
		
		if (gameState != GameState.NoGameActive)
		{
			clearGameField();
			appendGameField();
		}
		
		NetworkHandler.getInstance().newNetworkSocket(ipAdress);

		switch (player)
		{
			case Player1:
				setGameState(GameState.AwaitingPlayersGameField);
				break;
			case Player2:
				setGameState(GameState.AwaitingOpponentsGameField); // TODO Freezing GUI for P2 - Receiver to Runnable?
				break;
		}
	}

	private void sendGameField()
	{
		NetworkHandler.getInstance().sendArray(gameField);

		setGameState(GameState.CheckPlayersGameField);
	}

	private void reveiveGameField()
	{
		this.gameField = NetworkHandler.getInstance().receiveArray();

		appendGameField();

		setGameState(GameState.CheckOpponentsGameField);
	}

	private void gameOver()
	{	
		clearGameField();
		
		FileIOHandler.getInstance().addSessionInfo(gameState.toString(), NetworkHandler.getInstance().getOpponentIp());
		
		NetworkHandler.getInstance().closeNetworkSocket();
		
		MainWindow.getInstance().activateInputs();

		//setGameState(GameState.NoGameActive);
	}

	public void applyPlayerMove(int row, int column)
	{
		if (gameState == GameState.AwaitingPlayersGameField)
		{
			synchronized (this)
			{
				if (gameField[row][column] == 0)
				{
					switch (player)
					{
						case Player1:
							gameField[row][column] = 1;
							break;
						case Player2:
							gameField[row][column] = 2;
							break;
					}

					appendGameField();

					setGameState(GameState.SendingPlayersGameField);
				}
			}
		}
	}

	private void appendGameField()
	{
		MainWindow mainWindow = MainWindow.getInstance();

		for (int n = 0; n < 3; n++)
		{
			for (int m = 0; m < 3; m++)
			{
				switch (gameField[n][m])
				{
					case 1:
						mainWindow.setBtnGameFieldText(n, m, "X");
						break;
					case 2:
						mainWindow.setBtnGameFieldText(n, m, "O");
						break;
				}
			}
		}
	}
	
	private void clearGameField()
	{
		for (int n = 0; n < 3; n++)
		{
			for (int m = 0; m < 3; m++)
			{
				gameField[n][m] = 0;
			}
		}
	}

	private void checkGameField() // TODO clean rewrite checkGameField()
	{
		if (gameField[0][0] == 1 && gameField[0][1] == 1 && gameField[0][2] == 1 // top row
			|| gameField[1][0] == 1 && gameField[1][1] == 1 && gameField[1][2] == 1 // middle row
			|| gameField[2][0] == 1 && gameField[2][1] == 1 && gameField[2][2] == 1 // bottom row
			|| gameField[0][0] == 1 && gameField[1][0] == 1 && gameField[2][0] == 1 // left col
			|| gameField[0][1] == 1 && gameField[1][1] == 1 && gameField[2][1] == 1 // middle col
			|| gameField[0][2] == 1 && gameField[1][2] == 1 && gameField[2][2] == 1 // right col
			|| gameField[0][0] == 1 && gameField[1][1] == 1 && gameField[2][2] == 1 // TL to BR
			|| gameField[2][0] == 1 && gameField[1][1] == 1 && gameField[0][2] == 1) // TR to BL
		{
			if (player == Players.Player1)
			{
				setGameState(GameState.GameOver_Win);
			}
			else
			{
				setGameState(GameState.GameOver_Loose);
			}
		}
		else if (gameField[0][0] == 2 && gameField[0][1] == 2 && gameField[0][2] == 2 // top row
			|| gameField[1][0] == 2 && gameField[1][1] == 2 && gameField[1][2] == 2 // middle row
			|| gameField[2][0] == 2 && gameField[2][1] == 2 && gameField[2][2] == 2 // bottom row
			|| gameField[0][0] == 2 && gameField[1][0] == 2 && gameField[2][0] == 2 // left col
			|| gameField[0][1] == 2 && gameField[1][1] == 2 && gameField[2][1] == 2 // middle col
			|| gameField[0][2] == 2 && gameField[1][2] == 2 && gameField[2][2] == 2 // right col
			|| gameField[0][0] == 2 && gameField[1][1] == 2 && gameField[2][2] == 2 // TL to BR
			|| gameField[2][0] == 2 && gameField[1][1] == 2 && gameField[0][2] == 2) // TR to BL
		{
			if (player == Players.Player2)
			{
				setGameState(GameState.GameOver_Win);
			}
			else
			{
				setGameState(GameState.GameOver_Loose);
			}
		}
		else if (gameField[0][0] != 0 && gameField[0][1] != 0 && gameField[0][2] != 0 // top row
			&& gameField[1][0] != 0 && gameField[1][1] != 0 && gameField[1][2] != 0 // middle row
			&& gameField[2][0] != 0 && gameField[2][1] != 0 && gameField[2][2] != 0) // bottom row
		{
			setGameState(GameState.GameOver_Draw);
		}
		else
		{
			if (gameState == GameState.CheckOpponentsGameField)
			{
				setGameState(GameState.AwaitingPlayersGameField);
			}
			else if (gameState == GameState.CheckPlayersGameField)
			{
				setGameState(GameState.AwaitingOpponentsGameField);
			}
		}
	}

	public void setGameState(GameState gameState)
	{
		if (this.gameState != gameState)
		{
			this.gameState = gameState;

			MainWindow.getInstance().setLblGameStateText(gameState.toString());

			switch (gameState)
			{
				case AwaitingOpponentsGameField:
					reveiveGameField();
					break;
				case AwaitingPlayersGameField:
					break;
				case CheckOpponentsGameField:
					checkGameField();
					break;
				case CheckPlayersGameField:
					checkGameField();
					break;
				case GameOver_Draw:
					gameOver();
					break;
				case GameOver_Loose:
					gameOver();
					break;
				case GameOver_Win:
					gameOver();
					break;
				case SendingPlayersGameField:
					sendGameField();
					break;
				default: // NoGameActive, InitializingNewGame
					break;
			}
		}
	}
}
