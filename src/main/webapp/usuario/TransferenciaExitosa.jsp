<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String cbu = (String) request.getAttribute("cbu");
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
<link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>Transferencia Exitosa</title>
</head>
<body>
<%
    String usuarioLogin = (String) session.getAttribute("nombreUsuario");
%>
<div class="flex-space">
    <h3 class="titulo info-derecha">Cliente : <%=usuarioLogin%></h3>
</div>
<div class ="contenedor-centro">
	<div class="caja">
	<h2>Transferencia Exitosa</h2>
	<button class="boton" onclick="window.location.href='usuario/InicioCliente.jsp?cbu=<%= cbu %>'">Volver al Menu</button>
	</div>
</div>
</body>
</html>