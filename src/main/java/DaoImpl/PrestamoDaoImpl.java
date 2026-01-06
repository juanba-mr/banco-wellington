package DaoImpl;

import Dao.PrestamoDao;
import Entidad.Cliente;
import Entidad.Cuenta;
import Entidad.Prestamo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDaoImpl implements PrestamoDao {
	private Conexion cn;

	@Override
	public boolean insertarPrestamo(Prestamo prestamo) {
		boolean resultado = false;
		Conexion cn = new Conexion();
		cn.Open();

		String query = "INSERT INTO prestamo (id_cuenta, fecha_solicitud, importe_total,importe_solicitado, cantidad_cuotas, importe_mensual, monto_pagado, cuotas_pagadas, estado) "
				+ "VALUES (?, ?, ?,?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement ps = cn.prepareStatement(query);
			ps.setString(1, prestamo.getIdcuenta().getIdCuenta());
			ps.setDate(2, new java.sql.Date(prestamo.getFechaSolicitud().getTime()));
			ps.setDouble(3, prestamo.getImporteTotal());
			ps.setDouble(4, prestamo.getMontoSolicitado());
			ps.setInt(5, prestamo.getCantidadCuotas());
			ps.setDouble(6, prestamo.getImporteMensual());
			ps.setDouble(7, 0.0);
			ps.setInt(8, 0);
			ps.setString(9, "pendiente");

			int filas = ps.executeUpdate();
			resultado = (filas > 0);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.close();
		}

		return resultado;
	}

	@Override
	public boolean aceptarPrestamo(int idPrestamo) {
		boolean estado = false;

		cn = new Conexion();
		cn.Open();

		String query = "UPDATE prestamo SET estado = 1 WHERE idPrestamo =" + idPrestamo;

		try {
			estado = cn.execute(query);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.close();
		}

		return estado;
	}

	@Override
	public boolean rechazarPrestamo(int idPrestamo) {
		boolean estado = false;

		cn = new Conexion();
		cn.Open();

		String query = "UPDATE prestamo SET estado = 0 WHERE idPrestamo = " + idPrestamo;

		try {
			estado = cn.execute(query);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.close();
		}

		return estado;
	}

	@Override
	public List<Prestamo> obtenerPrestamosPendientes() {
		List<Prestamo> lista = new ArrayList<>();
		Conexion cn = new Conexion();
		cn.Open();

		String query = "SELECT p.id_prestamo, p.estado, p.fecha_solicitud, p.importe_total, p.cantidad_cuotas, p.importe_mensual, "
				+ "c.id_cuenta, cl.dni_cliente AS dni, cl.nombre, cl.apellido " + "FROM prestamo p "
				+ "INNER JOIN cuenta c ON p.id_cuenta = c.id_cuenta "
				+ "INNER JOIN cliente cl ON c.dni_cliente = cl.dni_cliente " + "WHERE p.estado = 'pendiente'";

		try {
			ResultSet rs = cn.query(query);

			if (rs == null) {
				System.out.println("ERROR: El ResultSet es nulo. Posiblemente fall贸 la conexi贸n o la consulta.");
				return lista;
			}

			while (rs.next()) {
				Prestamo p = new Prestamo();
				Cuenta c = new Cuenta();
				Cliente cl = new Cliente();

				cl.setDniCliente(rs.getString("dni")); // aunque se llama dni_cliente, lo traemos como "dni"
				cl.setNombreCliente(rs.getString("nombre"));
				cl.setApellidoCliente(rs.getString("apellido"));

				c.setIdCuenta(rs.getString("id_cuenta"));
				c.setDnicliente(cl);

				p.setIdPrestamo(rs.getInt("id_prestamo"));
				p.setFechaSolicitud(rs.getDate("fecha_solicitud"));
				p.setIdcuenta(c);
				p.setImporteTotal(rs.getDouble("importe_total"));
				p.setCantidadCuotas(rs.getInt("cantidad_cuotas"));
				p.setImporteMensual(rs.getDouble("importe_mensual"));

				lista.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.close();
		}

		return lista;
	}

	@Override
	public List<Prestamo> obtenerPrestamos() {
		List<Prestamo> lista = new ArrayList<>();
		Conexion cn = new Conexion();
		cn.Open();

		String query = "SELECT p.id_prestamo, p.estado, p.importe_total, p.fecha_solicitud, p.cantidad_cuotas, p.importe_mensual, "
				+ "c.id_cuenta, cl.dni_cliente AS dni, cl.nombre, cl.apellido " + "FROM prestamo p "
				+ "INNER JOIN cuenta c ON p.id_cuenta = c.id_cuenta "
				+ "INNER JOIN cliente cl ON c.dni_cliente = cl.dni_cliente ";

		try {
			ResultSet rs = cn.query(query);

			if (rs == null) {
				System.out.println("ERROR: El ResultSet es nulo. Posiblemente fall贸 la conexi贸n o la consulta.");
				return lista;
			}

			while (rs.next()) {
				Prestamo p = new Prestamo();
				Cuenta c = new Cuenta();
				Cliente cl = new Cliente();

				cl.setDniCliente(rs.getString("dni")); // aunque se llama dni_cliente, lo traemos como "dni"
				cl.setNombreCliente(rs.getString("nombre"));
				cl.setApellidoCliente(rs.getString("apellido"));

				c.setIdCuenta(rs.getString("id_cuenta"));
				c.setDnicliente(cl);

				p.setIdPrestamo(rs.getInt("id_prestamo"));
				p.setFechaSolicitud(rs.getDate("fecha_solicitud"));
				p.setIdcuenta(c);
				p.setImporteTotal(rs.getDouble("importe_total"));
				p.setCantidadCuotas(rs.getInt("cantidad_cuotas"));
				p.setImporteMensual(rs.getDouble("importe_mensual"));
				p.setEstado(rs.getString("estado"));

				lista.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.close();
		}

		return lista;
	}

	@Override
	public Prestamo buscarPrestamoPorId(String idCuenta) {
		Prestamo prestamo = null;
		Conexion cn = new Conexion();
		cn.Open();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		

		try {
			String sql = "SELECT * FROM Prestamo WHERE id_cuenta = ? AND estado = 'aceptado'";
			ps = cn.prepareStatement(sql);
			ps.setString(1, idCuenta);
			rs = ps.executeQuery();

			if (rs.next()) {
				prestamo = new Prestamo();
				prestamo.setIdPrestamo(rs.getInt("id_Prestamo"));
				prestamo.setCantidadCuotas(rs.getInt("cantidad_cuotas"));
				prestamo.setImporteTotal(rs.getDouble("importe_total"));
				prestamo.setImporteMensual(rs.getDouble("importe_mensual"));
				prestamo.setCuotasPagadas(rs.getInt("cuotas_pagadas"));
				Cuenta cuenta = new Cuenta();
				cuenta.setIdCuenta(rs.getString("id_cuenta"));
				prestamo.setIdcuenta(cuenta);
				prestamo.setEstado(rs.getString("estado"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (cn != null)
					cn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return prestamo;
	}

	@Override
	public Prestamo buscarPrestamoPorIdString(String idPrestamo) {
		Prestamo prestamo = null;
		cn = new Conexion();
		cn.Open();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM Prestamo WHERE id_prestamo = ?";
			ps = cn.prepareStatement(sql);
			ps.setString(1, idPrestamo);
			rs = ps.executeQuery();

			if (rs.next()) {
				prestamo = new Prestamo();
				prestamo.setIdPrestamo(rs.getInt("id_prestamo"));
				prestamo.setCantidadCuotas(rs.getInt("cantidad_cuotas"));
				prestamo.setImporteTotal(rs.getDouble("importe_total"));
				prestamo.setImporteMensual(rs.getDouble("importe_mensual"));
				prestamo.setCuotasPagadas(rs.getInt("cuotas_pagadas"));

				Cuenta cuenta = new Cuenta();
				cuenta.setIdCuenta(rs.getString("id_cuenta"));

				// Traer saldo para validar pago cuota
				CuentaDaoImpl cuentaDao = new CuentaDaoImpl();
				cuenta = cuentaDao.obtenerCuentaPorId(cuenta.getIdCuenta());

				prestamo.setIdcuenta(cuenta);
				prestamo.setEstado(rs.getString("estado"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (cn != null)
					cn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return prestamo;
	}

	@Override
	public boolean pagarCuota(int idPrestamo, double montoCuota) {
		boolean exito = false;
		cn = new Conexion();
		cn.Open();

		try {
			String selectSql = "SELECT importe_total, cantidad_cuotas, cuotas_pagadas, id_cuenta FROM Prestamo WHERE id_prestamo = ?";
			PreparedStatement psSelect = cn.prepareStatement(selectSql);
			psSelect.setInt(1, idPrestamo);
			ResultSet rs = psSelect.executeQuery();

			if (rs.next()) {
				double importeTotalActual = rs.getDouble("importe_total");
				int cantidadCuotasTotal = rs.getInt("cantidad_cuotas");
				int cuotasPagadasActual = rs.getInt("cuotas_pagadas");
				String idCuenta = rs.getString("id_cuenta");

				if (cuotasPagadasActual >= cantidadCuotasTotal) {
					System.out.println("Todas las cuotas ya pagadas");
					return false;
				}

				CuentaDaoImpl cuentaDao = new CuentaDaoImpl();
				boolean saldoActualizado = cuentaDao.RestarSaldo(idCuenta, montoCuota);

				// 馃毄 PEG脕 AQU脥 LA DEPURACI脫N
				System.out.println("======= DEPURACION pagarCuota =======");
				System.out.println("idPrestamo: " + idPrestamo);
				System.out.println("montoCuota: " + montoCuota);
				System.out.println("importeTotalActual: " + importeTotalActual);
				System.out.println("cantidadCuotasTotal: " + cantidadCuotasTotal);
				System.out.println("cuotasPagadasActual: " + cuotasPagadasActual);
				System.out.println("idCuenta: " + idCuenta);
				System.out.println("SaldoActualizado: " + saldoActualizado);
				int nuevasCuotasPagadas = cuotasPagadasActual + 1;
				String nuevoEstado = (nuevasCuotasPagadas >= cantidadCuotasTotal) ? "pagado" : "aceptado";
				double nuevoImporteTotal = (nuevasCuotasPagadas >= cantidadCuotasTotal) ? 0.0
						: (importeTotalActual - montoCuota);
				System.out.println("nuevoImporteTotal: " + nuevoImporteTotal);
				System.out.println("nuevasCuotasPagadas: " + nuevasCuotasPagadas);
				System.out.println("nuevoEstado: " + nuevoEstado);
				System.out.println("=====================================");

				if (saldoActualizado) {
					String updateSql = "UPDATE Prestamo SET importe_total = ?, cuotas_pagadas = ?, estado = ? WHERE id_prestamo = ?";
					PreparedStatement psUpdate = cn.prepareStatement(updateSql);
					psUpdate.setDouble(1, nuevoImporteTotal);
					psUpdate.setInt(2, nuevasCuotasPagadas);
					psUpdate.setString(3, nuevoEstado);
					psUpdate.setInt(4, idPrestamo);

					System.out.println("Ejecutando UPDATE Prestamo...");
					int filas = psUpdate.executeUpdate();
					System.out.println("Filas afectadas: " + filas);

					if (filas > 0) {
						exito = true;
					}
					psUpdate.close();
				} else {
					System.out.println("Saldo insuficiente o error al actualizar saldo cuenta");
				}

				rs.close();
			} else {
				System.out.println("No se encontr贸 el pr茅stamo con id " + idPrestamo);
			}

			psSelect.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.close();
		}
		return exito;
	}

	@Override
	public boolean actualizarEstadoPrestamo(int idPrestamo, String estado) {
		boolean resultado = false;
		Conexion cn = new Conexion();
		cn.Open();

		String query = "UPDATE prestamo SET estado = ? WHERE id_prestamo = ?";

		try {
			PreparedStatement ps = cn.prepareStatement(query);
			ps.setString(1, estado);
			ps.setInt(2, idPrestamo);

			resultado = (ps.executeUpdate() > 0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.close();
		}

		return resultado;
	}

	@Override
	public boolean existePrestamoPendienteOAceptado(String idCuenta) {
		boolean existe = false;
		Conexion cn = new Conexion();
		cn.Open();
		String sql = "SELECT 1 FROM prestamo WHERE id_cuenta = ? AND (estado = 'pendiente' OR estado = 'aceptado') LIMIT 1";

		try {
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setString(1, idCuenta);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					existe = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return existe;
	}

}