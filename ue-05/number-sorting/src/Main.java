import org.junit.Test;

import javax.annotation.processing.Generated;

import static org.junit.Assert.assertArrayEquals;

public class Main {
    @Test
    public void testSort() {
        // sort empty array
        int[] expectedArray = {};
        int[] actualArray = {};
        sort(actualArray);
        assertArrayEquals(actualArray, expectedArray);

        // sort array with one element
        expectedArray = new int[] { -30 };
        actualArray = new int[] { -30 };
        sort(actualArray);
        assertArrayEquals(actualArray, expectedArray);

        // sort array with two elements, maximum number left
        expectedArray = new int[] { -2, 500 };
        actualArray = new int[] { 500, -2 };
        sort(actualArray);
        assertArrayEquals(actualArray, expectedArray);

        // sort array with three elements, minimum number in the middle
        expectedArray = new int[] { -10000, -2, 200 };
        actualArray = new int[] { -2, -10000, 200 };
        sort(actualArray);
        assertArrayEquals(actualArray, expectedArray);

        // sort array with equal numbers
        expectedArray = new int[] { 17, 17, 17, 17, 17 };
        actualArray = new int[] { 17, 17, 17, 17, 17 };
        sort(actualArray);
        assertArrayEquals(actualArray, expectedArray);

        // sort sorted array (ascending)
        expectedArray = new int[] { 1, 2, 3, 4, 5, 6 };
        actualArray = new int[] { 1, 2, 3, 4, 5, 6 };
        sort(actualArray);
        assertArrayEquals(actualArray, expectedArray);

        // sort sorted array (descending)
        expectedArray = new int[] { 0, 10, 20, 30, 40, 50, 60, 70 };
        actualArray = new int[] { 70, 60, 50, 40, 30, 20, 10, 0 };
        sort(actualArray);
        assertArrayEquals(actualArray, expectedArray);

        // sort small random array with numbers between 0 and 10000, minimum number right, odd number of elements
        expectedArray = new int[] { 1165, 2208, 3891, 3896, 6128, 7041, 7577, 9080, 9586 };
        actualArray = new int[] { 6128, 3896, 9080, 3891, 9586, 7577, 2208, 7041, 1165 };
        sort(actualArray);
        assertArrayEquals(actualArray, expectedArray);

        // sort small random array with numbers between 0 and 10000, minimum number left, even number of elements
        expectedArray = new int[] { 988, 2352, 2361, 4949, 5133, 5154, 6221, 6340, 9579, 9679 };
        actualArray = new int[] { 988, 6221, 5154, 6340, 9579, 5133, 4949, 2352, 2361, 9679 };
        sort(actualArray);
        assertArrayEquals(actualArray, expectedArray);


        // sort large random array with numbers between -100 and 100, maximum number right
        expectedArray = new int[] { -100, -97, -96, -95, -94, -93, -89, -87, -81, -80, -71, -68, -65, -56, -55, -54,
                -53, -52, -51, -45, -44, -42, -41, -39, -38, -35, -34, -33, -32, -30, -29, -27, -26, -25, -22, -12, -9,
                -7, -4, -3, -2, -1, 0, 1, 2, 3, 5, 6, 9, 10, 11, 12, 13, 14, 15, 17, 18, 19, 22, 23, 26, 27, 28, 29, 32,
                33, 35, 36, 37, 39, 40, 42, 43, 45, 46, 48, 58, 60, 61, 63, 64, 65, 68, 69, 74, 75, 77, 79, 80, 81, 83,
                84, 88, 91, 92, 93, 94, 98, 99, 100 };
        actualArray = new int[] { -53, -97, -95, -41, 13, 83, 98, 42, 75, -52, 45, 58, -96, 23, -71, 80, 12, 10, 9, 60,
                48, -1, 61, 65, 93, 37, 94, -80, -2, 27, 74, -89, 68, -81, 35, 29, -68, -30, -56, 63, 84, 28, -38, 5,
                -7, -4, 99, 18, 3, -32, 14, 32, 1, -54, 17, 46, 40, 91, 92, 69, 77, 19, 88, 26, 81, 6, 39, -33, 36, -55,
                -22, 0, -87, -29, 64, -39, 43, -3, -35, -93, -25, -65, 33, -42, -44, -12, -100, -34, -26, -94, -9, 15,
                22, 2, -45, 79, 11, -27, -51, 100 };
        sort(actualArray);
        assertArrayEquals(actualArray, expectedArray);

        // sort medium size random array with numbers between -100 and -1
        expectedArray = new int[] { -99, -92, -89, -87, -83, -82, -81, -79, -77, -75, -74, -70, -69, -67, -66, -65, -63, -62, -51, -33, -26, -19, -18, -16, -14, -13, -12, -10, -5, -3 };
        actualArray = new int[] { -51, -10, -16, -83, -3, -12, -62, -65, -67, -33, -77, -26, -89, -63, -69, -19, -75,
                -87, -74, -18, -79, -70, -99, -81, -5, -82, -92, -13, -14, -66 };
        sort(actualArray);
        assertArrayEquals(actualArray, expectedArray);
    }

    public static void main(String[] args) {
        /*
         * I am using an IntelliJ project with 2 submodules where each
         * submodule is one of the exercises, however files need to be
         * specified relative to the project directory, not relative to
         * the module directory.
         */
        In.open("./number-sorting/input.txt");
        Out.open("./number-sorting/output.txt");

        int[] row = readRow();
        while(row != null) {
            sort(row);
            print(row);
            row = readRow();
        }

        In.close();
        Out.close();
    }

    /**
     * @param row - row which need to be sorted
     */
    protected static void sort(int[] row) {
        /*
         * Go from 0 to row.length - 1 because we check ahead 1 element.
         * If we ran till row.length we would try to access an element
         * at an index out of bounds.
         */
        for(int i = 0; i < row.length - 1; i++) {

            /*
             * Go from 0 to row.length - 1 - i, so we do not make
             * unnecessary checks for elements already sorted.
             */
            for(int j = 0; j < row.length - 1 - i; j++) {

                /*
                 * If element at index + 1 is bigger than the element
                 * at index, they do not need to be swapped
                 */
                if(row[j+1] >= row[j]) {
                    continue;
                }

                /*
                 * Swap element at index + 1 with element at index
                 */
                int temp = row[j];
                row[j] = row[j+1];
                row[j+1] = temp;
            }
        }
    }

    /**
     * Handles reading rows of int values from the text file.
     * The first value in each rows gives us information about
     * how many values we need to read.
     *
     * @return - numbers in current row as an int[]
     */
    protected static int[] readRow() {
        int numberCount = In.readInt();

        /*
         * Might seem counterintuitive, however In.done()
         * returns if the last read was successful, so basically
         * In.done() shows if we should keep going
         */
        if(!In.done()) {
            return null;
        }

        int[] row = new int[numberCount];

        for(int i = 0; i < numberCount; i++) {
            row[i] = In.readInt();
        }

        return row;
    }

    /**
     * @param row - row which should be printed
     */
    protected static void print(int[] row) {
        StringBuilder sb = new StringBuilder();

        /*
         * add line length as first element
         */
        sb.append(row.length);

        for(int i = 0; i < row.length; i++) {
            sb.append(" ");
            sb.append(row[i]);
        }

        Out.println(sb.toString());
    }
}
