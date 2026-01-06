
package Servlet;

import DaoImpl.MovimientoDaoImpl;
import Entidad.Movimiento;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/ComprobanteTransferenciaServlet")
public class ComprobanteTransferenciaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    MovimientoDaoImpl movDao = new MovimientoDaoImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        System.out.println("ID recibido en servlet: " + idStr);
        if (idStr == null) {
            System.out.println("El parámetro id es null");
            response.sendRedirect("error.jsp");
            return;
        }
        int id = 0;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println("El parámetro id no es un número válido: " + idStr);
            response.sendRedirect("error.jsp");
            return;
        }

        Movimiento mov = movDao.obtenerTransferenciaPorId(id);
        System.out.println("Movimiento obtenido: " + mov);

        if (mov != null) {
            request.setAttribute("movimiento", mov);
            request.getRequestDispatcher("/usuario/ComprobanteTransferencia.jsp").forward(request, response);
        } else {
            System.out.println("No se encontró el movimiento con id: " + id);
            response.sendRedirect("error.jsp");
        }
    }
}