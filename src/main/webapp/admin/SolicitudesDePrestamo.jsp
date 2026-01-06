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
<title>Solicitudes de Prestamos Pendientes</title>
</head>
<body>
<i class="bi bi-arrow-left flecha" onclick="window.location.href='<%=request.getContextPath()%>/admin/InicioAdmin.jsp'"></i>
    <div class="centrar">
        <div class="banner-titulo">
            <h2>Solicitudes de Prestamos Pendientes</h2>
        </div>

        <div class="banner-mediano2">
            <table class="tabla-clientes">
  <thead>
    <tr>
      <th>DNI</th>
      <th>ID Cuenta</th>
      <th>Fecha</th>
      <th>Nombre</th>
      <th>Apellido</th>
      <th>Monto Solicitado</th>
      <th>Cuotas</th>
      <th>Acciones</th>
    </tr>
  </thead>
  <tbody>
    <% for (Prestamo p : prestamos) { %>
      <tr>
        <td><%= p.getIdcuenta().getDnicliente().getDniCliente() %></td>
        <td><%= p.getIdcuenta().getIdCuenta() %></td>
        <td><%= p.getFechaSolicitud() %></td>
        <td><%= p.getIdcuenta().getDnicliente().getNombreCliente() %></td>
        <td><%= p.getIdcuenta().getDnicliente().getApellidoCliente() %></td>
        <td><%= formatoMoneda.format(p.getImporteTotal()) %></td>
        <td><%= p.getCantidadCuotas() %></td>
        <td>
          <form method="post" action="${pageContext.request.contextPath}/ServletPrestamos">
            <input type="hidden" name="idPrestamo" value="<%= p.getIdPrestamo() %>">
            <button type="submit" name="btnAceptar" class="boton2">Aceptar</button>
            <button type="submit" name="btnRechazar" class="boton2">Rechazar</button>
          </form>
        </td>
      </tr>
    <% } %>
  </tbody>
</table>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>