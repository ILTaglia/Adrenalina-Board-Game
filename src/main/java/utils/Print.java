package utils;

import java.io.PrintStream;

public class Print {


    private static PrintStream streamPrint=System.out;

    /**
     * Method to print Strings
     * @param stringToPrint is the string to print
     */
    public static void printOut(String stringToPrint){
        streamPrint.println(stringToPrint);
    }

    /**
     * Method to print int
     * @param intToPrint is the int to print
     */
    public static void printOut(int intToPrint){
        streamPrint.println(intToPrint);
    }

    /**
     * Method to print map space
     */
    public static void printMapSpace(){printOut("        ");}
}
