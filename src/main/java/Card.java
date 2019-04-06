import exceptions.*;

abstract public class Card {

    protected boolean Used;

    public void Set_Used(){
        this.Used=true;
    }

    public void Set_Available(){
        this.Used=false;
    }

    //public abstract void Collect_Card(Player player) throws CardAlreadyCollectedException;
}
