package chessknights.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessKnightsStateTest {

    @Test
    void testOneArgConstructor_InvalidArg() {
        assertThrows(IllegalArgumentException.class, () -> new ChessKnightsState(null));
        assertThrows(IllegalArgumentException.class, () -> new ChessKnightsState(new int[][] {
                {1, 1},
                {1, 0}})
        );
        assertThrows(IllegalArgumentException.class, () -> new ChessKnightsState(new int[][] {
                {0,0,0,0,0,0,0,2},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,4,0,0,0,0,0},
                {1,0,0,0,0,0,0,0}})
        );
        assertThrows(IllegalArgumentException.class, () -> new ChessKnightsState(new int[][] {
                {0,0,0,0,0,0,0,2,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,4,0,0,0,0,0,0,0},
                {1,0,0,0,0,0,0,0,0,0}})
        );
    }

    @Test
    void testOneArgConstructor_ValidArg() {
        int[][] a = new int[][] {
                {0,0,0,0,0,0,0,2},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {1,0,0,0,0,0,0,0}};
        ChessKnightsState state = new ChessKnightsState(a);
        ChessKnightsState clone = state.clone();
        assertArrayEquals(new Knights[][] {
                {Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.BLACK},
                {Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY},
                {Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY},
                {Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY},
                {Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY},
                {Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY},
                {Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY},
                {Knights.WHITE,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY},}, state.getTray());
        assertNotEquals(new int[][] {
                {3,0,0,0,0,0,0,2},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {1,0,0,0,0,0,0,0}},new Knights[][] {
                {Knights.USED,Knights.USED,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.BLACK},
                {Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY},
                {Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY},
                {Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY},
                {Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY},
                {Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY},
                {Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY},
                {Knights.WHITE,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY,Knights.EMPTY},});
        assertThrows(IllegalArgumentException.class,()-> new ChessKnightsState(new int[][] {
                {0,0,0,0,0,0,0,2},
                {0,0,0,4,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {1,0,0,0,0,0,0,0}}));
    }

    @Test
    void isFinished() {
        assertFalse(new ChessKnightsState().isFinished(1));
        assertTrue(new ChessKnightsState(new int[][] {
                {0,0,0,0,0,0,0,2},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,3,0,0,0,0,0,0},
                {0,0,3,0,0,0,0,0},
                {1,0,0,0,0,0,0,0}}).isFinished(1));
        assertFalse(new ChessKnightsState(new int[][] {
                {0,0,0,0,0,0,0,2},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,3,0,0,0,0,0,0},
                {0,0,3,0,0,0,0,0},
                {1,0,0,0,0,0,0,0}}).isFinished(2));
        assertTrue(new ChessKnightsState(new int[][] {
                {0,0,0,0,0,3,0,3},
                {0,0,0,0,3,0,0,0},
                {0,0,0,0,0,0,2,0},
                {0,0,0,0,3,0,0,0},
                {0,0,0,0,0,3,0,3},
                {0,3,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {1,0,0,0,0,0,0,0}}).isFinished(2));
    }

    @Test
    void movesLeft() {
        assertEquals(new ChessKnightsState(new int[][] {
                {0,0,0,0,0,3,0,3},
                {0,0,0,0,3,0,0,0},
                {0,0,0,0,0,0,2,0},
                {0,0,0,0,3,0,0,0},
                {0,0,0,0,0,3,0,3},
                {0,3,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {1,0,0,0,0,0,0,0}}).movesLeft(7,0),1);
        assertEquals(new ChessKnightsState(new int[][] {
                {0,0,0,0,0,0,0,3},
                {0,0,0,0,3,0,0,0},
                {0,0,0,0,0,0,2,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,1,0,0},
                {0,3,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0}}).movesLeft(2,6),3);
        assertNotEquals(new ChessKnightsState(new int[][] {
                {0,0,0,0,0,3,0,3},
                {0,0,0,0,3,0,0,0},
                {0,0,0,0,0,0,2,0},
                {0,0,0,0,3,0,0,0},
                {0,0,0,0,0,3,0,3},
                {0,3,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {1,0,0,0,0,0,0,0}}).movesLeft(7,0),5);
    }

    @Test
    void isValidMove() {
        ChessKnightsState state = new ChessKnightsState(new int[][] {
                {0,0,0,0,0,0,0,2},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,3,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {1,0,0,0,0,0,0,0}});
        assertTrue(state.isValidMove(6,2,1));
        assertFalse(state.isValidMove(6,2,2));
        assertTrue(state.isValidMove(1,5,2));
        assertFalse(state.isValidMove(5,1,1));
    }

    @Test
    void moveTo() {
        ChessKnightsState state = new ChessKnightsState();
        state.moveTo(6,2,1);
        assertEquals(state.getWhiteRow(),6); //Should be equal if successfully moved
        assertEquals(state.getWhiteCol(),2); //Should be equal if successfully moved
        assertEquals(state.getBlackRow(),0);
        assertEquals(state.getBlackCol(),7);
        state.moveTo(2,6,2);// Able to move
        assertNotEquals(state.getBlackRow(),0);
        assertNotEquals(state.getBlackCol(),7);
//        System.out.println(state);//displaying array
    }
}