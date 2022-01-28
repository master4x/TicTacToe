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

	private void reveiveGameField()
	{
		this.gameField = NetworkHandler.getInstance().receiveArray();

		appendGameField();

		setGameState(GameState.CheckOpponentsGameField);
	}

	@SuppressWarnings("incomplete-switch")
	private void gameOver()
	{		
		switch (gameState)
		{
			case GameOver_Draw:
				break;
			case GameOver_Loose:
				break;
			case GameOver_Win:
				break;
		}

		MainWindow.getInstance().setLblPlayerInfoText(gameState.toString()); // TODO add nice formatting
		
		// TODO save CSV gameOver()
		
		setGameState(GameState.NoGameActive);
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

	private void appendGameField() // TODO clean rewrite appendGameField()
	{
		MainWindow mainWindow = MainWindow.getInstance();

		// TODO use symbols to display in GUI | 1-O: \u274C 2-X: \u25EF

		mainWindow.setLblGameField00Text(Integer.toString(gameField[0][0]));
		mainWindow.setLblGameField01Text(Integer.toString(gameField[0][1]));
		mainWindow.setLblGameField02Text(Integer.toString(gameField[0][2]));
		mainWindow.setLblGameField10Text(Integer.toString(gameField[1][0]));
		mainWindow.setLblGameField11Text(Integer.toString(gameField[1][1]));
		mainWindow.setLblGameField12Text(Integer.toString(gameField[1][2]));
		mainWindow.setLblGameField20Text(Integer.toString(gameField[2][0]));
		mainWindow.setLblGameField21Text(Integer.toString(gameField[2][1]));
		mainWindow.setLblGameField22Text(Integer.toString(gameField[2][2]));
	}

	private void checkGameField() // TODO clean rewrite checkGameField()
	{
		if (gameField[0][0] != 0 && gameField[0][1] != 0 && gameField[0][2] != 0
			&& gameField[1][0] != 0 && gameField[1][1] != 0 && gameField[1][2] != 0
			&& gameField[2][0] != 0 && gameField[2][1] != 0 && gameField[2][2] != 0
			&& gameField[0][0] != 0 && gameField[1][0] != 0 && gameField[2][2] != 0
			&& gameField[0][1] != 0 && gameField[1][1] != 0 && gameField[1][1] != 0
			&& gameField[0][2] != 0 && gameField[1][2] != 0 && gameField[1][2] != 0
			&& gameField[0][1] != 0 && gameField[1][1] != 0 && gameField[2][2] != 0
			&& gameField[0][2] != 0 && gameField[1][1] != 0 && gameField[2][0] != 0)
		{
			setGameState(GameState.GameOver_Draw);
		}
		else if (gameField[0][0] == 1 && gameField[0][1] == 1 && gameField[0][2] == 1
			|| gameField[1][0] == 1 && gameField[1][1] == 1 && gameField[1][2] == 1
			|| gameField[2][0] == 1 && gameField[2][1] == 1 && gameField[2][2] == 1
			|| gameField[0][0] == 1 && gameField[1][0] == 1 && gameField[2][2] == 1
			|| gameField[0][1] == 1 && gameField[1][1] == 1 && gameField[1][1] == 1
			|| gameField[0][2] == 1 && gameField[1][2] == 1 && gameField[1][2] == 1
			|| gameField[0][1] == 1 && gameField[1][1] == 1 && gameField[2][2] == 1
			|| gameField[0][2] == 1 && gameField[1][1] == 1 && gameField[2][0] == 1)
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
		else if (gameField[0][0] == 2 && gameField[0][1] == 2 && gameField[0][2] == 2
			|| gameField[1][0] == 2 && gameField[1][1] == 2 && gameField[1][2] == 2
			|| gameField[2][0] == 2 && gameField[2][1] == 2 && gameField[2][2] == 2
			|| gameField[0][0] == 2 && gameField[1][0] == 2 && gameField[2][2] == 2
			|| gameField[0][1] == 2 && gameField[1][1] == 2 && gameField[1][1] == 2
			|| gameField[0][2] == 2 && gameField[1][2] == 2 && gameField[1][2] == 2
			|| gameField[0][1] == 2 && gameField[1][1] == 2 && gameField[2][2] == 2
			|| gameField[0][2] == 2 && gameField[1][1] == 2 && gameField[2][0] == 2)
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
		else
		{
			if (gameState == GameState.CheckOpponentsGameField)
			{
				setGameState(GameState.AwaitingPlayersGameField);
			}
			else if (gameState == GameState.CheckPlayersGameField)
			{
				MainWindow.getInstance().setLblPlayerInfoText(new String());
				
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
					MainWindow.getInstance().setLblPlayerInfoText(gameState.toString()); // TODO nice formatting + extra func
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
				default: // NoGameActive, AwaitingPlayersGameField, InitializingNewGame
					break;
			}
		}
	}
}
