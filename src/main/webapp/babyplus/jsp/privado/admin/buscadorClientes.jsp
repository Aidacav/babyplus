<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<p>
    <center>
        <form method="get" action="${pageContext.request.contextPath}/babyplus/jsp/privado/admin/buscarClientes">
            <label for="usuarioCliente"><fmt:message key="buscador.clientes.nombre.usuario"/></label>
            <input type="text" id="usuarioCliente" name="usuarioCliente">
            <label for="nombreCliente"><fmt:message key="buscador.clientes.nombre"/></label>
            <input type="text" id="nombreCliente" name="nombreCliente">
            <label for="apellidosCliente"><fmt:message key="buscador.clientes.apellidos"/></label>
            <input type="text" id="apellidosCliente" name="apellidosCliente">
            <label for="fechaCliente"><fmt:message key="buscador.clientes.fecha.alta"/></label>
            <input type="date" id="fechaCliente" name="fechaCliente">
            <label for="estadoCliente"><fmt:message key="buscador.clientes.estado"/></label>
            <select id="estadoCliente" name="estadoCliente">
                <option value="null" selected="selected"><fmt:message key="buscador.clientes.estado.todos"/></option>
                <option value="1"><fmt:message key="buscador.clientes.estado.activo"/></option>
                <option value="0"><fmt:message key="buscador.clientes.estado.deshabilitado"/></option>
            </select>
            <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
            <input type="submit" value="<fmt:message key="buscador.clientes.boton.buscar"/>">
        </form>
    </center>
</p>
