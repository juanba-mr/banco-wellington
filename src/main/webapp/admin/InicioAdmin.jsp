<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Entidad.Acceso" %>
<%
    Acceso adminLogueado = (Acceso) session.getAttribute("adminLogueado");
    if (adminLogueado == null) {
        response.sendRedirect(request.getContextPath() + "/InicioSesion.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
<link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>Inicio Admin</title>
</head>
<body>

<%
    String usuarioLogin = (String) session.getAttribute("nombreUsuario");
%>
<div class="flex-space">
    <h3 class="titulo info-derecha">Admin: <%=usuarioLogin%></h3>
</div>
<div class="centrar">

    <div class="banner-titulo">
        <h1>Menu Admin</h1>
    </div>

    <div class="banner-mediano2"> 
        <h2 class="info-centro">Clientes</h2>
        <div class="botonera">
            <form method="get" action="${pageContext.request.contextPath}/ServletProvincias">
                <input type="submit" value="Alta Cliente" class="boton" name="btnDarDeAlta">
            </form>
            <form method="get" action="${pageContext.request.contextPath}/ServletClientes">
<input type="submit" value="Modificar Clientes" class="boton" name="btnListarModificarClientes">
            </form>
            <form method="get" action="${pageContext.request.contextPath}/ServletClientes">
                <input type="submit" value="Listar Clientes" class="boton" name="btnListarClientes">
            </form>
        </div>
    </div>

    <div class="banner-mediano2"> 
        <h2 class="info-centro">Cuentas</h2>
        <div class="botonera">
            <form method="get" action="${pageContext.request.contextPath}/ServletCuentas">
                <input type="submit" value="Alta Cuenta" class="boton" name="action">
            </form>
            <form method="get" action="${pageContext.request.contextPath}/ServletCuentas">
                <input type="submit" value="Modificar Cuentas" class="boton"name="action">
            </form>
          <form method="get" action="${pageContext.request.contextPath}/ServletCuentas">
                <input type="submit" value="Listar Cuentas" class="boton" name="action">
            </form>
        </div>
    </div>

    <div class="banner-mediano2"> 
        <h2 class="info-centro">Préstamos</h2>
        <div class="botonera">
            <form method="get" action="${pageContext.request.contextPath}/ServletPrestamos">
            <input type="hidden" name="action" value="listarPendientes">
               <input type="submit" value="Solicitudes de Prestamos" class="boton">
            </form>

            <form method="get" action="${pageContext.request.contextPath}/ServletPrestamos">
            <input type="hidden" name="listar" value="listarPrestamos">
            <input type="submit" value="Listar Prestamos" class="boton">
            </form>
        </div>
    </div>

    <div class="banner-mediano2"> 
        <h2 class="info-centro">Informes</h2>
        <div class="botonera">
        
        
            <form method="get" action="${pageContext.request.contextPath}/ServletInformes">
                <button type="submit" class="boton"  name="action" value="Informes" >
                <i class="bi bi-person-lines-fill"></i> Informes
                </button>
            </form>
        </div>
    </div>

    <input type="button" name="btnCerrarSesion" id="btnCerrarSesion" value="Cerrar Sesión" onclick="window.location.href='${pageContext.request.contextPath}/ServletCerrarSesion'">
</div>

<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>