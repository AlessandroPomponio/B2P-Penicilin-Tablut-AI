package b2p.state.bitboard.bitset.aima;

import aima.core.agent.Action;
import aima.core.search.framework.problem.ResultFunction;
import b2p.model.IAction;
import b2p.state.bitboard.bitset.BitSetState;

import java.util.ArrayList;

public class BitSetResultFunction implements ResultFunction {

    @Override
    public Object result(Object state, Action action) {

        /*
         * Ci sono problemi con questa funzione
         */
        if (state instanceof BitSetState)
        {
            ArrayList<BitSetState> result = new ArrayList<>();
            BitSetState bitSetState = (BitSetState) state;
            BitSetState resultState = (BitSetState) bitSetState.clone();
            resultState.performMove((IAction) action);
            result.add(resultState);
            return result;
        }

        return null;
    }
}
