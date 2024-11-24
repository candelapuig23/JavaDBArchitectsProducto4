package JavaDBArchitects.controlador;

import JavaDBArchitects.modelo.dao.ExcursionDAOJPA;

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


