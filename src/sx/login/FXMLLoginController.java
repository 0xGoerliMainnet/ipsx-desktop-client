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
package sx.login;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author caio
 */
public class FXMLLoginController implements Initializable{
    
    
    /** The login with email button instance.  */
    @FXML
    private JFXButton btnLoginEmail;
    
    /** The login with Facebook button instance.  */
    @FXML
    private JFXButton btnLoginFacebook;
    
    /** The login info pane instance.  */
    @FXML
    private AnchorPane loginInfoPane;

    /** The main anchor pane instance.  */
    @FXML
    private AnchorPane mainAnchorPane;
    
    
    /* The JavaFX Stage instance.  */
    private Stage stage;
    
    /* The ResourceBundle instance.  */
    private ResourceBundle bundle;
    
    /**
     * Method responsible for set the current stage
     *
     * @param stage 
     *          The current stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
     

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.bundle = rb;
    }


}
