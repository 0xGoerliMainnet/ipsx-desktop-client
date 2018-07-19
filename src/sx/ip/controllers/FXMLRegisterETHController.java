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
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.UserethsApi;
import io.swagger.client.model.InlineResponse200;
import io.swagger.client.model.Usereths;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import sx.ip.utils.BlankSpacesValidator;

/**
 *
 * @author caio
 */
public class FXMLRegisterETHController extends NavController implements Initializable {

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
        UserethsApi apiInstance = new UserethsApi();
        Usereths newWallet = new Usereths();
        newWallet.setAddress(this.txtETHAdrr.getText());
        //newWallet.setUserId(null);
        newWallet.setAlias(this.txtWalletName.getText());
        try {
            Usereths result = apiInstance.userethsCreate(newWallet);
            //go to next screen
        } catch (ApiException ex) {
            Logger.getLogger(FXMLRegisterETHController.class.getName()).log(Level.SEVERE, null, ex);
            // show error message
        }
    }

    /**
     * Method resposible for the transition to the sign in screen.
     *
     * @param event An Event representing that the button has been fired.
     */
    @FXML
    private void signInWithAnotherAccountAction(ActionEvent event) throws IOException {
        //FXMLLoader loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLManualProxy.fxml"), ProxyUtils.getBundle());
        //NavControllerHandle.navigateTo(loader, stage, app);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        BlankSpacesValidator blankValidatorWalletName = new BlankSpacesValidator();
        BlankSpacesValidator blankValidatorETHAddr = new BlankSpacesValidator();
        blankValidatorETHAddr.setMessage(rb.getString("key.main.validator.empty"));
        blankValidatorWalletName.setMessage(rb.getString("key.main.validator.empty"));
        txtETHAdrr.getValidators().add(blankValidatorETHAddr);
        txtWalletName.getValidators().add(blankValidatorWalletName);

        txtWalletName.textProperty().addListener((observable, oldValue, newValue) -> {

            if (txtETHAdrr.validate() && txtWalletName.validate()) {
                btnDone.setDisable(false);
            } else {
                btnDone.setDisable(true);
            }
        });

        txtETHAdrr.textProperty().addListener((observable, oldValue, newValue) -> {
            if (txtETHAdrr.validate() && txtWalletName.validate()) {
                btnDone.setDisable(false);
            } else {
                btnDone.setDisable(true);
            }
        });

    }

}
