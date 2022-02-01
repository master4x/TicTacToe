package logic;

/**
 * @author Leon Kelle
 * @class AGB\AH911
 * @project TicTacToe
 */

public enum GameState
{
	NoGameActive, // default State
	InitializingNewGame, // nothing; called by btn event
	AwaitingOpponentsGameField, // called after check of local
	CheckOpponentsGameField, // called by checkGameField
	GameOver_Loose, // called by CheckOpponentsGameField
	GameOver_Draw, // called by CheckOpponentsGameField/CheckPlayersGameField
	AwaitingPlayersGameField, // nothing; called by txt click event
	CheckPlayersGameField, // called by player move
	GameOver_Win, // called by CheckPlayersGameField
	SendingPlayersGameField, // called by start and move
}
