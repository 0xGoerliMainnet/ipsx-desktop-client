package sx.ip;

import sx.ip.controllers.SystemTrayController;
import sx.ip.controllers.FXMLManualProxyController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import dorkbox.util.OS;
import javax.swing.UIManager;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
     * Define the window x offsets
     */
    private double xOffset = 0;

    /**
     * Define the window y offsets
     */
    private double yOffset = 0;

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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/fxml/FXMLManualProxy.fxml"), ProxyUtils.getBundle());

        loader.setControllerFactory(new HostServicesControllerFactory(getHostServices()));

        Parent root = loader.load();

        FXMLManualProxyController controller = loader.getController();

        controller.setStage(stage);

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

        // Setup the scene
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("resources/imgs/icon.png")));
        stage.setTitle("IP Exchange");
        stage.setScene(scene);
        stage.show();

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
        } catch (Exception ex) {
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
