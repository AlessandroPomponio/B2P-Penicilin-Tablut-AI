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

    // Board state
    private BitSet board;

    //
    private Turn turn;

    // There are special areas on the board
    private BitSet camps;

    public BitSetState(Turn turn, BitSet blackPawns, BitSet whitePawns, BitSet king) {
        this.turn = turn;
        this.blackPawns = blackPawns;
        this.whitePawns = whitePawns;
        this.king = king;
        this.board = new BitSet(boardDimension);

        board.or(blackPawns);
        board.or(whitePawns);
        board.or(king);
    }

    public BitSetState() {
        this(
                Turn.WHITE,
                BitSetStartingBoard.blackStartingBitSet,
                BitSetStartingBoard.whiteStartingBitSet,
                BitSetStartingBoard.kingStartingBitSet
        );
    }

    //region Turn getter and setter
    @Override
    public Turn getTurn() {
        return this.turn;
    }

    @Override
    public void setTurn(Turn turn) {
        this.turn = turn;
    }
    //endregion

    //region bitset getters

    public BitSet getBlackPawns() {
        return blackPawns;
    }

    public BitSet getWhitePawns() {
        return whitePawns;
    }

    public BitSet getKing() {
        return king;
    }

    public BitSet getBoard() {
        return board;
    }
    //endregion

    //region Win conditions
    @Override
    public boolean isWinningState() {
        return blackHasWon() || whiteHasWon();
    }

    @Override
    public boolean blackHasWon() {
        return king.isEmpty();
    }

    @Override
    public boolean whiteHasWon() {
        BitSet mask = BitSetUtils.copy(king);
        mask.and(BitSetPosition.escape);
        return mask.cardinality() == 1;
    }
    //endregion

    //region Move-related functions
    @Override
    public void performMove(IAction action) {
        BitSetPosition from = BitSetPosition.valueOf(action.getFrom());
        BitSetPosition to = BitSetPosition.valueOf(action.getFrom());
        performMove(from.ordinal(), to.ordinal());
    }

    @Override
    public void performMove(int from, int to) {

        board.clear(from);
        board.set(to);

        // It's easier to handle blacks, as there's no king
        if (turn == Turn.BLACK) {
            blackPawns.clear(from);
            blackPawns.set(to);
            //TODO: CHECK CAPTURES
            turn = Turn.WHITE;
            //TODO: UPDATE BOARD
            return;
        }

        // Check whether it's the king moving or not
        if (king.get(from)) {
            king.clear(from);
            king.set(to);
        } else {
            whitePawns.clear(from);
            whitePawns.set(to);
        }

        //TODO: CHECK CAPTURES
        //TODO: UPDATE BOARD
        turn = Turn.BLACK;

    }

    @Override
    public void undoMove(IAction action) {



    }

    @Override
    public List<IAction> getCurrentMoves() {
        return null;
    }

    @Override
    public List<IAction> getKingMoves() {
        return null;
    }
    //endregion

    //region Heuristics-related functions
    @Override
    public int getHeuristicValue() {
        return 0;
    }
    //endregion

    // Utility functions
    @Override
    public IState clone() {
        return new BitSetState(this.turn, this.blackPawns, this.whitePawns, this.king);
    }
    //

}
