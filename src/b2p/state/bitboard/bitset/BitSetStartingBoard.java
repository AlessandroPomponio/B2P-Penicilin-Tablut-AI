package b2p.state.bitboard.bitset;

import java.util.BitSet;

import static b2p.state.bitboard.bitset.BitSetPosition.*;

public class BitSetStartingBoard {

    //region Starting boards
    private static final int[] blackStartingBoard = {

                                                            D1.ordinal(),   E1.ordinal(),   F1.ordinal(),
                                                                            E2.ordinal(),

            A4.ordinal(),                                                                                                                   I4.ordinal(),
            A5.ordinal(),   B5.ordinal(),                                                                                   H5.ordinal(),   I5.ordinal(),
            A6.ordinal(),                                                                                                                   I6.ordinal(),

                                                                            E8.ordinal(),
                                                            D9.ordinal(),   E9.ordinal(),   F9.ordinal()

    };

    private static final int[] whiteStartingBoard = {



                                                                            E3.ordinal(),
                                                                            E4.ordinal(),
                                            C5.ordinal(),   D5.ordinal(),                   F5.ordinal(),   G5.ordinal(),
                                                                            E6.ordinal(),
                                                                            E7.ordinal(),



    };

    private static final int[] kingStartingBoard = {





                                                                            E5.ordinal()





    };
    //endregion
    
    //region quadrants    
    private static final int[] topLeftQuadrantMask = {
            A1.ordinal(), B1.ordinal(), C1.ordinal(), D1.ordinal(), E1.ordinal(),
            A2.ordinal(), B2.ordinal(), C2.ordinal(), D2.ordinal(), E2.ordinal(),
            A3.ordinal(), B3.ordinal(), C3.ordinal(), D3.ordinal(), E3.ordinal(),
            A4.ordinal(), B4.ordinal(), C4.ordinal(), D4.ordinal(), E4.ordinal(),
            A5.ordinal(), B5.ordinal(), C5.ordinal(), D5.ordinal(), E5.ordinal(),
    };

    private static final int[] topRightQuadrantMask = {
            E1.ordinal(), F1.ordinal(), G1.ordinal(), H1.ordinal(), I1.ordinal(),
            E2.ordinal(), F2.ordinal(), G2.ordinal(), H2.ordinal(), I2.ordinal(),
            E3.ordinal(), F3.ordinal(), G3.ordinal(), H3.ordinal(), I3.ordinal(),
            E4.ordinal(), F4.ordinal(), G4.ordinal(), H4.ordinal(), I4.ordinal(),
            E5.ordinal(), F5.ordinal(), G5.ordinal(), H5.ordinal(), I5.ordinal(),
    };

    private static final int[] bottomLeftQuadrantMask = {
            A5.ordinal(), B5.ordinal(), C5.ordinal(), D5.ordinal(), E5.ordinal(),
            A6.ordinal(), B6.ordinal(), C6.ordinal(), D6.ordinal(), E6.ordinal(),
            A7.ordinal(), B7.ordinal(), C7.ordinal(), D7.ordinal(), E7.ordinal(),
            A8.ordinal(), B8.ordinal(), C8.ordinal(), D8.ordinal(), E8.ordinal(),
            A9.ordinal(), B9.ordinal(), C9.ordinal(), D9.ordinal(), E9.ordinal(),
    };

    private static final int[] bottomRightQuadrantMask = {
            E5.ordinal(), F5.ordinal(), G5.ordinal(), H5.ordinal(), I5.ordinal(),
            E6.ordinal(), F6.ordinal(), G6.ordinal(), H6.ordinal(), I6.ordinal(),
            E7.ordinal(), F7.ordinal(), G7.ordinal(), H7.ordinal(), I7.ordinal(),
            E8.ordinal(), F8.ordinal(), G8.ordinal(), H8.ordinal(), I8.ordinal(),
            E9.ordinal(), F9.ordinal(), G9.ordinal(), H9.ordinal(), I9.ordinal(),
    };
    //endregion

    //region Starting BitSets
    public static final B2PBitSet blackStartingBitSet = BitSetUtils.newFromPositions(blackStartingBoard);
    public static final B2PBitSet whiteStartingBitSet = BitSetUtils.newFromPositions(whiteStartingBoard);
    public static final B2PBitSet kingStartingBitSet = BitSetUtils.newFromPositions(kingStartingBoard);
    //endregion

    // region quadrant bitsets
    public static final B2PBitSet topLeftQuadrant = BitSetUtils.newFromPositions(topLeftQuadrantMask);
    public static final B2PBitSet topRightQuadrant = BitSetUtils.newFromPositions(topRightQuadrantMask);
    public static final B2PBitSet bottomLeftQuadrant = BitSetUtils.newFromPositions(bottomLeftQuadrantMask);
    public static final B2PBitSet bottomRightQuadrant = BitSetUtils.newFromPositions(bottomRightQuadrantMask);
    public static final B2PBitSet[] quadrants = {topLeftQuadrant, topRightQuadrant, bottomLeftQuadrant, bottomRightQuadrant};
    //endregion

}
