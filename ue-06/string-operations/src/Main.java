import org.junit.Test;

import static org.junit.Assert.*;

public class Main {
    @Test
    public void testCountWords() {
        assertEquals(0, countWords(""));
        assertEquals(4, countWords("  Hello \\ß\\sfda sadff World "));
        assertEquals(3, countWords(" abcd test 1234 ! ! !"));
        assertEquals(1, countWords("abcd!!!abcd"));
        assertEquals(1, countWords("alf\\!#$%*&&*@&$*%((@((((((((%(@))][]}{}{sfdsadf"));
        assertEquals(4, countWords("Hi!! How are you?!?:DxD8)a"));

        assertEquals(0, countWords(""));
        assertEquals(1, countWords("Hello"));
        assertEquals(4, countWords("Hello /&/7/ Test (DKd)"));
        assertEquals(4, countWords("Mart and interconnected living"));
        assertEquals(5, countWords("Ist das ein normaler Satz"));
        assertEquals(15, countWords("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa."));
        assertEquals(8, countWords("a b    c  d     (/&§      e f g"));

    }

    @Test
    public void testReverseWordsInSentence() {
        assertEquals("", reverseWords(""));
        assertEquals("  olleH \\ß\\adfs ffdas dlroW ", reverseWords("  Hello \\ß\\sfda sadff World "));
        assertEquals(" dcba tset 4321 ! ! !", reverseWords(" abcd test 1234 ! ! !"));
        assertEquals("dcba!!!dcba", reverseWords("abcd!!!abcd"));
        assertEquals("fla\\!#$%*&&*@&$*%((@((((((((%(@))][]}{}{fdasdfs", reverseWords("alf\\!#$%*&&*@&$*%((@((((((((%(@))][]}{}{sfdsadf"));
        assertEquals("iH!! woH era uoy?!?:8DxD)a", reverseWords("Hi!! How are you?!?:DxD8)a"));

        assertEquals("", reverseWords(""));
        assertEquals("olleH", reverseWords("Hello"));
        assertEquals("olleH /&/7/ tseT (dKD)", reverseWords("Hello /&/7/ Test (DKd)"));
        assertEquals("traM dna detcennocretni gnivil", reverseWords("Mart and interconnected living"));
        assertEquals("tsI sad nie relamron ztaS", reverseWords("Ist das ein normaler Satz"));
        assertEquals("meroL muspi rolod tis tema, reutetcesnoc gnicsipida tile. naeneA odommoc alugil tege rolod. naeneA assam.", reverseWords("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa."));
        assertEquals("a b    c  d     (/&§      e f g", reverseWords("a b    c  d     (/&§      e f g"));
    }

    @Test
    public void testIsPalindrome() {
        assertFalse(isPalindrome("hello"));
        assertFalse(isPalindrome("HelloWorld"));
        assertFalse(isPalindrome("12345"));

        assertTrue(isPalindrome("a"));
        assertTrue(isPalindrome("racecar"));
        assertTrue(isPalindrome("RaceCar"));
        assertTrue(isPalindrome(""));
        assertTrue(isPalindrome("     "));
        assertTrue(isPalindrome("12321"));

        assertTrue(isPalindrome("A man, a plan, a canal, Panama"));
        assertTrue(isPalindrome("Step on no pets!"));
        assertTrue(isPalindrome("Mr. Owl ate my metal worm."));
        assertTrue(isPalindrome("Do geese see God?"));
        assertTrue(isPalindrome("Yo, Banana Boy!"));
        assertTrue(isPalindrome("Able was I, I saw Elba."));
        assertTrue(isPalindrome("No lemon, no melon!"));
        assertTrue(isPalindrome("Eva, can I see bees in a cave?"));
        assertTrue(isPalindrome("Was it a car or a cat I saw? Was it a car or a cat I saw?"));

        assertTrue(isPalindrome("A1B2C2B1A"));
        assertTrue(isPalindrome("9a8b7c7b8a9"));
        assertTrue(isPalindrome("11.22.33..22.11"));
        assertTrue(isPalindrome("abcdedcbaabcdedcba"));
        assertTrue(isPalindrome("1234554321racecar1234554321"));
    }

    public static int countWords(String text) {
        return getWords(text).length;
    }

    /*
     * Reverse all words in a sentence
     *
     * First the text is split by spaces and special characters, then each word is reversed
     * and the text is rebuilt
     *
     * This ensures, that all spaces and special characters are kept in their original
     * position and only letters will be reversed
     */
    public static String reverseWords(String text) {
        String[] splitText = getWordsAndSpaces(text);

        for(int i = 0; i < splitText.length; i++) {
            if(splitText[i].isEmpty() || splitText[i].length() == 1) {
                continue;
            }

            splitText[i] = reverseString(splitText[i]);
        }

        return String.join("", splitText);
    }

    /*
     * Check if a string is a palindrome
     *
     * First the string is converted to lowercase and filters out special characters,
     * then it is checked if the string is symmetrical.
     *
     * "A man, a plan, a canal, Panama" => is a palindrome even tho spaces
     * and special characters are not symmetrical
     */
    public static boolean isPalindrome(String text) {
        String filteredText = text.toLowerCase().replaceAll("[^\\wÄÖÜäöüß&]", "");

        for(int i = 0; i < filteredText.length() / 2; i++) {
            if(filteredText.charAt(i) != filteredText.charAt(filteredText.length()-i-1)) {
                return false;
            }
        }

        return true;
    }

    /*
     * Split text by spaces characters
     *
     * First special characters are filtered out, then the text is split by spaces
     * this ensures, that special characters are not counted as words (e.g. "abcd , abcd" => 2 words)
     */
    protected static String[] getWords(String text) {
        String trimmedText = text
                .replaceAll("[^\\wÄÖÜäöüß&\\s]", "")
                .trim();

        if(trimmedText.isEmpty()) {
            return new String[0];
        }

        return trimmedText.split("[^\\wÄÖÜäöüß&]+");
    }

    /*
     * Split text by spaces and special characters, but keep them in the result array
     * This is needed, so the string can be rebuilt later
     */
    protected static String[] getWordsAndSpaces(String text) {
        if(text.isEmpty()) {
            return new String[0];
        }

        return text.split("(?<=\\W)|(?=\\W)");
    }

    /*
     * Use StringBuilder to reverse a string
     */
    protected static String reverseString(String text) {
        StringBuilder sb = new StringBuilder(text);
        return sb.reverse().toString();
    }
}