import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Main {
    /*
     * If you don't want to read, skip ahead 60 lines
     *
     * There are multiple ways to test if our sinh implementation is correct
     * or not, I present 3 different solutions here:
     * - ExactValue
     * - ToPrecision
     * - WithExpectedValue
     *
     * I have chosen to use the 2nd method since it makes most sense. I will go
     * on and explain advantages/disadvantages possible use cases for all the
     * methods now, feel free to skip that part.
     *
     * Test exact value match
     *   Here is tested if our sinh implementation does match the exact value of
     *   the Math.sinh method
     *
     *   This test does make sense if we aim for 100% compatibility and precision,
     *   however is there a reason to not user the Math.sinh implementation then?
     *   I honestly don't know.
     *
     *   However, in our case I can say it does not make any sense to use this testing
     *   method since we know from the exercise document, we are off by a factor
     *   (compared to how many comma values there are, we are actually off by quiet a bit)
     *
     *
     * Test to specific precision
     *   Here is tested if our sinh implementation is at maximum a defined delta off
     *   of Math.sinh
     *
     *   This approach does make most sense, in case the implementation aims for maybe
     *   a faster or less precise approach. It is also more realistic since it is very hard
     *   to match 2 double values exactly.
     *
     *   I have chosen this approach since in my opinion this is not only a perfect
     *   solution for our case, I even think this is the best solution overall when
     *   working with testing double values.
     *
     *   Quick sidenote:
     *   When trying to use assertEquals(double val1, double val2), my IDEA even tells me
     *   that it is deprecated, and you should use
     *   assertEquals(double val1, double val2, double delta) instead
     *
     * Test with expected value
     *   Here the calculated values are compared to a hardcoded result.
     *
     *   This test just doesn't make sense in so many ways.
     *   Which "expected" values will you use?
     *   Math.sinh? same problem as first testing scenario
     *   of our sinh implementation? doesn't make sense either, we change the precision for example
     *   and would need to adapt our tests everytime we change anything.
     *   We would also need to run our code each time we want to write new tests to see
     *   the results of our implementation
     *
     *   In my opinion a good approach if you are working with int values, but absolutely
     *   terrible if you are working with double
     */
    @Test
    public void testSinh_ToPrecision() {
        // chosen 10e-9 since it does match the given precision of the exercise
        double delta = 10e-09;

        for(int i = 0; i <= 10; i++) {
            double value = i * 0.1;
            assertEquals(Math.sinh(value), sinh(value), delta);
        }
    }

    public static void main(String[] args) {
        /*
         * Print Head of the table
         */
        Out.println("x    sinh(x)       Math.sinh(x)  Difference  ");
        Out.println("---------------------------------------------");

        /*
         * Loop from 0 to 10 and multiplying i * 0.1 to calculate and compare
         * our sinh implementation with the Math.sinh method
         *
         * At the end print the line and format floats to contain 10 digits
         *
         * Another possible way to iterate from 0 to 1 and add 0.1 each iteration,
         * however 0.1 in the memory is not equal to 0.1 as we know it and differs
         * somewhere around the 20th comma digit and this offset would add up in the
         * loop
         * Another problem we could face, 10 * 0.1 could be slightly above 1, and we
         * would need to handle a slight offset like this:
         * i < 1 + maxOffset  // where maxOffset would be 10e-10 or a similar small value
         *
         * By iterating with an int type and calculating the value at the beginning of
         * the loop, we do not face these problems
         */
        for(int i = 0; i <= 10; i++) {
            double mult = i * 0.1;

            double customSinh = sinh(mult);
            double mathSinh = Math.sinh(mult);
            double diff = customSinh - mathSinh;

            if(diff < 0) {
                diff *= -1;
            }

            Out.print(String.format("%.1f", mult) + "  ");
            Out.print(String.format("%.10f", customSinh) + "  ");
            Out.print(String.format("%.10f", mathSinh) + "  ");
            Out.println(String.format("%.10f", diff));
        }
    }

    protected static double sinh(double x) {
        double result = 0;
        double term = x;
        int count = 1;

        /*
         * Calculate and add new term each iteration
         * Break the loop when the term reaches a value of less the 10^-6
         */
        do {
            result += term;
            count += 2;
            term = calculatePower(x, count) / calculateFactorial(count);
        } while(term >= 1e-6);

        /*
         * Since our loop broke, we need to add the last term which is < 10^-6
         * to our result
         *
         * Technically, the exercise document was reworked and this wasn't
         * needed in the first version, but our professor had a small mistake
         * in his calculation, he didn't want to change the value table, so he changed
         * up the text
         */
        result += term;

        return result;
    }

    /**
     * Calculate the power of a given number
     *
     * @param base - Base number which should be powered up
     * @param power - Power to which the number should be calculated
     * @return Given power of the given base
     */
    protected static double calculatePower(double base, int power) {
        double result = 1;

        while(power > 0) {
            result *= base;
            power--;
        }

        return result;
    }

    /**
     * Calculates factorial of given number
     * This method will return 1 for negative number and will not handle number
     * overflows
     *
     * @param num - number which the factorial should be calculated of
     * @return factorial of given number
     */
    protected static int calculateFactorial(int num) {
        int result = 1;

        while(num > 1) {
            result *= num;
            num--;
        }

        return result;
    }
}