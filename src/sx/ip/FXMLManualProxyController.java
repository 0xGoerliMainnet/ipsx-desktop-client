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
package sx.ip;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;
import javafx.util.converter.IntegerStringConverter;
import sx.ip.proxies.ProxyManager;
import sx.ip.proxies.ProxySettings;
import sx.ip.models.ProxyType;
import sx.ip.utils.ProxyUtils;

/**
 * Main controller for the main application window
 */
public class FXMLManualProxyController implements Initializable {

    private HostServices hostServices;

    private Stage stage;

    private final ProxyManager manager = ProxyManager.getInstance();

    private ProxySettings settings;

    private boolean isActivated = false;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXButton btnClose;
    
    @FXML
    private JFXButton btnActivate;

    @FXML
    private JFXComboBox<ProxyType> comboProtocol;

    @FXML
    private AnchorPane agreePane;

    @FXML
    private AnchorPane proxyPane;

    @FXML
    private AnchorPane proxyUrlPane;

    @FXML
    private JFXTextField proxyId;
    
    @FXML
    private JFXTextField proxyUrl;

    @FXML
    private JFXTextField proxyPort;

    @FXML
    private Hyperlink btnTerms;

    @FXML
    private JFXCheckBox bypassCB;

    @FXML
    private JFXCheckBox proxyAuthentication;

    @FXML
    private JFXCheckBox agreeCheckBox;

    @FXML
    private JFXCheckBox advancedSettings;

    @FXML
    private JFXProgressBar progressBar;

    public FXMLManualProxyController(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    /**
     * Method resposible for handle with the close action.
     *
     * @param event An Event representing that the button has been fired.
     *
     */
    @FXML
    private void handleCloseAction(ActionEvent event) {
        stage.close();
    }

    @FXML
    private void handleAdvancedSettings(ActionEvent event) {
        proxyPane.setVisible(!proxyPane.isVisible());
        proxyUrlPane.setVisible(!proxyUrlPane.isVisible());
    }

    /**
     * Method resposible for handle with the activate action.
     *
     * @param event An Event representing that the button has been fired.
     *
     */
    @FXML
    private void handleActivateAction(ActionEvent event) {
        String host = proxyPane.isVisible() ? proxyId.getText().trim(): proxyUrl.getText().trim() ;
        String type = (comboProtocol.getValue() != null) ? comboProtocol.getValue().getValue() : null;
        Integer port = null;
        String proxyAuthe = null;
        String proxyPass = null;
        if (agreeCheckBox.isSelected()) {
            if (proxyPane.isVisible()) {
                advancedProxyServer(host, port, type, proxyAuthe, proxyPass);
            } else {
                defaultProxyServer(host, proxyAuthe, proxyPass);
            }
        } else {
            Alert infoAlert = ProxyUtils.createAlert(AlertType.INFORMATION, "Information", null, "Please, read and agree with the terms and conditions of use before you proceed!");
            infoAlert.showAndWait();
        }
    }

    /**
     * Method resposible for open the browser.
     *
     * @param event An Event representing that the Button has been fired.
     *
     */
    @FXML
    public void openBrowser(ActionEvent event) {
        hostServices.showDocument(btnTerms.getAccessibleText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<ProxyType> data
                = FXCollections.observableArrayList(
                        new ProxyType("SOCKS", "SOCKS"),
                        new ProxyType("HTTP & HTTPS", "HTTP_AND_HTTPS"));

        comboProtocol.getItems().addAll(data);
        comboProtocol.setPromptText("Proxy protocol");
        proxyPort.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
    }

    /**
     * Method responsible for control the display layout.
     *
     * @param activate Flag to indicate if the proxy server is active or not
     */
    private void handleScene(boolean activate) {
        agreePane.setDisable(!agreePane.isDisable());
        if(proxyPane.isVisible()){
            comboProtocol.setDisable(!comboProtocol.isDisable());
            proxyId.setDisable(!proxyId.isDisable());
            proxyPort.setDisable(!proxyPort.isDisable());
        }else{
            proxyUrlPane.setDisable(!proxyUrlPane.isDisable());
        }

        if (activate) {
            btnActivate.setText("Deactivate");
            btnActivate.setStyle("-fx-background-color: #2ecc71;");
        } else {
            btnActivate.setText("Activate Proxy");
            btnActivate.setStyle("-fx-background-color: #2aace0;");
        }

    }

    /**
     * Method responsible for handle with the proxy activation / deactivation
     * thread.
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Method responsible for handle with the proxy activation / deactivation
     * thread.
     */
    private void startProxyThread() {
        Task task = new Task<Void>() {
            @Override
            public Void call() throws ProxyManager.ProxySetupException {
                progressBar.setVisible(true);
                btnActivate.setDisable(true);
                manager.setProxySettings(settings);
                return null;
            }

            @Override
            protected void done() {
                super.done();
                updateProgress(100, 100);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FXMLManualProxyController.class.getName()).log(Level.SEVERE, null, ex);
                }
                progressBar.setVisible(false);
                btnActivate.setDisable(false);
            }
        };

        progressBar.progressProperty().bind(task.progressProperty());
        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                ProxyUtils.createExceptionAlert("Internal Error", null, task.getException().getMessage(), (Exception) task.getException());
                Logger.getLogger(FXMLManualProxyController.class.getName()).log(Level.SEVERE, null, task.getException());
            }
        });
        new Thread(task).start();
    }

    private void advancedProxyServer(String host, Integer port, String type, String proxyAuthe, String proxyPass) {
        Alert warningAlert = ProxyUtils.createAlert(AlertType.WARNING, "Warning", null, "Please, fill at least the Protocol, Proxy ID and Port fields!");
        if (proxyPort.getText() != null && proxyPort.getText().trim().length() > 0) {
            port = Integer.valueOf(proxyPort.getText());
        }

        if ((host != null && host.length() > 0)) {
            if (type != null && port != null) {

                if (!isActivated) {
                    if (proxyAuthentication.isSelected()) {
                        Dialog dialog = ProxyUtils.createAuthenticationDialog("Proxy Authentication", "Enter with the proxy authentication");
                        Optional<Pair<String, String>> result = dialog.showAndWait();
                        if (result.isPresent()) {
                            proxyAuthe = result.get().getKey();
                            proxyPass = result.get().getValue();
                        }
                    }

                    settings = new ProxySettings(host, port, ProxySettings.ProxyType.valueOf(type), null, bypassCB.isSelected(), proxyAuthe, proxyPass);
                    isActivated = true;
                    handleScene(isActivated);
                } else {
                    settings = ProxySettings.getDirectConnectionSetting();
                    isActivated = false;
                    handleScene(isActivated);
                }
                startProxyThread();
            } else {
                warningAlert.showAndWait();
            }
        } else {
            warningAlert.showAndWait();
        }
    }
    
    private void defaultProxyServer(String acs, String proxyAuthe, String proxyPass) {
        Alert warningAlert = ProxyUtils.createAlert(AlertType.WARNING, "Warning", null, "Please, fill the ProxyID field!");

        if ((acs != null && acs.length() > 0)) {

                if (!isActivated) {
                    if (proxyAuthentication.isSelected()) {
                        Dialog dialog = ProxyUtils.createAuthenticationDialog("Proxy Authentication", "Enter with the proxy authentication");
                        Optional<Pair<String, String>> result = dialog.showAndWait();
                        if (result.isPresent()) {
                            proxyAuthe = result.get().getKey();
                            proxyPass = result.get().getValue();
                        }
                    }

                    settings = new ProxySettings("", null, null, acs, false, proxyAuthe, proxyPass);
                    isActivated = true;
                    handleScene(isActivated);
                } else {
                    settings = ProxySettings.getDirectConnectionSetting();
                    isActivated = false;
                    handleScene(isActivated);
                }
                startProxyThread();
            
        } else {
            warningAlert.showAndWait();
        }
    }
}
