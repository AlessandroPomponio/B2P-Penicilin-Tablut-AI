package b2p.state.bitboard.bitset;

import java.util.BitSet;

public class BitSetPositions {

    // region Board cells: A1-I9
    // Ordered by rows
    public final static int A = 0;
    public final static int B = 1;
    public final static int C = 2;
    public final static int D = 3;
    public final static int E = 4;
    public final static int F = 5;
    public final static int G = 6;
    public final static int H = 7;
    public final static int I = 8;

    // A
    public final static int A1 = A;
    public final static int A2 = A1 + 9;
    public final static int A3 = A2 + 9;
    public final static int A4 = A3 + 9;
    public final static int A5 = A4 + 9;
    public final static int A6 = A5 + 9;
    public final static int A7 = A6 + 9;
    public final static int A8 = A7 + 9;
    public final static int A9 = A8 + 9;

    // B
    public final static int B1 = B;
    public final static int B2 = B1 + 9;
    public final static int B3 = B2 + 9;
    public final static int B4 = B3 + 9;
    public final static int B5 = B4 + 9;
    public final static int B6 = B5 + 9;
    public final static int B7 = B6 + 9;
    public final static int B8 = B7 + 9;
    public final static int B9 = B8 + 9;

    // C
    public final static int C1 = C;
    public final static int C2 = C1 + 9;
    public final static int C3 = C2 + 9;
    public final static int C4 = C3 + 9;
    public final static int C5 = C4 + 9;
    public final static int C6 = C5 + 9;
    public final static int C7 = C6 + 9;
    public final static int C8 = C7 + 9;
    public final static int C9 = C8 + 9;

    // D
    public final static int D1 = D;
    public final static int D2 = D1 + 9;
    public final static int D3 = D2 + 9;
    public final static int D4 = D3 + 9;
    public final static int D5 = D4 + 9;
    public final static int D6 = D5 + 9;
    public final static int D7 = D6 + 9;
    public final static int D8 = D7 + 9;
    public final static int D9 = D8 + 9;

    // E
    public final static int E1 = E;
    public final static int E2 = E1 + 9;
    public final static int E3 = E2 + 9;
    public final static int E4 = E3 + 9;
    public final static int E5 = E4 + 9;
    public final static int E6 = E5 + 9;
    public final static int E7 = E6 + 9;
    public final static int E8 = E7 + 9;
    public final static int E9 = E8 + 9;

    // F
    public final static int F1 = F;
    public final static int F2 = F1 + 9;
    public final static int F3 = F2 + 9;
    public final static int F4 = F3 + 9;
    public final static int F5 = F4 + 9;
    public final static int F6 = F5 + 9;
    public final static int F7 = F6 + 9;
    public final static int F8 = F7 + 9;
    public final static int F9 = F8 + 9;

    // G
    public final static int G1 = G;
    public final static int G2 = G1 + 9;
    public final static int G3 = G2 + 9;
    public final static int G4 = G3 + 9;
    public final static int G5 = G4 + 9;
    public final static int G6 = G5 + 9;
    public final static int G7 = G6 + 9;
    public final static int G8 = G7 + 9;
    public final static int G9 = G8 + 9;

    // H
    public final static int H1 = H;
    public final static int H2 = H1 + 9;
    public final static int H3 = H2 + 9;
    public final static int H4 = H3 + 9;
    public final static int H5 = H4 + 9;
    public final static int H6 = H5 + 9;
    public final static int H7 = H6 + 9;
    public final static int H8 = H7 + 9;
    public final static int H9 = H8 + 9;

    // I
    public final static int I1 = I;
    public final static int I2 = I1 + 9;
    public final static int I3 = I2 + 9;
    public final static int I4 = I3 + 9;
    public final static int I5 = I4 + 9;
    public final static int I6 = I5 + 9;
    public final static int I7 = I6 + 9;
    public final static int I8 = I7 + 9;
    public final static int I9 = I8 + 9;
    //endregion

    //region Special boards

    private static final int[] campCells = {

                        D1, E1, F1,
                            E2,

            A4,                             I4,
            A5, B5,                     H5, I5,
            A6,                             I6,

                            E8,
                        D9, E9, F9

    };

    private static final int[] escapeCells = {

                B1, C1,             G1, H1,
            A2,                             I2,
            A3,                             I3,



            A7,                             I7,
            A8,                             I8,
                B9, C9,             G9, H9

    };

    private static final int[] castleCells = {





                            E5





    };


    //endregion

    //region Special BitSets

    public static final BitSet camps = BitSetUtils.newFromPositions(campCells);
    public static final BitSet escape = BitSetUtils.newFromPositions(escapeCells);
    public static final BitSet castle = BitSetUtils.newFromPositions(castleCells);

    //endregion

}
