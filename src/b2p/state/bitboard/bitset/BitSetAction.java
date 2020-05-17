package b2p.state.bitboard.bitset;


import b2p.model.IAction;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

import java.util.Objects;

/**
 * This Class represents an action in a game.
 *
 * @author Alessandro Buldini
 * @author Alessandro Pomponio
 * @author Federico Zanini
 */
public class BitSetAction implements IAction {

    /**
     * Represents the cell from which the pawn moves
     */
    private final String from;

    /**
     * Represents the final cell to which the pawn moves
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
     * Constructor for the BitSetAction Class
     *
     * @param from the starting position
     * @param to   the final position
     * @param turn the current turn
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
     *
     * @param from  the starting position
     * @param to    the final position
     * @param turn  the current turn
     * @param value the heuristic value
     * @see Turn
     */
    public BitSetAction(String from, String to, Turn turn, int value) {
        this.from = from;
        this.to = to;
        this.turn = turn;
        this.value = value;
    }

    /**
     * Accesses the private field from
     *
     * @return Value of from
     */
    @Override
    public String getFrom() {
        return this.from;
    }

    /**
     * Accesses the private field to
     *
     * @return Value of to
     */
    @Override
    public String getTo() {
        return this.to;
    }

    /**
     * Accesses the private field turn
     *
     * @return Value of turn
     */
    @Override
    public Turn getTurn() {
        return this.turn;
    }

    /**
     * Accesses the private field value
     *
     * @return Value of value
     */
    @Override
    public int getValue() {
        return this.value;
    }

    /**
     * Sets the value of the private field value
     *
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
