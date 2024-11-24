package JavaDBArchitects.modelo.dao;

import JavaDBArchitects.modelo.dao.entidades.FederacionEntidad;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class FederacionDAOJPA {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JavaDBArchitectsPU");

    // Método para registrar una nueva federación
    public void registrarFederacion(FederacionEntidad federacion) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(federacion);
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

    // Método para obtener todas las federaciones
    public List<FederacionEntidad> obtenerTodasLasFederaciones() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<FederacionEntidad> federaciones = null;
        try {
            federaciones = entityManager.createQuery("SELECT f FROM FederacionEntidad f", FederacionEntidad.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return federaciones;
    }

    // Método para buscar una federación por su ID
    public FederacionEntidad buscarFederacionPorId(int idFederacion) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        FederacionEntidad federacion = null;
        try {
            federacion = entityManager.find(FederacionEntidad.class, idFederacion);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return federacion;
    }

    // Método para eliminar una federación por su ID
    public void eliminarFederacion(int idFederacion) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            FederacionEntidad federacion = entityManager.find(FederacionEntidad.class, idFederacion);
            if (federacion != null) {
                entityManager.remove(federacion);
                entityManager.getTransaction().commit();
            } else {
                System.out.println("La federación con ID " + idFederacion + " no existe.");
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

