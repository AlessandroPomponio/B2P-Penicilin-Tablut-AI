package b2p.model;

import b2p.state.bitboard.bitset.B2PBitSet;
import b2p.state.bitboard.bitset.BitSetPosition;
import b2p.state.bitboard.bitset.BitSetState;
import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.util.List;
/**
 * Interface defining the state of the game
 *
 * @author Alessandro Buldini
 * @author Alessandro Pomponio
 * @author Federico Zanini
 */
public interface IState {

    //
    /**
     * Integer representing the board dimension
     */
    int boardDimension = 9 * 9;

    // Turn getters and setters
    /**
     * Accesses the private field turn
     * @return the current turn
     * @see State.Turn
     */
    State.Turn getTurn();

    /**
     * Sets the private field turn value
     * @param turn is a Turn data type
     * @see State.Turn
     */
    void setTurn(State.Turn turn);

    /**
     * Accesses the private field black pawns
     * @return B2PBitSet containing black pawns
     * @see B2PBitSet
     */
    B2PBitSet getBlackPawns();

    /**
     * Accesses the private field white pawns
     * @return B2PBitSet containing white pawns
     * @see B2PBitSet
     */
    B2PBitSet getWhitePawns();

    /**
     * Accesses the private field king
     * @return B2PBitSet containing the king
     * @see B2PBitSet
     */
    B2PBitSet getKing();

    /**
     * Accesses the private field board
     * @return B2PBitSet containing the board
     * @see B2PBitSet
     */
    B2PBitSet getBoard();

    // Win conditions
    /**
     * Checks if the current state is a winning state
     * @return {@code True} if it is a winning state
     */
    boolean isWinningState();
    /**
     * Checks if the black has won the game
     * @return {@code True} if black has won
     */
    boolean blackHasWon();
    /**
     * Checks if the white has won the game
     * @return {@code True} if white has won
     */
    boolean whiteHasWon();

    // Move-related functions
    /**
     * Simulates a specified move from a given {@code IAction}
     * @param action IAction representing the action to simulate
     * @return A copy of the {@link BitSetState} after the move is performed
     * @see IAction
     */
    IState simulateMove(IAction action);

    /**
     * Performs a given action using {@link BitSetPosition} from and {@link BitSetPosition} to
     * @param action IAction representing the action to perform
     * @see IAction
     * @see BitSetPosition
     */
    void performMove(IAction action);

    /**
     * Performs a given action using a specified {@link BitSetPosition} from and a specified {@link BitSetPosition} to
     * @param from String representing the starting position
     * @param to String representing the final position
     * @see BitSetPosition
     */
    void performMove(String from, String to);

    /**
     * Performs a given action using a specified from and a specified to
     * @param from Integer representing the starting position
     * @param to Integer representing the final position
     */
    void performMove(int from, int to);

    /**
     * Gets all the available moves for the pawns
     * @return A {@link List} of possible actions
     * @see IAction
     */
    List<IAction> getAvailablePawnMoves();

    /**
     * Gets all the available moves for the king
     * @return A {@link List} of possible actions
     * @see IAction
     */
    List<IAction> getAvailableKingMoves();

    // Heuristics-related functions
    /**
     * Returns the heuristic value for a player in the current turn
     * @return the heuristic value for a player in the current turn
     * @see State.Turn
     */
    int getHeuristicValue();

    /**
     * Returns the heuristic value for a given player in the current turn
     * @param player is a Turn object
     * @return the heuristic value for a given player in the current turn
     * @see State.Turn
     */
    int getHeuristicValueForPlayer(State.Turn player);

    // Utility functions
    IState clone();

}
