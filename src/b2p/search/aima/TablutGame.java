package b2p.search.aima;

import b2p.model.IAction;
import b2p.model.IState;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

import java.util.List;

public class TablutGame implements IGame<IState, IAction, Turn> {

    private IState state;

    public TablutGame(IState state) {
        this.state = state;
    }

    public void setState(IState state) {
        this.state = state;
    }

    @Override
    public Turn getPlayer(IState bitSetState) {
        return bitSetState.getTurn();
    }

    @Override
    public List<IAction> getActions(IState bitSetState) {
        return bitSetState.getAvailablePawnMoves();
    }

    @Override
    public IState getResult(IState bitSetState, IAction bitSetAction) {
        IState result = bitSetState.clone();
        result.performMove(bitSetAction);
        return result;
    }

    @Override
    public boolean isTerminal(IState bitSetState) {
        return bitSetState.isWinningState();
    }

    @Override
    public int getUtility(IState bitSetState, Turn turn) {

        if (turn == Turn.BLACK) {
            if (bitSetState.whiteHasWon())
                return Integer.MIN_VALUE;
            else if (bitSetState.blackHasWon())
                return Integer.MAX_VALUE;
        } else {
            if (bitSetState.whiteHasWon())
                return Integer.MAX_VALUE;
            else if (bitSetState.blackHasWon())
                return Integer.MIN_VALUE;

        }
        return bitSetState.getHeuristicValueForPlayer(turn);
    }
}
