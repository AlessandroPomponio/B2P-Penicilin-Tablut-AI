package b2p.search.aima.minmax;

import b2p.state.bitboard.bitset.BitSetAction;
import b2p.state.bitboard.bitset.BitSetState;
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

public class IterativeDeepening implements IAdversarialSearch {

    //
    private final TablutGame game;

    //
    private final int utilMin;
    private final int utilMax;

    private final IterativeDeepening.Timer timer;

    private int currDepthLimit;
    private Metrics metrics;

    private Turn ourPlayer;

    //
    private ExecutorService executor;

    /**
     * Creates a new search object for a given game.
     *
     * @param game    The game.
     * @param utilMin Utility value of worst state for this player. Supports
     *                evaluation of non-terminal states and early termination in
     *                situations with a safe winner.
     * @param utilMax Utility value of best state for this player. Supports
     *                evaluation of non-terminal states and early termination in
     *                situations with a safe winner.
     * @param time    Maximum Duration of the search algorithm
     */
    public IterativeDeepening(TablutGame game, int utilMin, int utilMax, int time, Turn ourPlayer) {

        this.game = game;
        this.utilMin = utilMin;
        this.utilMax = utilMax;
        this.timer = new IterativeDeepening.Timer(time);
        this.ourPlayer = ourPlayer;

        //
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    }

    public void setGameState(BitSetState state) {
        game.setState(state);
    }

    /**
     * Template method controlling the search. It is based on iterative
     * deepening and tries to make to a good decision in limited time. Credit
     * goes to Behi Monsio who had the idea of ordering actions by utility in
     * subsequent depth-limited search runs.
     */
    @Override
    public BitSetAction makeDecision(BitSetState state) {

        //
        Turn player = game.getPlayer(state);
        System.out.println("CURRENT PLAYER: " + game.getPlayer(state));
        metrics = new Metrics();
        List<BitSetAction> availableActions = orderActions(state, game.getActions(state), player, 0);

        //
        timer.start();
        currDepthLimit = 0;

        //
        ActionStore<BitSetAction> heuristicsResults;

        do {

            currDepthLimit++;
            System.out.println("DEPTH LIMIT:" + currDepthLimit);
            heuristicsResults = new ActionStore<>(availableActions.size());
            ArrayList<Future<Integer>> futureResults = new ArrayList<Future<Integer>>();

            for (BitSetAction action : availableActions) {

                futureResults.add(executor.submit(new MinMaxAlphaBetaSearch(this, state, action, player)));

            }

            for (int i = 0; i < availableActions.size(); i++) {

                try {

                    BitSetAction action = availableActions.get(i);
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

            for (int i = 0; i < availableActions.size(); i++) {

                if (!futureResults.get(i).isDone()) {
                    futureResults.get(i).cancel(true);
                }

            }

            if (heuristicsResults.size() > 0) {
                availableActions = heuristicsResults.getActions();
            }


        } while (!timer.timeOutOccurred());

        return availableActions.get(0);

    }

    public int getUtilMin() {
        return utilMin;
    }

    public int getUtilMax() {
        return utilMax;
    }

    public Timer getTimer() {
        return timer;
    }

    public int getCurrDepthLimit() {
        return currDepthLimit;
    }

    public Turn getOurPlayer() {
        return ourPlayer;
    }

    @Override
    public Metrics getMetrics() {
        return metrics;
    }

    /**
     * Primitive operation which is used to stop iterative deepening search in
     * situations where a clear best action exists. This implementation returns
     * always false.
     */

    protected boolean isSignificantlyBetter(int newUtility, int utility) {
        return newUtility / utility > 10;
    }

    /**
     * Primitive operation which is used to stop iterative deepening search in
     * situations where a safe winner has been identified. This implementation
     * returns true if the given value (for the currently preferred action
     * result) is the highest or lowest utility value possible.
     */

    protected boolean hasSafeWinner(int resultUtility) {
        return resultUtility <= utilMin || resultUtility >= utilMax;
    }

    /**
     * Primitive operation for action ordering. This implementation preserves
     * the original order (provided by the game).
     */
    public List<BitSetAction> orderActions(BitSetState state, List<BitSetAction> actions, Turn player, int depth) {
        return actions;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // nested helper classes
    ///////////////////////////////////////////////////////////////////////////////////////////

    protected static class Timer {
        private final long duration;
        private long startTime;

        Timer(int maxSeconds) {
            this.duration = 1000L * maxSeconds;
        }

        void start() {
            startTime = System.currentTimeMillis();
        }

        boolean timeOutOccurred() {
            return System.currentTimeMillis() > startTime + duration;
        }
    }

}
