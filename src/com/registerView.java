package com;

import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

/**
 * Created by adam on 30.03.15.
 */

import java.io.*;
import java.nio.file.Paths;

/**
 * A start view for navigating to the main view
 */
public class registerView extends VerticalLayout implements View {

    public registerView(ApplicationCore components) {

        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        Label imie = new Label("Wprowadz nazwe uzytkownika");
        Label haslo = new Label("Wprowadz haslo dla uzytkownika");
        imie.setWidth(null);
        haslo.setWidth(null);
        final TextField name = new TextField("");
        String textValidation = "Musi byc conajmniej pięć liter";
        final TextField password = new TextField("");


        name.addValidator(new StringLengthValidator(
            textValidation,
                5, 10, true));
        password.addValidator(new StringLengthValidator(
            textValidation,
                5, 10, true));

        Button button = new Button("Zarejestruj",
                event -> {
                    writeToFile(name, password);
                    Notification.show("Uzytkownik : " + String.valueOf(name) + " został zarejestrowany");
                    components.navigator.navigateTo("");
                });


        addComponent(imie);
        addComponent(name);
        addComponent(haslo);
        addComponent(password);
        addComponent(button);

    }

    private void writeToFile(TextField name, TextField password) {
        String content = "nazwa:" + String.valueOf(name) + "; haslo:" + String.valueOf(password);
        try (PrintWriter out = new PrintWriter
                (new BufferedWriter(new FileWriter("/home/adam/Projekt/src/com/uzytkownicy.txt", true)))) {
            out.println(content);
        } catch (IOException e) {
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Zaloguj sie osbie");
    }
}


