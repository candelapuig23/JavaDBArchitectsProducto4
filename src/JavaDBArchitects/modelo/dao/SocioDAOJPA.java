package JavaDBArchitects.modelo.dao;

import JavaDBArchitects.modelo.dao.entidades.FederacionEntidad;
import JavaDBArchitects.modelo.dao.entidades.SocioEntidad;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class SocioDAOJPA {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JavaDBArchitectsPU");

    //================Metodo para registrar socio===============

    public void registrarSocioJPA(String nombre, int tipoSocio, String nif, int idFederacion, Integer idSocioPadre, Object extra, String nombreFederacion) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            // Crear el objeto SocioEntidad
            SocioEntidad socio = new SocioEntidad();
            socio.setNombre(nombre);
            socio.setTipoSocio(obtenerTipoSocio(tipoSocio));
            socio.setNif(nif);

            // Si es un socio federado, buscar la federación y asignarla
            if (tipoSocio == 1) { // Federado
                FederacionEntidad federacion = entityManager.find(FederacionEntidad.class, idFederacion);
                if (federacion == null) {
                    // Si la federación no existe, la creamos
                    federacion = new FederacionEntidad();
                    federacion.setIdFederacion(idFederacion);
                    federacion.setNombre(nombreFederacion);
                    entityManager.persist(federacion);
                }
                socio.setFederacion(federacion);
            }

            // Si es un socio infantil, buscar al socio padre y asignarlo
            if (tipoSocio == 2) { // Infantil
                if (idSocioPadre != null) {
                    SocioEntidad socioPadre = entityManager.find(SocioEntidad.class, idSocioPadre);
                    if (socioPadre == null) {
                        throw new IllegalArgumentException("El número de socio padre proporcionado no existe.");
                    }
                    socio.setSocioPadre(socioPadre);
                } else {
                    throw new IllegalArgumentException("Para registrar un socio infantil, se debe proporcionar el número del socio padre.");
                }
            }

            // Persistir el socio en la base de datos
            entityManager.persist(socio);
            entityManager.getTransaction().commit();
            System.out.println("Socio registrado correctamente.");
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Error al registrar el socio: " + e.getMessage());
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    private String obtenerTipoSocio(int tipoSocio) {
        switch (tipoSocio) {
            case 0:
                return "Estandar";
            case 1:
                return "Federado";
            case 2:
                return "Infantil";
            default:
                throw new IllegalArgumentException("Tipo de socio no válido");
        }
    }

    //=============Metodo para modificar datos del socio==========

    public void modificarDatosSocioJPA(int numeroSocio, String nuevoNombre) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            // Iniciamos la transacción
            entityManager.getTransaction().begin();

            // Buscamos al socio por su número de socio
            SocioEntidad socio = entityManager.find(SocioEntidad.class, numeroSocio);

            if (socio == null) {
                System.out.println("Error: El socio con número " + numeroSocio + " no existe.");
                entityManager.getTransaction().rollback();
                return;
            }

            // Actualizamos el nombre del socio
            socio.setNombre(nuevoNombre);

            // Persistimos los cambios
            entityManager.merge(socio);

            // Confirmamos la transacción
            entityManager.getTransaction().commit();
            System.out.println("Datos del socio actualizados con éxito.");

        } catch (Exception e) {
            // Si ocurre un error, revertimos la transacción
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Error al modificar los datos del socio: " + e.getMessage());
        } finally {
            // Cerramos el EntityManager
            entityManager.close();
        }
    }

    //=============Metodo para mostrar socios por tipo============

    public List<SocioEntidad> listarSociosPorTipoJPA(int tipoSocio) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<SocioEntidad> socios = null;

        try {
            // Convertimos el valor numérico del tipo de socio a su correspondiente String
            String tipo = tipoSocio == 0 ? "ESTANDAR" : tipoSocio == 1 ? "FEDERADO" : "INFANTIL";

            // Iniciamos una transacción
            entityManager.getTransaction().begin();

            // Consulta JPA para obtener los socios por tipo
            socios = entityManager.createQuery("SELECT s FROM SocioEntidad s WHERE s.tipoSocio = :tipo", SocioEntidad.class)
                    .setParameter("tipo", tipo)
                    .getResultList();

            // Mostrar los resultados
            if (socios != null && !socios.isEmpty()) {
                for (SocioEntidad socio : socios) {
                    System.out.println("ID Socio: " + socio.getNumeroSocio());
                    System.out.println("Nombre: " + socio.getNombre());
                    System.out.println("Tipo de Socio: " + socio.getTipoSocio());
                    System.out.println("NIF: " + socio.getNif());
                    if (socio.getFederacion() != null) {
                        System.out.println("Federación: " + socio.getFederacion().getNombre());
                    }
                    if (socio.getSocioPadre() != null) {
                        System.out.println("ID Socio Padre: " + socio.getSocioPadre().getNumeroSocio());
                    }
                    System.out.println("------------------------------------");
                }
            } else {
                System.out.println("No se encontraron socios de tipo: " + tipo);
            }

            // Confirmar la transacción si todo está bien
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // Manejo de errores
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                System.err.println("Error al listar socios por tipo: " + e.getMessage());
            }
        } finally {
            entityManager.close();
        }

        return socios;
    }

    //===========Metodo para eliminar socio============

    public boolean eliminarSocioJPA(int idSocio) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        boolean eliminado = false;

        try {
            // Iniciar la transacción
            entityManager.getTransaction().begin();

            // Buscar al socio por su ID
            SocioEntidad socio = entityManager.find(SocioEntidad.class, idSocio);

            if (socio == null) {
                System.out.println("Error: El socio con número " + idSocio + " no existe.");
                entityManager.getTransaction().rollback();
            } else {
                // Eliminar el socio
                entityManager.remove(socio);

                // Confirmar la transacción
                entityManager.getTransaction().commit();
                eliminado = true;
                System.out.println("Socio eliminado correctamente.");
            }
        } catch (Exception e) {
            // En caso de error, revertir la transacción
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Error al eliminar el socio: " + e.getMessage());
        } finally {
            // Cerrar el EntityManager
            entityManager.close();
        }

        return eliminado;
    }

}