public class SquareRoot {
    public static final double EPSILON = 1e-7;

    public static void main(String[] args) {
        // 1 or 2 command line arguments, but no more or less
        //value argument REQUIRED <>; epsilon is optional []
        /*
        1) if number of arguments in incorrect, the program will print the usage message:
        Usage: java SquareRoot <value> [epsilon]
        */
        if (args.length < 1 || args.length > 2) {
            System.err.println("Usage: java SquareRoot <value> [epsilon]");
            System.exit(1);
        }
        /*
        2) if number cannot be converted into a double, the program will print:
        Error: Value argument must be a double
        */
        double v = 0.0;
        try {
            v = Double.parseDouble(args[0]);
        } catch (NumberFormatException nfe) {
            System.err.println("Error: Value argument must be a double.");
            System.exit(1);
        }
        /*
        3) if epsilon is not present then use default,
        if it is the program will attempt to parse it into a double variable
        if it cannot be converted, or not positive, the program will print:
        Error: Epsilon argument must be a positive double
        */
        double e = EPSILON;
        if (args.length > 1) {
            try {
                e = Double.parseDouble(args[1]);
                if (e <= 0) {
                    System.err.println("Error: Epsilon argument must be a positive double.");
                    System.exit(1);
                }
            } catch (NumberFormatException nfe) {
                System.err.println("Error: Epsilon argument must be a positive double.");
                System.exit(1);
            }
        }
        // If command line arguments are valid, the program then calls sqrt()
        //uses System.printf() with 8 decimal digits

        System.out.printf("%.8f\n", sqrt(v, e));
        System.exit(0);
    }
    public static double sqrt(double num, double epsilon) {
        // num is value whose square root will be computed
        // epsilon is he tolerance of the computed square root
        /*
        1) if num is NaN or <0
        return Double.NaN
        */
        if (num < 0 || Double.isNaN(num)) {
            return Double.NaN;
        }
         /*
        2) if num is 0 or Double.POSITIVE_INFINITY
        return num itself
         */
        if (num == 0 || num == Double.POSITIVE_INFINITY) {
            return num;
        }
        /*
        VARIABLES: currentGuess and previousGuess
        currentGuess = num
        In a Loop:
        previousGuess = currentGuess
        currentGuess = 0.5 * (previousGuess + (num/previousGuess))
        */
        double currentGuess = num;
        double previousGuess = 0;
        while (Math.abs(currentGuess - previousGuess) > epsilon) {
            previousGuess = currentGuess;
            currentGuess = 0.5 * (previousGuess + (num / previousGuess));
        }
        return currentGuess;
        //return final value of currentGuess
    }

}
