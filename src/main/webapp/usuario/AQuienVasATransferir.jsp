<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="Entidad.Cuenta" %>
<%@ page import="Entidad.Acceso" %>
<%
    Acceso acceso = (Acceso) session.getAttribute("clienteLogueado");
    if (acceso == null) {
        response.sendRedirect("InicioSesion.jsp");
        return;
    }

    String cbuOrigen = request.getParameter("cbu");
%>


<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
<link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>A Quien le vas a Transferir</title>
</head>
<body>
<i class="bi bi-arrow-left flecha" onclick="window.location.href='<%=request.getContextPath()%>/usuario/TransferirPesos.jsp'"></i>
<%
    String usuarioLogin = (String) session.getAttribute("nombreUsuario");
%>
<div class="flex-space">
    <h3 class="titulo info-derecha">Cliente : <%=usuarioLogin%></h3>
</div>
<div class="contenedor-centro" >
    <div class="caja">
        <h1>¿A quien le vas a transferir?</h1>

        <form method="get" action="${pageContext.request.contextPath}/ServletCuentas">
        <input type="hidden" name="action" value="verificarCbu">
        <input type="hidden" name="cbuOrigen" value="<%= cbuOrigen %>">
        <div class="titulo">
        <label for="cbuDestino">Ingrese el CBU</label>
        <input class="input-texto" type="text" name="cbuDestino" placeholder="CBU" required>
        </div>
        <button type="submit" class="boton">Validar</button>
    </form>
    
    <% if (request.getAttribute("mensajeError") != null) { %>
        <p style="color:red;"><%= request.getAttribute("mensajeError") %></p>
    <% } %>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>