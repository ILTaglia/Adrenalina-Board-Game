package network.messages;



public class MapUserRequest extends RequestMessage {

    String userID;

    public MapUserRequest(String requiredMap){
        super(requiredMap);
        this.content="MapUserRequest";
    }

    public String getUserID(){
        return userID;
    }
    public void setUserID(String userID){
        this.userID=userID;
    }
}
