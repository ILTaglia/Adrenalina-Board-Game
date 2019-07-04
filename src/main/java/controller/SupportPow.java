package controller;

import model.Player;

import java.util.List;

public class SupportPow {
    /**
     * indexpow is an integer indicating the position of the pow in the list of pow of the player
     * status is an integer indicating the status in the game, just to know what I have to do when i have a message from the client
     * direction is an integer indicating in what direction i have choosen to use the Newton
     * steps indicate the number of steps I have choosen using the Newton
     * attacker memorizes the player who performed the attack
     * victims memorize all the list of players attacked during the attack
     */

    private int indexpow;
    private int status;
    private int direction;
    private int steps;
    private Player attacker;
    private List<Player> victims;

    /**
     *
     * @return getindexpow
     */
    public int getindexpow()
    {
        return this.indexpow;
    }

    /**
     *
     * @return status
     */
    public int getStatus()
    {
        return this.status;
    }

    /**
     *
     * @return direction
     */
    public int getDirection()
    {
        return this.direction;
    }

    /**
     *
     * @return steps
     */
    public int getSteps()
    {
        return this.steps;
    }

    /**
     *
     * @return the player who performed the attack
     */
    public Player getAttacker()
    {
        return this.attacker;
    }

    /**
     *
     * @return the list of players attacked during the attack
     */
    public List<Player> getVictims()
    {
        return this.victims;
    }

    /**
     *
     * @param status to be set
     */
    public void setStatus(int status)
    {
        this.status=status;
    }

    /**
     *
     * @param direction to be set
     */
    public void setDirection(int direction)
    {
        this.direction=direction;
    }


    /**
     *
     * @param indexpow to be set
     */
    public void setIndexpow(int indexpow)
    {
        this.indexpow=indexpow;
    }


    /**
     *
     * @param steps to be set
     */
    public void setSteps(int steps)
    {
        this.steps=steps;
    }

    /**
     *
     * @param attacker is the player who performed the attack
     */
    public void setAttacker(Player attacker)
    {
        this.attacker=attacker;
    }

    /**
     *
     * @param victim to be added
     */
    public void addVictim(Player victim)
    {
        this.victims.add(victim);
    }

}
