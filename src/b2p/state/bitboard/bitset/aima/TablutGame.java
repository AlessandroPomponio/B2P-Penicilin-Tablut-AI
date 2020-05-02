package b2p.state.bitboard.bitset.aima;

import aima.core.search.adversarial.Game;
import b2p.state.bitboard.bitset.BitSetAction;
import b2p.state.bitboard.bitset.BitSetState;
import b2p.state.bitboard.bitset.BitSetUtils;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

import java.util.ArrayList;
import java.util.List;

public class TablutGame implements Game<BitSetState, BitSetAction, Turn> {

    @Override
    public BitSetState getInitialState() {
        return new BitSetState();
    }

    @Override
    public Turn[] getPlayers() {
        return new Turn[] {Turn.WHITE, Turn.BLACK};
    }

    @Override
    public Turn getPlayer(BitSetState bitSetState) {
        return bitSetState.getTurn();
    }

    @Override
    public List<BitSetAction> getActions(BitSetState bitSetState) {
        return bitSetState.getAvailablePawnMoves();
    }

    @Override
    public BitSetState getResult(BitSetState bitSetState, BitSetAction bitSetAction) {
        return ((ArrayList<BitSetState>) new BitSetResultFunction().result(bitSetState, bitSetAction)).get(0);
    }

    @Override
    public boolean isTerminal(BitSetState bitSetState) {
        return new BitSetGoalTest().isGoalState(bitSetState);
    }

    @Override
    public double getUtility(BitSetState bitSetState, Turn turn) {
        if(bitSetState.getTurn() != turn) {
            System.out.println("ILLEGAL ARGUMENT");
            System.out.println("EXPECTED TURN: " + turn + " HAD: " + bitSetState.getTurn());
            System.out.println("BLACK:");
            System.out.println(BitSetUtils.toBitString(bitSetState.getBlackPawns()));
            System.out.println("WHITE:");
            System.out.println(BitSetUtils.toBitString(bitSetState.getWhitePawns()));
            System.out.println("KING:");
            System.out.println(BitSetUtils.toBitString(bitSetState.getKing()));
            throw new IllegalArgumentException();
        }
        return bitSetState.getHeuristicValue();
    }
}
