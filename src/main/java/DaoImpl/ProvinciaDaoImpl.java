package DaoImpl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Dao.ProvinciaDao;

import Entidad.Provincia;

public class ProvinciaDaoImpl implements ProvinciaDao {
	private Conexion cn;
	@Override
	public List<Provincia> obtenerTodasLasProvincias() {
		
		cn = new Conexion();
		cn.Open();
		 List<Provincia> listaProvincias = new ArrayList<Provincia>();
		 try
		 {
			 ResultSet rs= cn.query("Select * from provincia ");
			 while(rs.next())
			 {
				 Provincia provincia = new Provincia();
				provincia.setIdProvincia(rs.getInt("id_provincia"));
				provincia.setNombre(rs.getString("nombre"));				
				 
				
				 listaProvincias.add(provincia);
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
		 return listaProvincias;
	}

}
