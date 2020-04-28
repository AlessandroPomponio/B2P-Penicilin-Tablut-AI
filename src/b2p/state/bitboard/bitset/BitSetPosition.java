package b2p.state.bitboard.bitset;

import java.util.BitSet;

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

    private BitSetPosition(String name) {
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

                                                            D1.ordinal(),   E1.ordinal(),   F1.ordinal(),
                                                                            E2.ordinal(),

            A4.ordinal(),                                                                                                                   I4.ordinal(),
            A5.ordinal(),   B5.ordinal(),                                   E5.ordinal(),                                   H5.ordinal(),   I5.ordinal(),
            A6.ordinal(),                                                                                                                   I6.ordinal(),

                                                                            E8.ordinal(),
                                                            D9.ordinal(),   E9.ordinal(),   F9.ordinal()

    };
    //endregion

    //region Special BitSets
    public static final BitSet camps = BitSetUtils.newFromPositions(campCells);
    public static final BitSet escape = BitSetUtils.newFromPositions(escapeCells);
    public static final BitSet castle = BitSetUtils.newFromPositions(castleCells);
    public static final BitSet obstacles = BitSetUtils.newFromPositions(obstacleCells);
    //endregion

}
