package Servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DaoImpl.ClienteDaoImpl;
import DaoImpl.CuentaDaoImpl;
import DaoImpl.MovimientoDaoImpl;
import Entidad.Acceso;
import Entidad.Cliente;
import Entidad.Cuenta;
import Entidad.Movimiento;
import Entidad.TipoCuenta;
import Entidad.TipoMovimiento;

/**
 * Servlet implementation class ServletCuentas
 */
@WebServlet("/ServletCuentas")
public class ServletCuentas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCuentas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String action = request.getParameter("action"); 
        System.out.println("DEBUG ServletCuentas: Action recibida: " + action);

        //CONTROLAMOS ERRORES:
        
        if (action == null || action.isEmpty()) {
            action= request.getParameter("accion");

        }
        
        switch(action) {
        case "Alta Cuenta":{
        	System.out.println("DEBUG ServletCuentas: Redirigiendo a formulario AltaCuenta.jsp");
        	
        	RequestDispatcher rd = request.getRequestDispatcher("admin/AltaCuenta.jsp");			
			rd.forward(request, response);
			break;
        }
        case "crearCuenta":{////////////////////////////////////////////////////////////////////////////////////////
        	String mensajeResultado;
			String dniParam =request.getParameter("txtDni");
			String tipoCuentaParam =request.getParameter("dllTipoCuenta");
			CuentaDaoImpl cuentaDao = new CuentaDaoImpl();
        	boolean tienemasde3 = false;
			
        	tienemasde3 = cuentaDao.Maximo3Cuenta(dniParam);
        	
            if (dniParam == null || dniParam.trim().isEmpty()) { 
            	mensajeResultado= "Error: El dni del cliente esta vacio";		
            } else if (tipoCuentaParam == null || tipoCuentaParam.trim().isEmpty() || tipoCuentaParam.equals("-- Seleccione un tipo de cuenta --")) {
            	mensajeResultado= "Error: Debe seleccionar un tipo de cuenta valido";
            }else if(dniParam.length()!=8){
            	
            	mensajeResultado= "Error: el Dni debe tener 8 caracteres";

            }else if(tienemasde3) {
            	mensajeResultado= "Error: Este Dni ya tiene 3 cuentas Activas";
            }
            else {
                try {
                    Cuenta cuenta = new Cuenta();
                    Cliente cliente =new Cliente();
                    TipoCuenta TCuenta= new TipoCuenta();
                    
                    cliente.setDniCliente(dniParam); 
                    cuenta.setDniCliente(cliente); 
                    TCuenta.setIdTipoCuenta(tipoCuentaParam);
                    System.out.println(tipoCuentaParam);
                    if(tipoCuentaParam=="1") {
                    	TCuenta.setDescripcion("corriente");
                    	
                    }else if(tipoCuentaParam=="2") {
                    	TCuenta.setDescripcion("ahorros");
                    	
                    }
                    cuenta.setTipoCuenta(TCuenta);
                    mensajeResultado = cuentaDao.insertarCuenta(cuenta);   
                    
                } catch (NumberFormatException e) {
                    mensajeResultado = "Error: El DNI debe ser un número válido.";
                    e.printStackTrace();
                } catch (Exception e) {
                    mensajeResultado = "Error inesperado al crear la cuenta: " + e.getMessage();
                    e.printStackTrace();
                }
            }
			
			request.setAttribute("mensajeResultado", mensajeResultado);
			
			RequestDispatcher rdCrear = request.getRequestDispatcher("admin/AltaCuenta.jsp");
			rdCrear.forward(request, response);
			break;
        }
        case"Modificar Cuentas":{ ////////////////////////////////////////////////////////////////////////////////////////
            CuentaDaoImpl cuentaDaoList = new CuentaDaoImpl(); 
            List<Cuenta> listaCuentasModificar;

            try {
                
                 
            
                        listaCuentasModificar = cuentaDaoList.obtenerTodasLasCuentas();
                  
            
                request.setAttribute("listaCuentas", listaCuentasModificar);
                RequestDispatcher rdModificar = request.getRequestDispatcher("admin/ModificarCuentas.jsp");
                rdModificar.forward(request, response);
                
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("mensajeError", "Error al listar cuentas para modificar.");
                RequestDispatcher rdError = request.getRequestDispatcher("error.jsp"); // Asegúrate de tener un error.jsp
                rdError.forward(request, response);
            }
            break;
        }
			
        case"Listar Cuentas":{////////////////////////////////////////////////////////////////////////////////////////
        	String filtro = request.getParameter("filtro");
        	CuentaDaoImpl cuenta = new CuentaDaoImpl();
        	List<Cuenta> listaCuentas;
        	System.out.println(filtro);

        	try {
        		if (filtro != null && !filtro.equals("") && !filtro.equals("mayor") && !filtro.equals("menor")) {
        		    System.out.println("Filtro por tipo de cuenta: " + filtro);
        		    listaCuentas = cuenta.obtenerPorTipoDeCuenta(filtro);
        		} else if ("mayor".equals(filtro)) {
        		    System.out.println("Ordenando por saldo de mayor a menor");
        		    listaCuentas = cuenta.obtenerOrdenadoSaldoMayorAMenor();
        		} else if ("menor".equals(filtro)) {
        		    System.out.println("Ordenando por saldo de menor a mayor");
        		    listaCuentas = cuenta.obtenerOrdenadoSaldoMenorAMayor();
        		} else {
        		    System.out.println("Sin filtro, trayendo todas las cuentas");
        		    listaCuentas = cuenta.obtenerTodasLasCuentas();
        		}
        		System.out.println("listar cuentas");
        		System.out.println("aca estoy");
        	    request.setAttribute("listaCuentas", listaCuentas);
        	    RequestDispatcher rdListar = request.getRequestDispatcher("admin/ListarCuentas.jsp");
        	    rdListar.forward(request, response);
             } catch (Exception e) {
             e.printStackTrace();
             response.sendRedirect("error.jsp");
             }
             break;
        }
        case "btnTusCuentas":{
        	HttpSession session = request.getSession(false);
            String cbu = request.getParameter("cbu");
            if (session == null || session.getAttribute("clienteLogueado") == null) {
                response.sendRedirect("InicioSesion.jsp");  // Redirigí al login
                return;
            }

            Acceso acceso = (Acceso) session.getAttribute("clienteLogueado");
            String dni = acceso.getCliente().getDniCliente();

            if (cbu != null && acceso != null) {
                CuentaDaoImpl dao = new CuentaDaoImpl();
                List<Cuenta> cuentasFiltradas = dao.obtenerCuentasFiltro(dni, cbu);

                request.setAttribute("cuentas", cuentasFiltradas);
                request.setAttribute("cbuActual", cbu);
                RequestDispatcher rdDispatcher = request.getRequestDispatcher("usuario/TusCuentas.jsp");
                rdDispatcher.forward(request, response);
            } else {
                response.sendRedirect("InicioSesion.jsp");
            }
            break;
        }
        case "btnTransferir":{
        	String cbuOrigen = request.getParameter("cbuOrigen");
            String cbuDestino = request.getParameter("cbuDestino");
            String montoStr = request.getParameter("monto");

            String montoStrLimpio = montoStr.replaceAll("[^\\d,\\.]", "").replace(".", "").replace(",", ".");

            try {
                double monto = Double.parseDouble(montoStrLimpio);

                if (monto <= 0) {
                    request.setAttribute("mensajeResultado", "El monto debe ser mayor a cero.");
                    request.getRequestDispatcher("/usuario/CuantoTransferir.jsp").forward(request, response);
                    return;
                }
                
                CuentaDaoImpl cuentaDao = new CuentaDaoImpl();
                Cuenta cuentaOrigen = cuentaDao.obtenerCuentaPorCbu(cbuOrigen);
                Cuenta cuentaDestino = cuentaDao.obtenerCuentaPorCbu(cbuDestino);
                ClienteDaoImpl clienteImpl = new ClienteDaoImpl();
                if (cuentaOrigen == null || cuentaDestino == null) {
                    request.setAttribute("mensajeResultado", "Una de tus cuentas no fue encontrada.");
                    request.getRequestDispatcher("/usuario/CuantoTransferir.jsp").forward(request, response);
                    return;
                }

                if (cuentaOrigen.getSaldoCuenta() < monto) {
                    request.setAttribute("mensajeResultado", "Saldo insuficiente.");
                    request.getRequestDispatcher("/usuario/CuantoTransferir.jsp").forward(request, response);
                    return;
                }

                // Datos del cliente desde la sesión
                Cliente cliente = clienteImpl.obtenerClientePorDni(cuentaDestino.getDnicliente().getDniCliente());
                
                // Mandar a página de confirmación
                request.setAttribute("monto", monto);
                request.setAttribute("nombreDestino", cliente.getNombreCliente());
                request.setAttribute("apellidoDestino", cliente.getApellidoCliente());
                request.setAttribute("cbuDestino", cuentaDestino.getCbuCuenta());
                request.setAttribute("cuilDestino", cliente.getCuilCliente());
                request.setAttribute("cbuOrigen", cuentaOrigen.getCbuCuenta());

                request.getRequestDispatcher("/usuario/EstaTodoCorrecto.jsp").forward(request, response);

            } catch (NumberFormatException e) {
                request.setAttribute("mensajeResultado", "Monto inválido.");
                request.getRequestDispatcher("/usuario/CuantoTransferir.jsp").forward(request, response);
            }
            break;            
        }
        case "btnAceptarTransferencia":{
        	String cbuOrigenConfirm = request.getParameter("cbuOrigen");
            String cbuDestinoConfirm = request.getParameter("cbuDestino");
            String montoConfirmStr = request.getParameter("monto");
            System.out.println("cuenta origen y cuenta destino: "+cbuOrigenConfirm+" "+cbuDestinoConfirm );
            try {
                double montoConfirm = Double.parseDouble(montoConfirmStr);

                CuentaDaoImpl cuentaDao = new CuentaDaoImpl();
                MovimientoDaoImpl movimientoImpl = new MovimientoDaoImpl();
                
                Cuenta cuentaOrigen = cuentaDao.obtenerCuentaPorCbu(cbuOrigenConfirm);
                Cuenta cuentaDestino = cuentaDao.obtenerCuentaPorCbu(cbuDestinoConfirm);

                if (cuentaOrigen != null && cuentaDestino != null && cuentaOrigen.getSaldoCuenta() >= montoConfirm) {
                    cuentaOrigen.setSaldoCuenta(cuentaOrigen.getSaldoCuenta() - montoConfirm);
                    cuentaDestino.setSaldoCuenta(cuentaDestino.getSaldoCuenta() + montoConfirm);

                    cuentaDao.actualizarSaldo(cuentaOrigen); 
                    cuentaDao.actualizarSaldo(cuentaDestino);
                    
                    Movimiento movimiento = new Movimiento();
                    LocalDateTime fecha = LocalDateTime.now();
                    Timestamp timestamp = Timestamp.valueOf(fecha);
                    movimiento.setFecha(timestamp);
                    movimiento.setCuentaOrigen(cuentaOrigen);
                    movimiento.setCuentaDestino(cuentaDestino);
                    movimiento.setMonto(montoConfirm);
                    movimiento.setTipoMovimiento(new TipoMovimiento(4,"transferencia"));
                    movimiento.setDescripcion("Transferencia Enviada");
                    System.out.println("seteando movimiento: ");
                    System.out.println(movimiento.getFecha() );
                    System.out.println(movimiento.getCuentaOrigen() );
                    System.out.println(movimiento.getCuentaDestino() );
                    System.out.println(movimiento.getMonto() );
                    System.out.println(movimiento.getTipoMovimiento() );
                    System.out.println(movimiento.getDescripcion() );
                    movimientoImpl.insertarMovimiento(movimiento);
                    
                    request.setAttribute("mensajeTransferencia", "Transferencia realizada con éxito.");
                    request.setAttribute("cbu", cuentaOrigen.getCbuCuenta());
                    request.getRequestDispatcher("/usuario/TransferenciaExitosa.jsp").forward(request, response);
                } else {
                    request.setAttribute("mensajeTransferencia", "Error: saldo insuficiente o cuenta inválida.");
                    request.getRequestDispatcher("/usuario/EstaTodoCorrecto.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("mensajeTransferencia", "Error en el monto ingresado.");
                request.getRequestDispatcher("/usuario/EstaTodoCorrecto.jsp").forward(request, response);
            }
            break;
        }
        case "verificarCbu":{
            String cbuDestino = request.getParameter("cbuDestino");
            String cbuOrigen = request.getParameter("cbuOrigen");

            CuentaDaoImpl dao = new CuentaDaoImpl();
            Cuenta cuentaDestinoObj = dao.obtenerCuentaPorCbu(cbuDestino); // Renombrá si querés evitar confusión

            if (cuentaDestinoObj != null) {
                response.sendRedirect("usuario/CuantoTransferir.jsp?cbuOrigen=" + cbuOrigen + "&cbuDestino=" + cbuDestino);
            } else {
                request.setAttribute("mensajeError", "El CBU ingresado no existe.");
                request.getRequestDispatcher("/usuario/AQuienVasATransferir.jsp").forward(request, response);
            }
            break;
        }
      }
 }	 
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("btnEliminar") != null) {

	        Boolean seElimino = false;
	        CuentaDaoImpl cuentadao = new CuentaDaoImpl();
	        String cuentaDarDeBajaStr = null; 
	        String errorMessage = null;        
	        Boolean Existe = false;
	        cuentaDarDeBajaStr = request.getParameter("CuentaEliminar");  
	        
	        Existe = cuentadao.existeCuenta(cuentaDarDeBajaStr);

	        try {
	                
	            if (cuentaDarDeBajaStr == null || cuentaDarDeBajaStr.trim().isEmpty()) {
	                errorMessage = "Por favor, ingrese un número de cuenta para eliminar.";
	            } else {	                	                              
	                if (cuentaDarDeBajaStr.length() != 10) {
	                    errorMessage = "Una ID tiene 10 digitos";
	                }else { 
	                	if (!Existe) {	                   	                
	                		errorMessage = "No existe Id Cuenta o se dio de baja";
	                } else {
	                	seElimino = cuentadao.borrarCuenta(cuentaDarDeBajaStr); 
	                }
	            }
		}

	        } catch (NumberFormatException e) {
	            
	            errorMessage = "El valor ingresado para la cuenta no es un número válido.";
	           
	        } catch (Exception e) {
	          
	            errorMessage = "Ocurrió un error inesperado al procesar la solicitud: " + e.getMessage();
	            
	        }

	       
	        if (errorMessage != null) {
	            request.setAttribute("errorMensaje", errorMessage); 
	        }

	       
	        List<Cuenta> lista = cuentadao.obtenerTodasLasCuentas();
	        request.setAttribute("listaCuentas", lista);

	        
	        if (errorMessage == null) {
	            request.setAttribute("seElimino", seElimino);
	        }

	      
	        RequestDispatcher rd = request.getRequestDispatcher("/admin/ModificarCuentas.jsp");
	        rd.forward(request, response);
		}
		
		
		if (request.getParameter("BtnEditar") != null) {
		
		    String id_cuenta = request.getParameter("idCuenta");
		    String dni_cliente = request.getParameter("dniCliente");
		    String tipocuenta = request.getParameter("tipoCuenta");
		    Cuenta cuenta =new Cuenta();
		    Cliente cliente = new Cliente();
		    TipoCuenta TCuenta= new TipoCuenta();
		    
		    cuenta.setIdCuenta(id_cuenta);
		    cliente.setDniCliente(dni_cliente);
		    cuenta.setDnicliente(cliente);
		    TCuenta.setIdTipoCuenta(tipocuenta);
		    if(tipocuenta=="1") {
            	TCuenta.setDescripcion("corriente");
            	
            }else if(tipocuenta=="2") {
            	TCuenta.setDescripcion("ahorros");
            	
            }

		    cuenta.setTipoCuenta(TCuenta);
		    
		    request.setAttribute("cuenta", cuenta);
		    RequestDispatcher rd = request.getRequestDispatcher("admin/EditarCuenta.jsp");
		    rd.forward(request, response);
		}
		
		

		if(request.getParameter("btnBuscar") != null){
		    String id = request.getParameter("txtId");
		    System.out.println(id);
		    
		    CuentaDaoImpl cDao = new CuentaDaoImpl();
		    List<Cuenta> lista = cDao.obtenerCuentaBuscadoPorLike(id);
		    
		    request.setAttribute("listaCuentas", lista);

		    if (lista.isEmpty()) {
		        request.setAttribute("errorMensaje", "No se encontraron cuentas en base a tu busqueda");
		    }

		    RequestDispatcher rd = request.getRequestDispatcher("admin/ModificarCuentas.jsp");
		    rd.forward(request, response);
		}
		 
		 if(request.getParameter("btnRestablecer")!=null){
				
				CuentaDaoImpl cDao = new CuentaDaoImpl();
			
		        List<Cuenta> lista = cDao.obtenerTodasLasCuentas();

		        request.setAttribute("listaCuentas", lista);

		        RequestDispatcher rd = request.getRequestDispatcher("admin/ModificarCuentas.jsp");
		        rd.forward(request, response);
				}
		
		if (request.getParameter("btnAcceptar") != null) {

		    String id = request.getParameter("idCuenta");
		    String dni = request.getParameter("DniCliente");

		    String tipocuenta = request.getParameter("ddlTipoCuenta");
		    Cuenta cuenta = new Cuenta();

		    Cliente cliente = new Cliente();
		    TipoCuenta TCuenta= new TipoCuenta();    
		    CuentaDaoImpl cuentadao= new CuentaDaoImpl();
		    
		    cuenta.setIdCuenta(id);
		    cliente.setDniCliente(dni);
		    cuenta.setDnicliente(cliente);  
            TCuenta.setIdTipoCuenta(tipocuenta);
        

            if(tipocuenta=="1") {
            	TCuenta.setDescripcion("corriente");
            }else if(tipocuenta=="2") {
            	TCuenta.setDescripcion("ahorros");

            }

		   
		    cuenta.setTipoCuenta(TCuenta);
        	


		    
		    cuentadao.modificarCuenta(cuenta);
		    List<Cuenta> lista = cuentadao.obtenerTodasLasCuentas();

		    request.setAttribute("listaCuentas", lista);
		    request.setAttribute("cuenta", cuenta);
		    RequestDispatcher rd = request.getRequestDispatcher("admin/ModificarCuentas.jsp");
		    rd.forward(request, response);
		}
		
if (request.getParameter("btnIngresarDinero") != null) {
            
		    String idCuentaStr = request.getParameter("idCuenta");
		    String montoStr = request.getParameter("monto");
		    String montoStrLimpio = montoStr.replaceAll("[^\\d,\\.]", "").replace(".", "").replace(",", ".");

		    try {
		        double monto = Double.parseDouble(montoStrLimpio);

		        if (monto <= 0 || monto > 500000) {
		            request.setAttribute("mensajeResultado", "El monto debe estar entre $1 y $500.000");
		            request.getRequestDispatcher("usuario/IngresarDinero.jsp?idCuenta=" + idCuentaStr).forward(request, response);
		            return;
		        }

		        CuentaDaoImpl cuentaDao = new CuentaDaoImpl();
	            MovimientoDaoImpl movimientoImpl = new MovimientoDaoImpl(); 
		        boolean exito = cuentaDao.SumarSaldoACuenta(monto, idCuentaStr);

		        if (exito) {
		            Cuenta cuenta = cuentaDao.obtenerCuentaPorId(idCuentaStr);
		            Movimiento movimiento = new Movimiento();
                    LocalDateTime fecha = LocalDateTime.now();
                    Timestamp timestamp = Timestamp.valueOf(fecha);
                    movimiento.setFecha(timestamp);
                    movimiento.setMonto(monto);
                    movimiento.setCuentaOrigen(cuenta);
                    movimiento.setCuentaDestino(cuenta);
                    movimiento.setTipoMovimiento(new TipoMovimiento(1,"alta_cuenta"));
                    movimiento.setDescripcion("Ingreso Dinero");
                    movimientoImpl.insertarMovimiento(movimiento);
		            response.sendRedirect("usuario/InicioCliente.jsp?cbu=" + cuenta.getCbuCuenta());
		            return;
		        } else {
		            request.setAttribute("mensajeResultado", "Error al ingresar saldo.");
		        }

		    } catch (NumberFormatException e) {
		        request.setAttribute("mensajeResultado", "Formato de monto invÃ¡lido.");
		        e.printStackTrace();
		    }  
		    request.getRequestDispatcher("usuario/IngresarDinero.jsp?idCuenta=" + idCuentaStr).forward(request, response);
		}
	}
}

