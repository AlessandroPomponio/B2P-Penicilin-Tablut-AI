package b2p.search.aima.minmax;

import b2p.model.IAction;
import b2p.model.IState;
import b2p.search.aima.TablutGame;
import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.util.concurrent.*;

public class MinMaxAlphaBetaSearch implements Callable<Integer> {

    //
    private final IterativeDeepening strategy;

    //
    private final TablutGame game;

    //
    private final IState state;
    private final IAction action;
    private final State.Turn player;

    //
    private final int utilMin, utilMax;
    private final int depthLimit;

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

    private int eval(IState state, State.Turn player) {
        return game.getUtility(state, player);
    }

    @Override
    public Integer call() {
        return minValue(game.getResult(state, action), player, utilMin, utilMax, 1);
    }

}
