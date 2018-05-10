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
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;
import javafx.util.converter.IntegerStringConverter;
import sx.ip.proxies.ProxyManager;
import sx.ip.proxies.ProxySettings;
import sx.ip.models.ProxyType;
import sx.ip.utils.ProxyUtils;

/**
 *
 * @author Renan
 */
public class FXMLManualProxyController implements Initializable {

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
    private JFXTextField proxyId;

    @FXML
    private JFXTextField proxyPort;

    @FXML
    private Hyperlink btnTerms;

    @FXML
    private JFXCheckBox bypassCB;

    @FXML
    private JFXCheckBox proxyAuthentication;

    private ProxyManager manager = ProxyManager.getInstance();

    private ProxySettings settings;

    private boolean isActivated = false;

    private final HostServices hostServices;

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
        System.exit(0);
    }

    /**
     * Method resposible for handle with the activate action.
     *
     * @param event An Event representing that the button has been fired.
     *
     */
    @FXML
    private void handleActivateAction(ActionEvent event) {
        Alert alert = ProxyUtils.createAlert(AlertType.WARNING, "Warning", null, "Please, fill at least the Protocol, Proxy ID and Port fields!");

        String host = proxyId.getText().trim();
        String type = (comboProtocol.getValue() != null) ? comboProtocol.getValue().getValue() : null;
        Integer port = null;
        String proxyAuthe = null;
        String proxyPass = null;

        if (proxyPort.getText() != null && proxyPort.getText().trim().length() > 0) {
            port = Integer.valueOf(proxyPort.getText());
        }

        if ((host != null && host.length() > 0)) {
            if (type != null && port != null) {

                try {
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

                    manager.setProxySettings(settings);

                } catch (ProxyManager.ProxySetupException ex) {
                    Logger.getLogger(FXMLManualProxyController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                alert.showAndWait();
            }
        } else {
            alert.showAndWait();

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
                        new ProxyType("HTTP", "HTTP"),
                        new ProxyType("HTTPS", "HTTPS"),
                        new ProxyType("FTP", "FTP"),
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
     *
     */
    private void handleScene(boolean activate) {
        agreePane.setDisable(!agreePane.isDisable());
        comboProtocol.setDisable(!comboProtocol.isDisable());
        proxyId.setDisable(!proxyId.isDisable());
        proxyPort.setDisable(!proxyPort.isDisable());

        if (activate) {
            btnActivate.setText("Deactivate");
            btnActivate.setStyle("-fx-background-color: #2ecc71;");
        } else {
            btnActivate.setText("Activate Proxy");
            btnActivate.setStyle("-fx-background-color: #2aace0;");
        }

    }
}
