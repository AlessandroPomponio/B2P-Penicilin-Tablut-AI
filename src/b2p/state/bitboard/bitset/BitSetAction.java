package b2p.state.bitboard.bitset;


import b2p.model.IAction;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

import java.util.Objects;

public class BitSetAction implements IAction {

    private final String from;
    private final String to;
    private final Turn turn;
    private int value;

    public BitSetAction(String from, String to, Turn turn) {
        this.from = from;
        this.to = to;
        this.turn = turn;
        this.value = -1;
    }

    public BitSetAction(String from, String to, Turn turn, int value) {
        this.from = from;
        this.to = to;
        this.turn = turn;
        this.value = value;
    }

    @Override
    public String getFrom() {
        return this.from;
    }

    @Override
    public String getTo() {
        return this.to;
    }

    @Override
    public Turn getTurn() {
        return this.turn;
    }

    @Override
    public int getValue() {
        return this.value;
    }

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
