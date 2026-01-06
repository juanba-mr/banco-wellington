package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DaoImpl.AccesoDaoImpl;
import Entidad.Acceso;

/**
 * Servlet implementation class ServletAcceso
 */
@WebServlet("/ServletAcceso")
public class ServletAcceso extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAcceso() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("btnIngresar")!=null) {
			String usuario = request.getParameter("usuario");
			String password = request.getParameter("password");	
			
			AccesoDaoImpl dao = new AccesoDaoImpl();
			Acceso acceso = dao.validarLogin(usuario,password);
			
			if (acceso != null && acceso.getTipoUsuario().equals("cliente")) {
			    HttpSession session = request.getSession();
			    session.setAttribute("clienteLogueado", acceso);
			    session.setAttribute("nombreUsuario", usuario);
			    response.sendRedirect("usuario/Tarjetas.jsp");
			} else if(acceso != null && acceso.getTipoUsuario().equals("admin")) {
				HttpSession session = request.getSession();
			    session.setAttribute("adminLogueado", acceso);
			    session.setAttribute("nombreUsuario", usuario);
			    response.sendRedirect("admin/InicioAdmin.jsp");
			}
			else {
			    request.setAttribute("errorLogin", "Usuario o contraseña incorrectos");
			    request.getRequestDispatcher("usuario/InicioSesion.jsp").forward(request, response);
			}
		}
	}
}
