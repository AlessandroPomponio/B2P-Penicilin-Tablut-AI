package b2p.state.bitboard.bitset.aima.adversarial;

import b2p.state.bitboard.bitset.BitSetAction;
import b2p.state.bitboard.bitset.BitSetState;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

import java.util.List;

public class TablutGameIterativeDeepeningAlphaBetaSearch implements IAdversarialSearch {

    private final TablutGame game;
    private final int utilMin;
    private final int utilMax;

    private final TablutGameIterativeDeepeningAlphaBetaSearch.Timer timer;

    private int currDepthLimit;
    private Metrics metrics;
/*
 *  TODO:
 *   - finire di scrivere le funzioni finali di questa classe
 *   - finire di ottimizzare tutte le funzioni di questa classe
 *   - strip makeDecision di tutte le funzioni non necessarie che possono rallentare l'esecuzione (releative ad esempio
 *     a metrics)
 *   - Modificare la classe ActionStore per fare in modo che ordini le azioni solo quando viene aggiunto un nuovo elemento
 *     E, anzi, sarebbe meglio se il metodo venisse chiamato esclusivamente quando vengono inserite nuove azioni con le
 *     rispettive valutazioni euristiche. Inoltre, si potrebbe pensare di creare una classe apposta che sfrutti la class
 *     classe Thread per fare in modo che le azioni vengano ordinate in un processo diverso da quello corrente.
 */

/*
 * DONE:
 *   - Rimosse le variabili inerenti al log e i relativi controlli.
 *   - Fixed typo in Timer.timeOutOccured -> Timer.timeOutOccurred.
 *   - Aggiunto argomento depth nella funzione eval, aggiunto controllo di profondità nella funzione depth.
 *   - Creato interfacce AdversarialSearch e IGame (in cui è stato cambiato il valore dell'euristica da double a int)
 *   - implementare correttamente questa classe nel client (e cambiare il timeout)
 *   - valutare se è necessario sostituire il risultato dell'euristica da double a Integer (per questioni di efficienza)
 *     nel caso è necessario cambiare anche l'interfaccia Game e la classe TablutGame
 *   - capire come viene impiegata la variabile booleana heuristicEvaluationUsed perchè non ha molto senso per come è
 *     stata scritta nel codice originale
 */
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
    public TablutGameIterativeDeepeningAlphaBetaSearch(TablutGame game, int utilMin, int utilMax, int time) {
        this.game = game;
        this.utilMin = utilMin;
        this.utilMax = utilMax;
        this.timer = new TablutGameIterativeDeepeningAlphaBetaSearch.Timer(time);
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
        metrics = new Metrics();
        List<BitSetAction> availableActions = orderActions(state, game.getActions(state), player, 0);

        //
        timer.start();
        currDepthLimit = 0;

        //
        ActionStore<BitSetAction> heuristicsResults = null;

        do {

            currDepthLimit++;
            System.out.println("DEPTH LIMIT:" + currDepthLimit);
            heuristicsResults = new ActionStore<>(availableActions.size());

            for (BitSetAction action : availableActions) {

                // minValue calls recursively maxValue and minValue
                // in order to perform Alpha/Beta Min/Max search.
                int heuristicValue = minValue(game.getResult(state, action), player, utilMin, utilMax, 1);

                // Make sure we don't overshoot
                if (timer.timeOutOccurred()) {
                    System.out.println("TIMEOUT");
                    break;
                }

                heuristicsResults.add(action, heuristicValue);

            }

            if (heuristicsResults.size() > 0) {

                availableActions = heuristicsResults.actions;

/*              // Codice potenzialmente inutile per il nostro gioco
                // If we have a safe winning value, we can stop the search
                if (hasSafeWinner(heuristicsResults.utilValues.get(0))) {
                    break;
                } else if (heuristicsResults.size() > 1) {
                    if (isSignificantlyBetter(heuristicsResults.utilValues.get(0), heuristicsResults.utilValues.get(1))) {
                        break;
                    }

                }
*/
            }


        } while(!timer.timeOutOccurred());

        return availableActions.get(0);

    }

    // returns an utility value
    public int maxValue(BitSetState state, Turn player, int alpha, int beta, int depth) {

        updateMetrics(depth);
        if (game.isTerminal(state) || depth >= currDepthLimit || timer.timeOutOccurred())
            return eval(state, player, depth);

        // The worst case for the maximizer is the minimum value
        int value = utilMin;

        // TODO: valutare come gestire l'ordinamento delle azioni
        for (BitSetAction action : orderActions(state, game.getActions(state), player, depth)) {

            // Min/Max depth-first
            int minimizerValue = minValue(game.getResult(state, action), player, alpha, beta, depth + 1);
            value = Math.max(value, minimizerValue);

            // Alpha/Beta pruning
            if (value >= beta)
                return value;

            // Update alpha value for maximizer
            alpha = Math.max(alpha, value);

        }

        return value;

    }

    // returns an utility value
    public int minValue(BitSetState state, Turn player, int alpha, int beta, int depth) {

        updateMetrics(depth);
        if (game.isTerminal(state) || depth >= currDepthLimit || timer.timeOutOccurred())
            return eval(state, player, depth);

        // The worst case scenario for the minimizer is Maxvalue
        int value = utilMax;

        // TODO: valutare come gestire l'ordinamento delle azioni
        for (BitSetAction action : orderActions(state, game.getActions(state), player, depth)) {

            // Min/Max depth-first
            int maximizerValue = maxValue(game.getResult(state, action), player, alpha, beta, depth + 1);
            value = Math.min(value, maximizerValue);

            // Alpha/Beta pruning
            if (value <= alpha)
                return value;

            // Update beta value for minimizer
            beta = Math.min(beta, value);

        }

        return value;

    }

    private void updateMetrics(int depth) {
        metrics.updateMetrics(depth);
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
/*
    protected boolean isSignificantlyBetter(double newUtility, double utility) {
        return false;
    }
*/
    /**
     * Primitive operation which is used to stop iterative deepening search in
     * situations where a safe winner has been identified. This implementation
     * returns true if the given value (for the currently preferred action
     * result) is the highest or lowest utility value possible.
     */
/*
    protected boolean hasSafeWinner(double resultUtility) {
        return resultUtility <= utilMin || resultUtility >= utilMax;
    }
*/
    /**
     * Primitive operation, which estimates the value for (not necessarily
     * terminal) states. This implementation returns the utility value for
     * terminal states and <code>(utilMin + utilMax) / 2</code> for non-terminal
     * states. When overriding, first call the super implementation! stocazzo
     */
    protected int eval(BitSetState state, Turn player, int depth) {

        if ((game.isTerminal(state) || depth >= currDepthLimit) && !timer.timeOutOccurred()) {
            return game.getUtility(state, player);
        }

        /*
         * Quando scatta il timeout, il nodo che stiamo analizzando non deve essere considerato nella scelta
         */
        if ((depth & 1) == 0)                // se è pari, equivalente a ((depth % 2) == 0), profondità per massimizzante
            return Integer.MIN_VALUE;
        else                                // se è dispari, equivalente a ((depth % 2) == 1, profondità per minimizzante
            return Integer.MAX_VALUE;
        // faceva (utilMin + utilMax) / 2 = -0.5 usando dei double

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