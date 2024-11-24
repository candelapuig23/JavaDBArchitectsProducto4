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

}