package JavaDBArchitects.vista;

import JavaDBArchitects.controlador.ControladorJPA; // Asegúrate de que ControladorJPA existe
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate; // Import necesario para LocalDate

public class MainViewController {

    // Campos para Registrar Excursión
    @FXML private TextField txtIdExcursion;
    @FXML private TextField txtDescripcion;
    @FXML private DatePicker dateFecha;
    @FXML private TextField txtNumDias;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtCodigo;

    // Campos para Registrar Socio
    @FXML private TextField txtNombre;
    @FXML private ComboBox<String> cbTipoSocio;
    @FXML private TextField txtNIF;
    @FXML private TextField txtIdFederacion;
    @FXML private TextField txtNombreFederacion;
    @FXML private TextField txtIdPadreMadre;

    // Método initialize: Se ejecuta al cargar la vista
    @FXML
    public void initialize() {
        // Inicializar el ComboBox de tipo de socio
        cbTipoSocio.getItems().addAll("Estandar", "Federado", "Infantil");
    }


    @FXML
    private void registrarExcursion() {
        // Recoge los datos ingresados en la interfaz
        String idExcursion = txtIdExcursion.getText();
        String descripcion = txtDescripcion.getText();
        LocalDate fecha = (dateFecha.getValue() != null) ? dateFecha.getValue() : null;
        int numDias;
        float precio;
        String codigo = txtCodigo.getText();

        try {
            numDias = Integer.parseInt(txtNumDias.getText());
            precio = Float.parseFloat(txtPrecio.getText());
        } catch (NumberFormatException e) {
            mostrarError("Error: Número de días y precio deben ser valores numéricos.");
            return;
        }

        if (fecha == null || idExcursion.isEmpty() || descripcion.isEmpty() || codigo.isEmpty()) {
            mostrarError("Error: Por favor, completa todos los campos.");
            return;
        }

        // Llamar al método del ControladorJPA para registrar la excursión
        try {
            ControladorJPA.registrarExcursionJPA(idExcursion, descripcion, fecha, numDias, precio);
            mostrarMensaje("Excursión registrada con éxito.");
        } catch (Exception e) {
            mostrarError("Error al registrar la excursión: " + e.getMessage());
            e.printStackTrace();
        }
    }



    // Método para registrar socio
    @FXML
    private void registrarSocio() {
        String nombre = txtNombre.getText();
        String tipoSocio = cbTipoSocio.getValue();
        String nif = txtNIF.getText();
        Integer idFederacion = null;
        String nombreFederacion = null;
        Integer idPadreMadre = null;

        try {
            if ("Federado".equalsIgnoreCase(tipoSocio)) {
                idFederacion = Integer.parseInt(txtIdFederacion.getText());
                nombreFederacion = txtNombreFederacion.getText();
            } else if ("Infantil".equalsIgnoreCase(tipoSocio)) {
                idPadreMadre = Integer.parseInt(txtIdPadreMadre.getText());
            }

            ControladorJPA.registrarSocioJPA(nombre, tipoSocio(tipoSocio), nif, idFederacion, idPadreMadre, null, nombreFederacion);
            mostrarMensaje("Socio registrado con éxito.");
        } catch (Exception e) {
            mostrarError("Error al registrar el socio: " + e.getMessage());
        }
    }

    //Metodo para eliminar excursion

    @FXML  private TextField txtIdEliminarExcursion;

    @FXML private void eliminarExcursion(){

        String idExcursion =txtIdEliminarExcursion.getText();

        if (idExcursion ==null|| idExcursion.trim().isEmpty()){
            mostrarError("Por favor, ingresa un ID");
            return;
        }
        try{
            boolean eliminado = ControladorJPA.eliminarExcursionJPA(idExcursion);

            if (eliminado) {
                mostrarMensaje("Excursión eliminada correctamente");
                txtIdEliminarExcursion.clear();
            } else {
                mostrarError("El ID indicado no corresponde a ninguna excursión existente");
            }
        }catch (Exception e){
            mostrarError("Error al eliminar la excursión:" + e.getMessage());
            e.printStackTrace();
        }
    }

    private int tipoSocio(String tipoSocio) {
        return switch (tipoSocio.toLowerCase()) {
            case "federado" -> 1;
            case "infantil" -> 2;
            default -> 0; // Estandar
        };
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

