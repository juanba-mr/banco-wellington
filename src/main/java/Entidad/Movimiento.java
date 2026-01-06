package Entidad;


import java.util.Date;

public class Movimiento {
    private int idMovimiento;
    private Date fecha;
    private Cuenta cuentaOrigen;
    private Cuenta cuentaDestino;
    private TipoMovimiento tipoMovimiento;
    private String descripcion;
    private double monto;

    public Movimiento() {
        this.idMovimiento = 0;
        this.fecha = new Date(0);
        this.cuentaOrigen = new Cuenta();
        this.cuentaDestino = new Cuenta();
        this.tipoMovimiento = new TipoMovimiento();
        this.descripcion = "";
        this.monto = 0.0;
    }

    public Movimiento(int idMovimiento, Date fecha, Cuenta cuentaOrigen, Cuenta cuentaDestino, TipoMovimiento tipoMovimiento, String descripcion, double monto) {
        this.idMovimiento = idMovimiento;
        this.fecha = fecha;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.tipoMovimiento = tipoMovimiento;
        this.descripcion = descripcion;
        this.monto = monto;
    }

    public int getIdMovimiento() { return idMovimiento; }
    public void setIdMovimiento(int idMovimiento) { this.idMovimiento = idMovimiento; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public Cuenta getCuentaOrigen() { return cuentaOrigen; }
    public void setCuentaOrigen(Cuenta cuentaOrigen) { this.cuentaOrigen = cuentaOrigen; }

    public Cuenta getCuentaDestino() { return cuentaDestino; }
    public void setCuentaDestino(Cuenta cuentaDestino) { this.cuentaDestino = cuentaDestino; }

    public TipoMovimiento getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) { this.tipoMovimiento = tipoMovimiento; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    @Override
    public String toString() {
        return "Movimiento [idMovimiento=" + idMovimiento + ", fecha=" + fecha + ", cuentaOrigen=" + cuentaOrigen
                + ", cuentaDestino=" + cuentaDestino + ", tipoMovimiento=" + tipoMovimiento
                + ", descripcion=" + descripcion + ", monto=" + monto + "]";
    }
}