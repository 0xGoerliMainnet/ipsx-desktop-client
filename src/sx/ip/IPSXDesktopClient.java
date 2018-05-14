package sx.ip;

import java.util.Locale;
import java.util.ResourceBundle;
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
import sx.ip.factories.HostServicesControllerFactory;

/**
 * Main class responsible to load the main window application
 */
public class IPSXDesktopClient extends Application {

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
        
        SystemTrayController systemTray = new SystemTrayController(stage, getBundle());
        
        systemTray.addAppToTray();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLManualProxy.fxml"));

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
        stage.setScene(scene);
        stage.show();
            
    }

    /**
     *
     * @param args the command line arguments
     *
     */
    public static void main(String[] args) {
        if (OS.isMacOsX() && OS.javaVersion <= 7) {
            System.setProperty("javafx.macosx.embedded", "true");
            java.awt.Toolkit.getDefaultToolkit();
        }
        
        launch(args);
    }

    /**
     * @return the bundle language
     */
    public ResourceBundle getBundle() {
        return ResourceBundle.getBundle("sx.ip.bundles.bundle", new Locale("en", "EN"));
    }

}
