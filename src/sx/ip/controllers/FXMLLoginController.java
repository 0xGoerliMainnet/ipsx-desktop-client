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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import sx.ip.IPSXDesktopClient;
import sx.ip.factories.HostServicesControllerFactory;
import sx.ip.utils.ProxyUtils;

/**
 *
 * @author caio
 */
public class FXMLLoginController extends NavController implements Initializable{
    
    
    /** The login with email button instance.  */
    @FXML
    private JFXButton btnLoginEmail;
    
    /** The login with Facebook button instance.  */
    @FXML
    private JFXButton btnLoginFacebook;
    
    /** The login info pane instance.  */
    @FXML
    private AnchorPane loginInfoPane;
    
    /** The close button instance.  */
    @FXML
    private JFXButton btnClose;

    /** The main anchor pane instance.  */
    @FXML
    private AnchorPane mainAnchorPane;
    
    /**
    * Method resposible for handling the close action.
    *
    * @param event 
    *          An Event representing that the button has been fired.
    */
    @FXML
    private void handleCloseAction(ActionEvent event) {
        stage.close();
    }
    
    /**
    * Method resposible for the transition to the login with email screen action.
    *
    * @param event
    *          An Event representing that the button has been fired.
    */
    @FXML
    private void loginWithEmailAction(ActionEvent event) throws IOException{
//        FXMLLoader loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLManualProxy.fxml"), ProxyUtils.getBundle());
//        loader.setControllerFactory(new HostServicesControllerFactory(app.getHostServices()));
//        NavControllerHandle.navigateTo(loader, stage, app);
    }
    
    @FXML
    private void loginWithFacebookAction(ActionEvent ae) throws IOException{
        
    }
    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }


}
