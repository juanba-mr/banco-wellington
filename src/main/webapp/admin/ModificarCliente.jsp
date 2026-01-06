<%@ page import="Entidad.Cliente"%>
<%@ page import="java.util.ArrayList"%>
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
<title>Modificar Cliente</title>
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
            <h1>Modificar Cliente</h1>
        </div> 
  <div class="contenedor-centro4"> 
        <div class="fila">
         <form action="ServletClientes" method="get">
                <h3>Buscar por DNI: </h3>
                <input type="number" name="txtDni"  class="input-texto">
                <button class="boton" name="btnBuscar" value="Buscar">Buscar</button>
                <button class="boton" name="btnRestablecer" value="restablecer">Restablecer</button>
                
                <% if (request.getAttribute("errorMensaje") != null) { %>
    				<p style="color: red; font-weight: bold;"><%= request.getAttribute("errorMensaje") %></p>
				<% } %>
                
              
                </form>
                
        
            </div>
<table id="tablaClientes" class="tabla-clientes display">
      <thead>
            <tr>
              <th>DNI</th>
              <th>CUIL</th>
              <th>Nombre</th>
              <th>Apellido</th>
              <th>Sexo</th>
              <th>Nacionalidad</th>
              <th>Provincia</th>
              <th>Localidad</th>
              <th>Dirección</th>
              <th>Correo</th>
              <th>Fecha Nacimiento</th>
              <th>Usuario</th>
              <th>Contraseña</th>
              <th>Editar</th>
              
            </tr>
          </thead>
          <tbody>
          <% 
          ArrayList<Cliente> obtenerCliente = new ArrayList<Cliente>(); 

          if(request.getAttribute("listaU") != null){
          obtenerCliente = (ArrayList<Cliente>) request.getAttribute("listaU");
         }
 		    %>
          
              <%if(obtenerCliente!=null)
            	   for(Cliente user : obtenerCliente) { %>
            	  <tr>
            	    <td><%= user.getDniCliente() %></td>
            	    <td><%= user.getCuilCliente() %></td>
            	    <td><%= user.getNombreCliente() %></td>
            	    <td><%= user.getApellidoCliente() %></td>
            	    <td><%= user.getSexoCliente() %></td>
            	    <td><%= user.getNacionalidad() %></td>
            	    <td><%= user.getIdProvinciaCliente().getNombre() %></td>
            	    <td><%= user.getIdLocalidadCliente().getNombre() %></td>
            	    <td><%= user.getDireccionCliente() %></td>
            	    <td><%= user.getCorreoElectronicoCliente() %></td>
            	    <td><%= user.getFechaNacimientoCliente() %></td>
            	    <td><%= user.getUsuarioCliente() %></td>
            	    <td><%= user.getContraseñaCliente() %></td>
            	    <td>
            	      <form action="ServletClientes" method="post">
            	        <input type="hidden" name="dniCliente" value="<%= user.getDniCliente() %>" />
            	        <input type="hidden" name="cuilCliente" value="<%= user.getCuilCliente() %>" />
            	        <input type="hidden" name="nombreCliente" value="<%= user.getNombreCliente() %>" />
            	        <input type="hidden" name="apellidoCliente" value="<%= user.getApellidoCliente() %>" />
            	        <input type="hidden" name="sexoCliente" value="<%= user.getSexoCliente() %>" />
            	        <input type="hidden" name="nacionalidad" value="<%= user.getNacionalidad() %>" />
            	        <input type="hidden" name="idProvinciaCliente" value="<%= user.getIdProvinciaCliente().getNombre() %>" />
            	        <input type="hidden" name="idLocalidadCliente" value="<%= user.getIdLocalidadCliente().getNombre() %>" />
            	        <input type="hidden" name="direccionCliente" value="<%= user.getDireccionCliente() %>" />
            	        <input type="hidden" name="CorreoCliente" value="<%= user.getCorreoElectronicoCliente() %>" />
            	        <input type="hidden" name="fechaNacimientoCliente" value="<%= user.getFechaNacimientoCliente() %>" />
            	        <input type="hidden" name="usuarioCliente" value="<%= user.getUsuarioCliente() %>" />
            	        <input type="hidden" name="contrasenaCliente1" value="<%= user.getContraseñaCliente() %>" />
            	        <input type="submit" name="BtnEditar" value="Editar" class="botonTabla" />
            	      </form>
            	    </td>
            	  </tr>
            	  <% } %>
           
          </tbody>
        </table>
      </div>   
      
      <div class="banner-mediano2">
          <div class="info-centro">
            <h2>Dar de baja cliente </h2>
          </div>
          <%
			String errorMensaje = (String) request.getAttribute("errorMensaje2");               
			Boolean seElimino = (Boolean) request.getAttribute("seElimino");

			if (errorMensaje != null) {
			%>
    			<p style="color: red;">¡Error! <%= errorMensaje %></p>
			<%
			} else if (seElimino != null && seElimino) {
			%>
    			<p style="color: green;">¡Cliente eliminado exitosamente!</p>
			<%
			} else if (seElimino != null && !seElimino) {
			%>
    			<p style="color: orange;">No se pudo eliminar el cliente.</p>
			<%
			}
			%>

                 <form method="post" action="${pageContext.request.contextPath}/ServletClientes">
          <div class="fila">
                <h3>Ingrese el DNI: </h3>

                <input type="number" class="input-texto"name="txtDniEliminar">
                
              
                <input type="submit" value="Eliminar Cliente" class="boton" name="btnEliminar"
onclick="return confirm('¿Está seguro que desea eliminar el cliente?');">
            </div>
                 </form>
             
      </div>
  </div>
 
  
 
  
  <script src="${pageContext.request.contextPath}/js/script.js"></script>
  <% if(request.getAttribute("error") != null && request.getAttribute("error").equals("dni_no_existe")) { %>
  <script>
    alert("El DNI ingresado no existe en la base de datos.");
  </script>
<% } %>

	<script>
    $(document).ready(function() {
        $('#tablaClientes').DataTable({
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