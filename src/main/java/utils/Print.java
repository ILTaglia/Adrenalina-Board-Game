package utils;

import java.io.PrintStream;

public class Print {

    private static PrintStream streamPrint=System.out;

    public static void printOut(String stringToPrint){
        streamPrint.println(stringToPrint);
    }
    public static void printOut(int intToPrint){
        streamPrint.println(intToPrint);
    }
    public static void printMapSpace(){printOut("        ");}
}
