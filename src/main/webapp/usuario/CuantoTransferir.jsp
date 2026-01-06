<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="DaoImpl.CuentaDaoImpl" %>
<%@ page import="DaoImpl.ClienteDaoImpl" %>
<%@ page import="Entidad.Cuenta" %>
<%@ page import="Entidad.Cliente" %>
<%@ page import="Entidad.Acceso" %>
    <%
String cbu = request.getParameter("cbuOrigen");
    
String cbuDestino = request.getParameter("cbuDestino");

CuentaDaoImpl cuentaDao = new CuentaDaoImpl();
Cuenta cuenta = cuentaDao.obtenerCuentaPorCbu(cbu);

Cuenta cuentaDestino = cuentaDao.obtenerCuentaPorCbu(cbuDestino);

ClienteDaoImpl clienteDao = new ClienteDaoImpl();
Cliente clienteDestino = clienteDao.obtenerClientePorDni(cuentaDestino.getDniCliente().getDniCliente());

Acceso clienteLogueado = (Acceso) session.getAttribute("clienteLogueado");

if (clienteLogueado == null) {
    response.sendRedirect("InicioSesion.jsp");
    return;
}

%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
<link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>Cuanto va a transferir</title>
</head>
<body>
<i class="bi bi-arrow-left flecha" onclick="history.back()"></i>
<%
    String usuarioLogin = (String) session.getAttribute("nombreUsuario");
%>
<div class="flex-space">
    <h3 class="titulo info-derecha">Cliente : <%=usuarioLogin%></h3>
</div>
<div class ="contenedor-centro">
    
    <div class="caja">
    <h1>¿Cuánto querés transferir a <%= clienteDestino.getNombreCliente() %> <%= clienteDestino.getApellidoCliente() %>?</h1>

    <form method="get" action="${pageContext.request.contextPath}/ServletCuentas">
    <input type="hidden" name="action" value="btnTransferir">
    <input type="hidden" name="cbuOrigen" value="<%= cuenta.getCbuCuenta() %>">
    <input type="hidden" name="cbuDestino" value="<%= request.getParameter("cbuDestino") %>">

    <div class="formulario">
        <div class="input-wrapper">
            <span class="prefix">$</span>
            <input type="text" name="monto" id="monto" oninput="formatearNumeroEuropeo(this)" value="0,00" required>
        </div>

        <%
        Locale localeAR = new Locale("es", "AR");
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(localeAR);
        String saldoFormateado = formatoMoneda.format(cuenta.getSaldoCuenta());
        %>
        <h3 class="descripcion">Dinero disponible: <%= saldoFormateado %></h3>

        <% String mensajeError = (String) request.getAttribute("mensajeResultado"); %>
        <% if (mensajeError != null) { %>
            <p style="color:red;"><%= mensajeError %></p>
        <% } %>

        <button type="submit" class="boton" name="btnTransferir"><b>Aceptar</b></button>
    </div>
</form>
    	</div>
    </div>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>