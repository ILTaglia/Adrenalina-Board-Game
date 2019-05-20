package network;


import java.io.IOException;
import java.util.Scanner;

public class GameLauncher{
//TODO: Main del tutto temporaneo
    public static void main(String[] args) {
        //Main temporaneo, scelta tra client e server a primo avvio
        Scanner userChoice;
        userChoice=new Scanner(System.in);
        System.out.println("Inserire 0 per avviare Server, 1 per avviare Client:\t");
        if(userChoice.nextInt()==0){
            try {
                GameServer.main();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }else{
            //
        }
    }
}
