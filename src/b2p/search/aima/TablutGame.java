package b2p.search.aima;

import b2p.model.IAction;
import b2p.model.IState;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

import java.util.List;

/**
 * This class implements {@link IGame} for Tablut
 *
 * @author Alessandro Buldini
 * @author Alessandro Pomponio
 * @author Federico Zanini
 * @see <a href="https://github.com/aimacode/aima-java">Aima Java</a>
 */
public class TablutGame implements IGame {

    /**
     * Represents the state of the game
     */
    private IState state;

    /**
     * Creates a TablutGame given an {@link IState}
     *
     * @param state state of the game
     */
    public TablutGame(IState state) {
        this.state = state;
    }

    /**
     * Sets the private variable state to the given value
     *
     * @param state value that needs to be set
     */
    public void setState(IState state) {
        this.state = state;
    }

    /**
     * Defines which player has to play at this stage of the game
     *
     * @param state represents the current state of the game
     * @return the player who has to move
     * @see IState
     */
    @Override
    public Turn getPlayer(IState state) {
        return state.getTurn();
    }

    /**
     * Returns all the possible actions a player can do at this stage of the game
     *
     * @param state represents the current state of the game
     * @return a list containing all possible actions
     * @see IState
     */
    @Override
    public List<IAction> getActions(IState state) {
        return state.getAvailablePawnMoves();
    }

    /**
     * Returns the state of the game after performing the input action
     *
     * @param state  current state of the game
     * @param action action to perform
     * @return IState after the move has been performed
     * @see IState
     * @see IAction
     */
    @Override
    public IState getResult(IState state, IAction action) {
        IState result = state.clone();
        result.performMove(action);
        return result;
    }

    /**
     * Returns a boolean whether the current state is a terminal one
     *
     * @param state IState to be evaluated
     * @return {@code true} if the current state is a terminal one
     * @see IState
     */
    @Override
    public boolean isTerminal(IState state) {
        return state.isWinningState();
    }

    /**
     * Returns the heuristic value for the given state and player
     *
     * @param state state of the game that needs to be evaluated
     * @param turn  player for which the heuristic function should be evaluated
     * @return the heuristic value of the input state
     * @see IState
     * @see Turn
     */
    @Override
    public int getUtility(IState state, Turn turn) {

        if (turn == Turn.BLACK) {
            if (state.whiteHasWon())
                return Integer.MIN_VALUE;
            else if (state.blackHasWon())
                return Integer.MAX_VALUE;
        } else {
            if (state.whiteHasWon())
                return Integer.MAX_VALUE;
            else if (state.blackHasWon())
                return Integer.MIN_VALUE;

        }
        return state.getHeuristicValueForPlayer(turn);
    }

}
