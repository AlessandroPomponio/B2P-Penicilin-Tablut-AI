import b2p.model.Turn;
import b2p.state.bitboard.bitset.*;

import java.util.ArrayList;
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

//        BitSetState startingState = new BitSetState();
//        startingState.getBoard().clear(BitSetPosition.F9.ordinal());
//        startingState.setTurn(Turn.BLACK);
//        System.out.println("StartingState:");
//        System.out.println(BitSetUtils.toBitString(startingState.getBoard()));
//
//        long startTime = System.nanoTime();
//        ArrayList<BitSetAction> moves = BitSetMove.getMovesForPawn(BitSetPosition.E9.ordinal(), startingState);
//        long endTime = System.nanoTime();
//        for (BitSetAction move : moves) {
//            System.out.println(move);
//        }
//
//        System.out.println("\n\nTook: " + (endTime-startTime));


        BitSetState startingState = new BitSetState();
        System.out.println("StartingState:");
        System.out.println(BitSetUtils.toBitString(startingState.getBoard()));

        long startTime = System.nanoTime();
        BitSet captures = BitSetMove.getCapturedPawns(BitSetPosition.E7.ordinal(), BitSetPosition.I7.ordinal(), startingState);
        long endTime = System.nanoTime();
        System.out.println("Captures:");
        System.out.println(BitSetUtils.toBitString(captures));

        System.out.println("\n\nTook: " + (endTime-startTime));


    }

}