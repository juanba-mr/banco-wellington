package DaoImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Connection;

import Dao.CuentaDao;
import Entidad.Cliente;
import Entidad.Cuenta;
import Entidad.TipoCuenta;

public class CuentaDaoImpl implements CuentaDao{
	private Conexion cn;
	
	@Override
	public List<Cuenta> obtenerTodasLasCuentas() {
		
		cn = new Conexion();
		cn.Open();
		 List<Cuenta> listacuentas = new ArrayList<Cuenta>();
		 try
		 {
			 ResultSet rs= cn.query("    SELECT id_cuenta, dni_cliente, fecha_creacion, tipocuenta.descripcion,tipo_cuenta,cbu, saldo FROM cuenta INNER JOIN tipocuenta  ON cuenta.tipo_cuenta = tipocuenta.id_tipo_cuenta WHERE estado = 1");
			 while(rs.next())
			 {
				TipoCuenta TCuenta= new TipoCuenta();
				Cuenta cuenta = new Cuenta();
				Cliente cliente = new Cliente();
				
				cuenta.setIdCuenta(rs.getString("id_cuenta"));
				cliente.setDniCliente(rs.getString("dni_cliente"));
				cuenta.setDniCliente(cliente);
				cuenta.setFechaCreacionCuenta(rs.getDate("fecha_creacion"));
				cuenta.setCbuCuenta(rs.getString("cbu"));
				TCuenta.setIdTipoCuenta(rs.getString("tipo_cuenta"));
				TCuenta.setDescripcion(rs.getString("descripcion"));
				cuenta.setSaldoCuenta(rs.getDouble("saldo"));
			
				
				cuenta.setTipoCuenta(TCuenta);
				 listacuentas.add(cuenta);
			 }
			 
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 finally
		 {
			 cn.close();
		 }
		 return listacuentas;
	}


	
	@Override
	public List<Cuenta> obtenerPorTipoDeCuenta(String tipoNombre) {
		cn = new Conexion();
		cn.Open();
		 List<Cuenta> listacuentas = new ArrayList<Cuenta>();
		 try
		 {
			 
			 ResultSet rs= cn.query("Select id_cuenta, dni_cliente, fecha_creacion, tipo_cuenta, tipocuenta.descripcion,cbu,saldo from cuenta INNER JOIN tipocuenta ON cuenta.tipo_cuenta = tipocuenta.id_tipo_cuenta where tipo_cuenta='"+tipoNombre+"' AND estado = 1");
			 while(rs.next())
			 {
				TipoCuenta TCuenta= new TipoCuenta();
				Cuenta cuenta = new Cuenta();
				cuenta.setIdCuenta(rs.getString("id_cuenta"));
				Cliente cliente = new Cliente();
				cliente.setDniCliente(rs.getString("dni_cliente"));
				cuenta.setDniCliente(cliente);				
				cuenta.setFechaCreacionCuenta(rs.getDate("fecha_creacion"));
				TCuenta.setIdTipoCuenta(rs.getString("tipo_cuenta"));
				TCuenta.setDescripcion(rs.getString("descripcion"));
				cuenta.setCbuCuenta(rs.getString("cbu"));
				cuenta.setSaldoCuenta(rs.getDouble("saldo"));
				cuenta.setTipoCuenta(TCuenta);
				System.out.println("tipo de cuenta "+ cuenta.getTipoCuenta().getDescripcion());
				System.out.println("tipo de cuenta "+ cuenta.getTipoCuenta().getIdTipoCuenta());
				 listacuentas.add(cuenta);
			 }
			 
			 
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 finally
		 {
			 cn.close();
		 }
		 return listacuentas;
	}

	@Override
	public List<Cuenta> obtenerCuentaPorBusqueda(int id) {
		cn = new Conexion();
		cn.Open();
		 List<Cuenta> listacuentas = new ArrayList<Cuenta>();
		 try
		 {
			 ResultSet rs = cn.query("SELECT cuenta.id_cuenta, cuenta.dni_cliente, cuenta.fecha_creacion, tipocuenta.descripcion, cuenta.tipo_cuenta, cuenta.cbu, cuenta.saldo, cuenta.estado FROM cuenta INNER JOIN tipocuenta ON cuenta.tipo_cuenta = tipocuenta.id_tipo_cuenta WHERE cuenta.id_cuenta = '" + id +"'AND estado = 1");
			 while(rs.next())
			 {
				 TipoCuenta TCuenta= new TipoCuenta();
				Cuenta cuenta = new Cuenta();
				Cliente cliente = new Cliente();
				cliente.setDniCliente(rs.getString("dni_cliente"));
				cuenta.setDniCliente(cliente);				
				cuenta.setFechaCreacionCuenta(rs.getDate("fecha_creacion"));
				TCuenta.setIdTipoCuenta(rs.getString("tipo_cuenta"));
				cuenta.setSaldoCuenta(rs.getDouble("saldo"));
				
				cuenta.setTipoCuenta(TCuenta);
				 listacuentas.add(cuenta);
			 }
			 
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 finally
		 {
			 cn.close();
		 }
		 return listacuentas;
	}

	@Override
	public String insertarCuenta(Cuenta cuenta) {
		String mensajeResultado = "";

        cn = new Conexion(); 
        Connection connection = null;

		try	
		 {	
			cn.Open();
			connection = (Connection) cn.getConnection();
			
            String query = "{CALL crear_nueva_cuenta(?, ?)}";
            System.out.println("Llamando SP: " + query);
			
            try (CallableStatement statement = (CallableStatement) connection.prepareCall(query)) {
                
                statement.setString(1, cuenta.getDniCliente().getDniCliente());
                statement.setString(2, cuenta.getTipoCuenta().getIdTipoCuenta());
                
                statement.execute();
                
                mensajeResultado = "Cuenta creada exitosamente.";

            }

		 }catch (SQLException e) {
	            System.err.println("Error SQL al insertar cuenta: " + e.getMessage());
	            System.err.println("SQLState: " + e.getSQLState());
	            System.err.println("VendorError: " + e.getErrorCode());

	            if ("45000".equals(e.getSQLState())) {
	                mensajeResultado = e.getMessage(); // Usa el mensaje del procedimiento almacenado
	            } else {
	                mensajeResultado = "Error de base de datos: " + e.getMessage();
	            }
	        } catch (NumberFormatException e) {
	            System.err.println("Error de formato de número para DNI: " + e.getMessage());
	            mensajeResultado = "Error: El DNI del cliente no es un número válido.";
	        } catch (Exception e) {
	            System.err.println("Error inesperado en insertarCuenta: " + e.getMessage());
	            e.printStackTrace();
	            mensajeResultado = "Ocurrió un error inesperado en la aplicación.";
	        } finally {
	            if (cn != null) {
	                cn.close();
	            }
	        }
	        return mensajeResultado; // Retorna el mensaje de resultado
	    }
	

	@Override
	public boolean modificarCuenta(Cuenta cuenta) {
	    cn = new Conexion();
	    cn.Open();
	    
	    String sql = "UPDATE cuenta SET  tipo_cuenta = ? WHERE id_cuenta = ?";
	    
	    try {
	        PreparedStatement ps = (PreparedStatement) cn.prepareStatement(sql);
	        
	        ps.setString(1, cuenta.getTipoCuenta().getIdTipoCuenta());
	        ps.setString(2, cuenta.getIdCuenta());
	        
	        ps.executeUpdate();

	        return true;
	        
	    } catch (SQLException e) {
	        System.out.println("DEBUG CuentaDaoImpl: Error al actualizar cuenta");
	        e.printStackTrace();
	        return false;
	    } finally {
	        cn.close();
	    }
	}

	@Override
	public boolean borrarCuenta(String id) {
		boolean estado=true;
		cn = new Conexion();
		cn.Open();		 
		String query = "UPDATE cuenta SET estado=0 WHERE id_cuenta='"+id+"'";
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
	public List<Cuenta> obtenerCuentaBuscadoPorLike(String idcuenta) {
		 cn = new Conexion();
		    cn.Open();
			 List<Cuenta> lista = new ArrayList<Cuenta>();


		    try {
		        String sql = "SELECT id_cuenta, dni_cliente, fecha_creacion, tipo_cuenta,tipocuenta.descripcion, cbu,saldo " +
		                     "FROM cuenta INNER JOIN tipocuenta on cuenta.tipo_cuenta=tipocuenta.Id_tipo_cuenta WHERE id_cuenta LIKE '" + idcuenta + "%' AND estado= 1";
		        ResultSet rs = cn.query(sql);

		        while (rs.next()) {
		        	
		        	TipoCuenta TCuenta= new TipoCuenta();
					Cuenta cuenta = new Cuenta();
					cuenta.setIdCuenta(rs.getString("id_cuenta"));
					Cliente cliente = new Cliente();
					cliente.setDniCliente(rs.getString("dni_cliente"));
					cuenta.setDniCliente(cliente);				
					cuenta.setFechaCreacionCuenta(rs.getDate("fecha_creacion"));
					TCuenta.setIdTipoCuenta(rs.getString("tipo_cuenta"));
					TCuenta.setDescripcion(rs.getString("descripcion"));
					cuenta.setCbuCuenta(rs.getString("cbu"));
					cuenta.setSaldoCuenta(rs.getDouble("saldo"));
					cuenta.setTipoCuenta(TCuenta);
					
					 lista.add(cuenta);
		        }

		        rs.close();

		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        cn.close();
		    }

		    return lista;
	}

	@Override
	public boolean SumarSaldoACuenta(Double monto, String idCuenta) {
	    if (monto <= 0 || monto > 500000) return false;

	    cn = new Conexion();
	    cn.Open();

	    String sql = "UPDATE cuenta SET saldo = saldo + ? WHERE id_cuenta = ?";

	    try {
	        PreparedStatement ps = cn.prepareStatement(sql);
	        ps.setDouble(1, monto);
	        ps.setString(2, idCuenta);
	        int filas = ps.executeUpdate();

	        return filas > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        cn.close();
	    }
	}

	@Override
	public boolean RestarSaldoACuenta(Double monto,int id) {
		   boolean estado = true;

		    cn = new Conexion();
		    cn.Open();

		    try {
		        String query = "UPDATE cuenta SET monto = monto - " + monto + " WHERE id_cuenta = " + id;
		        cn.execute(query);
		    } catch (Exception e) {
		        e.printStackTrace();
		        estado = false;
		    } finally {
		        cn.close();
		    }

		    return estado;
	}
	
	public List<Cuenta> obtenerCuentasPorDni(String dni) {
        cn = new Conexion();
        cn.Open();

        List<Cuenta> lista = new ArrayList<>();
        System.out.println("cuenta encontrada"+ dni);

        String query = "SELECT cuenta.id_cuenta, cuenta.tipo_cuenta, cuenta.cbu, tipocuenta.descripcion " +
                "FROM cuenta " +
                "INNER JOIN tipocuenta ON cuenta.tipo_cuenta = tipocuenta.id_tipo_cuenta " +
                "WHERE cuenta.dni_cliente = ? AND cuenta.estado = 1";
        try (PreparedStatement ps = (PreparedStatement) cn.prepareStatement(query)) {
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                TipoCuenta TCuenta= new TipoCuenta();
                Cuenta cuenta = new Cuenta();

                cuenta.setIdCuenta(rs.getString("id_cuenta"));
                TCuenta.setIdTipoCuenta(rs.getString("tipo_cuenta"));
                TCuenta.setDescripcion(rs.getString("descripcion"));
                cuenta.setCbuCuenta(rs.getString("cbu"));
                
                cuenta.setTipoCuenta(TCuenta);
                
                System.out.println("cuenta encontrada"+ cuenta.getIdCuenta());
                lista.add(cuenta);
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cn.close();
        }
        
        return lista;
    }
	
	public List<Cuenta> obtenerCuentasFiltro(String dni, String cbu) {
	    cn = new Conexion();
	    TipoCuenta TCuenta= new TipoCuenta();
	    cn.Open();

	    List<Cuenta> lista = new ArrayList<>();

	    String query = "SELECT id_cuenta, tipo_cuenta, tipocuenta.descripcion, cbu FROM cuenta INNER JOIN tipocuenta ON cuenta.tipo_cuenta = tipocuenta.id_tipo_cuenta WHERE dni_cliente = ? AND cbu != ? AND estado = 1";

	    try (PreparedStatement ps = (PreparedStatement) cn.prepareStatement(query)) {
	        ps.setString(1, dni);
	        ps.setString(2, cbu);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Cuenta cuenta = new Cuenta();
	            cuenta.setIdCuenta(rs.getString("id_cuenta"));
	            TCuenta.setIdTipoCuenta(rs.getString("tipo_cuenta"));
	            TCuenta.setDescripcion(rs.getString("descripcion"));
	            cuenta.setCbuCuenta(rs.getString("cbu"));
	            
	            cuenta.setTipoCuenta(TCuenta);
	            lista.add(cuenta);
	        }

	        rs.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        cn.close();
	    }
	    return lista;
	}
	
	public Cuenta obtenerCuentaPorId(String idCuenta) {
	    Cuenta cuenta = new Cuenta();
	    TipoCuenta TCuenta= new TipoCuenta();
	    cn = new Conexion();
	    cn.Open();

	    String query = "SELECT * FROM cuenta INNER JOIN tipocuenta ON cuenta.tipo_cuenta = tipocuenta.id_tipo_cuenta  WHERE id_cuenta = ? AND estado = 1";

	    try (PreparedStatement ps = (PreparedStatement) cn.prepareStatement(query)) {
	        ps.setString(1, idCuenta);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            cuenta = new Cuenta();
	            cuenta.setIdCuenta(rs.getString("id_cuenta"));
	            cuenta.setCbuCuenta(rs.getString("cbu"));
	            TCuenta.setIdTipoCuenta(rs.getString("tipo_cuenta"));
	            TCuenta.setDescripcion(rs.getString("descripcion"));
	            
	            cuenta.setSaldoCuenta(rs.getDouble("saldo"));
	            
	            cuenta.setTipoCuenta(TCuenta);
	        }

	        rs.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        cn.close();
	    }

	    return cuenta;
	}



	public Cuenta obtenerCuentaPorCbu(String cbu) {

        Cuenta cuenta = null;
        TipoCuenta TCuenta= new TipoCuenta();
        cn = new Conexion();
        cn.Open();
        System.out.println("entro dao");


        String query = "SELECT * FROM cuenta INNER JOIN tipocuenta ON cuenta.tipo_cuenta = tipocuenta.Id_tipo_cuenta WHERE cbu = ? AND estado=1";

        try (PreparedStatement ps = (PreparedStatement) cn.prepareStatement(query)) {
            ps.setString(1, cbu);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                cuenta = new Cuenta();
                cuenta.setIdCuenta(rs.getString("id_cuenta"));
                cuenta.setCbuCuenta(rs.getString("cbu"));
                System.out.println(cuenta.getCbuCuenta());
                cuenta.setEstado(rs.getBoolean("estado"));
                cuenta.setTipoCuenta(TCuenta);

                Cliente cliente = new Cliente();
                cliente.setDniCliente(rs.getString("dni_cliente"));
                cuenta.setDnicliente(cliente);

                TCuenta.setIdTipoCuenta(rs.getString("tipo_cuenta"));
                TCuenta.setDescripcion(rs.getString("descripcion"));
                cuenta.setSaldoCuenta(rs.getDouble("saldo"));

                System.out.println(cuenta.getTipoCuenta().getIdTipoCuenta());
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cn.close();
        }

        return cuenta;
    }



	@Override
	public List<Cuenta> obtenerOrdenadoSaldoMayorAMenor() {
		cn = new Conexion();
		cn.Open();
		 List<Cuenta> listacuentas = new ArrayList<Cuenta>();
		 try
		 {
			 ResultSet rs = cn.query(
					    "SELECT c.id_cuenta, c.dni_cliente, c.fecha_creacion, c.tipo_cuenta, c.cbu, c.saldo,  tc.descripcion " +
					    "FROM cuenta c " +
					    " INNER JOIN tipocuenta tc ON c.tipo_cuenta = tc.id_tipo_cuenta " +
					    "WHERE c.estado = 1 " +
					    "ORDER BY c.saldo DESC"
					);			 while(rs.next())
			 {
				TipoCuenta TCuenta= new TipoCuenta();
				Cuenta cuenta = new Cuenta();
				Cliente cliente = new Cliente();
				
				cuenta.setIdCuenta(rs.getString("id_cuenta"));
				cliente.setDniCliente(rs.getString("dni_cliente"));
				cuenta.setDniCliente(cliente);
				cuenta.setFechaCreacionCuenta(rs.getDate("fecha_creacion"));
				cuenta.setCbuCuenta(rs.getString("cbu"));
				TCuenta.setIdTipoCuenta(rs.getString("tipo_cuenta"));
				TCuenta.setDescripcion(rs.getString("descripcion"));
				cuenta.setSaldoCuenta(rs.getDouble("saldo"));
				
				
				cuenta.setTipoCuenta(TCuenta);
				 listacuentas.add(cuenta);
			 }
			 
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 finally
		 {
			 cn.close();
		 }
		 return listacuentas;
	}



	@Override
	public List<Cuenta> obtenerOrdenadoSaldoMenorAMayor() {
	    cn = new Conexion();
	    cn.Open();
	    List<Cuenta> listacuentas = new ArrayList<Cuenta>();

	    try {
	        ResultSet rs = cn.query(
	            "SELECT c.id_cuenta, c.dni_cliente, c.fecha_creacion, c.tipo_cuenta, c.cbu, c.saldo, tc.descripcion " +
	            "FROM cuenta c " +
	            "INNER JOIN tipocuenta tc ON c.tipo_cuenta = tc.id_tipo_cuenta " +
	            "WHERE c.estado = 1 " +
	            "ORDER BY c.saldo ASC"
	        );

	        while (rs.next()) {
	            TipoCuenta TCuenta = new TipoCuenta();
	            Cuenta cuenta = new Cuenta();
	            Cliente cliente = new Cliente();

	            cuenta.setIdCuenta(rs.getString("id_cuenta"));
	            cliente.setDniCliente(rs.getString("dni_cliente"));
	            cuenta.setDniCliente(cliente);
	            cuenta.setFechaCreacionCuenta(rs.getDate("fecha_creacion"));
	            cuenta.setCbuCuenta(rs.getString("cbu"));
	            TCuenta.setIdTipoCuenta(rs.getString("tipo_cuenta"));
	            TCuenta.setDescripcion(rs.getString("descripcion"));
	            cuenta.setSaldoCuenta(rs.getDouble("saldo"));
	         
	            cuenta.setTipoCuenta(TCuenta);

	            listacuentas.add(cuenta);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        cn.close();
	    }

	    return listacuentas;
	}
	public boolean actualizarSaldo(Cuenta cuenta) {
        boolean actualizado = false;

        cn = new Conexion();
        cn.Open();
        PreparedStatement ps = null;

        try {
            String sql = "UPDATE cuenta SET saldo = ? WHERE cbu = ?";
            ps = cn.prepareStatement(sql);
            ps.setDouble(1, cuenta.getSaldoCuenta());
            ps.setString(2, cuenta.getCbuCuenta());

            int filas = ps.executeUpdate();
            actualizado = filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (cn != null) cn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return actualizado;
    }



	@Override
	public boolean Maximo3Cuenta(String Dni) {
		int cont = 0;
        Conexion cn = null; 
        try {
            cn = new Conexion();
            cn.Open();
            String query = "SELECT COUNT(*) AS total_cuentas FROM cuenta WHERE dni_cliente = '" + Dni + "' AND estado = 1";
            ResultSet rs = cn.query(query);

            if (rs.next()) {
                cont = rs.getInt("total_cuentas");
            }

            if (cont >= 3) { 
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace(); 
            return false; 
        } finally {
            if (cn != null) { 
                cn.close();
            }
        }
	}



	@Override
	public boolean existeCuenta(String idCuenta) {
	    boolean existe = false;
	    Conexion cn = null;

	    try {
	        cn = new Conexion();
	        cn.Open();

	        String query = "SELECT COUNT(*) AS total_cuentas FROM cuenta WHERE id_cuenta = ? AND estado = 1";
	        PreparedStatement ps = cn.prepareStatement(query);
	        ps.setString(1, idCuenta);

	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt("total_cuentas");
	            existe = (count > 0);
	        }

	        rs.close();
	        ps.close();
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
	public boolean RestarSaldo(String idCuenta, double montoARestar) {
        boolean exito = false;
        cn = new Conexion();
        cn.Open();
        PreparedStatement ps = null;

        try {
            // Opcional: Obtener el saldo actual para verificar si es suficiente ANTES de restar.
            // Aunque ya lo haces en el servlet, es buena práctica tener una doble verificación.
            String checkSaldoSql = "SELECT saldo FROM Cuenta WHERE id_cuenta = ?";
            ps = cn.prepareStatement(checkSaldoSql);
            ps.setString(1, idCuenta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double saldoActual = rs.getDouble("saldo");
                if (saldoActual < montoARestar) {
                    System.out.println("Saldo insuficiente en la cuenta " + idCuenta);
                    return false; // Saldo insuficiente
                }
            } else {
                System.out.println("Cuenta no encontrada: " + idCuenta);
                return false; // Cuenta no encontrada
            }
            rs.close(); // Cerrar ResultSet del check


            String sql = "UPDATE Cuenta SET saldo = saldo - ? WHERE id_cuenta = ?";
            ps = cn.prepareStatement(sql);
            ps.setDouble(1, montoARestar);
            ps.setString(2, idCuenta);

            int filasAfectadas = ps.executeUpdate();
            exito = (filasAfectadas > 0);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (cn != null) cn.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return exito;
    }

	
	
	
	
	
}