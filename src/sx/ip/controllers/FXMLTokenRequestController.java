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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import org.slf4j.LoggerFactory;
import sx.ip.IPSXDesktopClient;
import sx.ip.api.UserApi;
import sx.ip.api.UserApiImpl;
import sx.ip.factories.HostServicesControllerFactory;
import sx.ip.models.TokenRequest;
import sx.ip.utils.ProxyUtils;

/**
 * Login with email screen controller
 */
public class FXMLTokenRequestController extends NavController implements Initializable {

    static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FXMLTokenRequestController.class);

    @FXML
    ListView<TokenRequest> listViewRequests;

    @FXML
    JFXButton btnPlus;

    /**
     * The main anchor pane instance.
     */
    @FXML
    AnchorPane mainAnchorPane;

    /**
     * The progress bar instance.
     */
    @FXML
    ProgressBar progressBar;

    private ObservableList<TokenRequest> trObservableList = FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle rb) {
        this.listViewRequests.setItems(this.trObservableList);
        this.listViewRequests.setCellFactory((ListView<TokenRequest> tokenRequestListView) -> new FXMLTokenRequestListViewCellController());
        AnchorPane.setBottomAnchor(this.listViewRequests, 0.0);
        AnchorPane.setTopAnchor(this.listViewRequests, 0.0);
        AnchorPane.setLeftAnchor(this.listViewRequests, 0.0);
        AnchorPane.setRightAnchor(this.listViewRequests, 0.0);
        try {
            this.retrieveTokenRequests();
        } catch (IOException ex) {
            Logger.getLogger(FXMLTokenRequestController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Method resposible for loading the wallet's combobox.
     *
     */
    private void retrieveTokenRequests() throws IOException {
        Task task = new Task<List<TokenRequest>>() {
            @Override
            protected List<TokenRequest> call() throws Exception {
                UserApi api = new UserApiImpl();
                boolean response = api.userHasEthWallet();
                if (response) {
                    return api.retrieveUserTokenRequests();
                }
                return null;
            }
        };
        task.setOnSucceeded((Event ev) -> {
            this.trObservableList.addAll((ArrayList<TokenRequest>) task.getValue());
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
        thread.run();

    }

    /**
     * Method resposible for handling the go back action.
     *
     * @param event An Event representing that the button has been fired.
     */
    @FXML
    private void goBackAction(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLLandingPage.fxml"), ProxyUtils.getBundle());
//        loader.setControllerFactory(new HostServicesControllerFactory(app.getHostServices()));
//        NavControllerHandle.navigateTo(loader, stage, app);
    }
    
    @FXML
    private void newRequestAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLTokenRequestCreation.fxml"), ProxyUtils.getBundle());
        loader.setControllerFactory(new HostServicesControllerFactory(app.getHostServices()));
        NavControllerHandle.navigateTo(loader, stage, app);
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

}
