package Entidad;

import java.util.Date;

public class Cuenta {
    private String idCuenta;
    private Cliente dnicliente;
    private Date fechaCreacionCuenta;
    private TipoCuenta tipoCuenta;
    private String cbuCuenta;
    private double saldoCuenta;
    private boolean estado;

    public Cuenta() {
        this.idCuenta = "";
        this.dnicliente = new Cliente();
        this.fechaCreacionCuenta = new Date(0);
        this.tipoCuenta = new TipoCuenta();
        this.cbuCuenta = "";
        this.saldoCuenta = 0.0;
        this.estado = true;
    }

    public Cuenta(String idCuenta, Cliente dnicliente, Date fechaCreacionCuenta, TipoCuenta tipoCuenta, String cbuCuenta, double saldoCuenta, boolean estado) {
        this.idCuenta = idCuenta;
        this.dnicliente = dnicliente;
        this.fechaCreacionCuenta = fechaCreacionCuenta;
        this.tipoCuenta = tipoCuenta;
        this.cbuCuenta = cbuCuenta;
        this.saldoCuenta = saldoCuenta;
        this.estado = estado;
    }

   

    public String getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(String idCuenta) {
		this.idCuenta = idCuenta;
	}

	public Cliente getDnicliente() {
		return dnicliente;
	}

	public void setDnicliente(Cliente dnicliente) {
		this.dnicliente = dnicliente;
	}

	public Cliente getDniCliente() { return dnicliente; }
    public void setDniCliente(Cliente cliente) { this.dnicliente = cliente; }

    public Date getFechaCreacionCuenta() { return fechaCreacionCuenta; }
    public void setFechaCreacionCuenta(Date fechaCreacionCuenta) { this.fechaCreacionCuenta = fechaCreacionCuenta; }

    public TipoCuenta getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(TipoCuenta tipoCuenta) { this.tipoCuenta = tipoCuenta; }

    public String getCbuCuenta() { return cbuCuenta; }
    public void setCbuCuenta(String cbuCuenta) { this.cbuCuenta = cbuCuenta; }

    public double getSaldoCuenta() { return saldoCuenta; }
    public void setSaldoCuenta(double saldoCuenta) { this.saldoCuenta = saldoCuenta; }
    
    

    public boolean getEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@Override
    public String toString() {
        return "Cuenta [idCuenta=" + idCuenta + ", cliente=" + dnicliente + ", fechaCreacionCuenta=" + fechaCreacionCuenta
                + ", tipoCuenta=" + tipoCuenta + ", cbuCuenta=" + cbuCuenta + ", saldoCuenta=" + saldoCuenta + ", estado=" + estado + "]";
    }
}


