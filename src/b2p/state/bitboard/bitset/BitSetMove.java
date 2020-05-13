package b2p.state.bitboard.bitset;

import b2p.model.IAction;
import b2p.model.IState;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;
import org.hamcrest.core.Is;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class BitSetMove {

    public static List<IAction> getMovesForPawn(int pawnPosition, IState state) {

        // 16 is the maximum amount of moves we can possibly have:
        // it ensures no further allocations are needed
        List<IAction> moves = new ArrayList<>(16);
        BitSetPosition from = BitSetPosition.values()[pawnPosition];

        //
        B2PBitSet forbiddenCells = state.getBoard().clone();

        // The castle cell is forbidden to step on
        forbiddenCells.set(BitSetPosition.E5.ordinal());

        // Camps may or may not be forbidden to step on
        if (state.getTurn() == Turn.WHITE) {

            // Whites can never go on the camps
            forbiddenCells.or(BitSetPosition.camps);

        } else {

            // Blacks can't go back on the camps
            if (!BitSetPosition.camps.get(pawnPosition)) {
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
        for (int cell = pawnPosition + 9; cell < IState.boardDimension; cell += 9) {

            // When we find a forbidden cell, we can stop
            if (forbiddenCells.get(cell))
                break;

            moves.add(new BitSetAction(from.getName(), BitSetPosition.values()[cell].getName(), state.getTurn()));

        }

        // Left: check only if the pawn isn't on column A
        if (pawnPosition % 9 != 0) {

            // Make sure we don't end up out of the board
            // or one row above in column I
            for (int cell = pawnPosition - 1; cell >= 0 && cell % 9 != 8; cell--) {

                // When we find a forbidden cell, we can stop
                if (forbiddenCells.get(cell))
                    break;

                moves.add(new BitSetAction(from.getName(), BitSetPosition.values()[cell].getName(), state.getTurn()));

            }

        }

        // Right: check only if the pawn isn't on column I
        if (pawnPosition % 9 != 8) {

            // Make sure we don't end up out of the board
            // or one row below in column A
            for (int cell = pawnPosition + 1; cell < IState.boardDimension && cell % 9 != 0; cell++) {

                // When we find a forbidden cell, we can stop
                if (forbiddenCells.get(cell))
                    break;

                moves.add(new BitSetAction(from.getName(), BitSetPosition.values()[cell].getName(), state.getTurn()));

            }

        }

        return moves;

    }

    public static B2PBitSet getCapturedPawns(int from, int to, IState state) {

        if (state.getTurn() == Turn.BLACK)
            return getCapturesForBlack(from, to, state);

        return getCapturesForWhite(from, to, state);

    }

    private static B2PBitSet getCapturesForBlack(int from, int to, IState state) {

        //
        B2PBitSet blacks = state.getBlackPawns().clone();
        blacks.clear(from);

        B2PBitSet king = state.getKing().clone();
        B2PBitSet whites = state.getWhitePawns().clone();

        //
        B2PBitSet specialCaptures = new B2PBitSet(IState.boardDimension);
        blacks.set(to);

        // Check if the king needs a special capture
        if (king.intersects(BitSetPosition.specialKingCells)) {

            int kingPosition = king.nextSetBit(0);

            // If the king is in the castle, he must be
            // surrounded by 4 black soldiers
            if (kingPosition == BitSetPosition.E5.ordinal()) {

                if (blacks.andResult(BitSetPosition.kingSurrounded).cardinality() == 4)
                    specialCaptures.set(BitSetPosition.E5.ordinal());

            } else  {

                B2PBitSet result = new B2PBitSet(IState.boardDimension);

                switch (kingPosition) {

                    // BitSetPosition.E4.ordinal() = 31
                    case 31:

                        result = blacks.andResult(BitSetPosition.kingInE4Surrounded);
                        break;

                    // BitSetPosition.D5.ordinal() = 39
                    case 39:

                        result = blacks.andResult(BitSetPosition.kingInD5Surrounded);
                        break;

                    // BitSetPosition.E6.ordinal() = 49
                    case 49:

                        result = blacks.andResult(BitSetPosition.kingInE6Surrounded);
                        break;

                    // BitSetPosition.F5.ordinal() = 41
                    case 41:

                        result = blacks.andResult(BitSetPosition.kingInF5Surrounded);
                        break;

                }

                if (result.cardinality() == 3)
                    specialCaptures.set(kingPosition);

            }

        } else {
            whites.or(king);
        }

        // Normal captures
        B2PBitSet captures = getNormalCaptures(to, blacks, whites);
        captures.or(specialCaptures);
        return captures;

    }

    private static B2PBitSet getCapturesForWhite(int from, int to, IState state) {

        B2PBitSet blacks = state.getBlackPawns().clone();
        B2PBitSet whites = state.getWhitePawns().clone();
        whites.or(state.getKing());
        whites.clear(from);

        return getNormalCaptures(to, whites, blacks);

    }

    private static B2PBitSet getNormalCaptures(int position, BitSet attack, BitSet defense) {

        B2PBitSet captured = new B2PBitSet(IState.boardDimension);

        //Check UP
        int oneUpCell = position - 9;
        if (oneUpCell > 0 && defense.get(oneUpCell)) {

            // If the defense pawn isn't in the first row
            // to be captured it can:
            //   - have an attack pawn above
            //   - have the castle above
            //   - be inside of a camp
            if (oneUpCell > 8) {

                int twoUpCell = oneUpCell - 9;
                if (attack.get(twoUpCell) || BitSetPosition.obstacles.get(twoUpCell)) {
                    captured.set(oneUpCell);
                }

            }

        }

        //Check LEFT
        int oneLeftCell = position - 1;
        if (oneLeftCell > 0 && defense.get(oneLeftCell)) {

            // If the defense pawn isn't in the first column
            // to be captured it can:
            //   - have an attack pawn on the left
            //   - have the castle on the left
            //   - be inside of a camp
            if (oneLeftCell % 9 != 0) {

                int twoLeftCell = oneLeftCell - 1;
                if (attack.get(twoLeftCell) || BitSetPosition.obstacles.get(twoLeftCell)) {
                    captured.set(oneLeftCell);
                }

            }

        }

        //Check DOWN
        int oneDownCell = position + 9;
        if (oneDownCell < IState.boardDimension && defense.get(oneDownCell)) {

            // If the defense pawn isn't in the last row
            // to be captured it can:
            //   - have an attack pawn below
            //   - have the castle below
            //   - be inside of a camp
            if (oneDownCell < IState.boardDimension - 8) {

                int twoDownCell = oneDownCell + 9;
                if (attack.get(twoDownCell) || BitSetPosition.obstacles.get(twoDownCell)) {
                    captured.set(oneDownCell);
                }

            }

        }

        //Check RIGHT
        int oneRightCell = position + 1;
        if (oneRightCell > 0 && defense.get(oneRightCell)) {

            // If the defense pawn isn't in the last column
            // to be captured it can:
            //   - have an attack pawn on the right
            //   - have the castle on the right
            //   - be inside of a camp
            if (oneRightCell % 9 != 8) {

                int twoRightCell = oneRightCell + 1;
                if (attack.get(twoRightCell) || BitSetPosition.obstacles.get(twoRightCell)) {
                    captured.set(oneRightCell);
                }

            }

        }

        return captured;
    }

    public static int kingEscapesInOneMove(IState state) {

        List<IAction> moves = getMovesForPawn(state.getKing().nextSetBit(0), state);
        int escapes = 0;

        // Check if one of the escape cells is in our reach
        for (IAction move : moves) {
            if (BitSetPosition.escapeHashSet.contains(move.getTo()))
                escapes++;
        }

        return escapes;

    }

    public static boolean kingHasMoreThanOneEscapePath(IState state) {

        int escapes;

        // Check if one of the escape cells is in our reach
        for (IAction move : state.getAvailableKingMoves()) {

            //
            escapes = 0;

            //
            for (IAction newMove : state.simulateMove(move).getAvailableKingMoves()) {

                if (BitSetPosition.escapeHashSet.contains(newMove.getTo()))
                    escapes++;

            }

            if (escapes > 1)
                return true;

        }

        return false;

    }

    public static int positionWeights(IState state) {

            final int WHITE_PAWNS_MULTIPLIER = 1;
            final int BLACK_PAWNS_MULTIPLIER = 1;

            int sumOfWeights = 0;
            B2PBitSet whiteMask = state.getWhitePawns().clone();
            B2PBitSet blackMask = state.getBlackPawns().clone();

            for (int i = whiteMask.nextSetBit(0); i >= 0; i = whiteMask.nextSetBit(i + 1)) {
                sumOfWeights += WHITE_PAWNS_MULTIPLIER * (BitSetPosition.whitePawnCellWeight[i] /*+ BitSetPosition.kingBuff[quadrant][i]*/);
            }

            for (int i = blackMask.nextSetBit(0); i >= 0; i = blackMask.nextSetBit(i + 1)) {
                sumOfWeights -= BLACK_PAWNS_MULTIPLIER * (BitSetPosition.blackPawnCellWeight[i] /*+ BitSetPosition.kingBuff[quadrant][i]*/);
            }

            return sumOfWeights;

    }

    public static int whiteCellInStrategicPosition(IState state) {
        return state.getWhitePawns().andResult(BitSetPosition.whiteEarlyGameStrategicCells).cardinality();
    }

    public static int kingStatus(IState state) {
        //
        B2PBitSet king = state.getKing().clone();
        B2PBitSet blacks = state.getBlackPawns().clone();
        B2PBitSet whites = state.getWhitePawns().clone();

        // Special capture needed
        if (king.intersects(BitSetPosition.specialKingCells)) {

            // The king is in the castle:
            // check how many allied and enemy
            // pawns are surrounding it.
            if (king.equals(BitSetPosition.castle)) {

                blacks.and(BitSetPosition.kingSurrounded);
                whites.and(BitSetPosition.kingSurrounded);

                return blacks.cardinality() - whites.cardinality();

            } else {

                int menace = 0;
                int kingPos = king.nextSetBit(0);

                if (blacks.get(kingPos - 1))
                    menace++;

                else if (whites.get(kingPos - 1))
                    menace--;

                if (blacks.get(kingPos + 1))
                    menace++;

                else if (whites.get(kingPos - 1))
                    menace--;

                if (blacks.get(kingPos - 9))
                    menace++;

                else if (whites.get(kingPos - 9))
                    menace--;

                if (blacks.get(kingPos + 9))
                    menace++;

                else if (whites.get(kingPos - 9))
                    menace--;

                return menace;

            }

        } else {

            int menace = 0;
            int kingPos = king.nextSetBit(0);

            if (kingPos % 9 != 0) {

                if (whites.get(kingPos - 1))
                    menace--;
                else if (blacks.get(kingPos - 1) || BitSetPosition.camps.get(kingPos - 1))
                    menace++;

            }

            if (kingPos % 9 != 8) {

                if (whites.get(kingPos + 1))
                    menace--;
                else if (blacks.get(kingPos + 1) || BitSetPosition.camps.get(kingPos + 1))
                    menace++;

            }

            if (kingPos > 9) {

                if (whites.get(kingPos - 9))
                    menace--;
                else if (blacks.get(kingPos - 9) || BitSetPosition.camps.get(kingPos - 9))
                    menace++;

            }

            if (kingPos < 72) {

                if (whites.get(kingPos + 9))
                    menace--;
                else if (blacks.get(kingPos + 9) || BitSetPosition.camps.get(kingPos + 9))
                    menace++;

            }

            return menace * 2;

        }

    }

    public static int kingMoves(IState state) {
        return state.getAvailableKingMoves().size();
    }

    public static int blackPawnsOutOfCamps(IState state) {
        return 16 - state.getBlackPawns().andResult(BitSetPosition.camps).cardinality();
    }

    public static int diagonalBlackCouples(IState state) {

        //
        B2PBitSet blacks = state.getBlackPawns().clone();
        int iColumn, iRow;
        int result = 0;

        for (int i = blacks.nextSetBit(0); i >= 0; i = blacks.nextSetBit(i + 1)) {

            iColumn = i % 9;
            iRow = i / 9;

            // Up Left
            if (iColumn > 0 && iRow > 0) {
                if(blacks.get(i - 9 - 1)) {
                    result++;
                }
            }

            // Up Right
            if (iColumn < 8 && iRow > 0) {
                if(blacks.get(i - 9 + 1)) {
                    result++;
                }
            }

            // Down Left
            if (iColumn > 0 && iRow < 8) {
                if(blacks.get(i + 9 - 1)) {
                    result++;
                }
            }

            // Down Right
            if (iColumn < 8 && iRow < 8) {
                if(blacks.get(i + 9 + 1))
                    result++;
            }
        }

        return result >> 1;
    }

    public static int whitePawnsAdjacentKing(IState state) {

        //
        B2PBitSet king = state.getKing().clone();
        B2PBitSet whites = state.getWhitePawns().clone();
        int kingPosition = king.nextSetBit(0);
        int kingColumn = kingPosition % 9;
        int result = 0;

        // Up
        if (kingPosition > 9) {
            if (whites.get(kingPosition - 9)) {
                result++;
            }
        }

        // Down
        if (kingPosition < 72) {
            if(whites.get(kingPosition + 9)) {
                result++;
            }
        }

        // Left
        if(kingColumn > 0) {
            if(whites.get(kingPosition - 1)) {
                result++;
            }
        }

        // Right
        if(kingColumn < 8) {
            if (whites.get(kingPosition + 1)) {
                result++;
            }
        }

        if(result == 1 || result == 3)
            return 1;

        if(result == 2)
            return result;

        return 0;
    }

    public static int dangerToKing(IState state) {

        //
        B2PBitSet king = state.getKing().clone();
        B2PBitSet blacks = state.getBlackPawns().clone();

        // Special capture needed
        if (king.intersects(BitSetPosition.specialKingCells)) {

            if (king.equals(BitSetPosition.castle)) {

                blacks.and(BitSetPosition.kingSurrounded);
                return blacks.cardinality();

            } else {

                int menace = 0;
                int kingPos = king.nextSetBit(0);

                if (blacks.get(kingPos - 1))
                    menace++;

                if (blacks.get(kingPos + 1))
                    menace++;

                if (blacks.get(kingPos - 9))
                    menace++;

                if (blacks.get(kingPos + 9))
                    menace++;

                return menace;

            }

        } else {

            int menace = 0;
            int kingPos = king.nextSetBit(0);

            if (kingPos % 9 != 0 && (blacks.get(kingPos - 1) || BitSetPosition.camps.get(kingPos - 1)))
                menace++;

            if (kingPos % 9 != 8 && (blacks.get(kingPos + 1) || BitSetPosition.camps.get(kingPos + 1)))
                menace++;

            if (kingPos > 9 && (blacks.get(kingPos - 9) || BitSetPosition.camps.get(kingPos - 9)))
                menace++;

            if (kingPos < 72 && (blacks.get(kingPos + 9) || BitSetPosition.camps.get(kingPos + 9)))
                menace++;

            return menace * 2;

        }
    }

    public static int movesNeededForKingEscape(IState state) {
        return movesNeededForKingEscape_rec(state, 1);
    }

    private static int movesNeededForKingEscape_rec(IState state, int step) {

        // Cutoff if we've searched already enough
        if (step > 3)
            return 50;

        if (state.getKing().intersects(BitSetPosition.escape))
            return step;

        // King moves
        List<IAction> moves = getMovesForPawn(state.getKing().nextSetBit(0), state);

        // Check if one of the escape cells is in our reach
        for (IAction move : moves) {
            if (BitSetPosition.escapeHashSet.contains(move.getTo()))
                return step;
        }

        // Needed for performMove
        // This way, it'll actually move the king around
        state.setTurn(Turn.WHITE);

        // Perform iteratively all the moves the king has
        // and check how long it takes us to get to an escape cell
        int shortestEscape = 50;
        for (IAction move : moves) {

            IState newState = state.clone();
            newState.performMove(move);

            int movesFromThisPosition = movesNeededForKingEscape_rec(newState, step+1);
            if (movesFromThisPosition < shortestEscape) {
                shortestEscape = movesFromThisPosition;
            }

        }

        return shortestEscape;

    }


}
