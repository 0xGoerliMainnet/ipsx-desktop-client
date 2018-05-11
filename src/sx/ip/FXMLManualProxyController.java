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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main controller for the main application window
 */
public class FXMLManualProxyController implements Initializable {

    private final HostServices hostServices;

    private Stage stage;

    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private JFXButton btnClose;

    @FXML
    private JFXComboBox<String> comboProtocol;

    @FXML
    private Hyperlink btnTerms;

    public FXMLManualProxyController(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    @FXML
    private void handleCloseAction(ActionEvent event) {
        stage.close();
    }

    @FXML
    public void openBrowser(ActionEvent actionEvent) throws Exception {
        hostServices.showDocument(btnTerms.getAccessibleText());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboProtocol.getItems().addAll("SOCKS", "HTTP & HTTPS");
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
