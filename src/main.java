import b2p.state.bitboard.bitset.*;

import java.util.BitSet;

public class main {

    public static void main(String[] args) {

        BitSet castle = BitSetStartingBoard.kingStartingBitSet;
        String byteString = BitSetUtils.toByteString(castle);
        String bitString = BitSetUtils.toBitString(castle);

        System.out.println("ByteString:");
        System.out.println(byteString);

        System.out.println("\n\nBitString:");
        System.out.println(bitString);

    }

}