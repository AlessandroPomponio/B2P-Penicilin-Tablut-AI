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
    private final B2PBitSet blackPawns;
    private final B2PBitSet whitePawns;
    private final B2PBitSet king;
    private final B2PBitSet board;

    // region Constructors
    public BitSetState(B2PBitSet blackPawns, B2PBitSet whitePawns, B2PBitSet king, Turn turn) {

        //
        this.turn = turn;
        this.blackPawns = blackPawns;
        this.whitePawns = whitePawns;
        this.king = king;

        //
        this.board = new B2PBitSet(boardDimension);
        board.or(blackPawns);
        board.or(whitePawns);
        board.or(king);

    }

    public BitSetState(B2PBitSet blackPawns, B2PBitSet whitePawns, B2PBitSet king, Turn turn, int turnAmt) {
        this(blackPawns, whitePawns, king, turn);
        this.turnAmt = turnAmt;
    }

    public BitSetState() {
        this(
                BitSetStartingBoard.blackStartingBitSet,
                BitSetStartingBoard.whiteStartingBitSet,
                BitSetStartingBoard.kingStartingBitSet,
                Turn.WHITE
        );
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

    public IState simulateMove(IAction action) {
        BitSetState result = (BitSetState) this.clone();
        result.performMove(action);
        return result;
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

        int kingPosition = king.nextSetBit(0);
        if (kingPosition == -1)
            return new ArrayList<>();

        return BitSetMove.getMovesForPawn(kingPosition, this);

    }
    //endregion

    //region Heuristics-related functions
    @Override
    public int getHeuristicValue() {
        return getHeuristicValueForPlayer(turn);
    }

    public int getHeuristicValueForPlayer(Turn player) {

        if (player == Turn.BLACK)
            return blackHeuristic();

        return -blackHeuristic();

    }

    private int blackHeuristic() {

        //
        int pieceDifference =  blackPawns.cardinality() - (whitePawns.cardinality() + king.cardinality());
        int strategicBlacks = blackPawns.andResult(BitSetPosition.blackStrategicCells).cardinality();

        //
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
        return new BitSetState(blackPawns.clone(),
                whitePawns.clone(),
                king.clone(),
                turn,
                turnAmt);
    }
    //

    //region Getters and setters
    public B2PBitSet getBlackPawns() {
        return blackPawns;
    }

    public B2PBitSet getWhitePawns() {
        return whitePawns;
    }

    public B2PBitSet getKing() {
        return king;
    }

    public B2PBitSet getBoard() {
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
