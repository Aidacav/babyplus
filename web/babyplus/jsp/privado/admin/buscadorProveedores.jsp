<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="recursos.mensajes"/>
<p>
    <center>
        <form method="get" action="${pageContext.request.contextPath}/babyplus/jsp/privado/admin/buscarProveedores">
            <label for="usuarioProveedor"><fmt:message key="buscador.proveedores.nombre.usuario"/></label>
            <input type="text" id="usuarioProveedor" name="usuarioProveedor">
            <label for="razonProveedor"><fmt:message key="buscador.proveedores.razon"/></label>
            <input type="text" id="razonProveedor" name="razonProveedor">
            <label for="cifProveedor"><fmt:message key="buscador.proveedores.cif"/></label>
            <input type="text" id="cifProveedor" name="cifProveedor">
            <label for="fechaProveedor"><fmt:message key="buscador.proveedores.fecha.alta"/></label>
            <input type="date" id="fechaProveedor" name="fechaProveedor">
            <label for="estadoProveedor"><fmt:message key="buscador.proveedores.estado"/></label>
            <select id="estadoProveedor" name="estadoProveedor">
                <option value="null" selected="selected"><fmt:message key="buscador.proveedores.estado.todos"/></option>
                <option value="1"><fmt:message key="buscador.proveedores.estado.activo"/></option>
                <option value="0"><fmt:message key="buscador.proveedores.estado.deshabilitado"/></option>
            </select>
            <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
            <input type="submit" value="<fmt:message key="buscador.proveedores.boton.buscar"/>">
        </form>
    </center>
</p>
