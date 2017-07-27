package gtl.common;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;

/*

static BitSet	valueOf(byte[] bytes)
Returns a new bit set containing all the bits in the given byte array.
static BitSet	valueOf(ByteBuffer bb)
Returns a new bit set containing all the bits in the given byte buffer between its position and limit.
static BitSet	valueOf(long[] longs)
Returns a new bit set containing all the bits in the given long array.
static BitSet	valueOf(LongBuffer lb)
Returns a new bit set containing all the bits in the given long buffer between its position and limit.
public BitSet()
Creates a new bit set. All bits are initially false.
public BitSet(int nbits)
Creates a bit set whose initial size is large enough to explicitly represent bits with indices in the range 0 through nbits-1. All bits are initially false.
Parameters:
nbits - the initial size of the bit set

 */
public class BitSet extends java.util.BitSet {
    /*
    * Creates a new bit set. All bits are initially false.
     */
    public BitSet() {
    }

    /*
    * Creates a bit set whose initial size is large enough to explicitly represent bits with indices in the range 0 through nbits-1. All bits are initially false.
    * Parameters:
    * nbits - the initial size of the bit set
     */
    public BitSet(int nbits) {
        super(nbits);
    }

    /*
    Returns a new bit set containing all the bits in the given byte array.
     */
    public static BitSet	valueOf(byte[] bytes){
        return null;
    }

    /*
    * Returns a new bit set containing all the bits in the given byte buffer between its position and limit.
     */
    public static BitSet	valueOf(ByteBuffer bb){
        return null;
    }
    /*
    * Returns a new bit set containing all the bits in the given long array.
     */
    public static BitSet	valueOf(long[] longs)
    {
        return null;
    }
    /*
    * Returns a new bit set containing all the bits in the given long buffer between its position and limit.
     */
    public static BitSet	valueOf(LongBuffer lb){
        return null;
    }


}
