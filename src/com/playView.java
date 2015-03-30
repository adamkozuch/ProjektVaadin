package com;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.ArrayList;

/** A start view for navigating to the main view */
public class playView extends VerticalLayout implements View {
    private MyVaadinApplication components;

    public playView(MyVaadinApplication components) {
        this.components = components;
        setSizeFull();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        Button button = new Button("Go to chooseViwe",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        components.navigator.navigateTo("chooseView");
                    }
                });
        addComponent(button);
       int tablica [][] =  new int[5][5];
        GridLayout gr = new GridLayout(5,5);
        for ( int i = 0; i < 5; i++)
            for ( int j = 0; j < 5; j++)
            {

                final int finalI = i;
                final int finalJ = j;
                gr.addComponent(new Button("",new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    if(tablica[finalI][finalJ]!=components.competitorNumber)
                    tablica[finalI][finalJ] = Integer.parseInt(components.playerNumber);

                }
            }), i, j);
            }

        addComponent(gr);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Graj sobie");
    }
}
