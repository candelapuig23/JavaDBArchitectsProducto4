package JavaDBArchitects.controlador;

import JavaDBArchitects.controlador.excepciones.*;
import JavaDBArchitects.modelo.*;
import JavaDBArchitects.modelo.dao.*;
import JavaDBArchitects.modelo.dao.JDBC.*;
import JavaDBArchitects.vista.MenuPrincipal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;

public class Controlador {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("JavaDBArchitectsPU");
    private static final EntityManager em = emf.createEntityManager();


    private static final SocioDAO socioDAO = DAOFactory.getSocioDAO();
    private static final ExcursionDAO excursionDAO = DAOFactory.getExcursionDAO();
    private static final InscripcionDAO inscripcionDAO = DAOFactory.getInscripcionDAO();
    private static final FederacionDAO federacionDAO = DAOFactory.getFederacion();
    private static final SeguroDAO seguroDAO = DAOFactory.getSeguroDAO();

    public static void iniciarAplicacion() {
        seguroDAO.initializeSeguros();  // Asegura que los seguros básicos estén configurados
        MenuPrincipal.mostrarMenu();    // Inicia el menú principal
    }

    public static void registrarSocioPA(String nombre, int tipoSocio, String nif, int idFederacion, Integer idSocioPadre, Object extra, String nombreFederacion) {
        em.getTransaction().begin();
        try {
            socioDAO.registrarSocioPA(nombre, tipoSocio, nif, idFederacion, idSocioPadre, extra, nombreFederacion);
            em.getTransaction().commit();
            MenuPrincipal.mostrarMensaje("Socio registrado correctamente.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            MenuPrincipal.mostrarError("Error al registrar socio: " + e.getMessage());
        }
    }

    public static void eliminarSocioPA(int numeroSocio) {
        em.getTransaction().begin();
        try {
            socioDAO.eliminarSocioPA(numeroSocio);
            em.getTransaction().commit();
            MenuPrincipal.mostrarMensaje("Socio eliminado con éxito.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            MenuPrincipal.mostrarError("Error al eliminar socio: " + e.getMessage());
        }
    }

    public static void registrarExcursionPA(String codigo, String descripcion, LocalDate fecha, int numeroDias, float precio) {
        em.getTransaction().begin();
        try {
            excursionDAO.registrarExcursionPA(codigo, descripcion, fecha, numeroDias, precio);
            em.getTransaction().commit();
            MenuPrincipal.mostrarMensaje("Excursión registrada con éxito.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            MenuPrincipal.mostrarError("Error al registrar excursión: " + e.getMessage());
        }
    }

    public static boolean eliminarExcursionPA(String idExcursion) {
        em.getTransaction().begin();
        try {
            boolean exito = excursionDAO.eliminarExcursionPA(idExcursion);
            em.getTransaction().commit();
            return exito;
        } catch (Exception e) {
            em.getTransaction().rollback();
            MenuPrincipal.mostrarError("Error al eliminar excursión: " + e.getMessage());
            return false;
        }
    }

    public static void inscribirEnExcursionPA(int numeroSocio, String codigoExcursion, LocalDate fechaInscripcion) {
        em.getTransaction().begin();
        try {
            inscripcionDAO.inscribirEnExcursionPA(numeroSocio, codigoExcursion, fechaInscripcion);
            em.getTransaction().commit();
            MenuPrincipal.mostrarMensaje("Inscripción realizada con éxito.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            MenuPrincipal.mostrarError("Error al inscribir en excursión: " + e.getMessage());
        }
    }


    public static void eliminarInscripcionPA(int numeroInscripcion) {
        em.getTransaction().begin();
        try {
            boolean eliminado = inscripcionDAO.eliminarInscripcionPA(numeroInscripcion);
            if (eliminado) {
                em.getTransaction().commit();
                MenuPrincipal.mostrarMensaje("Inscripción eliminada con éxito.");
            } else {
                em.getTransaction().rollback();
                MenuPrincipal.mostrarError("No se encontró la inscripción o no se pudo eliminar.");
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            MenuPrincipal.mostrarError("Error al eliminar inscripción: " + e.getMessage());
        }
    }

    public static void listarInscripcionesPA() {
        em.getTransaction().begin();
        try {
            inscripcionDAO.listarInscripcionesPA();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            MenuPrincipal.mostrarError("Error al listar inscripciones: " + e.getMessage());
        }
    }

    public static void listarExcursionesPorFecha(String fechaInicio, String fechaFin) {
        em.getTransaction().begin();
        try {
            // Convertir las fechas de String a LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaInicioLocalDate = LocalDate.parse(fechaInicio, formatter);
            LocalDate fechaFinLocalDate = LocalDate.parse(fechaFin, formatter);

            // Llamar al método del DAO con los tipos correctos
            List<Excursion> excursiones = excursionDAO.listarExcursionesPorFechaPA(fechaInicioLocalDate, fechaFinLocalDate);
            em.getTransaction().commit();

            if (excursiones.isEmpty()) {
                MenuPrincipal.mostrarMensaje("No se encontraron excursiones en el rango de fechas.");
            } else {
                excursiones.forEach(excursion -> MenuPrincipal.mostrarMensaje(excursion.toString()));
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            MenuPrincipal.mostrarError("Error al listar excursiones: " + e.getMessage());
        }
    }

    public static void listarSociosPorTipoPA(int tipoSocio) {
        em.getTransaction().begin();
        try {
            // Mapear el tipo de socio de int a String
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
                    MenuPrincipal.mostrarError("Tipo de socio inválido.");
                    return;
            }

            // Llamar al método del DAO con el tipo correcto
            List<Socio> socios = socioDAO.listarSociosPorTipoPA(tipoSocioStr);
            em.getTransaction().commit();

            if (socios.isEmpty()) {
                MenuPrincipal.mostrarMensaje("No se encontraron socios.");
            } else {
                socios.forEach(socio -> MenuPrincipal.mostrarMensaje(socio.toString()));
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            MenuPrincipal.mostrarError("Error al listar socios: " + e.getMessage());
        }
    }
    // El resto de métodos siguen el mismo patrón, asegurándose de gestionar las transacciones con JPA.

    public static void cerrarRecursos() {
        if (em.isOpen()) {
            em.close();
        }
        if (emf.isOpen()) {
            emf.close();
        }
    }

    public static void consultarFacturaMensual(int numeroSocio) {
        em.getTransaction().begin();
        try {
            Socio socio = socioDAO.getSocioByNumero(numeroSocio);

            if (socio == null) {
                MenuPrincipal.mostrarError("Error: El socio no existe.");
                em.getTransaction().rollback();
                return;
            }

            // Obtener la cuota mensual calculada desde la base de datos
            BigDecimal cuotaMensual = socio.getCuotaMensual(); // Incluye descuentos y seguro

            // Inicializar el total de la factura con la cuota mensual calculada
            BigDecimal totalFactura = cuotaMensual != null ? cuotaMensual : BigDecimal.ZERO;

            // Obtener todas las inscripciones del socio para calcular el total de excursiones
            List<Inscripcion> inscripciones = inscripcionDAO.getInscripcionesBySocio(numeroSocio);

            // Sumar el precio de cada excursión, aplicando cargos o descuentos según el tipo de socio
            for (Inscripcion inscripcion : inscripciones) {
                BigDecimal precioExcursion = BigDecimal.valueOf(inscripcion.getExcursion().getPrecio());

                if (socio instanceof Federado) {
                    // Para los federados, se aplica un 10% de descuento
                    totalFactura = totalFactura.add(precioExcursion.multiply(BigDecimal.valueOf(0.9)));
                } else {
                    // Para estándar e infantiles, se añade sin descuento
                    totalFactura = totalFactura.add(precioExcursion);
                }
            }

            em.getTransaction().commit();

            // Mostrar el total calculado de la factura mensual
            MenuPrincipal.mostrarMensaje("Factura mensual para el socio " + numeroSocio + ": " + totalFactura + "€");
        } catch (Exception e) {
            em.getTransaction().rollback();
            MenuPrincipal.mostrarError("Error al consultar la factura mensual: " + e.getMessage());
        }
    }

    public static void modificarDatosSocio(int numeroSocio, String nuevoNombre) {
        em.getTransaction().begin();
        try {
            Socio socio = socioDAO.getSocioByNumero(numeroSocio);
            if (socio == null) {
                throw new SocioNoExisteException("El socio no existe.");
            }
            // Actualizar el nombre del socio
            socio.setNombre(nuevoNombre);
            socioDAO.updateSocio(socio);

            em.getTransaction().commit();
            MenuPrincipal.mostrarMensaje("Datos del socio actualizados con éxito.");
        } catch (SocioNoExisteException e) {
            em.getTransaction().rollback();
            MenuPrincipal.mostrarError("Error: El socio no existe.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            MenuPrincipal.mostrarError("Error al modificar los datos del socio: " + e.getMessage());
        }
    }

    public static void mostrarInscripcionesConFiltros(Integer numeroSocio, LocalDate fechaInicio, LocalDate fechaFin) {
        em.getTransaction().begin();
        try {
            List<Inscripcion> inscripciones = inscripcionDAO.getAllInscripciones();

            // Filtrar por número de socio si se proporciona
            if (numeroSocio != null) {
                inscripciones = inscripciones.stream()
                        .filter(inscripcion -> inscripcion.getSocio().getNumeroSocio() == numeroSocio)
                        .collect(Collectors.toList());
            }

            // Filtrar por rango de fechas si se proporcionan ambas fechas
            if (fechaInicio != null && fechaFin != null) {
                inscripciones = inscripciones.stream()
                        .filter(inscripcion -> {
                            LocalDate fechaExcursion = inscripcion.getExcursion().getFecha();
                            return !fechaExcursion.isBefore(fechaInicio) && !fechaExcursion.isAfter(fechaFin);
                        })
                        .collect(Collectors.toList());
            }

            em.getTransaction().commit();

            // Mostrar mensaje si no hay inscripciones después de los filtros
            if (inscripciones.isEmpty()) {
                MenuPrincipal.mostrarMensaje("No se encontraron inscripciones con los filtros aplicados.");
            } else {
                for (Inscripcion inscripcion : inscripciones) {
                    LocalDate fechaExcursion = inscripcion.getExcursion().getFecha();
                    MenuPrincipal.mostrarMensaje("Inscripción: " + inscripcion + " - Fecha: " + fechaExcursion.format(FORMATO_FECHA));
                }
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            MenuPrincipal.mostrarError("Error al mostrar inscripciones: " + e.getMessage());
        }
    }

}

