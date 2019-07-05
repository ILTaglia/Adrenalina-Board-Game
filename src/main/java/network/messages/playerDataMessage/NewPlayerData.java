package network.messages.playerDataMessage;

import model.PlayerVisibleData;

public class NewPlayerData extends InfoMessage {

    private PlayerVisibleData playerVisibleData;

    /**
     *
     * @param playerVisibleData is the visible class of model for all players
     */
    public NewPlayerData(PlayerVisibleData playerVisibleData){
        super("Hi "+playerVisibleData.getPlayer().getName()+ " your assigned color is " +playerVisibleData.getPlayer().getColorAsString()+" = "+playerVisibleData.getPlayer().getColor());
        this.playerVisibleData=playerVisibleData;
        this.content="NewPlayerData";
    }

    public PlayerVisibleData getPlayerVisibleData(){
        return this.playerVisibleData;
    }
}
