package b2p.model;

import b2p.state.bitboard.bitset.B2PBitSet;
import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.util.List;

public interface IState {

    //
    int boardDimension = 9 * 9;

    // Turn getters and setters
    State.Turn getTurn();
    void setTurn(State.Turn turn);

    B2PBitSet getBlackPawns();
    B2PBitSet getWhitePawns() ;
    B2PBitSet getKing();
    B2PBitSet getBoard();

    // Win conditions
    boolean isWinningState();
    boolean blackHasWon();
    boolean whiteHasWon();

    // Move-related functions
    IState simulateMove(IAction action);
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
