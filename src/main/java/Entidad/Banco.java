package Entidad;

public class Banco {
    private int idBanco;
    private String nombre;
    private String razonSocial;
    private String cbu;

    public Banco() {
        this.idBanco = 0;
        this.nombre = "";
        this.razonSocial = "";
        this.cbu = "";
    }

    public Banco(int idBanco, String nombre, String razonSocial, String cbu) {
        this.idBanco = idBanco;
        this.nombre = nombre;
        this.razonSocial = razonSocial;
        this.cbu = cbu;
    }

    public int getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    @Override
    public String toString() {
        return "Banco [idBanco=" + idBanco + ", nombre=" + nombre + ", razonSocial=" + razonSocial + ", cbu=" + cbu + "]";
    }
}