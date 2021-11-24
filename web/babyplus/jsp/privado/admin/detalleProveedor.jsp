<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="recursos.mensajes"/>
<div class="contenedor">
    <c:if test="${!empty sessionScope.proveedor}">
        <c:set var = "formatoFecha" scope = "session">
            <fmt:message key="configuracion.formato.fecha.combo"/>
        </c:set>
        <p>
            <form method="post" action="${pageContext.request.contextPath}/babyplus/jsp/privado/admin/accionesProveedor">
                <table>
                    <tr>
                        <td><fmt:message key="proveedor.id"/></td>
                        <td><input type="number" value="${sessionScope.proveedor.usuario}" disabled="disabled"/></td>
                    </tr>
                    <tr>
                        <td><fmt:message key="proveedor.nombre.usuario"/></td>
                        <td><input type="text" value="${sessionScope.proveedor.usuario1.usuario}" disabled="disabled"/></td>
                    </tr>
                    <tr>
                        <td><fmt:message key="proveedor.password"/></td>
                        <td><input type="password" id="passwordProveedor" name="passwordProveedor" required="true" value="${sessionScope.proveedor.usuario1.password}"/></td>
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
                            <input type="hidden" id="idProveedor" name="idProveedor" value="${sessionScope.proveedor.usuario}">
                            <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                            <input type="submit" name="actualizar" value="<fmt:message key="administrador.gestion.proveedores.boton.actualizar"/>">
                        </td>
                    </tr>
                </table>
            </form>
            <% session.removeAttribute("proveedor"); %>
        </p>
    </c:if>
    <jsp:include page="${pageContext.request.contextPath}/jsp/mensajes.jsp"/>
</div>
<jsp:include page="${pageContext.request.contextPath}/jsp/plantillaInferior.jsp"/>


