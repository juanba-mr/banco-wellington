package Entidad;

public class Acceso {
    private Cliente cliente;        
    private String tipoUsuario;
    private Boolean estado;

    public Acceso() {
        this.cliente = new Cliente(); 
        this.tipoUsuario = "";
        this.estado = true;
    }

    public Acceso(Cliente cliente, String tipoUsuario, Boolean estado) {
        this.cliente = cliente;
        this.tipoUsuario = tipoUsuario;
        this.estado = estado;
    }

    // --- Getters y Setters ---
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Acceso [usuario=" + cliente.getUsuarioCliente()
                + ", dni=" + cliente.getDniCliente()
                + ", tipoUsuario=" + tipoUsuario
                + ", estado=" + estado + "]";
    }
}