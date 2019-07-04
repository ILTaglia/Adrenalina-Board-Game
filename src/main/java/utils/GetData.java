package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.Print.printOut;

public class GetData {

    private Scanner input = new Scanner(System.in);

    private List<String> colors=new ArrayList<>();
    private List<String> directions=new ArrayList<>();


    /**
     * GetData is for taking data from the user
     */
    public GetData(){

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

    /** Takes a string from the board
     * @return the string in input, exception gives "Color"
     */
    public String getValidColorForPlayer() {
        String color;
        do{
            color=input.nextLine();
        }while(!colors.contains(color));
        return color;
    }

    /**
     * Takes an int and returns the corresponding color
     * @param color is the int representing the color
     * @return the corresponding String
     */
    public String getColorFromInt(int color){
        if(color==0) return "Red";
        else if(color==1) return  "Blue";
        else if(color==2) return "Yellow";
        else return null;
    }

    /**
     * Takes a list of characters from the board (cardinal points for direction of movements)
     * @return the List in input
     */
    public List<String> getValidListDirectionForPlayer(){
        List<String> runListDirection = new ArrayList<>();
        String direction;

        do{
            direction=getValidDirectionForPlayer();
            if(!direction.equals("Stop")) runListDirection.add(direction);
        }while(!direction.equals("Stop"));

        return runListDirection;
    }

    /**
     * Takes characters from the board
     * @return the character in input, exception gives "direction"
     */
    public String getValidDirectionForPlayer() {
        String direction;
        do{
            try {
                direction = input.nextLine();
            }catch(IndexOutOfBoundsException e){
                direction=null;
                break;
            }
        }while(!directions.contains(direction));
        return direction;

    }

    /**
     * Takes a String from the board
     * @return the String in input
     */
    public String getName() {
        String name;
        do{
            name=(input.nextLine());
        }while(name.equals(""));
        return name;
    }

    /**
     * Takes a 4 int of coordinates from the board
     * @param xMin is the minimum number of line
     * @param xMax is the maximum number of line
     * @param yMin is the minimum number of column
     * @param yMax is the maximum number of column
     * @return a list containing all the coordinates in the map
     */
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

    /**
     * Takes an int from the board, chosen between min and max
     * @param min is the minimum value accepted
     * @param max is the maximum value accepted
     * @return the int inserted
     */
    public int getInt(int min,int max){
        int k = max+1;
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
                System.out.println("Not a valid number\n");
            }
            catch(IndexOutOfBoundsException e){
                break;
            }
        }while(!isValid || (k<min || k>max));
        return k;
    }

    /**
     *
     * @return This method return true if the answer is Yes, false otherwise
     */
    public boolean askYesOrNo(){
        printOut("Answer 'Yes' or 'No'");
        String answer;
        do{
            try {
                answer = input.nextLine();
            }
            catch(IndexOutOfBoundsException e){
                answer="No";
                break;
            }
        }while(!(answer.equals("Yes")||answer.equals("No")));
        return answer.equals("Yes");
    }

}
