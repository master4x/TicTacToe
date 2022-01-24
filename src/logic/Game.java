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
