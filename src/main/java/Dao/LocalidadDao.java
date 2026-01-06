package Dao;

import java.util.List;

import Entidad.Localidad;

public interface LocalidadDao {
	public List<Localidad>obtenerTodasLasLocalidades();
	public List<Localidad>obtenerLocalidadPorProvincia(int id);
}
