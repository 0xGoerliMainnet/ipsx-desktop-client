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
package sx.ip.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import org.slf4j.LoggerFactory;
import sx.ip.IPSXDesktopClient;
import sx.ip.api.UserApi;
import sx.ip.api.UserApiImpl;
import sx.ip.utils.BlankSpacesValidator;
import sx.ip.utils.EmailValidator;
import sx.ip.utils.ProxyUtils;

/**
 * Reset password screen controller
 */
public class FXMLResetPasswordController extends NavController implements Initializable {

    /**
     * The logger Object.
     */
    static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FXMLResetPasswordController.class);

    /**
     * The back to Login button instance.
     */
    @FXML
    private Hyperlink btnBack;

    /**
     * The send link button instance.
     */
    @FXML
    private JFXButton btnSendLink;

    /**
     * The close button instance.
     */
    @FXML
    private JFXButton btnClose;

    /**
     * The progress bar instance.
     */
    @FXML
    private JFXProgressBar progressBar;

    /**
     * The main anchor pane instance.
     */
    @FXML
    private AnchorPane mainAnchorPane;

    /**
     * The login info anchor pane instance.
     */
    @FXML
    private AnchorPane loginInfoPane;

    /**
     * The email text field instance.
     */
    @FXML
    private JFXTextField userEmail;

    /**
     * The FXMLLoarder instance.
     */
    private FXMLLoader loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLLoginEmail.fxml"), ProxyUtils.getBundle());

    /**
     * Method resposible for handling the close action.
     *
     * @param event An Event representing that the button has been fired.
     */
    @FXML
    private void handleCloseAction(ActionEvent event) {
        stage.close();
    }

    /**
     * Method resposible for the send link action.
     *
     * @param event An Event representing that the button has been fired.
     */
    @FXML
    private void sendLinkAction(ActionEvent event) throws IOException {
        Task task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                UserApi api = new UserApiImpl();
                boolean response = api.resetPassword(userEmail.getText().trim());
                return response;
            }
        };
        task.setOnSucceeded((Event ev) -> {
            try {
                if ((Boolean) task.getValue()) {
                    userEmail.clear();
                    ProxyUtils.createAndShowAlert(Alert.AlertType.INFORMATION, bundle.getString("key.main.alert.info.title"), null, bundle.getString("key.main.send.link"), null);
                    NavControllerHandle.navigateTo(loader, stage, app);
                } else {
                    ProxyUtils.createAndShowAlert(Alert.AlertType.INFORMATION, bundle.getString("key.main.alert.error.title"), null, bundle.getString("key.main.send.link.error"), null);
                }
            } catch (IOException ex) {
                Logger.getLogger(FXMLLoginEmailController.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.progressBar.setVisible(false);
            this.loginInfoPane.setDisable(false);
            this.btnSendLink.setDisable(false);
        });
        task.setOnFailed((Event ev) -> {
            Logger.getLogger(FXMLResetPasswordController.class.getName()).log(Level.SEVERE, null, task.getException());
            ProxyUtils.createAndShowAlert(Alert.AlertType.ERROR, bundle.getString("key.main.alert.error.title"), null, task.getException().getMessage(), null);
            LOGGER.error(task.getException().getMessage(), task.getException());
            this.progressBar.setVisible(false);
            this.loginInfoPane.setDisable(false);
            this.btnSendLink.setDisable(false);
        });
        Thread thread = new Thread(task);
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Logger.getLogger(FXMLResetPasswordController.class.getName()).log(Level.SEVERE, null, e);
            }
        });
        this.loginInfoPane.setDisable(true);
        this.btnSendLink.setDisable(true);
        this.progressBar.setVisible(true);
        this.progressBar.progressProperty().bind(task.progressProperty());
        thread.start();
    }

    /**
     * Method resposible for the transition to the Login screen.
     *
     * @param event An Event representing that the button has been fired.
     */
    @FXML
    private void onBackAction(ActionEvent ae) throws IOException {
        NavControllerHandle.navigateTo(loader, stage, app);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        BlankSpacesValidator blankValidatorEmail = new BlankSpacesValidator();
        blankValidatorEmail.setMessage(rb.getString("key.main.validator.empty"));

        EmailValidator emailValidator = new EmailValidator();
        emailValidator.setMessage(rb.getString("key.main.validator.email"));

        userEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                userEmail.getValidators().add(blankValidatorEmail);
                if (!userEmail.validate()) {
                    btnSendLink.setDisable(true);
                } else {
                    btnSendLink.setDisable(false);
                }
            } else {
                userEmail.getValidators().add(emailValidator);
                if (!userEmail.validate()) {
                    btnSendLink.setDisable(true);
                } else {
                    btnSendLink.setDisable(false);
                }
            }

        });

    }

}
