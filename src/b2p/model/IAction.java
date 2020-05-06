package b2p.model;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public interface IAction extends Comparable<IAction> {
    String getFrom();
    String getTo();
    State.Turn getTurn();
    int getValue();
    void setValue(int value);
}
