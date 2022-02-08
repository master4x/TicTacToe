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
	private GameState gameState = GameState.NoGameActive;
	private Players player;

	/*
	 * Constructor
	 */
	private Game()
	{
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

		// only runs after a game restart
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
				setGameState(GameState.AwaitingOpponentsGameField);
				break;
		}
	}

	private void sendGameField()
	{
		NetworkHandler.getInstance().sendArray(gameField);

		setGameState(GameState.CheckPlayersGameField);
	}

	private void receiveGameField()
	{
		// create and run network receiver thread
		NetworkHandler networkHandler = NetworkHandler.getInstance();
		networkHandler.networkHandlerThread = new Thread(networkHandler);
		networkHandler.networkHandlerThread.start();

		appendGameField();

		//setGameState(GameState.CheckOpponentsGameField);
	}

	private void gameOver()
	{
		appendGameField();

		clearGameField();

		FileIOHandler.getInstance().addSessionInfo(gameState.toString(), NetworkHandler.getInstance().getOpponentIp());

		NetworkHandler.getInstance().closeNetworkSocket();

		MainWindow.getInstance().activateInputs();
	}

	public void applyPlayerMove(int row, int column)
	{
		// check current GameState for action call
		if (gameState == GameState.AwaitingPlayersGameField)
		{
			// prevent multiple player moves at the same time
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
		for (int n = 0; n < 3; n++)
		{
			for (int m = 0; m < 3; m++)
			{
				switch (gameField[n][m])
				{
					case 0:
						MainWindow.getInstance().setBtnGameFieldText(n, m, null);
						break;
					case 1:
						MainWindow.getInstance().setBtnGameFieldText(n, m, "X");
						break;
					case 2:
						MainWindow.getInstance().setBtnGameFieldText(n, m, "O");
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

	private void checkGameField()
	{
		// check game field for win by player one
		if (gameField[0][0] == 1 && gameField[0][1] == 1 && gameField[0][2] == 1 // top row
			|| gameField[1][0] == 1 && gameField[1][1] == 1 && gameField[1][2] == 1 // middle row
			|| gameField[2][0] == 1 && gameField[2][1] == 1 && gameField[2][2] == 1 // bottom row
			|| gameField[0][0] == 1 && gameField[1][0] == 1 && gameField[2][0] == 1 // left column
			|| gameField[0][1] == 1 && gameField[1][1] == 1 && gameField[2][1] == 1 // middle column
			|| gameField[0][2] == 1 && gameField[1][2] == 1 && gameField[2][2] == 1 // right column
			|| gameField[0][0] == 1 && gameField[1][1] == 1 && gameField[2][2] == 1 // TL to BR
			|| gameField[2][0] == 1 && gameField[1][1] == 1 && gameField[0][2] == 1) // TR to BL
		{
			// check current player and change game state
			if (player == Players.Player1)
			{
				setGameState(GameState.GameOver_Win);
			}
			else
			{
				setGameState(GameState.GameOver_Loose);
			}
		}

		// check game field for win by player two
		else if (gameField[0][0] == 2 && gameField[0][1] == 2 && gameField[0][2] == 2 // top row
			|| gameField[1][0] == 2 && gameField[1][1] == 2 && gameField[1][2] == 2 // middle row
			|| gameField[2][0] == 2 && gameField[2][1] == 2 && gameField[2][2] == 2 // bottom row
			|| gameField[0][0] == 2 && gameField[1][0] == 2 && gameField[2][0] == 2 // left column
			|| gameField[0][1] == 2 && gameField[1][1] == 2 && gameField[2][1] == 2 // middle column
			|| gameField[0][2] == 2 && gameField[1][2] == 2 && gameField[2][2] == 2 // right column
			|| gameField[0][0] == 2 && gameField[1][1] == 2 && gameField[2][2] == 2 // TL to BR
			|| gameField[2][0] == 2 && gameField[1][1] == 2 && gameField[0][2] == 2) // TR to BL
		{
			// check current player and change game state
			if (player == Players.Player2)
			{
				setGameState(GameState.GameOver_Win);
			}
			else
			{
				setGameState(GameState.GameOver_Loose);
			}
		}

		// check game field for draw
		else if (gameField[0][0] != 0 && gameField[0][1] != 0 && gameField[0][2] != 0 // top row
			&& gameField[1][0] != 0 && gameField[1][1] != 0 && gameField[1][2] != 0 // middle row
			&& gameField[2][0] != 0 && gameField[2][1] != 0 && gameField[2][2] != 0) // bottom row
		{
			setGameState(GameState.GameOver_Draw);
		}

		// no win/loose/draw
		else
		{
			// continue when opponents game field is checked
			if (gameState == GameState.CheckOpponentsGameField)
			{
				setGameState(GameState.AwaitingPlayersGameField);
			}

			// continue when players game field is checked
			else if (gameState == GameState.CheckPlayersGameField)
			{
				setGameState(GameState.AwaitingOpponentsGameField);
			}
		}
	}

	public void setGameState(GameState gameState)
	{
		this.gameState = gameState;

		MainWindow.getInstance().setLblGameStateText(gameState.toString());

		switch (gameState)
		{
			case AwaitingOpponentsGameField:
				receiveGameField();
				break;
			case AwaitingPlayersGameField:
				break;
			case CheckOpponentsGameField:
				checkGameField();
				break;
			case CheckPlayersGameField:
				checkGameField();
				break;
			case SendingPlayersGameField:
				sendGameField();
				break;
			case GameOver_Draw:
			case GameOver_Loose:
			case GameOver_Win:
				gameOver();
				break;
			default: // NoGameActive, InitializingNewGame
				break;
		}
	}

	public void setGameField(int[][] gameField)
	{
		this.gameField = gameField;
	}

	public GameState getGameState()
	{
		return this.gameState;
	}
}
