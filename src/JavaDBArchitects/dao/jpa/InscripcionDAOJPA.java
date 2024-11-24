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

    //================Metodo para listar inscripciones==============

    public void listarInscripcionesJPA() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            // Consulta JPA para obtener todas las inscripciones
            List<InscripcionEntidad> inscripciones = entityManager.createQuery("SELECT i FROM InscripcionEntidad i", InscripcionEntidad.class).getResultList();

            // Mostrar el resultado
            for (InscripcionEntidad inscripcion : inscripciones) {
                System.out.println("ID Inscripción: " + inscripcion.getIdInscripcion());
                System.out.println("ID Socio: " + inscripcion.getSocio().getNumeroSocio() + " - Nombre Socio: " + inscripcion.getSocio().getNombre());
                System.out.println("ID Excursión: " + inscripcion.getExcursion().getIdExcursion() + " - Descripción Excursión: " + inscripcion.getExcursion().getDescripcion());
                System.out.println("Fecha Inscripción: " + inscripcion.getFechaInscripcion());
                System.out.println("------------------------------------");
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                System.err.println("Error al listar inscripciones: " + e.getMessage());
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
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
}