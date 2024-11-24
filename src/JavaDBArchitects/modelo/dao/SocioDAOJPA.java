package JavaDBArchitects.modelo.dao;

import JavaDBArchitects.modelo.dao.entidades.SocioEntidad;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class SocioDAOJPA {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JavaDBArchitectsPU");

    // Método para registrar un nuevo socio
    public void registrarSocio(SocioEntidad socio) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(socio);
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

    // Método para obtener todos los socios
    public List<SocioEntidad> obtenerTodosLosSocios() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<SocioEntidad> socios = null;
        try {
            socios = entityManager.createQuery("SELECT s FROM SocioEntidad s", SocioEntidad.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return socios;
    }

    // Método para buscar un socio por su ID
    public SocioEntidad buscarSocioPorId(int idSocio) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        SocioEntidad socio = null;
        try {
            socio = entityManager.find(SocioEntidad.class, idSocio);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return socio;
    }

    // Método para eliminar un socio por su ID
    public void eliminarSocio(int idSocio) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            SocioEntidad socio = entityManager.find(SocioEntidad.class, idSocio);
            if (socio != null) {
                entityManager.remove(socio);
                entityManager.getTransaction().commit();
            } else {
                System.out.println("El socio con ID " + idSocio + " no existe.");
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