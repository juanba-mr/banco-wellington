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
<title>Listar Clientes</title>
</head>
<body>
<i class="bi bi-arrow-left flecha" onclick="window.location.href='<%=request.getContextPath()%>/admin/InicioAdmin.jsp'"></i>
<%
    String usuarioLogin = (String) session.getAttribute("nombreUsuario");
%>
<div class="flex-space">
    <h3 class="titulo info-derecha">Admin: <%=usuarioLogin%></h3>
</div>
<form method="post" action="${pageContext.request.contextPath}/ServletClientes">

    <div class="centrar">
        <div class="banner-titulo">
            <h1>Listar Clientes</h1>
        </div>
  <div class="contenedor-centro4">
        <div class="fila">
                <h3>Buscar por Filtro: </h3>
                <select id="filtro" name="filtro" class="input-ddl">
                <option value="default">-- Seleccione su filtro --</option>
                <option value="F">Sexo F</option>
                <option value="M">Sexo M</option>
                <option value="O">Sexo O</option>


                </select>
                <button name ="filtrarClientes"class="boton">Buscar</button>
            </div>

     <%
         ArrayList<Cliente> obtenerCliente = null; 

          if(request.getAttribute("listaU") != null){
              obtenerCliente = (ArrayList<Cliente>) request.getAttribute("listaU");
          }

          if(obtenerCliente != null && !obtenerCliente.isEmpty()){
     %>

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
              <th>Fecha nacimiento</th>
              <th>Nombre usuario</th>
               <th>Contraseña</th>

            </tr>
          </thead>
          <tbody>

           <%
           for(Cliente user : obtenerCliente){

           %>

           <tr>
           <td><%=user.getDniCliente() %> </td>
           <td><%=user.getCuilCliente() %> </td>
           <td><%=user.getNombreCliente() %> </td>
           <td><%=user.getApellidoCliente() %> </td>
           <td><%=user.getSexoCliente() %> </td>
           <td><%=user.getNacionalidad() %> </td>
           <td><%=user.getIdProvinciaCliente().getNombre()%></td>
           <td><%=user.getIdLocalidadCliente().getNombre() %></td>
           <td><%=user.getDireccionCliente() %></td>
 			<td><%=user.getCorreoElectronicoCliente() %></td>
            <td><%=user.getFechaNacimientoCliente() %></td>
             <td><%=user.getUsuarioCliente() %></td>
              <td><%=user.getContraseñaCliente() %></td>

           <% } %>
		
		

          </tbody>
        </table>
         <% }else{ %>

        	   <p>No se encontraron Clientes.</p>

          <% } %>

      </div>
  </div>
  </form>
  <script src="${pageContext.request.contextPath}/js/script.js"></script>
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