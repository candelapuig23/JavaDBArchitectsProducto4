package JavaDBArchitects.modelo.dao.JDBC;

import JavaDBArchitects.modelo.Federacion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class FederacionDAO {

    private EntityManagerFactory emf;

    // Constructor para inicializar el EntityManagerFactory
    public FederacionDAO() {
        this.emf = Persistence.createEntityManagerFactory("JavaDBArchitectsPU");
    }

    // Método para agregar una federación a la base de datos
    public void addFederacion(Federacion federacion) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(federacion); // Guardar la federación en la base de datos
            em.getTransaction().commit();
            System.out.println("Federación añadida: " + federacion);
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Método para obtener una federación por su ID
    public Federacion getFederacionById(int idFederacion) {
        EntityManager em = emf.createEntityManager();
        Federacion federacion = null;
        try {
            federacion = em.find(Federacion.class, idFederacion); // Buscar la federación por su ID
            if (federacion != null) {
                System.out.println("Federación obtenida: " + federacion);
            } else {
                System.out.println("No se encontró ninguna federación con ID: " + idFederacion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return federacion;
    }

    // Método para obtener todas las federaciones
    public List<Federacion> getAllFederaciones() {
        EntityManager em = emf.createEntityManager();
        List<Federacion> federaciones = null;
        try {
            TypedQuery<Federacion> query = em.createQuery("SELECT f FROM Federacion f", Federacion.class);
            federaciones = query.getResultList();
            System.out.println("Federaciones obtenidas: " + federaciones);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return federaciones;
    }

    // Método para actualizar una federación en la base de datos
    public void updateFederacion(Federacion federacion) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(federacion); // Actualizar la federación
            em.getTransaction().commit();
            System.out.println("Federación actualizada: " + federacion);
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Método para eliminar una federación por su ID
    public void deleteFederacion(int idFederacion) {
        EntityManager em = emf.createEntityManager();
        try {
            Federacion federacion = em.find(Federacion.class, idFederacion);
            if (federacion != null) {
                em.getTransaction().begin();
                em.remove(federacion); // Eliminar la federación
                em.getTransaction().commit();
                System.out.println("Federación eliminada con éxito con ID: " + idFederacion);
            } else {
                System.out.println("No se encontró ninguna federación con ID: " + idFederacion);
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Método para limpiar todas las federaciones
    public void clearFederaciones() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Federacion").executeUpdate();
            em.getTransaction().commit();
            System.out.println("Todas las federaciones han sido eliminadas.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}

