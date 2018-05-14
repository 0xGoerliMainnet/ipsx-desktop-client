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
package sx.ip;

import dorkbox.systemTray.Menu;
import dorkbox.systemTray.MenuItem;
import dorkbox.systemTray.SystemTray;
import dorkbox.util.JavaFX;

import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * Class responsible to have all System Tray content in the application
 */
public class SystemTrayController {
    
    // application stage is stored so that it can be shown and hidden based on system tray icon operations.
    private final Stage stage;
    
    // language support for the application
    private final ResourceBundle bundle;
    
    private SystemTray systemTray;
    
    private static final URL SYSTRAY_ICON = SystemTrayController.class.getResource("imgs/systray-icon-32x32.png");

    /**
     * Constructor responsible to load the necessary data
     * @param stage current stage in the application window
     * @param bundle language support choose
     */
    public SystemTrayController(Stage stage, ResourceBundle bundle){
        this.stage = stage;
        this.bundle =  bundle;
    }
    
    /**
     * Sets up a system tray icon for the application.
     */
    public void addAppToTray() {
        try {
            
            this.systemTray = SystemTray.get();
            
            // app requires system tray support, just exit if there is no support.
            if (systemTray == null) {
            	throw new RuntimeException("Unable to load SystemTray!");
            }
            
            
            systemTray.setImage(SYSTRAY_ICON);
            
            Menu mainMenu = systemTray.getMenu();
            
            mainMenu.add(new MenuItem(bundle.getString("key.systray.menu.mainwindow"), new ActionListener() {
                @Override
                public
                void actionPerformed(final java.awt.event.ActionEvent e) {
    	            Platform.runLater(new Runnable() {
    	                @Override public void run() {
    	                	showStage();
    	                }
    	            });
                	        
                }
            })).setShortcut('o'); // case does not matter

            systemTray.getMenu().add(new MenuItem(bundle.getString("key.systray.menu.exit"), new ActionListener() {
                @Override
                public
                void actionPerformed(final java.awt.event.ActionEvent e) {
                    systemTray.shutdown();

                    if (!JavaFX.isEventThread()) {
                        JavaFX.dispatch(new Runnable() {
                            @Override
                            public
                            void run() {
                            	stage.hide(); // must do this BEFORE Platform.exit() otherwise odd errors show up
                                Platform.exit();  // necessary to close javaFx
                            }
                        });
                    } else {
                    	stage.hide(); // must do this BEFORE Platform.exit() otherwise odd errors show up
                        Platform.exit();  // necessary to close javaFx
                    }

                    //System.exit(0);  not necessary if all non-daemon threads have stopped.
                }
            })).setShortcut('q'); // case does not matter
            
        } catch (Exception e) {
            System.out.println("Unable to init system tray: " + e);
        }
    }
    
    /**
     * Shows the application stage and ensures that it is brought ot the front
     * of all stages.
     */
    private void showStage() {
        if (stage != null) {
            stage.show();
            stage.toFront();
        } 
    }
    
}
