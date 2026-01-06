package Entidad;

import java.sql.Date;

public class Cliente {
	private String dniCliente;
	private String cuilCliente;
	private String nombreCliente;
	private String apellidoCliente;
	private String sexoCliente;
	private String correoElectronicoCliente;
	private String direccionCliente;
	private Localidad idLocalidadCliente;
	private Provincia idProvinciaCliente;
	private String nacionalidad;
	private Date fechaNacimientoCliente;
	private String usuarioCliente;
	private String contraseñaCliente;
	private double SaldoTotal;
	

	private int estado;

	public Cliente() {
		this.dniCliente = "";
		this.cuilCliente = "";
		this.nombreCliente = "";
		this.apellidoCliente = "";
		this.sexoCliente = "";
		this.direccionCliente = "";
		this.idLocalidadCliente = new Localidad();
		this.nacionalidad = "";
		this.fechaNacimientoCliente = new Date(0);
		this.usuarioCliente = "";
		this.contraseñaCliente = "";
		this.estado = 1;
	}

	public Cliente(String dniCliente, String cuilCliente, String nombreCliente, String apellidoCliente,
			String sexoCliente, String correoElectronicoCliente, String direccionCliente, Provincia idProvinciaCliente, Localidad idLocalidadCliente,
			String nacionalidad, Date fechaNacimientoCliente, String usuarioCliente, String contraseña, int estado) {
		this.dniCliente = dniCliente;
		this.cuilCliente = cuilCliente;
		this.nombreCliente = nombreCliente;
		this.apellidoCliente = apellidoCliente;
		this.sexoCliente = sexoCliente;
		this.correoElectronicoCliente = correoElectronicoCliente;
		this.direccionCliente = direccionCliente;
		this.idLocalidadCliente = idLocalidadCliente;
		this.nacionalidad = nacionalidad;
		this.fechaNacimientoCliente = fechaNacimientoCliente;
		this.usuarioCliente = usuarioCliente;
		this.contraseñaCliente = contraseña;
		this.estado = estado;
	}

	public String getDniCliente() { return dniCliente; }
	public void setDniCliente(String dniCliente) { this.dniCliente = dniCliente; }

	public String getCuilCliente() { return cuilCliente; }
	public void setCuilCliente(String cuilCliente) { this.cuilCliente = cuilCliente; }

	public String getNombreCliente() { return nombreCliente; }
	public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

	public String getApellidoCliente() { return apellidoCliente; }
	public void setApellidoCliente(String apellidoCliente) { this.apellidoCliente = apellidoCliente; }

	public String getSexoCliente() { return sexoCliente; }
	public void setSexoCliente(String sexoCliente) { this.sexoCliente = sexoCliente; }
	

	public String getCorreoElectronicoCliente() {return correoElectronicoCliente;}
	public void setCorreoElectronicoCliente(String correoElectronicoCliente) {this.correoElectronicoCliente = correoElectronicoCliente;}

	public String getDireccionCliente() { return direccionCliente; }
	public void setDireccionCliente(String direccionCliente) { this.direccionCliente = direccionCliente; }

	
	public Localidad getIdLocalidadCliente() {return idLocalidadCliente;	}
	public void setIdLocalidadCliente(Localidad idLocalidadCliente) {this.idLocalidadCliente = idLocalidadCliente;  }

	public String getNacionalidad() { return nacionalidad; }
	public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }

	public Date getFechaNacimientoCliente() { return fechaNacimientoCliente; }
	public void setFechaNacimientoCliente(Date fechaNacimientoCliente) { this.fechaNacimientoCliente = fechaNacimientoCliente; }

	public String getUsuarioCliente() { return usuarioCliente; }
	public void setUsuarioCliente(String usuarioCliente) { this.usuarioCliente = usuarioCliente; }

	public String getContraseñaCliente() { return contraseñaCliente; }
	public void setContraseñaCliente(String contraseña) { this.contraseñaCliente = contraseña; }
	
	public int getEstado() {return estado;}
	public void setEstado(int estado) {this.estado = estado;}

	@Override
	public String toString() {
		return "Cliente [dniCliente=" + dniCliente + ", cuilCliente=" + cuilCliente + ", nombreCliente=" + nombreCliente
				+ ", apellidoCliente=" + apellidoCliente + ", sexoCliente=" + sexoCliente + ", direccionCliente="
				+ direccionCliente + ", localidad=" + idLocalidadCliente
				+ ", nacionalidad=" + nacionalidad + ", fechaNacimientoCliente=" + fechaNacimientoCliente
				+ ", usuarioCliente=" + usuarioCliente + ", contraseña=" + contraseñaCliente + ", estado=" + estado + "]";
	}

	public Provincia getIdProvinciaCliente() {
		return this.idProvinciaCliente;
	}

	public void setIdProvinciaCliente(Provincia idLocalidadProvincia) {
		this.idProvinciaCliente = idLocalidadProvincia;
	}

	public double getSaldoTotal() {
		return SaldoTotal;
	}

	public void setSaldoTotal(double saldoTotal) {
		SaldoTotal = saldoTotal;
	}

	
}