<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<div class="contenedor">
    <p>
    <form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/registrar">
        <table class="table">
            <thead class="table-info"></thead>
            <tbody>
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
                    <td><input type="text" id="razon" name="razon" required="true" value="${sessionScope.proveedor.razonSocial}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="proveedor.cif"/></td>
                    <td><input type="text" minlength="8" maxlength="8" id="cif" name="cif" required="true" value="${sessionScope.proveedor.cif}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="proveedor.direccion"/></td>
                    <td><input type="text" id="direccion" name="direccion" required="true" value="${sessionScope.proveedor.direccion}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="proveedor.localidad"/></td>
                    <td><input type="text" id="localidad" name="localidad" required="true" value="${sessionScope.proveedor.localidad}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="proveedor.cp"/></td>
                    <td><input type="number" min="10000" max="99999" id="cp" name="cp" required="true" value="${sessionScope.proveedor.cp}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="proveedor.responsable"/></td>
                    <td><input type="text" id="responsable" name="responsable" required="true" value="${sessionScope.proveedor.responsable}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="proveedor.logo"/></td>
                    <td><input type="file" id="logo" name="logo" accept="image/png, image/jpeg" required="true"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="cliente.idioma"/></td>
                    <td>
                        <select id="idioma" name="idioma">
                            <option value="ES" selected="selected"><fmt:message key="idioma.español"/></option>
                            <option value="EN"><fmt:message key="idioma.ingles"/></option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                        <input class="form-control btn btn-outline-primary" type="submit" name="registrarProveedor" value="<fmt:message key="registro.registrar.proveedor"/>">
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</p>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/mensajes.jsp"/>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>


