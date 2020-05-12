package b2p.search.aima;

import b2p.model.IAction;
import b2p.model.IState;
import b2p.state.bitboard.bitset.BitSetAction;
import b2p.state.bitboard.bitset.BitSetState;

public interface IAdversarialSearch {

    /**
     * Returns the action which appears to be the best at the given state.
     */
    IAction makeDecision(IState state);

    /**
     * Returns the metrics of the adversarial search.
     */
    Metrics getMetrics();
}