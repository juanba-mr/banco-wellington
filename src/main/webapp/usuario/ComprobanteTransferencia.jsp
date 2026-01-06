<%@page import="Entidad.Movimiento"%>
<%@page import="Entidad.Cuenta"%>
<%@page import="Entidad.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Movimiento mov = (Movimiento) request.getAttribute("movimiento");
%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
<link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>Comprobante de Transferencia</title>
</head>
<body>
<i class="bi bi-arrow-left flecha" onclick="history.back()"></i>
<div class="centrar">
    <div class="banner-titulo">
        <h1>Comprobante de Transferencia</h1>
    </div>

    <div class="banner-mediano2">
     <h2>Fecha:<%= mov.getFecha() %></h2>
     <h2>Monto: $<%= mov.getMonto() %></h2>
     <% if(mov.getDescripcion().equals("Transferencia Enviada")){ %>
     <h2> Descripción: <%= mov.getDescripcion()  %> </h2>
     <% }%>
     <% if(mov.getDescripcion().equals("Ingreso Dinero")){ %>
     <h2>  Descripción: <%= mov.getDescripcion()  %> </h2>
     <% }%>
      <% if(mov.getDescripcion().equals("Prestamo Recibido")){ %>
     <h2>  Descripción: <%= mov.getDescripcion()  %> </h2>
     <% }%>
     <% if(mov.getDescripcion().equals("Prestamo Pagado")){ %>
     <h2>  Descripción: <%= mov.getDescripcion()  %> </h2>
     <% }%>
     <br>
     <h2>Cuenta Origen</h2>
     <h2>ID cuenta: <%= mov.getCuentaOrigen().getIdCuenta() %></h2>
     <h2>Titular: <%= mov.getCuentaOrigen().getDnicliente().getNombreCliente() %> <%= mov.getCuentaOrigen().getDnicliente().getApellidoCliente() %></h2>
     <br>
     <h2>Cuenta Destino</h2>
     <h2>ID cuenta: <%= mov.getCuentaDestino().getIdCuenta() %></h2>
     <h2>Titular: <%= mov.getCuentaDestino().getDnicliente().getNombreCliente() %> <%= mov.getCuentaDestino().getDnicliente().getApellidoCliente() %></h2>
    </div>
</div>
</body>
</html>