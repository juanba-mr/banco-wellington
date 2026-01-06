package Dao;

import java.util.List;


import Entidad.Cuenta;



public interface CuentaDao {
	public List<Cuenta> obtenerTodasLasCuentas();
	public List<Cuenta> obtenerPorTipoDeCuenta(String tipoNombre);
	public List<Cuenta> obtenerCuentaPorBusqueda(int id);
	public List<Cuenta> obtenerCuentaBuscadoPorLike(String dni);
	public List<Cuenta> obtenerOrdenadoSaldoMayorAMenor();
	public List<Cuenta> obtenerOrdenadoSaldoMenorAMayor();
	public boolean Maximo3Cuenta(String Dni);
	
	public String insertarCuenta(Cuenta cuenta);
	public boolean modificarCuenta(Cuenta cuenta);
	public boolean borrarCuenta(String id);
	public boolean SumarSaldoACuenta(Double monto,String id);
	public boolean RestarSaldoACuenta(Double monto,int id);
	public boolean RestarSaldo(String idCuenta, double montoARestar);
	public Cuenta obtenerCuentaPorCbu(String cbu);
	public boolean actualizarSaldo(Cuenta cuenta);
	boolean existeCuenta(String idCuenta);
	
}