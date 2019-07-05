package network.messages.playerDataMessage;

import model.Weapon;

/**
 * Message for a new weapon to the player
 */
public class NewWeaponCard extends InfoMessage {
    private Weapon weaponCard;

    public NewWeaponCard(Weapon weaponCard){
        super("You have drawn a new WeaponCard. Name: "+ weaponCard.getName() + "aggiungere info utili weaponCard");
        this.weaponCard=weaponCard;
        this.content="NewWeaponCard";
    }

    /**
     *
     * @return the weapon
     */
    public Weapon getWeaponCard() {
        return weaponCard;
    }
}
