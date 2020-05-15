package b2p.state.bitboard.bitset;


import b2p.model.IAction;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

import java.util.Objects;

/**
 * This Class represents an action from a starting cell to another implementing {@link IAction}
 * @author Alessandro Buldini
 * @author Alessandro Pomponio
 * @author Federico Zanini
 */
public class BitSetAction implements IAction {

    /**
     * Represents a String for the cell from which the pawn moves
     */
    private final String from;
    /**
     * Represents a String for the final cell to which the pawn moves
     */
    private final String to;
    /**
     * Represents the current turn of the game
     */
    private final Turn turn;
    /**
     * Represents the heuristic value of the final state
     */
    private int value;

    /**
     * Constructor for BitSetAction Class
     * @param from is a String for the starting position
     * @param to is a String for the final position
     * @param turn is the current turn
     * @see Turn
     */
    public BitSetAction(String from, String to, Turn turn) {
        this.from = from;
        this.to = to;
        this.turn = turn;
        this.value = -1;
    }

    /**
     * Constructor for BitSetAction Class
     * @param from is a String for the starting position
     * @param to is a String for the final position
     * @param turn is the current turn
     * @param value is an Integer for the heuristic value
     * @see Turn
     */
    public BitSetAction(String from, String to, Turn turn, int value) {
        this.from = from;
        this.to = to;
        this.turn = turn;
        this.value = value;
    }

    /**
     * Method to access the private field from
     * @return Value of from
     */
    @Override
    public String getFrom() {
        return this.from;
    }

    /**
     * Method to access the private field to
     * @return Value of to
     */
    @Override
    public String getTo() {
        return this.to;
    }

    /**
     * Method to access the private field turn
     * @return Value of turn
     */
    @Override
    public Turn getTurn() {
        return this.turn;
    }

    /**
     * Method to access the private field value
     * @return Value of value
     */
    @Override
    public int getValue() {
        return this.value;
    }

    /**
     * Method to set the value of the private field value
     * @param value is an Integer data type
     */
    @Override
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BitSetAction{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", turn=" + turn +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BitSetAction that = (BitSetAction) o;
        return from.equals(that.from) &&
                to.equals(that.to) &&
                turn == that.turn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, turn);
    }

    @Override
    public int compareTo(IAction o) {
        return this.value - o.getValue();
    }
}
