package b2p.state.bitboard.bitset.aima;

import aima.core.agent.Action;
import aima.core.search.framework.problem.ActionsFunction;
import b2p.state.bitboard.bitset.BitSetAction;
import b2p.state.bitboard.bitset.BitSetState;
import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.util.HashSet;
import java.util.Set;

public class BitSetActionsFunction implements ActionsFunction {
    @Override
    public Set<Action> actions(Object state) {

        /*
         * return new HashSet<>((BitSetState) state.getAvailablePawnMoves());
         */

        if (state instanceof BitSetState)
        {
            BitSetState bitSetState = (BitSetState) state;
            return new HashSet<>(bitSetState.getAvailablePawnMoves());
        }
        return null;
    }
}
