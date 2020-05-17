package b2p.search.aima.minmax;

import b2p.model.IAction;
import b2p.model.IState;
import b2p.search.aima.ActionStore;
import b2p.search.aima.IAdversarialSearch;
import b2p.search.aima.Metrics;
import b2p.search.aima.TablutGame;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * This class implements {@link IAdversarialSearch} and defines the
 * Iterative Deepening Minimax algorithm with alpha-beta pruning.
 *
 * @author Alessandro Buldini
 * @author Alessandro Pomponio
 * @author Federico Zanini
 * @see <a href="https://github.com/aimacode/aima-java">AIMA Java</a>
 */
public class IterativeDeepening implements IAdversarialSearch {

    /**
     * Instance of the current game
     */
    private final TablutGame game;

    /**
     * Executes a set of given asynchronous tasks
     */
    private final ExecutorService executor;

    /**
     * Minimum value that the heuristic value can reach
     */
    private final int utilMin;

    /**
     * Maximum value that the heuristic value can reach
     */
    private final int utilMax;

    /**
     * Timer needed to stop the search algorithm after a given timeout
     */
    private final IterativeDeepening.Timer timer;

    /**
     * Current depth limit for the Iterative Deepening Minimax algorithm
     */
    private int currDepthLimit;

    /**
     * Metrics including all the parameters needed to evaluate the search performance
     */
    private Metrics metrics;

    /**
     * Creates an {@link IterativeDeepening} instance for a {@link TablutGame} with
     * a given timeout and utility values.
     *
     * @param game    instance of the TablutGame class
     * @param utilMin minimum value the heuristic function can reach
     * @param utilMax maximum value the heuristic function can reach
     * @param time    timeout for the search strategy
     */
    public IterativeDeepening(TablutGame game, int utilMin, int utilMax, int time) {

        //
        this.game = game;
        this.utilMin = utilMin;
        this.utilMax = utilMax;
        this.timer = new IterativeDeepening.Timer(time);

        // Use the amount of available processors (it includes hyperthreading-like technologies
        // in order not to have too many threads going around at the same time.
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    }

    /**
     * Returns the action which appears to be the best at the given state
     *
     * @param state state of the game on which the decision should be made
     * @return the best action to perform for the input state
     * @see IState
     */
    @Override
    public IAction makeDecision(IState state) {

        //
        System.out.println("Beginning search for player " + game.getPlayer(state));

        //
        Turn player = game.getPlayer(state);
        List<IAction> availableActions = game.getActions(state);
        ArrayList<Future<Integer>> futureResults;
        ActionStore heuristicsResults;
        metrics = new Metrics();

        //
        currDepthLimit = 0;
        timer.start();

        do {

            currDepthLimit++;
            System.out.println("Currently exploring up to depth: " + currDepthLimit);
            heuristicsResults = new ActionStore(availableActions.size());
            futureResults = new ArrayList<>(availableActions.size());

            // Start simulating all the possible actions
            for (IAction action : availableActions) {
                futureResults.add(executor.submit(new MinMaxAlphaBetaSearch(this, state, action, player)));
            }

            // Get results until we time out
            for (int i = 0; i < availableActions.size(); i++) {

                try {

                    IAction action = availableActions.get(i);
                    int heuristicValue = futureResults.get(i).get();

                    action.setValue(heuristicValue);
                    heuristicsResults.add(action, heuristicValue);

                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                if (timer.timeOutOccurred()) {
                    System.out.println("TIMEOUT");
                    break;
                }

            }

            // The rules state we can't perform computation during the opponent's turn
            // Cancel the leftover futures.
            for (int i = 0; i < availableActions.size(); i++) {

                if (!futureResults.get(i).isDone()) {
                    futureResults.get(i).cancel(true);
                }

            }

            // Prepare for the next round of iterative deepening
            if (heuristicsResults.size() > 0) {

                //
                availableActions = heuristicsResults.getActions();

                // Print best action for current level
                System.out.println("The best option at this depth seems to be: " + availableActions.get(0));

            }

        } while (!timer.timeOutOccurred());

        System.out.println("\nExplored a total of " + metrics.getNodesExpanded() + " nodes, reaching a depth limit of " + metrics.getCurrDepthLimit());
        return availableActions.get(0);

    }

    /**
     * Sets the game state to the value passed as input
     *
     * @param state state to be set within the {@link IterativeDeepening} class
     * @see IState
     */
    public void setGameState(IState state) {
        game.setState(state);
    }

    /**
     * Returns the minimum value the heuristic function can reach
     *
     * @return the minimum value the heuristic function can reach
     */
    public int getUtilMin() {
        return utilMin;
    }

    /**
     * Returns the maximum value the heuristic function can reach
     *
     * @return the maximum value the heuristic function can reach
     */
    public int getUtilMax() {
        return utilMax;
    }

    /**
     * Returns the timer in the current {@link IterativeDeepening} instance
     *
     * @return the timer in the current {@link IterativeDeepening} instance
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * Returns the current depth limit reached by the search
     *
     * @return the current depth limit reached by the search
     */
    public int getCurrDepthLimit() {
        return currDepthLimit;
    }

    /**
     * Returns the metrics object set within the class
     *
     * @return the metrics object set within the class
     */
    @Override
    public Metrics getMetrics() {
        return metrics;
    }

    /**
     * Given a depth value, updates the metrics object set within the class
     *
     * @param depth depth reached at the current state of the search
     */
    public synchronized void updateMetrics(int depth) {
        metrics.updateMetrics(depth);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // nested helper classes
    ///////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Utility class representing an in-game timer
     *
     * @author Alessandro Buldini
     * @author Alessandro Pomponio
     * @author Federico Zanini
     * @see <a href="https://github.com/aimacode/aima-java">AIMA Java</a>
     */
    protected static class Timer {

        /**
         * Duration of the timer
         */
        private final long duration;

        /**
         * Start time for the timer
         */
        private long startTime;

        /**
         * Creates a timer with a timeout of {@code maxSeconds}
         *
         * @param maxSeconds timeout for the timer
         */
        Timer(int maxSeconds) {
            this.duration = 1000L * maxSeconds;
        }

        /**
         * Starts the timer
         */
        void start() {
            startTime = System.currentTimeMillis();
        }

        /**
         * Returns {@code true} if the timeout has occurred
         *
         * @return {@code true} if the timeout has occurred
         */
        boolean timeOutOccurred() {
            return System.currentTimeMillis() > startTime + duration;
        }

    }

}
