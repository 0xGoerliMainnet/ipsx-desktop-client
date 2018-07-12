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

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.HostServices;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sx.ip.IPSXDesktopClient;

/**
 *
 * @author hygor
 */
public class NavController{
    
     /** The JavaFX Stage instance.  */
    final Stage stage;
    
    /** The Parent instance.  */
    Parent root;
    
    /** The ResourceBundle instance.  */
    ResourceBundle bundle;
    
    /** The object that provides Host Services for the Application.  */
    HostServices hostServices;
    
    /** Define the window x offsets. */
    private double xOffset = 0;

    /** Define the window y offsets. */
    private double yOffset = 0;
    
    public NavController(Stage stage){
        this.stage = stage;
    }

    public void setController(Stage stage, Parent root, ResourceBundle bundle){        
        this.root = root;
        this.bundle = bundle;
    }
    
    public void hideStage(){
        stage.close();
    }
    
    public void startScene() throws IOException{        
        Scene scene = new Scene(root);
        //grab your root here
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }

        });

        // Makes the window be draggable
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }

        });
        
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(IPSXDesktopClient.class.getResourceAsStream("resources/imgs/icon.png")));
        stage.setTitle("IP Exchange");
        stage.setScene(scene);
        stage.show();
    }   
    
}
