package b2p.state.bitboard.bitset.aima.adversarial;

import b2p.state.bitboard.bitset.BitSetAction;
import b2p.state.bitboard.bitset.BitSetState;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

import java.util.List;

public class TablutGame implements IGame<BitSetState, BitSetAction, Turn> {

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
        BitSetState result = (BitSetState) bitSetState.clone();
        result.performMove(bitSetAction);
        return result;
    }

    @Override
    public boolean isTerminal(BitSetState bitSetState) {
        return bitSetState.isWinningState();
    }

    @Override
    public int getUtility(BitSetState bitSetState, Turn turn) {

        if (turn == Turn.BLACK) {
            if (bitSetState.whiteHasWon())
                return Integer.MIN_VALUE;
            else if (bitSetState.blackHasWon())
                return Integer.MAX_VALUE;
        }
        else {
            if (bitSetState.whiteHasWon())
                return Integer.MAX_VALUE;
            else if (bitSetState.blackHasWon())
                return Integer.MIN_VALUE;

        }
        return bitSetState.getHeuristicValue();
    }
}
