package DaoImpl;


import java.sql.ResultSet;

import Dao.AccesoDao;
import Entidad.Acceso;
import Entidad.Cliente;
import Entidad.Localidad;
import Entidad.Provincia;


public class AccesoDaoImpl implements AccesoDao {
    private Conexion cn;

    public Acceso validarLogin(String usuario, String password) {
        cn = new Conexion();
        cn.Open();
        Acceso acceso = null;

        try {
            String query = "SELECT * FROM acceso WHERE nombre_usuario = '" + usuario + "' AND contrasenia = '" + password + "' AND estado=1";
            ResultSet rs = cn.query(query);

            if (rs.next()) {
                acceso = new Acceso();

                
                String nombreUsuario = rs.getString("nombre_usuario");

                
                String clienteQuery = "SELECT * FROM cliente WHERE usuario = '" + nombreUsuario + "'";
                ResultSet rsCliente = cn.query(clienteQuery);

                if (rsCliente.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setUsuarioCliente(rsCliente.getString("usuario"));
                    cliente.setNombreCliente(rsCliente.getString("nombre")); 
                    cliente.setCuilCliente(rsCliente.getString("cuil"));
                    cliente.setDniCliente(rsCliente.getString("dni_cliente"));
                    cliente.setApellidoCliente(rsCliente.getString("apellido"));

                    acceso.setCliente(cliente);
                }

                acceso.setTipoUsuario(rs.getString("tipo_usuario"));
                acceso.setEstado(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cn.close();
        }

        return acceso;
    }
	@Override
	public Boolean existeUsuario(String usuario) {
		Boolean existe = false;
		   

	    Conexion cn = new Conexion(); 
	    
	    try {
	        cn.Open(); // Abrir la conexión

	        System.out.println("entro a funcion " + usuario);

	        String sql = "SELECT acceso.nombre_usuario, acceso.contrasenia, acceso.dni_cliente, " +
	                     "acceso.tipo_usuario, acceso.estado from acceso where acceso.nombre_usuario='" +usuario+"'";
	                     

	        ResultSet rs = cn.query(sql);

	        if (rs.next()) {
	          
	            existe = true;


	            Cliente cli = new Cliente();  
	            Acceso acceso = new Acceso();
	            cli.setUsuarioCliente(rs.getString("nombre_usuario"));
	            cli.setContraseñaCliente(rs.getString("contrasenia"));
	            cli.setDniCliente(rs.getString("dni_cliente"));
	            acceso.setTipoUsuario(rs.getString("tipo_usuario"));
	            acceso.setEstado(rs.getBoolean("estado"));
	            acceso.setCliente(cli);
	         
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

}