<%@ page import="Entidad.Cuenta" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
<link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>Listar Cuentas</title>
</head>
<body>
<i class="bi bi-arrow-left flecha" onclick="window.location.href='<%=request.getContextPath()%>/admin/InicioAdmin.jsp'"></i>
<%
    String usuarioLogin = (String) session.getAttribute("nombreUsuario");
%>
<div class="flex-space">
    <h3 class="titulo info-derecha">Admin: <%=usuarioLogin%></h3>
</div>
<form method="get" action="${pageContext.request.contextPath}/ServletCuentas">
    <div class="centrar">
        <div class="banner-titulo">
            <h1>Listar Cuentas</h1>
        </div> 
        <div class="banner-mediano2"> 
            <div class="fila">
                <h3>Buscar por Filtro: </h3>
                <select name="filtro" class="input-ddl">
                    <option value="">-- Seleccione un filtro --</option>
                    <option value="mayor">Mayor Saldo</option>
                    <option value="menor">Menor Saldo</option>
                    <option value="2">Cuenta de Ahorros</option>
                    <option value="1">Cuenta Corriente</option>
                </select>
                <input type="hidden" name="action" value="Listar Cuentas">
                <button name="btnListarCuentas" class="boton">Buscar</button>
                
            </div>

            <%
                List<Cuenta> listaCuentas = (List<Cuenta>) request.getAttribute("listaCuentas");
                if (listaCuentas != null && !listaCuentas.isEmpty()) {
            %>

<table id="tablaCuentas" class="tabla-clientes display">
                <thead>
                    <tr>
                        <th>ID cuenta</th>
                        <th>DNI cliente</th>
                        <th>Fecha de Creación</th>
                        <th>Tipo de Cuenta</th>
                        <th>CBU</th>
                        <th>Saldo</th>
                     
                    </tr>
                </thead>
                <tbody>
                    <% for (Cuenta C : listaCuentas) { %>
                    <tr>
                        <td><%= C.getIdCuenta() %></td>
                        <td><%= C.getDniCliente().getDniCliente() %></td>
                        <td><%= C.getFechaCreacionCuenta() %></td>
                        <td><%= C.getTipoCuenta().getDescripcion() %></td>
                        <td><%= C.getCbuCuenta() %></td>
                        <td>$<%= String.format("%.2f", C.getSaldoCuenta()) %></td>
                   
                    </tr>
                    <% } %>
                </tbody>
            </table>

            <% } else { %>
                <p>No se encontraron cuentas.</p>
            <% } %>
        </div>   
    </div> 
</form>

<script src="${pageContext.request.contextPath}/js/script.js"></script>
		<script>
    $(document).ready(function() {
        $('#tablaCuentas').DataTable({
            pageLength: 10,
            ordering: false, 
            searching: false,
            language: {
                url: '//cdn.datatables.net/plug-ins/1.13.4/i18n/es-ES.json'
            }
        });
    });
</script>
</body>
</html>
</html>