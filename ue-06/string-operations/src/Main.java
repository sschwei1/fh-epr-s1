import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
    public void testReverseWords() {

    }

    @Test
    public void testIsPalindrome() {

    }

    public static int countWords(String text) {
        return getWords(text).length;
    }

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

    public static boolean isPalindrome(String text) {
        for(int i = 0; i < text.length() / 2; i++) {
            if(text.charAt(i) != text.charAt(text.length()-i-1)) {
                return false;
            }
        }

        return true;
    }

    protected static String[] getWords(String text) {
        String trimmedText = text
                .replaceAll("[^\\wÄÖÜäöüß&\\s]", "")
                .trim();

        if(trimmedText.isEmpty()) {
            return new String[0];
        }

        return trimmedText.split("[^\\wÄÖÜäöüß&]+");
    }

    protected static String[] getWordsAndSpaces(String text) {
        if(text.isEmpty()) {
            return new String[0];
        }

        return text.split("(?<=\\W)|(?=\\W)");
    }

    protected static String reverseString(String text) {
        StringBuilder sb = new StringBuilder(text);
        return sb.reverse().toString();
    }
}