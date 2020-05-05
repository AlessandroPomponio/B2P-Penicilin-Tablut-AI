package b2p.state.bitboard.bitset.aima.adversarial;

import b2p.state.bitboard.bitset.BitSetAction;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class ActionStore<ACTION> {

//    private final PriorityQueue<BitSetAction> actions;
//
//    // TODO: CONTROLLARE CHE IL COMPARATORE DI IACTION
//    // FACCIA IL SUO DOVERE
//    public ActionStore(int capacity) {
//        actions = new PriorityQueue<>(capacity);
//    }
//
//    public void add(BitSetAction action, int value) {
//        action.setValue(value);
//        actions.add(action);
//    }
//
//    public BitSetAction peekFirstAction() {
//        return actions.peek();
//    }
//
//    public BitSetAction takeFirstAction() {
//        return actions.poll();
//    }
//
//    public int size() {
//        return actions.size();
//    }
//
//    @Override
//    public String toString() {
//        return "ActionStore{" +
//                "actions=" + actions.toString() +
//                '}';
//    }

    protected List<ACTION> actions;
    protected List<Integer> utilValues;

    public ActionStore(int capacity) {
        actions  = new ArrayList<>(capacity);
        utilValues  = new ArrayList<>(capacity);
    }

    void add(ACTION action, int utilValue) {
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
