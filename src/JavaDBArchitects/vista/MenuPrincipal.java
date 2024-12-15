package JavaDBArchitects.vista;

import JavaDBArchitects.controlador.Controlador;
import JavaDBArchitects.controlador.ControladorJPA;
import JavaDBArchitects.modelo.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MenuPrincipal {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");




    public static void mostrarMenu() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("=== Menú Principal ===");
            System.out.println("1. Añadir Excursión");
            System.out.println("2. Registrar Socio");
            System.out.println("3. Inscribir en Excursión");
            System.out.println("4. Listar Excursiones por Fecha");
            System.out.println("5. Listar Inscripciones");
            System.out.println("6. Consultar Factura Mensual");
            System.out.println("7. Modificar Datos del Socio");
            System.out.println("8. Mostrar Socios por Tipo");
            System.out.println("9. Eliminar Inscripción");
            System.out.println("10. Eliminar Socio");
            System.out.println("11. Mostrar Inscripciones con Filtros");
            System.out.println("12. Eliminar Excursión");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> registrarExcursionJPAMenu();
                case 2 -> registrarSocioJPAMenu();
                case 3 -> inscribirEnExcursionJPAMenu();
                case 4 -> listarExcursionesPorFechaJPAMenu();
                case 5 -> listarInscripcionesJPAMenu();
                case 6 -> consultarFacturaMensualJPAMenu();
                case 7 -> modificarDatosSocioJPAMenu();
                case 8 -> mostrarSociosPorTipoJPAMenu();
                case 9 -> eliminarInscripcionJPAMenu();
                case 10 -> eliminarSocioJPAMenu();
                case 11 -> mostrarInscripcionesConFiltrosJPAMenu();
                case 12 -> eliminarExcursionJPAMenu();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }

    // =========Opción 1: Registrar excursión===================
    // ==JPA==

    private static void registrarExcursionJPAMenu() {
        System.out.println("=== Registrar Excursión ===");
        System.out.print("Código: ");
        String codigo = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Fecha (DD/MM/YYYY): ");
        String fechaStr = scanner.nextLine();

        LocalDate fecha = LocalDate.parse(fechaStr, formatter);

        System.out.print("Número de Días: ");
        int numeroDias = scanner.nextInt();
        scanner.nextLine();  // Capturar la línea vacía
        System.out.print("Precio: ");
        float precio = scanner.nextFloat();
        scanner.nextLine();  // Capturar la línea vacía

        // Ahora llamamos al controlador JPA
        ControladorJPA.registrarExcursionJPA(codigo, descripcion, fecha, numeroDias, precio);

    }

    //==JDBC==

    //FALTA

    //========Opción 2: Registrar socio=============
    //==JPA==

    private static void registrarSocioJPAMenu() {
        System.out.println("=== Registrar Socio ===");
        System.out.print("Nombre del Socio: ");
        String nombre = scanner.nextLine();
        System.out.print("Tipo de Socio (0: Estandar, 1: Federado, 2: Infantil): ");
        int tipoSocio = scanner.nextInt();
        scanner.nextLine();  // Capturar la línea vacía
        System.out.print("NIF: ");
        String nif = scanner.nextLine();

        int idFederacion = 0;  // Inicializamos en 0
        Integer idSocioPadre = null;  // Inicializamos en null
        String nombreFederacion = null;

        if (tipoSocio == 1) {  // Federado
            System.out.print("ID de la Federación: ");
            idFederacion = scanner.nextInt();
            scanner.nextLine();  // Capturar la línea vacía
            System.out.print("Nombre de la Federación: ");
            nombreFederacion = scanner.nextLine();
        } else if (tipoSocio == 2) {  // Infantil
            System.out.print("Número de Socio del Padre o Madre: ");
            idSocioPadre = scanner.nextInt();
            scanner.nextLine();  // Capturar la línea vacía
        }

        // Llamada al metodo en el controlador JPA
        ControladorJPA.registrarSocioJPA(nombre, tipoSocio, nif, idFederacion, idSocioPadre, null, nombreFederacion);
    }

    //==JDBC==

    private static void registrarSocioPAMenu() {
        System.out.println("=== Registrar Socio ===");
        System.out.print("Nombre del Socio: ");
        String nombre = scanner.nextLine();
        System.out.print("Tipo de Socio (0: Estandar, 1: Federado, 2: Infantil): ");
        int tipoSocio = scanner.nextInt();
        scanner.nextLine();  // Capturar la línea vacía
        System.out.print("NIF: ");
        String NIF = scanner.nextLine();

        Object extra = null;
        int idFederacion = 0;  // Lo inicializamos en 0
        Integer idSocioPadre = null;  // Lo inicializamos en null
        String nombreFederacion = null;  // Nombre de la federación para los socios federados

        if (tipoSocio == 0) {  // Estandar
            System.out.print("Tipo de Seguro (BASICO o COMPLETO): ");
            String tipoSeguroInput = scanner.nextLine().toUpperCase().trim();

            TipoSeguro tipoSeguro = null;
            if (tipoSeguroInput.equals("BASICO")) {
                tipoSeguro = TipoSeguro.BASICO;
            } else if (tipoSeguroInput.equals("COMPLETO")) {
                tipoSeguro = TipoSeguro.COMPLETO;
            } else {
                System.out.println("Error: Tipo de seguro inválido. Debe ser BASICO o COMPLETO.");
                return;
            }

            float precioSeguro = tipoSeguro == TipoSeguro.BASICO ? 50.0f : 100.0f;
            extra = new Seguro(tipoSeguro, precioSeguro); // El seguro se crea correctamente y se asigna
        } else if (tipoSocio == 1) {  // Federado
            System.out.print("ID de la Federación: ");
            idFederacion = scanner.nextInt();
            scanner.nextLine();  // Capturar la línea vacía
            System.out.print("Nombre de la Federación: ");
            nombreFederacion = scanner.nextLine();
            extra = new Federacion(idFederacion, nombreFederacion);
        }

        // Llamada al método en el controlador
        Controlador.registrarSocioPA(nombre, tipoSocio, NIF, idFederacion, idSocioPadre, extra, nombreFederacion);
    }

    //==Normal==

    /*private static void registrarSocio() {
        System.out.println("=== Registrar Socio ===");
        System.out.print("Número de Socio: ");
        int numeroSocio = scanner.nextInt();
        scanner.nextLine();  // Capturar la línea vacía
        System.out.print("Nombre del Socio: ");
        String nombre = scanner.nextLine();
        System.out.print("Tipo de Socio (0: Estandar, 1: Federado, 2: Infantil): ");
        int tipoSocio = scanner.nextInt();
        scanner.nextLine();  // Capturar la línea vacía
        System.out.print("NIF: ");
        String NIF = scanner.nextLine();

        Object extra = null;

        if (tipoSocio == 0) {  // Estandar
            System.out.print("Tipo de Seguro (BASICO o COMPLETO): ");
            String tipoSeguroInput = scanner.nextLine().toUpperCase().trim();

            TipoSeguro tipoSeguro = null;
            if (tipoSeguroInput.equals("BASICO")) {
                tipoSeguro = TipoSeguro.BASICO;
            } else if (tipoSeguroInput.equals("COMPLETO")) {
                tipoSeguro = TipoSeguro.COMPLETO;
            } else {
                System.out.println("Error: Tipo de seguro inválido. Debe ser BASICO o COMPLETO.");
                return;
            }

            float precioSeguro = tipoSeguro == TipoSeguro.BASICO ? 50.0f : 100.0f;
            extra = new Seguro(tipoSeguro, precioSeguro);

        } else if (tipoSocio == 1) {  // Federado
            System.out.print("ID de la Federación: ");
            int idFederacion = scanner.nextInt();
            scanner.nextLine();  // Capturar la línea vacía
            System.out.print("Nombre de la Federación: ");
            String nombreFederacion = scanner.nextLine();
            extra = new Federacion(idFederacion, nombreFederacion);

        } else if (tipoSocio == 2) {  // Infantil
            System.out.print("Número de Socio del Padre o Madre: ");
            int numSocioPadreOMadre = scanner.nextInt();
            extra = numSocioPadreOMadre;
        } else {
            System.out.println("Error: Tipo de socio no válido. Debe ser 0 (Estandar), 1 (Federado) o 2 (Infantil).");
            return;
        }

        try {
            Controlador.registrarSocio(numeroSocio, nombre, tipoSocio, NIF, extra, null);
            System.out.println("Socio registrado con éxito.");
        } catch (Exception e) {
            System.out.println("Error desconocido: " + e.getMessage());
        }
    } */

    //========Opción 3: Inscribir en excursión===========
    //==JPA==

    private static void inscribirEnExcursionJPAMenu() {
        System.out.println("=== Inscribir en Excursión ===");
        System.out.print("Número de Socio: ");
        int numeroSocio = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Código de la Excursión: ");
        String codigoExcursion = scanner.nextLine();
        System.out.print("Fecha de Inscripción (DD/MM/YYYY): ");
        String fechaStr = scanner.nextLine();

        LocalDate fechaInscripcion = LocalDate.parse(fechaStr, formatter);

        // Llamar al método del ControladorJPA
        try {
            ControladorJPA.inscribirEnExcursionJPA(numeroSocio, codigoExcursion, fechaInscripcion);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    //==JDBC==

    private static void inscribirEnExcursionPAMenu() {
        System.out.println("=== Inscribir en Excursión ===");
        System.out.print("Número de Socio: ");
        int numeroSocio = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Código de la Excursión: ");
        String codigoExcursion = scanner.nextLine();
        System.out.print("Fecha de Inscripción (DD/MM/YYYY): ");
        String fechaStr = scanner.nextLine();

        LocalDate fechaInscripcion = LocalDate.parse(fechaStr, formatter);

        // Llamada al método en Controlador en lugar de InscripcionDAO
        Controlador.inscribirEnExcursionPA(numeroSocio, codigoExcursion, fechaInscripcion);
    }

    //=======Opción 4: Listar excursiones por fecha ============
    //==JPA==

    private static void listarExcursionesPorFechaJPAMenu() {
        System.out.println("=== Listar Excursiones por Fechas ===");
        System.out.print("Fecha Inicio (DD/MM/YYYY): ");
        String fechaInicioStr = scanner.nextLine();
        System.out.print("Fecha Fin (DD/MM/YYYY): ");
        String fechaFinStr = scanner.nextLine();

        // Convertir las fechas ingresadas por el usuario a LocalDate
        LocalDate fechaInicio = LocalDate.parse(fechaInicioStr, formatter);
        LocalDate fechaFin = LocalDate.parse(fechaFinStr, formatter);

        // Llamar al método en el controladorJPA para listar excursiones por fecha usando JPA
        ControladorJPA.listarExcursionesPorFechaJPA(fechaInicio, fechaFin);
    }

    //==JDBC==

    private static void listarExcursionesPorFechaPAMenu() {
        System.out.println("=== Listar Excursiones por Fechas ===");
        System.out.print("Fecha Inicio (DD/MM/YYYY): ");
        String fechaInicioStr = scanner.nextLine();
        System.out.print("Fecha Fin (DD/MM/YYYY): ");
        String fechaFinStr = scanner.nextLine();

        LocalDate fechaInicio = LocalDate.parse(fechaInicioStr, formatter);
        LocalDate fechaFin = LocalDate.parse(fechaFinStr, formatter);

        // Convertimos las fechas a formato de cadena para el procedimiento almacenado
        String fechaInicioSQL = fechaInicio.toString();
        String fechaFinSQL = fechaFin.toString();

        // Llamada al método en el Controlador
        Controlador.listarExcursionesPorFecha(fechaInicioSQL, fechaFinSQL);
    }

    //==Normal==

    private static void listarExcursionesPorFechas() {
        System.out.println("=== Listar Excursiones por Fechas ===");
        System.out.print("Fecha Inicio (DD/MM/YYYY): ");
        String fechaInicioStr = scanner.nextLine();
        System.out.print("Fecha Fin (DD/MM/YYYY): ");
        String fechaFinStr = scanner.nextLine();

        LocalDate fechaInicio = LocalDate.parse(fechaInicioStr, formatter);
        LocalDate fechaFin = LocalDate.parse(fechaFinStr, formatter);

        Controlador.mostrarExcursionesEntreFechas(fechaInicio, fechaFin);
    }

    //========Opcion 5: Listar inscripciones===========
    //==JPA==

    private static void listarInscripcionesJPAMenu() {
        System.out.println("=== Listar Inscripciones ===");

        // Llamar al método del ControladorJPA para listar las inscripciones
        try {
            ControladorJPA.listarInscripcionesJPA();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    //==JDBC==

    private static void listarInscripcionesPAMenu() {
        System.out.println("=== Listar Inscripciones ===");

        // Llamada al método en Controlador
        Controlador.listarInscripcionesPA();
    }

    //==Normal==

    /*private static void listarInscripciones() {
        System.out.println("=== Listar Inscripciones ===");
        Controlador.listarInscripciones();
    }*/

    //=========Opción 6: Consultar factura mensual=============
    //==JPA==

    private static void consultarFacturaMensualJPAMenu() {
        System.out.println("=== Consultar Factura Mensual ===");
        System.out.print("Número de Socio: ");
        int numeroSocio = scanner.nextInt();
        scanner.nextLine();

        try {
            // Llamar al método del ControladorJPA para calcular la factura mensual
            ControladorJPA.consultarFacturaMensualJPA(numeroSocio);
        } catch (Exception e) {
            System.err.println("Error al consultar la factura mensual: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //==JDBC==

    //FALTA

    //=======Opcion 7: Modificar datos socio===============
    //==JPA==

    private static void modificarDatosSocioJPAMenu() {
        System.out.println("=== Modificar Datos del Socio ===");
        System.out.print("Número de Socio: ");
        int numeroSocio = scanner.nextInt();
        scanner.nextLine();  // Capturar la línea vacía

        System.out.print("Nuevo nombre del socio (o presione Enter para omitir): ");
        String nuevoNombre = scanner.nextLine();
        System.out.print("Nuevo NIF (o presione Enter para omitir): ");
        String nuevoNIF = scanner.nextLine();
        System.out.print("Nuevo Tipo de Socio (Estandar, Federado, Infantil; o presione Enter para omitir): ");
        String nuevoTipoSocio = scanner.nextLine();
        System.out.print("ID de la nueva Federación (o presione Enter para omitir): ");
        String nuevaFederacionIdStr = scanner.nextLine();
        Integer nuevaFederacionId = nuevaFederacionIdStr.isEmpty() ? null : Integer.parseInt(nuevaFederacionIdStr);
        // Ahora llamamos al controlador JPA
        ControladorJPA.modificarDatosSocioJPA(numeroSocio, nuevoNombre, nuevoNIF, nuevoTipoSocio, nuevaFederacionId);
    }

    //==JDBC==

    //FALTA

    //==Normal==

    private static void modificarDatosSocio() {
        System.out.println("=== Modificar Datos del Socio ===");
        System.out.print("Número de Socio: ");
        int numeroSocio = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nuevo nombre del socio: ");
        String nuevoNombre = scanner.nextLine();

        Controlador.modificarDatosSocio(numeroSocio, nuevoNombre);
    }

    //======Opción 8: Mostrar socios por tipo=============
    //==JPA==

    private static void mostrarSociosPorTipoJPAMenu() {
        System.out.println("=== Mostrar Socios por Tipo ===");
        System.out.print("Tipo de Socio (0: Estandar, 1: Federado, 2: Infantil): ");
        int tipoSocio = scanner.nextInt();
        scanner.nextLine();  // Capturar la línea vacía

        // Llamar al método del ControladorJPA para listar los socios por tipo
        ControladorJPA.listarSociosPorTipoJPA(tipoSocio);
    }

    //==JDBC==

    private static void mostrarSociosPorTipoPAMenu() {
        System.out.println("=== Mostrar Socios por Tipo ===");
        System.out.print("Tipo de Socio (0: Estandar, 1: Federado, 2: Infantil): ");
        int tipoSocio = scanner.nextInt();
        scanner.nextLine();  // Capturar la línea vacía

        Controlador.listarSociosPorTipoPA(tipoSocio);
    }

    //=======Opción 9: Eliminar inscripción========
    //==JPA==

    private static void eliminarInscripcionJPAMenu() {
        System.out.println("=== Eliminar Inscripción ===");
        System.out.print("Número de Inscripción: ");
        int numeroInscripcion = Integer.parseInt(scanner.nextLine());

        // Llamada al método del ControladorJPA para eliminar la inscripción
        boolean exito = ControladorJPA.eliminarInscripcion(numeroInscripcion);
        if (exito) {
            System.out.println("Inscripción eliminada con éxito.");
        } else {
            System.out.println("No se pudo eliminar la inscripción. Puede que no exista.");
        }
    }

    //==Normal==

    private static void eliminarInscripcionPAMenu() {
        System.out.println("=== Eliminar Inscripción ===");
        System.out.print("Número de Inscripción: ");
        int numeroInscripcion = Integer.parseInt(scanner.nextLine());

        // Llamada al método en Controlador
        Controlador.eliminarInscripcionPA(numeroInscripcion);
    }

    //=======Opción 10: Eliminar socio========
    //==JPA==

    private static void eliminarSocioJPAMenu() {
        System.out.println("=== Eliminar Socio ===");
        System.out.print("Número de Socio: ");
        int numeroSocio = Integer.parseInt(scanner.nextLine());

        // Llamar al método del ControladorJPA para eliminar al socio
        boolean exito = ControladorJPA.eliminarSocio(numeroSocio);
        if (exito) {
            System.out.println("Socio eliminado con éxito.");
        } else {
            System.out.println("No se pudo eliminar el socio. Puede que no exista o haya ocurrido un error.");
        }
    }

    //==Normal==
    /*
    private static void eliminarSocio() {
        System.out.println("=== Eliminar Socio ===");
        System.out.print("Número de Socio: ");
        int numeroSocio = scanner.nextInt();
        scanner.nextLine();  // Capturar la línea vacía

        Controlador.eliminarSocio(numeroSocio);
    }*/


    //==========Opción 11: Mostrar inscripciones con filtros===========
    //==JPA==

    private static void mostrarInscripcionesConFiltrosJPAMenu() {
        System.out.println("=== Mostrar Inscripciones con Filtros ===");

        System.out.print("Ingrese el número de socio (o presione Enter para omitir): ");
        String numeroSocioStr = scanner.nextLine();
        Integer numeroSocio = numeroSocioStr.isEmpty() ? null : Integer.parseInt(numeroSocioStr);

        System.out.print("Ingrese la fecha de inicio (DD/MM/YYYY) o presione Enter para omitir: ");
        String fechaInicioStr = scanner.nextLine();
        LocalDate fechaInicio = fechaInicioStr.isEmpty() ? null : LocalDate.parse(fechaInicioStr, formatter);

        System.out.print("Ingrese la fecha de fin (DD/MM/YYYY) o presione Enter para omitir: ");
        String fechaFinStr = scanner.nextLine();
        LocalDate fechaFin = fechaFinStr.isEmpty() ? null : LocalDate.parse(fechaFinStr, formatter);

        // Llamar al método en el controlador JPA
        ControladorJPA.mostrarInscripcionesConFiltrosJPA(numeroSocio, fechaInicio, fechaFin);
    }

    //===========Opción 12============
    //==JPA==

    private static void eliminarExcursionJPAMenu() {
        System.out.println("=== Eliminar Excursión ===");
        System.out.print("ID de la Excursión: ");
        String idExcursion = scanner.nextLine();

        // Llamar al método en ControladorJPA
        boolean exito = ControladorJPA.eliminarExcursionJPA(idExcursion);
        if (exito) {
            System.out.println("Excursión eliminada con éxito.");
        } else {
            System.out.println("No se encontró la excursión o ocurrió un error.");
        }
    }

    //==Normal==

    /*private static void eliminarExcursion() {
        System.out.println("=== Eliminar Excursión ===");
        System.out.print("Código de la Excursión: ");
        String codigoExcursion = scanner.nextLine();

        boolean resultado = Controlador.eliminarExcursion(codigoExcursion);
        if (resultado) {
            mostrarMensaje("Excursión eliminada con éxito.");
        } else {
            mostrarMensaje("No se pudo eliminar la excursión.");
        }
    }*/

    //========Mensajes====

    public static void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public static void mostrarError(String mensaje) {
        System.err.println(mensaje);
    }
}