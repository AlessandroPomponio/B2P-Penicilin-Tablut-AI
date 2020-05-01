package b2p.state.bitboard.bitset.test;

import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;
import b2p.state.bitboard.bitset.*;
import org.junit.jupiter.api.Assertions;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BitSetStateTest {

    /*
     * NOTE:
     * -    Poichè la getAvailableKingMoves incapsula la funzione getMovesForPawns, già testata in BitSetMoveTest, queste
     *      non verranno scritti test in merito
     *
     * -    Inoltre, il controllo della validità della mossa all'interno di performMove può essere fatto con:
     *          if(! getAvailablePawnMoves().contains(new BitSetAction(from+"", to+"", turn)))
     *              return;
     *      Oppure rendendo il metodo void performMove(int,int) privato ed aggiungendo il controllo nelle due funzioni che lo
     *      incapsulano.
     *      In una funzione di più alto livello sarà necessario testare che le mosse illegali come
     *      *   Attraversare accampamenti
     *      *   Attraversare il castello
     *      *   Spostare la pedina in diagonale
     *      Non vengano accettate o che lancino eccezioni (anche se potenzialmente pericoloso)
     *
     *
     * TODO:
     * - Verificare la performMove dello stato
     * - Simulare la cattura nella performMove dello stato e verificare che non catturi quando una pedina si trova tra
     *   due pedine del colore opposto (se una pedina del colore opposto si muove in generale e se si muove in una cella
     *   adiacente)
     *
     * - Verificare la isWinningState dello stato
     */

    /*
     * Test di getAvailablePawnMoves
     */

    /**
     * funzione : testGetAvailablePawnMovesEmpty
     * testa la funzione getAvailablePawnMoves per verificare che non restituisca mosse quando non deve restituirle
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   | N |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   | N | B | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     *   A   B   C   D   E   F   G   H   I
     *
     */
    @org.junit.Test
    public void testGetAvailablePawnMovesEmpty() {

        BitSetAction[] wanted;
        BitSetState current = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.H3,
                        BitSetPosition.G4,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.H4,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );
        wanted = new BitSetAction[]{

        };

        assertArrayEquals(current.getAvailablePawnMoves().toArray(), wanted);
    }


    /**
     * funzione : testCollisionGetAvailablePawnMoves
     * testa la funzione getAvailablePawnMoves per verificare che valuti le collisioni correttamente
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   | N | N | N |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A | B |   | B |  9
     * +---+---+---+---+---+---+---+---+---+
     *   A   B   C   D   E   F   G   H   I
     *
     */
    @org.junit.Test
    public void testCollisionGetAvailablePawnMoves() {

        BitSetAction[] wanted;
        BitSetState current = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.G8,
                        BitSetPosition.H8,
                        BitSetPosition.I8,

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.G9,
                        BitSetPosition.I9,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );
        wanted = new BitSetAction[]{
                new BitSetAction("G9", "H9", Turn.WHITE),
                new BitSetAction("I9", "H9", Turn.WHITE),
        };

        assertArrayEquals(current.getAvailablePawnMoves().toArray(), wanted);
    }

    /*
     * Test di performMoveAction
     */

    /**
     * funzione : testPerformMoveAction
     * testa la funzione performMove(IAction) per verificare che esegua correttamente la mossa
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | B |   | B'|   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     *   A   B   C   D   E   F   G   H   I
     *
     */
    @org.junit.Test
    public void testPerformMoveAction() {

        String from = "D3", to = "F3";
        BitSetState fromState = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.D3,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );

        BitSetState toState = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.F3,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );

        fromState.performMove(new BitSetAction(from, to, Turn.WHITE));
        assertEquals(BitSetUtils.toBitString(fromState.getBoard()), BitSetUtils.toBitString(toState.getBoard()));
    }


    /**
     * funzione : testPerformMoveString
     * testa la funzione performMove(String, String) per verificare che esegua correttamente la mossa
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | B |   | B'|   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     *   A   B   C   D   E   F   G   H   I
     *
     */
    @org.junit.Test
    public void testPerformMoveString() {

        String from = "D7", to = "F7";
        BitSetState fromState = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.D7,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );

        BitSetState toState = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.F7,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );

        fromState.performMove(from, to);
        assertEquals(BitSetUtils.toBitString(fromState.getBoard()), BitSetUtils.toBitString(toState.getBoard()));
    }


    /**
     * funzione : testPerformMoveStandard
     * testa la funzione performMove(int, int) per verificare che esegua correttamente la mossa
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | B |   | B'|   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     *   A   B   C   D   E   F   G   H   I
     *
     */
    @org.junit.Test
    public void testPerformMoveStandard() {

        BitSetState fromState = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.D3,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );

        BitSetState toState = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.F3,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );

        fromState.performMove(BitSetPosition.D3.ordinal(), BitSetPosition.F3.ordinal());
        assertEquals(BitSetUtils.toBitString(fromState.getBoard()), BitSetUtils.toBitString(toState.getBoard()));
    }


    /**
     * funzione : testKingInCastleIllegalCapture
     * testa la funzione blackHasWon per verificare le condizioni di vittoria del giocatore nero, simulando la mossa
     * con la performMove per verificare anche la cattura speciale del re circondato da tre pedine nere quando il re
     * si trova nel castello
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | N | - | - | > | N'|   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | R |   |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   | N |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     *   A   B   C   D   E   F   G   H   I
     *
     */
    @org.junit.Test
    public void testKingInCastleIllegalCapture() {

        BitSetState fromState = new BitSetState(
                Turn.BLACK,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.A4,
                        BitSetPosition.E6,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.E5,
                })
        );

        BitSetState toState = new BitSetState(
                Turn.BLACK,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.E4,
                        BitSetPosition.E6,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.E5
                })
        );
        fromState.performMove(BitSetPosition.A4.ordinal(), BitSetPosition.E4.ordinal());
        assertEquals(BitSetUtils.toBitString(fromState.getBoard()), BitSetUtils.toBitString(toState.getBoard()));
        assert(!fromState.blackHasWon());
    }


    /**
     * funzione : testKingInCastleIllegalSpecialCapture
     * testa la funzione blackHasWon per verificare le condizioni di vittoria del giocatore nero, simulando la mossa
     * con la performMove per verificare anche la cattura speciale del re circondato da tre pedine nere quando il re
     * si trova nel castello
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | N | - | - | > | N'|   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   | N | R | N |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     *   A   B   C   D   E   F   G   H   I
     *
     */
    @org.junit.Test
    public void testKingInCastleIllegalSpecialCapture() {

        BitSetState fromState = new BitSetState(
                Turn.BLACK,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.D5,
                        BitSetPosition.F5,
                        BitSetPosition.A4,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.E5,
                })
        );

        BitSetState toState = new BitSetState(
                Turn.BLACK,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.E4,
                        BitSetPosition.D5,
                        BitSetPosition.F5,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.E5
                })
        );
        fromState.performMove(BitSetPosition.A4.ordinal(), BitSetPosition.E4.ordinal());
        assertEquals(BitSetUtils.toBitString(fromState.getBoard()), BitSetUtils.toBitString(toState.getBoard()));
        assert(!fromState.blackHasWon());
    }

    /*
     * Test di hasWhiteWon
     */

    /**
     * funzione : testWhiteWinningCondition
     * testa la funzione whiteHasWon per verificare le condizioni di vittoria del giocatore bianco,
     * simulando la mossa tramite performeMove, per testare anch'essa sul re
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | R | - | - | - | > | R'|  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     *   A   B   C   D   E   F   G   H   I
     *
     */
    @org.junit.Test
    public void testWhiteStandardWinningCondition() {
        BitSetPosition from = BitSetPosition.D3, to = BitSetPosition.I3;

        BitSetState fromState = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                    from,
                })
        );

        BitSetState toState = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                    to,
                })
        );

        fromState.performMove(from.ordinal(), to.ordinal());
        assertEquals(BitSetUtils.toBitString(fromState.getBoard()), BitSetUtils.toBitString(toState.getBoard()));
        assert(toState.whiteHasWon());
    }

    /*
     * Test di hasBlackWon
     */

    /**
     * funzione : testBlackStandardWinningCondition
     * testa la funzione blackHasWon per verificare le condizioni di vittoria del giocatore nero, simulando la mossa
     * con la performMove per verificare anche la cattura standard del re circondato da due pedine nere quando il re
     * non si trova nè in una posizione adiacente al castello, nè dentro di esso
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * | N | - | - | - | - | > | N'| R | N |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     *   A   B   C   D   E   F   G   H   I
     *
     */
    @org.junit.Test
    public void testBlackStandardWinningCondition() {

        BitSetState fromState = new BitSetState(
                Turn.BLACK,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.A3,
                        BitSetPosition.I3,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.H3,
                })
        );

        BitSetState toState = new BitSetState(
                Turn.BLACK,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.G3,
                        BitSetPosition.I3,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );
        fromState.performMove(BitSetPosition.A3.ordinal(), BitSetPosition.G3.ordinal());
        assertEquals(BitSetUtils.toBitString(fromState.getBoard()), BitSetUtils.toBitString(toState.getBoard()));
        assert(fromState.blackHasWon());
    }


    /**
     * funzione : testKingNearCastleBlackWinningCondition
     * testa la funzione blackHasWon per verificare le condizioni di vittoria del giocatore nero, simulando la mossa
     * con la performMove per verificare anche la cattura speciale del re circondato da tre pedine nere quando il re
     * si trova in una posizione adiacente al castello
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * | N | - | - | > | N'|   |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   | N | R | N |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     *   A   B   C   D   E   F   G   H   I
     *
     */
    @org.junit.Test
    public void testKingNearCastleBlackWinningCondition() {

        BitSetState fromState = new BitSetState(
                Turn.BLACK,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.D4,
                        BitSetPosition.F4,
                        BitSetPosition.A3,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.E4,
                })
        );

        BitSetState toState = new BitSetState(
                Turn.BLACK,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.D4,
                        BitSetPosition.F4,
                        BitSetPosition.E3,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );
        fromState.performMove(BitSetPosition.A3.ordinal(), BitSetPosition.E3.ordinal());
        assertEquals(BitSetUtils.toBitString(fromState.getBoard()), BitSetUtils.toBitString(toState.getBoard()));
        assert(fromState.blackHasWon());
    }


    /**
     * funzione : testKingInCastleBlackWinningCondition
     * testa la funzione blackHasWon per verificare le condizioni di vittoria del giocatore nero, simulando la mossa
     * con la performMove per verificare anche la cattura speciale del re circondato da tre pedine nere quando il re
     * si trova nel castello
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | N | - | - | > | N'|   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   | N | R | N |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   | N |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     *   A   B   C   D   E   F   G   H   I
     *
     */
    @org.junit.Test
    public void testKingInCastleBlackWinningCondition() {

        BitSetState fromState = new BitSetState(
                Turn.BLACK,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.D5,
                        BitSetPosition.F5,
                        BitSetPosition.A4,
                        BitSetPosition.E6,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.E5,
                })
        );

        BitSetState toState = new BitSetState(
                Turn.BLACK,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.D5,
                        BitSetPosition.F5,
                        BitSetPosition.E4,
                        BitSetPosition.E6,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );
        fromState.performMove(BitSetPosition.A4.ordinal(), BitSetPosition.E4.ordinal());
        assertEquals(BitSetUtils.toBitString(fromState.getBoard()), BitSetUtils.toBitString(toState.getBoard()));
        assert(fromState.blackHasWon());
    }

}
