<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Entidad.Cuenta" %>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
<link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>Editar Cuenta</title>
</head>
<body>
<i class="bi bi-arrow-left flecha"    onclick="window.location.href='<%=request.getContextPath()%>/ServletCuentas?action=Modificar%20Cuentas'"></i>
<%
    String usuarioLogin = (String) session.getAttribute("nombreUsuario");
%>
<div class="flex-space">
    <h3 class="titulo info-derecha">Admin: <%=usuarioLogin%></h3>
</div>
<div class="centrar">
    <div class="banner-titulo">
        <h1>Editar Cuenta</h1>
    </div>
    <div class="banner-mediano-ingresar"> 
    <div class="formulario-ingresar">
    <%
    
    Cuenta cuenta = new Cuenta();
    if(request.getAttribute("cuenta") != null){
    cuenta=(Cuenta)request.getAttribute("cuenta");
    }
    
    
    %>
     <form action="ServletCuentas" method="post">
        <div class="fila">
        <h2>ID :</h2>
        <input type="text" name="idCuenta" class="input-texto" value="<%= cuenta.getIdCuenta()%>" readonly required >
        </div>

        <div class="fila">
        <h2>DNI:</h2>
        <input type="number" class="input-texto" name="DniCliente" value ="<%= cuenta.getDnicliente().getDniCliente() %>" readonly required >
        </div>

       

     

        <div class="fila">
        <h2>TIPO CUENTA:</h2>
        <select id="tipoCuenta" name="ddlTipoCuenta" class="input-ddl" required>
        <option value="1" <%= "1".equals(cuenta.getTipoCuenta().getIdTipoCuenta()) ? "selected" : "" %>>Cuenta corriente</option>
        <option value="2" <%= "2".equals(cuenta.getTipoCuenta().getIdTipoCuenta()) ? "selected" : "" %>>Cuenta de ahorro</option>
        </select>
        </div>

     

    
      
        <div class="info-centro">
       
        <input name="btnAcceptar" type="submit" value="Acceptar" class="boton">
        </div>
        </form>

    </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</body>
</html>