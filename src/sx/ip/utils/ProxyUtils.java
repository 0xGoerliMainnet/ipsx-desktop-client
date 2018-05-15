/*
 * Copyright 2018 IP Exchange : https://ip.sx/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sx.ip.utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Pair;

/**
 *
 * @author hygor
 */
public class ProxyUtils {

    /**
     * Method resposible for create a alert.
     *
     * @param type Represent the type of the alert
     *
     * @param title The title of the alert
     *
     * @param header The header of the alert
     *
     * @param content The content text of the alert
     *
     * @return The Alert instance
     *
     */
    public static Alert createAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        return alert;
    }

    /**
     * Method resposible for create the authentication dialog.
     *
     * @param title The title of the dialog
     *
     * @param header The header of the dialog
     *
     * @return The Dialog instance
     *
     */
    public static Dialog createAuthenticationDialog(String title, String header) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(header);

        ButtonType btnLoginType = new ButtonType("Confirm", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnLoginType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField proxyUser = new TextField();
        proxyUser.setPromptText("Username");
        PasswordField proxyPass = new PasswordField();
        proxyPass.setPromptText("Password");

        gridPane.add(new Label("Proxy User:"), 0, 0);
        gridPane.add(proxyUser, 1, 0);
        gridPane.add(new Label("Proxy Password:"), 0, 1);
        gridPane.add(proxyPass, 1, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(btnLoginType);
        loginButton.setDisable(true);

        proxyUser.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(gridPane);

        Platform.runLater(() -> proxyUser.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnLoginType) {
                return new Pair<>(proxyUser.getText(), proxyPass.getText());
            }
            return null;
        });

        return dialog;
    }
    
    public static void createExceptionAlert(String title, String header, String content, Exception ex){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Label label = new Label("The exception stacktrace was:");
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);
        
        alert.getDialogPane().setExpandableContent(expContent);
        
        alert.showAndWait();
    }

}