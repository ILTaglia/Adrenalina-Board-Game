package network.messages;

public class ColorRequest extends RequestMessage {

    private String userID;

    public ColorRequest(String requestedColor){
        super(requestedColor);
        this.content="ColorRequest";
    }

    public String getUserID(){
        return userID;
    }
    public void setUserID(String userID){
        this.userID=userID;
    }
}
