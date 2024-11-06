import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Main {
    @Test
    public void testFact() {
        // Test default cases
        assertEquals(1, fact(1));
        assertEquals(120, fact(5));

        // Testing with values greater Integer.MAX_VALUE
        assertEquals(87178291200L, fact(14));
        assertEquals(355687428096000L, fact(17));

        // Test edge cases
        assertEquals(1, fact(0));
        assertEquals(-1, fact(-10));
        assertEquals(-1, fact(10000));
    }

    /**
     * This code was copied from the moodle platform
     */
    public static long fact(int number) {
        if (number < 0)
            return -1;
        if (number == 0)
            return 1;

        long result = 1;
        for (int i = 2; i <= number; i++) {
            long tmp = result;
            result *= i;
            if (tmp > result) { // overflow
                return -1;
            }
        }
        return result;
    }
}