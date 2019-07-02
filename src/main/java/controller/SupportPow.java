package controller;

import model.Player;

import java.util.List;

public class SupportPow {
    private int status;
    private int direction;
    private int steps;
    private Player attacker;
    private List<Player> victims;

    public int getStatus()
    {
        return this.status;
    }

    public int getDirection()
    {
        return this.direction;
    }

    public int getSteps()
    {
        return this.steps;
    }

    public Player getAttacker()
    {
        return this.attacker;
    }

    public List<Player> getVictims()
    {
        return this.victims;
    }

    public void setStatus(int status)
    {
        this.status=status;
    }

    public void setDirection(int direction)
    {
        this.direction=direction;
    }

    public void setSteps(int steps)
    {
        this.steps=steps;
    }

    public void setAttacker(Player attacker)
    {
        this.attacker=attacker;
    }

    public void addVictim(Player victim)
    {
        this.victims.add(victim);
    }

}
