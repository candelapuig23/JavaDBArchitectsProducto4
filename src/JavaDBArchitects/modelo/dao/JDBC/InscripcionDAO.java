package JavaDBArchitects.modelo.dao.JDBC;

import JavaDBArchitects.modelo.Inscripcion;
import JavaDBArchitects.modelo.Socio;
import JavaDBArchitects.modelo.Excursion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class InscripcionDAO {

    private EntityManagerFactory emf;

    // Constructor para inicializar el EntityManagerFactory
    public InscripcionDAO() {
        this.emf = Persistence.createEntityManagerFactory("JavaDBArchitectsPU");
    }

    // Método para inscribir a un socio en una excursión
    public void inscribirEnExcursionPA(int numeroSocio, String codigoExcursion, LocalDate fechaInscripcion) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Buscar el socio y la excursión en la base de datos
            Socio socio = em.find(Socio.class, numeroSocio);
            Excursion excursion = em.find(Excursion.class, codigoExcursion);

            if (socio == null) {
                throw new IllegalArgumentException("El socio con número " + numeroSocio + " no existe.");
            }
            if (excursion == null) {
                throw new IllegalArgumentException("La excursión con código " + codigoExcursion + " no existe.");
            }

            // Crear la inscripción
            Inscripcion inscripcion = new Inscripcion();
            inscripcion.setSocio(socio);
            inscripcion.setExcursion(excursion);
            inscripcion.setFechaInscripcion(fechaInscripcion);

            // Persistir la inscripción
            em.persist(inscripcion);
            em.getTransaction().commit();

            System.out.println("Inscripción registrada correctamente.");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }





    // Método para eliminar una inscripción por ID
    public boolean eliminarInscripcionPA(int idInscripcion) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Inscripcion inscripcion = em.find(Inscripcion.class, idInscripcion);

            if (inscripcion != null) {
                em.remove(inscripcion);
                em.getTransaction().commit();
                System.out.println("Inscripción eliminada correctamente.");
                return true;
            } else {
                System.out.println("Inscripción no encontrada.");
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error al eliminar la inscripción: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    // Método para listar todas las inscripciones
    public List<Inscripcion> listarInscripcionesPA() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Inscripcion> query = em.createQuery("SELECT i FROM Inscripcion i", Inscripcion.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public boolean inscripcionExiste(int numeroSocio, String codigoExcursion) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT COUNT(i) FROM Inscripcion i WHERE i.socio.numeroSocio = :numeroSocio AND i.excursion.codigo = :codigoExcursion";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("numeroSocio", numeroSocio);
            query.setParameter("codigoExcursion", codigoExcursion);
            Long count = query.getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }



    // Método para obtener inscripciones de un socio específico
    public List<Inscripcion> getInscripcionesBySocio(Socio socio) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Inscripcion> query = em.createQuery("SELECT i FROM Inscripcion i WHERE i.socio = :socio", Inscripcion.class);
            query.setParameter("socio", socio);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Método para obtener una inscripción por su ID
    public Inscripcion getInscripcionById(String idInscripcion) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Inscripcion.class, idInscripcion);
        } finally {
            em.close();
        }
    }

    // Método para verificar si un socio tiene inscripciones
    public boolean socioTieneInscripciones(Socio socio) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(i) FROM Inscripcion i WHERE i.socio = :socio", Long.class);
            query.setParameter("socio", socio);
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }

    // Método para verificar si una excursión tiene inscripciones
    public boolean excursionTieneInscripciones(Excursion excursion) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(i) FROM Inscripcion i WHERE i.excursion = :excursion", Long.class);
            query.setParameter("excursion", excursion);
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }

    public List<Inscripcion> getInscripcionesBySocio(int numeroSocio) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT i FROM Inscripcion i WHERE i.socio.numeroSocio = :numeroSocio";
            TypedQuery<Inscripcion> query = em.createQuery(jpql, Inscripcion.class);
            query.setParameter("numeroSocio", numeroSocio);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Inscripcion> getAllInscripciones() {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT i FROM Inscripcion i";
            TypedQuery<Inscripcion> query = em.createQuery(jpql, Inscripcion.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Método para cerrar el EntityManagerFactory
    public void cerrarFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}


