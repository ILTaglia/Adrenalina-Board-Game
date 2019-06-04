package network.messages;

public abstract class ClientRequestMessage extends Message{

    protected String userID;

    public ClientRequestMessage(String requiredContent, String userID){
        this.info=requiredContent;
        this.type="ClientRequest";
        this.userID=userID;
    }

    public String getUserID(){
        return userID;
    }
    public void setUserID(String userID){
        this.userID=userID;
    }
}
