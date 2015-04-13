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
Label l  = new Label("Gracz: "+app.thisPlayerName);
        l.setWidth(null);
        Table players = new Table("Players");
        players.setWidth("300");
        players.setHeight("500");
        players.addContainerProperty("Name", String.class, null);

        int counter = 1;

        players.setSelectable(true);
        players.setImmediate(true);

        players.addValueChangeListener(event ->
        {app.competitorListener = Broadcaster.listenersMap.get(players.getValue());
                app.competitorName=String.valueOf(players.getValue());});

//endregion
//region button that choose player
        final Button choose = new Button("Zaproponuj grę");

        choose.addClickListener(event -> {
            if(app.competitorListener!=null)
            Broadcaster.sendRequest("wysylam do ciebie wiadomosc",
                    app.competitorListener,app,
                     "pytanie");
            else
                Notification.show("Nie wybrałeś przeciwnika");
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
