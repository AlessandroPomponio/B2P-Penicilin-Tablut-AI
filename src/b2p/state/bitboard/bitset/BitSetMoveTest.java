package b2p.state.bitboard.bitset;

import b2p.model.Turn;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class BitSetMoveTest {

    @org.junit.jupiter.api.Test
    void getMovesForPawn() {

        BitSetState start = new BitSetState();
        BitSetAction[] wanted = new BitSetAction[]{
                new BitSetAction("E3", "D3", Turn.WHITE),
                new BitSetAction("E3", "C3", Turn.WHITE),
                new BitSetAction("E3", "B3", Turn.WHITE),
                new BitSetAction("E3", "A3", Turn.WHITE),
                new BitSetAction("E3", "F3", Turn.WHITE),
                new BitSetAction("E3", "G3", Turn.WHITE),
                new BitSetAction("E3", "H3", Turn.WHITE),
                new BitSetAction("E3", "I3", Turn.WHITE),
        };

        assertArrayEquals(BitSetMove.getMovesForPawn(BitSetPosition.E3.ordinal(), start).toArray(), wanted);

    }

    @org.junit.jupiter.api.Test
    void getCapturedPawns() {
    }
}