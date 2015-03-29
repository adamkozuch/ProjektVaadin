package com;

import com.vaadin.annotations.Push;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

@Push
public class MyVaadinApplication extends UI  implements Broadcaster.BroadcastListener {

    int partner;
    String playerNumber;
    String nazwaGracza;
    String nazwaKonkurenta;
    Navigator navigator;
    Label playerLabel = new Label();
    Window windowAskPlayer;


    protected void init(VaadinRequest request) {

//region askWindow
        windowAskPlayer = new Window("Czy chcesz zagrać z graczem :"+nazwaKonkurenta );
        VerticalLayout verticalLayout = new VerticalLayout();
        Button asking = new Button("Accept", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                    navigator.navigateTo("playView");
                     windowAskPlayer.close();
            }
        });
        verticalLayout.addComponent(asking);
        windowAskPlayer.setContent(verticalLayout);
//endregion

//region navigation
        getPage().setTitle("Navigation Example");

        // Create a navigator to control the views
        navigator = new Navigator(this, this);

        // Create and register the views
        navigator.addView("", new loginView(this));
        navigator.addView("chooseView", new chooseView(this));
        navigator.addView("playView", new playView(this));
//endregion

        Broadcaster.register(this);
    }

    @Override
    public void detach() {
        Broadcaster.unregister(this);
        super.detach();
    }

    @Override
    public void receiveBroadcast(final String message, Broadcaster.BroadcastListener potentialCompetitor, Broadcaster.BroadcastListener ls) {
        access(new Runnable() {
            @Override
            public void run() {
                nazwaKonkurenta =potentialCompetitor.toString();
                addWindow(windowAskPlayer);

                Notification n = new Notification("Message received",
                        message, Notification.Type.TRAY_NOTIFICATION);
                n.show(getPage());
            }
        });
    }
}
