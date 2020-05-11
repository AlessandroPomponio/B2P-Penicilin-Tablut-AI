package b2p.state.bitboard.bitset;

import b2p.model.IAction;
import b2p.model.IState;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

import java.util.ArrayList;
import java.util.BitSet;

public class BitSetState implements IState {

    //
    public static final int boardDimension = 9 * 9;

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
    public ArrayList<BitSetAction> getAvailablePawnMoves() {

        ArrayList<BitSetAction> moves;

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
    public ArrayList<BitSetAction> getAvailableKingMoves() {

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

        return whiteHeuristic();

    }

    public int getHeuristicValueForPlayer(Turn player) {

        if (player == Turn.BLACK)
            return -whiteHeuristic();

        return whiteHeuristic();
//        return whiteHeuristic();

    }

    private int blackHeuristic() {

//        if (BitSetMove.kingHasMoreThanOneEscapePath(this))
//            return Integer.MIN_VALUE;


//        if (turnAmt >= 30) {
//            return - (whitePawns.cardinality() + king.cardinality()) + BitSetMove.dangerToKing(this);
//        }


        //
        int pieceDifference =  blackPawns.cardinality() - (whitePawns.cardinality() + king.cardinality());

        //
        BitSet blacks = BitSetUtils.copy(blackPawns);
        blacks.and(BitSetPosition.blackStrategicCells);
        int strategicBlacks = blacks.cardinality();

        //
//        int movesToKingEscape = BitSetMove.movesNeededForKingEscape(this);
        // I TURNI SONO x2
        if (turnAmt < 6) {
            return strategicBlacks + (pieceDifference-7);
        }

        if (turnAmt > 15) {
            return strategicBlacks + (pieceDifference-7)+ 2*BitSetMove.dangerToKing(this);
        }

        return strategicBlacks + 2*(pieceDifference-7)+ BitSetMove.dangerToKing(this);

//        return (pieceDifference-7) + strategicBlacks + 5*BitSetMove.dangerToKing(this) -5*getAvailableKingMoves().size();

//
//        return (pieceDifference-8) + strategicBlacks * 20 - movesToKingEscape + BitSetMove.dangerToKing(this) * 3;

    }

    private int whiteHeuristic() {

//        if (BitSetMove.kingHasMoreThanOneEscapePath(this))
//            return Integer.MAX_VALUE;

//        int bestQuadrant = BitSetPosition.findTargetQuadrantForWhites(this);
//        int kingInBestQuadrantBonus = 0;
//        if (king.intersects(BitSetStartingBoard.quadrants[bestQuadrant]))
//            kingInBestQuadrantBonus = 5;

        //

        //
        int GAME_PROG = 100;
//        return (GAME_PROG - turnAmt) * 61 * pieceDifference - (turnAmt) * 31 * BitSetMove.dangerToKing(this) + (GAME_PROG - turnAmt)
//                * 23 * BitSetMove.whitePawnsAdjacentKing(this);
        if(turnAmt < 30) {
            int pieceDifference = 2 * whitePawns.cardinality() - blackPawns.cardinality();
            return 7 * pieceDifference - 5 * BitSetMove.dangerToKing(this)
                    - 3 * BitSetMove.diagonalBlackCouples(this); // + 3 * BitSetMove.positionWeights(this);
        }
        int pieceDifference = whitePawns.cardinality() + 8 - blackPawns.cardinality();
        return 7 * pieceDifference - 5 * BitSetMove.dangerToKing(this) -
                - 3 * BitSetMove.diagonalBlackCouples(this) + BitSetMove.blackPawnsOutOfCamps(this);
                 // + 3 * BitSetMove.positionWeights(this);

////      int movesToKingEscape = BitSetMove.movesNeededForKingEscape(this);
//        // i turni sono x2
//        if (turnAmt > 10) {
//            // whitePawns.cardinality()
//            // freeColumnsOrRows()
//            return 15 * pieceDifference - 7 * BitSetMove.dangerToKing(this) + 5 * BitSetMove.whitePawnsAdjacentKing(this); // + 3 * (BitSetMove.kingHasMoreThanOneEscapePath(this) ? 15 : 0);
//        }
//        // (5 * whitePawns.cardinality() - 3 * blackPawns.cardinality())
//        return 5 * whitePawns.cardinality() - 3 * blackPawns.cardinality() - BitSetMove.dangerToKing(this); //  + BitSetMove.whiteCellInStrategicPosition(this)

    }

//    @Override
//    public int getHeuristicValue() {
//
//        if (turn == Turn.BLACK) {
//            return blackHeuristic();
//        }
//
//        return whiteHeuristic();
//
//    }
//
//    private int blackHeuristic() {
//
//        //
//        int pieceDifference =  blackPawns.cardinality() - (whitePawns.cardinality() + king.cardinality());
//
//        //
////        BitSet blacks = BitSetUtils.copy(blackPawns);
////        blacks.and(BitSetPosition.blackStrategicCells);
////        int strategicBlacks = blacks.cardinality() * 50;
//
//        int escapeMalus = BitSetMove.kingEscapesInOneMove(this) * -10000;
//
//        //
//        return pieceDifference +  escapeMalus + BitSetMove.dangerToKing(this);
//
//    }
//
//    private int whiteHeuristic() {
//
//        //
//        int pieceDifference = whitePawns.cardinality() + king.cardinality() - blackPawns.cardinality();
//
//        int strategicWhites = 0;
//        int escapeBonus = BitSetMove.kingEscapesInOneMove(this) * 10000;
////        if (turnAmt < 3) {
////            BitSet whites = BitSetUtils.copy(whitePawns);
////            whites.and(BitSetPosition.blackStrategicCells);
////            strategicWhites = whites.cardinality() * 50;
////        } else
//
////        if (turnAmt > 4) {
////            return pieceDifference + 8 + escapeBonus - 2*BitSetMove.dangerToKing(this);
////        }
//
//        return 3*(pieceDifference + 8) + strategicWhites + escapeBonus - BitSetMove.dangerToKing(this);
//
//    }
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
