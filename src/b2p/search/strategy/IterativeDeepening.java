package b2p.search.strategy;

import b2p.model.IAction;
import b2p.model.IState;
import b2p.search.minimax.MinMaxAlphaBeta;
import b2p.state.bitboard.bitset.BitSetAction;

public class IterativeDeepening implements Runnable{

    protected MinMaxAlphaBeta minimax;
    protected IState state;
    protected int startingDepth;
    protected boolean isMaximizingPlayer = true;

    protected BitSetAction selectedValuedAction;

    public IterativeDeepening(MinMaxAlphaBeta minimax, IState state, int startingDepth, boolean isMaximizingPlayer) {
        if (minimax == null)
            throw new IllegalArgumentException("Minimax can't be null");
        if (state == null)
            throw new IllegalArgumentException("State can't be null");
        if (startingDepth < 0)
            throw new IllegalArgumentException("Starting depth can't be negative");

        this.minimax = minimax;
        this.state = state;
        this.startingDepth = startingDepth;
        this.isMaximizingPlayer = isMaximizingPlayer;

        selectedValuedAction = null;
    }

    @Override
    public void run() {
        int depth = startingDepth;
        while (true) {
            selectedValuedAction = minimax.getBestMove(state, depth, isMaximizingPlayer);
            System.out.println("Level " + depth + " completed\n");
            if (selectedValuedAction.getValue() >= Integer.MAX_VALUE-3 || selectedValuedAction.getValue() <= Integer.MIN_VALUE+3)
                break;
            depth++;
        }
    }

    public BitSetAction getSelectedValuedAction() {
        return this.selectedValuedAction;
    }

    public MinMaxAlphaBeta getMinimax() {
        return this.minimax;
    }


}
