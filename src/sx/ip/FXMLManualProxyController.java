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
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import sx.ip.proxies.ProxyManager;
import sx.ip.proxies.ProxySettings;

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
    private JFXComboBox<String> comboProtocol;

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
    
    private ProxyManager manager = ProxyManager.getInstance();
    
    private ProxySettings settings;
    
    private boolean isActivated = false;
    
    private final HostServices hostServices ;

    public FXMLManualProxyController(HostServices hostServices) {
        this.hostServices = hostServices ;
    }
    
    @FXML
    private void handleCloseAction(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    private void handleActivateAction(ActionEvent event) {        
        
        Boolean response = false;
        String host = proxyId.getText();
        int port = Integer.valueOf(proxyPort.getText());
        String type = comboProtocol.getValue();
        
        try {
        if(!isActivated){
            settings = new ProxySettings(host, port, ProxySettings.ProxyType.valueOf(type), null, true, "", "");
            handleScene(true);
        }else{
            settings = new ProxySettings(null, null, null, null, false, "", "");
            handleScene(false);
        }

        
        response = manager.setProxySettings(settings);
        if(response){
            isActivated = true;
        }
        
        } catch (ProxyManager.ProxySetupException ex) {
            Logger.getLogger(FXMLManualProxyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void openBrowser(ActionEvent actionEvent) throws Exception {
        hostServices.showDocument(btnTerms.getAccessibleText());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       comboProtocol.getItems().addAll("HTTP", "HTTPS", "FTP", "SOCKS", "HTTP & HTTPS");
    }
    
    private void handleScene(boolean activate){
        agreePane.setDisable(!agreePane.isDisable());
        comboProtocol.setDisable(!comboProtocol.isDisable());
        proxyId.setDisable(!proxyId.isDisable());
        proxyPort.setDisable(!proxyPort.isDisable());
        
        if(activate){
            btnActivate.setText("Deactivate");
            btnActivate.setStyle("-fx-background-color: #2ecc71;");
        }else{
            btnActivate.setText("Activate Proxy");
            btnActivate.setStyle("-fx-background-color: #2aace0;");
        }
        
    }
    
    

}
