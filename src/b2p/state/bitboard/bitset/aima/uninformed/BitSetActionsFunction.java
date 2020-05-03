package b2p.state.bitboard.bitset.aima.uninformed;

import aima.core.agent.Action;
import aima.core.search.framework.problem.ActionsFunction;
import b2p.state.bitboard.bitset.BitSetState;

import java.util.HashSet;
import java.util.Set;

public class BitSetActionsFunction implements ActionsFunction {
    @Override
    public Set<Action> actions(Object state) {

        if (state instanceof BitSetState)
        {
            return new HashSet<>(((BitSetState) state).getAvailablePawnMoves());
        }
        return null;
    }
}
