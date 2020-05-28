package chessknights.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KnightsTest {

    @Test
    void testOf() {
        assertEquals(Knights.of(0),Knights.EMPTY);
        assertEquals(Knights.of(1),Knights.WHITE);
        assertEquals(Knights.of(2),Knights.BLACK);
        assertEquals(Knights.of(3),Knights.USED);
        assertNotEquals(Knights.of(2),Knights.USED);
        assertThrows(IllegalArgumentException.class,() -> Knights.of(4));
    }

    @Test
    void testGetValue() {
        assertEquals(Knights.EMPTY.getValue(),0);
        assertEquals(Knights.WHITE.getValue(),1);
        assertEquals(Knights.BLACK.getValue(),2);
        assertEquals(Knights.USED.getValue(),3);
        assertNotEquals(Knights.EMPTY.getValue(),1);
    }
}