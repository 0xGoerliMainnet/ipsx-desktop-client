/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sx.ip;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 *
 * @author Renan
 */
public class FXMLManualProxyController implements Initializable {

    @FXML
    private JFXButton btnClose;

    @FXML
    private JFXComboBox<String> comboProtocol;    
    
    @FXML
    private void handleCloseAction(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       comboProtocol.getItems().addAll("SOCKS", "HTTP & HTTPS");
    }
    
    

}
