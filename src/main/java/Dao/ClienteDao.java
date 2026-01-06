package Dao;

import java.util.List;

import Entidad.Cliente;

public interface ClienteDao {
	
	public List<Cliente> obtenerTodosLosClientes();
	public List<Cliente> obtenerClientesFiltradosPorSexo(String Sexo);
	public List<Cliente> obtenerClienteBuscadoPorLike(String dni);
	public boolean insertarCliente(Cliente cliente);
	public boolean borrarCliente(String dni);
	public boolean actualizarCliente(Cliente c);
	Boolean obtenerClienteBuscado(String dni);
	public Boolean existeCuil(String cuil);

	Cliente validarLogin(String usuario, String password);
	Cliente obtenerClientePorDni(String dni);
	public List<Cliente> obtenerSaldoTotalPorCliente() ;


}
