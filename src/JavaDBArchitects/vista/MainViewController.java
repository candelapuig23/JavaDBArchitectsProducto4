package JavaDBArchitects.vista;

import JavaDBArchitects.controlador.ControladorJPA;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import javafx.scene.control.cell.PropertyValueFactory;
import JavaDBArchitects.dao.entidades.ExcursionEntidad;
import JavaDBArchitects.dao.entidades.InscripcionEntidad;
import javafx.beans.property.SimpleStringProperty; // Para SimpleStringProperty
import java.util.List; // Para List
import JavaDBArchitects.dao.entidades.SocioEntidad;
import JavaDBArchitects.dao.jpa.SocioDAOJPA;
import JavaDBArchitects.dao.jpa.InscripcionDAOJPA;
import JavaDBArchitects.dao.entidades.SocioEntidad;
import java.math.BigDecimal;




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

    // Campos para Inscribir en Excursión
    @FXML private TextField txtNumeroSocio;
    @FXML private TextField txtCodigoExcursion;
    @FXML private DatePicker dateFechaInscripcion;

    // Campos para Listar Excursiones por Fecha
    @FXML
    private DatePicker dateInicio;
    @FXML
    private DatePicker dateFin;

    @FXML
    private TableView<ExcursionEntidad> tablaExcursiones;
    @FXML
    private TableColumn<ExcursionEntidad, String> colCodigo;
    @FXML
    private TableColumn<ExcursionEntidad, String> colDescripcion;
    @FXML
    private TableColumn<ExcursionEntidad, LocalDate> colFecha;
    @FXML
    private TableColumn<ExcursionEntidad, Integer> colNumDias;
    @FXML
    private TableColumn<ExcursionEntidad, Float> colPrecio;

//campos para listar inscripciones

    @FXML private TableView<InscripcionEntidad> tablaInscripciones;
    @FXML private TableColumn<InscripcionEntidad, Integer> colIdInscripcion;
    @FXML private TableColumn<InscripcionEntidad, String> colNombreSocio;
    @FXML private TableColumn<InscripcionEntidad, String> colDescripcionExcursion;
    @FXML private TableColumn<InscripcionEntidad, LocalDate> colFechaInscripcion;

    //Campo para eliminar un socio
    @FXML
    private TextField txtIdEliminarSocio; // Campo de entrada para el ID del socio

    @FXML
    private TextField txtNumeroSocioFactura; // Campo de entrada
    @FXML
    private TextArea txtResultadoFactura;    // Área de texto para el resultado

    //campos para modificar un socio
    @FXML
    private TextField txtIdSocioModificar; // Campo para el ID del socio
    @FXML
    private TextField txtNuevoNombre;      // Campo para el nuevo nombre
    @FXML
    private TextField txtNuevoNIF;         // Campo para el nuevo NIF
    @FXML
    private ComboBox<String> cbNuevoTipoSocio; // ComboBox para el nuevo tipo de socio
    @FXML
    private TextField txtNuevaFederacionId;    // Campo para el ID de la nueva federación

    @FXML
    public void initialize() {
        // Inicialización previa
        cbTipoSocio.getItems().addAll("Estandar", "Federado", "Infantil");
        cbTipoSocio.setOnAction(event -> actualizarFormularioTipoSocio());
        actualizarFormularioTipoSocio(); // Configuración inicial

        // Inicializar columnas de la tabla para listar excursiones
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colNumDias.setCellValueFactory(new PropertyValueFactory<>("numDias"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        // Configurar las columnas de la tabla
        colIdInscripcion.setCellValueFactory(new PropertyValueFactory<>("idInscripcion"));
        colNombreSocio.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSocio().getNombre()));
        colDescripcionExcursion.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getExcursion().getDescripcion()));
        colFechaInscripcion.setCellValueFactory(new PropertyValueFactory<>("fechaInscripcion"));
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

    // Método para inscribir en excursión
    @FXML
    private void inscribirEnExcursion() {
        String numeroSocioText = txtNumeroSocio.getText();
        String codigoExcursion = txtCodigoExcursion.getText();
        LocalDate fechaInscripcion = dateFechaInscripcion.getValue();

        // Validar campos requeridos
        if (numeroSocioText == null || numeroSocioText.trim().isEmpty() ||
                codigoExcursion == null || codigoExcursion.trim().isEmpty() ||
                fechaInscripcion == null) {
            mostrarError("Todos los campos son obligatorios.");
            return;
        }

        try {
            int numeroSocio = Integer.parseInt(numeroSocioText);

            // Llamada al controlador para inscribir en la excursión
            ControladorJPA.inscribirEnExcursionJPA(numeroSocio, codigoExcursion, fechaInscripcion);
            mostrarMensaje("Inscripción realizada con éxito.");
            limpiarFormularioInscripcion();

        } catch (NumberFormatException e) {
            mostrarError("Error: El número de socio debe ser un valor numérico.");
        } catch (IllegalArgumentException e) {
            mostrarError("Error: " + e.getMessage());
        } catch (Exception e) {
            mostrarError("Error al inscribir en la excursión: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método auxiliar para limpiar el formulario de inscripción
    private void limpiarFormularioInscripcion() {
        txtNumeroSocio.clear();
        txtCodigoExcursion.clear();
        dateFechaInscripcion.setValue(null);
    }

    @FXML
    private void listarExcursionesPorFecha() {
        // Obtener las fechas desde los DatePicker
        LocalDate fechaInicio = dateInicio.getValue();
        LocalDate fechaFin = dateFin.getValue();

        // Validar fechas
        if (fechaInicio == null || fechaFin == null) {
            mostrarError("Por favor, selecciona ambas fechas.");
            return;
        }
        if (fechaInicio.isAfter(fechaFin)) {
            mostrarError("La fecha de inicio no puede ser posterior a la fecha de fin.");
            return;
        }

        try {
            // Llamar al método del controlador para obtener las excursiones
            var excursiones = ControladorJPA.listarExcursionesPorFechaJPA(fechaInicio, fechaFin);

            // Actualizar la tabla
            tablaExcursiones.getItems().clear();
            tablaExcursiones.getItems().addAll(excursiones);

        } catch (Exception e) {
            mostrarError("Error al listar excursiones: " + e.getMessage());
            e.printStackTrace();
        }
    }

//listar inscripciones

    @FXML
    private void listarInscripciones() {
        try {
            // Obtener la lista de inscripciones
            List<InscripcionEntidad> inscripciones = ControladorJPA.listarInscripcionesJPA();

            // Actualizar la tabla
            tablaInscripciones.getItems().clear();
            tablaInscripciones.getItems().addAll(inscripciones);

        } catch (Exception e) {
            mostrarError("Error al listar inscripciones: " + e.getMessage());
            e.printStackTrace();
        }
    }
    //eliminar socio
    @FXML
    private void eliminarSocio() {
        String idSocioText = txtIdEliminarSocio.getText();
        // Validar que el campo no esté vacío
        if (idSocioText == null || idSocioText.trim().isEmpty()) {
            mostrarError("Por favor, ingresa un número de socio válido.");
            return;
        }
        try {
            int idSocio = Integer.parseInt(idSocioText);
            // Llamada al método del controlador JPA
            boolean eliminado = ControladorJPA.eliminarSocio(idSocio);
            if (eliminado) {
                mostrarMensaje("Socio eliminado con éxito.");
                txtIdEliminarSocio.clear(); // Limpia el campo
            } else {
                mostrarError("No se pudo eliminar el socio. Puede que no exista o haya ocurrido un error.");
            }
        } catch (NumberFormatException e) {
            mostrarError("Error: El ID del socio debe ser un número válido.");
        } catch (Exception e) {
            mostrarError("Error al eliminar el socio: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //metodo mostrarInscripcionesConFiltros
    @FXML
    private TextField txtNumeroSocioinscripcion;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private TextArea txtResultadosInscripciones;
    @FXML
    private void mostrarInscripcionesConFiltros() {
        try {
            // Capturar los filtros de la interfaz
            Integer numeroSocio = null;
            if (txtNumeroSocio.getText() != null && !txtNumeroSocio.getText().isEmpty()) {
                numeroSocio = Integer.parseInt(txtNumeroSocio.getText());
            }
            LocalDate fechaInicio = dpFechaInicio.getValue();
            LocalDate fechaFin = dpFechaFin.getValue();
            // Llamar al controlador JPA
            List<InscripcionEntidad> inscripciones = ControladorJPA.mostrarInscripcionesConFiltrosJPA(numeroSocio, fechaInicio, fechaFin);
            // Mostrar los resultados en el TextArea
            if (inscripciones == null || inscripciones.isEmpty()) {
                txtResultadosInscripciones.setText("No se encontraron inscripciones con los filtros seleccionados.");
            } else {
                StringBuilder resultados = new StringBuilder();
                for (InscripcionEntidad inscripcion : inscripciones) {
                    resultados.append("ID Inscripción: ").append(inscripcion.getIdInscripcion()).append("\n");
                    resultados.append("Socio: ").append(inscripcion.getSocio().getNombre()).append("\n");
                    resultados.append("Excursión: ").append(inscripcion.getExcursion().getDescripcion()).append("\n");
                    resultados.append("Fecha: ").append(inscripcion.getFechaInscripcion()).append("\n");
                    resultados.append("----------------------\n");
                }
                txtResultadosInscripciones.setText(resultados.toString());
            }
        } catch (Exception e) {
            mostrarError("Error al mostrar las inscripciones: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // MODIFICAR DATOS DE UN SOCIO
    @FXML
    private void modificarDatosSocio() {
        try {
            // Obtener y validar el ID del socio
            String idSocioText = txtIdSocioModificar.getText();
            if (idSocioText == null || idSocioText.trim().isEmpty()) {
                mostrarError("Por favor, ingresa el número de socio.");
                return;
            }
            int idSocio = Integer.parseInt(idSocioText);
            // Obtener los otros campos (permitiendo campos vacíos)
            String nuevoNombre = txtNuevoNombre.getText().trim().isEmpty() ? null : txtNuevoNombre.getText();
            String nuevoNIF = txtNuevoNIF.getText().trim().isEmpty() ? null : txtNuevoNIF.getText();
            String nuevoTipoSocio = cbNuevoTipoSocio.getValue();
            Integer nuevaFederacionId = null;
            if (txtNuevaFederacionId.getText() != null && !txtNuevaFederacionId.getText().trim().isEmpty()) {
                nuevaFederacionId = Integer.parseInt(txtNuevaFederacionId.getText());
            }
            // Llamar al controlador JPA
            ControladorJPA.modificarDatosSocioJPA(idSocio, nuevoNombre, nuevoNIF, nuevoTipoSocio, nuevaFederacionId);
            // Mostrar mensaje de éxito
            mostrarMensaje("Datos del socio modificados con éxito.");
            limpiarFormularioModificarSocio();
        } catch (NumberFormatException ex) {
            mostrarError("El ID del socio y la ID de la federación deben ser números válidos.");
        } catch (Exception ex) {
            mostrarError("Error al modificar los datos del socio: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    // Método para limpiar el formulario
    private void limpiarFormularioModificarSocio() {
        txtIdSocioModificar.clear();
        txtNuevoNombre.clear();
        txtNuevoNIF.clear();
        cbNuevoTipoSocio.getSelectionModel().clearSelection();
        txtNuevaFederacionId.clear();

    }
    //Mostrar Socios por tipo

    @FXML private ComboBox<String> cbTipoSocioBuscar;
    @FXML private TextArea txtResultadosSocios;

    @FXML
    private void mostrarSociosPorTipo() {
        String tipoSeleccionado = cbTipoSocioBuscar.getValue();

        // Validar la selección
        if (tipoSeleccionado == null || tipoSeleccionado.trim().isEmpty()) {
            mostrarError("Por favor, selecciona un tipo de socio.");
            return;
        }

        try {
            int tipoSocio = convertirTipoSocio(tipoSeleccionado);

            // Llamar al controlador para obtener los socios
            ControladorJPA.listarSociosPorTipoJPA(tipoSocio);
            List<SocioEntidad> sociosEntidad = ControladorJPA.listarSociosPorTipoJPA(tipoSocio);

            // Mostrar los resultados en el TextArea
            txtResultadosSocios.setText("Resultados cargados correctamente. Revisa tu consola para detalles.");
            // Construir el texto para mostrar en el TextArea
            if (sociosEntidad == null || sociosEntidad.isEmpty()) {
                txtResultadosSocios.setText("No se encontraron socios para el tipo seleccionado.");
            } else {
                StringBuilder resultados = new StringBuilder();
                for (SocioEntidad socio : sociosEntidad) {
                    resultados.append("ID: ").append(socio.getNumeroSocio()).append("\n");
                    resultados.append("Nombre: ").append(socio.getNombre()).append("\n");
                    resultados.append("Tipo: ").append(socio.getTipoSocio()).append("\n");
                    resultados.append("NIF: ").append(socio.getNif()).append("\n");
                    if (socio.getFederacion() != null) {
                        resultados.append("Federación: ").append(socio.getFederacion().getNombre()).append("\n");
                    }
                    resultados.append("----------------------\n");
                }
                txtResultadosSocios.setText(resultados.toString());
            }

        } catch (Exception e) {
            mostrarError("Error al mostrar los socios: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int convertirTipoSocio(String tipo) {
        return switch (tipo.toLowerCase()) {
            case "federado" -> 1;
            case "infantil" -> 2;
            default -> 0; // Estandar
        };
    }

    @FXML
    public void consultarFacturaMensual() {
        String numeroSocioText = txtNumeroSocioFactura.getText();

        try {
            if (numeroSocioText == null || numeroSocioText.trim().isEmpty()) {
                mostrarError("Por favor, ingresa un número de socio válido.");
                return;
            }

            int numeroSocio = Integer.parseInt(numeroSocioText);
            // Llamada corregida al método estático
            String resultado = ControladorJPA.consultarFacturaMensualJPA(numeroSocio);
            txtResultadoFactura.setText(resultado);
        } catch (NumberFormatException e) {
            mostrarError("El número de socio debe ser un número válido.");
        } catch (Exception e) {
            mostrarError("Error al consultar la factura mensual: " + e.getMessage());
            e.printStackTrace();
        }
    }



}




