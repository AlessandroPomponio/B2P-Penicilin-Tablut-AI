package b2p.state.bitboard.bitset;

import b2p.model.Turn;

import java.util.ArrayList;
import java.util.BitSet;

public class BitSetMove {

    public static ArrayList<BitSetAction> getMovesForPawn(int pawnPosition, BitSetState state) {

        ArrayList<BitSetAction> moves = new ArrayList<>();
        BitSetPosition from = BitSetPosition.values()[pawnPosition];

        //
        BitSet forbiddenCells = state.getBoard();

        // The castle cell is forbidden to step on
        forbiddenCells.set(BitSetPosition.E5.ordinal());

        // Camps may or may not be forbidden to step on
        if (state.getTurn() == Turn.WHITE) {

            // Whites can never go on the camps
            forbiddenCells.or(BitSetPosition.camps);

        } else {

            // Blacks can't go back on the camps
            BitSet currentPawn = BitSetUtils.newFromPositions(new int[]{pawnPosition});
            currentPawn.and(BitSetPosition.camps);

            if (currentPawn.isEmpty()) {
                forbiddenCells.or(BitSetPosition.camps);
            }

        }

        // Check for moves
        // Up
        for (int cell = pawnPosition - 9; cell > 0; cell -= 9) {

            // When we find a forbidden cell, we can stop
            if (forbiddenCells.get(cell))
                break;

            moves.add(new BitSetAction(from.getName(), BitSetPosition.values()[cell].getName(), state.getTurn()));

        }

        // Down
        for (int cell = pawnPosition + 9; cell < BitSetState.boardDimension; cell += 9) {

            // When we find a forbidden cell, we can stop
            if (forbiddenCells.get(cell))
                break;

            moves.add(new BitSetAction(from.getName(), BitSetPosition.values()[cell].getName(), state.getTurn()));

        }

        // Left: check only if the pawn isn't on column A
        if (pawnPosition % 9 != 0) {
            for (int cell = pawnPosition - 1; cell > 0 && cell % 9 != 0; cell--) {

                // When we find a forbidden cell, we can stop
                if (forbiddenCells.get(cell))
                    break;

                moves.add(new BitSetAction(from.getName(), BitSetPosition.values()[cell].getName(), state.getTurn()));

            }
        }

        // Right: check only if the pawn isn't on column I
        if (pawnPosition % 8 != 0) {
            for (int cell = pawnPosition + 1; cell < BitSetState.boardDimension && cell % 8 != 0; cell++) {

                // When we find a forbidden cell, we can stop
                if (forbiddenCells.get(cell))
                    break;

                moves.add(new BitSetAction(from.getName(), BitSetPosition.values()[cell].getName(), state.getTurn()));

            }
        }

        return moves;

    }



}
