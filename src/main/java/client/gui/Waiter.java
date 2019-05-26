package client.gui;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/*Class used to allow the adapter to be put in wait of a response by the client. GUIView wakes GUIViewAdapter the response is ready */
public class Waiter {

    private GUIViewAdapter guiViewAdapter;
    private static final Logger LOGGER= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Waiter(){
        LOGGER.setLevel(Level.INFO);
    }

    /*Set of guiViewAdapter to return the selected value
     */
    public void setGuiViewAdapter(GUIViewAdapter guiViewAdapter){
        this.guiViewAdapter=guiViewAdapter;
    }

    /*Stop thread of client*/
    public synchronized void stopAdapter(){
        try {
            wait();
        } catch (InterruptedException e) {
            LOGGER.log(Level.FINEST,e.getMessage(),e);
        }
    }

    /*Returns the selected value by the client if this value is an integer*/
    @SuppressWarnings("unchecked")
    public synchronized void wakeUpGuiViewAdapter(Object returnValue){
        notify();
        if(returnValue instanceof Integer){
        }
        if(returnValue instanceof String){
        }
        if(returnValue instanceof List<?>){
        }
    }

}

