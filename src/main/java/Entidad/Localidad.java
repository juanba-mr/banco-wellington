package Entidad;

public class Localidad {
    private int idLocalidad;
    private Provincia idProvincia;
    private String nombre;

    public Localidad() {
        this.idLocalidad = 0;
        this.idProvincia= new Provincia();
        this.nombre = "";
    }

    public Localidad(int idLocalidad, Provincia idprovincia, String nombre) {
        this.idLocalidad = idLocalidad;
        this.idProvincia=new Provincia();
        this.nombre = nombre;
    }

    public int getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad(int idLocalidad) {
        this.idLocalidad = idLocalidad;
    }

    public Provincia getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(Provincia idProvincia) {
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
        return "Localidad [idLocalidad=" + idLocalidad + ", idProvincia=" + idProvincia + ", nombre=" + nombre + "]";
    }
}