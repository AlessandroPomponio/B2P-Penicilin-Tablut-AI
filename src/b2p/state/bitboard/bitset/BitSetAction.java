package b2p.state.bitboard.bitset;

import b2p.model.IAction;
import b2p.model.Turn;

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
}
