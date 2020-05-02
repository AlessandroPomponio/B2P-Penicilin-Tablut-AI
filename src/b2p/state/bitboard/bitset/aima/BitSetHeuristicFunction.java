package b2p.state.bitboard.bitset.aima;

import aima.core.search.framework.evalfunc.HeuristicFunction;
import b2p.state.bitboard.bitset.BitSetState;

public class BitSetHeuristicFunction implements HeuristicFunction {

    @Override
    public double h(Object state) {
        if (state instanceof BitSetState)
        {
            BitSetState bitSetState = (BitSetState) state;
            return bitSetState.getHeuristicValue();
        }

        return Integer.MAX_VALUE;
    }
}
