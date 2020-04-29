package b2p.model;

import b2p.state.bitboard.bitset.BitSetAction;

import java.util.ArrayList;

public interface IState {

    // Turn getters and setters
    Turn getTurn();
    void setTurn(Turn turn);

    // Win conditions
    boolean isWinningState();
    boolean blackHasWon();
    boolean whiteHasWon();

    // Move-related functions
    void performMove(IAction action);
    void performMove(String from, String to);
    void performMove(int from, int to);
    ArrayList<BitSetAction> getAvailablePawnMoves();
    ArrayList<BitSetAction> getAvailableKingMoves();

    // Heuristics-related functions
    int getHeuristicValue();

    // Utility functions
    IState clone();

}
