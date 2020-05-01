package b2p.state.bitboard.bitset.test;

import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;
import b2p.state.bitboard.bitset.*;
import org.junit.jupiter.api.Assertions;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BitSetMoveTest {

    /*
     * funzione : getMovesForPawn
     *
     * Funzione che ritorna la bitboard con tutte le mosse che può fare una certa pedina
     * Il modo con cui genera le postazioni è: valuta le caselle dalla più vicina a quella più lontana in una direzione,
     * poi ripete la stessa valutazione rispetto a tutte le direzioni. Le direzioni sono, in ordine:
     * (Sopra, Sotto, Sinistra, Destra)
     *
     */

    /**
     * funzione : testGetMovesForWhitePawnsAtStart
     * testa la funzione getMovesForPawn per tutte le pedine bianche allo stato iniziale della partita
     * L'ordine di testing comincia dalla pedina in alto a sinitra, finisce con quella in basso a destra
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | N | N | N |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | N |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | B |   |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | N |   |   |   | B |   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | N | N | B | B | C | B | B | N | N |  5
     * +---+---+---+---+---+---+---+---+---+
     * | N |   |   |   | B |   |   |   | N |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | B |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | N |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | N | N | N |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     *   A   B   C   D   E   F   G   H   I
     *
     */
    @org.junit.Test
    public void testGetMovesForWhitePawnsAtStart() {

        BitSetState start = new BitSetState();
        BitSetAction[] wanted;


        wanted = new BitSetAction[]{
                new BitSetAction("E3", "D3", Turn.WHITE),
                new BitSetAction("E3", "C3", Turn.WHITE),
                new BitSetAction("E3", "B3", Turn.WHITE),
                new BitSetAction("E3", "A3", Turn.WHITE),
                new BitSetAction("E3", "F3", Turn.WHITE),
                new BitSetAction("E3", "G3", Turn.WHITE),
                new BitSetAction("E3", "H3", Turn.WHITE),
                new BitSetAction("E3", "I3", Turn.WHITE),
        };
        Assertions.assertArrayEquals(wanted, BitSetMove.getMovesForPawn(BitSetPosition.E3.ordinal(), start).toArray());

        wanted = new BitSetAction[]{
                new BitSetAction("E4", "D4", Turn.WHITE),
                new BitSetAction("E4", "C4", Turn.WHITE),
                new BitSetAction("E4", "B4", Turn.WHITE),
                new BitSetAction("E4", "F4", Turn.WHITE),
                new BitSetAction("E4", "G4", Turn.WHITE),
                new BitSetAction("E4", "H4", Turn.WHITE),
        };
        assertArrayEquals(wanted, BitSetMove.getMovesForPawn(BitSetPosition.E4.ordinal(), start).toArray());

        wanted = new BitSetAction[]{
                new BitSetAction("C5", "C4", Turn.WHITE),
                new BitSetAction("C5", "C3", Turn.WHITE),
                new BitSetAction("C5", "C2", Turn.WHITE),
                new BitSetAction("C5", "C1", Turn.WHITE),
                new BitSetAction("C5", "C6", Turn.WHITE),
                new BitSetAction("C5", "C7", Turn.WHITE),
                new BitSetAction("C5", "C8", Turn.WHITE),
                new BitSetAction("C5", "C9", Turn.WHITE),
        };
        assertArrayEquals(wanted, BitSetMove.getMovesForPawn(BitSetPosition.C5.ordinal(), start).toArray());

        wanted = new BitSetAction[]{
                new BitSetAction("D5", "D4", Turn.WHITE),
                new BitSetAction("D5", "D3", Turn.WHITE),
                new BitSetAction("D5", "D2", Turn.WHITE),
                new BitSetAction("D5", "D6", Turn.WHITE),
                new BitSetAction("D5", "D7", Turn.WHITE),
                new BitSetAction("D5", "D8", Turn.WHITE),
        };
        assertArrayEquals(wanted, BitSetMove.getMovesForPawn(BitSetPosition.D5.ordinal(), start).toArray());

        wanted = new BitSetAction[]{

        };
        assertArrayEquals(wanted, BitSetMove.getMovesForPawn(BitSetPosition.E5.ordinal(), start).toArray());

        wanted = new BitSetAction[]{
                new BitSetAction("F5", "F4", Turn.WHITE),
                new BitSetAction("F5", "F3", Turn.WHITE),
                new BitSetAction("F5", "F2", Turn.WHITE),
                new BitSetAction("F5", "F6", Turn.WHITE),
                new BitSetAction("F5", "F7", Turn.WHITE),
                new BitSetAction("F5", "F8", Turn.WHITE),
        };
        assertArrayEquals(wanted, BitSetMove.getMovesForPawn(BitSetPosition.F5.ordinal(), start).toArray());

        wanted = new BitSetAction[]{
                new BitSetAction("G5", "G4", Turn.WHITE),
                new BitSetAction("G5", "G3", Turn.WHITE),
                new BitSetAction("G5", "G2", Turn.WHITE),
                new BitSetAction("G5", "G1", Turn.WHITE),
                new BitSetAction("G5", "G6", Turn.WHITE),
                new BitSetAction("G5", "G7", Turn.WHITE),
                new BitSetAction("G5", "G8", Turn.WHITE),
                new BitSetAction("G5", "G9", Turn.WHITE),
        };
        assertArrayEquals(wanted, BitSetMove.getMovesForPawn(BitSetPosition.G5.ordinal(), start).toArray());

        wanted = new BitSetAction[]{
                new BitSetAction("E6", "D6", Turn.WHITE),
                new BitSetAction("E6", "C6", Turn.WHITE),
                new BitSetAction("E6", "B6", Turn.WHITE),
                new BitSetAction("E6", "F6", Turn.WHITE),
                new BitSetAction("E6", "G6", Turn.WHITE),
                new BitSetAction("E6", "H6", Turn.WHITE),
        };
        assertArrayEquals(wanted, BitSetMove.getMovesForPawn(BitSetPosition.E6.ordinal(), start).toArray());

        wanted = new BitSetAction[]{
                new BitSetAction("E7", "D7", Turn.WHITE),
                new BitSetAction("E7", "C7", Turn.WHITE),
                new BitSetAction("E7", "B7", Turn.WHITE),
                new BitSetAction("E7", "A7", Turn.WHITE),
                new BitSetAction("E7", "F7", Turn.WHITE),
                new BitSetAction("E7", "G7", Turn.WHITE),
                new BitSetAction("E7", "H7", Turn.WHITE),
                new BitSetAction("E7", "I7", Turn.WHITE),
        };
        assertArrayEquals(wanted, BitSetMove.getMovesForPawn(BitSetPosition.E7.ordinal(), start).toArray());
    }


    /**
     * funzione : testGetMovesForBlackPawnsAtStart
     * testa la funzione getMovesForPawn per le pedine nere dell'accampameno in alto
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | N | N | N |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | N |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | B |   |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   | B |   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A | B | B | C | B | B | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   | B |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | B |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     *   A   B   C   D   E   F   G   H   I
     *
     */
    @org.junit.Test
    public void testGetMovesForBlackPawnsAtStart() {

        BitSetState start = new BitSetState();
        BitSetAction[] wanted;

        start.setTurn(Turn.BLACK);

        wanted = new BitSetAction[]{
                new BitSetAction("D1", "D2", Turn.BLACK),
                new BitSetAction("D1", "D3", Turn.BLACK),
                new BitSetAction("D1", "D4", Turn.BLACK),
                new BitSetAction("D1", "C1", Turn.BLACK),
                new BitSetAction("D1", "B1", Turn.BLACK),
                new BitSetAction("D1", "A1", Turn.BLACK),
        };
        assertArrayEquals(wanted, BitSetMove.getMovesForPawn(BitSetPosition.D1.ordinal(), start).toArray());

        wanted = new BitSetAction[]{

        };
        assertArrayEquals(wanted, BitSetMove.getMovesForPawn(BitSetPosition.E1.ordinal(), start).toArray());

        wanted = new BitSetAction[]{
                new BitSetAction("F1", "F2", Turn.BLACK),
                new BitSetAction("F1", "F3", Turn.BLACK),
                new BitSetAction("F1", "F4", Turn.BLACK),
                new BitSetAction("F1", "G1", Turn.BLACK),
                new BitSetAction("F1", "H1", Turn.BLACK),
                new BitSetAction("F1", "I1", Turn.BLACK),
        };
        assertArrayEquals(wanted, BitSetMove.getMovesForPawn(BitSetPosition.F1.ordinal(), start).toArray());

        wanted = new BitSetAction[]{
                new BitSetAction("E2", "D2", Turn.BLACK),
                new BitSetAction("E2", "C2", Turn.BLACK),
                new BitSetAction("E2", "B2", Turn.BLACK),
                new BitSetAction("E2", "A2", Turn.BLACK),
                new BitSetAction("E2", "F2", Turn.BLACK),
                new BitSetAction("E2", "G2", Turn.BLACK),
                new BitSetAction("E2", "H2", Turn.BLACK),
                new BitSetAction("E2", "I2", Turn.BLACK),
        };
        assertArrayEquals(wanted, BitSetMove.getMovesForPawn(BitSetPosition.E2.ordinal(), start).toArray());
    }


    /**
     * funzione : testCampCollision
     * testa la funzione getMovesForPawn per verificare la collisione con gli accampamenti
     *
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   | N |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   | B | N |  2
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
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     *   A   B   C   D   E   F   G   H   I
     *
     */
    @org.junit.Test
    public void testCampCollision(){

        BitSetAction[] wanted;
        BitSetState current = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.I2,
                        BitSetPosition.H1,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.H2,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );

        wanted = new BitSetAction[]{
                new BitSetAction("H2", "H3", Turn.WHITE),
                new BitSetAction("H2", "H4", Turn.WHITE),
                new BitSetAction("H2", "G2", Turn.WHITE),
                new BitSetAction("H2", "F2", Turn.WHITE),
        };
        assertArrayEquals(wanted, BitSetMove.getMovesForPawn(BitSetPosition.H2.ordinal(), current).toArray());
    }


    /**
     * funzione : testCastleCollision
     * testa la funzione getMovesForPawn per verificare la collisione con il castello
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   | N |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   | B | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   | N |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     *   A   B   C   D   E   F   G   H   I
     *
     */
    @org.junit.Test
    public void testCastleCollision(){

        BitSetAction[] wanted;
        BitSetState current = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.G3,
                        BitSetPosition.G7,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.G5,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );

        wanted = new BitSetAction[]{
                new BitSetAction("G5", "G4", Turn.WHITE),
                new BitSetAction("G5", "G6", Turn.WHITE),
                new BitSetAction("G5", "F5", Turn.WHITE),
        };
        assertArrayEquals(BitSetMove.getMovesForPawn(BitSetPosition.G5.ordinal(), current).toArray(), wanted);
    }


    /**
     * funzione : testBorderCollision
     * testa la funzione getMovesForPawn per verificare la collisione con i bordi
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   | B |  1
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
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     *   A   B   C   D   E   F   G   H   I
     *
     */
    @org.junit.Test
    public void testBorderCollision(){

        BitSetAction[] wanted;
        BitSetState current = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.I1,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );

        wanted = new BitSetAction[]{
                new BitSetAction("I1", "I2", Turn.WHITE),
                new BitSetAction("I1", "I3", Turn.WHITE),
                new BitSetAction("I1", "H1", Turn.WHITE),
                new BitSetAction("I1", "G1", Turn.WHITE),
        };
        assertArrayEquals(wanted, BitSetMove.getMovesForPawn(BitSetPosition.I1.ordinal(), current).toArray());
    }


    /**
     * funzione : testKingCollision
     * testa la funzione getMovesForPawn per verificare la collisione del re con castello e bordi
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
     * | A | A |   |   | C |   | R | A | A |  5
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
    public void testKingCollisions(){

        BitSetAction[] wanted;
        BitSetState current = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.G5,
                })
        );

        wanted = new BitSetAction[]{
                new BitSetAction("G5", "G4", Turn.WHITE),
                new BitSetAction("G5", "G3", Turn.WHITE),
                new BitSetAction("G5", "G2", Turn.WHITE),
                new BitSetAction("G5", "G1", Turn.WHITE),
                new BitSetAction("G5", "G6", Turn.WHITE),
                new BitSetAction("G5", "G7", Turn.WHITE),
                new BitSetAction("G5", "G8", Turn.WHITE),
                new BitSetAction("G5", "G9", Turn.WHITE),
                new BitSetAction("G5", "F5", Turn.WHITE),
        };
        assertArrayEquals(wanted, BitSetMove.getMovesForPawn(BitSetPosition.G5.ordinal(), current).toArray());
    }


    /* funzione : testGetCapturedPawns
     * Funzione che ritorna la bitboard con le pedine che vengono mangiate facendo una determinata mossa
     * Bianco:  Funzione che fa la cattura standard dai due lati più casi limite (accampamenti, castello e ostacoli)
     * Nero:    Funzione che fa la cattura come quella dei bianchi, più le catture speciali.
     */

    /**
     * funzione : testGetCapturedPawnsNoCaptures
     * testa che la funzione getCapturedPawns restituisca una bitmap vuota se non ci sono condizioni di accerchiamento
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   | N |   | B |   |  3
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
     */
    @org.junit.Test
    public void testGetCapturedPawnsNoCaptures() {

        BitSet wanted;
        BitSetState current = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.F3,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.H3,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );

        wanted = BitSetUtils.newFromPositions(new BitSetPosition[]{

        });

        assertEquals(BitSetUtils.toBitString(wanted), BitSetUtils.toBitString(BitSetMove.getCapturedPawns(BitSetPosition.H3.ordinal(), BitSetPosition.G3.ordinal(), current)));
    }


    /**
     * funzione : testGetCapturedPawnsSingleCapture
     * testa la singola cattura di una pedina nera nella funzione getCapturedPawns.
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   | N | B |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   | B |   |   |   | A |  4
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
     */
    @org.junit.Test
    public void testGetCapturedPawnsSingleCapture() {

        BitSet wanted;
        BitSetState current = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.F3,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.G3,
                        BitSetPosition.E4,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );

        wanted = BitSetUtils.newFromPositions(new BitSetPosition[]{
                BitSetPosition.F3,
        });

        assertEquals(BitSetUtils.toBitString(wanted), BitSetUtils.toBitString(BitSetMove.getCapturedPawns(BitSetPosition.E4.ordinal(), BitSetPosition.E3.ordinal(), current)));
    }


    /**
     * funzione : testGetCapturedPawnsDoubleCapture
     * testa che la funzione getCapturedPawns catturi correttamente due pedine alla volta
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   | B |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | B | N |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   | N |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   | B | A | A |  5
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
    public void testGetCapturedPawnsDoubleCapture() {

        BitSet wanted;
        BitSetState current = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.F3,
                        BitSetPosition.G4,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.G2,
                        BitSetPosition.E3,
                        BitSetPosition.G5
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );

        wanted = BitSetUtils.newFromPositions(new BitSetPosition[]{
                BitSetPosition.F3,
                BitSetPosition.G4,
        });

        assertEquals(BitSetUtils.toBitString(wanted), BitSetUtils.toBitString(BitSetMove.getCapturedPawns(BitSetPosition.G2.ordinal(), BitSetPosition.G3.ordinal(), current)));
    }


    /**
     * funzione : testGetCapturedPawnsTripleCapture
     * testa che la funzione getCapturedPawns catturi correttamente tre pedine alla volta
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   | B |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | B | N |   | N | B |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   | N |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   | B | A | A |  5
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
    public void testGetCapturedPawnsTripleCapture() {

        BitSet wanted;
        BitSetState current = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.F3,
                        BitSetPosition.G4,
                        BitSetPosition.H3,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.G2,
                        BitSetPosition.E3,
                        BitSetPosition.G5,
                        BitSetPosition.I3,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );

        wanted = BitSetUtils.newFromPositions(new BitSetPosition[]{
                BitSetPosition.F3,
                BitSetPosition.G4,
                BitSetPosition.H3,
        });

        assertEquals(BitSetUtils.toBitString(wanted), BitSetUtils.toBitString(BitSetMove.getCapturedPawns(BitSetPosition.G2.ordinal(), BitSetPosition.G3.ordinal(), current)));
    }


    /**
     * funzione : testGetCapturedPawnsCastleLegalCapture
     * testa che la funzione getCapturedPawns catturi correttamente una pedina nera fra una pedina bianca e il castello
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
     * | A | A |   |   | C | N |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   | B |   | A |  6
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
    public void testGetCapturedPawnsCastleLegalCapture() {

        BitSet wanted;
        BitSetState current = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.F5,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.G6,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );

        wanted = BitSetUtils.newFromPositions(new BitSetPosition[]{
                BitSetPosition.F5,
        });

        assertEquals(BitSetUtils.toBitString(wanted), BitSetUtils.toBitString(BitSetMove.getCapturedPawns(BitSetPosition.G6.ordinal(), BitSetPosition.G5.ordinal(), current)));
    }


    /**
     * funzione : testGetCapturedPawnsCampLegalCapture
     * testa che la funzione getCapturedPawns catturi una pedina nera che si trovi dentro l'accampamento quando
     * la condizione di cattura è valida
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   | B |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   |   | N | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   | B | A |  6
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
    public void testGetCapturedPawnsCampLegalCapture() {

        BitSet wanted;
        BitSetState current = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.H5,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.H3,
                        BitSetPosition.H6,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );

        wanted = BitSetUtils.newFromPositions(new BitSetPosition[]{
                BitSetPosition.H5,
        });

        assertEquals(BitSetUtils.toBitString(wanted), BitSetUtils.toBitString(BitSetMove.getCapturedPawns(BitSetPosition.H3.ordinal(), BitSetPosition.H4.ordinal(), current)));
    }


    /**
     * funzione : testGetCapturedPawnsCampIllegalCapture
     * testa che la funzione getCapturedPawns non catturi la pedina che si trova nell'accampamento sul bordo, considerando
     * la cella centrale dell'accampamento come collisione
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   | B |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | N |  4
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
    public void testGetCapturedPawnsCampIllegalCapture() {

        BitSet wanted;
        BitSetState current = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.I4,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.H3,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );

        wanted = BitSetUtils.newFromPositions(new BitSetPosition[]{

        });

        assertEquals(BitSetUtils.toBitString(wanted), BitSetUtils.toBitString(BitSetMove.getCapturedPawns(BitSetPosition.H3.ordinal(), BitSetPosition.I3.ordinal(), current)));
    }


    /**
     * funzione : testGetCapturedPawnsOutOfCampCapture
     * testa che la funzione getCapturedPawns catturi una pedina nera che si trovi fuori dall'accampamento quando
     * la condizione di cattura è valida
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
     * | A | A |   |   | C |   | N | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   | B |   |   | A |  6
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
    public void testGetCapturedPawnsOutOfCampCapture() {

        BitSet wanted;
        BitSetState current = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.G5,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.F6,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );

        wanted = BitSetUtils.newFromPositions(new BitSetPosition[]{
                BitSetPosition.G5,
        });

        assertEquals(BitSetUtils.toBitString(wanted), BitSetUtils.toBitString(BitSetMove.getCapturedPawns(BitSetPosition.F6.ordinal(), BitSetPosition.F5.ordinal(), current)));
    }


    /**
     * funzione : testGetCapturedPawnsKingInCastle
     * testa che la funzione getCapturedPawns catturi correttamente la pedina re quando si trova nel castello
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   | N |   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   | N | R | N |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | N |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     *   A   B   C   D   E   F   G   H   I
     *
     */
    @org.junit.Test
    public void testGetCapturedPawnsKingInCastle() {

        BitSet wanted;
        BitSetState current = new BitSetState(
                Turn.BLACK,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.E4,
                        BitSetPosition.D5,
                        BitSetPosition.F5,
                        BitSetPosition.E7,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.E5,
                })
        );

        wanted = BitSetUtils.newFromPositions(new BitSetPosition[]{
                BitSetPosition.E5,
        });

        assertEquals(BitSetUtils.toBitString(wanted), BitSetUtils.toBitString(BitSetMove.getCapturedPawns(BitSetPosition.E7.ordinal(), BitSetPosition.E6.ordinal(), current)));
    }


    /**
     * funzione : testGetCapturedPawnsKingInCastle
     * testa che la funzione getCapturedPawns catturi correttamente la pedina re quando si trova nel castello
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | N |   |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   | N |   | R | N |   |   | A |  4
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
    public void testGetCapturedPawnsKingOutOfCastle() {

        BitSet wanted;
        BitSetState current = new BitSetState(
                Turn.BLACK,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.E3,
                        BitSetPosition.C4,
                        BitSetPosition.F4,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.E4,
                })
        );

        wanted = BitSetUtils.newFromPositions(new BitSetPosition[]{
                BitSetPosition.E4,
        });

        assertEquals(BitSetUtils.toBitString(wanted), BitSetUtils.toBitString(BitSetMove.getCapturedPawns(BitSetPosition.C4.ordinal(), BitSetPosition.D4.ordinal(), current)));
    }


    /*
     * function : movesNeededForKingEscape
     */

    /**
     * funzione : testMovesNeededForKingEscapeNoMoves
     * testa che la funzione movesNeededForKingEscape valuti correttamente il numero di mosse quando non ci sono mosse
     * che conducono al goal
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A | N |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | N | R | N |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   | N |   |   | A |  4
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
    public void testMovesNeededForKingEscapeNoMoves() {

        BitSetState current = new BitSetState(
                Turn.BLACK,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.F2,
                        BitSetPosition.E3,
                        BitSetPosition.G3,
                        BitSetPosition.F4,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.F3,
                })
        );

        assertEquals(50, BitSetMove.movesNeededForKingEscape(current));
    }


    /**
     * funzione : testMovesNeededForKingEscapeOneMove
     * testa che la funzione movesNeededForKingEscape valuti correttamente il numero di mosse quando il re è ad una
     * mossa dalla vittoria
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A | R |   |   |   |  2
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
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     *   A   B   C   D   E   F   G   H   I
     *
     */
    @org.junit.Test
    public void testMovesNeededForKingEscapeOneMove() {

        BitSetState current = new BitSetState(
                Turn.BLACK,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.F2,
                })
        );

        assertEquals(1, BitSetMove.movesNeededForKingEscape(current));
    }


    /**
     * funzione : testMovesNeededForKingEscapeTwoMoves
     * testa che la funzione movesNeededForKingEscape valuti correttamente il numero di mosse quando il re è a due
     * mosse dalla vittoria
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A | R1|   |   | R2|  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   | R |   |   | A |  4
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
    public void testMovesNeededForKingEscapeTwoMoves() {

        BitSetState current = new BitSetState(
                Turn.BLACK,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.F4,
                })
        );

        assertEquals(2, BitSetMove.movesNeededForKingEscape(current));
    }


    /**
     * funzione : testMovesNeededForKingEscapeThreeMoves
     * testa che la funzione movesNeededForKingEscape valuti correttamente il numero di mosse quando il re è a tre
     * mosse dalla vittoria
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   | R3|   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A | N |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | N | R | N |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   | N | R1|   | R2| A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C | N | N | A | A |  5
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
    public void testMovesNeededForKingEscapeThreeMoves() {

        BitSetState current = new BitSetState(
                Turn.BLACK,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.F2,
                        BitSetPosition.E3,
                        BitSetPosition.G3,
                        BitSetPosition.E4,
                        BitSetPosition.F5,
                        BitSetPosition.G5,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.F3,
                })
        );

        assertEquals(3, BitSetMove.movesNeededForKingEscape(current));
    }

}