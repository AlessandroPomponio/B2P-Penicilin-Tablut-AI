package b2p.model;

import java.util.List;

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
    List<IAction> getAvailablePawnMoves();
    List<IAction> getAvailableKingMoves();

    // Heuristics-related functions
    int getHeuristicValue();

    // Utility functions
    IState clone();

}
