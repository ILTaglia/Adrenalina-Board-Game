package network.messages.playerDataMessage;

import model.Coordinate;

public class NewPosition extends InfoMessage {

    private Coordinate coordinate;

    public NewPosition(int x, int y){
        super("Your position now is \nx: " + x +"\ny: " + y);
        this.coordinate=new Coordinate(x,y);
        this.content="NewPosition";
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}