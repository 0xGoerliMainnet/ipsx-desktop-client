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
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import sx.ip.utils.BlankSpacesValidator;

/**
 *
 * @author caio
 */
public class FXMLRegisterETHController extends NavController implements Initializable{
    
    
    /** The sign in with another account button instance.  */
    @FXML
    private Hyperlink btnSignAnotherAccount;
    
    /** The done button instance.  */
    @FXML
    private JFXButton btnDone;
    
    /** The close button instance.  */
    @FXML
    private JFXButton btnClose;

    /** The main anchor pane instance.  */
    @FXML
    private AnchorPane mainAnchorPane;
    
    /** The ETH wallet name text field instance.  */
    @FXML
    private JFXTextField txtWalletName;
    
    /** The ETH wallet address text field instance.  */
    @FXML
    private JFXTextField txtETHAdrr;
    
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
    * Method resposible for the transition to the proxies screen.
    *
    * @param event
    *          An Event representing that the button has been fired.
    */
    @FXML
    private void doneAction(ActionEvent event) throws IOException{
        //finish registration
    }
    
    /**
    * Method resposible for the transition to the sign in screen.
    *
    * @param event
    *          An Event representing that the button has been fired.
    */
    @FXML
    private void signInWithAnotherAccountAction(ActionEvent event) throws IOException{
        //FXMLLoader loader = new FXMLLoader(IPSXDesktopClient.class.getResource("resources/fxml/FXMLManualProxy.fxml"), ProxyUtils.getBundle());
        //NavControllerHandle.navigateTo(loader, stage, app);
    }
    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        BlankSpacesValidator blankValidatorEmail = new BlankSpacesValidator();
        blankValidatorEmail.setMessage(rb.getString("key.main.validator.empty"));
        

        
        txtWalletName.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty()){
                txtWalletName.getValidators().add(blankValidatorEmail);
                if(!txtWalletName.validate() && !txtETHAdrr.validate()){
                    btnDone.setDisable(true);
                }else{
                    btnDone.setDisable(false);
                }
            }
        });
        
        txtETHAdrr.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty()){
                txtETHAdrr.getValidators().add(blankValidatorEmail);
                if(!txtETHAdrr.validate() && !txtWalletName.validate()){
                    btnDone.setDisable(true);
                }else{
                    btnDone.setDisable(false);
                }
            }
        });
        
    }


}
