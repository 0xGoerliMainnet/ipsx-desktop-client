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
import com.mashape.unirest.http.exceptions.UnirestException;
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
import static sx.ip.controllers.NavController.bundle;
import sx.ip.factories.HostServicesControllerFactory;
import sx.ip.utils.BlankSpacesValidator;
import sx.ip.utils.ETHWalletValidator;
import sx.ip.utils.ProxyUtils;

public class FXMLRegisterETHController extends NavController implements Initializable {

    /**
     * The logger Object.
     */
    static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FXMLRegisterETHController.class);

    /**
     * The sign in with another account button instance.
     */
    @FXML
    private Hyperlink btnSignAnotherAccount;

    /**
     * The done button instance.
     */
    @FXML
    private JFXButton btnDone;

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
     * The main anchor pane instance.
     */
    @FXML
    private AnchorPane loginInfoPane;

    /**
     * The ETH wallet name text field instance.
     */
    @FXML
    private JFXTextField txtWalletName;

    /**
     * The ETH wallet address text field instance.
     */
    @FXML
    private JFXTextField txtETHAdrr;

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
     * Method resposible for the transition to the proxies screen.
     *
     * @param event An Event representing that the button has been fired.
     */
    @FXML
    private void doneAction(ActionEvent event) throws IOException {
        Task task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                UserApi api = new UserApiImpl();
                return api.addEthAddress(txtWalletName.getText(), txtETHAdrr.getText());
            }
        };
        task.setOnSucceeded((Event ev) -> {
            try {
                if ((Boolean) task.getValue()) {
                    ProxyUtils.createAndShowAlert(Alert.AlertType.INFORMATION, bundle.getString("key.main.alert.info.title"), null, bundle.getString("key.main.dialog.wallet.success"), null);
                    FXMLLoader loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLManualProxy.fxml"), ProxyUtils.getBundle());
                    loader.setControllerFactory(new HostServicesControllerFactory(app.getHostServices()));
                    NavControllerHandle.navigateTo(loader, stage, app);
                } else {
                    throw new Exception("Error in userAPI: Cannot create user's wallet");
                }
            } catch (IOException ex) {
                Logger.getLogger(FXMLLoginEmailController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                ProxyUtils.createAndShowAlert(Alert.AlertType.ERROR, bundle.getString("key.main.alert.error.title"), null, ex.getMessage(), null);
                LOGGER.error(ex.getMessage(), ex);
                Logger.getLogger(FXMLRegisterETHController.class.getName()).log(Level.SEVERE, null, ex);
                this.progressBar.setVisible(false);
                this.loginInfoPane.setDisable(false);
            }
        });
        task.setOnFailed((Event ev) -> {
            Logger.getLogger(FXMLLoginEmailController.class.getName()).log(Level.SEVERE, null, task.getException());
            ProxyUtils.createAndShowAlert(Alert.AlertType.ERROR, bundle.getString("key.main.alert.error.title"), null, task.getException().getMessage(), null);
            LOGGER.error(task.getException().getMessage(), task.getException());
            this.progressBar.setVisible(false);
            this.loginInfoPane.setDisable(false);
            this.btnDone.setDisable(false);
        });
        Thread thread = new Thread(task);
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Logger.getLogger(FXMLRegisterETHController.class.getName()).log(Level.SEVERE, null, e);
            }
        });
        this.loginInfoPane.setDisable(true);
        this.btnDone.setDisable(true);
        this.progressBar.setVisible(true);
        this.progressBar.progressProperty().bind(task.progressProperty());
        thread.start();
    }

    /**
     * Method resposible for the transition to the sign in screen.
     *
     * @param event An Event representing that the button has been fired.
     */
    @FXML
    private void signInWithAnotherAccountAction(ActionEvent event) throws IOException {
        Task task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                UserApi api = new UserApiImpl();
                return api.logoutUser();
            }
        };
        task.setOnSucceeded((Event ev) -> {
            try {
                if ((Boolean) task.getValue()) {
                    FXMLLoader loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLLogin.fxml"), ProxyUtils.getBundle());
                    loader.setControllerFactory(new HostServicesControllerFactory(app.getHostServices()));
                    NavControllerHandle.navigateTo(loader, stage, app);
                }
            } catch (IOException ex) {
                Logger.getLogger(FXMLLoginEmailController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                ProxyUtils.createAndShowAlert(Alert.AlertType.ERROR, bundle.getString("key.main.alert.error.title"), null, ex.getMessage(), null);
                LOGGER.error(ex.getMessage(), ex);
                Logger.getLogger(FXMLRegisterETHController.class.getName()).log(Level.SEVERE, null, ex);
                this.progressBar.setVisible(false);
                this.loginInfoPane.setDisable(false);
            }
        });
        task.setOnFailed((Event ev) -> {
            Logger.getLogger(FXMLLoginEmailController.class.getName()).log(Level.SEVERE, null, task.getException());
            ProxyUtils.createAndShowAlert(Alert.AlertType.ERROR, bundle.getString("key.main.alert.error.title"), null, task.getException().getMessage(), null);
            LOGGER.error(task.getException().getMessage(), task.getException());
            this.progressBar.setVisible(false);
            this.loginInfoPane.setDisable(false);
            this.btnDone.setDisable(false);
        });
        Thread thread = new Thread(task);
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Logger.getLogger(FXMLRegisterETHController.class.getName()).log(Level.SEVERE, null, e);
            }
        });
        this.loginInfoPane.setDisable(true);
        this.btnDone.setDisable(true);
        this.progressBar.setVisible(true);
        this.progressBar.progressProperty().bind(task.progressProperty());
        thread.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        BlankSpacesValidator blankValidatorWalletName = new BlankSpacesValidator();
        BlankSpacesValidator blankValidatorETHAddr = new BlankSpacesValidator();
        ETHWalletValidator ethValidatorETHAddr = new ETHWalletValidator();
        blankValidatorETHAddr.setMessage(rb.getString("key.main.validator.empty"));
        blankValidatorWalletName.setMessage(rb.getString("key.main.validator.empty"));
        ethValidatorETHAddr.setMessage(rb.getString("key.main.validator.eth"));
        txtWalletName.getValidators().add(blankValidatorWalletName);
        txtETHAdrr.getValidators().add(blankValidatorETHAddr);

        txtWalletName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (txtETHAdrr.validate() && txtWalletName.validate()) {
                btnDone.setDisable(false);
            } else {
                btnDone.setDisable(true);
            }
        });

        txtETHAdrr.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                txtETHAdrr.getValidators().add(blankValidatorETHAddr);
                if (txtETHAdrr.validate() && txtWalletName.validate()) {
                    btnDone.setDisable(false);
                } else {
                    btnDone.setDisable(true);
                }
            } else {
                txtETHAdrr.getValidators().add(ethValidatorETHAddr);
                if (txtETHAdrr.validate() && txtWalletName.validate()) {
                    btnDone.setDisable(false);
                } else {
                    btnDone.setDisable(true);
                }
            }

        });

    }

}
