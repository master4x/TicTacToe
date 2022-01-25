package logic;

import data.NetworkHandler;

/**
 * @author Leon Kelle
 * @class AGB\AH911
 * @project TicTacToe
 */

public class Game
{
	private int[][] gameField = new int[3][3];
	private GameState gameState = GameState.NoGameRunning;
	private Players player;

	public void startGame(Players player, String ipAdress)
	{
		this.player = player;
		
		NetworkHandler.getInstance().newNetworkSocket(ipAdress);
	}

	@SuppressWarnings("incomplete-switch")
	private void gameOver()
	{
		// TODO print GameOver
		switch (gameState)
		{
			case GameOver_Draw:
				break;
			case GameOver_Loose:
				break;
			case GameOver_Win:
				break;
		}

		// TODO save CSV result

		setGameState(GameState.NoGameRunning);
	}

	private synchronized void checkPlayerMove()
	{
		// TODO check Input and change gameField
	}

	private void checkGameField()
	{
		if (gameField[0][0] != 0 && gameField[0][1] != 0 && gameField[0][2] != 0 ||
			gameField[1][0] != 0 && gameField[1][1] != 0 && gameField[1][2] != 0 ||
			gameField[2][0] != 0 && gameField[2][1] != 0 && gameField[2][2] != 0 ||
			gameField[0][0] != 0 && gameField[1][0] != 0 && gameField[2][2] != 0 ||
			gameField[0][1] != 0 && gameField[1][1] != 0 && gameField[1][1] != 0 ||
			gameField[0][2] != 0 && gameField[1][2] != 0 && gameField[1][2] != 0 ||
			gameField[0][1] != 0 && gameField[1][1] != 0 && gameField[2][2] != 0 ||
			gameField[0][2] != 0 && gameField[1][1] != 0 && gameField[2][0] != 0)
		{
			setGameState(GameState.GameOver_Draw);
		}
		else if (gameField[0][0] == 1 && gameField[0][1] == 1 && gameField[0][2] == 1 ||
			gameField[1][0] == 1 && gameField[1][1] == 1 && gameField[1][2] == 1 ||
			gameField[2][0] == 1 && gameField[2][1] == 1 && gameField[2][2] == 1 ||
			gameField[0][0] == 1 && gameField[1][0] == 1 && gameField[2][2] == 1 ||
			gameField[0][1] == 1 && gameField[1][1] == 1 && gameField[1][1] == 1 ||
			gameField[0][2] == 1 && gameField[1][2] == 1 && gameField[1][2] == 1 ||
			gameField[0][1] == 1 && gameField[1][1] == 1 && gameField[2][2] == 1 ||
			gameField[0][2] == 1 && gameField[1][1] == 1 && gameField[2][0] == 1)
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
		else if (gameField[0][0] == 2 && gameField[0][1] == 2 && gameField[0][2] == 2 ||
			gameField[1][0] == 2 && gameField[1][1] == 2 && gameField[1][2] == 2 ||
			gameField[2][0] == 2 && gameField[2][1] == 2 && gameField[2][2] == 2 ||
			gameField[0][0] == 2 && gameField[1][0] == 2 && gameField[2][2] == 2 ||
			gameField[0][1] == 2 && gameField[1][1] == 2 && gameField[1][1] == 2 ||
			gameField[0][2] == 2 && gameField[1][2] == 2 && gameField[1][2] == 2 ||
			gameField[0][1] == 2 && gameField[1][1] == 2 && gameField[2][2] == 2 ||
			gameField[0][2] == 2 && gameField[1][1] == 2 && gameField[2][0] == 2)
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
			setGameState(GameState.WaitForOpponentsGameField);
		}
	}

	public void setGameState(GameState gameState)
	{
		this.gameState = gameState;

		if (this.gameState != gameState)
		{
			switch (gameState)
			{
				case CheckOpponentsGameField:
					checkGameField();
					break;
				case CheckPlayersGameField:
					checkGameField();
					break;
				case ConnectingWithOpponent:
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
				case NoGameRunning:
					break;
				case SendPlayersGameField:
					break;
				case WaitForOpponentsGameField:
					break;
				case WaitForPlayersGameField:
					break;
				default:
					break;
			}
		}
	}
}
