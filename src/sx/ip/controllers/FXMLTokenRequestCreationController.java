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
import java.io.IOException;
import java.net.URL;
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
import org.slf4j.LoggerFactory;
import sx.ip.IPSXDesktopClient;
import sx.ip.api.UserApi;
import sx.ip.api.UserApiImpl;
import sx.ip.factories.HostServicesControllerFactory;
import sx.ip.models.ETHWallet;
import sx.ip.utils.ProxyUtils;

/**
 * Login with email screen controller
 */
public class FXMLTokenRequestCreationController extends NavController implements Initializable {

    static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FXMLTokenRequestCreationController.class);

    @FXML
    JFXTextField txtAmount;

    @FXML
    JFXButton btnSubmit;

    @FXML
    JFXComboBox comboWallet;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            this.loadWalletComboAction(null);
        } catch (IOException ex) {
            Logger.getLogger(FXMLTokenRequestCreationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method resposible for loading the wallet's combobox.
     *
     * @param event An Event representing that the button has been fired.
     */
    @FXML
    private void loadWalletComboAction(ActionEvent event) throws IOException {
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
            new String("");
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
       
        thread.start();

    }
    
    
}
