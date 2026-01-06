package DaoImpl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Dao.LocalidadDao;
import Entidad.Localidad;
import Entidad.Provincia;


public class LocalidadDaoImpl implements LocalidadDao {
	private Conexion cn;
	@Override
	public List<Localidad> obtenerTodasLasLocalidades () {
		
		cn = new Conexion();
		cn.Open();
		 List<Localidad> listaLocalidades = new ArrayList<Localidad>();
		 try
		 {
			 ResultSet rs= cn.query("Select * from provincia ");
			 while(rs.next())
			 {
				 Localidad localidad = new Localidad();
				 localidad.setIdLocalidad(rs.getInt("id_localidad"));
				 localidad.setNombre(rs.getString("nombre"));
				 
				
				 listaLocalidades.add(localidad);
			 }
			 
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 finally
		 {
			 cn.close();
		 }
		 return listaLocalidades;
	}

	public List<Localidad> obtenerLocalidadPorProvincia(int idProvincia) {
		
		 cn = new Conexion();
		    cn.Open();
		    List<Localidad> listaLocalidades = new ArrayList<Localidad>();

		    try {
		        String consulta = "SELECT * FROM localidad WHERE id_provincia = " + idProvincia;
		        ResultSet rs = cn.query(consulta);

		        while (rs.next()) {
		            Localidad localidad = new Localidad();
		            localidad.setIdLocalidad(rs.getInt("id_localidad"));
		            
		            Provincia p = new Provincia();
					 
					 p.setIdProvincia(rs.getInt("id_provincia"));
					 localidad.setIdProvincia(p);

		               
		            localidad.setNombre(rs.getString("nombre"));

		            listaLocalidades.add(localidad);
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        cn.close();
		    }

		    return listaLocalidades;
	}

}
