package network.messages.gameRequest;

import network.messages.Message;

public class DirectionRequest extends Message {
    /**
     * Method for game request to ask direction
     * @param requiredDirection is the required direction
     */
    public DirectionRequest (String requiredDirection)
    {
        this.info=requiredDirection;
        this.type="directionRequest";
    }
}
