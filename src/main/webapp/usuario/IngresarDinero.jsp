<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=123">
<link rel="icon" type="/images/logo.png" href="${pageContext.request.contextPath}/images/logo.png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>Ingresar Dinero</title>
</head>
<body>
    <i class="bi bi-arrow-left flecha" onclick="history.back()"></i>
    <%
    String usuarioLogin = (String) session.getAttribute("nombreUsuario");
%>
<div class="flex-space">
    <h3 class="titulo info-derecha">Cliente : <%=usuarioLogin%></h3>
</div>

    <div class="contenedor-centro">
        <div class="caja">
            <div class="subtitulo">
                <h1>Ingrese la Cantidad de Dinero</h1>
            </div>

        <div class="formulario">
           <form action="${pageContext.request.contextPath}/ServletCuentas" method="post">
    		<input type="hidden" name="idCuenta" value="${param.idCuenta}" />
    		<div class="input-wrapper">
        	<span class="prefix">$</span>
        	<input type="text" name="monto" id="monto" oninput="formatearNumeroEuropeo(this)" value="0,00" required>
    		</div>
    		<button type="submit" class="boton" name="btnIngresarDinero"><b>Ingresar</b></button>
		   </form>

                
    <%
    String mensaje = (String) request.getAttribute("mensajeResultado");
    if (mensaje != null && !mensaje.isEmpty()) {
	%>
    <p><b><%= mensaje %></b></p>
	<%
    }
	%>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>