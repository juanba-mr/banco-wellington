<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Entidad.Provincia" %>
<%@ page import="Entidad.Cliente" %>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
<link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>Editar Cliente</title>
</head>
<body>
<i class="bi bi-arrow-left flecha" onclick="window.location.href='<%=request.getContextPath()%>/ServletClientes?btnListarModificarClientes=ModificarClientes'"></i>
<%
    String usuarioLogin = (String) session.getAttribute("nombreUsuario");
%>
<div class="flex-space">
    <h3 class="titulo info-derecha">Admin: <%=usuarioLogin%></h3>
</div>
<div class="centrar">
    <div class="banner-titulo">
        <h1>Editar Cliente</h1>
    </div>
    <div class="banner-mediano-ingresar">
        <div class="formulario-ingresar">
        <%
            
            Cliente Cli = new Cliente();
            if(request.getAttribute("cliente") != null){
                Cli = (Cliente)request.getAttribute("cliente");
            }
            
           
            String Actualizado = (String) request.getAttribute("Actualizado"); 
            String error = (String) request.getAttribute("error");
            
          
            boolean showErrorValues = (error != null);

          
            if ("1".equals(Actualizado)) {
                out.println("<p class='mensaje-exito'>¡Cliente actualizado exitosamente!</p>");
            } else if (error != null) {
                out.println("<p class='mensaje-error'>"); 
                if ("campos_vacios".equals(error)) {
                    out.println("Por favor, complete todos los campos obligatorios.");
                } else if ("nombre_apellido_invalido".equals(error)) {
                    out.println("El nombre o apellido contiene caracteres inválidos. Solo letras y espacios.");
                } else if ("las_contraseñas_no_coinciden".equals(error)) {
                    out.println("Las contraseñas no coinciden. Por favor, verifique.");
                } else if ("insert_fallido".equals(error)) { 
                    out.println("Falló la actualización del cliente en la base de datos.");
                } else if ("usuario_existente".equals(error)) {
                    out.println("El nombre de usuario ya existe. Por favor, elija otro.");
                } else {
                    out.println("Ha ocurrido un error desconocido durante la actualización.");
                }
                out.println("</p>"); 
            }
        %>
        
        <form action="ServletClientes" method="post">
            <input type="hidden" name="sexoCliente" value="<%= Cli.getSexoCliente() != null ? Cli.getSexoCliente() : "" %>">
            <input type="hidden" name="nacionalidad" value="<%= Cli.getNacionalidad() != null ? Cli.getNacionalidad() : "" %>">
            
            <div class="fila">
                <h2>DNI:</h2>
                <input type="number" name="txtDni" class="input-texto" 
                       value="<%= Cli.getDniCliente() != null ? Cli.getDniCliente() : "" %>" required readonly>
            </div>

            <div class="fila">
                <h2>CUIL:</h2>
                <input type="number" class="input-texto" name="txtCuil" 
                       value ="<%= Cli.getCuilCliente() != null ? Cli.getCuilCliente() : "" %>" required readonly>
            </div>

            <div class="fila">
                <h2>Nombre:</h2>
                <input type="text" class="input-texto" name="txtNombre" required
                       value="<%= showErrorValues && request.getParameter("txtNombre") != null ? request.getParameter("txtNombre") : (Cli.getNombreCliente() != null ? Cli.getNombreCliente() : "") %>">
            </div>

            <div class="fila">
                <h2>Apellido:</h2>
                <input type="text" class="input-texto" name="txtApellido" required
                       value="<%= showErrorValues && request.getParameter("txtApellido") != null ? request.getParameter("txtApellido") : (Cli.getApellidoCliente() != null ? Cli.getApellidoCliente() : "") %>">
            </div>
            
            <div class="fila">
                <h2>Correo Electronico:</h2>
                <input type="email" class="input-texto" name="txtCorreo" required
                       value="<%= showErrorValues && request.getParameter("txtCorreo") != null ? request.getParameter("txtCorreo") : (Cli.getCorreoElectronicoCliente() != null ? Cli.getCorreoElectronicoCliente() : "") %>">
            </div>

            <div class="fila">
                <h2>Dirección:</h2>
                <input type="text" class="input-texto" name="txtDireccion" required
                       value="<%= showErrorValues && request.getParameter("txtDireccion") != null ? request.getParameter("txtDireccion") : (Cli.getDireccionCliente() != null ? Cli.getDireccionCliente() : "") %>">
            </div>

            <div class="fila">
                <h2>Usuario Cliente:</h2>
                <input type="text" class="input-texto" name="txtUsuario" required  readonly
                       value="<%= Cli.getUsuarioCliente() != null ? Cli.getUsuarioCliente() : "" %>">
            </div>

            <div class="fila">
   			 <h2>Contraseña del Cliente:</h2>
   			 <input type="text" class="input-texto" name="txtContrasena1" required
             value="<%= showErrorValues && request.getParameter("txtContrasena1") != null ? request.getParameter("txtContrasena1") : (Cli.getContraseñaCliente() != null ? Cli.getContraseñaCliente() : "") %>">
		   	</div>
         
           <div class="fila">
  		   <h2>Repetir Contraseña:</h2>
  		   <input type="text" class="input-texto" name="txtContrasena2" required
            value="<%= showErrorValues && request.getParameter("txtContrasena2") != null ? request.getParameter("txtContrasena2") : (Cli.getContraseñaCliente() != null ? Cli.getContraseñaCliente() : "") %>">
		 	</div>
         
            <div class="info-centro">
                <input name="btnAcceptar" type="submit" value="Aceptar" class="boton">
            </div>
        </form>
    </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</body>
</html>