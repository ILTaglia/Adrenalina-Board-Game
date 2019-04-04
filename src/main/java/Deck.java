import java.util.ArrayList;
import java.util.Collections;

public abstract class Deck {

    protected ArrayList<Card> Stack;
    protected ArrayList<Card> Stack_Discarded;

    public Card Draw_Card(){
        int i=0;
        return Stack.remove(i);
    }

    public void Shuffle(){
        Collections.shuffle(Stack_Discarded);
        Collections.copy(Stack,Stack_Discarded);
        Stack_Discarded.clear();
        return;
    }


}
