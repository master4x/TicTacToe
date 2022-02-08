package logic;

/**
 * @author Leon Kelle
 * @class AGB\AH911
 * @project TicTacToe
 */

public enum GameState
{
	AwaitingOpponentsGameField,
	AwaitingPlayersGameField,
	CheckOpponentsGameField,
	CheckPlayersGameField,
	GameOver_Draw,
	GameOver_Loose,
	GameOver_Win,
	InitializingNewGame,
	NoGameActive,
	SendingPlayersGameField
}
