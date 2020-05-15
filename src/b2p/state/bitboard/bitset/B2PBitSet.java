package b2p.state.bitboard.bitset;

import java.util.BitSet;

public class B2PBitSet extends BitSet {

    public B2PBitSet(int dimension) {
        super(dimension);
    }

    public B2PBitSet andResult(B2PBitSet input) {
        B2PBitSet result = this.clone();
        result.and(input);
        return result;
    }

    @Override
    public B2PBitSet clone() {
        return (B2PBitSet) super.clone();
    }

}
