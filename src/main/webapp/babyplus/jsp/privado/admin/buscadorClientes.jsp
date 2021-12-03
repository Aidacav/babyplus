<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<p>
    <center>
        <form method="get" action="${pageContext.request.contextPath}/babyplus/jsp/privado/admin/buscarClientes">
            <label for="usuario"><fmt:message key="buscador.clientes.nombre.usuario"/></label>
            <input type="text" id="usuario" name="usuario">
            <label for="nombre"><fmt:message key="buscador.clientes.nombre"/></label>
            <input type="text" id="nombre" name="nombre">
            <label for="apellidos"><fmt:message key="buscador.clientes.apellidos"/></label>
            <input type="text" id="apellidos" name="apellidos">
            <label for="fechaAlta"><fmt:message key="buscador.clientes.fecha.alta"/></label>
            <input type="date" id="fechaAlta" name="fechaAlta">
            <label for="activo"><fmt:message key="buscador.clientes.estado"/></label>
            <select id="activo" name="activo">
                <option value="null" selected="selected"><fmt:message key="buscador.clientes.estado.todos"/></option>
                <option value="1"><fmt:message key="buscador.clientes.estado.activo"/></option>
                <option value="0"><fmt:message key="buscador.clientes.estado.deshabilitado"/></option>
            </select>
            <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
            <input type="submit" value="<fmt:message key="buscador.clientes.boton.buscar"/>">
        </form>
    </center>
</p>
