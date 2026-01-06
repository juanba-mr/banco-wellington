package Entidad;

public class Provincia {
    private int idProvincia;
    private String nombre;

    public Provincia() {
        this.idProvincia = 0;
        this.nombre = "";
    }

    public Provincia(int idProvincia, String nombre) {
        this.idProvincia = idProvincia;
        this.nombre = nombre;
    }

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Provincia [idProvincia=" + idProvincia + ", nombre=" + nombre + "]";
    }
}
