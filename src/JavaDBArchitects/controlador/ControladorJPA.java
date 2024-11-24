package JavaDBArchitects.controlador;

import JavaDBArchitects.modelo.dao.ExcursionDAOJPA;
import JavaDBArchitects.modelo.dao.InscripcionDAOJPA;
import JavaDBArchitects.modelo.dao.SocioDAOJPA;

import java.time.LocalDate;

public class ControladorJPA {

    // Opción 1: Añadir excursion

    public static void registrarExcursionJPA(String codigo, String descripcion, LocalDate fecha, int numDias, float precioInscripcion) {
        ExcursionDAOJPA excursionDAO = new ExcursionDAOJPA();
        try {
            excursionDAO.registrarExcursionJPA(codigo, descripcion, fecha, numDias, precioInscripcion);
            System.out.println("Excursión registrada con éxito.");
        } catch (Exception e) {
            System.err.println("Error al registrar la excursión: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Opción 2: Registrar Socio

    public static void registrarSocioJPA(String nombre, int tipoSocio, String nif, int idFederacion, Integer idSocioPadre, Object extra, String nombreFederacion) {
        SocioDAOJPA socioDAO = new SocioDAOJPA();
        try {
            socioDAO.registrarSocioJPA(nombre, tipoSocio, nif, idFederacion, idSocioPadre, extra, nombreFederacion);
        } catch (Exception e) {
            System.err.println("Error al registrar socio: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Opción 3: Inscribir en excursión

    private static final InscripcionDAOJPA inscripcionDAOJPA = new InscripcionDAOJPA();

    public static void inscribirEnExcursionJPA(int idSocio, String idExcursion, LocalDate fechaInscripcion) {
        try {
            inscripcionDAOJPA.inscribirEnExcursionJPA(idSocio, idExcursion, fechaInscripcion);
        } catch (IllegalArgumentException e) {
            System.err.println("Error al inscribir en la excursión: " + e.getMessage());
        }
    }

    //Opción 4: Listar excursiones por fecha

    public static void listarExcursionesPorFechaJPA(LocalDate fechaInicio, LocalDate fechaFin) {
        ExcursionDAOJPA excursionDAO = new ExcursionDAOJPA();
        try {
            excursionDAO.listarExcursionesPorFechaJPA(fechaInicio, fechaFin);
        } catch (Exception e) {
            System.err.println("Error al listar excursiones: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Opción 5: Listar inscripciones

    public static void listarInscripcionesJPA() {
        try {
            inscripcionDAOJPA.listarInscripcionesJPA();
        } catch (Exception e) {
            System.err.println("Error al listar inscripciones: " + e.getMessage());
        }
    }

    //Opción 7: Modificar datos socio

    public static void modificarDatosSocio(int numeroSocio, String nuevoNombre) {
        try {
            socioDAOJPA.modificarDatosSocioJPA(numeroSocio, nuevoNombre);
        } catch (Exception e) {
            System.err.println("Error al modificar los datos del socio: " + e.getMessage());
        }
    }

    //Opción 8: Mostrar socios por tipo

    private static final SocioDAOJPA socioDAOJPA = new SocioDAOJPA();

    public static void listarSociosPorTipoJPA(int tipoSocio) {
        try {
            socioDAOJPA.listarSociosPorTipoJPA(tipoSocio);
        } catch (Exception e) {
            System.err.println("Error al listar socios por tipo: " + e.getMessage());
        }
    }

    //Opcion 12: Eliminar excursion

    public static boolean eliminarExcursionJPA(String idExcursion) {
        try {
            new ExcursionDAOJPA().eliminarExcursionJPA(idExcursion);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}


