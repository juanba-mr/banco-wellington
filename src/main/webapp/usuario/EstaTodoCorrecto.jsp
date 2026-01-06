<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%

    double monto = (double) request.getAttribute("monto");
    String nombreDestino = (String) request.getAttribute("nombreDestino");
    String apellidoDestino = (String) request.getAttribute("apellidoDestino");
    String cbuDestino = (String) request.getAttribute("cbuDestino");
    String cuilDestino = (String) request.getAttribute("cuilDestino");

    Locale localeAR = new Locale("es", "AR");
    NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(localeAR);
    String montoFormateado = formatoMoneda.format(monto);
    
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
<link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>Confirmación</title>
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
    <h2>¿Todo está correcto?</h2>
    <div class="banner-mediano2">
        <h3 class="info-izquierda">Vas a transferir <%= montoFormateado %> a <%= nombreDestino %> <%= apellidoDestino %></h3>
        <h4 class="info-izquierda">CBU: <%= cbuDestino %></h4>
        <h4 class="info-izquierda">CUIL: <%= cuilDestino %></h4>
    </div>

<form method="get" action="${pageContext.request.contextPath}/ServletCuentas">
    <input type="hidden" name="action" value="btnAceptarTransferencia">
    <input type="hidden" name="cbuOrigen" value="${cbuOrigen}">
    <input type="hidden" name="cbuDestino" value="${cbuDestino}">
    <input type="hidden" name="monto" value="${monto}">
    <input type="submit" class="boton" name="btnAceptarTransferencia" value="Confirmar">
</form>
	</div>
</div>

<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>