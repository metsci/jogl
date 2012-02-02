/* Generic definitions */




/* Assertions (useful to generate conditional code) */
/* Current type and class (and size, if applicable) */
/* Value methods */
/* Interfaces (keys) */
/* Interfaces (values) */
/* Abstract implementations (keys) */
/* Abstract implementations (values) */
/* Static containers (keys) */
/* Static containers (values) */
/* Implementations */
/* Synchronized wrappers */
/* Unmodifiable wrappers */
/* Other wrappers */
/* Methods (keys) */
/* Methods (values) */
/* Methods (keys/values) */
/* Methods that have special names depending on keys (but the special names depend on values) */
/* Equality */
/* Object/Reference-only definitions (keys) */
/* Primitive-type-only definitions (keys) */
/* Object/Reference-only definitions (values) */
/* Primitive-type-only definitions (values) */
/*       
 * Original from fastutil by Sebastiano Vigna; modifications by Metron, Inc.
 *
 * Copyright (C) 2002-2011 Sebastiano Vigna 
 * Copyright (C) 2012 Metron, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package com.sun.opengl.impl;

import java.util.Arrays;

/** A type-specific hash map with a fast, small-footprint implementation.
 *
 * <P>Instances of this class use a hash table to represent a map. The table is
 * enlarged as needed by doubling its size when new entries are created, but it is <em>never</em> made
 * smaller (even on a {@link #clear()}). A family of {@linkplain #trim() trimming
 * methods} lets you control the size of the table; this is particularly useful
 * if you reuse instances of this class.
 *
 * <p><strong>Warning:</strong> The implementation of this class has significantly
 * changed in <code>fastutil</code> 6.1.0. Please read the
 * comments about this issue in the section &ldquo;Faster Hash Tables&rdquo; of the <a href="../../../../../overview-summary.html">overview</a>.
 *
 * @see Hash
 * @see HashCommon
 */
class Int2IntOpenHashMap
{
    /** The initial default size of a hash table. */
    public static final int DEFAULT_INITIAL_SIZE = 16;
    
    /** The default load factor of a hash table. */
    public static final float DEFAULT_LOAD_FACTOR = .75f;
    
    
    /** Return the least power of two greater than or equal to the specified value.
     * 
     * <p>Note that this function will return 1 when the argument is 0.
     * 
     * @param x a long integer smaller than or equal to 2<sup>62</sup>.
     * @return the least power of two greater than or equal to the specified value.
     */
    public static long nextPowerOfTwo( long x )
    {
        if ( x == 0 ) return 1;
        x--;
        x |= x >> 1;
        x |= x >> 2;
        x |= x >> 4;
        x |= x >> 8;
        x |= x >> 16;
        return ( x | x >> 32 ) + 1;
    }
    
    /** Returns the maximum number of entries that can be filled before rehashing. 
     *
     * @param n the size of the backing array.
     * @param f the load factor.
     * @return the maximum number of entries before rehashing. 
     */
    public static int maxFill( final int n, final float f )
    {
        return (int)Math.ceil( n * f );
    }
    
    /** Returns the least power of two smaller than or equal to 2<sup>30</sup> and larger than or equal to <code>Math.ceil( expected / f )</code>. 
     * 
     * @param expected the expected number of elements in a hash table.
     * @param f the load factor.
     * @return the minimum possible size for a backing array.
     * @throws IllegalArgumentException if the necessary size is larger than 2<sup>30</sup>.
     */
    public static int arraySize( final int expected, final float f )
    {
        final long s = nextPowerOfTwo( (long)Math.ceil( expected / f ) );
        if ( s > (1 << 30) ) throw new IllegalArgumentException( "Too large (" + expected + " expected elements with load factor " + f + ")" );
        return (int)s;
    }
    
    /** Avalanches the bits of an integer by applying the finalisation step of MurmurHash3.
     * 
     * <p>This function implements the finalisation step of Austin Appleby's <a href="http://sites.google.com/site/murmurhash/">MurmurHash3</a>.
     * Its purpose is to avalanche the bits of the argument to within 0.25% bias. It is used, among other things, to scramble quickly (but deeply) the hash
     * values returned by {@link Object#hashCode()}.
     * 
     * @param x an integer.
     * @return a hash value with good avalanching properties.
     */ 
    public final static int murmurHash3( int x )
    {
        x ^= x >>> 16;
        x *= 0x85ebca6b;
        x ^= x >>> 13;
        x *= 0xc2b2ae35;
        x ^= x >>> 16;
        return x;
    }
    
    
    
    /** The array of keys. */
    protected transient int key[];
    
    /** The array of values. */
    protected transient int value[];
    
    /** The array telling whether a position is used. */
    protected transient boolean used[];
    
    /** The acceptable load factor. */
    protected final float f;
    
    /** The current table size. */
    protected transient int n;
    
    /** Threshold after which we rehash. It must be the table size times {@link #f}. */
    protected transient int maxFill;
    
    /** The mask for wrapping a position counter. */
    protected transient int mask;
    
    /** Number of entries in the set. */
    protected int size;
    
    
    /** Creates a new hash map.
     *
     * <p>The actual table size will be the least power of two greater than <code>expected</code>/<code>f</code>.
     *
     * @param expected the expected number of elements in the hash set. 
     * @param f the load factor.
     */
    public Int2IntOpenHashMap( final int expected, final float f )
    {
        if ( f <= 0 || f > 1 ) throw new IllegalArgumentException( "Load factor must be greater than 0 and smaller than or equal to 1" );
        if ( expected < 0 ) throw new IllegalArgumentException( "The expected number of elements must be nonnegative" );
        this.f = f;
        n = arraySize( expected, f );
        mask = n - 1;
        maxFill = maxFill( n, f );
        key = new int[ n ];
        value = new int[ n ];
        used = new boolean[ n ];
    }
    
    /** Creates a new hash map with {@link Hash#DEFAULT_LOAD_FACTOR} as load factor.
     *
     * @param expected the expected number of elements in the hash map.
     */
    public Int2IntOpenHashMap( final int expected )
    {
        this( expected, DEFAULT_LOAD_FACTOR );
    }
    
    /** Creates a new hash map with initial expected {@link Hash#DEFAULT_INITIAL_SIZE} entries
     * and {@link Hash#DEFAULT_LOAD_FACTOR} as load factor.
     */
    public Int2IntOpenHashMap()
    {
        this( DEFAULT_INITIAL_SIZE, DEFAULT_LOAD_FACTOR );
    }
    
    public void put(final int k, final int v)
    {
        // The starting point.
        int pos = ( murmurHash3( (k) ) ) & mask;
        // There's always an unused entry.
        while( used[ pos ] ) {
            if ( ( (k) == (key[ pos ]) ) ) {
                value[ pos ] = v;
                return;
            }
            pos = ( pos + 1 ) & mask;
        }
        used[ pos ] = true;
        key[ pos ] = k;
        value[ pos ] = v;
        if ( ++size >= maxFill ) rehash( arraySize( size + 1, f ) );
    }
    
    public int get( final int k, final int defRetValue )
    {
        // The starting point.
        int pos = ( murmurHash3( ( k) ) ) & mask;
        // There's always an unused entry.
        while( used[ pos ] ) {
            if ( ( ( k) == (key[ pos ]) ) ) return value[ pos ];
            pos = ( pos + 1 ) & mask;
        }
        return defRetValue;
    }
    
    /* Removes all elements from this map.
     *
     * <P>To increase object reuse, this method does not change the table size.
     * If you want to reduce the table size, you must use {@link #trim()}.
     *
     */
    public void clear()
    {
        if ( size == 0 ) return;
        size = 0;
        Arrays.fill( used, false );
        // We null all object entries so that the garbage collector can do its work.
    }
    
    /** Resizes the map.
     *
     * <P>This method implements the basic rehashing strategy, and may be
     * overriden by subclasses implementing different rehashing strategies (e.g.,
     * disk-based rehashing). However, you should not override this method
     * unless you understand the internal workings of this class.
     *
     * @param newN the new size
     */
    protected void rehash( final int newN )
    {
        int i = 0, pos;
        final boolean used[] = this.used;
        int k;
        final int key[] = this.key;
        final int value[] = this.value;
        final int newMask = newN - 1;
        final int newKey[] = new int[ newN ];
        final int newValue[] = new int[newN];
        final boolean newUsed[] = new boolean[ newN ];
        for( int j = size; j-- != 0; ) {
            while( ! used[ i ] ) i++;
            k = key[ i ];
            pos = ( murmurHash3( (k) ) ) & newMask;
            while ( newUsed[ pos ] ) pos = ( pos + 1 ) & newMask;
            newUsed[ pos ] = true;
            newKey[ pos ] = k;
            newValue[ pos ] = value[ i ];
            i++;
        }
        n = newN;
        mask = newMask;
        maxFill = maxFill( n, f );
        this.key = newKey;
        this.value = newValue;
        this.used = newUsed;
    }

}
