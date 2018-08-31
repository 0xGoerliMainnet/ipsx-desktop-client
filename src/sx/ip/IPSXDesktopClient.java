package sx.ip;

import sx.ip.controllers.SystemTrayController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import dorkbox.util.OS;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.ip.controllers.NavControllerHandle;
import sx.ip.factories.HostServicesControllerFactory;
import sx.ip.utils.ProxyUtils;

/**
 * Main class responsible to load the main window application
 */
public class IPSXDesktopClient extends Application {

    /**
     * Class logger
     */
    static Logger LOGGER = LoggerFactory.getLogger(IPSXDesktopClient.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage stage) throws Exception {

        // instructs the javafx system not to exit implicitly when the last application window is shut.
        Platform.setImplicitExit(false);

        // Create Systray
        SystemTrayController systemTray = new SystemTrayController(stage, ProxyUtils.getBundle());
        systemTray.addAppToTray();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/fxml/FXMLDashboard.fxml"), ProxyUtils.getBundle());
        loader.setControllerFactory(new HostServicesControllerFactory(getHostServices()));
        NavControllerHandle.initializeStageScene(loader, stage, this);

    }

    /**
     * The start point of the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Load the log4j properties file
        PropertyConfigurator.configure("log4j.properties");

        try {
            // Set the system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            LOGGER.warn("Problems to set the look and feel", ex);
        }

        // Improve toolkit
        if (OS.isMacOsX() && OS.javaVersion <= 7) {
            System.setProperty("javafx.macosx.embedded", "true");
            java.awt.Toolkit.getDefaultToolkit();
        }

        LOGGER.info("Initializing IPSX service...");

        // Start the APP
        launch(args);
    }

}
