package b2p.state.bitboard.bitset;

import b2p.model.IAction;
import b2p.model.Turn;

import java.util.Objects;

public class BitSetAction implements IAction {

    private String from;
    private String to;
    private Turn turn;

    public BitSetAction(String from, String to, Turn turn) {
        this.from = from;
        this.to = to;
        this.turn = turn;
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
    public String toString() {
        return "BitSetAction{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", turn=" + turn +
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
}
