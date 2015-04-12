package com;



import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;


/** A start view for navigating to the main view */
public class playView extends VerticalLayout implements View {

    int numberToWin=3;
    public playView(ApplicationCore app) {

        app.tab =  new String[5][5];
        app.gridLayout = new GridLayout(5,5);

        setSizeFull();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        Button button = new Button("Idz do ekranu wyboru",
                event -> app.navigator.navigateTo("chooseView")
                );
        addComponent(button);
//TODO nie mam gracza
        createBoard(app.thisPlayerName,app.competitorName, app.gridLayout,app,app.tab);
        addComponent(app.gridLayout);
    }



    private void createBoard(String playerName, String competitorName, GridLayout gr, ApplicationCore applicationCore, String[][] tab) {
//MOŻE TAK BYC TYLKO trzeba wysyłać wiadomośc do drugiego gracza
        //ustawić odpowiednią terminologię
        for ( int i = 0; i < 5; i++)
            for ( int j = 0; j < 5; j++)
            {
                final int finalI = i;
                final int finalJ = j;
                gr.addComponent(new Button("x", event -> {

                   if(applicationCore.MOVE())
                   {

                    applicationCore.competitorListener.receiveMove(finalI,finalJ);
                    gr.getComponent(finalI,finalJ).setVisible(false);
                    applicationCore.yourMove =false;

                    if(tab[finalI][finalJ]!=competitorName) {
                        tab[finalI][finalJ] = playerName;
                        gr.getComponent(finalI,finalJ).setVisible(false);
                        checkIfWinner(competitorName, numberToWin, "Horizontally",tab); //czemu sprawdzam competitora
                    }
                }}), i, j);
            }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Graj sobie");
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
                        if(contionous==true)
                        counter++;
                        else
                            counter=1;
                    }else
                    {
                        contionous=false;
                    }
                }
                if(type=="Verticaly")
                {
                    if (tab[j][i] == playerNumber) {
                        if(contionous==true)
                            counter++;
                        else
                            counter=1;
                    }else
                    {
                        contionous=false;
                    }
                }

                if(counter==numberToWin){
                    Notification.show("Gracz numer:" + playerNumber + " wygral");


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

