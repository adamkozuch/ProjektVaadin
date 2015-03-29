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

    public static void broadcast(final String message,BroadcastListener potentialCompetitor,BroadcastListener broadcastListener) {

        for (BroadcastListener listener : listeners) {
            if(listener==potentialCompetitor)
            listener.receiveBroadcast(message,potentialCompetitor,broadcastListener);

        }
    }

    public interface BroadcastListener {
        public void receiveBroadcast(String message, BroadcastListener potentialCompetitor, BroadcastListener ls);
    }

}
