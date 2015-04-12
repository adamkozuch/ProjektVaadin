package com;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

/**
 * A start view for navigating to the main view
 */
public class chooseView extends VerticalLayout implements View {

    public chooseView(ApplicationCore app) {

        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);


//region definition of table

        Table players = new Table("Players");
        players.setWidth("300");
        players.setHeight("500");
        players.addContainerProperty("Name", String.class, null);

        int counter = 1;

        players.setSelectable(true);
        players.setImmediate(true);
        //po co jest ten kod?
        players.addValueChangeListener(event -> app.competitorListener =Broadcaster.listenersMap.get(players.getValue()));



//endregion
//region button that choose player
        final Button choose = new Button("Zaproponuj grę");

        choose.addClickListener(event -> {
            Broadcaster.sendRequest("wysylam do ciebie wiadomosc",
                    app.competitorListener,app,
                     "pytanie");
        });

        for (String listener : Broadcaster.listenersMap.keySet()) {
            if (listener != app.thisPlayerName)
                players.addItem(new Object[]{listener}, listener);
        }

        addComponent(players);
        addComponent(choose);

    }



    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("wybierz sobie");
    }
}
