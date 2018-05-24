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
import dorkbox.util.OS;

import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class responsible to have all System Tray content in the application.
 *
 * System Tray Library took from this repository:
 * https://github.com/dorkbox/SystemTray
 */
public class SystemTrayController {

    /**
     * The logger Object.
     */
    static Logger LOGGER = LoggerFactory.getLogger(SystemTrayController.class);

    /**
     * application stage is stored so that it can be shown and hidden based on
     * system tray icon operations.
     */
    private final Stage stage;

    /**
     * language support for the application.
     */
    private final ResourceBundle bundle;

    /**
     * The SystemTray instance.
     */
    private SystemTray systemTray;

    /**
     * Constructor responsible to load the necessary data
     *
     * @param stage Current stage in the application window
     * @param bundle Language support choose
     */
    public SystemTrayController(Stage stage, ResourceBundle bundle) {
        this.stage = stage;
        this.bundle = bundle;
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

            // IconFolder
            String iconfolder = "default";
            if (OS.isMacOsX()) {
                iconfolder = "macos";
            }

            // We support the sizes: 16, 22, 24, 32 and 64
            String systrayImageFile = "imgs/systray/" + iconfolder + "/icon64.png";
            if (systemTray.getTrayImageSize() <= 16) {
                systrayImageFile = "imgs/systray/" + iconfolder + "/icon16.png";
            } else {
                if (systemTray.getTrayImageSize() <= 18) {
                    systrayImageFile = "imgs/systray/" + iconfolder + "/icon18.png";
                } else {
                    if (systemTray.getTrayImageSize() <= 22) {
                        systrayImageFile = "imgs/systray/" + iconfolder + "/icon22.png";
                    } else {
                        if (systemTray.getTrayImageSize() <= 24) {
                            systrayImageFile = "imgs/systray/" + iconfolder + "/icon24.png";
                        } else {
                            if (systemTray.getTrayImageSize() <= 32) {
                                systrayImageFile = "imgs/systray/" + iconfolder + "/icon32.png";
                            } else {
                                systrayImageFile = "imgs/systray/" + iconfolder + "/icon64.png";
                            }
                        }
                    }
                }
            }

            systemTray.setImage(SystemTrayController.class.getResource(systrayImageFile));
            systemTray.setTooltip("IPSX Desktop Client");

            Menu mainMenu = systemTray.getMenu();

            mainMenu.add(new MenuItem(bundle.getString("key.systray.menu.mainwindow"), new ActionListener() {
                @Override
                public void actionPerformed(final java.awt.event.ActionEvent e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            showStage();
                        }
                    });
                }
            })).setShortcut('o'); // case does not matter

            systemTray.getMenu().add(new MenuItem(bundle.getString("key.systray.menu.exit"), new ActionListener() {
                @Override
                public void actionPerformed(final java.awt.event.ActionEvent e) {
                    systemTray.shutdown();

                    if (!JavaFX.isEventThread()) {
                        JavaFX.dispatch(new Runnable() {
                            @Override
                            public void run() {
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

        } catch (RuntimeException e) {
            LOGGER.error("Unable to init system tray", e);
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
