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
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sx.ip.IPSXDesktopClient;

/**
 * Main class responsible for system configuration
 */
public class NavController{
    
    /** The JavaFX Stage instance.  */
    Stage stage;
    
    /** The Parent instance.  */
    Parent root;
    
    /** The ResourceBundle instance.  */
    public static ResourceBundle bundle;
    
    /** The Application instance.  */
    Application app;   
    
    /** The accessToken instance.  */
    public static String accessToken = null; 
    
    /** Define the window x offsets. */
    private double xOffset = 0;

    /** Define the window y offsets. */
    private double yOffset = 0;
    
    /**
    * Method responsible for do the controller setup.
    *
    * @param stage
    *          The instance of the current stage.
    * @param root
    *          The instance of the current parent.
    * @param bundle
    *          The instance of the current resource bundle.
    * @param app
    *          The instance of the current application.
    */
    public void setupController(Stage stage,Parent root, ResourceBundle bundle, Application app){        
        this.root = root;
        this.stage = stage;
        this.bundle = bundle;
        this.app = app;
        
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
        if(!stage.isShowing()){
            stage.setResizable(false);        
            stage.initStyle(StageStyle.UNDECORATED);
            stage.getIcons().add(new Image(IPSXDesktopClient.class.getResourceAsStream("resources/imgs/icon.png")));
            stage.setTitle("IP Exchange");
        }
    }
    
    /**
    * Method responsible for start the stage.
    * 
    * @throws IOException
    */
    public void startScene() throws IOException{        
        Scene scene = new Scene(root);        
        stage.setScene(scene);
        if(!stage.isShowing()){
            stage.show();        
        }
    }
    
}
