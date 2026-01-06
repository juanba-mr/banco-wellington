package DaoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import Dao.ClienteDao;
import Entidad.Cliente;
import Entidad.Localidad;
import Entidad.Provincia;


public class ClienteDaoImpl implements ClienteDao {
	
	private Conexion cn;

	@Override
	public List<Cliente> obtenerTodosLosClientes() {
	    cn = new Conexion();
	    cn.Open();
	    List<Cliente> lista = new ArrayList<Cliente>();

	    try {
	        ResultSet rs = cn.query(
	            "SELECT cliente.dni_cliente, cliente.cuil, cliente.nombre AS nombre_cliente, cliente.apellido, cliente.sexo, cliente.nacionalidad, cliente.direccion, cliente.correo_electronico, cliente.id_localidad, localidad.nombre AS nombre_localidad, provincia.nombre AS nombre_provincia, provincia.id_provincia, cliente.fecha_nacimiento, cliente.usuario, cliente.contrasenia FROM cliente INNER JOIN localidad ON cliente.id_localidad = localidad.id_localidad INNER JOIN provincia ON localidad.id_provincia = provincia.id_provincia WHERE cliente.estado = 1"
	        );

	        while (rs.next()) {
	            Cliente cli = new Cliente();
	            cli.setDniCliente(rs.getString("dni_cliente"));
	            cli.setCuilCliente(rs.getString("cuil"));
	            cli.setNombreCliente(rs.getString("nombre_cliente")); // ← usar alias correcto
	            cli.setApellidoCliente(rs.getString("apellido"));
	            cli.setSexoCliente(rs.getString("sexo"));
	            cli.setNacionalidad(rs.getString("nacionalidad"));
	            cli.setDireccionCliente(rs.getString("direccion"));
	            cli.setCorreoElectronicoCliente(rs.getString("correo_electronico"));

	            // Provincia
	            Provincia p = new Provincia();
	            p.setNombre(rs.getString("nombre_provincia"));
	            p.setIdProvincia(rs.getInt("id_provincia")); // ← corregido

	            // Localidad
	            Localidad l = new Localidad();
	            l.setNombre(rs.getString("nombre_localidad"));
	            l.setIdProvincia(p); // Localidad contiene la provincia
	            l.setIdLocalidad(rs.getInt("id_localidad")); // opcional pero correcto

	            cli.setIdProvinciaCliente(p); // Cliente tiene provincia
	            cli.setIdLocalidadCliente(l); // Cliente tiene localidad

	            cli.setFechaNacimientoCliente(rs.getDate("fecha_nacimiento"));
	            cli.setUsuarioCliente(rs.getString("usuario"));
	            cli.setContraseñaCliente(rs.getString("contrasenia"));

	            lista.add(cli);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        cn.close();
	    }

	    return lista;
	}
	
	public boolean actualizarCliente(Cliente c) {
	    cn = new Conexion();
	    cn.Open();

	    String sql = "UPDATE cliente SET cuil = ?, nombre = ?, apellido = ?, sexo = ?, nacionalidad = ?, direccion = ?, correo_electronico = ?, contrasenia = ? , usuario = ? WHERE dni_cliente = ?";
	    
	    try {
	        PreparedStatement ps = (PreparedStatement) cn.prepareStatement(sql);

	        ps.setString(1, c.getCuilCliente());
	        ps.setString(2, c.getNombreCliente());
	        ps.setString(3, c.getApellidoCliente());
	        ps.setString(4, c.getSexoCliente());
	        ps.setString(5, c.getNacionalidad());
	        ps.setString(6, c.getDireccionCliente());
	        ps.setString(7, c.getCorreoElectronicoCliente()); 
	        ps.setString(8, c.getContraseñaCliente());
	        ps.setString(9, c.getUsuarioCliente());
	        ps.setString(10, c.getDniCliente());

	        int filasActualizadas = ps.executeUpdate();

	        if (filasActualizadas > 0) {
	            System.out.println("DEBUG: Cliente actualizado correctamente.");
	            return true;
	        } else {
	            System.out.println("DEBUG: No se encontró cliente con ese DNI.");
	            return false;
	        }

	    } catch (SQLException e) {
	        System.out.println("DEBUG: Error al actualizar cliente:");
	        e.printStackTrace();
	        return false;
	    } finally {
	        cn.close();
	    }
	}

	

	@Override
	public boolean insertarCliente(Cliente cliente) {
		
		boolean estado=true;

		cn = new Conexion();
		cn.Open();	

		String query = "INSERT INTO cliente (dni_cliente,cuil,nombre,apellido,sexo,direccion,correo_electronico,id_localidad,id_provincia,nacionalidad,fecha_nacimiento,usuario,contrasenia,estado) "
				     + "VALUES ('"+cliente.getDniCliente()+"','"+cliente.getCuilCliente()+"','"+cliente.getNombreCliente()+"','"+cliente.getApellidoCliente()+"','"+cliente.getSexoCliente()+"','"+cliente.getDireccionCliente()+"','"+cliente.getCorreoElectronicoCliente()+"','"+cliente.getIdLocalidadCliente().getIdLocalidad()+"','"+cliente.getIdProvinciaCliente().getIdProvincia()+"','"+cliente.getNacionalidad()+"','"+cliente.getFechaNacimientoCliente()+"','"+cliente.getUsuarioCliente()+"','"+cliente.getContraseñaCliente()+"','"+cliente.getEstado()+"')";
		System.out.println(query);
		try
		 {
			estado=cn.execute(query);
		 }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			cn.close();
		}
		return estado;
	}

	
	@Override
	public boolean borrarCliente(String dni) {
		boolean estado=true;
		cn = new Conexion();
		cn.Open();		 
		String query = "UPDATE cliente SET estado=0 WHERE dni_cliente="+dni;
		try
		 {
			estado=cn.execute(query);
		 }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			cn.close();
		}
		return estado;
	}

	@Override
	public List<Cliente> obtenerClienteBuscadoPorLike(String dni) {
	    cn = new Conexion();
	    cn.Open();
	    List<Cliente> lista = new ArrayList<Cliente>();
	    
	    System.out.println("entro a funcion like"+dni);
	    try {
	        String sql =  "SELECT cliente.dni_cliente, cliente.cuil, cliente.nombre AS nombre_cliente, cliente.apellido, cliente.sexo, cliente.nacionalidad, cliente.direccion, cliente.correo_electronico, cliente.id_localidad, localidad.nombre AS nombre_localidad, provincia.nombre AS nombre_provincia, provincia.id_provincia, cliente.fecha_nacimiento, cliente.usuario, cliente.contrasenia " +
	                "FROM cliente " +
	                "INNER JOIN localidad ON cliente.id_localidad = localidad.id_localidad " +
	                "INNER JOIN provincia ON localidad.id_provincia = provincia.id_provincia " +
	                "WHERE cliente.estado = 1 " +
	                "AND cliente.dni_cliente LIKE '" + dni + "%'";
	        ResultSet rs = cn.query(sql);

	        while (rs.next()) {
	        	 Cliente cli = new Cliente();
	        	
		            cli.setDniCliente(rs.getString("dni_cliente"));
		            cli.setCuilCliente(rs.getString("cuil"));
		            cli.setNombreCliente(rs.getString("nombre_cliente")); // ← usar alias correcto
		            cli.setApellidoCliente(rs.getString("apellido"));
		            cli.setSexoCliente(rs.getString("sexo"));
		            cli.setNacionalidad(rs.getString("nacionalidad"));
		            cli.setDireccionCliente(rs.getString("direccion"));
		            cli.setCorreoElectronicoCliente(rs.getString("correo_electronico"));

		            // Provincia
		            Provincia p = new Provincia();
		            p.setNombre(rs.getString("nombre_provincia"));
		            p.setIdProvincia(rs.getInt("id_provincia")); // ← corregido

		            // Localidad
		            Localidad l = new Localidad();
		            l.setNombre(rs.getString("nombre_localidad"));
		            l.setIdProvincia(p); // Localidad contiene la provincia
		            l.setIdLocalidad(rs.getInt("id_localidad")); // opcional pero correcto

		            cli.setIdProvinciaCliente(p); // Cliente tiene provincia
		            cli.setIdLocalidadCliente(l); // Cliente tiene localidad

		            cli.setFechaNacimientoCliente(rs.getDate("fecha_nacimiento"));
		            cli.setUsuarioCliente(rs.getString("usuario"));
		            cli.setContraseñaCliente(rs.getString("contrasenia"));

			    lista.add(cli);
	        }

	        rs.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        cn.close();
	    }

	    return lista;
	}
	
	public Cliente validarLogin(String usuario, String password) {
	    cn = new Conexion();
	    cn.Open();
	    Cliente cli = null;

	    try {
	        String query = "SELECT * FROM cliente WHERE usuario = '" + usuario + "' AND contrasenia = '" + password + "'";
	        ResultSet rs = cn.query(query);

	        if (rs.next()) {
	            cli = new Cliente();
	            cli.setDniCliente(rs.getString("dni_cliente"));
	            cli.setCuilCliente(rs.getString("cuil"));
	            cli.setNombreCliente(rs.getString("nombre"));
	            cli.setApellidoCliente(rs.getString("apellido"));
	            cli.setSexoCliente(rs.getString("sexo"));
	            cli.setDireccionCliente(rs.getString("direccion"));
	            Provincia p = new Provincia();
			    p.setIdProvincia(rs.getInt("id_provincia"));
			   

			    Localidad l = new Localidad();
			    l.setIdLocalidad(rs.getInt("id_localidad"));
			    l.setIdProvincia(p);
			    cli.setIdLocalidadCliente(l);
	            cli.setFechaNacimientoCliente(rs.getDate("fecha_nacimiento"));
	            cli.setUsuarioCliente(rs.getString("usuario"));
	            cli.setContraseñaCliente(rs.getString("contrasenia"));
	            cli.setEstado(1);
	        }
	        rs.close();
	    } catch(Exception e) {
	        e.printStackTrace();
	    } finally {
	        cn.close();
	    }
	    return cli;
	}

	@Override
	public List<Cliente> obtenerClientesFiltradosPorSexo(String Sexo) {
		
		 cn = new Conexion();
		    cn.Open();
		    List<Cliente> lista = new ArrayList<Cliente>();

		    try {
		        ResultSet rs = cn.query(
		            "SELECT cliente.dni_cliente, cliente.cuil, cliente.nombre AS nombre_cliente, cliente.apellido, cliente.sexo, cliente.nacionalidad, cliente.direccion, cliente.correo_electronico, cliente.id_localidad, localidad.nombre AS nombre_localidad, provincia.nombre AS nombre_provincia, provincia.id_provincia, cliente.fecha_nacimiento, cliente.usuario, cliente.contrasenia FROM cliente INNER JOIN localidad ON cliente.id_localidad = localidad.id_localidad INNER JOIN provincia ON localidad.id_provincia = provincia.id_provincia WHERE cliente.estado = 1 AND cliente.sexo='" + Sexo + "'"
		        );

		        while (rs.next()) {
		            Cliente cli = new Cliente();
		            cli.setDniCliente(rs.getString("dni_cliente"));
		            cli.setCuilCliente(rs.getString("cuil"));
		            cli.setNombreCliente(rs.getString("nombre_cliente")); 
		            cli.setApellidoCliente(rs.getString("apellido"));
		            cli.setSexoCliente(rs.getString("sexo"));
		            cli.setNacionalidad(rs.getString("nacionalidad"));
		            cli.setDireccionCliente(rs.getString("direccion"));
		            cli.setCorreoElectronicoCliente(rs.getString("correo_electronico"));

		            // Provincia
		            Provincia p = new Provincia();
		            p.setNombre(rs.getString("nombre_provincia"));
		            p.setIdProvincia(rs.getInt("id_provincia")); // ← corregido

		            // Localidad
		            Localidad l = new Localidad();
		            l.setNombre(rs.getString("nombre_localidad"));
		            l.setIdProvincia(p); // Localidad contiene la provincia
		            l.setIdLocalidad(rs.getInt("id_localidad")); // opcional pero correcto

		            cli.setIdProvinciaCliente(p); // Cliente tiene provincia
		            cli.setIdLocalidadCliente(l); // Cliente tiene localidad

		            cli.setFechaNacimientoCliente(rs.getDate("fecha_nacimiento"));
		            cli.setUsuarioCliente(rs.getString("usuario"));
		            cli.setContraseñaCliente(rs.getString("contrasenia"));

		            lista.add(cli);
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        cn.close();
		    }

		    return lista;
	}

	@Override
	
	public Boolean obtenerClienteBuscado(String dni) {
	    Boolean existe = false;
	   

	    Conexion cn = new Conexion(); 
	    
	    try {
	        cn.Open(); // Abrir la conexión

	        System.out.println("entro a funcion " + dni);

	        String sql = "SELECT cliente.dni_cliente, cliente.cuil, cliente.nombre AS nombre_cliente, " +
	                     "cliente.apellido, cliente.sexo, cliente.nacionalidad, cliente.direccion, " +
	                     "cliente.correo_electronico, cliente.id_localidad, localidad.nombre AS nombre_localidad, " +
	                     "provincia.nombre AS nombre_provincia, provincia.id_provincia, " +
	                     "cliente.fecha_nacimiento, cliente.usuario, cliente.contrasenia " +
	                     "FROM cliente " +
	                     "INNER JOIN localidad ON cliente.id_localidad = localidad.id_localidad " +
	                     "INNER JOIN provincia ON localidad.id_provincia = provincia.id_provincia " +
	                     "WHERE cliente.estado = 1 " +
	                     "AND cliente.dni_cliente ='" + dni + "'"; 

	        ResultSet rs = cn.query(sql);

	        if (rs.next()) {
	          
	            existe = true;

	         

	            Cliente cli = new Cliente(); // 

	            cli.setDniCliente(rs.getString("dni_cliente"));
	            cli.setCuilCliente(rs.getString("cuil"));
	            cli.setNombreCliente(rs.getString("nombre_cliente"));
	            cli.setApellidoCliente(rs.getString("apellido"));
	            cli.setSexoCliente(rs.getString("sexo"));
	            cli.setNacionalidad(rs.getString("nacionalidad"));
	            cli.setDireccionCliente(rs.getString("direccion"));
	            cli.setCorreoElectronicoCliente(rs.getString("correo_electronico"));

	            Provincia p = new Provincia();
	            p.setNombre(rs.getString("nombre_provincia"));
	            p.setIdProvincia(rs.getInt("id_provincia"));

	            Localidad l = new Localidad();
	            l.setNombre(rs.getString("nombre_localidad"));
	            l.setIdProvincia(p);
	            l.setIdLocalidad(rs.getInt("id_localidad"));

	            cli.setIdProvinciaCliente(p);
	            cli.setIdLocalidadCliente(l);

	            cli.setFechaNacimientoCliente(rs.getDate("fecha_nacimiento"));
	            cli.setUsuarioCliente(rs.getString("usuario"));
	            cli.setContraseñaCliente(rs.getString("contrasenia"));
	            
	            // Si solo queremos saber si existe, podemos salir aquí.
	            // Si necesitas el objeto completo, no hagas el return aquí y déjalo para el final.
	            // return true; 

	        }
	     
	        if (rs != null) { 
	            rs.close();
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (cn != null) {
	            cn.close();
	        }
	    }

	    return existe; 
	}
	
	
	
	public Boolean existeCuil(String cuil) {
	    Boolean existe = false;
	   

	    Conexion cn = new Conexion(); 
	    
	    try {
	        cn.Open(); // Abrir la conexión


	        String sql = "SELECT cliente.dni_cliente, cliente.cuil, cliente.nombre AS nombre_cliente, " +
	                     "cliente.apellido, cliente.sexo, cliente.nacionalidad, cliente.direccion, " +
	                     "cliente.correo_electronico, cliente.id_localidad, localidad.nombre AS nombre_localidad, " +
	                     "provincia.nombre AS nombre_provincia, provincia.id_provincia, " +
	                     "cliente.fecha_nacimiento, cliente.usuario, cliente.contrasenia " +
	                     "FROM cliente " +
	                     "INNER JOIN localidad ON cliente.id_localidad = localidad.id_localidad " +
	                     "INNER JOIN provincia ON localidad.id_provincia = provincia.id_provincia " +
	                     "WHERE cliente.estado = 1 " +
	                     "AND cliente.cuil ='" + cuil + "'"; 

	        ResultSet rs = cn.query(sql);

	        if (rs.next()) {
	          
	            existe = true;

	         

	            Cliente cli = new Cliente(); // 

	            cli.setDniCliente(rs.getString("dni_cliente"));
	            cli.setCuilCliente(rs.getString("cuil"));
	            cli.setNombreCliente(rs.getString("nombre_cliente"));
	            cli.setApellidoCliente(rs.getString("apellido"));
	            cli.setSexoCliente(rs.getString("sexo"));
	            cli.setNacionalidad(rs.getString("nacionalidad"));
	            cli.setDireccionCliente(rs.getString("direccion"));
	            cli.setCorreoElectronicoCliente(rs.getString("correo_electronico"));

	            Provincia p = new Provincia();
	            p.setNombre(rs.getString("nombre_provincia"));
	            p.setIdProvincia(rs.getInt("id_provincia"));

	            Localidad l = new Localidad();
	            l.setNombre(rs.getString("nombre_localidad"));
	            l.setIdProvincia(p);
	            l.setIdLocalidad(rs.getInt("id_localidad"));

	            cli.setIdProvinciaCliente(p);
	            cli.setIdLocalidadCliente(l);

	            cli.setFechaNacimientoCliente(rs.getDate("fecha_nacimiento"));
	            cli.setUsuarioCliente(rs.getString("usuario"));
	            cli.setContraseñaCliente(rs.getString("contrasenia"));
	            
	            // Si solo queremos saber si existe, podemos salir aquí.
	            // Si necesitas el objeto completo, no hagas el return aquí y déjalo para el final.
	            // return true; 

	        }
	     
	        if (rs != null) { 
	            rs.close();
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (cn != null) {
	            cn.close();
	        }
	    }

	    return existe; 
	}
	
	@Override
	public Cliente obtenerClientePorDni(String dni) {
	    cn = new Conexion();
	    cn.Open();
	    Cliente cli = null;

	    try {
	        String sql = "SELECT cliente.dni_cliente, cliente.cuil, cliente.nombre, cliente.apellido, cliente.sexo, " +
	                     "cliente.correo_electronico, cliente.direccion, " +
	                     "provincia.id_provincia, provincia.nombre AS nombre_provincia, " +
	                     "localidad.id_localidad, localidad.nombre AS nombre_localidad " +
	                     "FROM cliente " +
	                     "INNER JOIN localidad ON cliente.id_localidad = localidad.id_localidad " +
	                     "INNER JOIN provincia ON localidad.id_provincia = provincia.id_provincia " +
	                     "WHERE cliente.dni_cliente = ?";

	        PreparedStatement ps = (PreparedStatement) cn.prepareStatement(sql);
	        ps.setString(1, dni);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            cli = new Cliente();
	            cli.setDniCliente(rs.getString("dni_cliente"));
	            cli.setCuilCliente(rs.getString("cuil"));
	            cli.setNombreCliente(rs.getString("nombre"));
	            cli.setApellidoCliente(rs.getString("apellido"));
	            cli.setSexoCliente(rs.getString("sexo"));
	            cli.setCorreoElectronicoCliente(rs.getString("correo_electronico"));
	            cli.setDireccionCliente(rs.getString("direccion"));

	            Provincia p = new Provincia();
	            p.setIdProvincia(rs.getInt("id_provincia"));
	            p.setNombre(rs.getString("nombre_provincia"));

	            Localidad l = new Localidad();
	            l.setIdLocalidad(rs.getInt("id_localidad"));
	            l.setNombre(rs.getString("nombre_localidad"));
	            l.setIdProvincia(p);

	            cli.setIdLocalidadCliente(l);
	            cli.setIdProvinciaCliente(p);
	        }

	        rs.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        cn.close();
	    }

	    return cli;
	}

	@Override
	public List<Cliente> obtenerSaldoTotalPorCliente() {
	    List<Cliente> lista = new ArrayList<>();
	    Conexion cn = new Conexion();

	    try {
	        cn.Open();
	        String query = "SELECT c.dni_cliente, c.nombre, c.apellido, c.usuario, SUM(ct.saldo) AS total_saldo " +
	                "FROM cliente c " +
	                "INNER JOIN cuenta ct ON c.dni_cliente = ct.dni_cliente " +
	                "GROUP BY c.dni_cliente, c.nombre, c.apellido, c.usuario " +
	                "ORDER BY total_saldo DESC " +  
	                "LIMIT 10"; 
	        
	        ResultSet rs = cn.query(query);
	        while (rs.next()) {
	            Cliente c = new Cliente();	            
	            c.setDniCliente(rs.getString("dni_cliente"));
	            c.setNombreCliente(rs.getString("nombre"));
	            c.setApellidoCliente(rs.getString("apellido"));
	            c.setUsuarioCliente(rs.getString("usuario"));
	            c.setSaldoTotal(rs.getDouble("total_saldo"));

	            lista.add(c);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    }

	    return lista;
	}
	
	
}
