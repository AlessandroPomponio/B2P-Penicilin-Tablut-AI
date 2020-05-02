package b2p.state.bitboard.bitset.aima;

import aima.core.agent.Action;
import aima.core.search.framework.problem.StepCostFunction;

public class BitSetStepCostFunction implements StepCostFunction {

    @Override
    public double c(Object o, Action action, Object o1) {
        return 1.0;
    }
}
