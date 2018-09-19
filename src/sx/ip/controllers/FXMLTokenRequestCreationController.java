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
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import org.slf4j.LoggerFactory;
import sx.ip.IPSXDesktopClient;
import sx.ip.api.UserApi;
import sx.ip.api.UserApiImpl;
import sx.ip.factories.HostServicesControllerFactory;
import sx.ip.models.ETHWallet;
import sx.ip.utils.BlankSpacesValidator;
import sx.ip.utils.ProxyUtils;

/**
 * Login with email screen controller
 */
public class FXMLTokenRequestCreationController extends NavController implements Initializable {

    static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FXMLTokenRequestCreationController.class);

    /**
     * The amount text field instance.
     */
    @FXML
    JFXTextField txtAmount;

    /**
     * The submit button instance.
     */
    @FXML
    JFXButton btnSubmit;

    /**
     * The back button instance.
     */
    @FXML
    JFXButton btnBack;

    /**
     * The main anchor pane instance.
     */
    @FXML
    AnchorPane mainAnchorPane;

    /**
     * The ComboBox instance.
     */
    @FXML
    JFXComboBox<ETHWallet> comboWallet;

    /**
     * The progress bar instance.
     */
    @FXML
    ProgressBar progressBar;

    private ArrayList<ETHWallet> walletArray;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            this.loadWalletCombo();
        } catch (IOException ex) {
            Logger.getLogger(FXMLTokenRequestCreationController.class.getName()).log(Level.SEVERE, null, ex);
        }

        BlankSpacesValidator blankValidatorEmail = new BlankSpacesValidator();
        blankValidatorEmail.setMessage(rb.getString("key.main.validator.empty"));
        NumberValidator numValidator = new NumberValidator();
        numValidator.setMessage(rb.getString("key.main.validator.numbers"));
        txtAmount.getValidators().add(blankValidatorEmail);
        txtAmount.getValidators().add(numValidator);

        txtAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!txtAmount.validate() || comboWallet.getValue() == null) {
                btnSubmit.setDisable(true);
            } else {
                btnSubmit.setDisable(false);
            }
        });
    }

    /**
     * Method resposible for loading the wallet's combobox.
     *
     */
    private void loadWalletCombo() throws IOException {
        Task task = new Task<List<ETHWallet>>() {
            @Override
            protected List<ETHWallet> call() throws Exception {
                UserApi api = new UserApiImpl();
                boolean response = api.userHasEthWallet();
                if (response) {
                    return api.retrieveUsersETHWallets();
                }
                return null;
            }
        };
        task.setOnSucceeded((Event ev) -> {
            this.walletArray = (ArrayList<ETHWallet>) task.getValue();
            this.comboWallet.getItems().addAll(walletArray);
            this.comboWallet.setDisable(false);
            progressBar.setVisible(false);
        });
        task.setOnFailed((Event ev) -> {
            Logger.getLogger(FXMLLoginEmailController.class.getName()).log(Level.SEVERE, null, task.getException());
            ProxyUtils.createExceptionAlert(bundle.getString("key.main.alert.error.auth.title"), null, task.getException().getMessage(), bundle.getString("key.main.dialog.exception.stack.text"), task.getException(), null);
            LOGGER.error(task.getException().getMessage(), task.getException());

        });
        Thread thread = new Thread(task);
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Logger.getLogger(FXMLLoginEmailController.class.getName()).log(Level.SEVERE, null, e);
                ProxyUtils.createExceptionAlert(bundle.getString("key.main.alert.error.title"), null, e.getMessage(), bundle.getString("key.main.dialog.exception.stack.text"), e, null);
            }
        });
        progressBar.setVisible(true);
        thread.start();

    }

    /**
     * Method resposible for handling the go back action.
     *
     * @param event An Event representing that the button has been fired.
     */
    @FXML
    private void goBackAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLTokenRequest.fxml"), ProxyUtils.getBundle());
        loader.setControllerFactory(new HostServicesControllerFactory(app.getHostServices()));
        NavControllerHandle.navigateTo(loader, stage, app);
    }

    /**
     * Method resposible for handling the go back action.
     *
     * @param event An Event representing that the button has been fired.
     */
    @FXML
    private void submitTokenRequestAction(ActionEvent event) throws IOException {
        ETHWallet selectedWallet = (ETHWallet) comboWallet.getValue();
        Task task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                UserApi api = new UserApiImpl();
                return api.tokenRequest(selectedWallet, txtAmount.getText());
            }
        };
        task.setOnSucceeded((Event ev) -> {
            mainAnchorPane.setDisable(false);
            progressBar.setVisible(false);
            ProxyUtils.createAndShowAlert(Alert.AlertType.INFORMATION, bundle.getString("key.main.alert.info.title"), bundle.getString("key.main.alert.tokenrequest.success"), "Amount Requested: " + txtAmount.getText(), null);
        });
        task.setOnFailed((Event ev) -> {
            Logger.getLogger(FXMLLoginEmailController.class.getName()).log(Level.SEVERE, null, task.getException());
            ProxyUtils.createExceptionAlert(bundle.getString("key.main.alert.error.request.token.title"), null, task.getException().getMessage(), bundle.getString("key.main.dialog.exception.stack.text"), task.getException(), null);
            LOGGER.error(task.getException().getMessage(), task.getException());
            mainAnchorPane.setDisable(false);
            progressBar.setVisible(false);

        });
        Thread thread = new Thread(task);
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Logger.getLogger(FXMLLoginEmailController.class.getName()).log(Level.SEVERE, null, e);
                ProxyUtils.createExceptionAlert(bundle.getString("key.main.alert.error.title"), null, e.getMessage(), bundle.getString("key.main.dialog.exception.stack.text"), e, null);
            }
        });
        progressBar.setVisible(true);
        mainAnchorPane.setDisable(true);
        thread.start();

    }

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
     * Method resposible for handling the close action.
     *
     * @param event An Event representing that the button has been fired.
     */
    @FXML
    private void comboRefreshAction(ActionEvent event) {
        if (!txtAmount.validate() || comboWallet.getValue() == null) {
            btnSubmit.setDisable(true);
        } else {
            btnSubmit.setDisable(false);
        }
    }

}
