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
    private BufferedReader input = new BufferedReader(reader);
    private static final Logger LOGGER= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private List<String> colors=new ArrayList<>();
    private List<String> directions=new ArrayList<>();


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

        directions.add("N");
        directions.add("E");
        directions.add("S");
        directions.add("W");
        directions.add("Stop");
        //Cardinal points for player North, East, South, West

    }
    /* Takes a string from the board
     * @return gives the string in input, exception gives "Color"
     */
    public String getValidColorforPlayer() {
        String color="";
        do{
            try {
                color=(input.readLine());
            } catch (IOException e) {
                color="Color";
                LOGGER.log(Level.FINEST,e.getMessage(),e);

            }
        }while("".equals(color) || !colors.contains(color));
        return color;
    }

    public String getValidDirectionforPlayer() {
        String direction="";
        do{
            try {
                direction=(input.readLine());
            } catch (IOException e) {
                direction="Direction";
                LOGGER.log(Level.FINEST,e.getMessage(),e);

            }
        }while("".equals(direction) || !directions.contains(direction));
        return direction;
    }

    public List<String> getValidListDirectionforPlayer(){
        List<String> list = new ArrayList<>();
        String s = this.getValidDirectionforPlayer();
        while(!s.equals("Stop")){
            list.add(s);
            s = this.getValidDirectionforPlayer();
        }
        return list;
    }

    public String getName() {
        String name="";
        do{
            try {
                name=(input.readLine());
            } catch (IOException e) {}
        }while("".equals(name));
        return name;
    }

    public int getInt(int min,int max){
        int k=0;
        boolean isValid=false;
        if(min>max){
            k=max;
            max=min;
            min=k;
        }

        do{
            try {
                k=Integer.parseInt(input.readLine());
                isValid=true;
            } catch (NumberFormatException e) {
                isValid=false;
                LOGGER.log(Level.FINEST,e.getMessage(),e);

            } catch (IOException e) {
                isValid=false;
                LOGGER.log(Level.FINEST,e.getMessage(),e);

            }
        }while(!isValid || (k<min || k>max));
        return k;
    }
}
