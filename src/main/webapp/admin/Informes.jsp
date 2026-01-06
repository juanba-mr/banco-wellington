<%@ page import="Entidad.Cliente" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    List<Cliente> obtenerCliente = null;

    if (request.getAttribute("listaCuentas") != null) {
        obtenerCliente = (List<Cliente>) request.getAttribute("listaCuentas");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Informes y movimientos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
    <link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body>
<i class="bi bi-arrow-left flecha" onclick="window.location.href='<%=request.getContextPath()%>/admin/InicioAdmin.jsp'"></i>

<div class="centrar">
    <div class="banner-titulo">
        <h2>Informes</h2>
    </div>

    <div class="banner-mediano2">
    <div class="info-centro">
            <h3>Ranking top 10 Clientes con mayor saldo </h3>
          </div>
        <% if (obtenerCliente != null && !obtenerCliente.isEmpty()) { %>
            <table class="tabla-clientes">
                <thead>
                    <tr>
                        <th>DNI</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Saldo Total</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Cliente c : obtenerCliente) { %>
                        <tr>
                            <td><%= c.getDniCliente() %></td>
                            <td><%= c.getNombreCliente() %></td>
                            <td><%= c.getApellidoCliente() %></td>
                            <td><%= c.getSaldoTotal() %></td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <% } else { %>
            <p>No hay clientes con saldo registrado.</p>
        <% } %>
    </div>

    <div class="banner-mediano2">
    <div class="info-centro">
            <h2>Cantidad de movimientos en rangos de fechas </h2>
          </div>
        <h3>Seleccioná un rango de fechas</h3>

        <form action="ServletInformes" method="post">
            <label for="fechaInicio">Fecha de inicio:</label>
            <input type="date" id="fechaInicio" name="fechaInicio" required><br><br>

            <label for="fechaFin">Fecha de fin:</label>
            <input type="date" id="fechaFin" name="fechaFin" required><br><br>

            <input type="submit" value="Enviar" name="btnEnviar">
        </form>
        
      
          
        <% if (request.getAttribute("cantidad") != null) { %>
            <h2>Movimientos encontrados</h2>
            <p>
                Desde: <strong><%= request.getAttribute("fechaInicio") %></strong><br>
                Hasta: <strong><%= request.getAttribute("fechaFin") %></strong><br>
                Cantidad de movimientos: <strong><%= request.getAttribute("cantidad") %></strong><br>
                Importe Total: <strong><%= request.getAttribute("importe") %></strong>
            </p>
        <% } %>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>