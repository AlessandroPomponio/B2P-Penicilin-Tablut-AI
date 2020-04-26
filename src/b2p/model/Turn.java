package b2p.model;

public enum Turn {

    WHITE("W"), BLACK("B"), WHITEWIN("WW"), BLACKWIN("BW"), DRAW("D");

    private final String turn;

    Turn(String s) {
        turn = s;
    }

    public boolean equalsTurn(String otherName) {
        return turn.equals(otherName);
    }

    public String toString() {
        return turn;
    }

}
