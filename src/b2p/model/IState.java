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
     * Method to access the private field turn
     * @return the current turn
     * @see State.Turn
     */
    State.Turn getTurn();

    /**
     * Method to set the private field turn value
     * @param turn is a Turn data type
     * @see State.Turn
     */
    void setTurn(State.Turn turn);

    /**
     * Method to access the private field black pawns
     * @return B2PBitSet containing black pawns
     * @see B2PBitSet
     */
    B2PBitSet getBlackPawns();

    /**
     * Method to access the private field white pawns
     * @return B2PBitSet containing white pawns
     * @see B2PBitSet
     */
    B2PBitSet getWhitePawns();

    /**
     * Method to access the private field king
     * @return B2PBitSet containing the king
     * @see B2PBitSet
     */
    B2PBitSet getKing();

    /**
     * Method to access the private field board
     * @return B2PBitSet containing the board
     * @see B2PBitSet
     */
    B2PBitSet getBoard();

    // Win conditions
    /**
     * Method to check if the current state is a winning state
     * @return {@code True} if it is a winning state
     */
    boolean isWinningState();
    /**
     * Method to check if the black has won the game
     * @return {@code True} if black has won
     */
    boolean blackHasWon();
    /**
     * Method to check if the white has won the game
     * @return {@code True} if white has won
     */
    boolean whiteHasWon();

    // Move-related functions
    /**
     * Method used to simulate a specified move from a given {@code IAction}
     * @param action IAction representing the action to simulate
     * @return A copy of the {@link BitSetState} after the move is performed
     * @see IAction
     */
    IState simulateMove(IAction action);

    /**
     * Method to perform a given action using {@link BitSetPosition} from and {@link BitSetPosition} to
     * @param action IAction representing the action to perform
     * @see IAction
     * @see BitSetPosition
     */
    void performMove(IAction action);

    /**
     * Method to perform a given action using a specified {@link BitSetPosition} from and a specified {@link BitSetPosition} to
     * @param from String representing the starting position
     * @param to String representing the final position
     * @see BitSetPosition
     */
    void performMove(String from, String to);

    /**
     * Method to perform a given action using a specified from and a specified to
     * @param from Integer representing the starting position
     * @param to Integer representing the final position
     */
    void performMove(int from, int to);

    /**
     * Method to get all the available moves for the pawns
     * @return A {@link List} of possible actions
     * @see IAction
     */
    List<IAction> getAvailablePawnMoves();

    /**
     * Method to get all the available moves for the king
     * @return A {@link List} of possible actions
     * @see IAction
     */
    List<IAction> getAvailableKingMoves();

    // Heuristics-related functions
    /**
     * Method returning the heuristic value for a player in the current turn
     * @return the heuristic value for a player in the current turn
     * @see State.Turn
     */
    int getHeuristicValue();

    /**
     * Method returning the heuristic value for a given player in the current turn
     * @param player is a Turn object
     * @return the heuristic value for a given player in the current turn
     * @see State.Turn
     */
    int getHeuristicValueForPlayer(State.Turn player);

    // Utility functions
    IState clone();

}
