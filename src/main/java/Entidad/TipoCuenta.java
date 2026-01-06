package Entidad;

public class TipoCuenta {
    private String idTipoCuenta;
    private String descripcion;

    public TipoCuenta() {
        this.idTipoCuenta = "";
        this.descripcion = "";
    }

    public TipoCuenta(String idTipoCuenta, String descripcion) {
        this.idTipoCuenta = idTipoCuenta;
        this.descripcion = descripcion;
    }

    public String	 getIdTipoCuenta() {
        return idTipoCuenta;
    }

    public void setIdTipoCuenta(String idTipoCuenta) {
        this.idTipoCuenta = idTipoCuenta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "TipoCuenta [idTipoCuenta=" + idTipoCuenta + ", descripcion=" + descripcion + "]";
    }
}
