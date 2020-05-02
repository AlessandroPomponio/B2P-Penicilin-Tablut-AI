package b2p.state.bitboard.bitset.aima;

import aima.core.search.adversarial.Game;
import b2p.state.bitboard.bitset.BitSetAction;
import b2p.state.bitboard.bitset.BitSetState;
import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.util.ArrayList;
import java.util.List;

public class TablutGame implements Game<BitSetState, BitSetAction, State.Turn> {

    @Override
    public BitSetState getInitialState() {
        return new BitSetState();
    }

    @Override
    public State.Turn[] getPlayers() {
        return State.Turn.values();
    }

    @Override
    public State.Turn getPlayer(BitSetState bitSetState) {
        return bitSetState.getTurn();
    }

    @Override
    public List<BitSetAction> getActions(BitSetState bitSetState) {
        return bitSetState.getAvailablePawnMoves();
    }

    /*
    TODO: verificare l'esattezza delle funzioni getResult, isTerminal e getUtility
     */

    @Override
    public BitSetState getResult(BitSetState bitSetState, BitSetAction bitSetAction) {
        return ((ArrayList<BitSetState>) new BitSetResultFunction().result(bitSetState, bitSetAction)).get(0);
    }

    @Override
    public boolean isTerminal(BitSetState bitSetState) {
        return new BitSetGoalTest().isGoalState(bitSetState);
    }

    @Override
    public double getUtility(BitSetState bitSetState, State.Turn turn) {
        return new BitSetHeuristicFunction().h(bitSetState);
    }
}
