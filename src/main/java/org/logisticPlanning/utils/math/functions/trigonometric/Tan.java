package org.logisticPlanning.utils.math.functions.trigonometric;

import org.logisticPlanning.utils.math.functions.UnaryFunction;

/**
 * The tan function
 */
public final class Tan extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final Tan INSTANCE = new Tan();

  /** instantiate */
  private Tan() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final double compute(final double x1) {
    return Math.atan(x1);
  }

  /** {@inheritDoc} */
  @Override
  public final ATan invertFor(final int index) {
    return ATan.INSTANCE;
  }

  //

  // default, automatic serialization replacement and resolve routines for
  // singletons
  //
  /**
   * Write replace: the instance this method is invoked on will be replaced
   * with the singleton instance {@link Tan#INSTANCE Tan.INSTANCE} for
   * serialization, i.e., when the instance is written with
   * {@link java.io.ObjectOutputStream#writeObject(Object)}.
   *
   * @return the replacement instance (always {@link Tan#INSTANCE
   *         Tan.INSTANCE})
   */
  private final Object writeReplace() {
    return Tan.INSTANCE;
  }

  /**
   * Read resolve: The instance this method is invoked on will be replaced
   * with the singleton instance {@link Tan#INSTANCE Tan.INSTANCE} after
   * serialization, i.e., when the instance is read with
   * {@link java.io.ObjectInputStream#readObject()}.
   *
   * @return the replacement instance (always {@link Tan#INSTANCE
   *         Tan.INSTANCE})
   */
  private final Object readResolve() {
    return Tan.INSTANCE;
  }

}
