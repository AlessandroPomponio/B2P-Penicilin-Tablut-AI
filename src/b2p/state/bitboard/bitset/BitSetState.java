package b2p.state.bitboard.bitset;

import b2p.model.IAction;
import b2p.model.IState;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class BitSetState implements IState {

    //
    private Turn turn;
    private int turnAmt = 0;

    //
    private final BitSet blackPawns;
    private final BitSet whitePawns;
    private final BitSet king;
    private final BitSet board;

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

    public BitSetState(Turn turn, BitSet blackPawns, BitSet whitePawns, BitSet king, int turnAmt) {
        this(turn, blackPawns, whitePawns, king);
        this.turnAmt = turnAmt;
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
        BitSetPosition to = BitSetPosition.valueOf(action.getTo());
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

        BitSet captures;
        turnAmt++;

        if (turn == Turn.BLACK) {

            blackPawns.clear(from);
            blackPawns.set(to);

            captures = BitSetMove.getCapturedPawns(from, to, this);
            king.andNot(captures);
            whitePawns.andNot(captures);
            turn = Turn.WHITE;

        } else {

            if (king.get(from)) {
                king.clear(from);
                king.set(to);
            } else {
                whitePawns.clear(from);
                whitePawns.set(to);
            }

            captures = BitSetMove.getCapturedPawns(from, to, this);
            blackPawns.andNot(captures);
            turn = Turn.BLACK;

        }

        board.clear(from);
        board.set(to);
        board.andNot(captures);

    }

    @Override
    public List<IAction> getAvailablePawnMoves() {

        List<IAction> moves;

        if (turn == Turn.BLACK) {

            moves = new ArrayList<>(blackPawns.cardinality() * 10);
            for (int i = blackPawns.nextSetBit(0); i >= 0; i = blackPawns.nextSetBit(i + 1)) {
                moves.addAll(BitSetMove.getMovesForPawn(i, this));
            }

        } else {

            moves = new ArrayList<>(whitePawns.cardinality() * 10);
            for (int i = whitePawns.nextSetBit(0); i >= 0; i = whitePawns.nextSetBit(i + 1)) {
                moves.addAll(BitSetMove.getMovesForPawn(i, this));
            }

            moves.addAll(getAvailableKingMoves());

        }

        return moves;

    }

    @Override
    public List<IAction> getAvailableKingMoves() {

        // We need this for tests but do we need it
        // in production?
        int kingPosition = king.nextSetBit(0);
        if (kingPosition == -1)
            return new ArrayList<>();

        return BitSetMove.getMovesForPawn(kingPosition, this);

    }
    //endregion

    //region Heuristics-related functions
    @Override
    public int getHeuristicValue() {

        if (turn == Turn.BLACK) {
            return blackHeuristic();
        }

        return -blackHeuristic();

    }

    public int getHeuristicValueForPlayer(Turn player) {

        if (player == Turn.BLACK)
            return blackHeuristic();

        return -blackHeuristic();

    }

    private int blackHeuristic() {

        //
        int pieceDifference =  blackPawns.cardinality() - (whitePawns.cardinality() + king.cardinality());

        //
        BitSet blacks = BitSetUtils.copy(blackPawns);
        blacks.and(BitSetPosition.blackStrategicCells);
        int strategicBlacks = blacks.cardinality();

        return strategicBlacks + 2*(pieceDifference-7)+ BitSetMove.dangerToKing(this);

    }

    private int whiteHeuristic() {

        if(turnAmt < 30) {
            int pieceDifference = 2 * whitePawns.cardinality() - blackPawns.cardinality();
            return 7 * pieceDifference - 5 * BitSetMove.dangerToKing(this)
                    - 3 * BitSetMove.diagonalBlackCouples(this); // + 3 * BitSetMove.positionWeights(this);
        }
        int pieceDifference = whitePawns.cardinality() + 8 - blackPawns.cardinality();
        return 7 * pieceDifference - 5 * BitSetMove.dangerToKing(this) -
                - 3 * BitSetMove.diagonalBlackCouples(this) + BitSetMove.blackPawnsOutOfCamps(this);

    }
    //endregion

    // Utility functions
    @Override
    public IState clone() {

        BitSet blacks = BitSetUtils.copy(this.blackPawns);
        BitSet whites = BitSetUtils.copy(this.whitePawns);
        BitSet king = BitSetUtils.copy(this.king);
        Turn turn = this.turn;

        return new BitSetState(turn, blacks, whites, king, turnAmt);

    }
    //

    //region Getters and setters
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

    public static int getBoardDimension() { return boardDimension; }

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
