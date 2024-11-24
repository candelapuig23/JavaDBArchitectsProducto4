package JavaDBArchitects.modelo.dao;

import JavaDBArchitects.modelo.dao.entidades.ExcursionEntidad;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class ExcursionDAOJPA {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JavaDBArchitectsPU");

    // Método para registrar una nueva excursión
    public void registrarExcursionJPA(String idExcursion, String descripcion, LocalDate fecha, int numDias, float precioInscripcion) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            ExcursionEntidad excursion = new ExcursionEntidad();
            excursion.setIdExcursion(idExcursion);
            excursion.setDescripcion(descripcion);
            excursion.setFecha(java.sql.Date.valueOf(fecha));
            excursion.setNumDias(numDias);
            excursion.setPrecio(precioInscripcion);

            entityManager.persist(excursion);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    // Método para obtener todas las excursiones
    public List<ExcursionEntidad> obtenerTodasLasExcursionesJPA() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<ExcursionEntidad> excursiones = null;
        try {
            excursiones = entityManager.createQuery("SELECT e FROM ExcursionEntidad e", ExcursionEntidad.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return excursiones;
    }

    // Método para buscar una excursión por su ID
    public ExcursionEntidad buscarExcursionPorIdJPA(String idExcursion) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ExcursionEntidad excursion = null;
        try {
            excursion = entityManager.find(ExcursionEntidad.class, idExcursion);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return excursion;
    }

    // Método para eliminar una excursión por su ID
    public static void eliminarExcursionJPA(String idExcursion) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            ExcursionEntidad excursion = entityManager.find(ExcursionEntidad.class, idExcursion);
            if (excursion != null) {
                entityManager.remove(excursion);
                entityManager.getTransaction().commit();
            } else {
                System.out.println("La excursión con ID " + idExcursion + " no existe.");
            }
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
}

