<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<p>
    <center>
        <form method="get" action="${pageContext.request.contextPath}/babyplus/jsp/privado/admin/buscarProveedores">
            <label for="usuario"><fmt:message key="buscador.proveedores.nombre.usuario"/></label>
            <input type="text" id="usuario" name="usuario">
            <label for="razon"><fmt:message key="buscador.proveedores.razon"/></label>
            <input type="text" id="razon" name="razon">
            <label for="cif"><fmt:message key="buscador.proveedores.cif"/></label>
            <input type="text" id="cif" name="cif">
            <label for="fechaAlta"><fmt:message key="buscador.proveedores.fecha.alta"/></label>
            <input type="date" id="fechaAlta" name="fechaAlta">
            <label for="activo"><fmt:message key="buscador.proveedores.estado"/></label>
            <select id="activo" name="activo">
                <option value="null" selected="selected"><fmt:message key="buscador.proveedores.estado.todos"/></option>
                <option value="1"><fmt:message key="buscador.proveedores.estado.activo"/></option>
                <option value="0"><fmt:message key="buscador.proveedores.estado.deshabilitado"/></option>
            </select>
            <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
            <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="buscador.proveedores.boton.buscar"/>">
        </form>
    </center>
</p>
