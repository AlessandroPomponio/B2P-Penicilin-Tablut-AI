package b2p.state.bitboard.bitset;

import b2p.model.IAction;
import b2p.model.IState;
import b2p.model.Turn;

import java.util.ArrayList;
import java.util.BitSet;

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
        return king.intersects(BitSetPosition.escape);
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
    public void performMove(String from, String to) {
        BitSetPosition pFrom = BitSetPosition.valueOf(from);
        BitSetPosition pTo = BitSetPosition.valueOf(to);
        performMove(pFrom.ordinal(), pTo.ordinal());
    }

    @Override
    public void performMove(int from, int to) {

        if (turn == Turn.BLACK) {

            // Move the pawn
            blackPawns.clear(from);
            blackPawns.set(to);

            // Check for captures
            BitSet captures = BitSetMove.getCapturedPawns(from, to, this);

            // Update board
            king.xor(captures);
            whitePawns.xor(captures);
            board.xor(captures);
            board.clear(from);
            board.set(to);

            // Next up: white player
            turn = Turn.WHITE;
            return;

        }

        // Move the pawn
        if (king.get(from)) {

            king.clear(from);
            king.set(to);

        } else {

            whitePawns.clear(from);
            whitePawns.set(to);

        }

        // Check for captures
        BitSet captures = BitSetMove.getCapturedPawns(from, to, this);

        //Update the baord
        blackPawns.xor(captures);
        board.xor(captures);
        board.clear(from);
        board.set(to);

        // Next up: black player
        turn = Turn.BLACK;

    }

    @Override
    public ArrayList<BitSetAction> getAvailablePawnMoves() {

        ArrayList<BitSetAction> moves = null;

        if (turn == Turn.BLACK) {

            moves = new ArrayList<>(blackPawns.cardinality() * 10);
            for (int i = blackPawns.nextSetBit(0); i >= 0; i = blackPawns.nextSetBit(i+1)) {
                moves.addAll(BitSetMove.getMovesForPawn(i, this));
            }

        } else {

            moves = new ArrayList<>(whitePawns.cardinality() * 10);
            for (int i = whitePawns.nextSetBit(0); i >= 0; i = whitePawns.nextSetBit(i+1)) {
                moves.addAll(BitSetMove.getMovesForPawn(i, this));
            }

        }

        return moves;

    }

    @Override
    public ArrayList<BitSetAction> getAvailableKingMoves() {
        return BitSetMove.getMovesForPawn(king.nextSetBit(0), this);
    }
    //endregion

    //region Heuristics-related functions
    @Override
    public int getHeuristicValue() {
        //TODO: AGGIUNGERE QUALCOSA
        return 0;
    }
    //endregion

    // Utility functions
    @Override
    public IState clone() {
        return new BitSetState(this.turn, this.blackPawns, this.whitePawns, this.king);
    }
    //

    //region Getters and setters
    public BitSet getBlackPawns() { return blackPawns; }

    public BitSet getWhitePawns() { return whitePawns; }

    public BitSet getKing() { return king; }

    public BitSet getBoard() {
        return board;
    }

    @Override
    public Turn getTurn() {
        return this.turn;
    }

    @Override
    public void setTurn(Turn turn) {
        this.turn = turn;
    }
    //endregion


}
