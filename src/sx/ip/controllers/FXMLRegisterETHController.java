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
import com.jfoenix.controls.JFXTextField;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
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
     * The main anchor pane instance.
     */
    @FXML
    private AnchorPane mainAnchorPane;

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
        UserApi api = new UserApiImpl();
        try {
            //TODO: regex validator for valid eth addr.
            api.addEthAddress(this.txtWalletName.getText(), this.txtETHAdrr.getText());
            ProxyUtils.createAndShowAlert(Alert.AlertType.INFORMATION, bundle.getString("key.main.alert.info.title"), null, "Wallet added Sucessfully!", null);
            FXMLLoader loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLManualProxy.fxml"), ProxyUtils.getBundle());
            loader.setControllerFactory(new HostServicesControllerFactory(app.getHostServices()));
            NavControllerHandle.navigateTo(loader, stage, app);
        } catch (UnirestException ex) {
            ProxyUtils.createAndShowAlert(Alert.AlertType.ERROR, bundle.getString("key.main.alert.error.title"), null, ex.getMessage(), null);
            LOGGER.error(ex.getMessage(), ex);
            Logger.getLogger(FXMLRegisterETHController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method resposible for the transition to the sign in screen.
     *
     * @param event An Event representing that the button has been fired.
     */
    @FXML
    private void signInWithAnotherAccountAction(ActionEvent event) throws IOException {
        UserApi api = new UserApiImpl();
        try {
            if (api.logoutUser()) {
                FXMLLoader loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLLogin.fxml"), ProxyUtils.getBundle());
                //loader.setControllerFactory(new HostServicesControllerFactory(app.getHostServices()));
                NavControllerHandle.navigateTo(loader, stage, app);
            }
        } catch (UnirestException ex) {
            ProxyUtils.createExceptionAlert(bundle.getString("key.main.dialog.exception.title"), null, ex.getMessage(), bundle.getString("key.main.dialog.exception.stack.text"), ex, null);
            LOGGER.error(ex.getMessage(), ex);
            Logger.getLogger(FXMLRegisterETHController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
