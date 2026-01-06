<%@page import="Entidad.Cliente"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="Entidad.Prestamo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    List<Prestamo> prestamos = (List<Prestamo>) request.getAttribute("listarPrestamos");
    NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
<link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>Listar Prestamos</title>
</head>
<body>
<i class="bi bi-arrow-left flecha" onclick="history.back()"></i>
    <div class="centrar">
        <div class="banner-titulo">
            <h2>Listar Prestamos</h2>
        </div>

        <div class="banner-mediano2">
            <table class="tabla-clientes">
    <tr>
        <th>ID Préstamo</th>
        <th>DNI Cliente</th>
        <th>Fecha</th>
        <th>Nombre</th>
        <th>Apellido</th>
        <th>ID Cuenta</th>
        <th>Importe Total</th>
        <th>Importe Mensual</th>
        <th>Cuotas</th>
        <th>Estado</th>
    </tr>

<%
    if (prestamos != null) {
        for (Prestamo p : prestamos) {
            Cliente cliente = p.getIdcuenta().getDnicliente();
%>
    <tr>
        <td><%= p.getIdPrestamo() %></td>
        <td><%= cliente.getDniCliente() %></td>
        <td><%= p.getFechaSolicitud() %></td>
        <td><%= cliente.getNombreCliente() %></td>
        <td><%= cliente.getApellidoCliente() %></td>
        <td><%= p.getIdcuenta().getIdCuenta() %></td>
        <td><%= p.getImporteTotal() %></td>
        <td><%= p.getImporteMensual() %></td>
        <td><%= p.getCantidadCuotas() %></td>
        <td><%= p.getEstado() %></td>
    </tr>
<%
        }
    } else {
%>
    <tr><td colspan="9">No se encontraron préstamos.</td></tr>
<%
    }
%>
</table>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>