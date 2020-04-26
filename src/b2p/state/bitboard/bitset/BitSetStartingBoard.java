package b2p.state.bitboard.bitset;

import java.util.BitSet;

import static b2p.state.bitboard.bitset.BitSetPositions.*;

public class BitSetStartingBoard {

    //region Starting boards
    private static final int[] blackStartingBoard = {

                        D1, E1, F1,
                            E2,

            A4,                             I4,
            A5, B5,                     H5, I5,
            A6,                             I6,

                            E8,
                        D9, E9, F9

    };

    private static final int[] whiteStartingBoard = {



                            E3,
                            E4,
                    C5, D5,     F5, G5,
                            E6,
                            E7,



    };

    private static final int[] kingStartingBoard = {





                            E5





    };

    //endregion

    //region Starting BitSets

    public static final BitSet blackStartingBitSet = BitSetUtils.newFromPositions(blackStartingBoard);
    public static final BitSet whiteStartingBitSet = BitSetUtils.newFromPositions(whiteStartingBoard);
    public static final BitSet kingStartingBitSet = BitSetUtils.newFromPositions(kingStartingBoard);

    //endregion

}
