package test.junit.org.logisticPlanning.tsp.solving.algorithms.metaheuristics.permutation.ea.resizeStrategies;

import java.util.Random;

import org.logisticPlanning.tsp.solving.algorithms.metaheuristics.general.ea.populationResize.ShrinkOnSuccess;
import org.logisticPlanning.tsp.solving.algorithms.metaheuristics.permutation.ea.PermutationEA;

import test.junit.org.logisticPlanning.tsp.solving.algorithms.TSPAlgorithmSymmetricTest;

/**
 * the test of the permutation EA with a population shrinking on success
 */
public class PermutationEAParentsSurviveShrinkOnSuccessTest extends
    TSPAlgorithmSymmetricTest {

  /** create */
  public PermutationEAParentsSurviveShrinkOnSuccessTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected PermutationEA createAlgorithm() {
    PermutationEA res;
    Random r;
    int mu, lambda;

    res = new PermutationEA();
    res.setParentsSurvive(true);

    r = new Random();
    mu = (1 + r.nextInt(512));
    lambda = (mu + 1 + r.nextInt(512));
    res.setMu(mu);
    res.setLambda(lambda);
    res.setCrossoverRate(r.nextDouble());
    res.setResizeStrategy(new ShrinkOnSuccess());

    return res;
  }
}
