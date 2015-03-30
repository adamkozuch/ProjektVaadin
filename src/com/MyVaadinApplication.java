package com;

import com.vaadin.annotations.Push;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

@Push
public class MyVaadinApplication extends UI  implements Broadcaster.BroadcastListener {

    int partner;
    String playerNumber;
    Integer competitorNumber;
    String nazwaGracza;
    Broadcaster.BroadcastListener listenerOfCompetitor;
    Broadcaster.BroadcastListener listenerOfME;
    Navigator navigator;
    Label playerLabel = new Label();
    Window windowAskPlayer;


    protected void init(VaadinRequest request) {

//region askWindow
        windowAskPlayer = new Window("Czy chcesz zagrać z graczem :");
        VerticalLayout verticalLayout = new VerticalLayout();
        Button asking = new Button("Accept", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                     navigator.navigateTo("playView");
                     windowAskPlayer.close();
                     Broadcaster.broadcast("Ok fanie",listenerOfCompetitor,listenerOfME, "odpowiedz");
                     listenerOfCompetitor.receiveNumber(Integer.parseInt(playerNumber),"");
            }
        });
        verticalLayout.addComponent(asking);
        windowAskPlayer.setContent(verticalLayout);
//endregion

//region navigation
        getPage().setTitle("Navigation Example");

        // Create a navigator to control the views
        navigator = new Navigator(this, this);

        // Create and register the views
        navigator.addView("", new loginView(this));
        navigator.addView("chooseView", new chooseView(this));
        navigator.addView("playView", new playView(this));
        navigator.addView("registerView", new registerView(this));

//endregion

        listenerOfME=this;
        Broadcaster.register(this);

    }

    @Override
    public void detach() {
        Broadcaster.unregister(this);
        super.detach();
    }

    @Override
    public void receiveBroadcast(final String message, Broadcaster.BroadcastListener playerWhoAsk, String stan) {
        access(new Runnable() {
            @Override
            public void run() {
                if (stan == "pytanie") {
                    listenerOfCompetitor = playerWhoAsk;
                    addWindow(windowAskPlayer);
                    listenerOfCompetitor.receiveNumber(Integer.parseInt(playerNumber),"pytanie");
                } else if (stan == "odpowiedz") {
                    Notification n = new Notification("Zgodził się gracz : " + playerWhoAsk.toString(),
                            message, Notification.Type.TRAY_NOTIFICATION);
                    n.show(getPage());
                    navigator.navigateTo("playView");
                }

            }
        });}

        @Override
        public void receiveMove ( int x, int y){
            access(new Runnable() {
                @Override
                public void run() {


                }
            });

        }
    @Override
    public void receiveNumber ( int numberOfPlayer, String stan){
        access(new Runnable() {
            @Override
            public void run() {

                if(stan=="pytanie")
                competitorNumber=numberOfPlayer;
            }
        });

    }


    public void logikaGrania()
    {
        listenerOfCompetitor.receiveMove(6, 6);
    }


}
