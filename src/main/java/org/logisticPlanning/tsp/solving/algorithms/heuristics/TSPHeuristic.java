package org.logisticPlanning.tsp.solving.algorithms.heuristics;

import org.logisticPlanning.tsp.benchmarking.objective.ObjectiveFunction;
import org.logisticPlanning.tsp.solving.Individual;
import org.logisticPlanning.tsp.solving.TSPAlgorithm;

/**
 * <p>
 * A TSP heuristic is a special TSP algorithm that can return one single
 * {@link org.logisticPlanning.tsp.solving.Individual individual} instance
 * holding the (best) solution discovered. We can use this as base class
 * for some heuristics in order to make it more easy to &quot;plug&quot;
 * them into other algorithms.
 * </p>
 * <h2>Implementation Guide for TSP Algorithms</h2>
 * <p id="implementingAnAlgorithm">
 * The TSP algorithm evaluation framework provides automation for most of
 * the steps necessary to conduct an experiment. It will, for example,
 * automatically apply your algorithm to several benchmark sets,
 * automatically in parallel with as many CPUs you have available, and
 * store its results in files. In order to use these features, there are a
 * set of implementation requirements that must be followed. We discuss
 * them in the following.
 * </p>
 * <ol>
 * <li>
 * <p>
 * Your algorithm must be implemented as subclass of the class
 * {@link org.logisticPlanning.tsp.solving.TSPAlgorithm TSPAlgorithm} and
 * override the method
 * {@link org.logisticPlanning.tsp.solving.TSPAlgorithm#solve(org.logisticPlanning.tsp.benchmarking.objective.ObjectiveFunction)
 * solve(ObjectiveFunction)}. This method is where you must put your
 * algorithm implementation. It has no return value, since the
 * {@link org.logisticPlanning.tsp.benchmarking.objective.ObjectiveFunction
 * ObjectiveFunction} will collect all information (including your solution
 * and the objective values) automatically. At the same time, the
 * {@link org.logisticPlanning.tsp.benchmarking.objective.ObjectiveFunction
 * ObjectiveFunction} is also the source of all information for your
 * algorithm. It provides you, e.g., the the
 * {@link org.logisticPlanning.tsp.benchmarking.objective.ObjectiveFunction#n()
 * number} of cities in the TSP, the
 * {@link org.logisticPlanning.tsp.benchmarking.objective.ObjectiveFunction#distance(int, int)
 * distance} between two cities, and ways to evaluate the total distance of
 * a tour represented as
 * {@link org.logisticPlanning.tsp.benchmarking.objective.ObjectiveFunction#evaluate(int[])
 * city permutation} or
 * {@link org.logisticPlanning.tsp.benchmarking.objective.ObjectiveFunction#evaluateAdj(int[])
 * adjacency list}, amongst others.
 * </p>
 * <p>
 * Some example algorithm implementations can be found
 * {@link org.logisticPlanning.tsp.solving.algorithms.localSearch.permutation.hc.UpdatingPermutationHillClimber
 * here},
 * {@link org.logisticPlanning.tsp.solving.algorithms.localSearch.permutation.vns.PermutationRNS
 * here}, and
 * {@link org.logisticPlanning.tsp.solving.algorithms.metaheuristics.permutation.paco.PACO
 * here}.
 * </p>
 * </li>
 * <li>
 * <p>
 * {@link org.logisticPlanning.tsp.solving.TSPAlgorithm TSPAlgorithm} has a
 * constructor which takes a {@link java.lang.String String} holding the
 * algorithm name as parameter. Your own algorithm class must implement a
 * public zero-parameter constructor. This constructor must call the
 * inherited constructor and supply the name. The name can contain spaces
 * and should be the same name that you would also use in a paper or other
 * document for your algorithm. For example &quot;
 * {@link org.logisticPlanning.tsp.solving.algorithms.heuristics.doubleEndedNearestNeighbor.DoubleEndedNearestNeighborHeuristic
 * Double-Ended Nearest Neighbor Heuristic}&quot; is such a name.
 * </p>
 * </li>
 * <li>
 * <p>
 * The benchmark environment supports multi-threading, i.e., it will
 * execute as many runs in parallel as possible (1 thread per CPU), in
 * order to speed up the experiment. However,
 * {@link org.logisticPlanning.tsp.solving.TSPAlgorithm algorithms} do not
 * need to be thread-safe (and
 * {@link org.logisticPlanning.tsp.benchmarking.objective.ObjectiveFunction
 * objective functions} are not anyway). One instance of your algorithm
 * will always and only be accessed/executed by one single thread.
 * </p>
 * <p id="cloneAlgo">
 * This is possible by first creating one instance of your algorithm and
 * then {@link java.lang.Object#clone() cloning} it for each thread. On one
 * hand, you do not need to take care of synchronization (and we do not
 * waste runtime for such stuff). On the other hand, this also means that
 * you need to take good care of member variables and copy them by
 * overriding the {@link org.logisticPlanning.utils.NamedObject#clone()
 * clone method}. If your algorithm stores mutable objects as member
 * variables, then you must also clone them properly. Otherwise, the
 * instances of your algorithm that run in parallel will work on the same
 * data structures and produce corrupt results.
 * </p>
 * <p>
 * It is common that your algorithm may hold a member variable of type,
 * say, {@code double[]}, to avoid memory allocation in subsequent runs.
 * When cloning, this variable must be set to either {@code null} in the
 * clone or cloned as well. Otherwise, both algorithm instances (the
 * original and the clone) will point to the same array, use it in
 * parallel, and wreck havoc. Zounds! The same holds for any references to
 * objects or sub-algorithms that your method uses. As soon as these
 * objects are mutable or may hold variables that may change, you must
 * clone them as well.
 * </p>
 * <p>
 * Some examples for cloneable algorithms are given
 * {@link org.logisticPlanning.tsp.solving.algorithms.localSearch.permutation.hc.UpdatingPermutationHillClimber#clone()
 * here},
 * {@link org.logisticPlanning.tsp.solving.algorithms.localSearch.permutation.multiNeighborhoodSearch.MultiNeighborhoodSearch#clone()
 * here}, and
 * {@link org.logisticPlanning.tsp.solving.algorithms.metaheuristics.permutation.paco.PACO#clone()
 * here}.
 * </p>
 * </li>
 * <li>
 * <p>
 * Many algorithms have parameters. An
 * {@link org.logisticPlanning.tsp.solving.algorithms.metaheuristics.general.ea.EA}
 * may have a
 * {@link org.logisticPlanning.tsp.solving.algorithms.metaheuristics.general.ea.EA#setCrossoverRate(double)
 * crossover rate}, the
 * {@link org.logisticPlanning.tsp.solving.algorithms.metaheuristics.permutation.paco.PACO
 * population-based ACO} algorithm has an
 * {@link org.logisticPlanning.tsp.solving.algorithms.metaheuristics.permutation.paco.PACO#setAlpha(double)
 * alpha} and a
 * {@link org.logisticPlanning.tsp.solving.algorithms.metaheuristics.permutation.paco.PACO#setBeta(double)
 * beta} parameter, for instance. Our framework provides the ability of
 * setubg algorithm parameters via the command line or an ini-file and to
 * log these parameter values into the log files automatically. This is
 * very useful, as the algorithm settings should always be part of the
 * experiment documentation and this way, that is ensured automatically.
 * </p>
 * <p>
 * In order to make use of this facility, you should override the following
 * three methods, which are inherited from the class
 * {@link org.logisticPlanning.utils.config.Configurable Configurable}:
 * </p>
 * <ol>
 * <li>
 * {@link org.logisticPlanning.utils.config.Configurable#printParameters(java.io.PrintStream)
 * printParameters} should print &quot;parameter-name
 * parameter-description&quot; pairs to the {@link java.io.PrintStream
 * PrintStream} that it gets as parameter. The user can invoke the
 * benchmarker with the {@code -help} command line option and will then get
 * this information displayed. You must always call the
 * super-implementation of this method before executing own code.</li>
 * <li>The method
 * {@link org.logisticPlanning.utils.config.Configurable#printConfiguration(java.io.PrintStream)
 * printConfiguration}, too, gets a {@link java.io.PrintStream PrintStream}
 * as parameter. It prints the configuration of the object to that stream
 * in form of &quot;parameter-name parameter-value&quot; pairs. This method
 * is called by the benchmarking environment when the log files are
 * written. You must always call the super-implementation of this method
 * before executing own code.</li>
 * <li id="overrideConfigure">
 * {@link org.logisticPlanning.utils.config.Configurable#configure(org.logisticPlanning.utils.config.Configuration)
 * configure} receives an instance of the class
 * {@link org.logisticPlanning.utils.config.Configuration Configuration}.
 * This class has loaded all parameters from the command line (and possible
 * ini-files) and provides several convenient methods to access them, e.g.,
 * {@link org.logisticPlanning.utils.config.Configuration#getString(String, String)
 * getString},
 * {@link org.logisticPlanning.utils.config.Configuration#getFile(String, java.io.File)
 * getFile}, and
 * {@link org.logisticPlanning.utils.config.Configuration#getLong(String, long, long, long)
 * getLing}, each taking a parameter name, a default values (and possible
 * valid range limits) as parameter and returning a value of the type
 * implied by their name. You must always call the super-implementation of
 * this method before executing own code in order to set the parameters of
 * your algorithm.</li>
 * </ol>
 * <p>
 * Some examples for configurable algorithms are given
 * {@link org.logisticPlanning.tsp.solving.algorithms.metaheuristics.general.ea.EA
 * here},
 * {@link org.logisticPlanning.tsp.solving.algorithms.localSearch.permutation.multiNeighborhoodSearch.MultiNeighborhoodSearch
 * here}, and
 * {@link org.logisticPlanning.tsp.solving.algorithms.metaheuristics.permutation.paco.PACO
 * here}.
 * </p>
 * </li>
 * <li>
 * <p>
 * Starting at version 0.9.8 of TSP Suite, we provide run-depending
 * initialization and finalization support for algorithms and algorithm
 * modules. The new class
 * {@link org.logisticPlanning.tsp.solving.TSPModule} is introduced as
 * base-class for all algorithm modules (such as
 * {@link org.logisticPlanning.tsp.solving.operators.Operator search
 * operators} or {@link org.logisticPlanning.tsp.solving.gpm.GPM
 * genotype-phenotype mappings}) and the
 * {@link org.logisticPlanning.tsp.solving.TSPAlgorithm TSP algorithms}
 * themselves. This new class provides two methods,
 * {@link org.logisticPlanning.tsp.solving.TSPModule#beginRun(org.logisticPlanning.tsp.benchmarking.objective.ObjectiveFunction)}
 * and
 * {@link org.logisticPlanning.tsp.solving.TSPModule#endRun(org.logisticPlanning.tsp.benchmarking.objective.ObjectiveFunction)}
 * which must be called before and after a run, respectively. Each
 * algorithm module must then invoke them recursively for all of its
 * sub-components. The new method
 * {@link org.logisticPlanning.tsp.solving.TSPAlgorithm#call(org.logisticPlanning.tsp.benchmarking.objective.ObjectiveFunction)}
 * now acts as a wrapper for
 * {@link org.logisticPlanning.tsp.solving.TSPAlgorithm#solve(org.logisticPlanning.tsp.benchmarking.objective.ObjectiveFunction)}
 * and invokes them. This allows for a more targeted allocation and
 * de-allocation of data structures for each run.
 * </p>
 * </li>
 * <li>
 * <p id="MyHeuristic">
 * The last step is to provide a {@code main} method which invokes the
 * benchmarking system on your algorithm class. Assume that your class has
 * the name {@code MyHeuristic}, usually it would contain a method
 * {@code main} as given <a href="#invokeMyHeuristic">below</a>,
 * </p>
 * <table border="1" id="invokeMyHeuristic" style="margin-left:auto;margin-right:auto">
 * <tr>
 * <td>
 *
 * <pre class="altColor">
 * public static void main(final String[] args) {
 *     {@link org.logisticPlanning.tsp.solving.TSPAlgorithmRunner TSPAlgorithmRunner}.{@link org.logisticPlanning.tsp.solving.TSPAlgorithmRunner#benchmark(org.logisticPlanning.utils.collections.lists.ArrayListView, java.lang.Class, java.lang.String[]) benchmark}({@link org.logisticPlanning.tsp.benchmarking.instances.Instance#SYMMETRIC_INSTANCES Instance.SYMMETRIC_INSTANCES},
 *         MyHeuristic.class,
 *         args);
 *   }
 * </pre>
 *
 * </td>
 * </tr>
 * <tr>
 * <td>The main method which invokes the benchmarking system to test the
 * algorithm {@code MyHeuristic} for all
 * {@link org.logisticPlanning.tsp.benchmarking.instances.Instance#SYMMETRIC_INSTANCES
 * symmetric} <a
 * href="http://comopt.ifi.uni-heidelberg.de/software/TSPLIB95/">TSPLib
 * </a>&nbsp;[<a href="#cite_DACO1995TSPLIB"
 * style="font-weight:bold">1</a>, <a href="#cite_R1995T9"
 * style="font-weight:bold">2</a>, <a href="#cite_R1991ATSPL"
 * style="font-weight:bold">3</a>, <a href="#cite_W2003ROCFTB"
 * style="font-weight:bold">4</a>] instances.</td>
 * </tr>
 * </table>
 * <p>
 * In the <a href="#invokeMyHeuristic">above example</a>, the
 * {@link org.logisticPlanning.tsp.solving.TSPAlgorithmRunner
 * TSPAlgorithmRunner} will automatically apply your algorithm to all
 * {@link org.logisticPlanning.tsp.benchmarking.instances.Instance#SYMMETRIC_INSTANCES
 * symmetric} <a
 * href="http://comopt.ifi.uni-heidelberg.de/software/TSPLIB95/">TSPLib
 * </a>&nbsp;[<a href="#cite_DACO1995TSPLIB"
 * style="font-weight:bold">1</a>, <a href="#cite_R1995T9"
 * style="font-weight:bold">2</a>, <a href="#cite_R1991ATSPL"
 * style="font-weight:bold">3</a>, <a href="#cite_W2003ROCFTB"
 * style="font-weight:bold">4</a>] instances. If your algorithm can solve
 * {@link org.logisticPlanning.tsp.benchmarking.instances.Instance#ASYMMETRIC_INSTANCES
 * asymmetric} TSPs, you can instead choose
 * <code>{@link org.logisticPlanning.tsp.benchmarking.instances.Instance#ALL_INSTANCES Instance.ALL_INSTANCES}</code>
 * as parameter of the method
 * {@link org.logisticPlanning.tsp.solving.TSPAlgorithmRunner#benchmark(org.logisticPlanning.utils.collections.lists.ArrayListView, java.lang.Class, java.lang.String[])
 * benchmark}. Then, your algorithm will be applied to all (symmetric and
 * asymmetric) instances of <a
 * href="http://comopt.ifi.uni-heidelberg.de/software/TSPLIB95/"
 * >TSPLib</a>.
 * </p>
 * <p>
 * The second parameter of that method is the class of your algorithm
 * implementation, here {@code MyHeuristic} (which must be derived from
 * {@link org.logisticPlanning.tsp.solving.TSPAlgorithm TSPAlgorithm}), and
 * the third parameter is the array of command line arguments that has been
 * passed to your program (this will be used to construct the
 * {@link org.logisticPlanning.utils.config.Configuration Configuration}
 * instance to be handed to your algorithm's
 * {@link org.logisticPlanning.utils.config.Configurable#configure(org.logisticPlanning.utils.config.Configuration)
 * configure} method).
 * </p>
 * <p>
 * Once invoked, the
 * {@link org.logisticPlanning.tsp.solving.TSPAlgorithmRunner#benchmark(org.logisticPlanning.utils.collections.lists.ArrayListView, java.lang.Class, java.lang.String[])
 * benchmark} method will first instantiate your algorithm class (via the
 * zero-parameter, public constructor), then
 * {@link org.logisticPlanning.utils.config.Configuration Configuration}
 * instance to be handed to your algorithm's
 * {@link org.logisticPlanning.utils.config.Configurable#configure(org.logisticPlanning.utils.config.Configuration)
 * configure} it, and create one {@link java.lang.Object#clone() clone} of
 * the configured instance for each available processor. It then
 * step-by-step loads one
 * {@link org.logisticPlanning.tsp.benchmarking.instances.Instance problem
 * instance} after the other and applies the algorithm. Each clone of your
 * algorithm will perform a separate run in a separate thread, receiving a
 * separate instance of
 * {@link org.logisticPlanning.tsp.benchmarking.objective.ObjectiveFunction
 * ObjectiveFunction}. Once a run is completed, the log information is
 * written to the log file, along with your algorithm's
 * {@link org.logisticPlanning.utils.config.Configurable#printConfiguration(java.io.PrintStream)
 * configuration}.
 * </p>
 * </li>
 * <li id="testingTheAlgorithm">
 * <p>
 * Along with our benchmark code, we also provide a <a
 * href="https://en.wikipedia.org/wiki/Unit_testing">unit testing</a>
 * facility based on <a href="http://www.junit.org/">JUnit</a>&nbsp;[<a
 * href="#cite_B2009JPG" style="font-weight:bold">5</a>, <a
 * href="#cite_RS2005JRPMFPT" style="font-weight:bold">6</a>, <a
 * href="#cite_MH2004JIA" style="font-weight:bold">7</a>]. It is strongly
 * recommended that you run tests with your algorithm before doing real
 * experiments. Of course, our testing facility cannot decide whether your
 * algorithm is right or wrong, but it may be able to detect some
 * programming mistakes. (The current version cannot check if there are
 * concurrency-related errors, resulting from a missing or faulty cloning
 * method, see <a href="#cloneAlgo">here</a>).
 * </p>
 * <p>
 * You can easily create a unit test for your algorithm by extending the
 * class
 * {@link test.junit.org.logisticPlanning.tsp.solving.algorithms.TSPAlgorithmSymmetricTest
 * TSPAlgorithmSymmetricTest} if your algorithm can only solve
 * {@link org.logisticPlanning.tsp.benchmarking.instances.Instance#SYMMETRIC_INSTANCES
 * symmetric} TSP instances and
 * {@link test.junit.org.logisticPlanning.tsp.solving.algorithms.TSPAlgorithmSymmetricTest
 * TSPAlgorithmSymmetricTest} if it can also solve asymmetric instances. In
 * your new test, you only need to override the method
 * {@link test.junit.org.logisticPlanning.tsp.solving.algorithms.TSPAlgorithmSymmetricTest
 * createAlgorithm}. All what this method does is to return an instance of
 * {@link org.logisticPlanning.tsp.solving.TSPAlgorithm TSPAlgorithm} that
 * should be tested &ndash; in this case, an instance of your algorithm.
 * </p>
 * <p>
 * Assume that your algorithm is implemented as class {@code MyHeuristic}
 * in package {@code MyPackage}. If {@code MyHeuristic} is a solver for
 * symmetric TSPs, then the test class will look like the listing <a
 * href="#testMyHeuristic">below</a>. For solvers that also understand,
 * just replace {@code TSPAlgorithmSymmetricTest} with
 * {@code TSPAlgorithmAsymmetricTest}.
 * </p>
 * <table border="1" id="testMyHeuristic" style="margin-left:auto;margin-right:auto">
 * <tr>
 * <td>
 *
 * <pre class="altColor">
 * package test.junit.{@code MyPackage}.{@code MyHeuristic};
 * 
 * import {@code MyPackage}.{@code MyHeuristic};
 * 
 * import {@link test.junit.org.logisticPlanning.tsp.solving.algorithms.TSPAlgorithmSymmetricTest test.junit.org.logisticPlanning.tsp.solving.algorithms.TSPAlgorithmSymmetricTest};
 * 
 * public class {@code MyHeuristic}Test extends {@link test.junit.org.logisticPlanning.tsp.solving.algorithms.TSPAlgorithmSymmetricTest TSPAlgorithmSymmetricTest} {
 * 
 *   public {@code MyHeuristic}Test() {
 *     super();
 *   }
 * 
 *   {@code @Override}
 *   protected {@code MyHeuristic} createAlgorithm() {
 *     return new {@code MyHeuristic}();
 *   }
 * }
 * </pre>
 *
 * </td>
 * </tr>
 * <tr>
 * <td>A unit test for algorithm {@code MyHeuristic}.</td>
 * </tr>
 * </table>
 * <p>
 * The unit test will apply the algorithm to several small scale problems
 * with different runtime settings. If the algorithm produces invalid
 * results in these tests, the tests will fail. You must never use an
 * algorithm that failed one of the tests in an experiment.
 * </p>
 * <p>
 * Examples for tests can be found
 * {@link test.junit.org.logisticPlanning.tsp.solving.algorithms.heuristics.mst.MSTHeuristicTest
 * here},
 * {@link test.junit.org.logisticPlanning.tsp.solving.algorithms.metaheuristics.permutation.paco.PACOTest
 * here}, and
 * {@link test.junit.org.logisticPlanning.tsp.solving.algorithms.heuristics.edgeGreedy.EdgeGreedyHeuristicTest
 * here}.
 * </p>
 * </li>
 * </ol>
 * <p>
 * This way of making use of a multi-threaded environment, by doing as many
 * runs with separate copies of an algorithm, is more efficient than
 * starting several instances of the benchmarking process in parallel
 * (which still is possible). The reason is that the runs being performed
 * in parallel may share the same distance computers, as distance computers
 * are immutable and we therefore can save both memory and improve caching.
 * </p>
 * <h2>Running Experiments</h2>
 * <p id="runningExperiments">
 * So you have implemented <em>and tested</em> your algorithm (let's call
 * it again {@code MyHeuristic}). The <em>TSP Suite</em> system can
 * automatically run experiments with it. More precisely, it can
 * automatically apply the algorithm to the problem instance from the
 * well-known <a
 * href="http://comopt.ifi.uni-heidelberg.de/software/TSPLIB95/"
 * >TSPLib</a>&nbsp;[<a href="#cite_DACO1995TSPLIB"
 * style="font-weight:bold">1</a>, <a href="#cite_R1995T9"
 * style="font-weight:bold">2</a>, <a href="#cite_R1991ATSPL"
 * style="font-weight:bold">3</a>, <a href="#cite_W2003ROCFTB"
 * style="font-weight:bold">4</a>] benchmark set. During such an
 * experiment, your algorithm will be applied to each of the symmetric
 * instances of this benchmark set (and also the asymmetric ones, if you
 * have specified that in the <a href="#invokeMyHeuristic">main</a> method)
 * for <code>30</code> independent trials taking up to one hour each (under
 * default settings).
 * </p>
 * <p>
 * The general way to conduct an experiment is as follows:
 * </p>
 * <ol>
 * <li id="makeAlgorithmJar">
 * <p>
 * Generate a {@code jar} archive including your algorithms code. Let's say
 * you called your archive {@code myHeuristicJar.jar}. If you are using <a
 * href="http://www.eclipse.org/">Eclipse</a>&nbsp;[<a href="#cite_ECLIPSE"
 * style="font-weight:bold">8</a>], you could do that as follows:
 * </p>
 * <ol>
 * <li>Right-click on your project in Eclipse's package view.</li>
 * <li>Choose point &quot;Export&uot; in the pop-up menu coming up.</li>
 * <li>In the dialog that emerges select &quot;JAR file&quot; (under folder
 * &quot;Java&quot;).</li>
 * <li>Click &quot;next&quot;</li>
 * <li>In the new dialog, make sure that your project is selected as well
 * as all <em>sources of the TSP Suite.</em></li>
 * <li>Check the boxes &quot;Export generated class files and
 * resources&quot; and &quot;Export Java source files and resources&quot;.</li>
 * <li>In input line &quot;JAR file,&quot; select a path and file name
 * where you want your {@code jar} to emerge (e.g., a new folder and
 * <code id="myHeuristicJar">myHeuristicJar.jar</code>). Make sure the file
 * name ends with {@code .jar}.</li>
 * <li>Click &quot;Next&quot;.</li>
 * <li>Click &quot;Next&quot; (again).</li>
 * <li>Next to the input line for &quot;Main class,&quot; click
 * &quot;Browse&quot;.</li>
 * <li>In the dialog that pops up, select your algorithm's class (
 * {@code MyHeuristic}) and click &quot;OK&quot;.</li>
 * <li>Click &quot;Finish&quot; (again).</li>
 * <li>Congratulations: you have created a single binary holding your
 * algorithm implementation. This binary holds not only your project's
 * compiled class files, but also the source code. We suggest that you
 * store it together with the benchmarking results that you will obtain
 * with our system, since it is basically a part of your experiment's
 * documentation.</li>
 * </ol>
 * </li>
 * <li>
 * <p>
 * You can now run the experiments with your program by executing the
 * {@code jar} archive from the command line with the command
 * {@code java -jar myHeuristicJar.jar}.
 * </p>
 * <ol>
 * <li>Please check the <a href="#jarCommandLine">command line
 * parameters</a> that you can supply to the benchmarking system.</li>
 * <li>If you have overridden the
 * {@link org.logisticPlanning.utils.config.Configurable#configure(org.logisticPlanning.utils.config.Configuration)
 * configure} method in your algorithm class in order to <a
 * href="#overrideConfigure">expose its parameters</a> to the command line,
 * well, you should pass them through the command line as well.</li>
 * <li>You should run the experiments on a powerful machine with many
 * processors that you do not need for other stuff for, say, a week, since
 * they will take quite some time to run.</li>
 * <li>
 * <p>
 * If you set many parameter values, you can store them in an configuration
 * file as {@code key=value} pairs. If the configuration file's name is
 * {@code myConfigFile.txt}, then you would pass the command line parameter
 * &quot;
 * <code>{@link org.logisticPlanning.utils.config.Configuration#PARAM_PROPERTY_FILE configFile}=myConfigFile.txt</code>
 * &quot;. Then, all parameters in {@code configFile.txt} will be treated
 * as if they were directly provided through the command line.
 * </p>
 * </li>
 * </ol>
 * </li>
 * </ol>
 * <p>
 * That's it. Now your experiments are running!
 * </p>
 * <h2>Parallel and Distributed Benchmarking</h2>
 * <p>
 * Please be aware that the experiments may take quite some time. Ideally,
 * you should run them on a strong computer with many processors that you
 * do not need to touch for about a week&hellip; &hellip;and after that,
 * you will have a nice set of log files. In order to speed up the process,
 * we provide parallelization and distribution support for experimentation.
 * </p>
 * <p>
 * Benchmarking is done by (potentially) multiple threads. Each thread does
 * the following: It iterates through the list of benchmarking instances
 * (from the <a
 * href="http://comopt.ifi.uni-heidelberg.de/software/TSPLIB95/"
 * >TSPLib</a>). For each benchmark instance it will define an output
 * folder of the corresponding name inside the overall output folder &quot;
 * {@link org.logisticPlanning.tsp.benchmarking.objective.Benchmark#PARAM_DEST_DIR
 * outputDir}&quot;. It will now iterate through the runs that should be
 * done, from <code>1</code> to
 * {@link org.logisticPlanning.tsp.benchmarking.objective.Benchmark#PARAM_MAX_RUNS
 * maxRuns}, where <code>maxRuns</code> is 30, by default. Each combination
 * of benchmark instance and run index defines a unique path and file name.
 * The thread tries to create the file with the corresponding name with the
 * {@link java.io.File#createNewFile()} which reports <code>true</code> if
 * a new file was created and <code>false</code> if the file already
 * existed. If the thread managed to create the file as &quot;new&quot;, it
 * will immediately begin to perform the run and after the run is finished,
 * store its results into the file. If the file already existed, the thread
 * will just move to the next run index. If the run index reaches
 * <code>maxRuns</code>, it moves to the next benchmark instance.
 * </p>
 * <p>
 * This mechanism allows the most primitive and yet surprisingly robust way
 * to enable parallelization, distribution, and restarting of experiments.
 * For instance, amongst the threads in a single
 * {@link org.logisticPlanning.tsp.solving.TSPAlgorithmRunner process}, no
 * communication is necessary. Each thread will automatically find the next
 * run it should perform and no two threads may try to do the same work.
 * </p>
 * <p>
 * Distribution, e.g., when executing the experiments in a cluster, can be
 * achieved the same way: You only need to let their
 * {@link org.logisticPlanning.tsp.benchmarking.objective.Benchmark#PARAM_DEST_DIR
 * outputDir} parameters point to the same <em>shared</em> folder. This
 * way, it is possible to let 3 computers with 24 threads each work on the
 * same experiment. Since there are about 110 benchmarking instances for
 * symmetric problems, the total required runtime for a symmetric TSP
 * solver could be reduced to 110 * 30 * 1 hour divided by 3 * 24, i.e., to
 * about two days (unless the solver can solve some problems in less than
 * an hour, in which case the time would be less, too).
 * </p>
 * <p id="restart">
 * Restarting experiments is also easy because of this mechanism: A
 * completed run will have an associated log file of non-zero size. Since
 * the log information is only written once the run is completed (in order
 * to not bias the time measurements), all runs that have been started but
 * are incomplete will have log files of zero size. Thus, if your computer
 * crashes or something, just delete all zero-sized files and you can
 * restart the benchmarker. It will resume its work and not repeat work
 * that has already been done. Under <a
 * href="http://en.wikipedia.org/wiki/Linux">Linux</a>, you could do
 * something like
 * <code>find&nbsp;~/{@link org.logisticPlanning.tsp.benchmarking.objective.Benchmark#PARAM_DEST_DIR outputDir}/&nbsp;-type&nbsp;f&nbsp;-empty&nbsp;-delete</code>
 * or
 * <code>find&nbsp;~/{@link org.logisticPlanning.tsp.benchmarking.objective.Benchmark#PARAM_DEST_DIR outputDir}/&nbsp;-size&nbsp;&nbsp;0&nbsp;-print0&nbsp;|xargs&nbsp;-0&nbsp;rm</code>
 * , where
 * {@link org.logisticPlanning.tsp.benchmarking.objective.Benchmark#PARAM_DEST_DIR
 * outputDir} is the output folder you have defined.
 * </p>
 * <h2>Command Line Parameters of the Benchmarking Environment</h2>
 * <p id="jarCommandLine">
 * The benchmarking environment and running can be parameterized via
 * command line or config files. The following parameters are supported:
 * </p>
 * <ol>
 * <li>
 * <p>
 * If &quot;
 * <code>{@link org.logisticPlanning.tsp.solving.TSPAlgorithmRunner#PARAM_MAX_THREADS maxThreads}=nnn</code>
 * &quot; is supplied, then the benchmarker will use at most {@code nnn}
 * threads. Otherwise, it will use one thread per available processor.
 * </p>
 * </li>
 * <li>
 * <p>
 * If &quot;
 * <code>{@link org.logisticPlanning.tsp.benchmarking.objective.Benchmark#PARAM_DEST_DIR outputDir}=dir</code>
 * &quot; is set, the output of the program, i.e., the log files, will be
 * written to the directory {@code dir}. Otherwise, they will be written to
 * the current directory. Generally, the benchmarking environment will
 * automatically create a directory structure which allows us to easily
 * distinguish symmetric and asymmetric TSPs and log files for different
 * problem instances.
 * </p>
 * </li>
 * <li>
 * <p>
 * If you pass the arguments &quot;
 * <code>-{@link org.logisticPlanning.tsp.solving.TSPAlgorithmRunner#PARAM_RUNNER_LOGGER algoLogger}="global";ALL</code>
 * &quot;, you will receive logging information about the benchmarking
 * progress.
 * </p>
 * </li>
 * <li>
 * <p>
 * You may pass in parameters like &quot;
 * <code>{@link org.logisticPlanning.tsp.benchmarking.objective.CreatorInfo#PARAM_RESEARCHER_NAME researcherName}=&quot;first name last name&quot;</code>
 * &quot; to have your name in the log files (see class
 * {@link org.logisticPlanning.tsp.benchmarking.objective.CreatorInfo
 * CreatorInfo} for more author-related parameters).
 * </p>
 * </li>
 * <li>
 * <p>
 * If &quot;
 * <code>{@link org.logisticPlanning.tsp.solving.TSPAlgorithmRunner#PARAM_DET_INIT_CLASS initclass}=fullyQualifiedClassName</code>
 * &quot; is supplied, then the benchmarker will automatically instantiate
 * the class {@code fullyQualifiedClassName},
 * {@link org.logisticPlanning.utils.config.Configurable#configure(org.logisticPlanning.utils.config.Configuration)
 * configure} it, and apply it as
 * {@link org.logisticPlanning.tsp.benchmarking.objective.ObjectiveFunction#beginDeterministicInitialization(org.logisticPlanning.utils.NamedObject)
 * deterministic initialization procedure} to each instance before invoking
 * your algorithm. The result of this procedure is then available to your
 * algorithm via the
 * {@link org.logisticPlanning.tsp.benchmarking.objective.ObjectiveFunction#getDeterministicInitializationLogPoint()
 * initialization log point} and
 * {@link org.logisticPlanning.tsp.benchmarking.objective.ObjectiveFunction#getCopyOfBestAdj(int[])
 * getCopyOfBestAdj} or
 * {@link org.logisticPlanning.tsp.benchmarking.objective.ObjectiveFunction#getCopyOfBest(int[])
 * getCopyOfBest} at startup (the data provided by the latter two methods
 * will obviously change during the algorithm run). If this parameter is
 * not provided, no such deterministic initialization will be performed.
 * </p>
 * <p>
 * A deterministic initialization procedure is often useful to start your
 * algorithm with a reasonably good approximation of the solution. This is
 * especially often done in local search methods, that usually often invoke
 * a {@link org.logisticPlanning.tsp.solving.algorithms.heuristics
 * heuristic}. Due to this command line parameter, you do not need to
 * hard-code the choice of that algorithm in your program and instead
 * comfortably test it with different heuristics. Also, this allows us to
 * do some runtime optimization: As a deterministic initializer should
 * always return the same result, we need to compute that only once per
 * benchmark case and can re-use it for each run.
 * </p>
 * </li>
 * <li>
 * <p>
 * The parameter &quot;
 * <code>{@link org.logisticPlanning.tsp.benchmarking.objective.Benchmark#PARAM_MAX_RUNS maxRuns}=nnn</code>
 * &quot; can be provided to set the number of runs to be performed for
 * each benchmark case. The
 * {@link org.logisticPlanning.tsp.benchmarking.objective.Benchmark#DEFAULT_MAX_RUNS
 * default value} is {@code 30}. I suggest to not change that value, i.e.,
 * to not provide this parameter.
 * </p>
 * </li>
 * <li>
 * <p>
 * The parameter &quot;
 * <code>{@link org.logisticPlanning.tsp.benchmarking.objective.Benchmark#PARAM_MAX_FES maxFEs}=nnn</code>
 * &quot; can be provided to set the maximum number {@code nnn} of
 * {@link org.logisticPlanning.tsp.benchmarking.objective.LogPoint#getConsumedFEs()
 * function evaluations} to be performed for each benchmark run. The
 * default value is <code>100*n<sup>3</sup></code>, where {@code n} is the
 * number of cities of the benchmark cases. I suggest to not change that
 * value, i.e., to not provide this parameter.
 * </p>
 * </li>
 * <li>
 * <p>
 * The parameter &quot;
 * <code>{@link org.logisticPlanning.tsp.benchmarking.objective.Benchmark#PARAM_MAX_DES maxDEs}=nnn</code>
 * &quot; can be provided to set the maximum number {@code nnn} of
 * {@link org.logisticPlanning.tsp.benchmarking.objective.LogPoint#getConsumedDEs()
 * distance evaluations} to be performed for each benchmark run. The
 * default value is <code>100*n<sup>4</sup></code>, where {@code n} is the
 * number of cities of the benchmark cases. I suggest to not change that
 * value, i.e., to not provide this parameter.
 * </p>
 * </li>
 * <li>
 * <p>
 * The parameter &quot;
 * <code>{@link org.logisticPlanning.tsp.benchmarking.objective.Benchmark#PARAM_MAX_TIME maxTime}=nnn</code>
 * &quot; can be provided to set the maximum runtime, specified as maximum
 * number {@code nnn} of
 * {@link org.logisticPlanning.tsp.benchmarking.objective.LogPoint#getConsumedRuntime()
 * milliseconds} that a benchmark run may take. The default value is
 * {@link org.logisticPlanning.tsp.benchmarking.objective.Benchmark#DEFAULT_MAX_TIME_PER_RUN
 * 3600000}, i.e., one hour. I suggest to not change that value, i.e., to
 * not provide this parameter.
 * </p>
 * </li>
 * <li>
 * <p>
 * If you set many parameter values, you can store them in an configuration
 * file as {@code key=value} pairs. If the configuration file's name is
 * {@code myConfigFile.txt}, then you would pass the command line parameter
 * &quot;
 * <code>{@link org.logisticPlanning.utils.config.Configuration#PARAM_PROPERTY_FILE configFile}=myConfigFile.txt</code>
 * &quot;. Then, all parameters in {@code configFile.txt} will be treated
 * as if they were directly provided through the command line.
 * </p>
 * </li>
 * </ol>
 * <h2 id="heuristicAlgorithmsList">
 * {@linkplain org.logisticPlanning.tsp.solving.algorithms.heuristics
 * Implemented Heuristics}</h2>
 * <p>
 * Heuristics are usually simple procedures that build one solution in a
 * constructive or iterative manner. The following algorithms from this
 * family have been implemented:
 * </p>
 * <ol>
 * <li>The
 * {@link org.logisticPlanning.tsp.solving.algorithms.heuristics.nearestNeighbor.NearestNeighborHeuristic
 * Nearest-Neighbor Heuristic}&nbsp;[<a href="#cite_RSL1977AAOSHFTTSP"
 * style="font-weight:bold">9</a>, <a href="#cite_RSSL2009AAOSHFTTSP"
 * style="font-weight:bold">10</a>, <a href="#cite_JMB1997TTSPACSILO"
 * style="font-weight:bold">11</a>, <a href="#cite_JMG2004EAOHFTS"
 * style="font-weight:bold">12</a>] starts at a given node and builds a
 * tour by iteratively adding the nearest node to it.</li>
 * <li>The
 * {@link org.logisticPlanning.tsp.solving.algorithms.heuristics.doubleEndedNearestNeighbor.DoubleEndedNearestNeighborHeuristic
 * Double-Ended Nearest-Neighbor Heuristic}&nbsp;[<a
 * href="#cite_JMB1997TTSPACSILO" style="font-weight:bold">11</a>, <a
 * href="#cite_JMG2004EAOHFTS" style="font-weight:bold">12</a>] starts a
 * given node and then iteratively builds a tour by adding the nearest
 * nodes to it. Different from the nearest-neighbor heuristic, it checks
 * both ends of the current tour for nearest neighbors.</li>
 * <li>The
 * {@link org.logisticPlanning.tsp.solving.algorithms.heuristics.edgeGreedy.EdgeGreedyHeuristic
 * Edge-Greedy Heuristic}&nbsp;[<a href="#cite_JMB1997TTSPACSILO"
 * style="font-weight:bold">11</a>, <a href="#cite_JMG2004EAOHFTS"
 * style="font-weight:bold">12</a>] constructs a tour by adding the
 * cheapest edge (that does not violate any validity constraint).</li>
 * <li>The
 * {@link org.logisticPlanning.tsp.solving.algorithms.heuristics.mst.MSTHeuristic
 * (Double) Minimum Spanning Tree Heuristic}&nbsp;[<a
 * href="#cite_J1930OJPMZDPOB" style="font-weight:bold">13</a>, <a
 * href="#cite_P1957SCNASG" style="font-weight:bold">14</a>] first computes
 * a <a href="https://en.wikipedia.org/wiki/Minimum_spanning_tree">minimum
 * spanning tree</a> (MST)&nbsp;[<a href="#cite_J2004MSTSPT"
 * style="font-weight:bold">15</a>] and then traces it from the root note
 * to form a tour by skipping any already visited nodes.</li>
 * <li>The
 * {@link org.logisticPlanning.tsp.solving.algorithms.heuristics.savings.SavingsHeuristic
 * Savings Heuristic}&nbsp;[<a href="#cite_CW1964SOVFACDTANODP"
 * style="font-weight:bold">16</a>, <a href="#cite_JMB1997TTSPACSILO"
 * style="font-weight:bold">11</a>, <a href="#cite_JMG2004EAOHFTS"
 * style="font-weight:bold">12</a>] starts by choosing a depot. For each of
 * the remaining {@code n-1} nodes, a tour from the depot to the node and
 * directly back is assumed. Iteratively, the cheapest way to combine tours
 * is sought and applied, until only one tour &ndash; the solution to the
 * TSP &dash; remains.</li>
 * </ol>
 * <h2>References</h2>
 * <ol>
 * <li><div><span id="cite_DACO1995TSPLIB" /><a
 * href="http://comopt.ifi.uni-heidelberg.de/people/reinelt/">Gerhard
 * Reinelt</a>: <span
 * style="font-style:italic;font-family:cursive;">&ldquo;
 * TSPLIB,&rdquo;</span> (Website), 1995, Heidelberg, Germany: Office
 * Research Group Discrete Optimization, Ruprecht Karls University of
 * Heidelberg. <div>link: [<a
 * href="http://comopt.ifi.uni-heidelberg.de/software/TSPLIB95/"
 * >1</a>]</div></div></li>
 * <li><div><span id="cite_R1995T9" /><a
 * href="http://comopt.ifi.uni-heidelberg.de/people/reinelt/">Gerhard
 * Reinelt</a>: <span style="font-weight:bold">&ldquo;TSPLIB
 * 95,&rdquo;</span> <span
 * style="font-style:italic;font-family:cursive;">Technical Report</span>
 * 1995; published by Heidelberg, Germany: Universit&#228;t Heidelberg,
 * Institut f&#252;r Mathematik. <div>link: [<a
 * href="http://comopt.ifi.uni-heidelberg.de/software/TSPLIB95/DOC.PS"
 * >1</a>]</div></div></li>
 * <li><div><span id="cite_R1991ATSPL" /><a
 * href="http://comopt.ifi.uni-heidelberg.de/people/reinelt/">Gerhard
 * Reinelt</a>: <span style="font-weight:bold">&ldquo;TSPLIB &#8212; A
 * Traveling Salesman Problem Library,&rdquo;</span> in <span
 * style="font-style:italic;font-family:cursive;">ORSA Journal on
 * Computing</span> 3(4):376&ndash;384, Fall&nbsp;1991; published by
 * Operations Research Society of America (ORSA). doi:&nbsp;<a
 * href="http://dx.doi.org/10.1287/ijoc.3.4.376">10.1287/ijoc.3.4.376</a>;
 * OCLC:&nbsp;<a
 * href="https://www.worldcat.org/oclc/60628815">60628815</a>;
 * ISSN:&nbsp;<a
 * href="https://www.worldcat.org/issn/08991499">0899-1499</a></div></li>
 * <li><div><span id="cite_W2003ROCFTB" /><a
 * href="http://www2.isye.gatech.edu/~wcook/">William John Cook</a>: <span
 * style="font-style:italic;font-family:cursive;">&ldquo;Results of
 * Concorde for TSPLib Benchmark,&rdquo;</span> (Website),
 * December&nbsp;2003, Atlanta, GA, USA: Georgia Institute of Technology,
 * H. Milton Stewart School of Industrial and Systems Engineering.
 * <div>link: [<a
 * href="http://www.tsp.gatech.edu/concorde/benchmarks/bench99.html"
 * >1</a>]</div></div></li>
 * <li><div><span id="cite_B2009JPG" /><a
 * href="http://en.wikipedia.org/wiki/Kent_Beck">Kent Beck</a>: <span
 * style="font-style:italic;font-family:cursive;">&ldquo;JUnit Pocket
 * Guide,&rdquo;</span> 2009, Sebastopol, CA, USA: O'Reilly Media, Inc..
 * ISBN:&nbsp;<a
 * href="https://www.worldcat.org/isbn/1449379028">1449379028</a>
 * and&nbsp;<a
 * href="https://www.worldcat.org/isbn/9781449379025">9781449379025</a>;
 * Google Book ID:&nbsp;<a
 * href="http://books.google.com/books?id=Ur_zMK0WQwIC"
 * >Ur_zMK0WQwIC</a></div></li>
 * <li><div><span id="cite_RS2005JRPMFPT" /><a
 * href="http://en.wikipedia.org/wiki/J._B._Rainsberger">Joe B.
 * Rainsberger</a> and&nbsp;<a
 * href="http://www.linkedin.com/in/sstirling">Scott Stirling</a>: <span
 * style="font-style:italic;font-family:cursive;">&ldquo;Junit Recipes:
 * Practical Methods for Programmer Testing,&rdquo;</span> 2005, Manning
 * Pubs Co, Greenwich, CT, USA: Manning Publications Co.. ISBN:&nbsp;<a
 * href="https://www.worldcat.org/isbn/1932394230">1932394230</a>
 * and&nbsp;<a
 * href="https://www.worldcat.org/isbn/9781932394238">9781932394238</a>;
 * Google Book ID:&nbsp;<a
 * href="http://books.google.com/books?id=5h7oDjuY5WYC"
 * >5h7oDjuY5WYC</a></div></li>
 * <li><div><span id="cite_MH2004JIA" />Vincent Massol and&nbsp;<a
 * href="http://www.linkedin.com/in/husted">Ted Husted</a>: <span
 * style="font-style:italic;font-family:cursive;">&ldquo;Junit In
 * Action,&rdquo;</span> 2004, Greenwich, CT, USA: Manning Publications
 * Co.. ISBN:&nbsp;<a
 * href="https://www.worldcat.org/isbn/8177225383">8177225383</a>
 * and&nbsp;<a
 * href="https://www.worldcat.org/isbn/9788177225389">9788177225389</a>;
 * Google Book ID:&nbsp;<a
 * href="http://books.google.com/books?id=P1mDmZUmje0C"
 * >P1mDmZUmje0C</a></div></li>
 * <li><div><span id="cite_ECLIPSE" /><span
 * style="font-style:italic;font-family:cursive;"
 * >&ldquo;Eclipse,&rdquo;</span> (Software), Ottawa, ON, Canada: Eclipse
 * Foundation. <div>link: [<a
 * href="http://www.eclipse.org/">1</a>]</div></div></li>
 * <li><div><span id="cite_RSL1977AAOSHFTTSP" />Daniel J. Rosenkrantz,
 * Richard E. Stearns, and&nbsp;Philip M. Lewis II: <span
 * style="font-weight:bold">&ldquo;An Analysis of Several Heuristics for
 * the Traveling Salesman Problem,&rdquo;</span> in <span
 * style="font-style:italic;font-family:cursive;">SIAM Journal on
 * Computing</span> 6(3):563&ndash;581, 1977; published by Philadelphia,
 * PA, USA: Society for Industrial and Applied Mathematics (SIAM).
 * doi:&nbsp;<a
 * href="http://dx.doi.org/10.1137/0206041">10.1137/0206041</a>;
 * ISSN:&nbsp;<a
 * href="https://www.worldcat.org/issn/00975397">0097-5397</a> and&nbsp;<a
 * href="https://www.worldcat.org/issn/10957111">1095-7111</a>;
 * CODEN:&nbsp;<a href=
 * "http://www-library.desy.de/cgi-bin/spiface/find/coden/www?code=SMJCAT">
 * SMJCAT</a></div></li>
 * <li><div><span id="cite_RSSL2009AAOSHFTTSP" />Daniel J. Rosenkrantz,
 * Richard E. Stearns, and&nbsp;Philip M. Lewis II: <span
 * style="font-weight:bold">&ldquo;An Analysis of Several Heuristics for
 * the Traveling Salesman Problem,&rdquo;</span> in <span
 * style="font-style:italic;font-family:cursive;">Fundamental Problems in
 * Computing: Essays in Honor of Professor Daniel J. Rosenkrantz</span>,
 * pages 45&ndash;69, S.S. Ravi and&nbsp;Sandeep K. Shukla, editors,
 * Dordrecht, Netherlands: Springer Netherlands. ISBN:&nbsp;<a
 * href="https://www.worldcat.org/isbn/9781402096877"
 * >978-1-4020-9687-7</a>; doi:&nbsp;<a
 * href="http://dx.doi.org/10.1007/978-1-4020-9688-4_3">10.1007/978-
 * 1-4020-9688-4_3</a></div></li>
 * <li><div><span id="cite_JMB1997TTSPACSILO" /><a
 * href="https://en.wikipedia.org/wiki/David_S._Johnson">David Stifler
 * Johnson</a> and&nbsp;<a
 * href="https://www.amherst.edu/people/facstaff/lamcgeoch">Lyle A.
 * McGeoch</a>: <span style="font-weight:bold">&ldquo;The Traveling
 * Salesman Problem: A Case Study in Local Optimization,&rdquo;</span> in
 * <span style="font-style:italic;font-family:cursive;">Local Search in
 * Combinatorial Optimization</span>, pages 215&ndash;310, Emile H. L.
 * Aarts and&nbsp;<a
 * href="https://en.wikipedia.org/wiki/Jan_Karel_Lenstra">Jan Karel
 * Lenstra</a>, editors, Estimation, Simulation, and Control &#8211;
 * Wiley-Interscience Series in Discrete Mathematics and Optimization,
 * Princeton, NJ, USA: Princeton University Press and&nbsp;Chichester, West
 * Sussex, UK: Wiley Interscience. ISBN:&nbsp;<a
 * href="https://www.worldcat.org/isbn/0691115222">0691115222</a>
 * and&nbsp;<a
 * href="https://www.worldcat.org/isbn/9780691115221">9780691115221</a>;
 * OCLC:&nbsp;<a
 * href="https://www.worldcat.org/oclc/45733970">45733970</a>; Google Book
 * ID:&nbsp;<a
 * href="http://books.google.com/books?id=NWghN9G7q9MC">NWghN9G7q9MC</a>.
 * <div>link: [<a
 * href="http://www.research.att.com/~dsj/papers/TSPchapter.pdf">1
 * </a>]</div></div></li>
 * <li><div><span id="cite_JMG2004EAOHFTS" /><a
 * href="https://en.wikipedia.org/wiki/David_S._Johnson">David Stifler
 * Johnson</a> and&nbsp;<a
 * href="https://www.amherst.edu/people/facstaff/lamcgeoch">Lyle A.
 * McGeoch</a>: <span style="font-weight:bold">&ldquo;Experimental Analysis
 * of Heuristics for the STSP,&rdquo;</span> in <span
 * style="font-style:italic;font-family:cursive;">The Traveling Salesman
 * Problem and its Variations</span>, chapter 369&ndash;443, pages
 * 369&ndash;443, <a href=
 * "http://www.rhul.ac.uk/computerscience/staffdirectory/ggutin/home.aspx">
 * Gregory Z. Gutin</a> and&nbsp;<a
 * href="http://www.sfu.ca/~apunnen/">Abraham P. Punnen</a>, editors,
 * volume 12 of Combinatorial Optimization, Norwell, MA, USA: Kluwer
 * Academic Publishers. ISBN:&nbsp;<a
 * href="https://www.worldcat.org/isbn/0306482134">0-306-48213-4</a>
 * and&nbsp;<a
 * href="https://www.worldcat.org/isbn/9781402006647">978-1-4020
 * -0664-7</a>; doi:&nbsp;<a
 * href="http://dx.doi.org/10.1007/0-306-48213-4_9"
 * >10.1007/0-306-48213-4_9</a>; OCLC:&nbsp;<a
 * href="https://www.worldcat.org/oclc/55085594">55085594</a>; Google Book
 * ID:&nbsp;<a
 * href="http://books.google.com/books?id=TRYkPg_Xf20C">TRYkPg_Xf20C</a>.
 * <div>link: [<a
 * href="http://www2.research.att.com/~dsj/papers/stspchap.pdf">1</a>];
 * CiteSeer<sup>x</sup><sub style="font-style:italic">&#946;</sub>:&nbsp;<a
 * href=
 * "http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.15.9438">10.1
 * .1.15 .9438</a></div></div></li>
 * <li><div><span id="cite_J1930OJPMZDPOB" /><a
 * href="https://en.wikipedia.org/wiki/Vojt%C4%9Bch_Jarn%C3%ADk"
 * >Vojt&#283;ch Jarn&#237;k</a>: <span style="font-weight:bold">&ldquo;O
 * Jist&#233;m Probl&#233;mu Minim&#225;ln&#237;m: (Z Dopisu Panu O.
 * Bor&#367;skovi),&rdquo;</span> in <span
 * style="font-style:italic;font-family:cursive;">Pr&#225;ce Moravsk&#233;
 * P&#345;&#237;rodov&#283;deck&#233; Spole&#269;nosti: Acta Societatis
 * Scientiarum Naturalium Moravia</span> 6:57&ndash;63, 1930; published by
 * Brno, Czechoslovakia: Moravsk&#225; P&#345;&#237;rodov&#277;deck&#225;
 * Spolec&#780;&#328;ost. OCLC:&nbsp;<a
 * href="https://www.worldcat.org/oclc/762243334">762243334</a>; Google
 * Book ID:&nbsp;<a
 * href="http://books.google.com/books?id=GOc3HAAACAAJ">GOc3HAAACAAJ</a>
 * and&nbsp;<a
 * href="http://books.google.com/books?id=kCMpAQAAMAAJ">kCMpAQAAMAAJ</a>;
 * ISSN:&nbsp;<a
 * href="https://www.worldcat.org/issn/12110248">1211-0248</a></div></li>
 * <li><div><span id="cite_P1957SCNASG" /><a
 * href="https://en.wikipedia.org/wiki/Robert_C._Prim">Robert Clay
 * Prim</a>: <span style="font-weight:bold">&ldquo;Shortest Connection
 * Networks and Some Generalizations,&rdquo;</span> in <span
 * style="font-style:italic;font-family:cursive;">Bell System Technical
 * Journal</span> 36(6):1389&ndash;1401, November&nbsp;1957; published by
 * Berkeley Heights, NJ, USA: Bell Laboratories. ASIN:&nbsp;<a
 * href="http://www.amazon.com/dp/B004VJC9KA">B004VJC9KA</a>. <div>link:
 * [<a href
 * ="www.alcatel-lucent.com/bstj/vol36-1957/articles/bstj36-6-1389.pdf"
 * >1</a> ]</div></div></li>
 * <li><div><span id="cite_J2004MSTSPT" /><a
 * href="http://www.me.utexas.edu/~orie/Jensen.html">Paul A. Jensen</a>:
 * <span style="font-style:italic;font-family:cursive;">&ldquo;Minimal
 * Spanning Tree/Shortest Path Tree,&rdquo;</span> (Website),
 * 2004&ndash;September&nbsp;11, 2010, Austin, TX, USA: University of
 * Texas, Mechanical Engineering Department. <div>link: [<a href=
 * "http://www.me.utexas.edu/~jensen/ORMM/methods/unit/network/subunits/mst_spt/index.html"
 * >1</a>]</div></div></li>
 * <li><div><span id="cite_CW1964SOVFACDTANODP" />G. Clarke and&nbsp;J. W.
 * Wright: <span style="font-weight:bold">&ldquo;Scheduling of Vehicles
 * from a Central Depot to a Number of Delivery Points,&rdquo;</span> in
 * <span style="font-style:italic;font-family:cursive;">Operations Research
 * (Oper. Res.)</span> 12(4):568&ndash;581, July&ndash;August 1964;
 * published by Linthicum, ML, USA: Institute for Operations Research and
 * the Management Sciences (INFORMS) and&nbsp;Cambridge, MA, USA: HighWire
 * Press (Stanford University). LCCN:&nbsp;<a
 * href="http://lccn.loc.gov/66099702">66099702</a>; doi:&nbsp;<a
 * href="http://dx.doi.org/10.1287/opre.12.4.568"
 * >10.1287/opre.12.4.568</a>; JSTOR stable:&nbsp;<a
 * href="http://www.jstor.org/stable/167703">167703</a>; OCLC:&nbsp;<a
 * href="https://www.worldcat.org/oclc/2394608">2394608</a>; ISSN:&nbsp;<a
 * href="https://www.worldcat.org/issn/0030364X">0030-364X</a> and&nbsp;<a
 * href="https://www.worldcat.org/issn/15265463">1526-5463</a>;
 * CODEN:&nbsp;<a href=
 * "http://www-library.desy.de/cgi-bin/spiface/find/coden/www?code=OPREAI"
 * >OPREAI</a>. <div>link: [<a href=
 * "http://read.pudn.com/downloads160/doc/fileformat/721736/Scheduling%20of%20vehicles%20from%20a%20central%20depot%20to%20a%20number%20of%20delivery%20points.pdf"
 * >1</a>]</div></div></li>
 * </ol>
 */
public class TSPHeuristic extends TSPAlgorithm {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * instantiate the algorithm class
   *
   * @param name
   *          the name
   */
  protected TSPHeuristic(final String name) {
    super(name);
  }

  /**
   * Solve the TSP problem defined by the given
   * {@link org.logisticPlanning.tsp.benchmarking.objective.ObjectiveFunction
   * objective function} {@code f} and store the (best) result along with
   * its quality (if the quality was computed) in the individual
   * {@code dest}.
   *
   * @param f
   *          the objective function
   * @param dest
   *          the destination individual record, or {@code null} if no
   *          output is necessary
   */
  public void solve(final ObjectiveFunction f, final Individual<int[]> dest) {
    dest.clear();
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  @Override
  public final void solve(final ObjectiveFunction f) {
    this.solve(f, null);
  }

}
