package b2p.state.bitboard.bitset;

import java.util.BitSet;

public class BitSetUtils {

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
