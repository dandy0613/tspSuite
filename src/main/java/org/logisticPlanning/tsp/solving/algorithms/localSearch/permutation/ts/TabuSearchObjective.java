package org.logisticPlanning.tsp.solving.algorithms.localSearch.permutation.ts;

import java.io.PrintStream;

import org.logisticPlanning.tsp.benchmarking.instances.Instance;
import org.logisticPlanning.tsp.benchmarking.objective.ObjectiveFunction;
import org.logisticPlanning.tsp.solving.Individual;
import org.logisticPlanning.tsp.solving.TSPAlgorithmRunner;
import org.logisticPlanning.tsp.solving.TSPModule;
import org.logisticPlanning.tsp.solving.algorithms.localSearch.TSPLocalSearchAlgorithm;
import org.logisticPlanning.tsp.solving.operators.permutation.update.PermutationUpdateOperator;
import org.logisticPlanning.tsp.solving.operators.permutation.update.PermutationUpdateOperators;
import org.logisticPlanning.utils.config.Configurable;
import org.logisticPlanning.utils.config.Configuration;

/**
 * <p>
 * A simple tabu search, i.e., a local search, which in each step checks if
 * the best solution is already in a list called tabu list. If it is, then
 * checks the second best solution until one solution not in the tabu list
 * found and accepts this solution. And also, add each solution we get in
 * each step into the tabu list.
 * {@link org.logisticPlanning.tsp.solving.operators.permutation.update.PermutationUpdateOperator
 * updating operations}&nbsp;[<a href="#cite_RN2002AI"
 * style="font-weight:bold">1</a>, <a href="#cite_JCS2003HC"
 * style="font-weight:bold">2</a>, <a href="#cite_DHS2000HC"
 * style="font-weight:bold">3</a>] would yield the best improvement and
 * then applies this operator.
 * </p>
 *
 * @author <em>Dan Xu</em>, Email:&nbsp;<a
 *         href="mailto:dandy@mail.ustc.edu.cn">dandy@mail.ustc.edu.cn</a>,
 *         Tel.:&nbsp;<a href="tel:+86 139 650 406 13">+86 139 650 406
 *         13</a>; <a href="http://www.ustc.edu.cn/">University of Science
 *         and Technology of China (USTC)</a>
 *         [&#x4E2D;&#x56FD;&#x79D1;&#x5B66
 *         ;&#x6280;&#x672F;&#x5927;&#x5B66;], (<a href=
 *         "https://en.wikipedia.org/wiki/University_of_Science_and_Technology_of_China"
 *         >wikipedia</a>); <a href="http://cs.ustc.edu.cn/">School of
 *         Computer Science and Technology (SCST)</a>
 *         [&#x8BA1;&#x7B97;&#x673A;&#x79D1;&#x5B66
 *         ;&#x4E0E;&#x6280;&#x672F;&#x5B66;&#x9662;]; <a
 *         href="http://ubri.ustc.edu.cn/">USTC-Birmingham Joint Research
 *         Institute in Intelligent Computation and Its Applications
 *         (UBRI)</a>; West Campus [&#x897F;&#x533A;]; Crossroad of
 *         Huangshan Road and Feixi Road
 *         [&#x9EC4;&#x5C71;&#x8DEF;/&#x80A5;&
 *         #x897F;&#x8DEF;&#x5341;&#x5B57 ;&#x8DEF;&#x53E3;]; <a
 *         href="https://en.wikipedia.org/wiki/Hefei">Hefei</a>
 *         [&#x5408;&#x80A5;&#x5E02;] 230027; <a
 *         href="https://en.wikipedia.org/wiki/Anhui">Anhui</a>
 *         [&#x5B89;&#x5FBD;&#x7701;]; <a
 *         href="https://en.wikipedia.org/wiki/People%27s_Republic_of_China"
 *         >China</a> [&#x4E2D;&#x56FD;]
 */
public class TabuSearchObjective extends TSPLocalSearchAlgorithm<int[]> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the updating operators: {@value} */
  public static final String PARAM_UPDATING_OPERATORS = "updatingOperators"; //$NON-NLS-1$
  /** the absolute component of the length of tabu list: {@value} */
  public static final String PARAM_ABSOLUTE_TABU_LIST_LENGTH = "absolute"; //$NON-NLS-1$
  /**
   * the rate component of tabu tenure depending on problem scale: * * * *
   * * * {@value}
   */
  public static final String PARAM_TABU_LIST_LENGTH_RATE = "rate"; //$NON-NLS-1$
  /** the length of tabu list: {@value} */
  public static final String PARAM_TABU_LIST_LENGTH = "tabuListLength"; //$NON-NLS-1$
  /** should the first improving move be used: {@value} */
  public static final String PARAM_USE_FIRST_IMPROVEMENT = "useFirstImprovement"; //$NON-NLS-1$

  /** the update operations */
  private PermutationUpdateOperator[] m_ops;

  /** the absolute tabu tenure */
  private int m_absolute;
  /** the rate */
  private int m_rate;
  /** the tabu list length */
  private int m_tabuListLength;
  /** the tabu list */
  private long[] m_tabuList;
  /** the size */
  private int m_tabuSize;
  /** the tabu list start */
  private int m_tabuStart;

  /** should we stop as soon as we found any improvement? */
  private boolean m_useFirstImprovement;

  /** instantiate */
  public TabuSearchObjective() {
    super("TabuSearchObjectiveValue");//$NON-NLS-1$
    this.m_ops = PermutationUpdateOperators.OPERATORS_AND_COMPLEMENT;
  }

  /**
   * Perform the tabu search
   *
   * @param args
   *          the command line arguments
   */
  public final static void main(final String[] args) {
    TSPAlgorithmRunner.benchmark(//
        Instance.SYMMETRIC_INSTANCES, TabuSearchObjective.class,//
        // Instance.SYMMETRIC_TEST, TabuSearchSolution.class,
        args);
  }

  /** {@inheritDoc} */
  @Override
  public void localSearch(final Individual<int[]> srcdst,
      final ObjectiveFunction f) {
    final int n;
    boolean foundImprov = true;
    int currentDelta, currentStart, currentEnd, bestDelta, bestStart, bestEnd;
    PermutationUpdateOperator bestOperator;

    // create the hash for the current solution
    this.__addTabu(srcdst.tourLength);

    n = f.n();

    while (foundImprov && (!(f.shouldTerminate()))) {
      foundImprov = false;
      bestOperator = null;
      bestDelta = Integer.MAX_VALUE;
      bestStart = bestEnd = (-1);

      // test every possible distinct (start, end) pair
      findOperation: for (currentEnd = n; (--currentEnd) > 0;) {
        for (currentStart = currentEnd; (--currentStart) >= 0;) {

          // test each possible update operation op(start, end)
          for (final PermutationUpdateOperator currentOperator : this.m_ops) {

            // how good would the result be, if we applied the
            // operation?
            currentDelta = currentOperator.delta(srcdst.solution, f,
                currentStart, currentEnd);

            // is the new solution better than the best permissible
            // one
            // we have seen?
            if (currentDelta < bestDelta) { // only then we need to
              // is the new solution in the tabu list?
              if (!(this.__isTabu(srcdst.tourLength + currentDelta))) {
                // no - so we remember it
                bestDelta = currentDelta;
                bestOperator = currentOperator;
                bestStart = currentStart;
                bestEnd = currentEnd;

                if (bestDelta < 0) {// at first improvement:
                  // stop
                  if (this.m_useFirstImprovement) {
                    break findOperation; // don't revert,
                    // keep solution
                  }
                }
              }
            } // end currentDelta<bestDelta
          } // end for currentOperator
        } // end: for currentStart
      } // end: for currentEnd

      // OK, the main loop has terminated, every possible op(start, end)
      // has been tested.
      if (bestOperator == null) { // there was no permissible move
        return; // so we exit here
      }

      // update solution with best operator found
      if (bestDelta < 0) {
        foundImprov = true;
      }
      bestOperator.update(srcdst.solution, bestStart, bestEnd);
      srcdst.tourLength += bestDelta;
      this.__addTabu(srcdst.tourLength);
      f.registerFEs(1, srcdst.solution, srcdst.tourLength);
    }
  }

  /**
   * add a tabu
   *
   * @param tabu
   *          the tabu
   */
  private final void __addTabu(final long tabu) {
    if (this.m_tabuSize < this.m_tabuListLength) {
      this.m_tabuList[this.m_tabuSize++] = tabu;
    } else {
      if (this.m_tabuListLength > 0) {
        this.m_tabuList[this.m_tabuStart] = tabu;
        this.m_tabuStart = ((this.m_tabuStart + 1) % this.m_tabuListLength);
      }
    }
  }

  /**
   * check if a given objective value is tabu
   *
   * @param f
   *          the objective value
   * @return {@code true} if it is tabu, {@code false} otherwise
   */
  private final boolean __isTabu(final long f) {
    for (final long t : this.m_tabuList) {
      if (t == f) {
        return true;
      }
      if (t <= 0L) {
        return false;
      }
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final TabuSearchObjective clone() {
    final TabuSearchObjective res;
    final PermutationUpdateOperator[] ops;
    int i;

    res = ((TabuSearchObjective) (super.clone()));
    res.m_ops = ops = res.m_ops.clone();
    for (i = ops.length; (--i) >= 0;) {
      ops[i] = ops[i].clone();
    }

    this.m_tabuListLength = (-1);
    this.m_tabuList = null;

    return res;
  }

  /** {@inheritDoc} */
  @Override
  public void configure(final Configuration config) {
    super.configure(config);

    this.m_absolute = config.getInt(
        TabuSearchObjective.PARAM_ABSOLUTE_TABU_LIST_LENGTH, 0,
        Integer.MAX_VALUE, this.m_absolute);
    this.m_rate = config.getInt(
        TabuSearchObjective.PARAM_TABU_LIST_LENGTH_RATE, 0,
        Integer.MAX_VALUE, this.m_rate);
    this.m_useFirstImprovement = config.getBoolean(
        TabuSearchObjective.PARAM_USE_FIRST_IMPROVEMENT,
        this.m_useFirstImprovement);

    this.m_tabuListLength = (-1);
    this.m_tabuList = null;
  }

  /** {@inheritDoc} */
  @Override
  public void printConfiguration(final PrintStream ps) {
    super.printConfiguration(ps);

    Configurable.printKey(TabuSearchObjective.PARAM_TABU_LIST_LENGTH, ps);
    ps.println(this.m_tabuListLength);
    Configurable.printKey(
        TabuSearchObjective.PARAM_ABSOLUTE_TABU_LIST_LENGTH, ps);
    ps.println(this.m_absolute);
    Configurable.printKey(TabuSearchObjective.PARAM_TABU_LIST_LENGTH_RATE,
        ps);
    ps.println(this.m_rate);
    Configurable.printKey(TabuSearchObjective.PARAM_USE_FIRST_IMPROVEMENT,
        ps);
    ps.println(this.m_useFirstImprovement);
    Configurable
        .printKey(TabuSearchObjective.PARAM_UPDATING_OPERATORS, ps);
    Configurable.printlnObject(this.m_ops, ps);
  }

  /** {@inheritDoc} */
  @Override
  public void beginRun(final ObjectiveFunction f) {
    super.beginRun(f);
    TSPModule.invokeBeginRun(f, this.m_ops);

    this.m_tabuListLength = (this.m_absolute + (int) (this.m_rate * Math
        .sqrt(f.n())));
    this.m_tabuList = new long[this.m_tabuListLength];
    this.m_tabuSize = 0;
    this.m_tabuStart = 0;
  }

  /** {@inheritDoc} */
  @Override
  public void endRun(final ObjectiveFunction f) {

    this.m_tabuList = null;
    this.m_tabuSize = 0;
    this.m_tabuStart = 0;

    try {
      TSPModule.invokeEndRun(f, this.m_ops);
    } finally {
      super.endRun(f);
    }
  }
}