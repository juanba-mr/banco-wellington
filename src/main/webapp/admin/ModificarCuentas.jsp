<%@ page import="Entidad.Cuenta" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modificar Cuenta</title>

    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png">

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
</head>
<body>
    <i class="bi bi-arrow-left flecha" onclick="window.location.href='<%= request.getContextPath() %>/admin/InicioAdmin.jsp'"></i>
<%
    String usuarioLogin = (String) session.getAttribute("nombreUsuario");
%>
<div class="flex-space">
    <h3 class="titulo info-derecha">Admin: <%=usuarioLogin%></h3>
</div>
    <div class="centrar">
        <div class="banner-titulo">
            <h1>Modificar Cuenta</h1>
        </div>

        <div class="banner-mediano2">
            <!-- Formulario de búsqueda -->
            <div class="fila">
                <form action="ServletCuentas" method="post">
                    <h3>Buscar por ID:</h3>
                    <input type="text" name="txtId" class="input-texto">
                    <button class="boton" name="btnBuscar" value="Buscar">Buscar</button>
                    <button class="boton" name="btnRestablecer" value="restablecer">Restablecer</button>
                </form>
            </div>

            <!-- Tabla de resultados -->
            <%
                List<Cuenta> listaCuentas = (List<Cuenta>) request.getAttribute("listaCuentas");
                if (listaCuentas != null && !listaCuentas.isEmpty()) {
            %>
            <table id="tablaCuentas" class="tabla-clientes display">
                <thead>
                    <tr>
                        <th>ID Cuenta</th>
                        <th>DNI Cliente</th>
                        <th>Fecha de Creación</th>
                        <th>Tipo de Cuenta</th>
                        <th>CBU</th>
                        <th>Saldo</th>
                        <th>Editar</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Cuenta c : listaCuentas) { %>
                        <tr>
                            <td><%= c.getIdCuenta() %></td>
                            <td><%= c.getDniCliente().getDniCliente() %></td>
                            <td><%= c.getFechaCreacionCuenta() %></td>
                            <td><%= c.getTipoCuenta().getDescripcion() %></td>
                            <td><%= c.getCbuCuenta() %></td>
                            <td><%= String.format("%.2f", c.getSaldoCuenta()) %></td>
                            <td>
                                <form action="ServletCuentas" method="post">
                                    <input type="hidden" name="idCuenta" value="<%= c.getIdCuenta() %>" />
                                    <input type="hidden" name="dniCliente" value="<%= c.getDniCliente().getDniCliente() %>" />
                                    <input type="hidden" name="fechaCreacion" value="<%= c.getFechaCreacionCuenta() %>" />
                                    <input type="hidden" name="tipoCuenta" value="<%= c.getTipoCuenta().getIdTipoCuenta() %>" />
                                    <input type="hidden" name="cbuCuenta" value="<%= c.getCbuCuenta() %>" />
                                    <input type="hidden" name="saldoCuenta" value="<%= String.format("%.2f", c.getSaldoCuenta()) %>" />
                                    <input type="submit" name="BtnEditar" value="Editar" class="botonTabla" />
                                </form>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
            <% } %>
        </div>

        <!-- Formulario para eliminar cuenta -->
        <div class="banner-mediano2">
            <div class="info-centro">
                <h2>Dar de Baja Cuenta</h2>
            </div>

            <%
                String errorMensaje = (String) request.getAttribute("errorMensaje");
                Boolean seElimino = (Boolean) request.getAttribute("seElimino");

                if (errorMensaje != null) {
            %>
                <p style="color: red;">¡Error! <%= errorMensaje %></p>
            <%
                } else if (seElimino != null && seElimino) {
            %>
                <p style="color: green;">¡Cuenta eliminada exitosamente!</p>
            <%
                } else if (seElimino != null && !seElimino) {
            %>
                <p style="color: orange;">No se pudo eliminar la cuenta.</p>
            <%
                }
            %>

            <form method="post" action="${pageContext.request.contextPath}/ServletCuentas">
                <div class="fila">
                    <h3>Ingrese el ID:</h3>
                    <input type="text" class="input-texto" name="CuentaEliminar">
                    <input type="submit" value="Eliminar Cuenta" class="boton" name="btnEliminar"
                           onclick="return confirm('¿Está seguro que desea eliminar la cuenta?');">
                </div>
            </form>
        </div>
    </div>

    <!-- Scripts -->
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
    <script>
        $(document).ready(function () {
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