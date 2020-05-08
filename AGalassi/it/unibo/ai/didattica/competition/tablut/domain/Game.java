package it.unibo.ai.didattica.competition.tablut.domain;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.exceptions.ActionException;
import it.unibo.ai.didattica.competition.tablut.exceptions.BoardException;
import it.unibo.ai.didattica.competition.tablut.exceptions.CitadelException;
import it.unibo.ai.didattica.competition.tablut.exceptions.ClimbingCitadelException;
import it.unibo.ai.didattica.competition.tablut.exceptions.ClimbingException;
import it.unibo.ai.didattica.competition.tablut.exceptions.DiagonalException;
import it.unibo.ai.didattica.competition.tablut.exceptions.OccupitedException;
import it.unibo.ai.didattica.competition.tablut.exceptions.PawnException;
import it.unibo.ai.didattica.competition.tablut.exceptions.StopException;
import it.unibo.ai.didattica.competition.tablut.exceptions.ThroneException;

/**
 * Contains the rules of the game
 *
 */
public interface Game {

	/**
	 * This method checks an action in a state: if it is correct the state is
	 * going to be changed, if it is wrong it throws a specific exception
	 * 
	 * @param state
	 *            the state of the game
	 * @param a
	 *            the action to be analyzed
	 * @return the new state of the game
	 * @throws BoardException
	 *             try to move a pawn out of the board
	 * @throws ActionException
	 *             the format of the action is wrong
	 * @throws StopException
	 *             try to not move any pawn
	 * @throws PawnException
	 *             try to move an enemy pawn
	 * @throws DiagonalException
	 *             try to move a pawn diagonally
	 * @throws ClimbingException
	 *             try to climb over another pawn
	 * @throws ThroneException
	 *             try to move a pawn into the throne boxe
	 * @throws OccupitedException
	 *             try to move a pawn into an ccupited box
	 * @throws ClimbingCitadelException
	 * @throws CitadelException
	 */
	public State checkMove(State state, Action a)
			throws BoardException, ActionException, StopException, PawnException, DiagonalException, ClimbingException,
			ThroneException, OccupitedException, ClimbingCitadelException, CitadelException;

	public void endGame(State state);
}
