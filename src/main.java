import b2p.client.B2PTablutClient;

import java.io.IOException;

public class main {

    public static void main(String[] args) {

//        BitSet castle = BitSetStartingBoard.kingStartingBitSet;
//        String byteString = BitSetUtils.toByteString(castle);
//        String bitString = BitSetUtils.toBitString(castle);
//
//        System.out.println("ByteString:");
//        System.out.println(byteString);
//
//        System.out.println("\n\nBitString:");
//        System.out.println(bitString);

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


//        BitSetState startingState = new BitSetState();
//        startingState.setTurn(Turn.BLACK);
//        startingState.performMove(BitSetPosition.F1.ordinal(), BitSetPosition.F4.ordinal());
//        startingState.setTurn(Turn.BLACK);
//
//        System.out.println("StartingState:");
//        System.out.println(BitSetUtils.toBitString(startingState.getBoard()));
//
//        long startTime = System.nanoTime();
//        BitSet captures = BitSetMove.getCapturedPawns(BitSetPosition.D1.ordinal(), BitSetPosition.D4.ordinal(), startingState);
//        long endTime = System.nanoTime();
//        System.out.println("Captures:");
//        System.out.println(BitSetUtils.toBitString(captures));
//
//        System.out.println("\n\nTook: " + (endTime-startTime));
//
//        BitSetState s = new BitSetState();
//        MinMaxAlphaBeta m = new MinMaxAlphaBeta(60);
//        long start = System.nanoTime();
//        BitSetAction bsa = m.getBestMove(s, 12, true);
//        long end = System.nanoTime();
//        System.out.println("TEMPO: " + (end-start));
//        System.out.println(bsa);
//
//        BitSetState testState = new BitSetState(
//                Turn.WHITE,
//                BitSetStartingBoard.blackStartingBitSet,
//                BitSetStartingBoard.whiteStartingBitSet,
//                BitSetUtils.newFromPositions(new int[]{BitSetPosition.D4.ordinal()})
//        );



//        BitSetState testState = new BitSetState(
//                Turn.WHITE,
//                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
//                        BitSetPosition.E4,
//                        BitSetPosition.D5,
//                        BitSetPosition.F5,
//                        BitSetPosition.E7,
//                }),
//                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
//
//                }),
//                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
//                        BitSetPosition.E5,
//                })
//        );
//        System.out.println("TEST:\n\n" + BitSetUtils.toBitString(testState.getBoard()));
//        long start = System.nanoTime();
//        int moves = BitSetMove.movesNeededForKingEscape(testState);
//        long end = System.nanoTime();
//        System.out.println("Mosse necessarie: " + moves);
//        System.out.println("Elapsed: " + (end-start));

        try {
            B2PTablutClient client = new B2PTablutClient("black", 15000);
            client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}