public class App {
    public static void main(String[] args) {
        /**
         * Since the input parameters are read in as integer in [cm]
         * all variables regarding length and are contain values
         * in [cm] or [cm^2]
         * 
         * Since the price is given in eur/m^2 and area in m^2 we need an additional value
         * to scale our messurements [cm^2] => [c * (10^-2)^-2] => c * 10^-4
         * => 10^-4 => 1/10000
         * So when we calculate the price or the area in [m], we are off by a factor of 10000,
         * so we need to divide by it.
         * 
         * In a real world example I would use a float instead of int and
         * have no problem with precision (technically float can be inprecise,
         * however the precision we need in this example is nowhere near that).
         * This is also the reason I am working with [cm] instead of [m], to not lose
         * any precision and also use some hackish workarounds to be able to handle
         * representation of floating numbers
         */

        
        //
        // 1.) Read Values from StdIn + assign value for pricePerM2
        //
        Out.print("Abmessungen Segment (l, h1, h2 - in cm): ");
        int l = In.readInt();
        int h1 = In.readInt();
        int h2 = In.readInt();

        int pricePerM2 = 96;


        //
        // 2.) Calculate averageHeigh, area and price
        //

        /**
         * +1 so values are rounded, since commaes are removed
         * you can round by adding a value of 0.5 and cast to int, since we only should
         * use int values, I add 1 before dividing by 2 to archieve the same result
         *
         * if h1 + h2 is even, +1 will not change the outcome
         * if h1 + h2 is odd, +1 will up the result by 1
         */
        int averageHeight = (h1 + h2 + 1) / 2;

        // these two values are off by a factor of 10000
        int area = averageHeight * l;
        int price = area * pricePerM2;


        //
        // 3.) Print calculated Values
        //

        Out.println("---- Kosten für das Segment ----");
        Out.print("Länge: " + l +" cm, ");
        Out.println("Mittlere Höhe: " + averageHeight + " cm");
        
        Out.print("Fläche: ");
        printNumbersWithComma(area);
        Out.println(" m2");

        Out.print("Preis für das Segment: ");
        printNumbersWithComma(price);
        Out.println(" EUR");
    }

    /**
     * Prints numbers  off by a factor of 10000 with the
     * correct comma representation
     * 
     * To calculate the base number before the comma we need to
     * divide by 10000. To recieve further numbers we need to divide
     * by a factor of 10 less for each position (10^3 and 10^2 for
     * the first 2 comma values).
     * 
     * Since we are off by a factor of 10000 there is also
     * the possibility we need to round our last digit.
     * This happens by adding 50 (10^2 / 2 => 100/2 => 50)
     * before dividing the number.
     * 
     * @param number - number which should be printed
     */
    protected static void printNumbersWithComma(int number) {
        int fullNumber = number / 10000;
        int commaNumber1 = number / 1000 % 10;
        int commaNumber2 = (number + 50) / 100 % 10;

        Out.print(fullNumber + "." + commaNumber1 + commaNumber2);
    }
}
