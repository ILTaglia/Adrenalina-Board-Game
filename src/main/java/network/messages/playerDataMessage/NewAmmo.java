package network.messages.playerDataMessage;

import java.util.List;

/**
 * Message for new ammo for the player
 */
public class NewAmmo extends InfoMessage {

    private List<Integer> listAmmo;

    public NewAmmo(List<Integer> listAmmo){
        super("Now you have new Ammos:"+"\nRed: "+ listAmmo.get(0) +"\nBlue: "+listAmmo.get(1)+"\nYellow: "+listAmmo.get(2));
        this.listAmmo=listAmmo;
        this.content="NewAmmo";
    }

    public List<Integer> getListAmmo() {
        return listAmmo;
    }
}
