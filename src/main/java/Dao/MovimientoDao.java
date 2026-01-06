package Dao;

import java.util.List;

import Entidad.Movimiento;

public interface MovimientoDao {

	public List<Movimiento> obtenerTodosLosMovimientos();
	public boolean insertarMovimiento(Movimiento Movimiento);
	public List<Movimiento> obtenerMovimientoBuscado(int id);
	public List<Movimiento> obtenerTipoMovimientoBuscado(int tipo);
	public List<Movimiento> obtenerMovimientosTransferenciasPorId(String id);
	public int contarMovimientosPorRangoFechas(String fechaInicio, String fechaFin);
    public Movimiento obtenerTransferenciaPorId(int idMovimiento);

}