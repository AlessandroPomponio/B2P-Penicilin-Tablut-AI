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
    private final ExecutorService executor;

    //
    private final int utilMin;
    private final int utilMax;

    private final IterativeDeepening.Timer timer;

    private int currDepthLimit;
    private Metrics metrics;

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

    @Override
    public IAction makeDecision(IState state) {

        //
        System.out.println("Beginning search for player " + game.getPlayer(state));

        //
        Turn player = game.getPlayer(state);
        List<IAction> availableActions = game.getActions(state);
        ArrayList<Future<Integer>> futureResults;
        ActionStore<IAction> heuristicsResults;
        metrics = new Metrics();

        //
        currDepthLimit = 0;
        timer.start();

        do {

            currDepthLimit++;
            System.out.println("Currently exploring up to depth: " + currDepthLimit);
            heuristicsResults = new ActionStore<>(availableActions.size());
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

        System.out.println("\nExplored a total of " + metrics.getNodeExpanded() + " nodes, reaching a depth limit of " + metrics.getCurrDepthLimit());
        return availableActions.get(0);

    }

    public void setGameState(IState state) {
        game.setState(state);
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

    public synchronized void updateMetrics(int depth) {
        metrics.updateMetrics(depth);
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
