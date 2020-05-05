package b2p.state.bitboard.bitset.aima.adversarial;

import b2p.state.bitboard.bitset.BitSetAction;
import b2p.state.bitboard.bitset.BitSetState;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

import java.util.ArrayList;
import java.util.List;

public class TablutGameIterativeDeepeningAlphaBetaSearch implements IAdversarialSearch {

    private final TablutGame game;
    private final int utilMin;
    private final int utilMax;

    private TablutGameIterativeDeepeningAlphaBetaSearch.Timer timer;

    private int currDepthLimit;
/*
 *  TODO:
 *   - finire di scrivere le funzioni finali di questa classe
 *   - finire di ottimizzare tutte le funzioni di questa classe
 *   - implementare correttamente questa classe nel client (e cambiare il timeout)
 *   - strip makeDecision di tutte le funzioni non necessarie che possono rallentare l'esecuzione (releative ad esempio
 *     a metrics)
 *   - valutare se è necessario sostituire il risultato dell'euristica da double a Integer (per questioni di efficienza)
 *     nel caso è necessario cambiare anche l'interfaccia Game e la classe TablutGame
 *   - capire come viene impiegata la variabile booleana heuristicEvaluationUsed perchè non ha molto senso per come è
 *     stata scritta nel codice originale
 *   - Modificare la classe orderAction per fare in modo che ordini le azioni solo quando viene aggiunto un nuovo elemento
 *     E, anzi, sarebbe meglio se il metodo venisse chiamato esclusivamente quando vengono inserite nuove azioni con le
 *     rispettive valutazioni euristiche. Inoltre, si potrebbe pensare di creare una classe apposta che sfrutti la class
 *     classe Thread per fare in modo che le azioni vengano ordinate in un processo diverso da quello corrente.
 *   - Considerare se è meglio settare currDepthLimit a un valore diverso di 2
 */

/*
 * DONE:
 *   - Rimosse le variabili inerenti al log e i relativi controlli.
 *   - Fixed typo in Timer.timeOutOccured -> Timer.timeOutOccurred.
 *   - Aggiunto argomento depth nella funzione eval, aggiunto controllo di profondità nella funzione depth.
 *   - Creato interfacce AdversarialSearch e IGame (in cui è stato cambiato il valore dell'euristica da double a int)
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
        Turn player = game.getPlayer(state);
        List<BitSetAction> results = orderActions(state, game.getActions(state), player, 0);
        timer.start();
        currDepthLimit = 0;

        do {
            currDepthLimit++;
            ActionStore<BitSetAction> newResults = new ActionStore<BitSetAction>();
            for (BitSetAction action : results) {
                int value = minValue(game.getResult(state, action), player, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
                if (timer.timeOutOccurred())
                    break; // exit from action loop
                newResults.add(action, value);
            }
            if (newResults.size() > 0) {
                results = newResults.actions;
                if (!timer.timeOutOccurred()) {
                    if (hasSafeWinner(newResults.utilValues.get(0)))
                        break; // exit from iterative deepening loop
                    else if (newResults.size() > 1
                            && isSignificantlyBetter(newResults.utilValues.get(0), newResults.utilValues.get(1)))
                        break; // exit from iterative deepening loop
                }
            }
        } while (!timer.timeOutOccurred());
        return results.get(0);
    }

    // returns an utility value
    public int maxValue(BitSetState state, Turn player, double alpha, double beta, int depth) {
        if (game.isTerminal(state) || depth >= currDepthLimit || timer.timeOutOccurred())
            return eval(state, player, depth);

        int value = Integer.MIN_VALUE;
        // valutare come gestire l'ordinamento delle azioni
        for (BitSetAction action : orderActions(state, game.getActions(state), player, depth)) {
            value = Math.max(value, minValue(game.getResult(state, action), //
                    player, alpha, beta, depth + 1));
            if (value >= beta)
                return value;

            alpha = Math.max(alpha, value);
        }
        return value;

    }

    // returns an utility value
    public int minValue(BitSetState state, Turn player, double alpha, double beta, int depth) {
        if (game.isTerminal(state) || depth >= currDepthLimit || timer.timeOutOccurred())
            return eval(state, player, depth);

        int value = Integer.MAX_VALUE;
        // valutare come gestire l'ordinamento delle azioni
        for (BitSetAction action : orderActions(state, game.getActions(state), player, depth)) {
            value = Math.min(value, maxValue(game.getResult(state, action), //
                player, alpha, beta, depth + 1));
            if (value <= alpha)
                return value;

            beta = Math.min(beta, value);
        }
        return value;

    }

    /**
     * Primitive operation which is used to stop iterative deepening search in
     * situations where a clear best action exists. This implementation returns
     * always false.
     */
    protected boolean isSignificantlyBetter(double newUtility, double utility) {
        return false;
    }

    /**
     * Primitive operation which is used to stop iterative deepening search in
     * situations where a safe winner has been identified. This implementation
     * returns true if the given value (for the currently preferred action
     * result) is the highest or lowest utility value possible.
     */
    protected boolean hasSafeWinner(double resultUtility) {
        return resultUtility <= utilMin || resultUtility >= utilMax;
    }

    /**
     * Primitive operation, which estimates the value for (not necessarily
     * terminal) states. This implementation returns the utility value for
     * terminal states and <code>(utilMin + utilMax) / 2</code> for non-terminal
     * states. When overriding, first call the super implementation! stocazzo
     */
    protected int eval(BitSetState state, Turn player, int depth) {

        if ((game.isTerminal(state) || depth >= currDepthLimit) && !timer.timeOutOccurred()) {
            return game.getUtility(state, player);
        } else {
            /*
             * Quando scatta il timeout, il nodo che stiamo analizzando non deve essere considerato nella scelta
             */
            if((depth & 1) == 0)                // se è pari, equivalente a ((depth % 2) == 0), profondità per massimizzante
                return Integer.MIN_VALUE;
            else                                // se è dispari, equivalente a ((depth % 2) == 1, profondità per massimizzante
                return Integer.MAX_VALUE;
                                                // faceva (utilMin + utilMax) / 2 = -0.5 usando dei double
        }
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
        private long duration;
        private long startTime;

        Timer(int maxSeconds) {
            this.duration = 1000l * maxSeconds;
        }

        void start() {
            startTime = System.currentTimeMillis();
        }

        boolean timeOutOccurred() {
            return System.currentTimeMillis() > startTime + duration;
        }
    }

    /** Orders actions by utility. */
    protected static class ActionStore<ACTION> {

        // fare hashTable?
        private List<ACTION> actions = new ArrayList<ACTION>();
        private List<Double> utilValues = new ArrayList<Double>();

        void add(ACTION action, double utilValue) {
            int idx;
            for (idx = 0; idx < actions.size() && utilValue <= utilValues.get(idx); idx++)
                ;                   // da completare
            actions.add(idx, action);
            utilValues.add(idx, utilValue);
        }

        int size() {
            return actions.size();
        }
    }
}
