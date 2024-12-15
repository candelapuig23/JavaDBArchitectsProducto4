package JavaDBArchitects.vista;
import JavaDBArchitects.controlador.Controlador;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargar el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        Parent root = loader.load();

        // Configurar la escena
        Scene scene = new Scene(root);
        primaryStage.setTitle("Software centro excursionista");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Lanzar la aplicación JavaFX
        launch();
    }
}

