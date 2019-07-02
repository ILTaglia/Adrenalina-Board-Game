package network.messages.gameRequest;

import network.messages.Message;

public class DirectionRequest extends Message {
    public DirectionRequest (String requiredDirection)
    {
        this.info=requiredDirection;
        this.type="directionRequest";
    }
}
