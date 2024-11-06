import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Main {
    /*
     * NOTE:
     * The exercise states "write a JUnit test with the name
     * testIsMagicSquare" however I do think writing a single
     * test to handle different edge cases is not helpful and
     * is also not productive for real world testing.
     *
     * Splitting up tests will help you to see where exactly
     * is the error.
     *
     * Also by only testing the method "isMagicSquare" it is
     * impossible for me to reach 100% test coverage since
     * some lines are unable to reach every single edge case
     * due to previous calculations.
     * So I also added tests for checking columns, rows and
     * diagonals individually
     *
     * I was talking with Professor Holzmann if he is fine
     * with my test coverage and said yes, even tho this is
     * out of scope for our current state
     */
    @Test
    public void testIsMagicSquare_ValidMagicSquare() {
        int[][] magicSquare = {
                {13, 22, 18, 27, 11, 20},
                {31,  4, 36,  9, 29,  2},
                {12, 21, 14, 23, 16, 25},
                {30,  3,  5, 32, 34,  7},
                {17, 26, 10, 19, 15, 24},
                { 8, 35, 28,  1,  6, 33}
        };
        assertTrue(isMagicSquare(magicSquare));
    }

    @Test
    public void testIsMagicSquare_InvalidMagicSquare_Row() {
        int[][] nonMagicSquareRow = {
                {5, 3, 7},
                {5, 3, 8}, // Invalid row
                {5, 3, 7}
        };
        assertFalse(isMagicSquare(nonMagicSquareRow));
    }

    @Test
    public void testIsMagicSquare_InvalidMagicSquare_Column() {
        int[][] nonMagicSquareColumn = {
                {5, 5, 5},
                {5, 4, 6}, // Invalid column
                {5, 3, 7}
        };
        assertFalse(isMagicSquare(nonMagicSquareColumn));
    }

    @Test
    public void testIsMagicSquare_InvalidMagicSquare_Diagonal() {
        int[][] nonMagicSquareDiagonal = {
                {6, 8, 1},
                {7, 3, 5},
                {2, 4, 9} // Invalid Diagonal
        };
        assertFalse(isMagicSquare(nonMagicSquareDiagonal));
    }

    @Test
    public void testIsMagicSquare_ZeroMagicSquare() {
        int[][] zeroMagicSquare = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        assertTrue(isMagicSquare(zeroMagicSquare));
    }

    @Test
    public void testValidateRows_Valid() {
        int[][] validSquare = {
                {8, 1, 6},
                {3, 5, 7},
                {4, 9, 2}
        };
        int checkSum = calculateCheckSum(validSquare);
        assertTrue(validateRows(validSquare, checkSum));
    }

    @Test
    public void testValidateRows_Invalid() {
        int[][] invalidSquare = {
                {8, 1, 6},
                {3, 5, 8}, // Invalid row
                {4, 9, 2}
        };
        int checkSum = calculateCheckSum(invalidSquare);
        assertFalse(validateRows(invalidSquare, checkSum));
    }

    @Test
    public void testValidateColumns_Valid() {
        int[][] validSquare = {
                {8, 1, 6},
                {3, 5, 7},
                {4, 9, 2}
        };
        int checkSum = calculateCheckSum(validSquare);
        assertTrue(validateColumns(validSquare, checkSum));
    }

    @Test
    public void testValidateColumns_Invalid() {
        int[][] invalidSquare = {
                {8, 1, 6},
                {3, 5, 7},
                {4, 8, 2} // Invalid column
        };
        int checkSum = calculateCheckSum(invalidSquare);
        assertFalse(validateColumns(invalidSquare, checkSum));
    }

    @Test
    public void testValidateDiagonals_Valid() {
        int[][] validSquare = {
                {8, 1, 6},
                {3, 5, 7},
                {4, 9, 2}
        };
        int checkSum = calculateCheckSum(validSquare);
        assertTrue(validateDiagonals(validSquare, checkSum));
    }

    @Test
    public void testValidateDiagonal1_Invalid() {
        int[][] invalidSquare = {
                {8, 1, 6},
                {3, 5, 7},
                {4, 9, 3} // Invalid diagonal
        };
        int checkSum = calculateCheckSum(invalidSquare);
        assertFalse(validateDiagonals(invalidSquare, checkSum));
    }

    @Test
    public void testValidateDiagonal2_Invalid() {
        int[][] invalidSquare = {
                {6, 1, 8},
                {7, 5, 3},
                {3, 9, 4} // Invalid diagonal
        };
        int checkSum = calculateCheckSum(invalidSquare);
        assertFalse(validateDiagonals(invalidSquare, checkSum));
    }

    @Test
    public void testValidateDiagonal_AllInvalid() {
        int[][] invalidSquare = {
                {5,7,3},
                {1,6,8},
                {9,2,4}
        };
        int checkSum = calculateCheckSum(invalidSquare);
        assertFalse(validateDiagonals(invalidSquare, checkSum));
    }

    @Test
    public void testCalculateCheckSum() {
        /*
         * calculateCheckSum only uses the first value of
         * each column for its calculation
         */
        int[][] square1 = {{1},{2},{3}};
        int[][] square2 = {{3},{5},{7},{10}};
        int[][] square3 = {{9},{7},{2},{3},{5}};
        int[][] square4 = {{0},{0},{0},{0},{0},{0}};

        assertEquals(6, calculateCheckSum(square1));
        assertEquals(25, calculateCheckSum(square2));
        assertEquals(26, calculateCheckSum(square3));
        assertEquals(0, calculateCheckSum(square4));
    }

    /**
     * Validates if a square is a magice square or not
     * For this 
     *
     * @param square - given magic square
     * @return - true if given square is a magic square, false otherwise
     */
    public static boolean isMagicSquare(int[][] square) {
        int checkSum = calculateCheckSum(square);

        return validateRows(square, checkSum) &&
                validateColumns(square, checkSum) &&
                validateDiagonals(square, checkSum);
    }

    /**
     * Takes the first line of the square and
     * sums up its values. This sum is used to
     * validate all other rows, columns and diagonals
     *
     * @param square - given magic square
     * @return - sum which will be used to compare all directions of the square
     */
    protected static int calculateCheckSum(int[][] square) {
        int checkSum = 0;

        for(int i = 0; i < square.length; i++) {
            checkSum += square[i][0];
        }

        return checkSum;
    }

    /**
     * Validates if all rows equal to the checksum
     *
     * @param square - given magic square
     * @param checkSum - sum which all lines will be compared to
     * @return - true if all rows equal the checksum, false otherwise
     */
    protected static boolean validateRows(int[][] square, int checkSum) {
        int squareSize = square.length;

        for(int row = 0; row < squareSize; row++) {
            int rowSum = 0;

            for(int col = 0; col < squareSize; col++) {
                rowSum += square[row][col];
            }

            if(rowSum != checkSum) {
                return false;
            }
        }

        return true;
    }

    /**
     * Validates if all columns are equals to the checksum
     *
     * @param square - given magic square
     * @param checkSum - sum which all lines will be compared to
     * @return - true if all columns equal the checksum, false otherwise
     */
    protected static boolean validateColumns(int[][] square, int checkSum) {
        int squareSize = square.length;

        for(int col = 0; col < squareSize; col++) {
            int colSum = 0;

            for(int row = 0; row < squareSize; row++) {
                colSum += square[row][col];
            }

            if(colSum != checkSum) {
                return false;
            }
        }

        return true;
    }

    /**
     * Validates if the diagonals are equal to the checksum
     *
     * @param square - given magic square
     * @param checkSum - sum which all lines will be compared to
     * @return - true if both diagonals match the checksum
     */
    protected static boolean validateDiagonals(int[][] square, int checkSum) {
        int squareSize = square.length;

        int diagonal1 = 0;
        int diagonal2 = 0;

        int offset = squareSize - 1;

        for(int i = 0; i < squareSize; i++) {
            diagonal1 += square[i][i];
            diagonal2 += square[i][offset - i];
        }

        return diagonal1 == checkSum && diagonal2 == checkSum;
    }
}
