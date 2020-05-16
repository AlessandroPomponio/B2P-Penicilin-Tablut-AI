package b2p.search.aima.minmax;

import b2p.model.IAction;
import b2p.model.IState;
import b2p.search.aima.IAdversarialSearch;
import b2p.search.aima.TablutGame;
import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.util.concurrent.*;

/**
 * This class implements {@link Callable<Integer>} in order to run each possible initial move and detect the best one
 * according to the Iterative Deepening Minimax algorithm with alpha-beta pruning.
 *
 * @see <a href="https://github.com/aimacode/aima-java">Aima Java</a>
 *
 * @author Alessandro Buldini
 * @author Alessandro Pomponio
 * @author Federico Zanini
 */
public class MinMaxAlphaBetaSearch implements Callable<Integer> {

    //
    /**
     * Object that represents the Iterative Deepening strategy
     */
    private final IterativeDeepening strategy;

    //
    /**
     * Instance of the game that needs to be evaluated
     */
    private final TablutGame game;

    //
    /**
     * Current state of the game that needs to be evaluated
     */
    private final IState state;
    /**
     * Initial action based on which the function's decision tree is created
     */
    private final IAction action;
    /**
     * Player for which the decision needs to be made
     */
    private final State.Turn player;

    //
    /**
     * Minimum value the heuristic function can assume
     */
    private final int utilMin;
    /**
     * Maximum value the heuristic function can assume
     */
    private final int utilMax;
    /**
     * Depth limit set by the Iterative Deepening strategy
     */
    private final int depthLimit;

    /**
     * Builds a MinMaxAlphaBetaSearch object given the strategy, the current state of the game, the initial action and
     * the player for whom the best decision has to be made
     * @param strategy instance of Iterative Deepening strategy
     * @param state current state of the game
     * @param action initial action performed for this thread based on which the function's decision tree is created
     * @param player player for whom the best decision has to be made
     */
    protected MinMaxAlphaBetaSearch(IterativeDeepening strategy, IState state, IAction action, State.Turn player) {

        //
        super();

        //
        this.strategy = strategy;

        //
        game = new TablutGame(state);

        //
        this.state = state;
        this.action = action;
        this.player = player;

        //
        utilMax = strategy.getUtilMax();
        utilMin = strategy.getUtilMin();

        //
        depthLimit = strategy.getCurrDepthLimit();

    }

    /**
     * function needed to implement correctly the alpha-beta pruning: detects the maximum value between two possible states
     * and prunes all the sub-trees that won't be explored according to the current heuristic function
     * @param state current state of the game
     * @param player player that performs the move
     * @param alpha alpha value
     * @param beta beta value
     * @param depth current depth
     * @return maximum heuristic value between two states within the decision tree
     */
    // Maximizer for Minimax with alpha/beta pruning
    public int maxValue(IState state, State.Turn player, int alpha, int beta, int depth) {

        //
        strategy.updateMetrics(depth);

        //
        if (game.isTerminal(state) || depth >= depthLimit || strategy.getTimer().timeOutOccurred())
            return eval(state, player);

        // The worst case for the maximizer is the minimum value
        int value = utilMin;

        for (IAction action : game.getActions(state)) {

            // Min/Max depth-first
            int minimizerValue = minValue(game.getResult(state, action), player, alpha, beta, depth + 1);
            value = Math.max(value, minimizerValue);

            // Alpha/Beta pruning
            if (value >= beta)
                return value;

            // Update alpha value for maximizer
            alpha = Math.max(alpha, value);

        }

        return value;

    }

    /**
     * function needed to implement correctly the alpha-beta pruning: detects the minimum value between two possible states
     * and prunes all the sub-trees that won't be explored according to the current heuristic function
     * @param state current state of the game
     * @param player player that performs the move
     * @param alpha alpha value
     * @param beta beta value
     * @param depth current depth
     * @return minimum heuristic value between two states within the decision tree
     */
    // Minimizer for Minimax with alpha/beta pruning
    public int minValue(IState state, State.Turn player, int alpha, int beta, int depth) {

        //
        strategy.updateMetrics(depth);

        //
        if (game.isTerminal(state) || depth >= depthLimit || strategy.getTimer().timeOutOccurred())
            return eval(state, player);

        // The worst case scenario for the minimizer is Maxvalue
        int value = utilMax;

        for (IAction action : game.getActions(state)) {

            // Min/Max depth-first
            int maximizerValue = maxValue(game.getResult(state, action), player, alpha, beta, depth + 1);
            value = Math.min(value, maximizerValue);

            // Alpha/Beta pruning
            if (value <= alpha)
                return value;

            // Update beta value for minimizer
            beta = Math.min(beta, value);

        }

        return value;

    }

    /**
     * Evaluates a state according to TablutGame's given heuristic function
     * @param state current state of the game
     * @param player player for whom the heuristic value has to be calculated
     * @return an heuristic integer value
     * @see TablutGame
     */
    private int eval(IState state, State.Turn player) {
        return game.getUtility(state, player);
    }

    /**
     * Starts searching for the best decision to be made given an initial action
     * @return an integer containing the best heuristic value according to Iterative Deepening MiniMax search with
     * alpha-beta pruning
     */
    @Override
    public Integer call() {
        return minValue(game.getResult(state, action), player, utilMin, utilMax, 1);
    }

}
