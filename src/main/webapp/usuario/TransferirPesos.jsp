<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Entidad.Cuenta" %>

<% String cbu = request.getParameter("cbu"); %>
<%
    if (cbu != null && !cbu.isEmpty()) {
        session.setAttribute("cbuCuentaActual", cbu);
    }
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
<link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>Transferir Pesos</title>
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
		<h1 class="info-centro separar">Transferir Pesos</h1>
     	<div>
     		<div class="botonera">
     		<form method="get" action="${pageContext.request.contextPath}/ServletCuentas">
     		 <input type="hidden" name="cbu" value="<%= cbu %>">
    		 <button type="submit" class="boton" name="action" value="btnTusCuentas">Tus cuentas</button>
     		</form>
     		<button type="submit" class="boton" onclick="window.location.href='AQuienVasATransferir.jsp?cbu=<%= cbu  %>'"> Nueva Cuenta </button>
     		<button type="submit" class="boton" name="action" value="btnHistorial" onclick="window.location.href='HistorialTransferencias.jsp?cbu=<%= cbu  %>'"> Historial </button>
     		</div>
     	</div>
     </div>
</div>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>