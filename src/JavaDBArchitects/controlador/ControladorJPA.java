package JavaDBArchitects.controlador;

import JavaDBArchitects.modelo.dao.ExcursionDAOJPA;

public class ControladorJPA {


    public static boolean eliminarExcursionJPA(String idExcursion) {
        try {
            ExcursionDAOJPA.eliminarExcursionJPA(idExcursion);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
