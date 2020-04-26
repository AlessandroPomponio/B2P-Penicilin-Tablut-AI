package b2p.model;

import java.util.List;

public interface IState {

    void move(IAction action);
    void move(int from, int to);
    void unmove (IAction action);
    int getHeuristicValue();
    boolean isWinningState();
    boolean hasBlackWon();
    boolean hasWhiteWon();

    List<IAction> getCurrentMoves();
    Turn getTurn();
    IState clone();
    void setTurn(Turn turn);

    List<IAction> getKingMoves();

}
