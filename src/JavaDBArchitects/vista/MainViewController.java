package JavaDBArchitects.vista;

import JavaDBArchitects.controlador.ControladorJPA;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

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

    @FXML private Label lblIdFederacion, lblNombreFederacion, lblIdPadreMadre;

    // Campos para Eliminar Excursión e Inscripción
    @FXML private TextField txtIdEliminarExcursion;
    @FXML private TextField txtIdEliminarInscripcion;

    @FXML
    public void initialize() {
        // Inicializar ComboBox y configurar evento para cambio dinámico
        cbTipoSocio.getItems().addAll("Estandar", "Federado", "Infantil");
        cbTipoSocio.setOnAction(event -> actualizarFormularioTipoSocio());
        actualizarFormularioTipoSocio(); // Configuración inicial
    }

    // Actualiza la visibilidad de los campos del formulario según el tipo de socio seleccionado
    @FXML
    private void actualizarFormularioTipoSocio() {
        String tipoSocio = cbTipoSocio.getValue();

        // Ocultar todos los campos dinámicos
        lblIdFederacion.setVisible(false);
        txtIdFederacion.setVisible(false);
        lblNombreFederacion.setVisible(false);
        txtNombreFederacion.setVisible(false);
        lblIdPadreMadre.setVisible(false);
        txtIdPadreMadre.setVisible(false);

        // Mostrar campos específicos
        if ("Federado".equalsIgnoreCase(tipoSocio)) {
            lblIdFederacion.setVisible(true);
            txtIdFederacion.setVisible(true);
            lblNombreFederacion.setVisible(true);
            txtNombreFederacion.setVisible(true);
        } else if ("Infantil".equalsIgnoreCase(tipoSocio)) {
            lblIdPadreMadre.setVisible(true);
            txtIdPadreMadre.setVisible(true);
        }
    }


    @FXML
    private void registrarSocio() {
        String nombre = txtNombre.getText();
        String tipoSocio = cbTipoSocio.getValue();
        String nif = txtNIF.getText();

        int idFederacion = 0; // Valor por defecto si no es Federado
        Integer idPadreMadre = null; // Valor por defecto
        String nombreFederacion = null;

        try {
            // Validación de campos obligatorios
            if (nombre == null || nombre.trim().isEmpty() || nif == null || nif.trim().isEmpty()) {
                mostrarError("El nombre y NIF son campos obligatorios.");
                return;
            }

            // Lógica según tipo de socio
            if ("Federado".equalsIgnoreCase(tipoSocio)) {
                if (txtIdFederacion.getText() != null && !txtIdFederacion.getText().trim().isEmpty()) {
                    idFederacion = Integer.parseInt(txtIdFederacion.getText());
                }
                nombreFederacion = txtNombreFederacion.getText();
            } else if ("Infantil".equalsIgnoreCase(tipoSocio)) {
                if (txtIdPadreMadre.getText() != null && !txtIdPadreMadre.getText().trim().isEmpty()) {
                    idPadreMadre = Integer.parseInt(txtIdPadreMadre.getText());
                }
            }

            // Llamada al método del ControladorJPA
            ControladorJPA.registrarSocioJPA(
                    nombre,
                    tipoSocio(tipoSocio),
                    nif,
                    idFederacion,      // Pasa 0 si no aplica
                    idPadreMadre,      // Pasa null si no aplica
                    null,
                    nombreFederacion   // Pasa null si no aplica
            );

            mostrarMensaje("Socio registrado con éxito.");
            limpiarFormularioSocio();

        } catch (NumberFormatException e) {
            mostrarError("Error: Verifica que los campos ID Federación e ID Padre/Madre sean numéricos.");
        } catch (Exception e) {
            mostrarError("Error al registrar el socio: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método auxiliar para limpiar los campos del formulario
    private void limpiarFormularioSocio() {
        txtNombre.clear();
        txtNIF.clear();
        txtIdFederacion.clear();
        txtNombreFederacion.clear();
        txtIdPadreMadre.clear();
        cbTipoSocio.getSelectionModel().clearSelection();
        actualizarFormularioTipoSocio(); // Reinicia la visibilidad de campos
    }


    // Método para registrar excursión
    @FXML
    private void registrarExcursion() {
        String idExcursion = txtIdExcursion.getText();
        String descripcion = txtDescripcion.getText();
        LocalDate fecha = (dateFecha.getValue() != null) ? dateFecha.getValue() : null;
        int numDias;
        float precio;

        try {
            numDias = Integer.parseInt(txtNumDias.getText());
            precio = Float.parseFloat(txtPrecio.getText());
        } catch (NumberFormatException e) {
            mostrarError("Error: Número de días y precio deben ser valores numéricos.");
            return;
        }

        if (fecha == null || idExcursion.isEmpty() || descripcion.isEmpty()) {
            mostrarError("Error: Por favor, completa todos los campos.");
            return;
        }

        try {
            ControladorJPA.registrarExcursionJPA(idExcursion, descripcion, fecha, numDias, precio);
            mostrarMensaje("Excursión registrada con éxito.");
        } catch (Exception e) {
            mostrarError("Error al registrar la excursión: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para eliminar excursión
    @FXML
    private void eliminarExcursion() {
        String idExcursion = txtIdEliminarExcursion.getText();

        if (idExcursion == null || idExcursion.trim().isEmpty()) {
            mostrarError("Por favor, ingresa un ID válido.");
            return;
        }
        try {
            boolean eliminado = ControladorJPA.eliminarExcursionJPA(idExcursion);
            if (eliminado) {
                mostrarMensaje("Excursión eliminada correctamente.");
                txtIdEliminarExcursion.clear();
            } else {
                mostrarError("El ID indicado no corresponde a ninguna excursión.");
            }
        } catch (Exception e) {
            mostrarError("Error al eliminar la excursión: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para eliminar inscripción
    @FXML
    private void eliminarInscripcion() {
        String idInscripcionText = txtIdEliminarInscripcion.getText();

        if (idInscripcionText == null || idInscripcionText.trim().isEmpty()) {
            mostrarError("Por favor, ingresa un ID de inscripción válido.");
            return;
        }

        try {
            int idInscripcion = Integer.parseInt(idInscripcionText);
            boolean eliminado = ControladorJPA.eliminarInscripcion(idInscripcion);
            if (eliminado) {
                mostrarMensaje("Inscripción eliminada correctamente.");
                txtIdEliminarInscripcion.clear();
            } else {
                mostrarError("El ID indicado no corresponde a ninguna inscripción.");
            }
        } catch (NumberFormatException e) {
            mostrarError("Error: El ID de inscripción debe ser un número válido.");
        } catch (Exception e) {
            mostrarError("Error al eliminar la inscripción: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int tipoSocio(String tipoSocio) {
        return switch (tipoSocio.toLowerCase()) {
            case "federado" -> 1;
            case "infantil" -> 2;
            default -> 0;
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
