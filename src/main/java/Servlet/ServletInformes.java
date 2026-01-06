package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DaoImpl.ClienteDaoImpl;
import DaoImpl.CuentaDaoImpl;
import DaoImpl.MovimientoDaoImpl;
import Entidad.Cliente;
import Entidad.Cuenta;

/**
 * Servlet implementation class ServletInformes
 */
@WebServlet("/ServletInformes")
public class ServletInformes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletInformes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
	     System.out.println("get " );

	        
	        if (action == null || action.isEmpty()) {			            
	            response.sendRedirect("usuario/InicioCliente.jsp"); 
	            return;
	        }
	        
	        switch (action) {
            case "Informes":{
            	ClienteDaoImpl cliente =new ClienteDaoImpl();
            	List<Cliente> listaCuentas;
            	
            	
            	listaCuentas = cliente.obtenerSaldoTotalPorCliente();
            		
            	
            	request.setAttribute("listaCuentas", listaCuentas);
            	
            	RequestDispatcher rd = request.getRequestDispatcher("/admin/Informes.jsp");
                rd.forward(request, response);
            	
            	
            	break;
	        }
	        
	        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		    if(request.getParameter("btnEnviar") != null) {
		        System.out.println("post ");
		        String fechaIniciot = request.getParameter("fechaInicio");
		        String fechaFint = request.getParameter("fechaFin");

		        MovimientoDaoImpl dao = new MovimientoDaoImpl();
		        int cantidad = dao.contarMovimientosPorRangoFechas(fechaIniciot, fechaFint);
		        double importe = dao.contarImportePorRangoFechas(fechaIniciot, fechaFint);

		        ClienteDaoImpl clienteDao = new ClienteDaoImpl();
		        List<Cliente> listaCuentas = clienteDao.obtenerSaldoTotalPorCliente();

		        request.setAttribute("cantidad", cantidad);
		        request.setAttribute("fechaInicio", fechaIniciot);
		        request.setAttribute("fechaFin", fechaFint);
		        request.setAttribute("importe", importe);
		        request.setAttribute("listaCuentas", listaCuentas);

		        System.out.println("Cantidad " + cantidad);
		        RequestDispatcher rd = request.getRequestDispatcher("/admin/Informes.jsp");
		        rd.forward(request, response);
		        return;
		    }
		   
		}


}