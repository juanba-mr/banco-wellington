<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Entidad.Cuenta" %>
<%List<Cuenta> cuentas = (List<Cuenta>) request.getAttribute("cuentas");%>
<% String cbu = (String) request.getAttribute("cbuActual"); %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
<link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>Tus Cuentas</title>
</head>
<body>
	<i class="bi bi-arrow-left flecha" onclick="window.location.href='<%=request.getContextPath()%>/usuario/TransferirPesos.jsp'"></i>
	<%
    String usuarioLogin = (String) session.getAttribute("nombreUsuario");
%>
<div class="flex-space">
    <h3 class="titulo info-derecha">Cliente : <%=usuarioLogin%></h3>
</div>
	<div class="contenedor-centro">
		<div class="banner-titulo2">
		<h1>Tus otras Cuentas</h1>
		</div>	

	<div class="banner-mediano">
	<% if (cuentas != null && !cuentas.isEmpty()) { 
       for (Cuenta cuenta : cuentas) { %>

    <div class="banner-items" onclick="window.location.href='usuario/CuantoTransferir.jsp?cbuOrigen=<%= cbu %>&cbuDestino=<%= cuenta.getCbuCuenta() %>'">
        <div class="info-izquierda">
            <h2 class="nombre"><%= cuenta.getTipoCuenta().getDescripcion().equalsIgnoreCase("corriente") ? "Cuenta Corriente" : "Cuenta de Ahorros" %></h2>
            <h4 class="descripcion">CBU <%= cuenta.getCbuCuenta() %></h4>
        </div>
        <div class="info-derecha">
            <i class="bi bi-chevron-right monto"></i>
        </div>
    </div>

	<%   } 
    } else { %>
    <p class="info-centro">No tenés otras cuentas asociadas.</p>
	<% } %>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>