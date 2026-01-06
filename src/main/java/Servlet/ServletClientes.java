package Servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DaoImpl.AccesoDaoImpl;
import DaoImpl.ClienteDaoImpl;
import DaoImpl.LocalidadDaoImpl; // Importa LocalidadDaoImpl
import DaoImpl.ProvinciaDaoImpl; // Importa ProvinciaDaoImpl
import Entidad.Cliente;

import Entidad.Provincia;
import Excepciones.FaltaArrobaException;
import Excepciones.FaltaPuntoException;
import Entidad.Localidad;

/**
 * Servlet implementation class ServletCliente
 */
@WebServlet("/ServletClientes")
public class ServletClientes extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletClientes() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	private void populateDdlData(HttpServletRequest request) {
		ProvinciaDaoImpl provinciaDao = new ProvinciaDaoImpl();
		List<Provincia> provincias = provinciaDao.obtenerTodasLasProvincias();
		request.setAttribute("provincias", provincias);

	
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,FaltaArrobaException,FaltaPuntoException {

		
		populateDdlData(request);		
	
		if(request.getParameter("btnDarDeAlta")!=null){
			Cliente cliente = new Cliente();
			String dni = request.getParameter("txtDni").trim();
			String nombre = request.getParameter("txtNombre").trim();
			String apellido = request.getParameter("txtApellido").trim();
			String correo=request.getParameter("txtCorreo").trim();
			String contraseña = request.getParameter("txtContraseña").trim();
			String contraseña2 = request.getParameter("txtContraseña2").trim();
			String cuil = request.getParameter("txtCuil").trim();
			String usuario=request.getParameter("txtUsuario").trim();
			String fechaNacimientoStr = request.getParameter("txtFechaNacimiento");


			// Validaciones básicas
			if(dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
				request.setAttribute("error", "campos_vacios");
				RequestDispatcher rd = request.getRequestDispatcher("/admin/AltaCliente.jsp");
				rd.forward(request, response);
				return;
			}
			
			// Verificar DNI existente
			ClienteDaoImpl dao = new ClienteDaoImpl();
			if(dao.obtenerClienteBuscado(dni)== true) {
				request.setAttribute("error", "dni_existente");
				System.out.println("DEBUG  entro  a  DNI EXISTE PAI:");
				RequestDispatcher rd = request.getRequestDispatcher("/admin/AltaCliente.jsp");
				rd.forward(request, response);
				return;
			}

		
			
			if (!dni.matches("^[0-9]+$") || dni.length() > 8) {
                request.setAttribute("error", "dni_invalido"); 
                RequestDispatcher rd = request.getRequestDispatcher("/admin/AltaCliente.jsp");
                rd.forward(request, response);
                return;
            }
			
			 
			try {
                if (!correo.contains("@")) {
                    throw new FaltaArrobaException();
                }

                if (!correo.contains(".")) {
                    throw new FaltaPuntoException();
                }

            } catch (FaltaArrobaException | FaltaPuntoException e) {
                e.printStackTrace();
                request.setAttribute("error", e.getMessage());


                RequestDispatcher rd = request.getRequestDispatcher("/admin/AltaCliente.jsp");
                rd.forward(request, response);
                return;
            }
			
			if(dao.existeCuil(cuil)== true) {
                request.setAttribute("error", "cuil_existente");
                System.out.println("DEBUG  entro  a  cuil EXISTE :");
                RequestDispatcher rd = request.getRequestDispatcher("/admin/AltaCliente.jsp");
                rd.forward(request, response);
                return;
			}
         if (!cuil.matches("^[0-9]+$") || cuil.length() > 11) {
                request.setAttribute("error", "cuil_invalido"); 
                RequestDispatcher rd = request.getRequestDispatcher("/admin/AltaCliente.jsp");
                rd.forward(request, response);
                return;
            }
         
     	if(!nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$") || !apellido.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
			request.setAttribute("error", "nombre_apellido_invalido");
			RequestDispatcher rd = request.getRequestDispatcher("/admin/AltaCliente.jsp");
			rd.forward(request, response);
			return;
		}
     	
     	AccesoDaoImpl Accesodao =new AccesoDaoImpl();
     	if(Accesodao.existeUsuario(usuario)== true) {
            request.setAttribute("error", "usuario_existente");
            System.out.println("DEBUG  entro  a  usuario EXISTE :");
            RequestDispatcher rd = request.getRequestDispatcher("/admin/AltaCliente.jsp");
            rd.forward(request, response);
            return;
     	}
			if (contraseña == null || contraseña2 == null || !contraseña.equals(contraseña2)) {
				request.setAttribute("error", "las_contraseñas_no_coinciden");
				RequestDispatcher rd = request.getRequestDispatcher("/admin/AltaCliente.jsp");
				rd.forward(request, response);
				return;
			}

		

		
			cliente.setDniCliente(dni);
			cliente.setCuilCliente(request.getParameter("txtCuil"));
			cliente.setNombreCliente(nombre);
			cliente.setApellidoCliente(apellido);
			cliente.setSexoCliente(request.getParameter("ddlSexo"));
			cliente.setCorreoElectronicoCliente(request.getParameter("txtCorreo"));
			Provincia provincia = new Provincia();
			Localidad localidad = new Localidad();
			provincia.setIdProvincia(Integer.parseInt(request.getParameter("ddlProvincia")));
			localidad.setIdLocalidad(Integer.parseInt(request.getParameter("ddlLocalidad")));
			localidad.setIdProvincia(provincia);
			cliente.setIdLocalidadCliente(localidad);
			cliente.setIdProvinciaCliente(provincia);
			cliente.setNacionalidad(request.getParameter("ddlPaises"));

			try {				
				 SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
	                Date fechaNacimientoUtil = formato.parse(fechaNacimientoStr); 
	                Date fechaActualUtil = new Date(); 

	                LocalDate fechaNacimientoLocal = fechaNacimientoUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	                LocalDate fechaActualLocal = fechaActualUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

	                if (fechaNacimientoLocal.isAfter(fechaActualLocal)) { 
	                    request.setAttribute("error", "fecha_nacimiento_futura"); 
	                    System.out.println("DEBUG: Fecha de nacimiento es futura.");
	                    RequestDispatcher rd = request.getRequestDispatcher("/admin/AltaCliente.jsp");
	                    rd.forward(request, response);
	                    return;
	                }

	                LocalDate fechaMinimaNacimiento = fechaActualLocal.minus(Period.ofYears(18));
	                
	                if (fechaNacimientoLocal.isAfter(fechaMinimaNacimiento)) { // Si la fecha de nacimiento es DESPUÉS de la fecha mínima (es decir, es más joven)
	                    request.setAttribute("error", "menor_de_18_anios"); // Nuevo código de error
	                    System.out.println("DEBUG: Cliente es menor de 18 años.");
	                    RequestDispatcher rd = request.getRequestDispatcher("/admin/AltaCliente.jsp");
	                    rd.forward(request, response);
	                    return;
	                }
				 
				 cliente.setFechaNacimientoCliente(new java.sql.Date(fechaNacimientoUtil.getTime()));
			} catch (ParseException e) {
				request.setAttribute("error", "formato_fecha_invalido");
               System.err.println("ERROR: Formato de fecha de nacimiento inválido: " + fechaNacimientoStr);
				e.printStackTrace();
				 RequestDispatcher rd = request.getRequestDispatcher("/admin/AltaCliente.jsp");
	             rd.forward(request, response);
	             return;
				
			}

			
			cliente.setDireccionCliente(request.getParameter("txtDireccion"));
			cliente.setUsuarioCliente(request.getParameter("txtUsuario"));
			cliente.setContraseñaCliente(request.getParameter("txtContraseña"));
			
			boolean seAgrego = dao.insertarCliente(cliente);

			if (seAgrego) {
				request.setAttribute("agregado", "1");
			} else {
				System.out.println("DEBUG  entro  a EL INSERT PLIN PLIN :");
				request.setAttribute("error", "insert_fallido");
			}

			RequestDispatcher rd = request.getRequestDispatcher("/admin/AltaCliente.jsp");
			rd.forward(request, response);

	
		}
		
		if(request.getParameter("btnListarClientes")!=null){
		
		ClienteDaoImpl cDao = new ClienteDaoImpl();
        List<Cliente> lista = cDao.obtenerTodosLosClientes();

        request.setAttribute("listaU", lista);

        RequestDispatcher rd = request.getRequestDispatcher("admin/ListarClientes.jsp");
        rd.forward(request, response);
		}
		
		if(request.getParameter("btnListarModificarClientes")!=null){
			
			ClienteDaoImpl cDao = new ClienteDaoImpl();
	        List<Cliente> lista = cDao.obtenerTodosLosClientes();

	        request.setAttribute("listaU", lista);

	        RequestDispatcher rd = request.getRequestDispatcher("admin/ModificarCliente.jsp");
	        rd.forward(request, response);
			}
		
		
		 if(request.getParameter("btnBuscar")!=null){
			 System.out.println("entro a btnBuscar");
			String dni=request.getParameter("txtDni");
			System.out.println(dni);
			ClienteDaoImpl cDao = new ClienteDaoImpl();
		
	        List<Cliente> lista = cDao.obtenerClienteBuscadoPorLike(dni);

	        request.setAttribute("listaU", lista);
	        if (lista.isEmpty()) {
		        request.setAttribute("errorMensaje", "No se encontraron clientes en base a tu busqueda");
		    }

	        RequestDispatcher rd = request.getRequestDispatcher("admin/ModificarCliente.jsp");
	        rd.forward(request, response);
			}
		
		 
		 if(request.getParameter("btnRestablecer")!=null){
			
			ClienteDaoImpl cDao = new ClienteDaoImpl();
		
	        List<Cliente> lista = cDao.obtenerTodosLosClientes();

	        request.setAttribute("listaU", lista);

	        

	        RequestDispatcher rd = request.getRequestDispatcher("admin/ModificarCliente.jsp");
	        rd.forward(request, response);
			}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("filtrarClientes")!=null) {
			ClienteDaoImpl cDao = new ClienteDaoImpl();
			String filtro=request.getParameter("filtro");
			List<Cliente> lista = new ArrayList<>();
			if (!filtro.equals("default")) {
			    lista = cDao.obtenerClientesFiltradosPorSexo(filtro);
			} else {
			    lista = cDao.obtenerTodosLosClientes();
			}
			
			
			request.setAttribute("listaU", lista);
	        RequestDispatcher rd = request.getRequestDispatcher("admin/ListarClientes.jsp");
	        rd.forward(request, response);
			
		}
		if (request.getParameter("BtnEditar") != null) {
			System.out.println("DEBUG ServletClientes : entro en btnEditar :");
		    String dni = request.getParameter("dniCliente");
		    String cuil = request.getParameter("cuilCliente");
		    String nombre = request.getParameter("nombreCliente");
		    String apellido = request.getParameter("apellidoCliente");
		    String direccion = request.getParameter("direccionCliente");
		    String correo = request.getParameter("CorreoCliente");
		    String usuario = request.getParameter("usuarioCliente"); 
		    String contraseña = request.getParameter("contrasenaCliente1"); 
		    

		    String Sexo = request.getParameter("sexoCliente");
		    String Nacionalidad = request.getParameter("nacionalidad");
		    
		    Cliente cliente = new Cliente();
		    String fechaNacimientoStr = request.getParameter("fechaNacimientoCliente");

		    if (fechaNacimientoStr != null && !fechaNacimientoStr.isEmpty()) {
		        try {
		            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		            Date fechaNacimientoUtil = sdf.parse(fechaNacimientoStr);
		            java.sql.Date fechaNacimientoSql = new java.sql.Date(fechaNacimientoUtil.getTime());
		           
					cliente.setFechaNacimientoCliente(fechaNacimientoSql);
		        } catch (ParseException e) {
		            e.printStackTrace();
		        }
		    }

		    
		    

		    
		    cliente.setDniCliente(dni);
		    cliente.setCuilCliente(cuil);
		    cliente.setNombreCliente(nombre);
		    cliente.setApellidoCliente(apellido);
		    cliente.setDireccionCliente(direccion);
		    cliente.setCorreoElectronicoCliente(correo);
		    cliente.setUsuarioCliente(usuario);
		    cliente.setContraseñaCliente(contraseña);
		    cliente.setSexoCliente(Sexo);
		    cliente.setNacionalidad(Nacionalidad);
		    
		    System.out.println("contraseña 1: " + contraseña);
		    System.out.println("Usuario: " + usuario);
		
		   

		    request.setAttribute("cliente", cliente);

		    RequestDispatcher rd = request.getRequestDispatcher("admin/EditarCliente.jsp");
		    rd.forward(request, response);
		}

		
		
		
		if (request.getParameter("btnAcceptar") != null) {
		    System.out.println("DEBUG ServletClientes : entro en btnAcceptar :");

		    // 1. Obtener TODOS los parámetros del formulario que el usuario acaba de enviar.
		    String dni = request.getParameter("txtDni");
		    String cuil = request.getParameter("txtCuil");
		    String nombre = request.getParameter("txtNombre");
		    String apellido = request.getParameter("txtApellido");
		    String direccion = request.getParameter("txtDireccion");
		    String correo = request.getParameter("txtCorreo");
		    String usuario = request.getParameter("txtUsuario");
		    String contrasena = request.getParameter("txtContrasena1");
		    String contrasena2 = request.getParameter("txtContrasena2");
		    String Sexo = request.getParameter("sexoCliente"); 
		    String Nacionalidad = request.getParameter("nacionalidad"); 

		 
		    Cliente clienteConDatosIngresados = new Cliente();
		    clienteConDatosIngresados.setDniCliente(dni);
		    clienteConDatosIngresados.setCuilCliente(cuil);
		    clienteConDatosIngresados.setNombreCliente(nombre);
		    clienteConDatosIngresados.setApellidoCliente(apellido);
		    clienteConDatosIngresados.setDireccionCliente(direccion);
		    clienteConDatosIngresados.setCorreoElectronicoCliente(correo);
		    clienteConDatosIngresados.setUsuarioCliente(usuario);
		    clienteConDatosIngresados.setContraseñaCliente(contrasena); 
		    clienteConDatosIngresados.setSexoCliente(Sexo);
		    clienteConDatosIngresados.setNacionalidad(Nacionalidad);

		   
	

		    if(!nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$") || !apellido.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
		      
		        request.setAttribute("cliente", clienteConDatosIngresados);
		        request.setAttribute("error", "nombre_apellido_invalido");
		        RequestDispatcher rd = request.getRequestDispatcher("admin/EditarCliente.jsp");
		        rd.forward(request, response);
		        return;
		    }

		    AccesoDaoImpl Accesodao = new AccesoDaoImpl();
		    
		    if(Accesodao.existeUsuario(usuario) == true) {
		        request.setAttribute("cliente", clienteConDatosIngresados); 
		        request.setAttribute("error", "usuario_existente");
		        System.out.println("DEBUG  entro  a  usuario EXISTE :");
		        RequestDispatcher rd = request.getRequestDispatcher("/admin/EditarCliente.jsp");
		        rd.forward(request, response);
		        return;
		    }

		    System.out.println("contraseña 1: " + contrasena);
		    System.out.println("contraseña 2: " + contrasena2);
		
		    if (contrasena == null || contrasena2 == null || !contrasena.equals(contrasena2)) {
		        request.setAttribute("cliente", clienteConDatosIngresados); 
		        request.setAttribute("error", "las_contraseñas_no_coinciden");
		        RequestDispatcher rd = request.getRequestDispatcher("/admin/EditarCliente.jsp");
		        rd.forward(request, response);
		        return;
		    }

		
		    Cliente clienteParaActualizarDB = new Cliente();
		    clienteParaActualizarDB.setDniCliente(dni);
		    clienteParaActualizarDB.setCuilCliente(cuil);
		    clienteParaActualizarDB.setNombreCliente(nombre);
		    clienteParaActualizarDB.setApellidoCliente(apellido);
		    clienteParaActualizarDB.setDireccionCliente(direccion);
		    clienteParaActualizarDB.setCorreoElectronicoCliente(correo);
		    clienteParaActualizarDB.setUsuarioCliente(usuario);
		    clienteParaActualizarDB.setContraseñaCliente(contrasena);
		    clienteParaActualizarDB.setSexoCliente(Sexo);
		    clienteParaActualizarDB.setNacionalidad(Nacionalidad);

		    System.out.println("DEBUG ServletClientes entro en btnAcceptar" );
		    ClienteDaoImpl cDao = new ClienteDaoImpl();
		    boolean seActualizo = cDao.actualizarCliente(clienteParaActualizarDB);

		   
		    if (!seActualizo) {
		        request.setAttribute("cliente", clienteParaActualizarDB); 
		        request.setAttribute("error", "insert_fallido");
		        RequestDispatcher rd = request.getRequestDispatcher("admin/EditarCliente.jsp"); 
		        rd.forward(request, response);
		        return;
		    }

		  
		    List<Cliente> listaActualizada = cDao.obtenerTodosLosClientes();
		    request.setAttribute("listaU", listaActualizada);
		    request.setAttribute("agregado", "1"); 
		    RequestDispatcher rd = request.getRequestDispatcher("admin/ModificarCliente.jsp");
		    rd.forward(request, response);
		}
		
		
		if(request.getParameter("btnEliminar") != null) {
		    String dniDarDeBaja = request.getParameter("txtDniEliminar").trim();
		    ClienteDaoImpl dao = new ClienteDaoImpl();

		    
		    List<Cliente> clientesEncontrados = dao.obtenerClienteBuscadoPorLike(dniDarDeBaja);

		    if (!dniDarDeBaja.matches("\\d{7,8}")) {
		        List<Cliente> lista = dao.obtenerTodosLosClientes();
		        request.setAttribute("listaU", lista);
		        request.setAttribute("errorMensaje2", "El DNI ingresado no es válido.");
		        
		        RequestDispatcher rd = request.getRequestDispatcher("admin/ModificarCliente.jsp");
		        rd.forward(request, response);
		        return;
		    }
		    
		    if (clientesEncontrados == null || clientesEncontrados.isEmpty()) {
		        List<Cliente> lista = dao.obtenerTodosLosClientes();
		        request.setAttribute("listaU", lista);
		        request.setAttribute("errorMensaje2", "No existe el DNI o ya fue dado de baja");
		        
		        RequestDispatcher rd = request.getRequestDispatcher("admin/ModificarCliente.jsp");
		        rd.forward(request, response);
		        return;
		    }
   	    
		    boolean seElimino = dao.borrarCliente(dniDarDeBaja);
		    List<Cliente> lista = dao.obtenerTodosLosClientes();

		    request.setAttribute("listaU", lista);
		    request.setAttribute("seElimino", seElimino);
		    
		    RequestDispatcher rd = request.getRequestDispatcher("admin/ModificarCliente.jsp");
		    rd.forward(request, response);
		}
		

	}
}

