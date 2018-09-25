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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.slf4j.LoggerFactory;
import sx.ip.IPSXDesktopClient;
import sx.ip.api.UserApi;
import sx.ip.api.UserApiImpl;
import static sx.ip.controllers.NavController.bundle;
import sx.ip.factories.HostServicesControllerFactory;
import sx.ip.utils.ProxyUtils;

/**
 * Login with email screen controller
 */
public class FXMLDashboardController extends NavController implements Initializable {

    static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FXMLDashboardController.class);

    @FXML
    Label lblBalance;

    @FXML
    JFXProgressBar progressBar;

    @FXML
    AnchorPane mainAnchorPane;

    @FXML
    JFXButton depositFX;

    @FXML
    JFXButton btnNewProxy;

    @FXML
    JFXButton btnManualProxy;

    @FXML
    JFXButton providerFX;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.retrieveUserBalance();
        this.depositFX.setText("");
        this.providerFX.setText("");
    }

    @FXML
    private void tokenRequestAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLTokenRequest.fxml"), ProxyUtils.getBundle());
        NavControllerHandle.navigateTo(loader, stage, app);
    }

    private void retrieveUserBalance() {
        Task task = new Task<String>() {
            @Override
            protected String call() throws Exception {
                UserApi api = new UserApiImpl();
                String response = api.retrieveUserBalance();
                return response;
            }
        };
        task.setOnSucceeded((Event ev) -> {
            this.mainAnchorPane.setDisable(false);
            this.progressBar.setVisible(false);
            this.lblBalance.setText((String) task.getValue());

        });
        task.setOnFailed((Event ev) -> {
            this.progressBar.setVisible(false);
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
        this.mainAnchorPane.setDisable(true);
        this.progressBar.setVisible(true);
        thread.start();
    }

    /**
     * Method resposible for the user logout and transition to the sign in
     * screen.
     *
     * @param event An Event representing that the button has been fired.
     */
    @FXML
    private void logoutAction(ActionEvent event) throws IOException {
        UserApi api = new UserApiImpl();
        try {
            if (api.logoutUser()) {
                FXMLLoader loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLLoginEmail.fxml"), ProxyUtils.getBundle());
                //loader.setControllerFactory(new HostServicesControllerFactory(app.getHostServices()));
                NavControllerHandle.navigateTo(loader, stage, app);
            }
        } catch (UnirestException ex) {
            ProxyUtils.createExceptionAlert(bundle.getString("key.main.dialog.exception.title"), null, ex.getMessage(), bundle.getString("key.main.dialog.exception.stack.text"), ex, null);
            LOGGER.error(ex.getMessage(), ex);
            Logger.getLogger(FXMLDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

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

    @FXML
    private void newProxyAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLNewProxy.fxml"), ProxyUtils.getBundle());
        NavControllerHandle.navigateTo(loader, stage, app);
    }

    @FXML
    private void manualProxyAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLManualProxy.fxml"), ProxyUtils.getBundle());
        loader.setControllerFactory(new HostServicesControllerFactory(app.getHostServices()));
        NavControllerHandle.navigateTo(loader, stage, app);
    }

}
