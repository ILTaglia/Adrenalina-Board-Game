package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.Print.printOut;

public class GetData {
    //private InputStreamReader reader= new InputStreamReader(System.in);
    //private BufferedReader input = new BufferedReader(reader);
    private Scanner input = new Scanner(System.in);
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
    public String getValidColorForPlayer() {
        String color;
        do{
            color=input.nextLine();
        }while(!colors.contains(color));
        return color;
    }
    //TODO: serve classe utils per la View, qua o da qualche altra parte?
    public String getColorFromInt(int color){
        if(color==0) return "Red";
        else if(color==1) return  "Blue";
        else if(color==2) return "Yellow";
        else return null;
    }


    public List<String> getValidListDirectionForPlayer(){
        List<String> runListDirection = new ArrayList<>();
        String direction;

        do{
            direction=getValidDirectionForPlayer();
            if(!direction.equals("Stop")) runListDirection.add(direction);
        }while(!direction.equals("Stop"));

        return runListDirection;
    }
    public String getValidDirectionForPlayer() {
        String direction;
        do{
            direction=input.nextLine();
        }while(!directions.contains(direction));
        return direction;

    }

    public String getName() {
        String name;
        do{
            name=(input.nextLine());
        }while(name.equals(""));
        return name;
    }
    public List<Integer> getCoordinate(int xMin,int xMax, int yMin, int yMax){
        List<Integer> coordinate=new ArrayList<>();
        int xCoordinate;
        int yCoordinate;
        do{
            xCoordinate=input.nextInt();
            if(xCoordinate<xMin||xCoordinate>xMax) System.out.println("Not a valid xCoordinate\n");
        }while (xCoordinate<xMin||xCoordinate>xMax);
        System.out.println("Ok xCoordinate is: "+ xCoordinate);
        coordinate.add(xCoordinate);
        do{
            yCoordinate=input.nextInt();
            if(yCoordinate<yMin||yCoordinate>yMax) System.out.println("Not a valid yCoordinate\n");
        }while (yCoordinate<yMin||yCoordinate>yMax);
        System.out.println("Ok yCoordinate is: "+ yCoordinate);
        coordinate.add(yCoordinate);
        return coordinate;
    }

    public int getInt(int min,int max){
        int k = 0;
        boolean isValid;
        if(min>max){
            k=max;
            max=min;
            min=k;
        }

        do{
            try {
                k=input.nextInt();
                isValid=true;
            } catch (NumberFormatException e) {
                isValid=false;
                LOGGER.log(Level.FINEST,e.getMessage(),e);
                System.out.println("Not a valid number\n");

            }
        }while(!isValid || (k<min || k>max));
        return k;
    }
    //This method return true if the answer is Yes, false otherwise
    public boolean askYesOrNo(){
        printOut("Answer 'Yes' or 'No'");
        String answer;
        do{
            answer = input.nextLine();
        }while(!(answer.equals("Yes")||answer.equals("No")));
        return answer.equals("Yes");
    }

}
