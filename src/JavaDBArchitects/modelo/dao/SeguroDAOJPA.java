package JavaDBArchitects.modelo.dao;

import JavaDBArchitects.modelo.dao.entidades.SegurosEntidad;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class SeguroDAOJPA {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JavaDBArchitectsPU");

    // Método para registrar un nuevo seguro
    public void registrarSeguro(SegurosEntidad seguro) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(seguro);
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

    // Método para obtener todos los seguros
    public List<SegurosEntidad> obtenerTodosLosSeguros() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<SegurosEntidad> seguros = null;
        try {
            seguros = entityManager.createQuery("SELECT s FROM SeguroEntidad s", SegurosEntidad.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return seguros;
    }

    // Método para buscar un seguro por su ID
    public SegurosEntidad buscarSeguroPorId(int idSeguro) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        SegurosEntidad seguro = null;
        try {
            seguro = entityManager.find(SegurosEntidad.class, idSeguro);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return seguro;
    }

    // Método para eliminar un seguro por su ID
    public void eliminarSeguro(int idSeguro) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            SegurosEntidad seguro = entityManager.find(SegurosEntidad.class, idSeguro);
            if (seguro != null) {
                entityManager.remove(seguro);
                entityManager.getTransaction().commit();
            } else {
                System.out.println("El seguro con ID " + idSeguro + " no existe.");
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
