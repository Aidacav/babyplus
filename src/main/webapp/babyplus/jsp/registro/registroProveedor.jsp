<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<div class="contenedor">
    <p>
        <form method="post" action="${pageContext.request.contextPath}/registrar">
            <table>
                <tr>
                    <td><fmt:message key="proveedor.nombre.usuario"/></td>
                    <td><input type="text" id="usuario" name="usuario" value="${sessionScope.cliente.usuario1.usuario}" required="true"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="proveedor.password"/></td>
                    <td><input type="password" id="password" name="password" required="true" value="${sessionScope.proveedor.usuario1.password}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="proveedor.razon"/></td>
                    <td><input type="text" id="razonProveedor" name="razonProveedor" required="true" value="${sessionScope.proveedor.razonSocial}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="proveedor.cif"/></td>
                    <td><input type="text" minlength="8" maxlength="8" id="cifProveedor" name="cifProveedor" required="true" value="${sessionScope.proveedor.cif}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="proveedor.direccion"/></td>
                    <td><input type="text" id="direccionProveedor" name="direccionProveedor" required="true" value="${sessionScope.proveedor.direccion}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="proveedor.localidad"/></td>
                    <td><input type="text" id="localidadProveedor" name="localidadProveedor" required="true" value="${sessionScope.proveedor.localidad}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="proveedor.cp"/></td>
                    <td><input type="number" min="10000" max="99999" id="cpProveedor" name="cpProveedor" required="true" value="${sessionScope.proveedor.cp}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="proveedor.responsable"/></td>
                    <td><input type="text" id="responsableProveedor" name="responsableProveedor" required="true" value="${sessionScope.proveedor.responsable}"/></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                        <input type="submit" name="registrarProveedor" value="<fmt:message key="administrador.gestion.proveedores.boton.actualizar"/>">
                    </td>
                </tr>
            </table>
        </form>
    </p>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>


