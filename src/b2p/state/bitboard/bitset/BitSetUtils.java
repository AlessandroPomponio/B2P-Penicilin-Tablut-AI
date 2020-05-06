package b2p.state.bitboard.bitset;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;

import java.util.Arrays;
import java.util.BitSet;

public class BitSetUtils {

    public static BitSetState newFromServer(StateTablut serverState) {

        State.Turn turn = serverState.getTurn();

        int[] blackPawns = new int[serverState.getNumberOf(State.Pawn.BLACK)];
        int[] whitePawns = new int[serverState.getNumberOf(State.Pawn.WHITE)];
        int[] kingPawn = new int[serverState.getNumberOf(State.Pawn.KING)];

        int blackIdx = 0, whiteIdx = 0;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                switch (serverState.getBoard()[i][j]) {
                    case BLACK:
                        blackPawns[blackIdx++] = (9*i)+j;
                        break;
                    case WHITE:
                        whitePawns[whiteIdx++] = (9*i)+j;
                        break;
                    case KING:
                        kingPawn[0] = (9*i)+j;
                }
            }
        }

        return new BitSetState(
                serverState.getTurn(),
                BitSetUtils.newFromPositions(blackPawns),
                BitSetUtils.newFromPositions(whitePawns),
                BitSetUtils.newFromPositions(kingPawn)
        );
    }

    public static BitSet newFromPositions(int[] positions) {

        BitSet bitSet = new BitSet(BitSetState.boardDimension);
        for (int position : positions)
            bitSet.set(position);

        return bitSet;

    }

    public static BitSet newFromPositions(BitSetPosition[] positions) {

        int[] p = new int[positions.length];
        for (int i = 0; i < positions.length; i++) {
            p[i] = positions[i].ordinal();
        }

        return BitSetUtils.newFromPositions(p);

    }

    public static String toByteString(BitSet bitSet) {

        StringBuilder builder = new StringBuilder();
        for (byte b : bitSet.toByteArray())
            builder.append(b).append(", ");

        return builder.toString();

    }

    public static String toBitString(BitSet bitSet) {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bitSet.length(); i++) {

            if (i % 9 == 0)
                builder.append('\n');

            builder.append(bitSet.get(i) ? 1 : 0);

        }

        return builder.toString();

    }

    public static BitSet copy(BitSet input) {
        return (BitSet) input.clone();
    }

}
