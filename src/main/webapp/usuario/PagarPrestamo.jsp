<%@page import="DaoImpl.CuentaDaoImpl"%>
<%@page import="Entidad.Cuenta"%>
<%@page import="Entidad.Prestamo"%>
<%@page import="DaoImpl.PrestamoDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String idCuenta = request.getParameter("idCuenta");

    PrestamoDaoImpl prestamoImpl = new PrestamoDaoImpl();
    CuentaDaoImpl cuentaImpl = new CuentaDaoImpl();

    Prestamo prestamo = prestamoImpl.buscarPrestamoPorId(idCuenta);
    Cuenta cuenta = cuentaImpl.obtenerCuentaPorId(idCuenta);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pagar Préstamo</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
    <link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body>
<i class="bi bi-arrow-left flecha" onclick="history.back()"></i>

<div class="centrar">
    <div class="banner-titulo">
        <h1>Pagar tu Préstamo</h1>
    </div>

    <div class="banner-mediano2"> 
        <div class="info-centro">
            <% if (request.getAttribute("mensaje") != null) { %>
                <p style="color: red; font-weight: bold;"><%= request.getAttribute("mensaje") %></p>
            <% } %>

            <% if (prestamo != null) { %>
                <h2>Total restante: $<%= String.format("%.2f", prestamo.getImporteMensual() * (prestamo.getCantidadCuotas() - prestamo.getCuotasPagadas())) %></h2> 
                <h2>Importe por cuota: $<%= String.format("%.2f", prestamo.getImporteMensual()) %></h2>
                <h4>Dinero disponible en cuenta: $<%= String.format("%.2f", cuenta.getSaldoCuenta()) %></h4>

                <%
                    int cuotasPagadas = prestamo.getCuotasPagadas();
                    int totalCuotas = prestamo.getCantidadCuotas();

                    for (int i = 0; i < totalCuotas; i++) {
                        if (i < cuotasPagadas) {
                %>
                    <input type="button" value="Cuota <%= i + 1 %> - PAGADA" class="boton-pagado" disabled>
                <%
                        } else if (i == cuotasPagadas) {
                %>
                    <form action="${pageContext.request.contextPath}/ServletPrestamos" method="get" style="display: inline;">
                        <input type="hidden" name="action" value="pagarCuota">
                        <input type="hidden" name="idPrestamo" value="<%= prestamo.getIdPrestamo() %>">
                        <input type="hidden" name="idCuenta" value="<%= idCuenta %>">
                        <input type="submit" value="Pagar cuota <%= i + 1 %>" class="boton">
                    </form>
                <%
                        } else {
                %>
                    <input type="button" value="Cuota <%= i + 1 %> - Debe pagar las anteriores" class="boton" disabled>
                <%
                        }
                    }
                %>
            <% } else { %>
    <p style="color: red; font-weight: bold;">No hay préstamos por pagar.</p>
    <a href="${pageContext.request.contextPath}/usuario/InicioCliente.jsp?cbu=<%= cuenta.getCbuCuenta() %>">Volver al inicio</a>

<% } %>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
