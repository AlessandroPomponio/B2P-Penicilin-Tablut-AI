package b2p.model;

import it.unibo.ai.didattica.competition.tablut.domain.State;
/**
 * This Interface represents an action that can be performed during the game
 * @author Alessandro Buldini
 * @author Alessandro Pomponio
 * @author Federico Zanini
 */
public interface IAction extends Comparable<IAction> {
    /**
     * Accesses the private field from
     * @return Value of from
     */
    String getFrom();

    /**
     * Accesses the private field to
     * @return Value of to
     */
    String getTo();

    /**
     * Accesses the private field turn
     * @return Value of turn
     */
    State.Turn getTurn();

    /**
     * Accesses the private field value
     * @return Value of value
     */
    int getValue();

    /**
     * Sets the value of the private field value
     * @param value is an Integer data type
     */
    void setValue(int value);
}
