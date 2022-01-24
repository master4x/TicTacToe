package logic;

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

	private void checkGameField(int playerSymbol)
	{
		if (gameField[0][0] == playerSymbol && gameField[0][1] == playerSymbol && gameField[0][2] == playerSymbol || //top row
			gameField[1][0] == playerSymbol && gameField[1][1] == playerSymbol && gameField[1][2] == playerSymbol || //middle row
			gameField[2][0] == playerSymbol && gameField[2][1] == playerSymbol && gameField[2][2] == playerSymbol || //bottom row
			gameField[0][0] == playerSymbol && gameField[1][0] == playerSymbol && gameField[2][2] == playerSymbol || //left column
			gameField[0][1] == playerSymbol && gameField[1][1] == playerSymbol && gameField[1][1] == playerSymbol || //middle column
			gameField[0][2] == playerSymbol && gameField[1][2] == playerSymbol && gameField[1][2] == playerSymbol || //right column
			gameField[0][1] == playerSymbol && gameField[1][1] == playerSymbol && gameField[2][2] == playerSymbol || //TL to BR diagonal
			gameField[0][2] == playerSymbol && gameField[1][1] == playerSymbol && gameField[2][0] == playerSymbol) 	 //TR to BL diagonal
		{
			
		} else
		{
			
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
					break;
				case CheckPlayersGameField:
					break;
				case ConnectingWithOpponent:
					break;
				case GameOver_Draw:
					break;
				case GameOver_Loose:
					break;
				case GameOver_Win:
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
