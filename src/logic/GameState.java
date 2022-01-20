package logic;

/**
 * @author Leon Kelle
 * @class AGB\AH911
 * @project TicTacToe
 */

public enum GameState
{
	None,
	ConnectingWithOpponent,
	WaitForOpponentsGameField,
	CheckOpponentsGameField,
	GameOver_Loose,
	GameOver_Draw,
	WaitForPlayersGameField,
	CheckPlayersGameField,
	GameOver_Win,
	SendPlayersGameField
}
