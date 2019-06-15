package utils;

import model.Match;
import network.messages.Message;
import network.server.GameRoom;
import network.server.GameServer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class NotifyClient {

    private static GameServer gameServer;
    private static Map<String, GameRoom> userIDtoGameRoom =new HashMap<>();
    private static Map<Match,GameRoom> matchToGameRoom=new HashMap<>();

    public static void registerServer(GameServer server){
        gameServer=server;
    }

    public static void registerNewGame(Collection<String> userIDs, GameRoom gameRoom){
        for(String userID:userIDs){
            userIDtoGameRoom.put(userID,gameRoom);
        }
    }
    public static void registerNewMatch(GameRoom gameRoom,Match match){
        matchToGameRoom.put(match,gameRoom);
    }

    public static void notifyAllClients(Match match,Message message){
        GameRoom gameRoom=matchToGameRoom.get(match);
        Map<String,GameRoom> result= userIDtoGameRoom.entrySet()
                .stream()
                .filter(value->value.getValue().equals(gameRoom))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Collection<String> userIDs=result.keySet();
        for(String userID:userIDs){
            try {
                gameServer.sendMessageToID(userID,message);
            }catch (NullPointerException e){
                //Test case: gameServer not instantiated
            }
        }
    }
    public static void notifyAllExceptOneClient(String userIDToExclude,Message message){
        GameRoom gameRoom=userIDtoGameRoom.get(userIDToExclude);
        Map<String,GameRoom> result= userIDtoGameRoom.entrySet()
                .stream()
                .filter(value->value.getValue().equals(gameRoom))
                .filter(key->!key.getKey().equals(userIDToExclude))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Collection<String> userIDs=result.keySet();
        for(String userID:userIDs){
            try {
                gameServer.sendMessageToID(userID,message);
            }catch (NullPointerException e){
                //Test case: gameServer not instantiated
            }
        }
    }
    public static void notifySpecificClient(String userID, Message message){
        try {
            gameServer.sendMessageToID(userID,message);
        }catch (NullPointerException e){
            //Test case: gameServer not instantiated
        }
    }


}
