package Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DaoImpl.CuentaDaoImpl;
import DaoImpl.MovimientoDaoImpl;
import Entidad.Cuenta;
import Entidad.Movimiento;

/**
 * Servlet implementation class ServletMovimientos
 */
@WebServlet("/ServletMovimientos")
public class ServletMovimientos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletMovimientos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		if (request.getParameter("btnVerMas") != null) {
		    
			String cbu = request.getParameter("cbu");

		    if (cbu != null && !cbu.isEmpty()) {
		        CuentaDaoImpl cuentaDao = new CuentaDaoImpl();
		        Cuenta cuenta = cuentaDao.obtenerCuentaPorCbu(cbu);

		        if (cuenta != null) {
		            MovimientoDaoImpl movimientoDao = new MovimientoDaoImpl();
		            List<Movimiento> movimientos = movimientoDao.obtenerMovimientosTransferenciasPorId(cuenta.getIdCuenta());

		            request.setAttribute("cuenta", cuenta);
		            request.setAttribute("movimientos", movimientos);
		            request.getRequestDispatcher("/usuario/VerMisMovimientos.jsp").forward(request, response);
		            return;
		        }
		    }
			
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
