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
        app.gridLayout.setStyleName("header");

        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        Button button = new Button("Idz do ekranu wyboru",
                event -> app.navigator.navigateTo("chooseView")
                );
        button.setStyleName("");

        Label l =new Label("Gracz : " + app.thisPlayerName);
        l.setStyleName("h1");
        l.setWidth(null);
        addComponent(l);
        setComponentAlignment(l,Alignment.MIDDLE_CENTER);
        addComponent(button);
        app.createBoard(app.thisPlayerName);
        addComponent(app.gridLayout);
    }



    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Graj sobie");
    }
}

