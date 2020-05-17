package b2p.search.aima;

import b2p.model.IAction;
import b2p.model.IState;

/**
 * This interface defines the functions needed to perform an Adversarial Search
 *
 * @author Alessandro Buldini
 * @author Alessandro Pomponio
 * @author Federico Zanini
 * @see <a href="https://github.com/aimacode/aima-java">Aima Java</a>
 */
public interface IAdversarialSearch {

    /**
     * Returns the action which appears to be the best at the given state
     *
     * @param state current state of the game, based on which the decision should be made
     * @return the best action to perform for the given heuristic function
     */
    IAction makeDecision(IState state);

    /**
     * Returns the metrics of the adversarial search
     *
     * @return the metrics of the adversarial search
     */
    Metrics getMetrics();

}