package Servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DaoImpl.ClienteDaoImpl;
import DaoImpl.CuentaDaoImpl;
import Entidad.Cliente;
import Entidad.Cuenta;

/**
 * Servlet implementation class ServletUsuarioCuentas
 */
@WebServlet("/ServletUsuarioCuentas")
public class ServletUsuarioCuentas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletUsuarioCuentas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

				 String action = request.getParameter("action");
			     System.out.println("DEBUG ServletUsuarioCuentas: Action recibida: " + action);

			        
			        if (action == null || action.isEmpty()) {			            
			            response.sendRedirect("usuario/InicioCliente.jsp"); 
			            return;
			        }
			        
			        HttpSession session = request.getSession();

	                String dniClienteLogueado = (String) session.getAttribute("dniUsuario");
	                String cbuCuentaActual = (String) session.getAttribute("cbuCuentaActual");
	                String idCuentaActual = (String) session.getAttribute("idCuentaActual");
	                
	                System.out.println("entrando a servlet estas son sus variables");

	                System.out.println(dniClienteLogueado);
	                System.out.println(cbuCuentaActual);
	                System.out.println(idCuentaActual);

			        switch (action) {
			            case "mostrarMisDatos":
			                 
			            	
			                
			                if (dniClienteLogueado != null && !dniClienteLogueado.isEmpty() &&
			                    cbuCuentaActual != null && !cbuCuentaActual.isEmpty()) {

			                    ClienteDaoImpl clienteDao = new ClienteDaoImpl();
			                    CuentaDaoImpl cuentaDao = new CuentaDaoImpl();

				                System.out.println("entrando a primer if");

			                    Cliente cliente = clienteDao.obtenerClientePorDni(dniClienteLogueado);
			                    Cuenta cuenta = cuentaDao.obtenerCuentaPorCbu(cbuCuentaActual);
			                    
			                    
			                    if (cliente != null && cuenta != null) {
			                        
			                        request.setAttribute("cliente", cliente);
			                        request.setAttribute("cuenta", cuenta);
					                System.out.println("seteando cliente");

			    	              

			                        RequestDispatcher rd = request.getRequestDispatcher("usuario/MisDatos.jsp");
			                        rd.forward(request, response);
			                    } else {
					                System.out.println("error");

			                        request.setAttribute("mensajeError", "No se encontraron sus datos o la cuenta seleccionada. Por favor, intente de nuevo.");
			                        RequestDispatcher rd = request.getRequestDispatcher("usuario/InicioCliente.jsp"); // Asegúrate de tener esta página de error
			                        rd.forward(request, response);
			                    }

			                } else {
			                    
			                    request.setAttribute("mensajeError", "Error de sesión. Por favor, inicie sesión y seleccione una cuenta.");
			                    response.sendRedirect("InicioSesion.jsp"); // Redirigir al login
			                }
			                break; // Fin del case "mostrarMisDatos"

			            // Aquí irían los otros cases para otras acciones de usuario si este Servlet las maneja
			            // case "btnTusCuentas":
			            //     // ... lógica para obtener y listar todas las cuentas del usuario ...
			            //     break;
			            // default:
			            //    response.sendRedirect("usuario/InicioCliente.jsp");
			            //    break;
			        }
				
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}