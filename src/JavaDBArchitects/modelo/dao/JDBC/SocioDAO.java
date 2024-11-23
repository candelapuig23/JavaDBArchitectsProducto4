package JavaDBArchitects.modelo.dao.JDBC;

import JavaDBArchitects.modelo.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.math.BigDecimal;
import java.util.List;

public class SocioDAO {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JavaDBArchitectsPU");
    private EntityManager entityManager;

    // Constructor sin argumentos
    public SocioDAO() {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    // Constructor que recibe un EntityManager
    public SocioDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Registrar un socio mediante JPA
    public void registrarSocioPA(String nombre, int tipoSocio, String nif, int idFederacion, Integer idSocioPadre, Object extra, String nombreFederacion) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            Socio socio;
            if (tipoSocio == 0) { // ESTANDAR
                TipoSeguro tipoSeguro = (TipoSeguro) extra;
                socio = new Estandar(nombre, nif, tipoSeguro, new BigDecimal(50)); // Ejemplo con seguro básico
            } else if (tipoSocio == 1) { // FEDERADO
                Federacion federacion = entityManager.find(Federacion.class, idFederacion);
                if (federacion == null) {
                    throw new IllegalArgumentException("Federación no encontrada.");
                }
                socio = new Federado(nombre, nif, federacion, new BigDecimal(75)); // Ejemplo con precio fijo
            } else { // INFANTIL
                socio = new Infantil(nombre, idSocioPadre, new BigDecimal(30)); // Ejemplo con precio fijo
            }
            entityManager.persist(socio);
            transaction.commit();
            System.out.println("Socio registrado correctamente.");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error al registrar socio: " + e.getMessage());
        }
    }

    // Listar socios por tipo (debe ser un String: "ESTANDAR", "FEDERADO", "INFANTIL")
    public List<Socio> listarSociosPorTipoPA(String tipoSocio) {
        EntityTransaction transaction = entityManager.getTransaction();
        List<Socio> socios = null;

        try {
            transaction.begin();
            TypedQuery<Socio> query = entityManager.createQuery(
                    "SELECT s FROM Socio s WHERE s.tipoSocio = :tipoSocio", Socio.class);
            query.setParameter("tipoSocio", tipoSocio);
            socios = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al listar socios por tipo: " + e.getMessage());
        }

        return socios;
    }


    // Eliminar un socio por ID
    public void eliminarSocioPA(int idSocio) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            Socio socio = entityManager.find(Socio.class, idSocio);
            if (socio != null) {
                entityManager.remove(socio);
                transaction.commit();
                System.out.println("Socio eliminado correctamente.");
            } else {
                System.out.println("No se encontró un socio con el ID proporcionado.");
                transaction.rollback();
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al eliminar el socio: " + e.getMessage());
        }
    }

    // Obtener un socio por su número
    public Socio getSocioByNumero(int numeroSocio) {
        return entityManager.find(Socio.class, numeroSocio);
    }

    // Actualizar los datos de un socio
    public void actualizarSocio(Socio socio) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.merge(socio);
            transaction.commit();
            System.out.println("Socio actualizado correctamente.");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al actualizar el socio: " + e.getMessage());
        }
    }

    // Listar todos los socios
    public List<Socio> listarTodosLosSocios() {
        TypedQuery<Socio> query = entityManager.createQuery("SELECT s FROM Socio s", Socio.class);
        return query.getResultList();
    }


    // Método para actualizar los datos de un socio en la base de datos
    // Método para actualizar los datos de un socio en la base de datos
    public void updateSocio(Socio socio) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            // Usamos merge para actualizar el objeto en la base de datos
            entityManager.merge(socio);
            transaction.commit();
            System.out.println("Socio actualizado correctamente.");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al actualizar el socio: " + e.getMessage());
        }
    }



    // Verificar si un socio existe por su número
    public boolean socioExiste(int numeroSocio) {
        Socio socio = entityManager.find(Socio.class, numeroSocio);
        return socio != null;
    }
}



