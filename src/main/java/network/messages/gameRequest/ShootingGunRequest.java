package network.messages.gameRequest;

import network.messages.Message;

public class ShootingGunRequest extends GameRequestMessage {
    /**
     * Method for game request for the shooting gun
     * @param gunIndex is the index of chosen weapon
     */
    public ShootingGunRequest(String gunIndex)
    {
        super(gunIndex);
        this.content="GunIndex";
    }
}
