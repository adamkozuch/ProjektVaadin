package com;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

@Push
@Theme("runo")
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
    String znak ;

    protected void init(VaadinRequest request) {

        windowAskForGame = new Window();
        windowAskForGame.center();

        VerticalLayout verticalLayout = new VerticalLayout();
        Button asking = new Button("Akceptuj", clickEvent -> {
                 navigator.addView("playView",new playView(this));
                 navigator.navigateTo("playView");
                 windowAskForGame.close();
                 Broadcaster.sendRequest("OK", competitorListener,this, "odpowiedz");
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
                    windowAskForGame.setCaption("Gracz :"+thisListener+" proponuje grę");
                    ApplicationCore.this.addWindow(windowAskForGame);
                     yourMove=false;//gracz proszący jest pierwszy
                    znak="x";

                } else if (stan.equals("odpowiedz")) {
                    Notification n = new Notification("Gracz : " + cls.toString(),
                            message, Notification.Type.TRAY_NOTIFICATION);
                    ApplicationCore.this.competitorListener=thisListener;
                    n.show(ApplicationCore.this.getPage());
                    yourMove=true;//gracz proszący jest pierwszy

                    navigator.addView("playView",new playView(ApplicationCore.this) );
                    navigator.navigateTo("playView");
                    znak ="o";
                }
            }

        });
    }

        @Override
        public void receiveMove (int x, int y){
            access(() -> {
                String s;
                if(znak.equals("o"))
                    s="x";
                else
                    s="o";
                gridLayout.getComponent(x,y).setCaption(s);
                yourMove=true;
            });
            Notification.show("Czekaj aż przeciwnik wykona ruch",Notification.Type.ASSISTIVE_NOTIFICATION);
        }

    public boolean MOVE()
    {
        return this.yourMove;
    }


    void createBoard(String playerName) {

        for ( int i = 0; i < 5; i++)
            for ( int j = 0; j < 5; j++)
            {
                final int finalI = i;
                final int finalJ = j;
                gridLayout.addComponent(new Button("", event -> {
                    if (MOVE()) {
                        if (tab[finalI][finalJ] != competitorName||tab[finalI][finalJ] != playerName)
                        {
                            yourMove = false;
                            gridLayout.getComponent(finalI,finalJ).setCaption(znak);
                            competitorListener.receiveMove(finalI, finalJ);
                            tab[finalI][finalJ] = playerName;
                            checkIfWinner(competitorName, 3, "Horizontally", tab); //czemu sprawdzam competitora
                            checkIfWinner(competitorName,3,"Verticaly",tab);
                        }
                    } else
                    {
                        Notification.show("Czekaj aż przeciwnik wykona ruch");
                    }
                }), i, j);
                gridLayout.getComponent(finalI,finalJ).setHeight("40px");
                gridLayout.getComponent(finalI,finalJ).setWidth("40px");
            }
    }

    public void checkIfWinner(String playerNumber, int numberToWin, String type,String[][] tab)
    {
        boolean contionous=false;
        int counter = 0;
        for ( int i= 0; i< 5; i++)
            for ( int j = 0; j < 5; j++)
            {
                if(type=="Horizontally") {
                    if (tab[i][j] == playerNumber) {
                        if(contionous==true) {
                            counter++;
                            if (counter == numberToWin) {
                                Notification.show("Gracz numer:" + playerNumber + " wygral");
                            }
                        }
                        else {
                            counter = 1;
                            contionous=true;
                        }
                    }else
                    {
                        contionous=false;
                    }
                }
                if(type=="Verticaly")
                {
                    if (tab[j][i] == playerNumber) {
                        if(contionous==true) {
                            counter++;
                            if (counter == numberToWin) {
                                Notification.show("Gracz numer:" + playerNumber + " wygral");
                            }
                        }
                        else {
                            counter = 1;
                            contionous=false;
                        }
                    }else
                    {
                        contionous=false;
                    }
                }
            }
    }

    public void checkCross() {
        for (int i = 0; 5 < i; i++) {
            boolean contionous = false;
            int counter = 0;

            for (int j = 0; j < 5; j++) {
            }
        }
    }

    public void sendMove(int i, int j,ApplicationCore applicationCore)
    {
        applicationCore.competitorListener.receiveMove(i,j);
    }



}
