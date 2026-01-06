<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
<link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>Inicio Sesion</title>
</head>
<body>
    <div class="contenedor-centro">
    <div class="caja">
      <h1>Bienvenido a Banco Wellington</h1>
      <h3>Ingrese a su cuenta</h3>
      <% String errorLogin = (String) request.getAttribute("errorLogin"); %>
		<% if (errorLogin != null) { %>
  		<div style="color: red; margin-bottom: 15px; font-weight: bold;">
    	<%= errorLogin %>
  		</div>
	  <% } %>
      <form action="${pageContext.request.contextPath}/ServletAcceso" method="post" class="formulario">
  		<input type="text" name="usuario" placeholder="Usuario" class="input-texto" required>
  		<div class="campo-password">
    	<input type="password" id="clave" name="password" class="input-texto" placeholder="Contraseña" required>
    	<i class="bi bi-eye-slash toggle-password" id="contraseñaOculta"></i>
  		</div>
  		<input name="btnIngresar" type="submit" class="boton" value="Ingresar">
	  </form>
    </div>
  </div>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>