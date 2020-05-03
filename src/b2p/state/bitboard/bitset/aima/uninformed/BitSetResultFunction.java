package b2p.state.bitboard.bitset.aima.uninformed;

import aima.core.agent.Action;
import aima.core.search.framework.problem.ResultFunction;
import b2p.model.IAction;
import b2p.state.bitboard.bitset.BitSetState;

import java.util.ArrayList;

public class BitSetResultFunction implements ResultFunction {

    @Override
    public Object result(Object state, Action action) {

        if (state instanceof BitSetState)
        {
            ArrayList<BitSetState> result = new ArrayList<>();
            BitSetState resultState = (BitSetState) ((BitSetState) state).clone();
            resultState.performMove((IAction) action);
            result.add(resultState);
            return result;
        }

        // supah dangerous
        return null;
    }
}
