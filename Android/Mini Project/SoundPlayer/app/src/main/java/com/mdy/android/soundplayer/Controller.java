package com.mdy.android.soundplayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 3/3/2017.
 */

public class Controller {
    private static Controller instance = null;
    List<ControlInterface> targets;

    private Controller(){
        targets = new ArrayList<>();
    }

    public static Controller getInstance(){
        if(instance == null){
            instance = new Controller();
        }
        return instance;
    }

    public void addObserver(ControlInterface target) {
        targets.add(target);
    }

    public void play(){
        for(ControlInterface target : targets){
            target.startPlayer();
        }
    }

    public void pause(){
        for(ControlInterface target : targets){
            target.pausePlayer();
        }
    }

    public void remove(ControlInterface target){
        targets.remove(target);
    }
}
