package b2p.state.bitboard.bitset;

import b2p.model.IAction;
import b2p.model.IState;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * This Class wraps the game state into a {@link B2PBitSet} object
 *
 * @author Alessandro Buldini
 * @author Alessandro Pomponio
 * @author Federico Zanini
 */
public class BitSetState implements IState {

    //
    /**
     * Represents the turn
     */
    private Turn turn;
    /**
     * Represents the turn number
     */
    private int turnAmt = 0;

    //
    /**
     * {@link B2PBitSet} representing the black pawns
     */
    private final B2PBitSet blackPawns;
    /**
     * {@link B2PBitSet} representing the white pawns
     */
    private final B2PBitSet whitePawns;
    /**
     * {@link B2PBitSet} representing the king
     */
    private final B2PBitSet king;
    /**
     * {@link B2PBitSet} representing the board
     */
    private final B2PBitSet board;

    // region Constructors

    /**
     * Constructor method to create an instance of BitSetState from the given arguments
     * @param blackPawns the black pawns
     * @param whitePawns the white pawns
     * @param king the king
     * @param turn the board
     * @see B2PBitSet
     */
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

    /**
     * Constructor method to create an instance of BitSetState from the given arguments
     * @param blackPawns the black pawns
     * @param whitePawns the white pawns
     * @param king the king
     * @param turn the board
     * @param turnAmt the turn number
     * @see B2PBitSet
     */
    public BitSetState(B2PBitSet blackPawns, B2PBitSet whitePawns, B2PBitSet king, Turn turn, int turnAmt) {
        this(blackPawns, whitePawns, king, turn);
        this.turnAmt = turnAmt;
    }

    /**
     * Default constructor method. White player plays first.
     */
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

    /**
     * Checks if the current state is a winning state
     * @return {@code true} if it is a winning state
     */
    @Override
    public boolean isWinningState() {
        return blackHasWon() || whiteHasWon();
    }

    /**
     * Checks if the black has won the game
     * @return {@code true} if black has won
     */
    @Override
    public boolean blackHasWon() {
        return king.isEmpty();
    }

    /**
     * Checks if the white has won the game
     * @return {@code true} if white has won
     */
    @Override
    public boolean whiteHasWon() {
        return king.intersects(BitSetPosition.escape);
    }
    //endregion

    //region Move-related functions

    /**
     * Performs a given action using {@link BitSetPosition} from and {@link BitSetPosition} to
     * @param action the action to perform
     * @see IAction
     * @see BitSetPosition
     */
    @Override
    public void performMove(IAction action) {
        BitSetPosition from = BitSetPosition.valueOf(action.getFrom());
        BitSetPosition to = BitSetPosition.valueOf(action.getTo());
        performMove(from.ordinal(), to.ordinal());
    }

    /**
     * Performs a given action using a specified {@link BitSetPosition} from and a specified {@link BitSetPosition} to
     * @param from the starting position
     * @param to the final position
     * @see BitSetPosition
     */
    @Override
    public void performMove(String from, String to) {
        BitSetPosition pFrom = BitSetPosition.valueOf(from);
        BitSetPosition pTo = BitSetPosition.valueOf(to);
        performMove(pFrom.ordinal(), pTo.ordinal());
    }

    /**
     * Performs a given action using a specified from and a specified to
     * @param from the starting position
     * @param to the final position
     */
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

    /**
     * Simulates a specified move from a given {@code IAction}
     * @param action the action to simulate
     * @return A copy of the {@link BitSetState} after the move is performed
     * @see IAction
     */
    public IState simulateMove(IAction action) {
        BitSetState result = (BitSetState) this.clone();
        result.performMove(action);
        return result;
    }

    /**
     * Gets all the available moves for the pawns
     * @return A {@link List} of possible actions
     * @see IAction
     */
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

    /**
     * Gets all the available moves for the king
     * @return A {@link List} of possible actions
     * @see IAction
     */
    @Override
    public List<IAction> getAvailableKingMoves() {

        int kingPosition = king.nextSetBit(0);
        if (kingPosition == -1)
            return new ArrayList<>();

        return BitSetMove.getMovesForPawn(kingPosition, this);

    }
    //endregion

    //region Heuristics-related functions

    /**
     * Returns the heuristic value for a player in the current turn
     * @return the heuristic value for a player in the current turn
     * @see Turn
     */
    @Override
    public int getHeuristicValue() {
        return getHeuristicValueForPlayer(turn);
    }

    /**
     * Returns the heuristic value for a given player in the current turn
     * @param player the player for which the heuristic value is evaluated
     * @return the heuristic value for a given player in the current turn
     * @see Turn
     */
    public int getHeuristicValueForPlayer(Turn player) {

        if (player == Turn.BLACK)
            return blackHeuristic();

        return -blackHeuristic();

    }

    /**
     * Calculates the heuristic value for the black player
     * @return the heuristic value for the black player
     */
    private int blackHeuristic() {

        //
        int pieceDifference =  blackPawns.cardinality() - (whitePawns.cardinality() + king.cardinality());
        int strategicBlacks = blackPawns.andResult(BitSetPosition.blackStrategicCells).cardinality();

        //
        return strategicBlacks + 2*(pieceDifference-7)+ BitSetMove.dangerToKing(this);

    }

    /**
     * Calculates the heuristic value for the white player
     * @return the heuristic value for the white player
     */
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

    /**
     * Accesses the private field black pawns
     * @return {@link B2PBitSet} containing black pawns
     * @see B2PBitSet
     */
    public B2PBitSet getBlackPawns() {
        return blackPawns;
    }

    /**
     * Accesses the private field white pawns
     * @return {@link B2PBitSet} containing white pawns
     * @see B2PBitSet
     */
    public B2PBitSet getWhitePawns() {
        return whitePawns;
    }

    /**
     * Accesses the private field king
     * @return {@link B2PBitSet} containing the king
     * @see B2PBitSet
     */
    public B2PBitSet getKing() {
        return king;
    }

    /**
     * Accesses the private field board
     * @return {@link B2PBitSet} containing the board
     * @see B2PBitSet
     */
    public B2PBitSet getBoard() {
        return board;
    }

    /**
     * Accesses the private field turn
     * @return the current turn
     * @see Turn
     */
    @Override
    public Turn getTurn() {
        return this.turn;
    }

    /**
     * Method to set the private field turn value
     * @param turn the current turn
     * @see Turn
     */
    @Override
    public void setTurn(Turn turn) {
        this.turn = turn;
    }
    //endregion

}
