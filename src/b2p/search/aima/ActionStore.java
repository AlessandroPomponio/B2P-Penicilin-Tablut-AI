package b2p.search.aima;

import b2p.model.IAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to implement an ordered collection of {@link IAction}
 *
 * @see <a href="https://github.com/aimacode/aima-java">Aima Java</a>
 *
 * @author Alessandro Buldini
 * @author Alessandro Pomponio
 * @author Federico Zanini
 */
public class ActionStore {
    /**
     * List of actions stored
     */
    private final List<IAction> actions;
    /**
     * List of values stored
     */
    private final List<Integer> utilValues;

    /**
     * Creates an object ActionStore that can hold a fixed amount of actions and their respective heuristic values
     * @param capacity capacity of both {@code actions} and {@code utilValues} lists
     */
    public ActionStore(int capacity) {
        actions = new ArrayList<>(capacity);
        utilValues = new ArrayList<>(capacity);
    }

    /**
     * Sorts and stores within the lists both the input action and the input heuristic value
     *
     * @param action action to be stored within the class
     * @param utilValue action's associated value
     */
    public synchronized void add(IAction action, int utilValue) {
        int idx;
        for (idx = 0; idx < actions.size() && utilValue <= utilValues.get(idx); idx++)
            ;
        actions.add(idx, action);
        utilValues.add(idx, utilValue);
    }

    /**
     * Returns the size of {@code action} list
     * @return the size of {@code action} list
     */
    public int size() {
        return actions.size();
    }

    /**
     * Returns the list containing the actions stored
     * @return the list containing the actions stored
     */
    public List<IAction> getActions() {
        return actions;
    }

}
