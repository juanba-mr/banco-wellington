<%@page import="Entidad.Cuenta"%>
<%@page import="Entidad.Movimiento"%>
<%@page import="Entidad.Cliente"%>
<%@page import="java.util.List"%>
<%@page import="DaoImpl.MovimientoDaoImpl"%>
<%@page import="DaoImpl.CuentaDaoImpl"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
MovimientoDaoImpl movimientoImpl = new MovimientoDaoImpl(); 
CuentaDaoImpl cuentaImpl = new CuentaDaoImpl();


String cbu = request.getParameter("cbu"); 
Cuenta cuenta = cuentaImpl.obtenerCuentaPorCbu(cbu);
Cuenta cuentaDestino = null;

List <Movimiento> movimientos = movimientoImpl.obtenerMovimientosTransferenciaEnviadaPorId(cuenta.getIdCuenta());

%>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
<link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>Historial de Transacciones</title>
</head>
<body>
<i class="bi bi-arrow-left flecha" onclick="window.location.href='<%=request.getContextPath()%>/usuario/TransferirPesos.jsp'"></i>
<%
    String usuarioLogin = (String) session.getAttribute("nombreUsuario");
%>
<div class="flex-space">
    <h3 class="titulo info-derecha">Cliente : <%=usuarioLogin%></h3>
</div>
	<div class="contenedor-centro2">
 		<div class="banner-titulo2">
		<h1>Historial</h1>
		</div>	
		
		<div class="banner-mediano2"> 
		<% 
for (Movimiento m : movimientos) {
    cuentaDestino = cuentaImpl.obtenerCuentaPorId(m.getCuentaDestino().getIdCuenta());
    if (cuentaDestino != null && cuentaDestino.getDnicliente() != null) {
%>
    <div class="banner-items" onclick="window.location.href='CuantoTransferir.jsp?cbuOrigen=<%= cbu %>&cbuDestino=<%= cuentaDestino.getCbuCuenta() %>'">
        <div class="info-izquierda">
        <%
    	SimpleDateFormat formatoFechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String fechaHoraFormateada = formatoFechaHora.format(m.getFecha());
		%>
            <h5 class="descripcion-fecha"><%= fechaHoraFormateada %></h5>
            <h4 class="nombre2">
                <%= cuentaDestino.getDnicliente().getNombreCliente() %> <%= cuentaDestino.getDnicliente().getApellidoCliente() %>
            </h4>
            <h5 class="descripcion">CBU: <%= cuentaDestino.getCbuCuenta() %></h5>
        </div>
        <div class="info-derecha">
            <p class="monto">$<%= String.format("%.2f", m.getMonto()) %></p>
        </div>
    </div>
	<% 
    	}
	}
	%>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>