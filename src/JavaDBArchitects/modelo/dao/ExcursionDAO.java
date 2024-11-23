package JavaDBArchitects.modelo.dao;

import JavaDBArchitects.modelo.Excursion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class ExcursionDAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("JavaDBArchitectsPU");

    // Método para registrar una excursión
    public void registrarExcursionPA(String codigo, String descripcion, LocalDate fecha, int numeroDias, float precio) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Crear una nueva instancia de Excursion y configurar sus propiedades
            Excursion excursion = new Excursion();
            excursion.setCodigo(codigo);
            excursion.setDescripcion(descripcion);
            excursion.setFecha(fecha);
            excursion.setNumeroDias(numeroDias);
            excursion.setPrecio(precio);

            // Persistir la entidad
            em.persist(excursion);
            em.getTransaction().commit();
            System.out.println("Excursión registrada correctamente.");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Método para eliminar una excursión por su ID
    public boolean eliminarExcursionPA(String idExcursion) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Excursion excursion = em.find(Excursion.class, idExcursion);
            if (excursion != null) {
                em.remove(excursion);
                em.getTransaction().commit();
                System.out.println("Excursión eliminada correctamente.");
                return true;
            } else {
                System.out.println("No se encontró ninguna excursión con el ID proporcionado.");
                return false;
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    // Método para listar excursiones por rango de fechas
    public List<Excursion> listarExcursionesPorFechaPA(LocalDate fechaInicio, LocalDate fechaFin) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT e FROM Excursion e WHERE e.fecha BETWEEN :fechaInicio AND :fechaFin";
            TypedQuery<Excursion> query = em.createQuery(jpql, Excursion.class);
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Método para obtener todas las excursiones
    public List<Excursion> getAllExcursiones() {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT e FROM Excursion e";
            TypedQuery<Excursion> query = em.createQuery(jpql, Excursion.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Método para buscar una excursión por su ID
    public Excursion getExcursionById(String idExcursion) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Excursion.class, idExcursion);
        } finally {
            em.close();
        }
    }

    // Método para verificar si una excursión tiene inscripciones activas
    public boolean tieneInscripcionesActivas(String idExcursion) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT COUNT(i) FROM Inscripcion i WHERE i.excursion.idExcursion = :idExcursion";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("idExcursion", idExcursion);
            Long count = query.getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    // Método para verificar si una excursión existe según su código
    public boolean excursionExiste(String codigo) {
        EntityManager em = emf.createEntityManager(); // Inicializamos el EntityManager aquí
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(e) FROM Excursion e WHERE e.codigo = :codigo", Long.class);
            query.setParameter("codigo", codigo);
            return query.getSingleResult() > 0;
        } finally {
            em.close(); // Cerramos el EntityManager después de usarlo
        }
    }

    // Método para actualizar una excursión
    public boolean actualizarExcursion(Excursion excursion) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(excursion); // Actualizar la entidad
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
}







