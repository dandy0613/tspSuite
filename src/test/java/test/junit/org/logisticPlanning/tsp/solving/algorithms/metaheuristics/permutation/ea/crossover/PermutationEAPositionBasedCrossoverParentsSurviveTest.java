package test.junit.org.logisticPlanning.tsp.solving.algorithms.metaheuristics.permutation.ea.crossover;

import org.logisticPlanning.tsp.solving.algorithms.metaheuristics.permutation.ea.PermutationEA;
import org.logisticPlanning.tsp.solving.operators.permutation.recombination.PermutationPositionBasedCrossover;

import test.junit.org.logisticPlanning.tsp.solving.algorithms.metaheuristics.permutation.ea.PermutationEAParentsSurviveTest;

/**
 * the test of the permutation EA with position-based crossover
 */
public class PermutationEAPositionBasedCrossoverParentsSurviveTest extends
    PermutationEAParentsSurviveTest {

  /** create */
  public PermutationEAPositionBasedCrossoverParentsSurviveTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected PermutationEA createAlgorithm() {
    PermutationEA ea;

    ea = super.createAlgorithm();
    ea.setBinaryOperator(new PermutationPositionBasedCrossover());

    return ea;
  }
}
