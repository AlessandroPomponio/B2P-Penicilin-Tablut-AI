package it.unibo.ai.didattica.competition.tablut.exceptions;

import it.unibo.ai.didattica.competition.tablut.domain.Action;

/**
 * This exception represent an action that is climbing over a pawn 
 * @author A.Piretti
 *
 */
public class ClimbingException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ClimbingException(Action a)
	{
		super("A pawn is tryng to climb over another pawn: "+a.toString());
	}

}
