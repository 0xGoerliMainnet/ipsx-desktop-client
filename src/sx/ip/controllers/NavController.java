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
 *
 * @author hygor
 */
public class NavController{
    
    /** The JavaFX Stage instance.  */
    Stage stage;
    
    /** The Parent instance.  */
    Parent root;
    
    /** The ResourceBundle instance.  */
    ResourceBundle bundle;
    
    Application app;
    
    /** Define the window x offsets. */
    private double xOffset = 0;

    /** Define the window y offsets. */
    private double yOffset = 0;
    
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
        
        stage.setResizable(false);
        if(stage.isShowing()){
            stage.initStyle(StageStyle.UNDECORATED);
        }
        
        stage.getIcons().add(new Image(IPSXDesktopClient.class.getResourceAsStream("resources/imgs/icon.png")));
        stage.setTitle("IP Exchange");
    }
    
    public void startScene() throws IOException{        
        Scene scene = new Scene(root);        
        stage.setScene(scene);
        stage.show();
    }   
    
}
