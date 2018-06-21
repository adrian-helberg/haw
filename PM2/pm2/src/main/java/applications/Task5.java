/**
 * PM2 Praktikum, Aufgabenblatt 5
 * @author Adrian Helberg, Rodrigo Ehlers
 */

package applications;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import views.Task5View;

public class Task5 extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Task5View.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 800, 600);

        stage.setScene(scene);
        stage.setTitle("Aufgabe 5");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
