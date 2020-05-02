package b2p.state.bitboard.bitset.aima;

import aima.core.search.framework.problem.GoalTest;
import b2p.state.bitboard.bitset.BitSetState;

public class BitSetGoalTest implements GoalTest {

    @Override
    public boolean isGoalState(Object state) {
        if(state instanceof BitSetState)
        {
            BitSetState bitSetState = (BitSetState) state;
            return bitSetState.isWinningState();
        }

        return false;
    }
}
