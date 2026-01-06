<%@page import="Entidad.Movimiento"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="DaoImpl.CuentaDaoImpl" %>
<%@ page import="DaoImpl.MovimientoDaoImpl" %>
<%@ page import="Entidad.Cuenta" %>

<%
String cbu = request.getParameter("cbu");

// Si no vino por parámetro, intento obtenerlo desde la sesión
if (cbu == null || cbu.isEmpty()) {
    cbu = (String) session.getAttribute("cbuCuentaActual");
}

// Si sigue siendo nulo o vacío, redirige a Tarjetas
if (cbu == null || cbu.isEmpty()) {
    response.sendRedirect("Tarjetas.jsp");
    return;
}
    CuentaDaoImpl cuentaDao = new CuentaDaoImpl();
    Cuenta cuenta = cuentaDao.obtenerCuentaPorCbu(cbu);

    if (cuenta == null) {
        out.println("<h3>No se encontró la cuenta</h3>");
        return;
    }
    String dniDelCliente;
    String idCuentaStr = String.valueOf(cuenta.getIdCuenta()); 
    dniDelCliente = cuenta.getDniCliente().getDniCliente(); 
    String idCuenta = cuenta.getIdCuenta();
    
    //guarde cbu y idCuenta en el session
    session.setAttribute("cbuCuentaActual", cbu); 
    session.setAttribute("dniUsuario", dniDelCliente);
    session.setAttribute("idCuentaActual", cuenta.getIdCuenta());
    
    MovimientoDaoImpl movimientoImpl = new MovimientoDaoImpl();
    List<Movimiento> movimientos = movimientoImpl.obtenerMovimientosTransferenciasPorId(cuenta.getIdCuenta());    
    String contextPath = request.getContextPath();

%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
<link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>Inicio</title>
</head>
<body>
  <i class="bi bi-arrow-left flecha" onclick="window.location.href='<%=request.getContextPath()%>/usuario/Tarjetas.jsp'"></i>
  <%
    String usuarioLogin = (String) session.getAttribute("nombreUsuario");
%>
<div class="flex-space">
    <h3 class="titulo info-derecha">Cliente : <%=usuarioLogin%></h3>
</div>
  <div class="contenedor-centro">
  <div class="banner-titulo-numero">
  <% if(cuenta.getTipoCuenta().getIdTipoCuenta().equals("1")){ %> <h1 class="titulo">Cuenta Corriente</h1>
  <% }else{ %> <h1 class="titulo">Cuenta de Ahorros</h1> <% }%>
    <h3 class="cuenta">ID: <%= cuenta.getIdCuenta() %></h3>
  </div>
    <div class="banner-mediano">
      <h1>Saldo disponible</h1>
      <%
		Locale localeAR = new Locale("es", "AR");
		NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(localeAR);
		String saldoFormateado = formatoMoneda.format(cuenta.getSaldoCuenta());
		%>
      <h1> <%= saldoFormateado %></h1>
      
    <div class="botonera">
      
    <button type="submit" class="boton"  onclick="window.location.href='IngresarDinero.jsp?idCuenta=<%= idCuenta  %>'">
        <i class="bi bi-arrow-90deg-up"></i> Agregar
    </button>

    <button type="submit" class="boton" onclick="window.location.href='TransferirPesos.jsp?cbu=<%= cbu  %>'">
        <i class="bi bi-arrow-90deg-down"></i> Transferir
    </button>

   

    <button type="submit" class="boton" onclick="window.location.href='Prestamos.jsp?idCuenta=<%= idCuenta  %>'">
        <i class="bi bi-cash-stack"></i> Préstamos
    </button>
  
	<form method="get" action="${pageContext.request.contextPath}/ServletUsuarioCuentas">
    <button type="submit" class="boton" name="action" value="mostrarMisDatos">
    
        <i class="bi bi-person-lines-fill"></i> Mis Datos
	</button>
    </form>
    </div>
</div>

<div class="banner-mediano2 info-centro ">
  <h2>Últimos movimientos</h2>

<%
int limite = 5;
int contador = 0;
for (Movimiento m : movimientos) {
    if (contador >= limite) break;

    // Link al servlet con contexto incluido y parámetro id
    String linkComprobante = contextPath + "/ComprobanteTransferenciaServlet?id=" + m.getIdMovimiento();

    // Caso especial: ingreso de dinero
    if (m.getCuentaOrigen().getIdCuenta().equals(cuenta.getIdCuenta()) &&
        m.getCuentaDestino().getIdCuenta().equals(cuenta.getIdCuenta())) {
%>
    <div class="banner-items" onclick="location.href='<%= linkComprobante %>'" style="cursor:pointer;">
        <div class="info-izquierda">
            <p class="nombre">Ingreso de Dinero</p>
            <p class="descripcion">Transacción de ingreso</p>
        </div>
        <div class="info-derecha">
            <p class="monto recibido">+ $ <%= String.format("%.2f", m.getMonto()) %></p>
        </div>
    </div>
<%
        contador++;
        continue;
    }

    if (m.getCuentaOrigen().getIdCuenta().equals(cuenta.getIdCuenta())) {
%>
    <div class="banner-items" onclick="location.href='<%= linkComprobante %>'" style="cursor:pointer;">
        <div class="info-izquierda">
            <p class="nombre"><%= m.getCuentaDestino().getDnicliente().getNombreCliente() %> <%= m.getCuentaDestino().getDnicliente().getApellidoCliente() %></p>
            <p class="descripcion">Transferencia Enviada</p>
        </div>
        <div class="info-derecha">
            <p class="monto enviado">- $ <%= String.format("%.2f", m.getMonto()) %></p>
        </div>
    </div>
<%
    } else {
%>
    <div class="banner-items" onclick="location.href='<%= linkComprobante %>'" style="cursor:pointer;">
        <div class="info-izquierda">
            <p class="nombre"><%= m.getCuentaOrigen().getDnicliente().getNombreCliente() %> <%= m.getCuentaOrigen().getDnicliente().getApellidoCliente() %></p>
            <p class="descripcion">Transferencia Recibida</p>
        </div>
        <div class="info-derecha">
            <p class="monto recibido">+ $ <%= String.format("%.2f", m.getMonto()) %></p>
        </div>
    </div>
<%
    }
    contador++;
}
%>
<form method="get" action="${pageContext.request.contextPath}/ServletMovimientos">
  <input type="hidden" name="cbu" value="<%= cbu %>">
  <input type="submit" value="Ver más" class="boton" name="btnVerMas">
</form>
 </div>
</div>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>