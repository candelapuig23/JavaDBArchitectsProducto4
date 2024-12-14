package JavaDBArchitects.vista;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JavaFXTest extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Crear un nodo simple para mostrar
        Label label = new Label("¡JavaFX está funcionando correctamente!");

        // Crear un contenedor para el nodo
        StackPane root = new StackPane(label);

        // Configurar la escena
        Scene scene = new Scene(root, 400, 200);

        // Configurar la ventana (Stage)
        primaryStage.setTitle("Prueba JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
