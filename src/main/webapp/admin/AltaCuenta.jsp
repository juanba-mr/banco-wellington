<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
<link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>Alta Cuenta</title>
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
            
            <h1>Alta Cuenta</h1>
        </div>
        
        <div class="banner-mediano-ingresar">
            <div class="formulario-ingresar">
            <form method="get" action="${pageContext.request.contextPath}/ServletCuentas">
                    <div class="fila">
                        <h2>DNI:</h2>
                        <input type="number" name=txtDni maxlength="8" class="input-texto">
                    </div>

                    <div class="fila">
                        <h2>Tipo De Cuenta:</h2>
                        <select id="cuentas" name="dllTipoCuenta" class="input-ddl">
                                <option value="" disabled selected>-- Seleccione un tipo de cuenta --</option>
                                <option value="1">Cuenta Corriente</option>
                                <option value="2">Caja De Ahorro</option>
                                
                        </select>
                    </div>

                    <br>
                    
                    <div>
                        <div class="info-centro">
                        	<input type="hidden" name="action" value="crearCuenta"> 
                            <input type="submit" name="btnCrearCuenta"  value="crear Cuenta" class="boton">
                        </div>
                    </div>
           </form>
           <%-- Lógica para mostrar el mensaje de éxito o error --%>
           <% 
               
               String mensaje = (String) request.getAttribute("mensajeResultado"); 
               if (mensaje != null && !mensaje.isEmpty()) { 
           %>
               <div style="margin-top: 15px; font-weight: bold;">
                   <% if (mensaje.startsWith("Error:")) { %>
                       <span style="color: red;"><%= mensaje %></span> 
                   <% } else { %>
                       <span style="color: green;"><%= mensaje %></span> 
                   <% } %>
               </div>
           <% } %>
           
        </div>
       
    </div>
    </div>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>