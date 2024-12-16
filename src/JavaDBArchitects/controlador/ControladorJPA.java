package JavaDBArchitects.controlador;

import JavaDBArchitects.dao.jpa.ExcursionDAOJPA;
import JavaDBArchitects.dao.jpa.InscripcionDAOJPA;
import JavaDBArchitects.dao.jpa.SocioDAOJPA;
import JavaDBArchitects.dao.entidades.SocioEntidad;
import JavaDBArchitects.dao.entidades.InscripcionEntidad;
import JavaDBArchitects.dao.entidades.ExcursionEntidad;

import java.math.BigDecimal;
import java.util.List;
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

    //Opción 4: Listar excursiones por fecha modificado apara javafx

    public static List<ExcursionEntidad> listarExcursionesPorFechaJPA(LocalDate fechaInicio, LocalDate fechaFin) {
        ExcursionDAOJPA excursionDAO = new ExcursionDAOJPA();
        try {
            return excursionDAO.listarExcursionesPorFechaJPA(fechaInicio, fechaFin); // Retorna la lista de excursiones
        } catch (Exception e) {
            System.err.println("Error al listar excursiones: " + e.getMessage());
            e.printStackTrace();
            return List.of(); // Devuelve una lista vacía en caso de error
        }
    }

    //Opción 5: Listar inscripciones modificado para favafx

    public static List<InscripcionEntidad> listarInscripcionesJPA() {
        InscripcionDAOJPA inscripcionDAO = new InscripcionDAOJPA();
        try {
            return inscripcionDAO.listarInscripcionesJPA(); // Retorna la lista obtenida del DAO
        } catch (Exception e) {
            System.err.println("Error al listar inscripciones: " + e.getMessage());
            e.printStackTrace();
            return List.of(); // Retorna una lista vacía en caso de error
        }
    }



    public static void consultarFacturaMensualJPA(int numeroSocio) {
        SocioDAOJPA socioDAO = new SocioDAOJPA();
        InscripcionDAOJPA inscripcionDAO = new InscripcionDAOJPA();

        try {
            // Obtener el socio
            SocioEntidad socio = socioDAO.obtenerSocioPorNumero(numeroSocio); // Método de SocioDAOJPA
            if (socio == null) {
                System.out.println("Error: El socio no existe.");
                return;
            }

            // Obtener la cuota mensual directamente desde el socio
            BigDecimal cuotaMensual = socio.getCuotaMensual();
            BigDecimal totalFactura = cuotaMensual != null ? cuotaMensual : BigDecimal.ZERO;

            // Obtener las inscripciones asociadas al socio
            List<InscripcionEntidad> inscripciones = inscripcionDAO.getInscripcionesBySocio(numeroSocio);

            // Calcular el total de la factura basado en las inscripciones
            for (InscripcionEntidad inscripcion : inscripciones) {
                ExcursionEntidad excursion = inscripcion.getExcursion();
                BigDecimal precioExcursion = BigDecimal.valueOf(excursion.getPrecio());
                if ("FEDERADO".equalsIgnoreCase(socio.getTipoSocio())) {
                    totalFactura = totalFactura.add(precioExcursion.multiply(BigDecimal.valueOf(0.9))); // Descuento del 10% para federados
                } else {
                    totalFactura = totalFactura.add(precioExcursion); // Precio completo para otros tipos de socios
                }
            }

            // Mostrar el total de la factura mensual
            System.out.println("Factura mensual para el socio " + numeroSocio + ": " + totalFactura + "€");
        } catch (Exception e) {
            System.err.println("Error al calcular la factura mensual: " + e.getMessage());
            e.printStackTrace();
        }
    }


    //Opción 7: Modificar datos socio

    public static void modificarDatosSocioJPA(int numeroSocio, String nuevoNombre, String nuevoNIF, String nuevoTipoSocio, Integer nuevaFederacionId) {
        try {
            socioDAOJPA.modificarDatosSocioJPA(numeroSocio, nuevoNombre, nuevoNIF, nuevoTipoSocio, nuevaFederacionId);
        } catch (Exception e) {
            System.err.println("Error al modificar los datos del socio: " + e.getMessage());
        }
    }



    //Opción 8: Mostrar socios por tipo

    private static final SocioDAOJPA socioDAOJPA = new SocioDAOJPA();

    public static List<SocioEntidad> listarSociosPorTipoJPA(int tipoSocio) {
        try {
            // Llamar al DAO y devolver la lista de socios
            return socioDAOJPA.listarSociosPorTipoJPA(tipoSocio);
        } catch (Exception e) {
            System.err.println("Error al listar socios por tipo: " + e.getMessage());
            return null; // En caso de error, devuelve null
        }
    }

    //Opción 9: Eliminar inscripción

    public static boolean eliminarInscripcion(int idInscripcion) {
        boolean eliminado = false;
        try {
            eliminado = inscripcionDAOJPA.eliminarInscripcionJPA(idInscripcion);
        } catch (Exception e) {
            System.err.println("Error al eliminar la inscripción: " + e.getMessage());
        }
        return eliminado;
    }

    //Opción 10: Eliminar Socio

    public static boolean eliminarSocio(int idSocio) {
        boolean eliminado = false;
        try {
            eliminado = socioDAOJPA.eliminarSocioJPA(idSocio);
        } catch (Exception e) {
            System.err.println("Error al eliminar el socio: " + e.getMessage());
        }
        return eliminado;
    }

    //Opcion 11: Mostrar inscripciones con filtros

    public static List<InscripcionEntidad> mostrarInscripcionesConFiltrosJPA(Integer numeroSocio, LocalDate fechaInicio, LocalDate fechaFin) {
        InscripcionDAOJPA inscripcionDAO = new InscripcionDAOJPA();
        return inscripcionDAO.getInscripcionesConFiltros(numeroSocio, fechaInicio, fechaFin);
    }


    //Opcion 12: Eliminar excursion

    public static boolean eliminarExcursionJPA(String idExcursion) {
        try {
            ExcursionDAOJPA dao = new ExcursionDAOJPA();
            boolean eliminado = dao.eliminarExcursionJPA(idExcursion); // Llamada al DAO

            if (eliminado) {
                return true; // Eliminación exitosa
            } else {
                System.err.println("La excursión con el ID proporcionado no existe.");
                return false; // No se encontró el ID
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar la excursión: " + e.getMessage());
            return false; // Error inesperado
        }
    }
}


