public class App {
    /**
     * I am personally no fan of this approach for multiple reasons:
     * - No fixed naming conventions for static protected variables
     *   They remind me of constant variables which should not be changed,
     *   however as far I have found this naming convention also goes for
     *   protected static vars.
     * 
     * - Using static variables just so I have access to them everywhere
     *   is not a nice approach, however I like  splitting up my code
     *   for better maintainability and with no class context this was
     *   the best approach I could think of.
     *   Return values of function would not have worked since I would have to
     *   return multiple values of my function, maybe Java supports this
     *   behavior but I don't know how and think this would go out of scope
     *   of the exercise.
     */
    protected static int TOTAL_KM = 0;
    protected static int TOTAL_MIN = 0; 
    protected static int MAX_KM = 0;

    public static void main(String[] args) {
        In.open("./TrainingRecords.txt");

        Out.println("Trainingsauswertung");
        Out.println("-------------------");

        int totalNumRuns = 0;
        int currWeek = 1;

        /**
         * If we would read the value at the start of each loop, we would
         * have a problem for our last iteration, because In.readInt()
         * would read the EOF, returns 0 and sets done to false. Even tho
         * done = false, we will recieve a valid return value and end up
         * printing an aditional week with numRuns = 0.
         * 
         * To solve this problem we either can re-check for In.done() right
         * after reading it and continue the loop to end it immideatly or
         * what was done here, read numRuns beforehand and at the end of
         * each loop.
         */
        int numRuns = In.readInt();

        while(In.done()) {
            totalNumRuns += numRuns;

            Out.println("Woche " + currWeek + ":");
            printWeekDetails(numRuns);
            Out.println();

            currWeek++;
            numRuns = In.readInt();
        }

        In.close();

        Out.println("Gesamtergebnis:");
        printDetails(totalNumRuns, TOTAL_KM, TOTAL_MIN, MAX_KM);
    }

    /**
     * Method to handle printing out week details and updating global
     * variables.
     * 
     * Since we do not have to handle error cases and are allowed to
     * assume the input data is always in a correct format, reading in
     * data can be done by a simple looping x (numRuns) amount of times
     * and reading 2 values each iteration.
     * 
     * What happens in here:
     * - define variables to store weekly run data
     * - handle case of numRuns = 0
     * - reading file data + updating weekly values
     * - updating global values
     * 
     * @param numRuns
     */
    protected static void printWeekDetails(int numRuns) {
        int totalWeekKm = 0;
        int totalWeekMin = 0;

        int maxWeekKm = 0;

        Out.print("  Läufe: ");

        /**
         * Handle weeks with 0 runs
         */
        if(numRuns == 0) {
            Out.println("keine");
            return;
        }

        /**
         * Read runs and update values
         */
        for(int i = 0; i < numRuns; i++) {
            int km = In.readInt();
            int min = In.readInt();

            if(i != 0) {
                Out.print("; ");
            }

            if(maxWeekKm < km) {
                maxWeekKm = km;
            }

            Out.print(km + " km in " + min + " min");

            totalWeekKm += km;
            totalWeekMin += min;
        }

        /**
         * Updating global run data 
         */
        if(MAX_KM < maxWeekKm) {
            MAX_KM = maxWeekKm;
        }
        
        TOTAL_KM += totalWeekKm;
        TOTAL_MIN += totalWeekMin;

        /**
         * Print Details of week
         */
        Out.println();
        printDetails(numRuns, totalWeekKm, totalWeekMin, maxWeekKm);
    }
    
    /**
     * Handles printing all details except which runs were taken.
     * This can also be used to print the summary of all runs 
     * 
     * @param numRuns
     * @param totalKm
     * @param totalMin
     * @param maxKm
     */
    protected static void printDetails(int numRuns, int totalKm, int totalMin, int maxKm) {
        /**
         * Values are scaled by 100 for rounding purposes later
         */
        int averageKm = totalKm * 100 / numRuns;
        int averageRunningSpeed = totalMin * 100 / totalKm;

        Out.println("  Gesamte Distanz: " + totalKm + " km");
        Out.println("  Max. gelaufene Distanz: " + maxKm + " km");
        
        Out.print("  Durchschnittl. Distanz pro Lauf: ");
        printNumberRounded(averageKm);
        Out.println(" km");

        Out.print("  Durchschnittl. Laufgeschwindigkeit: ");
        printNumberRounded(averageRunningSpeed);
        Out.println(" min/km");
    }

    /**
     * Prints out number with 1 comma value. To correctly round numbers
     * with a single comma value we need to upscale the numbers beforehand
     * with 100.
     * 
     * How does it work?
     * If I want to print X comma values, I need to upscale the numbers by
     * 10^(x+1). The +1 is needed so we can add 5 to the number for rounding.
     * 
     * Since we want 1 comma we need to use 10^(1+1) => 10^2 => 100
     * 
     * @param number
     */
    protected static void printNumberRounded(int number) {
        int baseNumber = number / 100;
        int commaNumber = (number % 100 + 5) / 10;
        Out.print(baseNumber + "." + commaNumber);
    }
}
