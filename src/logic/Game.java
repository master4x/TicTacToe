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
	private GameState gameState = GameState.NoGameActive;
	private Players player;

	public void startGame(Players player, String ipAdress)
	{
		this.player = player;
		
		NetworkHandler.getInstance().newNetworkSocket(ipAdress);
	}
	
	private void sendGameField()
	{
		NetworkHandler.getInstance().sendArray(gameField);
		
		setGameState(GameState.CheckPlayersGameField);
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

		setGameState(GameState.NoGameActive);
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
			if (gameState == GameState.CheckPlayersGameField)
			{
				setGameState(GameState.AwaitingOpponentsGameField);
			}
			else if (gameState == GameState.CheckOpponentsGameField)
			{
				setGameState(GameState.AwaitingPlayersGameField);
			}
		}
	}

	public void setGameState(GameState gameState)
	{
		this.gameState = gameState;

		if (this.gameState != gameState)
		{
			switch (gameState)
			{
				case AwaitingOpponentsGameField:
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
				case InitializingNewGame:
					break;
				case NoGameActive:
					break;
				case SendingPlayersGameField:
					sendGameField();
					break;
				default:
					break;
			}
		}
	}
}
