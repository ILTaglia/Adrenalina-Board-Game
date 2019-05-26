package client.gui;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Popup {
    //TODO serializable?
    private static final int TRANSITION_TIME=1000;
    private Point startingPoint;
    private Point endingPoint;
    private int stoppingTime;
    private boolean isGoing=false;
    private List<String> queueMessage=new ArrayList<String>();
    Timer timer;

    private ActionListener animationTask = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            ((Timer)evt.getSource()).stop();
            Popup.this.goBack();
            //si potrebbe mettere un timer che attende che l'animazione sia finita
            Popup.this.isGoing=false;
            if(Popup.this.queueMessage.size()>0){
                showMessage(Popup.this.queueMessage.get(0));
                Popup.this.queueMessage.remove(0);
            }
        }
    };

    Popup(Point startingPoint,Point endingPoint,int stoppingTime){
        this.startingPoint=startingPoint;
        this.endingPoint=endingPoint;
        this.stoppingTime=stoppingTime;

        Color c=new Color(40,40,40,100);
    }

    public void showMessage(String message){
        if(!isGoing){

            //Si potrebbe mettere un timer che attende che l'animazione sia finita
            timer=new Timer(stoppingTime, animationTask);
            timer.start();
            isGoing=true;
        }else{
            queueMessage.add(message);
        }
    }

    private void goBack(){

    }
}
