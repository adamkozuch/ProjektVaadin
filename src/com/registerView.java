package com;

import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

/**
 * Created by adam on 30.03.15.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/** A start view for navigating to the main view */
    public class registerView extends VerticalLayout implements View {
        private ApplicationCore components;

        public registerView(ApplicationCore components) {
            this.components = components;
            setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
            setHeight("100");
            setWidth("1000");


            final TextField name = new TextField("");
            name.setSizeFull();
            final TextField password = new TextField("");
            password.setHeight("400");
            name.addValidator(new StringLengthValidator(
                    "Musi byc conajmniej pięć liter",
                    5, 10, true));
            password.addValidator(new StringLengthValidator(
                    "Musi być conajmniej pięć liter",
                    5, 10, true));

            Button button = new Button("Zarejestruj",
                    event -> {
                        writeToFile(name, password);
                          components.navigator.navigateTo("");
                    });
            addComponent(button);

            addComponent(name);
            addComponent(password);

            //setComponentAlignment(name,Alignment.MIDDLE_CENTER);
        }

    private void writeToFile(TextField name, TextField password) {
        File file = new File("/home/adam/Projekt/src/com/uzytkownicy.txt");
        FileWriter fw = null;
        try {
            fw = new FileWriter(file.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bw = new BufferedWriter(fw);
        String content ="nazwa:" + String.valueOf(name)+"; haslo:"+String.valueOf(password);
        try {
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
        public void enter(ViewChangeListener.ViewChangeEvent event) {
            Notification.show("Zaloguj sie osbie");
        }
    }


