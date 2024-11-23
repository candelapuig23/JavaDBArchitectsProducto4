package JavaDBArchitects.modelo.dao.JDBC;

import JavaDBArchitects.modelo.Seguro;
import JavaDBArchitects.modelo.TipoSeguro;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class SeguroDAO {

    private final EntityManagerFactory emf;
    private final EntityManager em;

    public SeguroDAO() {
        this.emf = Persistence.createEntityManagerFactory("JavaDBArchitectsPU");
        this.em = emf.createEntityManager();
    }

    // Método para inicializar seguros en la base de datos
    public void initializeSeguros() {
        if (getSeguroByTipo(TipoSeguro.BASICO) == null) {
            addSeguro(new Seguro(TipoSeguro.BASICO, 50.0f));
        }
        if (getSeguroByTipo(TipoSeguro.COMPLETO) == null) {
            addSeguro(new Seguro(TipoSeguro.COMPLETO, 100.0f));
        }
    }

    // Método para agregar un seguro a la base de datos
    public void addSeguro(Seguro seguro) {
        try {
            em.getTransaction().begin();
            em.persist(seguro);
            em.getTransaction().commit();
            System.out.println("Seguro de tipo " + seguro.getTipo() + " añadido con éxito.");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    // Método para obtener un seguro por tipo
    public Seguro getSeguroByTipo(TipoSeguro tipo) {
        try {
            TypedQuery<Seguro> query = em.createQuery("SELECT s FROM Seguro s WHERE s.tipo = :tipo", Seguro.class);
            query.setParameter("tipo", tipo);
            return query.getResultStream().findFirst().orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para obtener todos los seguros
    public List<Seguro> getAllSeguros() {
        try {
            TypedQuery<Seguro> query = em.createQuery("SELECT s FROM Seguro s", Seguro.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para cerrar el EntityManager y el EntityManagerFactory
    public void close() {
        if (em.isOpen()) em.close();
        if (emf.isOpen()) emf.close();
    }
}


