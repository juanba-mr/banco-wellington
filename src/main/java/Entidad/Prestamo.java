package Entidad;

import java.util.Date;

public class Prestamo {
    private int idPrestamo;
    private Cuenta idcuenta;
    private Date fechaSolicitud;
    private double importeTotal;
    private int cantidadCuotas;
    private double importeMensual;
    private double montoPagado;
    private int cuotasPagadas;
    private String estado;
    private double montoSolicitado;


    public Prestamo() {
        this.idPrestamo = 0;
        this.idcuenta = new Cuenta();
        this.fechaSolicitud = new Date(0);
        this.importeTotal = 0.0;
        this.cantidadCuotas = 0;
        this.importeMensual = 0.0;
        this.montoPagado = 0.0;
        this.cuotasPagadas = 0;
        this.estado = "";
    }

    public Prestamo(int idPrestamo, Cuenta idcuenta, Date fechaSolicitud, double importeTotal, int cantidadCuotas, double importeMensual, double montoPagado, int cuotasPagadas, String estado) {
        this.idPrestamo = idPrestamo;
        this.idcuenta = idcuenta;
        this.fechaSolicitud = fechaSolicitud;
        this.importeTotal = importeTotal;
        this.cantidadCuotas = cantidadCuotas;
        this.importeMensual = importeMensual;
        this.montoPagado = montoPagado;
        this.cuotasPagadas = cuotasPagadas;
        this.estado = estado;
    }

	public int getIdPrestamo() {
		return idPrestamo;
	}

	public void setIdPrestamo(int idPrestamo) {
		this.idPrestamo = idPrestamo;
	}

	public Cuenta getIdcuenta() {
		return idcuenta;
	}

	public void setIdcuenta(Cuenta idcuenta) {
		this.idcuenta = idcuenta;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public double getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(double importeTotal) {
		this.importeTotal = importeTotal;
	}

	public int getCantidadCuotas() {
		return cantidadCuotas;
	}

	public void setCantidadCuotas(int cantidadCuotas) {
		this.cantidadCuotas = cantidadCuotas;
	}

	public double getImporteMensual() {
		return importeMensual;
	}

	public void setImporteMensual(double importeMensual) {
		this.importeMensual = importeMensual;
	}

	public double getMontoPagado() {
		return montoPagado;
	}

	public void setMontoPagado(double montoPagado) {
		this.montoPagado = montoPagado;
	}

	public int getCuotasPagadas() {
		return cuotasPagadas;
	}

	public void setCuotasPagadas(int cuotasPagadas) {
		this.cuotasPagadas = cuotasPagadas;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Prestamo [idPrestamo=" + idPrestamo + ", idcuenta=" + idcuenta + ", fechaSolicitud=" + fechaSolicitud
				+ ", importeTotal=" + importeTotal + ", cantidadCuotas=" + cantidadCuotas + ", importeMensual="
				+ importeMensual + ", montoPagado=" + montoPagado + ", cuotasPagadas=" + cuotasPagadas + ", estado="
				+ estado + "]";
	}

	public double getMontoSolicitado() {
		return montoSolicitado;
	}

	public void setMontoSolicitado(double montoSolicitado) {
		this.montoSolicitado = montoSolicitado;
	}
}
