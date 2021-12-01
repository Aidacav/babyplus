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
                    <td><fmt:message key="cliente.nombre.usuario"/></td>
                    <td><input type="text" id="usuario" name="usuario" value="${sessionScope.cliente.usuario1.usuario}" required="true"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="cliente.password"/></td>
                    <td><input type="password" id="password" name="password" required="true" value="${sessionScope.cliente.usuario1.password}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="cliente.nombre"/></td>
                    <td><input type="text" id="nombreCliente" name="nombreCliente" required="true" value="${sessionScope.cliente.nombre}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="cliente.apellidos"/></td>
                    <td><input type="text" id="apellidosCliente" name="apellidosCliente" required="true" value="${sessionScope.cliente.apellidos}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="cliente.domicilio"/></td>
                    <td><input type="text" id="domicilioCliente" name="domicilioCliente" required="true" value="${sessionScope.cliente.domicilio}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="cliente.localidad"/></td>
                    <td><input type="text" id="localidadCliente" name="localidadCliente" required="true" value="${sessionScope.cliente.localidad}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="cliente.cp"/></td>
                    <td><input type="number" min="10000" max="99999" id="cpCliente" name="cpCliente" required="true" value="${sessionScope.cliente.cp}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="cliente.fecha.nacimiento"/></td>
                    <td><input type="date" id="fechaCliente" name="fechaCliente" required="true" value="<fmt:formatDate pattern="${formatoFecha}" type="DATE" value="${sessionScope.cliente.fechaNacimiento}"/>"/></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                        <input type="submit" name="registrarCliente" value="<fmt:message key="registro.registrar.cliente"/>">
                    </td>
                </tr>
            </table>
        </form>
    </p>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>


