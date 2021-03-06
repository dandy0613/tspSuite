package org.logisticPlanning.utils.math.functions.binary;

import org.logisticPlanning.utils.math.functions.BinaryFunction;

/**
 * The binary xor.
 */
public final class BXOr extends BinaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final BXOr INSTANCE = new BXOr();

  /** the forbidden constructor */
  private BXOr() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final byte compute(final byte x1, final byte x2) {
    return ((byte) (x1 ^ x2));
  }

  /** {@inheritDoc} */
  @Override
  public final short compute(final short x1, final short x2) {
    return ((short) (x1 ^ x2));
  }

  /** {@inheritDoc} */
  @Override
  public final int compute(final int x1, final int x2) {
    return (x1 ^ x2);
  }

  /** {@inheritDoc} */
  @Override
  public final long compute(final long x1, final long x2) {
    return (x1 ^ x2);
  }

  /** {@inheritDoc} */
  @Override
  public final float compute(final float x1, final float x2) {
    return this.compute(((long) x1), ((long) x2));
  }

  /** {@inheritDoc} */
  @Override
  public final double compute(final double x1, final double x2) {
    return this.compute(((long) x1), ((long) x2));
  }

  // default, automatic serialization replacement and resolve routines for
  // singletons
  //
  /**
   * Write replace: the instance this method is invoked on will be replaced
   * with the singleton instance {@link BXOr#INSTANCE INSTANCE} for
   * serialization, i.e., when the instance is written with
   * {@link java.io.ObjectOutputStream#writeObject(Object)}.
   *
   * @return the replacement instance (always {@link BXOr#INSTANCE
   *         INSTANCE})
   */
  private final Object writeReplace() {
    return BXOr.INSTANCE;
  }

  /**
   * Read resolve: The instance this method is invoked on will be replaced
   * with the singleton instance {@link BXOr#INSTANCE INSTANCE} after
   * serialization, i.e., when the instance is read with
   * {@link java.io.ObjectInputStream#readObject()}.
   *
   * @return the replacement instance (always {@link BXOr#INSTANCE
   *         INSTANCE})
   */
  private final Object readResolve() {
    return BXOr.INSTANCE;
  }
}
