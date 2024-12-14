
import JavaDBArchitects.controlador.Controlador;

//clase Main



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargar el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("JavaDBArchitects/vista/MainView.fxml"));
        Scene scene = new Scene(loader.load()); // Crear la escena desde el archivo FXML

        // Configurar la ventana
        primaryStage.setTitle("Centro Excursionista Senderos y Montañas");
        primaryStage.setScene(scene); // Asociar la escena con la ventana
        primaryStage.show(); // Mostrar la ventana
    }

    public static void main(String[] args) {
        launch(args); // Llamar al método start para iniciar JavaFX
    }
}

/*
public class Main {
    public static void main(String[] args) {
        Controlador.iniciarAplicacion();  // Iniciar la aplicación con la inicialización de seguros y el menú
    }
}*/

























