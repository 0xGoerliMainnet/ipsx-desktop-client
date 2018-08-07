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
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import sx.ip.IPSXDesktopClient;
import sx.ip.api.UserApi;
import sx.ip.api.UserApiImpl;
import static sx.ip.controllers.FXMLLoginEmailController.LOGGER;
import static sx.ip.controllers.NavController.bundle;
import sx.ip.factories.HostServicesControllerFactory;
import sx.ip.utils.CredentialType;
import sx.ip.utils.ProxyUtils;
import sx.ip.utils.SecurityHandle;

/**
 * Landing page controller
 */
public class FXMLLandingPageController extends NavController implements Initializable {

    /**
     * The register button instance.
     */
    @FXML
    private JFXButton btnRegister;

    /**
     * The login button instance.
     */
    @FXML
    private JFXButton btnLogin;

    /**
     * The close button instance.
     */
    @FXML
    private JFXButton btnClose;

    /**
     * The object that provides Host Services for the Application.
     */
    private HostServices hostServices;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       System.out.println("New screen initialized...");
    }

    /**
     * Method resposible for handle with the close action.
     *
     * @param event An Event representing that the button has been fired.
     */
    @FXML
    private void handleCloseAction(ActionEvent event) {
        stage.close();
    }

    /**
     * Method resposible for handle with the navegation action.
     *
     * @param event An Event representing that the button has been fired.
     */
    @FXML
    private void handleLoginAction(ActionEvent event) throws IOException {
        CredentialType credentialType = CredentialType.STRING;
        try {
            if (ProxyUtils.loadCredentials("username", credentialType.STRING) != "") {
                this.loginUserWithKeptCredentials();
            } else {
                FXMLLoader loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLLogin.fxml"), ProxyUtils.getBundle());
                NavControllerHandle.navigateTo(loader, stage, app);
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLLandingPageController.class.getName()).log(Level.SEVERE, null, ex);
            LOGGER.error(ex.getMessage(), ex);
        }

    }

    /**
     * Method to check if the user has credentials save and login automatically
     * to refresh token.
     */
    private void loginUserWithKeptCredentials() throws IOException {
        UserApi api = new UserApiImpl();
        CredentialType credentialType = CredentialType.STRING;
        SecurityHandle sh = new SecurityHandle();
        try {
            String email = (String) ProxyUtils.loadCredentials("username", credentialType.STRING);
            Map<byte[], Integer> encriptedPasswordMap = (HashMap<byte[], Integer>) ProxyUtils.loadCredentials("encryptedPassword", credentialType.BYTEARRAY);
            String password = sh.decryption(encriptedPasswordMap);

            boolean response = api.authUser(email, password);

            if (response) {
                if (api.userHasEthWallet()) {
                    //User goes to dashboard
                    FXMLLoader loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLManualProxy.fxml"), ProxyUtils.getBundle());
                    loader.setControllerFactory(new HostServicesControllerFactory(app.getHostServices()));
                    NavControllerHandle.navigateTo(loader, stage, app);
                } else {
                    FXMLLoader loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLRegisterETH.fxml"), ProxyUtils.getBundle());
                    loader.setControllerFactory(new HostServicesControllerFactory(app.getHostServices()));
                    NavControllerHandle.navigateTo(loader, stage, app);
                }
            }
        } catch (UnirestException ex) {
            ProxyUtils.createExceptionAlert(bundle.getString("key.main.dialog.exception.title"), null, ex.getMessage(), bundle.getString("key.main.dialog.exception.stack.text"), ex, null);
            LOGGER.error(ex.getMessage(), ex);
            Logger.getLogger(FXMLLandingPageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLLandingPageController.class.getName()).log(Level.SEVERE, null, ex);
            LOGGER.error(ex.getMessage(), ex);
        }catch (Exception ex) {
            ProxyUtils.createExceptionAlert(bundle.getString("key.main.dialog.exception.title"), null, ex.getMessage(), bundle.getString("key.main.dialog.exception.stack.text"), ex, null);
            Logger.getLogger(FXMLLandingPageController.class.getName()).log(Level.SEVERE, null, ex);
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    /**
     * Method resposible for open the browser.
     *
     * @param event An Event representing that the Button has been fired.
     */
    @FXML
    public void openBrowser(ActionEvent event) {
        hostServices.showDocument(btnRegister.getAccessibleText());
    }

    /**
     * Method responsible for set the current hostServices
     *
     * @param hostServices The current hostServices
     */
    public FXMLLandingPageController(HostServices hostServices) {
        this.hostServices = hostServices;
    }

}
