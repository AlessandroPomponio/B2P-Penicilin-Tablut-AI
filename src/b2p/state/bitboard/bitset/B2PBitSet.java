package b2p.state.bitboard.bitset;

import java.util.BitSet;

/**
 * This Class extends BitSet adding utility methods to simplify development
 *
 * @author Alessandro Buldini
 * @author Alessandro Pomponio
 * @author Federico Zanini
 * @see BitSet
 */
public class B2PBitSet extends BitSet {

    /**
     * Creates a bit set whose initial size is large enough to explicitly
     * represent bits with indices in the range {@code 0} through
     * {@code nbits-1}. All bits are initially {@code false}.
     *
     * @param dimension the initial size of the bit set
     */
    public B2PBitSet(int dimension) {
        super(dimension);
    }

    /**
     * Returns the results of a bitwise AND operation of this target with the argument B2PBitSet.
     * The value of this target is not modified any way.
     *
     * @param input is a B2PBitSet object
     * @return the results of a bitwise AND operation of this target with the argument B2PBitSet.
     * @see BitSet
     * @see B2PBitSet
     */
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
