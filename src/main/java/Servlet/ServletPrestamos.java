package Servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import DaoImpl.CuentaDaoImpl;
import DaoImpl.PrestamoDaoImpl;
import Entidad.Cuenta;
import Entidad.Prestamo;

@WebServlet("/ServletPrestamos")
public class ServletPrestamos extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ServletPrestamos() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrestamoDaoImpl dao = new PrestamoDaoImpl();

        // Solicitar préstamo
        if (request.getParameter("btnPedirPrestamo") != null) {
            try {
                String idCuenta = request.getParameter("idCuenta");
                String montoParam = request.getParameter("monto").replace(",", ".").replace(" ", "");
                double montoOriginal = Double.parseDouble(montoParam);
                double totalConInteres = montoOriginal * 1.10;
                int cuotas = Integer.parseInt(request.getParameter("cuotas"));
                double mensual = totalConInteres / cuotas;

                Prestamo prestamo = new Prestamo();
                CuentaDaoImpl daoCuenta = new CuentaDaoImpl();
                Cuenta cuentaCompleta = daoCuenta.obtenerCuentaPorId(idCuenta);
                String cbu = cuentaCompleta.getCbuCuenta();

                Cuenta cuenta = new Cuenta();
                cuenta.setIdCuenta(idCuenta);
                prestamo.setIdcuenta(cuenta);
                prestamo.setFechaSolicitud(new Date());
                prestamo.setImporteTotal(totalConInteres);
                prestamo.setCantidadCuotas(cuotas);
                prestamo.setImporteMensual(mensual);
                prestamo.setMontoSolicitado(montoOriginal);
                prestamo.setEstado("pendiente");

                boolean existePrestamoActivo = dao.existePrestamoPendienteOAceptado(idCuenta);

                if (existePrestamoActivo) {
                    request.setAttribute("mensaje", "Ya tenés un préstamo pendiente o activo. No podés solicitar otro hasta finalizarlo.");
                    request.getRequestDispatcher("usuario/PedirPrestamo.jsp").forward(request, response);
                    return;
                }

                boolean exito = dao.insertarPrestamo(prestamo);

                if (exito) {
                    response.sendRedirect("usuario/InicioCliente.jsp?cbu=" + cbu);
                } else {
                    request.setAttribute("mensaje", "Error al solicitar el préstamo.");
                    request.getRequestDispatcher("usuario/PedirPrestamo.jsp").forward(request, response);
                }

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("mensaje", "Error inesperado.");
                request.getRequestDispatcher("usuario/PedirPrestamo.jsp").forward(request, response);
            }
        }

        // Listar préstamos pendientes (admin)
        if ("listarPendientes".equals(request.getParameter("action"))) {
            List<Prestamo> lista = dao.obtenerPrestamosPendientes();
            request.setAttribute("listarPrestamos", lista);
            request.getRequestDispatcher("admin/SolicitudesDePrestamo.jsp").forward(request, response);
            return;
        }

        // Listar todos los préstamos (admin)
        if ("listarPrestamos".equals(request.getParameter("listar"))) {
            List<Prestamo> lista = dao.obtenerPrestamos();
            request.setAttribute("listarPrestamos", lista);
            request.getRequestDispatcher("admin/ListarPrestamos.jsp").forward(request, response);
            return;
        }

     // Mostrar página para pagar cuotas de un préstamo (cliente)
        if ("mostrarPagarPrestamo".equals(request.getParameter("action"))) {
            String idPrestamoStr = request.getParameter("idPrestamo");
            if (idPrestamoStr != null) {
                Prestamo prestamo = dao.buscarPrestamoPorIdString(idPrestamoStr);
                if (prestamo != null) {
                    request.setAttribute("prestamo", prestamo);
                } else {
                    request.setAttribute("mensaje", "No hay préstamos por pagar.");
                }
                request.getRequestDispatcher("usuario/PagarPrestamo.jsp").forward(request, response);
                return;
            }
            response.sendRedirect("usuario/InicioCliente.jsp");
            return;
        }
        
        System.out.println("ACTION recibido: " + request.getParameter("action"));

     // Procesar pago cuota (botón)
        if ("pagarCuota".equals(request.getParameter("action"))) {
            try {
                String idPrestamoStr = request.getParameter("idPrestamo");
                Prestamo prestamo = dao.buscarPrestamoPorIdString(idPrestamoStr);

                if (prestamo != null &&
                    (prestamo.getEstado().equalsIgnoreCase("aceptado") || prestamo.getEstado().equalsIgnoreCase("en pago"))) {

                    int pagadas = prestamo.getCuotasPagadas();
                    int totalCuotas = prestamo.getCantidadCuotas();

                    double saldoDisponible = prestamo.getIdcuenta().getSaldoCuenta();
                    double montoCuota = prestamo.getImporteMensual();

                    // ✅ PONER LA DEPURACIÓN AQUÍ:
                    System.out.println("====== DEBUG ======");
                    System.out.println("Saldo disponible: " + saldoDisponible);
                    System.out.println("Monto cuota: " + montoCuota);
                    System.out.println("Cuotas pagadas: " + pagadas);
                    System.out.println("Total cuotas: " + totalCuotas);
                    System.out.println("Estado del préstamo: " + prestamo.getEstado());
                    System.out.println("===================");

                    if (pagadas < totalCuotas) {
                        if (saldoDisponible >= montoCuota) {
                            boolean actualizado = dao.pagarCuota(prestamo.getIdPrestamo(), montoCuota);
                            System.out.println("pagarCuota retornó: " + actualizado);
                            
                            if (actualizado) {
                                request.setAttribute("mensaje", "Cuota " + (pagadas + 1) + " pagada correctamente.");
                                prestamo = dao.buscarPrestamoPorIdString(idPrestamoStr);
                                request.setAttribute("prestamo", prestamo);
                            } else {
                                request.setAttribute("mensaje", "No se pudo procesar el pago.");
                            }
                        } else {
                            request.setAttribute("mensaje", "Saldo insuficiente para pagar la cuota.");
                        }
                    } else {
                        request.setAttribute("mensaje", "Ya has pagado todas las cuotas.");
                    }

                } else {
                    request.setAttribute("mensaje", "El préstamo no está en estado válido para pagar.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("mensaje", "Error al intentar pagar la cuota.");
            }

            request.getRequestDispatcher("usuario/PagarPrestamo.jsp").forward(request, response);
            return;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("idPrestamo");

        if (idStr != null) {
            try {
                int idPrestamo = Integer.parseInt(idStr);
                PrestamoDaoImpl dao = new PrestamoDaoImpl();

                if (request.getParameter("btnAceptar") != null) {
                    dao.actualizarEstadoPrestamo(idPrestamo, "aceptado");
                } else if (request.getParameter("btnRechazar") != null) {
                    dao.actualizarEstadoPrestamo(idPrestamo, "rechazado");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin/InicioAdmin.jsp");
    }
}