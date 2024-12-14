package JavaDBArchitects.vista;
import JavaDBArchitects.controlador.Controlador;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXTest extends Application {
    @Override

    public void start(Stage primaryStage) throws IOException {
       /// Llama a la lógica de inicialización (si es necesaria)
        Controlador.iniciarAplicacion();

        // Cargar el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vista/MainView.fxml"));
        Parent root = loader.load();

        // Configurar la escena y la ventana
        Scene scene = new Scene(root);
        primaryStage.setTitle("Centro excursionista Senderos y Montañas");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Lanzar la aplicación JavaFX
        launch(args);
    }
}
