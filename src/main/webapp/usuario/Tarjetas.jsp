<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Entidad.Acceso" %>
<%@ page import="Entidad.Cuenta" %>
<%@ page import="java.util.List" %>
<%@ page import="DaoImpl.CuentaDaoImpl" %>
<%
   
    Acceso clienteLogueado = (Acceso) session.getAttribute("clienteLogueado");
	CuentaDaoImpl cuentaImpl = new CuentaDaoImpl();

    /*if (clienteLogueado == null) {
        response.sendRedirect(request.getContextPath() + "/InicioSesion.jsp");
        return; 
    }*/
    
    
    List<Cuenta> listaCuentas = cuentaImpl.obtenerCuentasPorDni(clienteLogueado.getCliente().getDniCliente() );
    
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
<link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>Tarjetas</title>
</head>
<body>
<%
    String usuarioLogin = (String) session.getAttribute("nombreUsuario");
%>
<div class="flex-space">
    <h3 class="titulo info-derecha">Cliente : <%=usuarioLogin%></h3>
</div>
  <div class="centrar">
    <div class="bloque-Tit-Sub">
      <h1>¡Hola <%= clienteLogueado.getCliente().getNombreCliente() %>!</h1>
      <% 
      if(listaCuentas.isEmpty()){
   	  %>
   	  <h3>No tenes ninguna cuenta disponible</h3>
      <%  
      }else{
      %>
      <h3>Elige una cuenta para continuar</h3>
      <%  
      }
      %>
    </div>

    <div class="cuadrados">
	<%
    for (Cuenta c : listaCuentas) {
	%>
      <div class="grupo-cuenta">
        <% 
      	if(c.getTipoCuenta().getDescripcion().equals("corriente")){
   	  	%>
   	  	<h3>Cuenta Corriente</h3>
      	<%  
      	}else{
      	%>
      	<h3>Cuenta de Ahorros</h3>
      	<%  
      	}
      	%>
        <div class="tarjeta seleccionable" onclick="window.location.href='InicioCliente.jsp?cbu=<%= c.getCbuCuenta() %>'">
          **** <%= c.getCbuCuenta().substring(c.getCbuCuenta().length() - 4) %>
        </div>
      </div>
	<%
    }
	%>
    </div>
    <input type="button" name="btnCerrarSesion" id="btnCerrarSesion" value="Cerrar Sesión" onclick="window.location.href='${pageContext.request.contextPath}/ServletCerrarSesion'">
  </div>
  <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>