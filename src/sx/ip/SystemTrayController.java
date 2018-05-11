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

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * Class responsible to have all System Tray content in the application
 */
public class SystemTrayController {
    
    // application stage is stored so that it can be shown and hidden based on system tray icon operations.
    private final Stage stage;
    
    // language support for the application
    private final ResourceBundle bundle;
    
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
            // ensure awt toolkit is initialized.
            Toolkit.getDefaultToolkit();

            // app requires system tray support, just exit if there is no support.
            if (!SystemTray.isSupported()) {
                openFatalErrorAlert();
            }
            
            SystemTray tray = SystemTray.getSystemTray();
            
            // set up a system tray icon.
            TrayIcon trayIcon = setupTrayIcon();

            // if the user double-clicks on the tray icon, show the main app stage.
            trayIcon.addActionListener(event -> Platform.runLater(this::showStage));

            trayIcon.setPopupMenu(buildPopupMenu());

            // add the application tray icon to the system tray.
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("Unable to init system tray: " + e);
        }
    }
    
    /**
     * Construct all Popup menu content available on the System Tray
     * @return all the Popup menu content available
     */
    private PopupMenu buildPopupMenu() {
        
        // Create a pop-up menu components
        MenuItem exitItem = new MenuItem(bundle.getString("key.systray.menu.exit"));
        exitItem.addActionListener(event -> {
            System.exit(0);
        });
        
        MenuItem mainWindowItem = new MenuItem(bundle.getString("key.systray.menu.mainwindow"));
        mainWindowItem.addActionListener(event -> Platform.runLater(this::showStage));
        
        // setup the popup menu for the application.
        final PopupMenu popup = new PopupMenu();
        popup.add(mainWindowItem);
        popup.add(exitItem);
        
        return popup;
    }
    
    /**
     * Alert screen that will open if the System Tray is not supported by the OS
     */
    private void openFatalErrorAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setResizable(false);
        alert.setTitle(bundle.getString("key.systray.error.fatal.title"));
        alert.setHeaderText(bundle.getString("key.systray.error.fatal.header"));
        alert.setContentText(bundle.getString("key.systray.error.fatal.content"));
        alert.showAndWait();
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK || result.get() == ButtonType.CLOSE){
            System.exit(0);
        }
    }
    
    /**
     * set up the system tray icon.
     * @return the system tray icon generated
     */
    private TrayIcon setupTrayIcon() {
        URL imageLoc = getClass().getResource("imgs/systray-icon.png");
        Image image = Toolkit.getDefaultToolkit().getImage(imageLoc);
        return new TrayIcon(image);
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
