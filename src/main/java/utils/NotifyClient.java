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

    /**
     * gameServer is the game server
     * userIDtoGameRoom is useful to notify to all clients
     * matchtoGameRoom is useful to connect the match to the gameRoom
     * userIDToStatusConnection is for client connected with that userID
     */
    private static GameServer gameServer;
    private static Map<String, GameRoom> userIDtoGameRoom =new HashMap<>();
    private static Map<Match,GameRoom> matchToGameRoom=new HashMap<>();
    private static Map<String,Boolean> userIDToStatusConnection=new HashMap<>();

    /**
     * Method to register server
     * @param server is the gameServer to register
     */
    public static void registerServer(GameServer server){
        gameServer=server;
    }

    /**
     * Method to register a new game
     * @param userIDs are the userID of the clients to connect
     * @param gameRoom is the gameRoom in which the clients are
     */
    public static void registerNewGame(Collection<String> userIDs, GameRoom gameRoom){
        for(String userID:userIDs){
            userIDtoGameRoom.put(userID,gameRoom);
            userIDToStatusConnection.put(userID,true);
        }
    }

    /**
     * Method to register a new match
     * @param gameRoom is the gameRoom
     * @param match is the match to register
     */
    public static void registerNewMatch(GameRoom gameRoom,Match match){
        matchToGameRoom.put(match,gameRoom);
    }

    /**
     * Method to update player status
     * @param userID is the userID of the player to update
     * @param connected is the boolean value connected
     */
    public static void updatePlayerStatus(String userID,boolean connected){
        userIDToStatusConnection.replace(userID,connected);
    }

    /**
     * Method to notify all clients
     * @param match is the match
     * @param message is the message to notify
     */
    public static void notifyAllClients(Match match,Message message){
        GameRoom gameRoom=matchToGameRoom.get(match);
        Map<String,GameRoom> result= userIDtoGameRoom.entrySet()
                .stream()
                .filter(value->value.getValue().equals(gameRoom))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Collection<String> userIDs=result.keySet();
        for(String userID:userIDs){
            try {
                sendMessageToID(userID,message);
            }catch (NullPointerException e){
                //Test case: gameServer not instantiated
            }
        }
    }

    /**
     * Method to notify except
     * @param userIDToExclude is the userID of the client to disconnect
     * @param message is the message to notify
     */
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
                sendMessageToID(userID,message);
            }catch (NullPointerException e){
                //Test case: gameServer not instantiated
            }
        }
    }

    /**
     * Method to notify specific clients
     * @param userID is the userID to be notified
     * @param message is teh message to notify
     */
    public static void notifySpecificClient(String userID, Message message){
        try {
            sendMessageToID(userID,message);
        }catch (NullPointerException e){
            //Test case: gameServer not instantiated
        }
    }

    /**
     * Method to send a message to an ID
     * @param userID is the userID
     * @param message is the message
     */
    private static void sendMessageToID(String userID,Message message){
        if(userIDToStatusConnection.get(userID)){
            gameServer.sendMessageToID(userID,message);
        }
    }
}
