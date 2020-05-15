package b2p.search.aima;

import java.util.List;

/**
 * @param <BitSetState>  Type which is used for states in the game.
 * @param <BitSetAction> Type which is used for actions in the game.
 * @param <Turn>         Type which is used for players in the game.
 */
public interface IGame<BitSetState, BitSetAction, Turn> {

    Turn getPlayer(BitSetState state);

    List<BitSetAction> getActions(BitSetState state);

    BitSetState getResult(BitSetState state, BitSetAction action);

    boolean isTerminal(BitSetState state);

    int getUtility(BitSetState state, Turn player);
}
