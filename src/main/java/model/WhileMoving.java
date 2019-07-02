package model;

import java.util.ArrayList;
import java.util.List;

public class WhileMoving implements TypeAttack {
    /**
     * type player is an identifier that allows to define in what type of series an attack of a weapon will be performed
     *
     * extra are the money required to unlock extra functions when a player shoots
     * Conventions for the price:
     * (extra is a List of three elements)
     * Position 0 (extra.get(0)) = Number of red Ammos
     * Position 1 (extra.get(0)) = Number of blue Ammos
     * Position 2 (extra.get(0)) = Number of yellow Ammos
     *
     * distance is an int that indicates the distance between the attacking player and the attacked player
     * moveme is an int that indicates of how many steps the attacking player can move
     * moveyou is an int that indicates of how many steps the attacked player can move
     * E is the list containing all the effects that a weapon with the given type attack will own
     */
    private List<Integer> extra;
    private int typeplayer;
    private int distance;
    private int moveme;
    private int moveyou;
    private List<Effect> E;

    /**
     *
     * @return the distance between the attacking player and the attacked
     */
    public int getDistance()
    {
        return this.distance;
    }

    /**
     *
     * @return the type of the player, that is the identifier that allows to define in what type of series an attack of a weapon will be performed
     */
    public int getTypePlayer()
    {
        return this.typeplayer;
    }

    /**
     *
     * @return the list containing the money required to unlock extra functions when a player shoots
     */
    public List getExtras()
    {
        return this.extra;
    }

    /**
     * Method to set the distance of a type of attack
     * @param distance is the distance between the attacking player and the attacked
     */
    public void setDistance(int distance)
    {
        this.distance=distance;
    }

    /**
     * Method to set the value of typeplayer
     * @param typePlayer is the identifier that allows to define in what type of series an attack of a weapon will be performed
     */
    public void setTypePlayer(int typePlayer)
    {
        this.typeplayer= typePlayer;
    }

    /**
     * Method to add the money for an extra
     * @param extra is an int representing the price of an extra function
     */
    public void addExtra(int extra)
    {
        this.extra.add(extra);
    }

    /**
     * Method to set the value of moveme
     * @param moveMe is an int that indicates of how many steps the attacking player can move
     */
    public void setMoveMe(int moveMe)
    {
        this.moveme= moveMe;
    }

    /**
     * Method to set the value of moveyou
     * @param moveYou is an int that indicates of how many steps the attacked player can move
     */
    public void setMoveYou(int moveYou)
    {
        this.moveyou= moveYou;
    }

    /**
     * Method to add an effect to the list of owned effects of the weapon
     * @param E is the effect to be added to the owned effects of the type attack
     */
    public void addEffect(Effect E)
    {
        this.E.add(E);
    }

    /**
     *
     * @param i is the number of the effect
     * @return the effect at position i in the list
     */
    public Effect getEffect(int i)
    {
        return this.E.get(i);
    }

    /**
     *
     * @return the number of effects of the type attack
     */
    public int getNumberEffect()
    {
        return this.E.size();
    }

    /**
     *
     * @return the number of different types of ammo in the game
     * In the game, according to the actual version, the number is set on three (red, blue, yellow).
     * In possible future versions there may be more types of Ammos (green or orange for example);
     * that's the reason why we choose to distinguish.
     * Therefore, this number is used to cicle on the List given by getExtras()- method at line 53
     */
    public int getNumberExtra()
    {
        return this.extra.size();
    }

    /**
     *
     * @return moveme, the int that indicates of how many steps the attacking player can move
     */
    public int getMoveMe()
    {
        return this.moveme;
    }

    /**
     *
     * @return moveyou, the int that indicates of how many steps the attacked player can move
     */
    public int getMoveYou()
    {
        return this.moveyou;
    }

    /**
     *
     * @return an int corresponding to the type of type attack
     */
    public int getType(){return 6;}


    /**
     *
     * @param typeplayer is an identifier that allows to define in what type of series an attack of a weapon will be performed
     * @param distance is an int that indicates the distance between the attacking player and the attacked player
     * @param moveme is an int that indicates of how many steps the attacking player can move
     * @param moveyou is an int that indicates of how many steps the attacked player can move
     */


    public WhileMoving(int typeplayer, int distance, int moveme, int moveyou)
    {
        this.extra=new ArrayList<Integer>();
        this.E= new ArrayList<Effect>();
        this.distance=distance;
        this.moveme=moveme;
        this.moveyou=moveyou;
        this.typeplayer=typeplayer;
    }
}