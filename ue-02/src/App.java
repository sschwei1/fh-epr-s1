public class App {
    public static void main(String[] args) {
        Out.print("Eingabe Uhrzeit (hh mm): ");
        int hour = In.readInt();
        int minute = In.readInt();

        printClockText(hour, minute);
        Out.println();
    }

    /**
     * To print the clock time, we need to check if all values
     * are within our boundrys and print "***" otherwise
     * 
     * In the 2nd step it is determined wheter or not we need
     * to print the minute or hour text first. 
     * 
     * @param hour
     * @param minute
     */
    protected static void printClockText(int hour, int minute) {
        if(hour < 1 || hour > 12 || minute < 0 || minute > 59) {
            Out.print("***");
            return;
        }
        
        if(minute % 15 == 0) {
            printMinuteText(minute);
            printHourText(hour, minute);
            return;
        }

        printHourText(hour, minute);
        printMinuteText(minute);
    }

    protected static void printHourText(int hour, int minute) {
        /**
         * if we have half or quarter to the next hour, it needs
         * to be increased by one.
         * The edge case we have is 12 + 1 => 13 however we interpret
         * 13 as "ein Uhr"
         * 
         * We need to use % 12 first, otherwise in the example of
         * (11 + 1) % 12 would return 0 however it should be 12 and by
         * adding the one after we will never have the case of 0 hours
         * and fix our problem
         */
        if(minute == 30 || minute == 45) {
            hour = hour % 12 + 1;
        }

        printNumberString(hour);
        Out.print(" Uhr ");
    }

    protected static void printMinuteText(int minute) {
        printNumberString(minute);

        /**
         * handles printing s
         * e.g.:
         * - 11 01 => elf Uhr ein[s]
         * - 01 21 => ein Uhr einundzwanzig (s not needed in general)
         */
        if(minute == 1) {
            Out.print("s");
        }
    }

    /**
     * Method to print a number between 0-59
     * 
     * It handles printing the single digit and tenth digit number
     * and also handles special cases as well as the connector text
     * "und" for bigger numbers
     * 
     * @param number
     */
    protected static void printNumberString(int number) {
        switch (number) {
            case 11: Out.print("elf");      return;
            case 12: Out.print("zwölf");    return;
            case 16: Out.print("sechzehn"); return;
            case 17: Out.print("siebzehn"); return;

            case 0:  Out.print("punkt ");        return;
            case 15: Out.print("viertel nach "); return;
            case 30: Out.print("halb ");         return;
            case 45: Out.print("dreiviertel ");  return;
        }

        int singleDigitNumber = number % 10;
        int tenthDigitNumber = number / 10;

        printSingleDigitString(singleDigitNumber);

        /**
         * print "und" only for tenthDigits > 1 and numbers % 10 != 0
         * e.g.:
         * - 20 => zwanzig (no "und" needed)
         * - 14 => vierzehn (no "und" needed)
         * - 22 => zwei[und]zwanzig
         */
        if(singleDigitNumber != 0 && tenthDigitNumber > 1) {
            Out.print("und");
        }

        printTenthDigitString(tenthDigitNumber);
    }

    /**
     * Method to print a single digit number
     * 
     * @param singleDigitNumber
     */
    protected static void printSingleDigitString(int singleDigitNumber) {
        switch (singleDigitNumber) {
            case 1: Out.print("ein");    break;
            case 2: Out.print("zwei");   break;
            case 3: Out.print("drei");   break;
            case 4: Out.print("vier");   break;
            case 5: Out.print("fünf");   break;
            case 6: Out.print("sechs");  break;
            case 7: Out.print("sieben"); break;
            case 8: Out.print("acht");   break;
            case 9: Out.print("neun");   break;
        }
    }

    /**
     * Method to print the tenth digit number. Since we want
     * to print clock times, the value can vary between 0-5.
     * 
     * In case the tenth digit is 0 (minutes < 10) we only
     * print the single digit, so we do not have to handle
     * that case in here.
     * 
     * @param tenthDigitNumber
     */
    protected static void printTenthDigitString(int tenthDigitNumber) {
        switch (tenthDigitNumber) {
            case 1: Out.print("zehn");    break;
            case 2: Out.print("zwanzig"); break;
            case 3: Out.print("dreißig"); break;
            case 4: Out.print("vierzig"); break;
            case 5: Out.print("fünfzig"); break;
        }
    }
}