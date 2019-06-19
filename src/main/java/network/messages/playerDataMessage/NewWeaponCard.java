package network.messages.playerDataMessage;

import model.Weapon;
import network.messages.playerDataMessage.InfoMessage;

public class NewWeaponCard extends InfoMessage {
    private Weapon weaponCard;

    public NewWeaponCard(Weapon weaponCard){
        //TODO: verificare info utili weaponCard (non conosco i metodi)
        super("You have drawn a new WeaponCard. Name: "+ weaponCard.getName() + "aggiungere info utili weaponCard");
        this.weaponCard=weaponCard;
        this.content="NewWeaponCard";
    }

    public Weapon getWeaponCard() {
        return weaponCard;
    }
}
