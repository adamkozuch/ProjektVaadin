package com;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/** A start view for navigating to the main view */
public class loginView extends VerticalLayout implements View {
    public loginView(ApplicationCore components) {

        final TextField name = new TextField("");
        final PasswordField password = new PasswordField("");

        Button button = new Button("Zaloguj",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {

                        components.thisPlayerName =String.valueOf(name);

                        if(!Broadcaster.listenersMap.keySet().contains(String.valueOf(name)))
                        if(loginUser(name, password, components)==false)
                        {
                            Notification.show("Wprowadziłes nieprawidłowe dane");
                        }
                        Broadcaster.register(components,components.thisPlayerName);
                   //     components.navigator.addView("registerView", new registerView(this));
                    }
                });

        Button registerBtn = new Button("zarejestruj",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        components.navigator.navigateTo("registerView");
                    }
                });


//        setComponentAlignment(name, Alignment.MIDDLE_CENTER);
//        setComponentAlignment(password, Alignment.MIDDLE_CENTER);
//        setComponentAlignment(button, Alignment.MIDDLE_CENTER);
//        setComponentAlignment(registerBtn,Alignment.MIDDLE_CENTER);
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        Label lLogin = new Label("Login");
        lLogin.setWidth(null);
        Label lHaslo = new Label("Haslo");
        lHaslo.setWidth(null);
        lLogin.setStyleName("h3");
        lHaslo.setStyleName("h3");
        HorizontalLayout h = new HorizontalLayout();

        h.addComponent(button);
        h.addComponent(registerBtn);
        
        addComponent(lLogin);
        addComponent(name);
        addComponent(lHaslo);
        addComponent(password);
        addComponent(h);


    }

    private boolean loginUser(TextField name, PasswordField password, ApplicationCore components) {
        try {
            for (String line : Files.readAllLines(Paths.get("/home/adam/Projekt/src/com/uzytkownicy.txt"))) {


                String s[] = line.split(";");
                String n= s[0].substring(s[0].indexOf(":")+1);
                String h= s[1].substring(s[1].indexOf(":")+1);
                String inputName = String.valueOf(name);
                String inputPassword =String.valueOf(password);

                if(n.equals(inputName)&&h.equals(inputPassword))
                {
                    components.navigator.navigateTo("chooseView");
                    return true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
