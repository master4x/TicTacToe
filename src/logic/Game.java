package logic;

/**
 * @author Leon Kelle
 * @class AGB\AH911
 * @project TicTacToe
 */

public class Game
{
	private int[][] gameField = new int[3][3];
	private GameState gameState;
	
	public void onGameStateChange(GameState gameState)
	{
		this.gameState = gameState;
	}
}
