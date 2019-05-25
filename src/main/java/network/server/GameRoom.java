package network.server;

import java.util.List;

public class GameRoom {

    //In questa stanza istanzio il controller e da qua inizia la partita in cui vengono aggiunti i giocatori ecc.

    //TODO: capire se i messaggi vadano gestiti direttamente qua o inoltrati al controller

    public GameRoom(List<String> username){
        System.out.println(username);
    }
}
