package com;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/** A start view for navigating to the main view */
public class loginView extends VerticalLayout implements View {
    private MyVaadinApplication components;

    public loginView(MyVaadinApplication components) {
        this.components = components;
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setHeight("100");
        setWidth("1000");


        final TextField name = new TextField("");
        final TextField password = new TextField("");

        Button button = new Button("Zaloguj",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        components.playerLabel.setValue(String.valueOf(name));
                        components.nazwaGracza=String.valueOf(name);

                        if(loginUser(name, password, components)==false)
                        {
                            Notification.show("Wprowadziłes nieprawidłowe dane");
                        }
                    }
                });

        Button registerBtn = new Button("zarejestruj",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        components.navigator.navigateTo("registerView");
                    }
                });


        addComponent(button);

        addComponent(name);
        addComponent(password);
        addComponent(registerBtn);


    }

    private boolean loginUser(TextField name, TextField password, MyVaadinApplication components) {
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
        Notification.show("Zaloguj sie osbie");
    }
}
