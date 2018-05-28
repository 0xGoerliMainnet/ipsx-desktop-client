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

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Pair;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Class responsible for hold all useful functions.
 */
public class ProxyUtils {

    /**
     * Method resposible for create a alert.
     *
     * @param type 
     *          Represent the type of the alert
     * @param title 
     *          The title of the alert
     * @param header 
     *          The header of the alert
     * @param content 
     *          The content text of the alert
     * 
     * @return The Alert instance.
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
     * Method resposible for create a confirm alert.
     * 
     * @return A boolean indicating the user response
     *
     */
    public static boolean createQuestionPane(){

        Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType confirm = new ButtonType(getBundle().getString("key.main.dialog.yes"));
        ButtonType cancel = new ButtonType(getBundle().getString("key.main.dialog.no"));
        dialogoExe.setTitle(getBundle().getString("key.main.dialog.title"));
        dialogoExe.setHeaderText(null);
        dialogoExe.setContentText(getBundle().getString("key.main.dialog.message"));
        dialogoExe.getButtonTypes().setAll(confirm, cancel);
        
        dialogoExe.showAndWait();
        
        return dialogoExe.getResult() == confirm;
        
    }
    
    /**
     * Method resposible for create and show a alert.
     *
     * @param type 
     *          Represent the type of the alert
     * @param title 
     *          The title of the alert
     * @param header 
     *          The header of the alert
     * @param content 
     *          The content text of the alert
     *
     */
    public static void createAndShowAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = createAlert(type, title, header, content);        
        alert.showAndWait();
    }
    

    /**
     * Method resposible for create the authentication dialog.
     *
     * @param title 
     *          The title of the dialog
     * @param header 
     *          The header of the dialog
     * 
     * @return The Dialog instance
     *
     */
    public static Dialog createAuthenticationDialog(String title, String header) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(header);

        ButtonType btnConfirmType = new ButtonType(getBundle().getString("key.main.button.confirm"), ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnConfirmType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));
        
        JFXTextField proxyUser = new JFXTextField();
        proxyUser.setPromptText(getBundle().getString("key.main.dialog.auth.username.prompt"));
        JFXPasswordField proxyPass = new JFXPasswordField();
        proxyPass.setPromptText(getBundle().getString("key.main.dialog.auth.password.prompt"));

        gridPane.add(new Label(getBundle().getString("key.main.dialog.auth.username.label")), 0, 0);
        gridPane.add(proxyUser, 1, 0);
        gridPane.add(new Label(getBundle().getString("key.main.dialog.auth.password.label")), 0, 1);
        gridPane.add(proxyPass, 1, 1);

        Node confirmButton = dialog.getDialogPane().lookupButton(btnConfirmType);
        confirmButton.setDisable(true);

        proxyUser.textProperty().addListener((observable, oldValue, newValue) -> {
            confirmButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(gridPane);

        Platform.runLater(() -> proxyUser.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnConfirmType) {
                return new Pair<>(proxyUser.getText(), proxyPass.getText());
            }
            return null;
        });

        return dialog;
    }
    
    
    /**
     * Method resposible for create the exception alert.
     *
     * @param title 
     *          The title of the alert
     * @param header 
     *          The header of the alert
     * @param content 
     *          The content text
     * @param stackMessage 
     *          The stack title 
     * 
     * @param ex The exception.
     *
     */
    public static void createExceptionAlert(String title, String header, String content, String stackMessage,  Exception ex) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Label label = new Label(stackMessage);

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
    
    /**
     * @return the bundle language.
     */
    public static ResourceBundle getBundle() {
        return ResourceBundle.getBundle("sx.ip.bundles.bundle", new Locale("en", "EN"));
    }

}
