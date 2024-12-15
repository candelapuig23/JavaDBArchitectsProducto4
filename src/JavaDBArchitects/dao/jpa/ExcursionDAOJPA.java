package JavaDBArchitects.dao.jpa;

import JavaDBArchitects.dao.entidades.ExcursionEntidad;
import JavaDBArchitects.dao.entidades.InscripcionEntidad;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class ExcursionDAOJPA {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JavaDBArchitectsPU");

    // ==============Metodo para registrar una excursion =============

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

    //=============Metodo para listar las excursiones por fecha===========

    public void listarExcursionesPorFechaJPA(LocalDate fechaInicio, LocalDate fechaFin) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            // Comenzar la transacción
            entityManager.getTransaction().begin();

            // JPQL para seleccionar excursiones entre dos fechas
            TypedQuery<ExcursionEntidad> query = entityManager.createQuery(
                    "SELECT e FROM ExcursionEntidad e WHERE e.fecha BETWEEN :fechaInicio AND :fechaFin",
                    ExcursionEntidad.class
            );
            query.setParameter("fechaInicio", Date.valueOf(fechaInicio));
            query.setParameter("fechaFin", Date.valueOf(fechaFin));

            // Ejecutar la consulta y obtener la lista de resultados
            List<ExcursionEntidad> excursiones = query.getResultList();

            // Procesar el resultado
            if (excursiones.isEmpty()) {
                System.out.println("No se encontraron excursiones en el rango de fechas proporcionado.");
            } else {
                for (ExcursionEntidad excursion : excursiones) {
                    System.out.println("ID Excursión: " + excursion.getIdExcursion());
                    System.out.println("Descripción: " + excursion.getDescripcion());
                    System.out.println("Fecha: " + excursion.getFecha());
                    System.out.println("Número de Días: " + excursion.getNumDias());
                    System.out.println("Precio: $" + excursion.getPrecio());
                    System.out.println("------------------------------------");
                }
            }

            // Cometer la transacción
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Error al listar excursiones: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    //==============Metodo para eliminar una excursion =============

    public boolean eliminarExcursionJPA(String idExcursion) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        boolean eliminado = false; // Variable para rastrear el éxito de la operación

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
                eliminado = true; // Marcar como éxito si la excursión se elimina
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

        return eliminado; // Devolver true si se eliminó correctamente, false si no
    }
}

