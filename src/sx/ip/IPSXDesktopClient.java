package sx.ip;

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
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.ip.factories.HostServicesControllerFactory;
import sx.ip.utils.ProxyUtils;

/**
 * Main class responsible to load the main window application
 */
public class IPSXDesktopClient extends Application {

    static Logger LOGGER = LoggerFactory.getLogger(IPSXDesktopClient.class);

    //define your offsets here
    private double xOffset = 0;

    private double yOffset = 0;

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage stage) throws Exception {

        // instructs the javafx system not to exit implicitly when the last application window is shut.
        Platform.setImplicitExit(false);

        SystemTrayController systemTray = new SystemTrayController(stage, ProxyUtils.getBundle());

        systemTray.addAppToTray();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLManualProxy.fxml"), ProxyUtils.getBundle());

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

        //move around here
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }

        });

        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("imgs/icon.png")));
        stage.setTitle("IP Exchange");
        stage.setScene(scene);
        stage.show();

    }

    /**
     *
     * @param args the command line arguments
     *
     */
    public static void main(String[] args) {
        PropertyConfigurator.configure("log4j.properties");

        if (OS.isMacOsX() && OS.javaVersion <= 7) {
            System.setProperty("javafx.macosx.embedded", "true");
            java.awt.Toolkit.getDefaultToolkit();
        }

        LOGGER.info("Initializing IPSX service...");

        launch(args);
    }

}
