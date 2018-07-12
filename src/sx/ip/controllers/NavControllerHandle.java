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
import sx.ip.factories.HostServicesControllerFactory;
import sx.ip.utils.ProxyUtils;

/**
 *
 * @author hygor
 */
public class NavControllerHandle {
    
    public static void initializeStageScene(FXMLLoader loader, Stage stage, Application app) throws IOException{
        //loader.setControllerFactory(new HostServicesControllerFactory(app.getHostServices()));
        navigateTo(loader, stage, app);        
    }
    
    public static void navigateTo(FXMLLoader loader, Stage stage, Application app) throws IOException{
        Parent root = loader.load();

        NavController controller = loader.getController();

        controller.setupController(stage, root, ProxyUtils.getBundle(), app);
        controller.startScene();
    
    }
}
