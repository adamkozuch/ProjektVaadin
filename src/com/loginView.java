package com;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

/** A start view for navigating to the main view */
public class loginView extends VerticalLayout implements View {
    private MyVaadinApplication components;

    public loginView(MyVaadinApplication components) {
        this.components = components;
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setHeight("100");
        setWidth("1000");


        final TextField name = new TextField("");
        Button button = new Button("Zaloguj",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        components.playerLabel.setValue(String.valueOf(name));

                        components.navigator.navigateTo("chooseView");
                    }
                });
        addComponent(button);
        //setComponentAlignment(button,Alignment.MIDDLE_CENTER);
        addComponent(name);

        //setComponentAlignment(name,Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Zaloguj sie osbie");
    }
}
