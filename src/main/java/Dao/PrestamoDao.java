package Dao;

import java.util.List;

import Entidad.Prestamo;

public interface PrestamoDao {
    boolean insertarPrestamo(Prestamo prestamo);
    boolean aceptarPrestamo(int idPrestamo);
    boolean rechazarPrestamo(int idPrestamo);
    public List<Prestamo> obtenerPrestamosPendientes();
    public List<Prestamo> obtenerPrestamos();
    public Prestamo buscarPrestamoPorId(String idCuenta);
    public boolean actualizarEstadoPrestamo(int idPrestamo, String estado);
    public boolean existePrestamoPendienteOAceptado(String idCuenta);
    public Prestamo buscarPrestamoPorIdString(String idPrestamo);

    public boolean pagarCuota(int idPrestamo, double montoCuota);
}
