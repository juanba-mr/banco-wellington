<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="Entidad.Movimiento"%>
<%@page import="Entidad.Cuenta"%>
<%@page import="java.util.List"%>

<%
    Cuenta cuenta = (Cuenta) request.getAttribute("cuenta");
    List<Movimiento> movimientos = (List<Movimiento>) request.getAttribute("movimientos");
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
<link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>Ver Movimientos</title>
</head>
<body>
<i class="bi bi-arrow-left flecha" onclick="history.back()"></i>
  <div class="centrar">
    <div class="banner-titulo">
        <h1>Tus movimientos</h1>
    </div>
    <div class="banner-mediano2 info-centro ">
        <% 
        for (Movimiento m : movimientos) {
            // Armo la URL completa usando el contexto y el servlet
            String linkComprobante = contextPath + "/ComprobanteTransferenciaServlet?id=" + m.getIdMovimiento();

            // Caso especial: ingreso de dinero (origen y destino son la misma cuenta)
            if (m.getCuentaOrigen().getIdCuenta().equals(cuenta.getIdCuenta()) &&
                m.getCuentaDestino().getIdCuenta().equals(cuenta.getIdCuenta())) {
        %>
        <div class="banner-items" onclick="location.href='<%= linkComprobante %>'" style="cursor:pointer;">
            <div class="info-izquierda">
                <p class="nombre">Ingreso de Dinero</p>
                <p class="descripcion">TransacciÃ³n de ingreso</p>
            </div>
            <div class="info-derecha">
                <p class="monto recibido">+ $ <%= String.format("%.2f", m.getMonto()) %></p>
            </div>
        </div>
        <%
                continue;
            }

            // Transferencia enviada
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
        }
        %>
    </div>

 </div>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
