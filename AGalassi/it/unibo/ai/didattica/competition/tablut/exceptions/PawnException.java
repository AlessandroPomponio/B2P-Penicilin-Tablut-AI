package it.unibo.ai.didattica.competition.tablut.exceptions;

import it.unibo.ai.didattica.competition.tablut.domain.Action;

/**
 * This exception represent an action of a player that is trying to move an enemy or an empty pawn
 * @author A.Piretti
 *
 */
public class PawnException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public PawnException(Action a)
	{
		super("The player is tryng to move a wrong pawn: "+a.toString());
	}

}
