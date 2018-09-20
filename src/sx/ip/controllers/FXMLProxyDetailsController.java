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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.slf4j.LoggerFactory;

/**
 * Login with email screen controller
 */
public class FXMLProxyDetailsController extends NavController implements Initializable {

    static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FXMLProxyDetailsController.class);

    @FXML
    private Label lblPAC;

    @FXML
    private Label lblIP;

    @FXML
    private Label lblUserIP;

    @FXML
    private Label lblStartDate;

    @FXML
    private Label lblEndDate;

    @FXML
    private Label lblStartTime;

    @FXML
    private Label lblEndTime;

    @FXML
    private Label lblCountry;

    @FXML
    private Label lblPort;

    @FXML
    private Label progressBand;

    @FXML
    private Label progressTime;

    @FXML
    private JFXButton btnCopy;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    @FXML
    private void toClipboardAction() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(lblPAC.getText());
        clipboard.setContent(content);
    }

}
