package b2p.search.minimax;

import b2p.model.IAction;
import b2p.model.IState;
import b2p.state.bitboard.bitset.BitSetAction;
import b2p.utils.Timer;

import java.util.ArrayList;

public class MinMaxAlphaBeta {

    private final Timer timer;
    private int nodesExplored = 0;

    public MinMaxAlphaBeta(int timeout) {
        this.timer = new Timer(timeout);
    }

    public BitSetAction getBestMove(IState state, int depth, boolean isMaximizingPlayer) {

        // For metrics
        long elapsed = System.nanoTime();

        //
        BitSetAction action = search(state, depth, isMaximizingPlayer);

        // Print metrics
        elapsed = System.nanoTime() - elapsed;
        System.out.println("Min Max with Alpha/Beta pruning:");
        System.out.println("Nodes explored: " + nodesExplored);
        System.out.println("Elapsed: " + elapsed);
        System.out.println(action);

        return action;

    }

    private BitSetAction search(IState state, int depth, boolean isMaximizingPlayer) {

        // Default min/max, alpha/beta values
        double max = Integer.MIN_VALUE;
        double min = Integer.MAX_VALUE;
        double alpha = -10000;
        double beta = 10000;

        // Result
        BitSetAction bestMove = null;

        //
        ArrayList<BitSetAction> availableMoves = state.getAvailablePawnMoves();
        for (IAction move : availableMoves) {

            // TODO: rimuovere alla fine
            // Update metrics
            nodesExplored++;

            // Simulate the move
            IState s = state.clone();
            s.performMove(move);

            // Begin searching
            int valueFound = searchRecursively(depth, s, alpha, beta, !isMaximizingPlayer);

            if (isMaximizingPlayer) {

                if (valueFound > max) {
                    max = valueFound;
                    bestMove = new BitSetAction(move.getFrom(), move.getTo(), move.getTurn(), valueFound);
                }

            } else {

                if (valueFound < min) {
                    min = valueFound;
                    bestMove = new BitSetAction(move.getFrom(), move.getTo(), move.getTurn(), valueFound);
                }

            }

        }

        return bestMove;
    }

    private int searchRecursively(int depth, IState state, double alpha, double beta, boolean isMaximizingPlayer) {

        if (state.whiteHasWon() && state.getTurn().ordinal() == 1)
            return Integer.MAX_VALUE;
        else if (state.whiteHasWon() && state.getTurn().ordinal() == 0)
            return Integer.MIN_VALUE;
        else if (state.blackHasWon() && state.getTurn().ordinal() == 1)
            return Integer.MAX_VALUE;
        else if (state.blackHasWon() && state.getTurn().ordinal() == 0)
            return Integer.MIN_VALUE;

        if (depth == 0) {
            return state.getHeuristicValue();
        }

        ArrayList<BitSetAction> availableMoves = state.getAvailablePawnMoves();
        int bestValue;

        if (isMaximizingPlayer) {

            bestValue = -9999;
            for (IAction move : availableMoves) {

                // Update metrics
                nodesExplored++;

                // Simulate the move
                IState s = state.clone();
                s.performMove(move);

                // Find recursively the best move
                bestValue = Math.max(bestValue, searchRecursively(depth - 1, s, alpha, beta, false));
                alpha = Math.max(alpha, bestValue);
                if (beta <= alpha) {
                    return bestValue;
                }

            }

        } else {

            bestValue = 9999;
            for (IAction move : availableMoves) {

                // Update metrics
                nodesExplored++;

                // Simulate the move
                IState s = state.clone();
                s.performMove(move);

                // Find recursively the best move
                bestValue = Math.min(bestValue, searchRecursively(depth - 1, s, alpha, beta, true));
                beta = Math.min(beta, bestValue);
                if (beta <= alpha) {
                    return bestValue;
                }

            }

        }

        return bestValue;

    }

}
