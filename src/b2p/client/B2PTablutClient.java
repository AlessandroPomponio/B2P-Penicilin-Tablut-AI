package b2p.client;

import b2p.model.IAction;
import b2p.search.aima.TablutGame;
import b2p.search.aima.minmax.IterativeDeepening;
import b2p.state.bitboard.bitset.BitSetState;
import b2p.state.bitboard.bitset.BitSetUtils;
import it.unibo.ai.didattica.competition.tablut.client.TablutClient;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;

import java.io.IOException;

/**
 * This Class extends the {@link TablutClient} class to represent a B2P player
 *
 * @author Alessandro Buldini
 * @author Alessandro Pomponio
 * @author Federico Zanini
 * @see TablutClient
 */
public class B2PTablutClient extends TablutClient {

    /**
     * String containing the player name
     */
    private static final String PLAYER_NAME = "B2P";
    /**
     * Integer representing the timeout of every turn
     */
    private final int timeout;

    /**
     * Constructor method to create an instance of {@link B2PTablutClient} with given arguments
     * @param player String containing the player name
     * @param timeout Integer representing the timeout of every turn
     * @param ipAddress String representing IP address of the server
     * @throws IOException
     */
    public B2PTablutClient(String player, int timeout, String ipAddress) throws IOException {
        super(player, PLAYER_NAME, timeout, ipAddress);
        this.timeout = timeout;
    }

    /**
     * Constructor method to create an instance of {@link B2PTablutClient} with given arguments and default timeout set to 60000 milliseconds
     * @param player String containing the player name
     * @param ipAddress String representing IP address of the server
     * @throws IOException
     */
    public B2PTablutClient(String player, String ipAddress) throws IOException {
        super(player, PLAYER_NAME, 60000, ipAddress);
        this.timeout = 60000;
    }

    /**
     * Constructor method to create an instance of {@link B2PTablutClient} with a given player name and default
     * timeout (set to 60000 milliseconds) and ipAddress
     * @param player String containing the player name
     * @throws IOException
     */
    public B2PTablutClient(String player) throws IOException {
        super(player, PLAYER_NAME, 60000);
        this.timeout = 60000;
    }

    /**
     * Constructor method to create an instance of {@link B2PTablutClient} with given arguments and default timeout set to 60000 milliseconds
     * @param player String containing the player name
     * @param timeout Integer representing the timeout of every turn
     * @throws IOException
     */
    public B2PTablutClient(String player, int timeout) throws IOException {
        super(player, PLAYER_NAME, timeout);
        this.timeout = timeout;
    }

    /**
     * Starts the client
     */
    @Override
    public void run() {

        // Declare the name first thing
        try {
            this.declareName();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // States
        BitSetState state;
        State serverState;

        System.out.println("You are player " + this.getPlayer().toString() + "!");
        TablutGame gameInstance = new TablutGame(new BitSetState());
        IterativeDeepening search = new IterativeDeepening(gameInstance, Integer.MIN_VALUE, Integer.MAX_VALUE, timeout / 1000 - 1);

        int turn = 0;

        while (true) {

            turn++;
            try {
                this.read();
            } catch (ClassNotFoundException | IOException e1) {
                e1.printStackTrace();
                System.exit(1);
            }

            System.out.println("----- TURN " + turn + " -----");
            System.out.println("Current state:");
            serverState = this.getCurrentState();
            System.out.println(serverState.toString() + "\n");

            state = BitSetUtils.newFromServer((StateTablut) serverState, turn);
            search.setGameState(state);

            if (this.getPlayer().equals(State.Turn.WHITE)) {

                if (this.getCurrentState().getTurn().equals(StateTablut.Turn.WHITE)) {
                    searchAndSubmitAction(state, search);
                } else {
                    printTurnComment(state, "YOU WIN!", "YOU LOSE!");
                }

            } else {

                if (this.getCurrentState().getTurn().equals(StateTablut.Turn.BLACK)) {
                    searchAndSubmitAction(state, search);
                } else {
                    printTurnComment(state, "YOU LOSE!", "YOU WIN!");
                }
            }
        }
    }

    /**
     * Method used to search the best move for a given state
     * @param state {@link BitSetState} representing the current state of the game
     * @param search {@link IterativeDeepening} instance representing the search algorithm
     * @see BitSetState
     * @see IterativeDeepening
     */
    private void searchAndSubmitAction(BitSetState state, IterativeDeepening search) {
        try {

            IAction bestMove = search.makeDecision(state);
            System.out.println("The move that has been selected is: " + bestMove.toString() + "\n");
            Action a = new Action(bestMove.getFrom(), bestMove.getTo(), StateTablut.Turn.valueOf(bestMove.getTurn().name()));
            this.write(a);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Utility method used to print a comment of the current turn
     * @param state {@link BitSetState} representing the current state of the game
     * @param whiteWins String to print in case of white win
     * @param blackWins String to print in case of black win
     * @see BitSetState
     */
    private void printTurnComment(BitSetState state, String whiteWins, String blackWins) {
        if (state.getTurn().equals(StateTablut.Turn.WHITE)) {
            System.out.println("Waiting for your opponent move... ");
        } else if (state.getTurn().equals(StateTablut.Turn.WHITEWIN)) {
            System.out.println(whiteWins);
            System.exit(0);
        } else if (state.getTurn().equals(StateTablut.Turn.BLACKWIN)) {
            System.out.println(blackWins);
            System.exit(0);
        } else if (state.getTurn().equals(StateTablut.Turn.DRAW)) {
            System.out.println("DRAW!");
            System.exit(0);
        }
    }


}

