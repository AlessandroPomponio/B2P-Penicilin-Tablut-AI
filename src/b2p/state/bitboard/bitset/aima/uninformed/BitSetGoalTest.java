package b2p.state.bitboard.bitset.aima.uninformed;

import aima.core.search.framework.problem.GoalTest;
import b2p.state.bitboard.bitset.BitSetState;

public class BitSetGoalTest implements GoalTest {

    @Override
    public boolean isGoalState(Object state) {
        if(state instanceof BitSetState)
        {
            return ((BitSetState) state).isWinningState();
        }

        return false;
    }
}
