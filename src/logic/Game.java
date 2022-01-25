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
	
	public void startGame(String ipAdress)
	{
		NetworkHandler.getInstance().newNetworkSocket(ipAdress);
	}
	
	private void gameOver()
	{
		//TODO print GameOver
		switch (gameState)
		{
			case GameOver_Draw:
				break;
			case GameOver_Loose:
				break;
			case GameOver_Win:
				break;
		}
		
		//TODO save CSV result
		
		setGameState(GameState.NoGameRunning);
	}
	
	private synchronized void checkPlayerMode()
	{
		//TODO check Input and change gameField
	}

	private void checkGameField()
	{
		boolean player1Wins = false, player2Wins = false;

		for (int i = 1; i < 3; i++)
		{
			if (gameField[0][0] == i && gameField[0][1] == i && gameField[0][2] == i || // top row
				gameField[1][0] == i && gameField[1][1] == i && gameField[1][2] == i || // middle row
				gameField[2][0] == i && gameField[2][1] == i && gameField[2][2] == i || // bottom row
				gameField[0][0] == i && gameField[1][0] == i && gameField[2][2] == i || // left column
				gameField[0][1] == i && gameField[1][1] == i && gameField[1][1] == i || // middle column
				gameField[0][2] == i && gameField[1][2] == i && gameField[1][2] == i || // right column
				gameField[0][1] == i && gameField[1][1] == i && gameField[2][2] == i || // TL to BR diagonal
				gameField[0][2] == i && gameField[1][1] == i && gameField[2][0] == i) // TR to BL diagonal
			{
				switch (i)
				{
					case 1:
						player1Wins = true;
						break;
					case 2:
						player2Wins = true;
						break;
				}
			}
		}

		if (player1Wins && player2Wins)
		{
			setGameState(GameState.GameOver_Draw);
		}
		else if (player1Wins || player2Wins)
		{
			if (player == Players.Player1)
			{
				if (player1Wins)
				{
					setGameState(GameState.GameOver_Win);
				}
				else
				{
					setGameState(GameState.GameOver_Loose);
				}
			}
			else if (player == Players.Player2)
			{
				if (player2Wins)
				{
					setGameState(GameState.GameOver_Win);
				}
				else
				{
					setGameState(GameState.GameOver_Loose);
				}
			}
		}
		else
		{
			if (gameState == GameState.CheckOpponentsGameField)
			{
				setGameState(GameState.WaitForPlayersGameField);
			}
			else if (gameState == GameState.CheckPlayersGameField)
			{
				setGameState(GameState.SendPlayersGameField);
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
