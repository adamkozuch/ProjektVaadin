package com;

import com.vaadin.annotations.Push;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

@Push
public class ApplicationCore extends UI  implements Broadcaster.BroadcastListener {


    GridLayout gridLayout;
    String tab[][];
    String thisPlayerName;
    Broadcaster.BroadcastListener competitorListener;
    Broadcaster.BroadcastListener myListener;
    Navigator navigator;
    Window windowAskForGame;
    boolean yourMove = false;
    public String competitorName;

    protected void init(VaadinRequest request) {

        windowAskForGame = new Window("Czy chcesz zagrać z graczem :");

        VerticalLayout verticalLayout = new VerticalLayout();
        Button asking = new Button("Accept", clickEvent -> {
                 navigator.addView("playView",new playView(this));
                 navigator.navigateTo("playView");
                 windowAskForGame.close();
                 Broadcaster.sendRequest("Ok fanie", competitorListener,this, "odpowiedz");
    //             competitorListener.receivePlayerNumber(thisPlayerNumber, "");
        });
        verticalLayout.addComponent(asking);
        windowAskForGame.setContent(verticalLayout);


//region navigation
        getPage().setTitle("Navigation Example");
        myListener =this;
        // Create a navigator to control the views
        navigator = new Navigator(this, this);

        // Create and register the views
        navigator.addView("", new loginView(this));
        navigator.addView("chooseView", new chooseView(this));
        navigator.addView("registerView", new registerView(this));
//endregion

    }

    @Override
    public void detach() {
        Broadcaster.unregister(this);
        super.detach();
    }

    @Override
    public void receivePrpositionFromCompetitor(final String message, Broadcaster.BroadcastListener cls,Broadcaster.BroadcastListener thisListener, String stan) {
        access(new Runnable() {
            @Override
            public void run() {
                if (stan.equals("pytanie")) {
                    ApplicationCore.this.competitorListener =thisListener ;  //przekazuje sobie competitora
                    ApplicationCore.this.addWindow(windowAskForGame);
                     yourMove=false;//gracz proszący jest pierwszy

                } else if (stan.equals("odpowiedz")) {
                    Notification n = new Notification("Gracz : " + cls.toString(),
                            message, Notification.Type.TRAY_NOTIFICATION);
                    ApplicationCore.this.competitorListener=cls;
                    n.show(ApplicationCore.this.getPage());
                    yourMove=true;//gracz proszący jest pierwszy

                    navigator.addView("playView",new playView(ApplicationCore.this) );
                    navigator.navigateTo("playView");
                }
            }

        });
    }

        @Override
        public void receiveMove ( int x, int y){
            access(() -> {
                gridLayout.getComponent(x,y).setVisible(false);
                yourMove=true;
            });

        }

    @Override
    public void receivePlayerNumber(int numberOfPlayer, String s) {

    }
//    @Override
//    public void receivePlayerNumber(int competitorNumber, String stan){
//        access(() -> {
//            if(stan=="pytanie")
//            competitorPlayerNumber =competitorNumber;
//        });
//
//    }


    public void logikaGrania()
    {
        competitorListener.receiveMove(6, 6);
    }

    public boolean MOVE()
    {
        return this.yourMove;
    }


}
