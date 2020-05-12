package b2p.state.bitboard.bitset;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;

public enum BitSetPosition {
    
    A1("A1"), B1("B1"), C1("C1"), D1("D1"), E1("E1"), F1("F1"), G1("G1"), H1("H1"), I1("I1"),
    A2("A2"), B2("B2"), C2("C2"), D2("D2"), E2("E2"), F2("F2"), G2("G2"), H2("H2"), I2("I2"),
    A3("A3"), B3("B3"), C3("C3"), D3("D3"), E3("E3"), F3("F3"), G3("G3"), H3("H3"), I3("I3"),
    A4("A4"), B4("B4"), C4("C4"), D4("D4"), E4("E4"), F4("F4"), G4("G4"), H4("H4"), I4("I4"),
    A5("A5"), B5("B5"), C5("C5"), D5("D5"), E5("E5"), F5("F5"), G5("G5"), H5("H5"), I5("I5"),
    A6("A6"), B6("B6"), C6("C6"), D6("D6"), E6("E6"), F6("F6"), G6("G6"), H6("H6"), I6("I6"),
    A7("A7"), B7("B7"), C7("C7"), D7("D7"), E7("E7"), F7("F7"), G7("G7"), H7("H7"), I7("I7"),
    A8("A8"), B8("B8"), C8("C8"), D8("D8"), E8("E8"), F8("F8"), G8("G8"), H8("H8"), I8("I8"),
    A9("A9"), B9("B9"), C9("C9"), D9("D9"), E9("E9"), F9("F9"), G9("G9"), H9("H9"), I9("I9");

    private final String name;

    BitSetPosition(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    //region Special boards
    private static final int[] campCells = {
                                                            D1.ordinal(),   E1.ordinal(),   F1.ordinal(),
                                                                            E2.ordinal(),

            A4.ordinal(),                                                                                                                   I4.ordinal(),
            A5.ordinal(),   B5.ordinal(),                                                                                   H5.ordinal(),   I5.ordinal(),
            A6.ordinal(),                                                                                                                   I6.ordinal(),

                                                                            E8.ordinal(),
                                                            D9.ordinal(),   E9.ordinal(),   F9.ordinal()
    };

    private static final int[] escapeCells = {
                            B1.ordinal(),   C1.ordinal(),                                                   G1.ordinal(),   H1.ordinal(),
            A2.ordinal(),                                                                                                                   I2.ordinal(),
            A3.ordinal(),                                                                                                                   I3.ordinal(),



            A7.ordinal(),                                                                                                                   I7.ordinal(),
            A8.ordinal(),                                                                                                                   I8.ordinal(),
                            B9.ordinal(),   C9.ordinal(),                                                   G9.ordinal(),   H9.ordinal()
    };

    private static final int[] castleCells = {




                                                                            E5.ordinal()




    };

    private static final int[] obstacleCells = {
                                                            D1.ordinal(),                   F1.ordinal(),
                                                                            E2.ordinal(),

            A4.ordinal(),                                                                                                                   I4.ordinal(),
                            B5.ordinal(),                                   E5.ordinal(),                                   H5.ordinal(),
            A6.ordinal(),                                                                                                                   I6.ordinal(),

                                                                            E8.ordinal(),
                                                            D9.ordinal(),                   F9.ordinal()
    };

    private static final int[] kingSpecialCells = {



                                                                            E4.ordinal(),
                                                            D5.ordinal(),   E5.ordinal(),   F5.ordinal(),
                                                                            E6.ordinal(),



    };

    private static final int[] kingSurroundedCells = {



                                                                            E4.ordinal(),
                                                            D5.ordinal(),                   F5.ordinal(),
                                                                            E6.ordinal(),



    };

    private static final int[] kingInE4SurroundedCells = {


                                                                            E3.ordinal(),
                                                            D4.ordinal(),                   F4.ordinal(),





    };

    private static final int[] kingInD5SurroundedCells = {



                                                            D4.ordinal(),
                                            C5.ordinal(),
                                                            D6.ordinal(),



    };

    private static final int[] kingInE6SurroundedCells = {





                                                            D6.ordinal(),                   F6.ordinal(),
                                                                            E7.ordinal(),


    };

    private static final int[] kingInF5SurroundedCells = {



                                                                                            F4.ordinal(),
                                                                                                               G5.ordinal(),
                                                                                            F6.ordinal(),



    };

    private static final int[] strategicBlackCells = {

                                                                D3.ordinal(),           F3.ordinal(),
                                                C4.ordinal(),                                           G4.ordinal(),

                                                C6.ordinal(),                                           G6.ordinal(),
                                                                D7.ordinal(),           F7.ordinal()
    };

    private static final int[] earlyGameStrategicWhiteCells = {

                                                C3.ordinal(),                                           G3.ordinal(),



                                                C7.ordinal(),                                           G7.ordinal(),

    };
    //endregion

    public static final int[] whitePawnCellWeight = {
            5,  5,  5,  0,  0,  0,  5,  5,  5,
            5,  1,  1,  4,  0,  4,  1,  1,  5,
            5,  1,  2,  1,  3,  1,  2,  1,  5,
            0,  4,  1,  1,  3,  1,  1,  5,  0,
            0,  0,  3,  3,  0,  3,  3,  0,  0,
            0,  4,  1,  1,  3,  1,  1,  5,  0,
            5,  1,  2,  1,  3,  1,  2,  1,  3,
            5,  1,  1,  4,  0,  4,  1,  1,  5,
            5,  5,  5,  0,  0,  0,  5,  5,  5,
    };

    public static final int[] blackPawnCellWeight = {
            1,  3,  2,  3,  3,  3,  2,  3,  1,
            3,  2,  5,  1,  3,  1,  5,  2,  3,
            2,  5,  1,  5,  2,  5,  1,  5,  2,
            3,  1,  5,  1,  2,  1,  5,  1,  3,
            3,  3,  2,  2,  0,  2,  2,  3,  3,
            3,  1,  5,  1,  2,  1,  5,  1,  3,
            2,  5,  1,  5,  2,  5,  1,  5,  2,
            3,  2,  5,  1,  3,  1,  5,  2,  3,
            1,  3,  2,  3,  3,  3,  2,  3,  1,
    };

    public static final int[] kingTopLeftWeight = {
            1,  1,  1,  1,  1,  0,  0,  0,  0,
            1,  1,  1,  1,  1,  0,  0,  0,  0,
            1,  1,  1,  1,  1,  0,  0,  0,  0,
            1,  1,  1,  1,  1,  0,  0,  0,  0,
            1,  1,  1,  1,  0, -1, -1, -1, -1,
            0,  0,  0,  0, -1, -1, -1, -1, -1,
            0,  0,  0,  0, -1, -1, -1, -1, -1,
            0,  0,  0,  0, -1, -1, -1, -1, -1,
            0,  0,  0,  0, -1, -1, -1, -1, -1,
    };

    public static final int[] kingTopRightWeight = {
             0,  0,  0,  0,  1,  1,  1,  1,  1,
             0,  0,  0,  0,  1,  1,  1,  1,  1,
             0,  0,  0,  0,  1,  1,  1,  1,  1,
             0,  0,  0,  0,  1,  1,  1,  1,  1,
            -1, -1, -1, -1,  0,  1,  1,  1,  1,
            -1, -1, -1, -1, -1,  0,  0,  0,  0,
            -1, -1, -1, -1, -1,  0,  0,  0,  0,
            -1, -1, -1, -1, -1,  0,  0,  0,  0,
            -1, -1, -1, -1, -1,  0,  0,  0,  0,
    };

    public static final int[] kingBottomLeftWeight = {
            0,  0,  0,  0, -1, -1, -1, -1, -1,
            0,  0,  0,  0, -1, -1, -1, -1, -1,
            0,  0,  0,  0, -1, -1, -1, -1, -1,
            0,  0,  0,  0, -1, -1, -1, -1, -1,
            1,  1,  1,  1,  0, -1, -1, -1, -1,
            1,  1,  1,  1,  1,  0,  0,  0,  0,
            1,  1,  1,  1,  1,  0,  0,  0,  0,
            1,  1,  1,  1,  1,  0,  0,  0,  0,
            1,  1,  1,  1,  1,  0,  0,  0,  0,
    };

    public static final int[] kingBottomRightWeight = {
            -1, -1, -1, -1, -1,  0,  0,  0,  0,
            -1, -1, -1, -1, -1,  0,  0,  0,  0,
            -1, -1, -1, -1, -1,  0,  0,  0,  0,
            -1, -1, -1, -1, -1,  0,  0,  0,  0,
            -1, -1, -1, -1,  0,  1,  1,  1,  1,
             0,  0,  0,  0,  1,  1,  1,  1,  1,
             0,  0,  0,  0,  1,  1,  1,  1,  1,
             0,  0,  0,  0,  1,  1,  1,  1,  1,
             0,  0,  0,  0,  1,  1,  1,  1,  1,
    };

    public static final int[][] kingBuff = { kingTopLeftWeight, kingTopRightWeight, kingBottomLeftWeight, kingBottomRightWeight };

    //region Special BitSets
    public static final B2PBitSet camps = BitSetUtils.newFromPositions(campCells);
    public static final B2PBitSet escape = BitSetUtils.newFromPositions(escapeCells);
    public static final B2PBitSet castle = BitSetUtils.newFromPositions(castleCells);
    public static final B2PBitSet obstacles = BitSetUtils.newFromPositions(obstacleCells);
    public static final B2PBitSet specialKingCells = BitSetUtils.newFromPositions(kingSpecialCells);
    public static final B2PBitSet kingSurrounded = BitSetUtils.newFromPositions(kingSurroundedCells);
    public static final B2PBitSet kingInE4Surrounded = BitSetUtils.newFromPositions(kingInE4SurroundedCells);
    public static final B2PBitSet kingInD5Surrounded = BitSetUtils.newFromPositions(kingInD5SurroundedCells);
    public static final B2PBitSet kingInE6Surrounded = BitSetUtils.newFromPositions(kingInE6SurroundedCells);
    public static final B2PBitSet kingInF5Surrounded = BitSetUtils.newFromPositions(kingInF5SurroundedCells);
    public static final B2PBitSet blackStrategicCells = BitSetUtils.newFromPositions(strategicBlackCells);
    public static final B2PBitSet whiteEarlyGameStrategicCells = BitSetUtils.newFromPositions(earlyGameStrategicWhiteCells);
    //endregion
    
    public static final HashSet<String> escapeHashSet = new HashSet<>(Arrays.asList(
            "B1", "C1", "G1", "H1",
            "A2", "I2",
            "A3", "I3",


            "A7", "I7",
            "A8", "I8",
            "B9", "C9", "G9", "H9"));

    public static final int TOP_LEFT_QUADRANT = 0;
    public static final int TOP_RIGHT_QUADRANT = 1;
    public static final int BOTTOM_LEFT_QUADRANT = 2;
    public static final int BOTTOM_RIGHT_QUADRANT = 3;

    public static int findTargetQuadrantForWhites(BitSetState state) {

        int maxDifference = -1;
        int bestQuadrant = 0;
        int pieceDifference ;

        // top left
        BitSet blacks = BitSetUtils.copy(state.getBlackPawns());
        BitSet whites = BitSetUtils.copy(state.getWhitePawns());
        blacks.and(BitSetStartingBoard.topLeftQuadrant);
        whites.and(BitSetStartingBoard.topLeftQuadrant);
        pieceDifference = whites.cardinality() - blacks.cardinality();

        if(pieceDifference > maxDifference) {
            maxDifference = pieceDifference;
            bestQuadrant = TOP_LEFT_QUADRANT;
        }

        // top left
        blacks = BitSetUtils.copy(state.getBlackPawns());
        whites = BitSetUtils.copy(state.getWhitePawns());
        blacks.and(BitSetStartingBoard.topRightQuadrant);
        whites.and(BitSetStartingBoard.topRightQuadrant);
        pieceDifference = whites.cardinality() - blacks.cardinality();

        if(pieceDifference > maxDifference) {
            maxDifference = pieceDifference;
            bestQuadrant = TOP_RIGHT_QUADRANT;
        }

        // bottom left
        blacks = BitSetUtils.copy(state.getBlackPawns());
        whites = BitSetUtils.copy(state.getWhitePawns());
        blacks.and(BitSetStartingBoard.bottomLeftQuadrant);
        whites.and(BitSetStartingBoard.bottomRightQuadrant);
        pieceDifference = whites.cardinality() - blacks.cardinality();

        if(pieceDifference > maxDifference) {
            maxDifference = pieceDifference;
            bestQuadrant = BOTTOM_LEFT_QUADRANT;
        }

        // bottom right
        blacks = BitSetUtils.copy(state.getBlackPawns());
        whites = BitSetUtils.copy(state.getWhitePawns());
        blacks.and(BitSetStartingBoard.bottomRightQuadrant);
        whites.and(BitSetStartingBoard.bottomRightQuadrant);
        pieceDifference = whites.cardinality() - blacks.cardinality();

        if(pieceDifference > maxDifference) {
            bestQuadrant = BOTTOM_RIGHT_QUADRANT;
        }

        return bestQuadrant;

    }

}
