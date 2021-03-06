package org.logisticPlanning.tsp.solving.utils.edgeData;

/**
 * <p>
 * This class is an internal class. Please do not instantiate it, use it by
 * yourself, or otherwise meddle with it.
 * </p>
 * <p>
 * A class that assigns on byte to each edge, specialized for symmetric
 * TSPs.
 * </p>
 * <p>
 * Warning: If you use an instance of this class in your code, you are
 * likely to not be able to tackle larger TSPs due to the memory
 * consumption!
 * </p>
 * <p>
 * The backing store of this matrix is a one-dimensional array of 8 bit
 * signed integer (byte) ({@code byte}). Arrays are indexed with 32 bit
 * signed integer (int) ({@code int}s). Thus, the highest possible value of
 * the matrix dimension {@code n} is 46'342. Be aware that such a matrix
 * may have a high memory consumption for high values of {@code n}:
 * </p>
 * <table border="1">
 * <tr>
 * <td>{@code n=10}</td>
 * <td>{@code n=64}</td>
 * <td>{@code n=100}</td>
 * <td>{@code n=1'024}</td>
 * <td>{@code n=10'000}</td>
 * <td>{@code n=16'384}</td>
 * <td>{@code n=46'342}</td>
 * </tr>
 * <tr>
 * <td>45&nbsp;bytes</td>
 * <td>2&prime;016&nbsp;bytes &asymp; 2&nbsp;KiB</td>
 * <td>4&prime;950&nbsp;bytes &asymp; 5&nbsp;KiB</td>
 * <td>523&prime;776&nbsp;bytes &asymp; 512&nbsp;KiB</td>
 * <td>49&prime;995&prime;000&nbsp;bytes &asymp; 48&nbsp;MiB</td>
 * <td>134&prime;209&prime;536&nbsp;bytes &asymp; 128&nbsp;MiB</td>
 * <td>1&prime;073&prime;767&prime;311&nbsp;bytes &asymp; 2&nbsp;GiB</td>
 * </tr>
 * </table>
 * <p>
 * Thus, if {@code n} rises or you allocate many objects, you may get an
 * {@link java.lang.OutOfMemoryError}.
 * </p>
 *
 *
 @author <em><a href="http://www.it-weise.de/">Thomas Weise</a></em>,
 *         Email:&nbsp;<a
 *         href="mailto:tweise@ustc.edu.cn">tweise@ustc.edu.cn</a>
 */
final class _SymmetricEdgeByte extends EdgeByte {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * create the _SymmetricEdgeByte
   *
   * @param n
   *          the number of nodes
   */
  _SymmetricEdgeByte(final int n) {
    super(n);
    if (n > 46342) {
      // The maximum size is calculated such that 32-bit integer index
      // computations
      // cannot overflow.
      throw new IllegalArgumentException( //
          "Matrix dimension cannot be higher than 46'342."); //$NON-NLS-1$
    }
    this.m_data = new byte[((int) ((((long) (n)) * ((n) - 1)) >>> 1))];
  }

  /** {@inheritDoc} */
  @Override
  public final void setByte(final int a, final int b, final byte value) {
    final int i, j;

    if (a > b) {
      i = a;
      j = b;
    } else {
      i = b;
      j = a;
    }
    this.m_data[((((((i) - 1) * ((i) - 2)) >>> 1) + (j)) - 1)] = (value);
  }

  /** {@inheritDoc} */
  @Override
  public final byte getByte(final int a, final int b) {
    final int i, j;

    if (a > b) {
      i = a;
      j = b;
    } else {
      i = b;
      j = a;
    }
    return (this.m_data[((((((i) - 1) * ((i) - 2)) >>> 1) + (j)) - 1)]);
  }

  /** {@inheritDoc} */
  @Override
  public final void addByte(final int a, final int b, final byte value) {
    final int i, j;

    if (a > b) {
      i = a;
      j = b;
    } else {
      i = b;
      j = a;
    }
    this.m_data[((((((i) - 1) * ((i) - 2)) >>> 1) + (j)) - 1)] += (value);
  }

  /** {@inheritDoc} */
  @Override
  public final void setShort(final int a, final int b, final short value) {
    final int i, j;

    if (a > b) {
      i = a;
      j = b;
    } else {
      i = b;
      j = a;
    }
    this.m_data[((((((i) - 1) * ((i) - 2)) >>> 1) + (j)) - 1)] = ((byte) (value));
  }

  /** {@inheritDoc} */
  @Override
  public final short getShort(final int a, final int b) {
    final int i, j;

    if (a > b) {
      i = a;
      j = b;
    } else {
      i = b;
      j = a;
    }
    return (this.m_data[((((((i) - 1) * ((i) - 2)) >>> 1) + (j)) - 1)]);
  }

  /** {@inheritDoc} */
  @Override
  public final void addShort(final int a, final int b, final short value) {
    final int i, j;

    if (a > b) {
      i = a;
      j = b;
    } else {
      i = b;
      j = a;
    }
    this.m_data[((((((i) - 1) * ((i) - 2)) >>> 1) + (j)) - 1)] += ((byte) (value));
  }

  /** {@inheritDoc} */
  @Override
  public final void setInt(final int a, final int b, final int value) {
    final int i, j;

    if (a > b) {
      i = a;
      j = b;
    } else {
      i = b;
      j = a;
    }
    this.m_data[((((((i) - 1) * ((i) - 2)) >>> 1) + (j)) - 1)] = ((byte) (value));
  }

  /** {@inheritDoc} */
  @Override
  public final int getInt(final int a, final int b) {
    final int i, j;

    if (a > b) {
      i = a;
      j = b;
    } else {
      i = b;
      j = a;
    }
    return (this.m_data[((((((i) - 1) * ((i) - 2)) >>> 1) + (j)) - 1)]);
  }

  /** {@inheritDoc} */
  @Override
  public final void addInt(final int a, final int b, final int value) {
    final int i, j;

    if (a > b) {
      i = a;
      j = b;
    } else {
      i = b;
      j = a;
    }
    this.m_data[((((((i) - 1) * ((i) - 2)) >>> 1) + (j)) - 1)] += ((byte) (value));
  }

  /** {@inheritDoc} */
  @Override
  public final void setLong(final int a, final int b, final long value) {
    final int i, j;

    if (a > b) {
      i = a;
      j = b;
    } else {
      i = b;
      j = a;
    }
    this.m_data[((((((i) - 1) * ((i) - 2)) >>> 1) + (j)) - 1)] = ((byte) (value));
  }

  /** {@inheritDoc} */
  @Override
  public final long getLong(final int a, final int b) {
    final int i, j;

    if (a > b) {
      i = a;
      j = b;
    } else {
      i = b;
      j = a;
    }
    return (this.m_data[((((((i) - 1) * ((i) - 2)) >>> 1) + (j)) - 1)]);
  }

  /** {@inheritDoc} */
  @Override
  public final void addLong(final int a, final int b, final long value) {
    final int i, j;

    if (a > b) {
      i = a;
      j = b;
    } else {
      i = b;
      j = a;
    }
    this.m_data[((((((i) - 1) * ((i) - 2)) >>> 1) + (j)) - 1)] += ((byte) (value));
  }

  /** {@inheritDoc} */
  @Override
  public final void setFloat(final int a, final int b, final float value) {
    final int i, j;

    if (a > b) {
      i = a;
      j = b;
    } else {
      i = b;
      j = a;
    }
    this.m_data[((((((i) - 1) * ((i) - 2)) >>> 1) + (j)) - 1)] = ((byte) ((value)));
  }

  /** {@inheritDoc} */
  @Override
  public final float getFloat(final int a, final int b) {
    final int i, j;

    if (a > b) {
      i = a;
      j = b;
    } else {
      i = b;
      j = a;
    }
    return (this.m_data[((((((i) - 1) * ((i) - 2)) >>> 1) + (j)) - 1)]);
  }

  /** {@inheritDoc} */
  @Override
  public final void addFloat(final int a, final int b, final float value) {
    final int i, j;

    if (a > b) {
      i = a;
      j = b;
    } else {
      i = b;
      j = a;
    }
    this.m_data[((((((i) - 1) * ((i) - 2)) >>> 1) + (j)) - 1)] += ((byte) ((value)));
  }

  /** {@inheritDoc} */
  @Override
  public final void setDouble(final int a, final int b, final double value) {
    final int i, j;

    if (a > b) {
      i = a;
      j = b;
    } else {
      i = b;
      j = a;
    }
    this.m_data[((((((i) - 1) * ((i) - 2)) >>> 1) + (j)) - 1)] = ((byte) ((value)));
  }

  /** {@inheritDoc} */
  @Override
  public final double getDouble(final int a, final int b) {
    final int i, j;

    if (a > b) {
      i = a;
      j = b;
    } else {
      i = b;
      j = a;
    }
    return (this.m_data[((((((i) - 1) * ((i) - 2)) >>> 1) + (j)) - 1)]);
  }

  /** {@inheritDoc} */
  @Override
  public final void addDouble(final int a, final int b, final double value) {
    final int i, j;

    if (a > b) {
      i = a;
      j = b;
    } else {
      i = b;
      j = a;
    }
    this.m_data[((((((i) - 1) * ((i) - 2)) >>> 1) + (j)) - 1)] += ((byte) ((value)));
  }

  /** {@inheritDoc} */
  @Override
  public final void inc(final int a, final int b) {
    final int i, j;

    if (a > b) {
      i = a;
      j = b;
    } else {
      i = b;
      j = a;
    }
    this.m_data[((((((i) - 1) * ((i) - 2)) >>> 1) + (j)) - 1)]++;
  }

  /** {@inheritDoc} */
  @Override
  public final void dec(final int a, final int b) {
    final int i, j;

    if (a > b) {
      i = a;
      j = b;
    } else {
      i = b;
      j = a;
    }
    this.m_data[((((((i) - 1) * ((i) - 2)) >>> 1) + (j)) - 1)]--;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isSymmetric() {
    return true;
  }

}
