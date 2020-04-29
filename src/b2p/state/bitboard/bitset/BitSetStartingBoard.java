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

    //region Starting BitSets
    public static final BitSet blackStartingBitSet = BitSetUtils.newFromPositions(blackStartingBoard);
    public static final BitSet whiteStartingBitSet = BitSetUtils.newFromPositions(whiteStartingBoard);
    public static final BitSet kingStartingBitSet = BitSetUtils.newFromPositions(kingStartingBoard);
    //endregion

}
