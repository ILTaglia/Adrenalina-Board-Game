package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetData {
    private InputStreamReader reader= new InputStreamReader(System.in);
    private BufferedReader myInput= new BufferedReader(reader);
    private static final Logger LOGGER= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private List<String> colors=new ArrayList<>();

    /**
     * Nel costruttore setto il livello di default del LOGGER
     */
    public GetData(){
        LOGGER.setLevel(Level.INFO);

        colors.add("Blue");
        colors.add("Green");
        colors.add("Yellow");
        colors.add("Pink");
        colors.add("Grey");
        //players color are blue(0), green(1), yellow(2), pink(3), grey(4)


    }
    /* Takes a string from the board
     * @return gives the string in input, exception gives "Color"
     */
    public String getValidColorforPlayer() {
        String color="";
        do{
            try {
                color=(myInput.readLine());
            } catch (IOException e) {
                color="Color";
                LOGGER.log(Level.FINEST,e.getMessage(),e);

            }
        }while("".equals(color));
        return color;
    }
}
