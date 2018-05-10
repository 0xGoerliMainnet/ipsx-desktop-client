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

import java.util.Optional;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 *
 * @author hygor
 */
public class ProxyUtils {

    public static Alert createAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        return alert;
    }

    public static Dialog createAuthenticationDialog(String title, String header) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        
        ButtonType btnLoginType = new ButtonType("Login", ButtonData.OK_DONE);
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

}
