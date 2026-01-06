package Dao;

import Entidad.Acceso;

public interface AccesoDao {
    public Acceso validarLogin(String usuario, String password);
    public Boolean existeUsuario(String usuario);
}