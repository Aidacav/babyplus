<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<div class="contenedor">
    <p>
        <form method="post" action="${pageContext.request.contextPath}/registrar">
            <table class="table">
                <thead class="table-info"></thead>
                <tbody>
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
                    <td><input type="text" id="nombre" name="nombre" required="true" value="${sessionScope.cliente.nombre}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="cliente.apellidos"/></td>
                    <td><input type="text" id="apellidos" name="apellidos" required="true" value="${sessionScope.cliente.apellidos}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="cliente.domicilio"/></td>
                    <td><input type="text" id="domicilio" name="domicilio" required="true" value="${sessionScope.cliente.domicilio}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="cliente.localidad"/></td>
                    <td><input type="text" id="localidad" name="localidad" required="true" value="${sessionScope.cliente.localidad}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="cliente.cp"/></td>
                    <td><input type="number" min="10000" max="99999" id="cp" name="cp" required="true" value="${sessionScope.cliente.cp}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="cliente.fecha.nacimiento"/></td>
                    <td><input type="date" id="fechaNacimiento" name="fechaNacimiento" required="true" value="<fmt:formatDate pattern="${formatoFecha}" type="DATE" value="${sessionScope.cliente.fechaNacimiento}"/>"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="cliente.idioma"/></td>
                    <td>
                        <select id="idioma" name="idioma">
                            <option value="ES" selected="selected"><fmt:message key="idioma.espa�ol"/></option>
                            <option value="EN"><fmt:message key="idioma.ingles"/></option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                        <input class="form-control btn btn-outline-primary" type="submit" name="registrarCliente" value="<fmt:message key="registro.registrar.cliente"/>">
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </p>
    <jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/mensajes.jsp"/>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>


