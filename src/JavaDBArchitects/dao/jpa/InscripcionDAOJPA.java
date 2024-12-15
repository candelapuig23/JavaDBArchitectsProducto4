package JavaDBArchitects.dao.jpa;

import JavaDBArchitects.dao.entidades.ExcursionEntidad;
import JavaDBArchitects.dao.entidades.InscripcionEntidad;
import JavaDBArchitects.dao.entidades.SocioEntidad;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;

public class InscripcionDAOJPA {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JavaDBArchitectsPU");

    //===============Metodo para inscribir en excursion===========

    public void inscribirEnExcursionJPA(int idSocio, String idExcursion, LocalDate fechaInscripcion) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            // Buscar el socio utilizando su ID
            SocioEntidad socio = entityManager.find(SocioEntidad.class, idSocio);
            if (socio == null) {
                throw new IllegalArgumentException("No se encontró el socio con el ID: " + idSocio);
            }

            // Buscar la excursión utilizando su ID
            ExcursionEntidad excursion = entityManager.find(ExcursionEntidad.class, idExcursion);
            if (excursion == null) {
                throw new IllegalArgumentException("No se encontró la excursión con el ID: " + idExcursion);
            }

            // Crear la nueva inscripción
            InscripcionEntidad inscripcion = new InscripcionEntidad();
            inscripcion.setSocio(socio);
            inscripcion.setExcursion(excursion);
            inscripcion.setFechaInscripcion(java.sql.Date.valueOf(fechaInscripcion));

            // Persistir la inscripción
            entityManager.persist(inscripcion);
            entityManager.getTransaction().commit();

            System.out.println("Inscripción registrada correctamente.");

        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                System.err.println("La transacción se ha revertido debido a un error: " + e.getMessage());
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    //================Metodo para listar inscripciones modificado para JAvaFX==============

    public List<InscripcionEntidad> listarInscripcionesJPA() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<InscripcionEntidad> inscripciones = null;

        try {
            // Consulta JPA para obtener todas las inscripciones
            inscripciones = entityManager.createQuery(
                    "SELECT i FROM InscripcionEntidad i", InscripcionEntidad.class
            ).getResultList();

        } catch (Exception e) {
            System.err.println("Error al listar inscripciones: " + e.getMessage());
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return inscripciones; // Devuelve la lista de inscripciones
    }


    //============Metodo para eliminar inscripcion===========

    public boolean eliminarInscripcionJPA(int idInscripcion) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        boolean eliminado = false;

        try {
            // Iniciar la transacción
            entityManager.getTransaction().begin();

            // Buscar la inscripción por su ID
            InscripcionEntidad inscripcion = entityManager.find(InscripcionEntidad.class, idInscripcion);

            if (inscripcion == null) {
                System.out.println("Error: La inscripción con ID " + idInscripcion + " no existe.");
                entityManager.getTransaction().rollback();
            } else {
                // Eliminar la inscripción
                entityManager.remove(inscripcion);

                // Confirmar la transacción
                entityManager.getTransaction().commit();
                eliminado = true;
                System.out.println("Inscripción eliminada con éxito.");
            }
        } catch (Exception e) {
            // En caso de error, revertir la transacción
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Error al eliminar la inscripción: " + e.getMessage());
        } finally {
            // Cerrar el EntityManager
            entityManager.close();
        }

        return eliminado;
    }

    //============ metodo para obtener inscripciones por socio =========
    public List<InscripcionEntidad> getInscripcionesBySocio(int numeroSocio) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<InscripcionEntidad> inscripciones = null;

        try {
            inscripciones = entityManager.createQuery(
                    "SELECT i FROM InscripcionEntidad i WHERE i.socio.numeroSocio = :numeroSocio",
                    InscripcionEntidad.class
            ).setParameter("numeroSocio", numeroSocio).getResultList();
        } catch (Exception e) {
            System.err.println("Error al obtener las inscripciones: " + e.getMessage());
        } finally {
            entityManager.close();
        }

        return inscripciones;
    }

//============ metodo con filtros dinámicos para filtrar las inscripciones por numero de socio y rango de fechas =====

    public List<InscripcionEntidad> getInscripcionesConFiltros(Integer numeroSocio, LocalDate fechaInicio, LocalDate fechaFin) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<InscripcionEntidad> inscripciones = null;

        try {
            // Construcción dinámica de la consulta JPQL
            StringBuilder jpql = new StringBuilder("SELECT i FROM InscripcionEntidad i WHERE 1=1");

            // Filtro por número de socio (opcional)
            if (numeroSocio != null) {
                jpql.append(" AND i.socio.numeroSocio = :numeroSocio");
            }

            // Filtro por rango de fechas en fecha_inscripcion (opcional)
            if (fechaInicio != null && fechaFin != null) {
                jpql.append(" AND i.fechaInscripcion BETWEEN :fechaInicio AND :fechaFin");
            }

            // Crear consulta
            var query = entityManager.createQuery(jpql.toString(), InscripcionEntidad.class);

            // Configurar parámetros dinámicos
            if (numeroSocio != null) {
                query.setParameter("numeroSocio", numeroSocio);
            }
            if (fechaInicio != null && fechaFin != null) {
                query.setParameter("fechaInicio", java.sql.Date.valueOf(fechaInicio));
                query.setParameter("fechaFin", java.sql.Date.valueOf(fechaFin));
            }

            // Ejecutar consulta
            inscripciones = query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al filtrar inscripciones: " + e.getMessage());
        } finally {
            entityManager.close();
        }

        return inscripciones;
    }



}