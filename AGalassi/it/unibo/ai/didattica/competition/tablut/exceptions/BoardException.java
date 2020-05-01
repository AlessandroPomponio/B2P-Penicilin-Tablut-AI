package it.unibo.ai.didattica.competition.tablut.exceptions;

import it.unibo.ai.didattica.competition.tablut.domain.Action;

/**
 * This exception represent an action that is trying to move out of the board
 * @author A.Piretti
 *
 */
public class BoardException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public BoardException(Action a)
	{
		super("The move is out of the board: "+a.toString());
	}

}
