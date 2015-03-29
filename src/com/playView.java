package com;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

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

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Graj sobie");
    }
}
