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
import com.jfoenix.controls.JFXPasswordField;
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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.slf4j.LoggerFactory;
import sx.ip.IPSXDesktopClient;
import sx.ip.api.UserApi;
import sx.ip.api.UserApiImpl;
import static sx.ip.controllers.NavController.bundle;
import sx.ip.factories.HostServicesControllerFactory;
import sx.ip.utils.BlankSpacesValidator;
import sx.ip.utils.EmailValidator;
import sx.ip.utils.ProxyUtils;

/**
 * Login with email screen controller
 */
public class FXMLLoginEmailController extends NavController implements Initializable {

    static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FXMLLoginEmailController.class);

    /**
     * The reset button instance.
     */
    @FXML
    private Hyperlink btnReset;

    /**
     * The login button instance.
     */
    @FXML
    private JFXButton btnLoginEmail;

    /**
     * The close button instance.
     */
    @FXML
    private JFXButton btnClose;

    /**
     * The error label instance.
     */
    @FXML
    private Label lblError;

    /**
     * The progress bar instance.
     */
    @FXML
    private JFXProgressBar progressBar;

    /**
     * The Go back button.
     */
    @FXML
    private Hyperlink btnBack;

    /**
     * The main anchor pane instance.
     */
    @FXML
    private AnchorPane mainAnchorPane;

    /**
     * The login info pane instance.
     */
    @FXML
    private AnchorPane loginInfoPane;

    /**
     * The email text field instance.
     */
    @FXML
    private JFXTextField userEmail;

    /**
     * The password text field instance.
     */
    @FXML
    private JFXPasswordField userPass;

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
     * Method resposible for the login with email action.
     *
     * @param event An Event representing that the button has been fired.
     */
    @FXML
    private void loginAction(ActionEvent event) throws IOException {
        Task task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                UserApi api = new UserApiImpl();
                boolean response = api.authUser(userEmail.getText().trim(), userPass.getText().trim());
                if (response) {
                    return api.userHasEthWallet();
                }
                return null;
            }
        };
        task.setOnSucceeded((Event ev) -> {
            try {
                if ((Boolean) task.getValue()) {
                    //User goes to dashboard
                    FXMLLoader loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLManualProxy.fxml"), ProxyUtils.getBundle());
                    loader.setControllerFactory(new HostServicesControllerFactory(app.getHostServices()));
                    NavControllerHandle.navigateTo(loader, stage, app);
                } else {
                    FXMLLoader loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLRegisterETH.fxml"), ProxyUtils.getBundle());
                    loader.setControllerFactory(new HostServicesControllerFactory(app.getHostServices()));
                    NavControllerHandle.navigateTo(loader, stage, app);
                }
            } catch (IOException ex) {
                Logger.getLogger(FXMLLoginEmailController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        task.setOnFailed((Event ev) -> {
            Logger.getLogger(FXMLLoginEmailController.class.getName()).log(Level.SEVERE, null, task.getException());
            ProxyUtils.createAndShowAlert(Alert.AlertType.ERROR, bundle.getString("key.main.alert.error.auth.title"), null, task.getException().getMessage(), null);
            LOGGER.error(task.getException().getMessage(), task.getException());
            this.loginInfoPane.setDisable(false);
            this.btnLoginEmail.setDisable(false);
            this.progressBar.setVisible(false);
        });
        Thread thread = new Thread(task);
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Logger.getLogger(FXMLLoginEmailController.class.getName()).log(Level.SEVERE, null, e);
            }
        });
        this.loginInfoPane.setDisable(true);
        this.btnLoginEmail.setDisable(true);
        this.progressBar.setVisible(true);
        this.progressBar.progressProperty().bind(task.progressProperty());
        thread.start();

    }

    /**
     * Method resposible for handling the go back action.
     *
     * @param event An Event representing that the button has been fired.
     */
    @FXML
    private void goBackAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLLandingPage.fxml"), ProxyUtils.getBundle());
        loader.setControllerFactory(new HostServicesControllerFactory(app.getHostServices()));
        NavControllerHandle.navigateTo(loader, stage, app);
    }

    /**
     * Method resposible for the transition to the reset password screen action.
     *
     * @param event An Event representing that the button has been fired.
     */
    @FXML
    private void resetPassword(ActionEvent ae) throws IOException {
        FXMLLoader loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLResetPassword.fxml"), ProxyUtils.getBundle());
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
                    btnLoginEmail.setDisable(true);
                } else {
                    btnLoginEmail.setDisable(false);
                }
            } else {
                userEmail.getValidators().add(emailValidator);
                if (!userEmail.validate()) {
                    btnLoginEmail.setDisable(true);
                } else {
                    btnLoginEmail.setDisable(false);
                }
            }

        });

    }

}
