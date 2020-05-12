package b2p.model;

import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.util.BitSet;
import java.util.List;

public interface IState {

    //
    int boardDimension = 9 * 9;

    // Turn getters and setters
    State.Turn getTurn();
    void setTurn(State.Turn turn);

    BitSet getBlackPawns();
    BitSet getWhitePawns() ;
    BitSet getKing();
    BitSet getBoard();

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
    int getHeuristicValueForPlayer(State.Turn player);

    // Utility functions
    IState clone();

}
