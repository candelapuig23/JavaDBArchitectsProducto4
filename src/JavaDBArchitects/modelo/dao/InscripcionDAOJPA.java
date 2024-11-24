package JavaDBArchitects.modelo.dao;

import JavaDBArchitects.modelo.dao.entidades.InscripcionEntidad;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class InscripcionDAOJPA {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JavaDBArchitectsPU");

    // Método para registrar una nueva inscripción
    public void registrarInscripcion(InscripcionEntidad inscripcion) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(inscripcion);
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

    // Método para obtener todas las inscripciones
    public List<InscripcionEntidad> obtenerTodasLasInscripciones() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<InscripcionEntidad> inscripciones = null;
        try {
            inscripciones = entityManager.createQuery("SELECT i FROM InscripcionEntidad i", InscripcionEntidad.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return inscripciones;
    }

    // Método para buscar inscripciones por ID de excursión
    public List<InscripcionEntidad> obtenerInscripcionesPorExcursion(String idExcursion) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<InscripcionEntidad> inscripciones = null;
        try {
            inscripciones = entityManager.createQuery("SELECT i FROM InscripcionEntidad i WHERE i.idExcursion = :idExcursion", InscripcionEntidad.class)
                    .setParameter("idExcursion", idExcursion)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return inscripciones;
    }

    // Método para eliminar una inscripción por su ID
    public void eliminarInscripcion(int idInscripcion) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            InscripcionEntidad inscripcion = entityManager.find(InscripcionEntidad.class, idInscripcion);
            if (inscripcion != null) {
                entityManager.remove(inscripcion);
                entityManager.getTransaction().commit();
            } else {
                System.out.println("La inscripción con ID " + idInscripcion + " no existe.");
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