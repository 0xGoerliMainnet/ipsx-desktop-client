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
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.slf4j.LoggerFactory;
import sx.ip.IPSXDesktopClient;
import sx.ip.controllers.NavController;
import static sx.ip.controllers.NavController.bundle;

/**
 * Class responsible for hold all useful functions.
 */
public class ProxyUtils {

    /**
     * Class logger
     */
    static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ProxyUtils.class);

    /**
     * Method resposible for create a alert.
     *
     * @param type Represent the type of the alert
     * @param title The title of the alert
     * @param header The header of the alert
     * @param content The content text of the alert
     * @param is InputStream representing the IPSX icon
     *
     * @return The Alert instance.
     *
     */
    public static Alert createAlert(Alert.AlertType type, String title, String header, String content, InputStream is) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();

        if (is == null) {
            is = IPSXDesktopClient.class.getResourceAsStream("resources/imgs/icon.png");
        }

        alertStage.getIcons().add(new Image(is));

        return alert;
    }

    /**
     * Method resposible for create a confirm alert.
     *
     * @param is InputStream representing the IPSX icon
     *
     * @return A boolean indicating the user response
     *
     */
    public static boolean createQuestionRemoveAll(InputStream is) {

        Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType confirm = new ButtonType(getBundle().getString("key.main.dialog.yes"));
        ButtonType cancel = new ButtonType(getBundle().getString("key.main.dialog.no"));
        dialogoExe.setTitle(getBundle().getString("key.main.dialog.title"));
        Stage alertStage = (Stage) dialogoExe.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(is));
        dialogoExe.setHeaderText(null);
        dialogoExe.setContentText(getBundle().getString("key.main.dialog.message"));
        dialogoExe.getButtonTypes().setAll(confirm, cancel);

        dialogoExe.showAndWait();

        return dialogoExe.getResult() == confirm;

    }

    /**
     * Method that will create a confirmation alert.
     *
     * @param is InputStream representing the IPSX icon
     * @param title The title of the window
     * @param content The content of the window (main text)
     *
     * @return A boolean indicating the user response
     *
     */
    public static boolean createQuestionPane(InputStream is, String title, String content) {

        Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType confirm = new ButtonType(getBundle().getString("key.main.dialog.yes"));
        ButtonType cancel = new ButtonType(getBundle().getString("key.main.dialog.no"));
        dialogoExe.setTitle(title);
        Stage alertStage = (Stage) dialogoExe.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(is));
        dialogoExe.setHeaderText(null);
        dialogoExe.setContentText(content);
        dialogoExe.getButtonTypes().setAll(confirm, cancel);

        dialogoExe.showAndWait();

        return dialogoExe.getResult() == confirm;

    }

    /**
     * Method resposible for create and show a alert.
     *
     * @param type Represent the type of the alert
     * @param title The title of the alert
     * @param header The header of the alert
     * @param content The content text of the alert
     * @param is InputStream representing the IPSX icon
     *
     */
    public static void createAndShowAlert(Alert.AlertType type, String title, String header, String content, InputStream is) {
        Alert alert = createAlert(type, title, header, content, is);
        alert.showAndWait();
    }

    /**
     * Method resposible for create the authentication dialog.
     *
     * @param title The title of the dialog
     * @param header The header of the dialog
     * @param is InputStream representing the IPSX icon
     *
     * @return The Dialog instance
     *
     */
    public static Dialog createAuthenticationDialog(String title, String header, InputStream is) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(header);

        Stage alertStage = (Stage) dialog.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(is));

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
     * Method resposible for saving user credentials.
     *
     * @param credential The credential to be saved
     * @param type The type of the credential
     *
     */
    public static void saveCredentials(Object credential, CredentialType type) {
        Preferences prefs = Preferences.userRoot().node(ProxyUtils.class.getName());

        switch (type) {
            case STRING:
                prefs.put("username", (String) credential);
                break;

            case BYTEARRAY:
                HashMap<byte[], Integer> castCredential = (HashMap<byte[], Integer>) credential;
                byte[] encryptedPassword = castCredential.keySet().iterator().next();
                prefs.putInt("cipherKeyLength", castCredential.get(encryptedPassword));
                prefs.putByteArray("encryptedPassword", encryptedPassword);
                break;
        }
    }

    /**
     * Method resposible for erasing user credentials.
     */
    public static void eraseCredentials() {
        Preferences prefs = Preferences.userRoot().node(ProxyUtils.class.getName());
        prefs.put("username", "");
        prefs.putByteArray("encryptedPassword", new byte[]{});
        prefs.putInt("cipherKeyLength", 0);
    }

    /**
     * Method resposible for loading user credentials.
     *
     * @param credential The credential key to be loaded
     * @param type The type of the credential
     *
     * @return Returns the credendial to be cast
     *
     */
    public static Object loadCredentials(String credential, CredentialType type) {

        Preferences prefs = Preferences.userRoot().node(ProxyUtils.class.getName());

        switch (type) {
            case STRING:
                return prefs.get(credential, "");
            case BYTEARRAY:
                HashMap<byte[], Integer> encryptedPassword = new HashMap<>();
                encryptedPassword.put(prefs.getByteArray("encryptedPassword", new byte[]{}), prefs.getInt("cipherKeyLength", 0));
                return encryptedPassword;
            default:
                return new Object();
        }
    }

    /**
     * Method resposible for create the exception alert.
     *
     * @param title The title of the alert
     * @param header The header of the alert
     * @param content The content text
     * @param stackMessage The stack title
     * @param is InputStream representing the IPSX icon
     *
     * @param ex The exception.
     *
     */
    public static void createExceptionAlert(String title, String header, String content, String stackMessage, Exception ex, InputStream is) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        if (is == null) {
            is = IPSXDesktopClient.class.getResourceAsStream("resources/imgs/icon.png");
        }
        alertStage.getIcons().add(new Image(is));

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

    /**
     * Method resposible for verify the URL format.
     *
     * @param url The URL to be verified
     *
     * @return A boolean indicating if the url is valid or not
     *
     */
    public static boolean isValid(String url) {
        String urlRegex = "^(https|http|ftp|ftps)(:\\/\\/)([a-z0-9]+)(\\.[a-z]{2,})*(\\/?[a-zA-Z0-9])+\\.(pac)$";
        Pattern pattern = Pattern.compile(urlRegex);
        Matcher m = pattern.matcher(url);
        return m.matches();
    }

    /**
     * Method resposible for validate the url response code.
     *
     * @param url The URL to be validated
     *
     * @throws IOException
     *
     * @return A boolean indicating if the url is valid or not
     */
    public static boolean validateUrl(String url) throws IOException {
        if (isValid(url)) {

            try {
                HttpResponse<String> jsonResponse = Unirest.get(url)
                        .header("Content-Type", "application/json")
                        .header("accept", "application/json")
                        .queryString("access_token", NavController.accessToken)
                        .asString();

                return URLStatus.HTTP_OK.getStatusCode() == jsonResponse.getStatus();
            } catch (UnirestException ex) {
                Logger.getLogger(ProxyUtils.class.getName()).log(Level.SEVERE, null, ex);
                ProxyUtils.createExceptionAlert(bundle.getString("key.main.dialog.exception.title"), null, ex.getMessage(), bundle.getString("key.main.dialog.exception.stack.text"), ex, null);
                LOGGER.error(ex.getMessage(), ex);
            }
        }
        return false;
    }

}
