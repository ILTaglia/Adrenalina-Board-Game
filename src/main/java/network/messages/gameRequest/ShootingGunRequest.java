package network.messages.gameRequest;

import network.messages.Message;

public class ShootingGunRequest extends GameRequestMessage {
    public ShootingGunRequest(String gunIndex)
    {
        super(gunIndex);
        this.content="GunIndex";
    }
}
