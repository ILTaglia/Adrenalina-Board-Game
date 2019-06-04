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
    private static Map<String, GameRoom> userIDtoMatch=new HashMap<>();
    private static Map<Match,GameRoom > matchToGameRoom=new HashMap<>();

    public static void registerServer(GameServer server){
        gameServer=server;
    }

    public static void registerNewGame(Collection<String> userIDs, GameRoom gameRoom){
        for(String userID:userIDs){
            userIDtoMatch.put(userID,gameRoom);
        }
    }
    public static void registerNewMatch(GameRoom gameRoom,Match match){
        matchToGameRoom.put(match,gameRoom);
    }

    public static void notifyAllClients(Match match,Message message){
        GameRoom gr=matchToGameRoom.get(match);
        Map<String,GameRoom> result= userIDtoMatch.entrySet()
                .stream()
                .filter(value->value.getValue().equals(gr))
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
        Collection<String> userIDs=result.keySet();
        for(String userID:userIDs){
            gameServer.sendMessageToID(userID,message);
        }
    }

    public static void notifySpecificClient(String userID, Message message){
        gameServer.sendMessageToID(userID,message);
    }


}
