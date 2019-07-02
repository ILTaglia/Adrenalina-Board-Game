package model;

import java.io.Serializable;
import java.util.List;

public interface TypeAttack extends Serializable {
     /**
     *
     * @return the distance between the attacking player and the attacked
     */
    int getDistance();

    /**
     *
     * @return the type of the player, that is the identifier that allows to define in what type of series an attack of a weapon will be performed
     */
    int getTypePlayer();

    /**
     *
     * @return the list containing the money required to unlock extra functions when a player shoots
     */
    List getExtras();

    /**
     * Method to set the distance of a type of attack
     * @param distance is the distance between the attacking player and the attacked
     */
    void setDistance(int distance);

    /**
     * Method to set the value of typeplayer
     * @param typePlayer is the identifier that allows to define in what type of series an attack of a weapon will be performed
     */
    void setTypePlayer(int typePlayer);

    /**
     * Method to add the money for an extra
     * @param extra is an int representing the price of an extra function
     */
    void addExtra(int extra);

    /**
     * Method to set the value of moveme
     * @param moveMe is an int that indicates of how many steps the attacking player can move
     */
    void setMoveMe(int moveMe);

    /**
     * Method to set the value of moveyou
     * @param moveYou is an int that indicates of how many steps the attacked player can move
     */
    void setMoveYou(int moveYou);

    /**
     * Method to add an effect to the list of owned effects of the weapon
     * @param E is the effect to be added to the owned effects of the type attack
     */
    void addEffect(Effect E);

    /**
     *
     * @param i is the number of the effect
     * @return the effect at position i in the list
     */
    Effect getEffect(int i);

    /**
     *
     * @return the number of effects of the type attack
     */
    int getNumberEffect();

    /**
     *
     * @return the number of different types of ammo in the game
     * In the game, according to the actual version, the number is set on three (red, blue, yellow).
     * In possible future versions there may be more types of Ammos (green or orange for example);
     * that's the reason why we choose to distinguish.
     * Therefore, this number is used to cicle on the List given by getExtras()- method at line 53
     */
    int getNumberExtra();

    /**
     *
     * @return moveme, the int that indicates of how many steps the attacking player can move
     */
    int getMoveMe();

    /**
     *
     * @return moveyou, the int that indicates of how many steps the attacked player can move
     */
    int getMoveYou();
    /**
     *
     * @return an int corresponding to the type of type attack
     */
    int getType();

    /**
     *
     * @param typeplayer is an identifier that allows to define in what type of series an attack of a weapon will be performed
     * @param distance is an int that indicates the distance between the attacking player and the attacked player
     * @param moveme is an int that indicates of how many steps the attacking player can move
     * @param moveyou is an int that indicates of how many steps the attacked player can move
     */
}
