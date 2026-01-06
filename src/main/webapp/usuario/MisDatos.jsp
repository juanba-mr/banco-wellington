<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Entidad.Cliente" %>
<%@ page import="Entidad.Cuenta" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
<link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>Mis Datos</title>
</head>
<body>
<i class="bi bi-arrow-left flecha" onclick="history.back()"></i>
<%
    String usuarioLogin = (String) session.getAttribute("nombreUsuario");
%>
<div class="flex-space">
    <h3 class="titulo info-derecha">Cliente : <%=usuarioLogin%></h3>
</div>
<div class="centrar">
    <div class="banner-titulo">
        <h1>Mis datos</h1>
    </div>
    <div class="banner-mediano2"> 
    	<%
            
            Cliente cliente = (Cliente) request.getAttribute("cliente");
            Cuenta cuenta = (Cuenta) request.getAttribute("cuenta");
        %>

        <% if (cliente != null) { %>
            <div class="info-izquierda">
                <h2>Nombre: <%= cliente.getNombreCliente() %></h2>
                <h2>Apellido: <%= cliente.getApellidoCliente() %></h2>
                <h2>DNI: <%= cliente.getDniCliente() %></h2>
                <h2>CUIL: <%= cliente.getCuilCliente() %></h2>
                <h2>Dirección: <%= cliente.getDireccionCliente() %></h2>
                <h2>Localidad:<%= cliente.getIdLocalidadCliente().getNombre() %></h2>
                <h2>Provincia:  <%= cliente.getIdProvinciaCliente().getNombre() %>  </h2>
                <h2>Correo electronico: <%= cliente.getCorreoElectronicoCliente() %></h2>
                     
              
              
                
                <%-- Aquí mostramos el CBU de la cuenta seleccionada --%>
                <% if (cuenta != null) { %>
                    <h2>CBU: <%= cuenta.getCbuCuenta() %></h2>
                    <%-- Puedes añadir más detalles de la cuenta si los necesitas, como tipo de cuenta --%>
                    <h2>Tipo de Cuenta: <%= cuenta.getTipoCuenta().getDescripcion() %></h2> <%-- Usando getIdTipoCuenta() del objeto TipoCuenta --%>
                <% } else { %>
                    <h2>No se pudo cargar la información de la cuenta asociada.</h2>
                <% } %>
            </div>
        <% } else { %>
            <%-- Mostrar mensaje de error si viene del Servlet --%>
            <% String mensajeError = (String) request.getAttribute("mensajeError");
               if (mensajeError != null && !mensajeError.isEmpty()) { %>
                   <p style="text-align: center; color: red;"><%= mensajeError %></p>
            <% } %>
        <% } %>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>