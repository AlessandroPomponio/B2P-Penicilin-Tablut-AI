package b2p.search.aima;

import b2p.model.IAction;
import b2p.model.IState;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

import java.util.List;

/**
 * This interface defines what functions a Class Game must have
 *
 * @see <a href="https://github.com/aimacode/aima-java">Aima Java</a>
 *
 * @author Alessandro Buldini
 * @author Alessandro Pomponio
 * @author Federico Zanini
 */
public interface IGame {

    /**
     * Defines which player has to play at this stage of the game
     * @param state represents the current state of the game
     * @return the player who has to move
     * @see IState
     */
    Turn getPlayer(IState state);

    /**
     * Returns all the possible actions a player can do at this stage of the game
     * @param state represents the current state of the game
     * @return a list containing all possible actions
     * @see IState
     */
    List<IAction> getActions(IState state);

    /**
     * Returns the state of the game after performing the input action
     * @param state current state of the game
     * @param action action to perform
     * @return IState after the move has been performed
     * @see IState
     * @see IAction
     */
    IState getResult(IState state, IAction action);

    /**
     * Returns a boolean whether the current state is a terminal one
     * @param state IState to be evaluated
     * @return {@code true} if the current state is a terminal one
     * @see IState
     */
    boolean isTerminal(IState state);

    /**
     * Returns the heuristic value for the given state and player
     * @param state state of the game that needs to be evaluated
     * @param player player for which the heuristic function should be evaluated
     * @return the heuristic value of the input state
     * @see IState
     * @see Turn
     */
    int getUtility(IState state, Turn player);
}
