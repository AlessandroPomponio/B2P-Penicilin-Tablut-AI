package b2p.model;

import b2p.state.bitboard.bitset.BitSetAction;
import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.util.ArrayList;
import java.util.BitSet;

public interface IState {

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
    ArrayList<BitSetAction> getAvailablePawnMoves();
    ArrayList<BitSetAction> getAvailableKingMoves();

    // Heuristics-related functions
    int getHeuristicValue();
    int getHeuristicValueForPlayer(State.Turn player);

    // Utility functions
    IState clone();

}
