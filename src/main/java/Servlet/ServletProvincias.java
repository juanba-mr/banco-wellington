package Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DaoImpl.ProvinciaDaoImpl;
import Entidad.Provincia;

/**
 * Servlet implementation class ServletProvincias
 */
@WebServlet("/ServletProvincias")
public class ServletProvincias extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletProvincias() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if(request.getParameter("btnDarDeAlta")!=null){
    	ProvinciaDaoImpl provinciaDao = new ProvinciaDaoImpl();
    	List<Provincia> listaProvincias = provinciaDao.obtenerTodasLasProvincias();
    	request.setAttribute("provincias", listaProvincias);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("admin/AltaCliente.jsp");
    	dispatcher.forward(request, response);
    }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
