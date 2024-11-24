package JavaDBArchitects.modelo.dao;

import JavaDBArchitects.modelo.dao.entidades.ExcursionEntidad;
import JavaDBArchitects.modelo.dao.entidades.InscripcionEntidad;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class ExcursionDAOJPA {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JavaDBArchitectsPU");

    // Método para registrar una nueva excursión
    public void registrarExcursionJPA(String codigo, String descripcion, LocalDate fecha, int numDias, float precioInscripcion) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            // Crear y configurar la excursión
            ExcursionEntidad excursion = new ExcursionEntidad();
            excursion.setIdExcursion(codigo); // Usar 'codigo' también como 'idExcursion' para simplificar (puedes cambiar esto si lo deseas)
            excursion.setCodigo(codigo); // Asignar el código proporcionado por el usuario
            excursion.setDescripcion(descripcion);
            excursion.setFecha(java.sql.Date.valueOf(fecha));
            excursion.setNumDias(numDias); // Asegúrate de que se asigna 'numDias'
            excursion.setPrecio(precioInscripcion);

            // Persistir la entidad
            entityManager.persist(excursion);
            entityManager.getTransaction().commit();
            System.out.println("Excursión registrada con éxito.");
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
    public void eliminarExcursionJPA(String idExcursion) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            // 1. Eliminar las inscripciones relacionadas con la excursión
            List<InscripcionEntidad> inscripciones = entityManager.createQuery(
                            "SELECT i FROM InscripcionEntidad i WHERE i.excursion.idExcursion = :idExcursion", InscripcionEntidad.class)
                    .setParameter("idExcursion", idExcursion)
                    .getResultList();

            for (InscripcionEntidad inscripcion : inscripciones) {
                entityManager.remove(inscripcion);
            }

            // 2. Eliminar la excursión
            ExcursionEntidad excursion = entityManager.find(ExcursionEntidad.class, idExcursion);
            if (excursion != null) {
                entityManager.remove(excursion);
            } else {
                System.out.println("La excursión con ID " + idExcursion + " no existe.");
            }

            // 3. Commit de la transacción
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
}

