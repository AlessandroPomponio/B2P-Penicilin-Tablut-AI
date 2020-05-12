package b2p.search.aima;

import java.util.ArrayList;
import java.util.List;

public class ActionStore<ACTION> {
    private List<ACTION> actions;
    private List<Integer> utilValues;

    public ActionStore(int capacity) {
        actions = new ArrayList<>(capacity);
        utilValues = new ArrayList<>(capacity);
    }

    public synchronized void add(ACTION action, int utilValue) {
        int idx;
        for (idx = 0; idx < actions.size() && utilValue <= utilValues.get(idx); idx++)
            ;                   // da completare
        actions.add(idx, action);
        utilValues.add(idx, utilValue);
    }

    public int size() {
        return actions.size();
    }

    public List<ACTION> getActions() {
        return actions;
    }

    public List<Integer> getUtilValues() {
        return utilValues;
    }

}
