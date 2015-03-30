package com;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Broadcaster {

    //listenerzy są statyczny czyli każdy kto załąvzy aplikację jest dodawany jako nowy listener
    public static final List<BroadcastListener> listeners = new CopyOnWriteArrayList<BroadcastListener>();

    public static void register(BroadcastListener listener) {
        listeners.add(listener);
    }

    public static void unregister(BroadcastListener listener) {
        listeners.remove(listener);
    }

    public static void broadcast(final String message, BroadcastListener potentialCompetitor, BroadcastListener nameOfPlayer, String stan ) {

        for (BroadcastListener listener : listeners) {
            if(listener==potentialCompetitor)
            listener.receiveBroadcast(message, nameOfPlayer, stan);
        }
    }

    public interface BroadcastListener {
        public void receiveBroadcast(String message, BroadcastListener potentialCompetitor, String stan);
        public void receiveMove(int x, int y);
        public void receiveNumber(int numberOfPlayer,String s);
    }

}
