package b2p.search.aima.minmax;

import b2p.state.bitboard.bitset.BitSetAction;
import b2p.state.bitboard.bitset.BitSetState;
import b2p.search.aima.TablutGame;
import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.util.concurrent.*;

public class MinMaxAlphaBetaSearch implements Callable<Integer> {

    //
    private IterativeDeepening strategy;

    //
    private TablutGame game;

    //
    private BitSetState state;
    private BitSetAction action;
    private State.Turn player;

    //
    private final int utilMin, utilMax;
    private final int depthLimit;

    //
    private int result;

    protected MinMaxAlphaBetaSearch(IterativeDeepening strategy, BitSetState state, BitSetAction action, State.Turn player) {

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
    public int maxValue(BitSetState state, State.Turn player, int alpha, int beta, int depth) {

        //
//        strategy.updateMetrics(depth);

        if (game.isTerminal(state) || depth >= depthLimit || strategy.getTimer().timeOutOccurred())
            return eval(state, player, depth);

        // The worst case for the maximizer is the minimum value
        int value = utilMin;

        for (BitSetAction action : game.getActions(state)) {

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
    public int minValue(BitSetState state, State.Turn player, int alpha, int beta, int depth) {

        //
//        strategy.updateMetrics(depth);

        if (game.isTerminal(state) || depth >= depthLimit || strategy.getTimer().timeOutOccurred())
            return eval(state, player, depth);

        // The worst case scenario for the minimizer is Maxvalue
        int value = utilMax;

        for (BitSetAction action : game.getActions(state)) {

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

    private int eval(BitSetState state, State.Turn player, int depth) {

        return game.getUtility(state, player);
//
//        if ((game.isTerminal(state) || depth >= strategy.getCurrDepthLimit()) && !strategy.getTimer().timeOutOccurred()) {
//            return game.getUtility(state, player);
//        }
//
//        if (player == strategy.getOurPlayer()) {
//            return Integer.MIN_VALUE;
//        }
//
//        return Integer.MAX_VALUE;

    }

    @Override
    public Integer call() throws Exception {
        return minValue(game.getResult(state, action), player, utilMin, utilMax, 1);
    }

}
