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
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import sx.ip.utils.ProxyUtils;

/**
 * Class responsible for do the navigation
 */
public class NavControllerHandle {
    
    public static Object extra;
    
    /**
    * Method responsible for the first scene initialization.
    *
    * @param loader
    *          The instance of the current loader.
    * @param stage
    *          The instance of the current stage.
    * @param app
    *          The instance of the current application.
    * 
    * @throws IOException
    */
    public static void initializeStageScene(FXMLLoader loader, Stage stage, Application app) throws IOException{
        navigateTo(loader, stage, app);        
    }
    
    /**
    * Method responsible for the navigation between scenes.
    *
    * @param loader
    *          The instance of the current loader.
    * @param stage
    *          The instance of the current stage.
    * @param app
    *          The instance of the current application.
    * 
    * @throws IOException
    */
    public static void navigateTo(FXMLLoader loader, Stage stage, Application app) throws IOException{
        Parent root = loader.load();

        NavController controller = loader.getController();

        controller.setupController(stage, root, ProxyUtils.getBundle(), app);
        controller.startScene();
    
    }
    
    /**
    * Method responsible for the navigation between scenes.
    *
    * @param loader
    *          The instance of the current loader.
    * @param stage
    *          The instance of the current stage.
    * @param app
    *          The instance of the current application.
    * @param extra
    *          The instance of the object passed between controllers.
    * 
    * @throws IOException
    */
    public static void navigateTo(FXMLLoader loader, Stage stage, Application app, Object extra) throws IOException{
        NavControllerHandle.extra = extra;
        
        Parent root = loader.load();

        NavController controller = loader.getController();

        controller.setupController(stage, root, ProxyUtils.getBundle(), app);
        controller.startScene();
    
    }
    
}
