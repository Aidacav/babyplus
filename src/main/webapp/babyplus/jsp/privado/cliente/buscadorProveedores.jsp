<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<p>
    <center>
        <form method="get" action="${pageContext.request.contextPath}/babyplus/jsp/privado/cliente/buscar">
            <label for="razonProveedor"><fmt:message key="cliente.buscador.nombre"/></label>
            <input type="text" id="razonProveedor" name="razonProveedor">
            <label for="localidadProveedor"><fmt:message key="cliente.buscador.localidad"/></label>
            <input type="text" id="localidadProveedor" name="localidadProveedor">
            <label for="cpProveedor"><fmt:message key="cliente.buscador.cp"/></label>
            <input type="number" min="10000" max="99999" id="cpProveedor" name="cpProveedor">
            <label for="servicioProveedor"><fmt:message key="cliente.buscador.servicio"/></label>
            <input type="text" id="servicioProveedor" name="servicioProveedor">
            <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
            <input type="submit" value="<fmt:message key="cliente.buscador.boton"/>">
        </form>
    </center>
</p>
