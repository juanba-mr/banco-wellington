<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Entidad.Provincia" %>
<%@ page import="Entidad.Localidad" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
<link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>Alta Cliente</title>
</head>
<body>
<i class="bi bi-arrow-left flecha" onclick="window.location.href='<%=request.getContextPath()%>/admin/InicioAdmin.jsp'"></i>
<%
    String usuarioLogin = (String) session.getAttribute("nombreUsuario");
%>
<div class="flex-space">
    <h3 class="titulo info-derecha">Admin: <%=usuarioLogin%></h3>
</div>
<div class="centrar">
    <div class="banner-titulo">
        <h1>Alta Cliente</h1>
    </div>
    <div class="banner-mediano-ingresar">
    <div class="formulario-ingresar">
        <%
            String agregado = (String) request.getAttribute("agregado");
            String error = (String) request.getAttribute("error");
            boolean showErrorValues = (error != null);


            if ("1".equals(agregado)) {
            out.println("Agregado");
            } else if (error != null) {
        
            	if ("campos_vacios".equals(error)) {
                    out.println("Por favor, complete todos los campos.");
                } else if ("nombre_apellido_invalido".equals(error)) {
                    out.println("El nombre o apellido contiene caracteres inválidos.");
                } else if ("las_contraseñas_no_coinciden".equals(error)) {
                    out.println("Las contraseñas no coinciden.");
                } else if ("dni_existente".equals(error)) {
                    out.println("El DNI ingresado ya existe.");
                } else if ("insert_fallido".equals(error)) {
                    out.println("Falló la inserción del cliente en la base de datos.");
                } else if("dni_invalido".equals(error)){
                    out.println("Dni Maximo 8 Numero");
                }else if("cuil_invalido".equals(error)){
                    out.println("Cuil Maximo 11 Numero");
                }else if ("cuil_existente".equals(error)){
                    out.println("El Cuil ingresado ya existe.");
                }else if ("usuario_existente".equals(error)) {
                        out.println("El Usuario ya existe");
                }else if ("fecha_nacimiento_futura".equals(error)) {
                    out.println("Fecha de nacimiento es futura.");
                }else if ("menor_de_18_anios".equals(error)) {
                        out.println("ERROR: El cliente debe tener al menos 18 años.");
                }else{
                    out.println("Ha ocurrido un error desconocido.");
                }
            
        
            }
        %>

	<%
    String mensajeError = (String) request.getAttribute("mensajeError");
    if (mensajeError != null) {
	%>
    <div style="color:red; font-weight:bold;">
        <%= mensajeError %>
    </div>
	<%
    }
	%>
    <form method="get" action="${pageContext.request.contextPath}/ServletClientes">
      <div class="fila">
    <h2>DNI:</h2>
    <input type="number" name="txtDni" class="input-texto" required maxlength="8"
           value="<%= showErrorValues && request.getParameter("txtDni") != null ? request.getParameter("txtDni") : "" %>">
</div>

<div class="fila">
    <h2>CUIL:</h2>
    <input type="number" class="input-texto" name="txtCuil" required
           value="<%=showErrorValues && request.getParameter("txtCuil") != null ? request.getParameter("txtCuil") : "" %>">
</div>

<div class="fila">
    <h2>Nombre:</h2>
    <input type="text" class="input-texto" name="txtNombre" required
           value="<%=showErrorValues && request.getParameter("txtNombre") != null ? request.getParameter("txtNombre") : "" %>">
</div>

<div class="fila">
    <h2>Apellido:</h2>
    <input type="text" class="input-texto" name="txtApellido" required
           value="<%=showErrorValues && request.getParameter("txtApellido") != null ? request.getParameter("txtApellido") : "" %>">
</div>

        <div class="fila">
        <h2>Sexo:</h2>
        <select id="sexo" name="ddlSexo" class="input-ddl" required>
                <option value="" disabled selected>-- Seleccione su sexo --</option>
                <option value="M">Masculino</option>
                <option value="F">Femenino</option>
                <option value="O">Otro</option>
        </select>
        </div>
        
        <div class="fila">
    <h2>Correo Electronico:</h2>
    <input type="text" class="input-texto" name="txtCorreo" required
           value="<%=showErrorValues && request.getParameter("txtCorreo") != null ? request.getParameter("txtCorreo") : "" %>">
	</div>

	<div class="fila">
    <h2>Fecha de Nacimiento:</h2>
    <input type="date" class="input-texto" name="txtFechaNacimiento" required
           value="<%=showErrorValues && request.getParameter("txtFechaNacimiento") != null ? request.getParameter("txtFechaNacimiento") : "" %>">
	</div>

        <div class="fila">
        <h2>Nacionalidad:</h2>
        <select id="paises" name="ddlPaises" class="input-ddl" required>
                <option value="" disabled selected>-- Seleccione su nacionalidad --</option>
                <option value="Argentina">Argentina</option>
                <option value="Uruguay">Uruguay</option>
                <option value="Colombia">Colombia</option>
                <option value="Chile">Chile</option>
                <option value="Peru">Peru</option>
                <option value="Venezuela">Venezuela</option>
                <option value="Ecuador">Ecuador</option>
                <option value="Brasil">Brasil</option>
        </select>
        </div>

        <div class="fila">
  		<h2>Provincia:</h2>
  		<select id="ddlProvincia" name="ddlProvincia" class="input-ddl" onchange="cargarLocalidades()" required>
    	<option value="" disabled selected>-- Seleccione su provincia --</option>
    	<%
		List<Provincia> provincias = (List<Provincia>) request.getAttribute("provincias");
		if (provincias != null) {
    	for (Provincia p : provincias) {
		%>
    	<option value="<%= p.getIdProvincia() %>"><%= p.getNombre() %></option>
		<%
    		}
		}
		%>
  		</select>
		</div>

        <div class="fila">
  		<h2>Localidad:</h2>
  		<select id="ddlLocalidad" name="ddlLocalidad" class="input-ddl" required>
    	<option value="" disabled selected>-- Seleccione su Localidad --</option>
  		</select>
		</div>

       <div class="fila">
 	   <h2>Dirección:</h2>
 	    <input type="text" class="input-texto" name="txtDireccion" required
          value="<%=showErrorValues && request.getParameter("txtDireccion") != null ? request.getParameter("txtDireccion") : "" %>">
		</div>

		<div class="fila">
   	    <h2>Usuario Cliente:</h2>
   		 <input type="text" class="input-texto" name="txtUsuario" required
           value="<%=showErrorValues &&  request.getParameter("txtUsuario") != null ? request.getParameter("txtUsuario") : "" %>">
		</div>

        <div class="fila">
        <h2>Contraseña del Cliente:</h2>
        <input type="password" class="input-texto" name="txtContraseña" required>
        </div>
        
        <div class="fila">
        <h2>Repetir Contraseña:</h2>
        <input type="password" class="input-texto" name="txtContraseña2" required>
        </div>
        
        <div class="info-centro">
        <input name="btnDarDeAlta" type="submit" value="Dar de alta" class="boton">
        </div>
        </form>
    </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>