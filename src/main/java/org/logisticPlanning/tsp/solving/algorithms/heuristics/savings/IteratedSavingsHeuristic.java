package org.logisticPlanning.tsp.solving.algorithms.heuristics.savings;

import org.logisticPlanning.tsp.benchmarking.instances.Instance;
import org.logisticPlanning.tsp.benchmarking.objective.ObjectiveFunction;
import org.logisticPlanning.tsp.solving.Individual;
import org.logisticPlanning.tsp.solving.TSPAlgorithmRunner;
import org.logisticPlanning.tsp.solving.utils.NodeManager;
import org.logisticPlanning.utils.math.random.Randomizer;

/**
 * This class applies the
 * {@link org.logisticPlanning.tsp.solving.algorithms.heuristics.savings.SavingsHeuristic
 * savings heuristic} again and again with different depots, until either
 * all depots have been tested or the runtime is up.
 */
public class IteratedSavingsHeuristic extends SavingsHeuristic {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** instantiate */
  public IteratedSavingsHeuristic() {
    super("Iterated "); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public void solve(final ObjectiveFunction f, final Individual<int[]> dest) {
    final NodeManager nm;
    final Randomizer r;

    nm = new NodeManager();
    nm.init(f.n());

    r = f.getRandom();
    for (;;) {
      this.solve(f, dest, nm.deleteRandom(r));
      if (f.shouldTerminate() || nm.isEmpty()) {
        return;
      }
    }
  }

  /**
   * Execute the iterated savings heuristic
   *
   * @param args
   *          the command line arguments
   */
  public static void main(final String[] args) {
    TSPAlgorithmRunner.benchmark(Instance.SYMMETRIC_INSTANCES,
        IteratedSavingsHeuristic.class,//
        args);
  }

}
