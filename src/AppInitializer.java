import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * @author : Pathum Kaleesha
 * @since : 0.1.0
 * @day :19.10.21
 **/
public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch (args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("view/MainForm.fxml"));
        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Super_Market 'Main Form'");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
