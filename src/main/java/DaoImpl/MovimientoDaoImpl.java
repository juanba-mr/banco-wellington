package DaoImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import Dao.MovimientoDao;
import Entidad.Cliente;
import Entidad.Cuenta;
import Entidad.Movimiento;
import Entidad.TipoMovimiento;


public class MovimientoDaoImpl implements MovimientoDao {

private Conexion cn;
	
public List<Movimiento> obtenerTodosLosMovimientos() {
    cn = new Conexion();
    cn.Open();
    List<Movimiento> lista = new ArrayList<>();

    try {
        String query = "SELECT id_movimiento, fecha, cuenta_origen, cuenta_destino, tipo_movimiento, descripcion, monto FROM movimiento";
        ResultSet rs = cn.query(query);

        while (rs.next()) {
            Movimiento mov = new Movimiento();
            mov.setIdMovimiento(rs.getInt("id_movimiento"));
            mov.setFecha(rs.getTimestamp("fecha")); // usa getTimestamp si guardÃ¡s fecha y hora

            // Origen
            Cuenta cuentaOrigen = new Cuenta();
            cuentaOrigen.setIdCuenta(rs.getString("cuenta_origen"));
            mov.setCuentaOrigen(cuentaOrigen);

            // Destino
            Cuenta cuentaDestino = new Cuenta();
            cuentaDestino.setIdCuenta(rs.getString("cuenta_destino"));
            mov.setCuentaDestino(cuentaDestino);

            // TipoMovimiento
            TipoMovimiento tipoMov = new TipoMovimiento();
            tipoMov.setIdTipoMovimiento(rs.getInt("tipo_movimiento"));
            mov.setTipoMovimiento(tipoMov);

            // Otros campos
            mov.setDescripcion(rs.getString("descripcion"));
            mov.setMonto(rs.getDouble("monto"));

            lista.add(mov);
        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        cn.close();
    }

    return lista;
}
	
	
	public List<Movimiento> obtenerMovimientoBuscado(int id) {
		cn = new Conexion();
		cn.Open();
		List<Movimiento> lista = new ArrayList<Movimiento> ();
		try
		 {
			 ResultSet rs= cn.query("Select id_movimiento, fecha, cuenta_origen, cuenta_destino, tipo_movimiento, descripcion , monto from movimiento where id_movimiento LIKE '%" + id +"%'");
			 rs.next();
		 
			 Movimiento mov = new Movimiento();
			 
			 mov.setIdMovimiento(rs.getInt("id_movimiento"));
			 mov.setFecha(rs.getDate("fecha"));
			 Cuenta cuentaOrigen = new Cuenta();
			 cuentaOrigen.setIdCuenta(rs.getString("cuenta_origen"));
			 mov.setCuentaOrigen(cuentaOrigen);

			 
			 Cuenta cuentaDestino = new Cuenta();
			 cuentaDestino.setIdCuenta(rs.getString("cuenta_destino"));
			 mov.setCuentaDestino(cuentaDestino);
			 TipoMovimiento tipoMov = new TipoMovimiento();
	            tipoMov.setIdTipoMovimiento(rs.getInt("tipo_movimiento"));
	            mov.setTipoMovimiento(tipoMov);
			 mov.setDescripcion(rs.getString("descripcion"));
			 mov.setMonto(rs.getDouble("monto"));
			
			 lista.add(mov);
			
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 finally
		 {
			 cn.close();
		 }
		return lista;
	}
	
	
	public boolean insertarMovimiento(Movimiento movimiento) {
	    boolean estado = false;
	    cn = new Conexion();
	    cn.Open();

	    String query = "INSERT INTO movimiento (fecha, cuenta_origen, cuenta_destino, monto, tipo_movimiento, descripcion) VALUES (?, ?, ?, ?, ?, ?)";

	    try (PreparedStatement ps = cn.prepareStatement(query)) {
	        ps.setTimestamp(1, (Timestamp) movimiento.getFecha());
	        ps.setString(2, movimiento.getCuentaOrigen().getIdCuenta());
	        ps.setString(3, movimiento.getCuentaDestino().getIdCuenta());
	        ps.setDouble(4, movimiento.getMonto());
	        ps.setInt(5, movimiento.getTipoMovimiento().getIdTipoMovimiento());
	        ps.setString(6, movimiento.getDescripcion());
	        System.out.println("consulta insertar movimiento: "+ movimiento.getCuentaOrigen().getIdCuenta()+ movimiento.getCuentaDestino().getIdCuenta() );
	        
	        estado = ps.executeUpdate() > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        cn.close();
	    }

	    return estado;
	}



@Override
public List<Movimiento> obtenerTipoMovimientoBuscado(int tipo) {
	
	cn = new Conexion();
	cn.Open();
	List<Movimiento> lista = new ArrayList<Movimiento> ();
	Movimiento mov = new Movimiento();
	try
	 {
		 ResultSet rs= cn.query("Select id_movimiento, fecha, cuenta_origen, cuenta_destino, tipo_movimiento, descripcion , monto from movimiento where tipo_movimiento="+tipo);
		 rs.next();
	 
		 mov.setIdMovimiento(rs.getInt("id_movimiento"));
		 mov.setFecha(rs.getDate("fecha"));
		 Cuenta cuentaOrigen = new Cuenta();
		 cuentaOrigen.setIdCuenta(rs.getString("cuenta_origen"));
		 mov.setCuentaOrigen(cuentaOrigen);

		 
		 Cuenta cuentaDestino = new Cuenta();
		 cuentaDestino.setIdCuenta(rs.getString("cuenta_destino"));
		 mov.setCuentaDestino(cuentaDestino);;
		 TipoMovimiento tipoMov = new TipoMovimiento();
         tipoMov.setIdTipoMovimiento(rs.getInt("tipo_movimiento"));
         mov.setTipoMovimiento(tipoMov);
		 mov.setDescripcion(rs.getString("descripcion"));
		 mov.setMonto(rs.getDouble("monto"));
		
		 lista.add(mov);
		
	 }
	 catch(Exception e)
	 {
		 e.printStackTrace();
	 }
	 finally
	 {
		 cn.close();
	 }
	
	return lista;
}

public List<Movimiento> obtenerMovimientosTransferenciaEnviadaPorId(String id) {
	cn = new Conexion();
	cn.Open();
	List<Movimiento> lista = new ArrayList<Movimiento> ();
	try
	 {
		ResultSet rs = cn.query("SELECT * from movimiento WHERE cuenta_origen = '"+ id +"' AND tipo_movimiento = 4 ORDER BY fecha DESC");

		while (rs.next()) {
		    Movimiento mov = new Movimiento();
		    mov.setIdMovimiento(rs.getInt("id_movimiento"));
		    mov.setFecha(rs.getDate("fecha"));

		    Cuenta cuentaOrigen = new Cuenta();
		    cuentaOrigen.setIdCuenta(rs.getString("cuenta_origen"));
		    mov.setCuentaOrigen(cuentaOrigen);

		    Cuenta cuentaDestino = new Cuenta();
		    cuentaDestino.setIdCuenta(rs.getString("cuenta_destino"));
		    mov.setCuentaDestino(cuentaDestino);

		    TipoMovimiento tipoMov = new TipoMovimiento();
		    tipoMov.setIdTipoMovimiento(rs.getInt("tipo_movimiento"));
		    mov.setTipoMovimiento(tipoMov);

		    mov.setDescripcion(rs.getString("descripcion"));
		    mov.setMonto(rs.getDouble("monto"));

		    lista.add(mov);
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
	return lista;
}

public List<Movimiento> obtenerMovimientosTransferenciasPorId(String id) {
    cn = new Conexion();
    cn.Open();
    List<Movimiento> lista = new ArrayList<Movimiento>();
    try {
    	String query = "SELECT m.id_movimiento, m.fecha, m.cuenta_origen, m.cuenta_destino, m.monto, m.tipo_movimiento, m.descripcion,  "
    		    + "cli_dest.nombre AS nombre_destino, cli_dest.apellido AS apellido_destino, "
    		    + "cli_origen.nombre AS nombre_origen, cli_origen.apellido AS apellido_origen "
    		    + "FROM movimiento m "
    		    + "LEFT JOIN cuenta c_origen ON m.cuenta_origen = c_origen.id_cuenta "
    		    + "LEFT JOIN cliente cli_origen ON c_origen.dni_cliente = cli_origen.dni_cliente "
    		    + "LEFT JOIN cuenta c_dest ON m.cuenta_destino = c_dest.id_cuenta "
    		    + "LEFT JOIN cliente cli_dest ON c_dest.dni_cliente = cli_dest.dni_cliente "
    		    + "WHERE (m.cuenta_origen = ? AND m.cuenta_destino = ?) || (m.cuenta_origen = ? OR m.cuenta_destino = ?) "
    		    + "ORDER BY m.fecha DESC";
        PreparedStatement ps = cn.prepareStatement(query);
        ps.setString(1, id);
        ps.setString(2, id);
        ps.setString(3, id);
        ps.setString(4, id);
        
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Movimiento mov = new Movimiento();
            mov.setIdMovimiento(rs.getInt("id_movimiento"));
            mov.setFecha(rs.getDate("fecha"));

            Cuenta cuentaOrigen = new Cuenta();
            cuentaOrigen.setIdCuenta(rs.getString("cuenta_origen"));
            Cliente clienteOrigen = new Cliente();
            clienteOrigen.setNombreCliente(rs.getString("nombre_origen"));
            clienteOrigen.setApellidoCliente(rs.getString("apellido_origen"));
            cuentaOrigen.setDnicliente(clienteOrigen);
            mov.setCuentaOrigen(cuentaOrigen);

            Cuenta cuentaDestino = new Cuenta();
            cuentaDestino.setIdCuenta(rs.getString("cuenta_destino"));
            Cliente clienteDestino = new Cliente();
            clienteDestino.setNombreCliente(rs.getString("nombre_destino"));
            clienteDestino.setApellidoCliente(rs.getString("apellido_destino"));
            cuentaDestino.setDnicliente(clienteDestino);
            mov.setCuentaDestino(cuentaDestino);

            TipoMovimiento tipoMov = new TipoMovimiento();
            tipoMov.setIdTipoMovimiento(rs.getInt("tipo_movimiento"));
            mov.setTipoMovimiento(tipoMov);

            mov.setDescripcion(rs.getString("descripcion"));
            mov.setMonto(rs.getDouble("monto"));

            lista.add(mov);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        cn.close();
    }
    return lista;
}


@Override
public int contarMovimientosPorRangoFechas(String fechaInicio, String fechaFin) {
    int cantidad = 0;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Conexion cn = new Conexion();

    try {
        cn.Open();
        String sql = "SELECT COUNT(*) AS cantidad FROM movimiento WHERE fecha BETWEEN ? AND ?";
        ps = cn.prepareStatement(sql);
        ps.setString(1, fechaInicio);
        ps.setString(2, fechaFin);
        rs = ps.executeQuery();

        if (rs.next()) {
            cantidad = rs.getInt("cantidad");
        }
    } catch (Exception e) {
        System.out.println("Error al contar movimientos: " + e.getMessage());
    } 
        
    

    return cantidad;
}



public double contarImportePorRangoFechas(String fechaInicio, String fechaFin) {
    double sumaImporte = 0.0;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Conexion cn = new Conexion();

    try {
        cn.Open();
        String sql = "SELECT SUM(monto) AS suma_importe FROM movimiento WHERE fecha BETWEEN ? AND ?";
        ps = cn.prepareStatement(sql);
        ps.setString(1, fechaInicio);
        ps.setString(2, fechaFin);
        rs = ps.executeQuery();

        if (rs.next()) {
            sumaImporte = rs.getDouble("suma_importe");
        }
    } catch (Exception e) {
        System.out.println("Error al sumar importe: " + e.getMessage());
    } 
    return sumaImporte;
}


@Override
public Movimiento obtenerTransferenciaPorId(int idMovimiento) {
    Movimiento mov = null;
    cn = new Conexion();
    cn.Open();

    try {
        String query = "SELECT m.id_movimiento, m.fecha, m.cuenta_origen, m.cuenta_destino, m.monto, m.tipo_movimiento, m.descripcion, "
                + "cli_dest.nombre AS nombre_destino, cli_dest.apellido AS apellido_destino, "
                + "cli_origen.nombre AS nombre_origen, cli_origen.apellido AS apellido_origen "
                + "FROM movimiento m "
                + "LEFT JOIN cuenta c_origen ON m.cuenta_origen = c_origen.id_cuenta "
                + "LEFT JOIN cliente cli_origen ON c_origen.dni_cliente = cli_origen.dni_cliente "
                + "LEFT JOIN cuenta c_dest ON m.cuenta_destino = c_dest.id_cuenta "
                + "LEFT JOIN cliente cli_dest ON c_dest.dni_cliente = cli_dest.dni_cliente "
                + "WHERE m.id_movimiento = ?";

        PreparedStatement ps = cn.prepareStatement(query);
        ps.setInt(1, idMovimiento);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            mov = new Movimiento();
            mov.setIdMovimiento(rs.getInt("id_movimiento"));
            mov.setFecha(rs.getTimestamp("fecha"));
            mov.setDescripcion(rs.getString("descripcion"));
            mov.setMonto(rs.getDouble("monto"));

            Cuenta cuentaOrigen = new Cuenta();
            cuentaOrigen.setIdCuenta(rs.getString("cuenta_origen"));
            Cliente clienteOrigen = new Cliente();
            clienteOrigen.setNombreCliente(rs.getString("nombre_origen"));
            clienteOrigen.setApellidoCliente(rs.getString("apellido_origen"));
            cuentaOrigen.setDnicliente(clienteOrigen);
            mov.setCuentaOrigen(cuentaOrigen);

            Cuenta cuentaDestino = new Cuenta();
            cuentaDestino.setIdCuenta(rs.getString("cuenta_destino"));
            Cliente clienteDestino = new Cliente();
            clienteDestino.setNombreCliente(rs.getString("nombre_destino"));
            clienteDestino.setApellidoCliente(rs.getString("apellido_destino"));
            cuentaDestino.setDnicliente(clienteDestino);
            mov.setCuentaDestino(cuentaDestino);

            TipoMovimiento tipoMov = new TipoMovimiento();
            tipoMov.setIdTipoMovimiento(rs.getInt("tipo_movimiento"));
            mov.setTipoMovimiento(tipoMov);
            System.out.println("Movimiento encontrado: id=" + mov.getIdMovimiento());
        } else {
            System.out.println("No hay resultados para idMovimiento=" + idMovimiento);
        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        cn.close();
    }

    return mov;
}



}