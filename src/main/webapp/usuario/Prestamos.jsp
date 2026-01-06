<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% String idCuenta = request.getParameter("idCuenta"); %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
<link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>Préstamos</title>
</head>
<body>
	<i class="bi bi-arrow-left flecha" onclick="window.location.href='<%=request.getContextPath()%>/usuario/InicioCliente.jsp'"></i>     
	<%
    String usuarioLogin = (String) session.getAttribute("nombreUsuario");
%>
<div class="flex-space">
    <h3 class="titulo info-derecha">Cliente : <%=usuarioLogin%></h3>
</div>
<div class= "centrar">
     <div class="banner-chico">
		<h1 class="info-centro separar">Préstamos</h1>
		
     	<div class="botonera">
             <button type="submit" class="boton"  onclick="window.location.href='PedirPrestamo.jsp?idCuenta=<%= idCuenta  %>'">
            <i class="bi bi-arrow-90deg-up"></i> Pedir Prestamo
            </button>
            <button type="submit" class="boton"  onclick="window.location.href='PagarPrestamo.jsp?idCuenta=<%= idCuenta  %>'">
            <i class="bi bi-arrow-90deg-up"></i> Pagar Prestamo
            </button>
         </div>
     </div>
</div>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>