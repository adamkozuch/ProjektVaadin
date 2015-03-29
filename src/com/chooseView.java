package com;

import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

/**
 * A start view for navigating to the main view
 */
public class chooseView extends VerticalLayout implements View {
    private MyVaadinApplication components;

    public chooseView(MyVaadinApplication components) {
        this.components = components;
        setSizeUndefined();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Button button = new Button("Go to playView",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        components.navigator.navigateTo("playView");
                    }
                });
        addComponent(button);

//region definition of table
        Table players = new Table("Players");
        players.addContainerProperty("Name", String.class, null);

        int counter = 0;
        for (Broadcaster.BroadcastListener listener : Broadcaster.listeners) {
            if (listener != this)
                players.addItem(new Object[]{listener.toString()}, counter);
            counter++;
        }
        players.setSelectable(true);
        players.setImmediate(true);
        players.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent event) {
                components.playerNumber = players.getValue().toString();
            }
        });

//endregion
//region button that choose player
        final Button choose = new Button("Choose");

        choose.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                Broadcaster.broadcast("wysylam do ciebie wiadomosc", Broadcaster.listeners.get(Integer.parseInt(components.playerNumber)),null);
            }
        });

        addComponent(choose);
        addComponent(components.playerLabel);
        addComponent(players);




    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("wybierz sobie");
    }
}
