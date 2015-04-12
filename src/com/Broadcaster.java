package com;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Broadcaster {

    public static final List<BroadcastListener> listeners = new CopyOnWriteArrayList<BroadcastListener>();
    public static final HashMap<String,BroadcastListener> listenersMap = new HashMap<>();

    public static void register(BroadcastListener playerListener, String playerName) {
        listeners.add(playerListener);
        listenersMap.put(playerName,playerListener);
    }

    public static void unregister(BroadcastListener listener) {
        listeners.remove(listener);
    }

    public static void sendRequest(final String message, BroadcastListener competitorListener,BroadcastListener thisListener, String stan) {
            competitorListener.receivePrpositionFromCompetitor(message, competitorListener,thisListener, stan);
        }

    public interface BroadcastListener {
        public void receivePrpositionFromCompetitor(String message, BroadcastListener competitorListener,BroadcastListener thisListener,String stan);
        public void receiveMove(int x, int y);
        public void receivePlayerNumber(int numberOfPlayer, String s);
    }
}
