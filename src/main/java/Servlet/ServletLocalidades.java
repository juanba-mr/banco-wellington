package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DaoImpl.LocalidadDaoImpl;
import Entidad.Localidad;

/**
 * Servlet implementation class ServletLocalidades
 */
@WebServlet("/ServletLocalidades")
public class ServletLocalidades extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletLocalidades() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int idProvincia = Integer.parseInt(request.getParameter("idProvincia"));
        LocalidadDaoImpl localidadDao = new LocalidadDaoImpl();
        List<Localidad> listaLocalidades = localidadDao.obtenerLocalidadPorProvincia(idProvincia);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        for (Localidad l : listaLocalidades) {
            out.println("<option value=\"" + l.getIdLocalidad() + "\">" + l.getNombre() + "</option>");
        }

        out.close();
    	
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
