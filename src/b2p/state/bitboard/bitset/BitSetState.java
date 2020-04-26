package b2p.state.bitboard.bitset;

import b2p.model.IAction;
import b2p.model.IState;
import b2p.model.Turn;

import java.util.BitSet;
import java.util.List;

public class BitSetState implements IState {

    //
    public static final int boardDimension = 9*9;

    // One bitboard per pawn type
    private BitSet blackPawns;
    private BitSet whitePawns;
    private BitSet king;

    //
    private Turn turn;

    // There are special areas on the board
    private BitSet camps;

    public BitSetState(Turn turn, BitSet blackPawns, BitSet whitePawns, BitSet king) {
        this.turn = turn;
        this.blackPawns = blackPawns;
        this.whitePawns = whitePawns;
        this.king = king;
    }

    public BitSetState() {
        this(
                Turn.WHITE,
                BitSetStartingBoard.blackStartingBitSet,
                BitSetStartingBoard.whiteStartingBitSet,
                BitSetStartingBoard.kingStartingBitSet
        );


    }

    @Override
    public void move(IAction action) {

    }

    @Override
    public void move(int from, int to) {

    }

    @Override
    public void unmove(IAction action) {

    }

    @Override
    public int getHeuristicValue() {
        return 0;
    }

    @Override
    public boolean isWinningState() {
        return hasBlackWon() || hasWhiteWon();
    }

    @Override
    public boolean hasBlackWon() {
        return false;
    }

    @Override
    public boolean hasWhiteWon() {
        return false;
    }

    @Override
    public List<IAction> getCurrentMoves() {
        return null;
    }

    @Override
    public Turn getTurn() {
        return null;
    }

    @Override
    public IState clone() {
        return new BitSetState(this.turn, this.blackPawns, this.whitePawns, this.king);
    }

    @Override
    public void setTurn(Turn turn) {

    }

    @Override
    public List<IAction> getKingMoves() {
        return null;
    }
}
