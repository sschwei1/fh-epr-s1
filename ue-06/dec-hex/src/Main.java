public class Main {
    public static void main(String[] args) {
        Out.print("Geben Sie eine positive ganze Zahl ein (Ende mit Eingabe < 0): ");
        int n = In.readInt();

        while(In.done()) {
            if(n < 0) {
                break;
            }

            printHex(n);
            Out.println();
            Out.print("Geben Sie eine positive ganze Zahl ein (Ende mit Eingabe < 0): ");
            n = In.readInt();
        }
    }

    protected static void printHex(int n) {
        Out.println(n + "(dez) => " + dezToHex(n) + "(hex)");
    }

    protected static String dezToHex(int value) {
        if(value == 0) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        while(value != 0) {
            /*
             * Same as "value % 16" however bitwise operation are
             * faster, but you can only use them when working with
             * power of 2
             *
             * Take the last 4 bits of the number
             */
            int rest = value & 0xF;
            char offset = rest < 10 ? '0' : 'A';
            char hexDigit = (char)(offset + rest % 10);
            sb.append(hexDigit);

            /*
             * Same as "value / 16", however bitwise operation are
             * faster, but you can only use them when working with
             * power of 2
             *
             * Shift bits to the right by 4 positions
             */
            value >>>= 4;
        }

        return sb.reverse().toString();
    }
}