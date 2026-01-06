<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="DaoImpl.CuentaDaoImpl"%>
<%@page import="Entidad.Cuenta"%>
<%@page import="DaoImpl.MovimientoDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String mensaje = (String) request.getAttribute("mensaje"); 

String idCuenta = request.getParameter("idCuenta"); 
CuentaDaoImpl CuentaImpl = new CuentaDaoImpl();
 
Cuenta cuenta = CuentaImpl.obtenerCuentaPorId(idCuenta);
Double saldoCuotas = cuenta.getSaldoCuenta();

Locale localeAR = new Locale("es", "AR");
NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(localeAR);
double monto1 = ((((saldoCuotas * 2)/2)/2)/2);
double monto2 = (((saldoCuotas * 2)/2)/2);
double monto3 = ((saldoCuotas * 2)/2);
double monto4 = (saldoCuotas * 2);

String saldoFormateado1 = formatoMoneda.format(monto4);
String saldoFormateado2 = formatoMoneda.format(monto3);
String saldoFormateado3 = formatoMoneda.format(monto2);
String saldoFormateado4 = formatoMoneda.format(monto1);
%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
<link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

<meta charset="UTF-8">
<title>Pedir Prestamo</title>
</head>
<body>
<i class="bi bi-arrow-left flecha" onclick="window.location.href='<%=request.getContextPath()%>/usuario/Prestamos.jsp'"></i>
<div class="centrar">
    <div class="banner-titulo">
        <h1>Pedir Prestamo</h1>
    </div>
    
    <% if (mensaje != null) { %>
        <%= mensaje  %>
      
<% } %>
    <div class="banner-mediano2"> 
    <form method="get" action="${pageContext.request.contextPath}/ServletPrestamos">
        <div class="banner-mediano2"> 
            <div class="flex-space">
            <h2>Monto a solicitar</h2>
            <select id="montoSeleccionado" name="monto" class="input-ddl">
                <option value="<%= monto1 %>"><%= saldoFormateado4 %></option>
				<option value="<%= monto2 %>"><%= saldoFormateado3 %></option>
				<option value="<%= monto3 %>"><%= saldoFormateado2 %></option>
				<option value="<%= monto4 %>"><%= saldoFormateado1 %></option>
            </select>
            </div>
        <div class="flex-space">
            <h2>Cuotas a pagar</h2>
            <select id="cuotasSeleccionado" name="cuotas" class="input-ddl">
                <option value="3">3</option>
                <option value="6">6</option>
                <option value="9">9</option>
                <option value="12">12</option>
            </select>
        </div>
    </div>
        
    <div class="banner-mediano2">
        <div>
            <div class="flex-space">
            <h2>Cuota mensual</h2>
            <input type="text" id="cuotaMensual" name="cuotaMensual" value="0" readonly>
            </div>
        </div>
            <div class="flex-space">
            <h2>Total a devolver</h2>
            <input type="text" id="totalDevolver" name="inputTotalADevolver" value="0" readonly>
            </div>
        </div>
        <div class="info-centro">
        <input type="hidden" name="idCuenta" value="<%= idCuenta %>">
        <input type="submit" value="Enviar Solicitud" name="btnPedirPrestamo" class="boton">
        </div>
      </form>
    </div>
</div>
<script>
document.addEventListener("DOMContentLoaded", function () {
    const montoSeleccionado = document.getElementById("montoSeleccionado");
    const cuotasSeleccionado = document.getElementById("cuotasSeleccionado");
    const cuotaMensualInput = document.getElementById("cuotaMensual");
    const totalDevolverInput = document.getElementById("totalDevolver");

    function formatMoneda(valor) {
        return valor.toLocaleString("es-AR", { style: "currency", currency: "ARS" });
    }

    function actualizarValores() {
        const monto = parseFloat(montoSeleccionado.value);
        const cuotas = parseInt(cuotasSeleccionado.value);

        if (isNaN(monto) || isNaN(cuotas)) return;

        const montoConInteres = monto * 1.10;
        const cuotaMensual = montoConInteres / cuotas;

        cuotaMensualInput.value = formatMoneda(cuotaMensual);
        totalDevolverInput.value = formatMoneda(montoConInteres);
    }

    montoSeleccionado.addEventListener("change", actualizarValores);
    cuotasSeleccionado.addEventListener("change", actualizarValores);

    actualizarValores();
});
</script>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>