package utils;

import java.io.PrintStream;
//TODO: Dare un senso ai nomi dei metodi

public class printStream {
    private static PrintStream streamPrint=System.out;

    public static void printOut(String stringToPrint){
        streamPrint.println(stringToPrint);
    }
    public static void printOut(int intToPrint){
        streamPrint.println(intToPrint);
    }
}
