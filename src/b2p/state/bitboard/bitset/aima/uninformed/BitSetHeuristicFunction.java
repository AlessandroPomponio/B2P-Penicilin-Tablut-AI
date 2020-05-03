package b2p.state.bitboard.bitset.aima.uninformed;

import aima.core.search.framework.evalfunc.HeuristicFunction;
import b2p.state.bitboard.bitset.BitSetState;

public class BitSetHeuristicFunction implements HeuristicFunction {

    /*
     * Rimangono da gestire i nodi finali, in base a come viene implementata la strategia, è necessario maneggiare cor-
     * rettamente i nodi che soddisfano isWinningState() == True
     */
    @Override
    public double h(Object state) {
        if (state instanceof BitSetState)
        {
            return ((BitSetState) state).getHeuristicValue();
        }

        return Integer.MAX_VALUE;
    }
}
