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

public class IterativeDeepening implements IAdversarialSearch {

    //
    private final TablutGame game;

    //
    private final int utilMin;
    private final int utilMax;

    private final IterativeDeepening.Timer timer;

    private int currDepthLimit;
    private Metrics metrics;

    //
    private final ExecutorService executor;

    /**
     * Creates a new search object for a given game.
     *  @param game    The game.
     * @param utilMin Utility value of worst state for this player. Supports
     *                evaluation of non-terminal states and early termination in
     *                situations with a safe winner.
     * @param utilMax Utility value of best state for this player. Supports
 *                evaluation of non-terminal states and early termination in
 *                situations with a safe winner.
     * @param time    Maximum Duration of the search algorithm
     */
    public IterativeDeepening(TablutGame game, int utilMin, int utilMax, int time) {

        this.game = game;
        this.utilMin = utilMin;
        this.utilMax = utilMax;
        this.timer = new IterativeDeepening.Timer(time);

        //
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    }

    public void setGameState(IState state) {
        game.setState(state);
    }

    /**
     * Template method controlling the search. It is based on iterative
     * deepening and tries to make to a good decision in limited time. Credit
     * goes to Behi Monsio who had the idea of ordering actions by utility in
     * subsequent depth-limited search runs.
     */
    @Override
    public IAction makeDecision(IState state) {

        //
        Turn player = game.getPlayer(state);
        System.out.println("CURRENT PLAYER: " + game.getPlayer(state));
        metrics = new Metrics();
        List<IAction> availableActions = game.getActions(state);

        //
        timer.start();
        currDepthLimit = 0;

        //
        ActionStore<IAction> heuristicsResults;

        do {

            currDepthLimit++;
            System.out.println("DEPTH LIMIT:" + currDepthLimit);
            heuristicsResults = new ActionStore<>(availableActions.size());
            ArrayList<Future<Integer>> futureResults = new ArrayList<>();

            for (IAction action : availableActions) {

                futureResults.add(executor.submit(new MinMaxAlphaBetaSearch(this, state, action, player)));

            }

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

    @Override
    public Metrics getMetrics() {
        return metrics;
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
