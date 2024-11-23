package JavaDBArchitects.modelo;

import JavaDBArchitects.controlador.excepciones.*;
import JavaDBArchitects.modelo.dao.DAOFactory;
import JavaDBArchitects.modelo.dao.ExcursionDAO;
import JavaDBArchitects.modelo.dao.InscripcionDAO;
import JavaDBArchitects.modelo.dao.SocioDAO;

import java.util.List;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.math.BigDecimal;

public class Datos {

    // Instancias de DAO
    private static final SocioDAO socioDAO = DAOFactory.getSocioDAO();
    private static final ExcursionDAO excursionDAO = DAOFactory.getExcursionDAO();
    private static final InscripcionDAO inscripcionDAO = DAOFactory.getInscripcionDAO();

    // ------------------------------
    // Métodos de gestión de Socios
    // ------------------------------

    public static boolean registrarSocioPA(List<Object> parametros) throws SocioYaExisteException, SeguroInvalidoException, TipoSocioInvalidoException {
        int tipoSocio = (Integer) parametros.get(0);
        String nombre = parametros.get(2).toString();
        String NIF = parametros.get(3).toString();
        BigDecimal cuotaMensual = (BigDecimal) parametros.get(5);
        Object extra = null;
        int idFederacion = 0;  // Inicializamos en 0 para los federados
        Integer idSocioPadre = null;  // Inicializamos en null para los infantiles
        String nombreFederacion = null; // Nombre de la federación para los federados

        if (tipoSocio == 0) {
            extra = (Seguro) parametros.get(4);
        } else if (tipoSocio == 1) {
            Federacion federacion = (Federacion) parametros.get(4);
            idFederacion = federacion.getIdFederacion();
            nombreFederacion = federacion.getNombre(); // Asignamos el nombre de la federación
            extra = federacion;
        } else if (tipoSocio == 2) {
            idSocioPadre = (Integer) parametros.get(4);
        } else {
            throw new TipoSocioInvalidoException("El tipo de socio es inválido.");
        }

        if (socioDAO.socioExiste((Integer) parametros.get(1))) {
            throw new SocioYaExisteException("El socio con número " + parametros.get(1) + " ya existe.");
        }

        // Llamada a registrarSocioPA con el séptimo parámetro
        socioDAO.registrarSocioPA(nombre, tipoSocio, NIF, idFederacion, idSocioPadre, extra, nombreFederacion);
        return true;
    }


    public static boolean eliminarSocio(int numeroSocio) throws SocioNoExisteException, SocioConInscripcionesException {
        Socio socio = socioDAO.getSocioByNumero(numeroSocio);

        if (socio == null) {
            throw new SocioNoExisteException("El socio con número " + numeroSocio + " no existe.");
        }

        if (inscripcionDAO.socioTieneInscripciones(socio)) { // Pasamos el objeto socio
            throw new SocioConInscripcionesException("El socio no puede ser eliminado.");
        }

        socioDAO.eliminarSocioPA(numeroSocio);
        return true;
    }


    public static List<Socio> listarSocios(int tipoSocio) {
        String tipoSocioStr;
        switch (tipoSocio) {
            case 0:
                tipoSocioStr = "ESTANDAR";
                break;
            case 1:
                tipoSocioStr = "FEDERADO";
                break;
            case 2:
                tipoSocioStr = "INFANTIL";
                break;
            default:
                throw new IllegalArgumentException("Tipo de socio inválido.");
        }

        return socioDAO.listarSociosPorTipoPA(tipoSocioStr);
    }


    // ------------------------------
    // Métodos de gestión de Excursiones
    // ------------------------------

    public static boolean registrarExcursionPA(List<Object> parametros) throws ExcursionYaExisteException {
        String codigo = parametros.get(0).toString();
        String descripcion = parametros.get(1).toString();
        LocalDate fecha = ((java.sql.Date) parametros.get(2)).toLocalDate();
        int numeroDias = (Integer) parametros.get(3);
        float precio = (Float) parametros.get(4);

        if (excursionDAO.excursionExiste(codigo)) {
            throw new ExcursionYaExisteException("La excursión ya existe.");
        }

        Excursion excursion = new Excursion();
        excursion.setCodigo(codigo);
        excursion.setDescripcion(descripcion);
        excursion.setFecha(fecha);
        excursion.setNumeroDias(numeroDias);
        excursion.setPrecio(precio);

        excursionDAO.registrarExcursionPA(
                codigo,
                descripcion,
                fecha,
                numeroDias,
                precio
        );
        return true;
    }


    public static boolean eliminarExcursionPA(String idExcursion) throws ExcursionNoExisteException, EliminarExcursionConInscripcionesException {
        Excursion excursion = excursionDAO.getExcursionById(idExcursion);

        if (excursion == null) {
            throw new ExcursionNoExisteException("La excursión no existe.");
        }

        if (inscripcionDAO.excursionTieneInscripciones(excursion)) {
            throw new EliminarExcursionConInscripcionesException("La excursión tiene inscripciones activas.");
        }

        excursionDAO.eliminarExcursionPA(idExcursion);
        return true;
    }

    public static List<Excursion> listarExcursionesPorFechaPA(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Excursion> excursionesEnRango = new ArrayList<>();

        for (Excursion excursion : excursionDAO.getAllExcursiones()) {
            LocalDate fechaExcursion = excursion.getFecha()
                    ;

            if ((fechaExcursion.isEqual(fechaInicio) || fechaExcursion.isAfter(fechaInicio)) &&
                    (fechaExcursion.isEqual(fechaFin) || fechaExcursion.isBefore(fechaFin))) {
                excursionesEnRango.add(excursion);
            }
        }

        return excursionesEnRango;
    }

    // ------------------------------
    // Métodos de gestión de Inscripciones
    // ------------------------------

    public static boolean registrarInscripcion(List<Object> parametros)
            throws SocioNoExisteException, ExcursionNoExisteException, FechaInvalidaException, InscripcionYaExisteException {

        int numeroSocio = (Integer) parametros.get(0);
        String codigoExcursion = parametros.get(1).toString();
        LocalDate fechaInscripcion = (LocalDate) parametros.get(2);

        Socio socio = socioDAO.getSocioByNumero(numeroSocio);
        Excursion excursion = excursionDAO.getExcursionById(codigoExcursion);

        if (socio == null) {
            throw new SocioNoExisteException("El socio no existe.");
        }

        if (excursion == null) {
            throw new ExcursionNoExisteException("La excursión no existe.");
        }

        if (fechaInscripcion.isAfter(excursion.getFecha())) {
            throw new FechaInvalidaException("La inscripción no puede ser después de la excursión.");
        }

        if (inscripcionDAO.inscripcionExiste(numeroSocio, codigoExcursion)) {
            throw new InscripcionYaExisteException("El socio ya está inscrito.");
        }

        inscripcionDAO.inscribirEnExcursionPA(numeroSocio, codigoExcursion, fechaInscripcion);
        return true;
    }




    public static boolean eliminarInscripcion(String numeroInscripcion) throws InscripcionNoExisteException, CancelacionInvalidaException {
        Inscripcion inscripcion = inscripcionDAO.getInscripcionById(numeroInscripcion);

        if (inscripcion == null) {
            throw new InscripcionNoExisteException("La inscripción no existe.");
        }

        if (inscripcion.getExcursion().getFecha().isBefore(LocalDate.now())) {
            throw new CancelacionInvalidaException("No se puede eliminar una inscripción de una excursión ya realizada.");
        }

        return inscripcionDAO.eliminarInscripcionPA(Integer.parseInt(numeroInscripcion));
    }
}

