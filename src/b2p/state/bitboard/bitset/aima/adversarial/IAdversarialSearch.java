package b2p.state.bitboard.bitset.aima.adversarial;

import b2p.state.bitboard.bitset.BitSetAction;
import b2p.state.bitboard.bitset.BitSetState;

/**
 * Variant of the search interface. Since players can only control the next
 * move, method <code>makeDecision</code> returns only one action, not a
 * sequence of actions.
 *
 * @param <S> The type used to represent states
 * @param <A> The type of the actions to be used to navigate through the state space
 * @author Ruediger Lunde
 */
public interface IAdversarialSearch {

    /**
     * Returns the action which appears to be the best at the given state.
     */
    BitSetAction makeDecision(BitSetState state);
}