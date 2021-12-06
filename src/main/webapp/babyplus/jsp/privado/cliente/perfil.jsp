<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<div class="contenedor">
    <c:if test="${!empty sessionScope.cliente}">
        <c:set var = "formatoFecha" scope = "session">
            <fmt:message key="configuracion.formato.fecha.combo"/>
        </c:set>
        <p>
            <form method="post" action="${pageContext.request.contextPath}/babyplus/jsp/privado/cliente/accionesCliente">
                <table>
                    <tr>
                        <th colspan="2"><fmt:message key="cliente.gestion.cabecera.datos"/></th>
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
                        <td colspan="2">
                            <input type="hidden" id="id" name="id" value="${sessionScope.cliente.usuario}">
                            <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                            <input type="submit" class="botonUnico" name="actualizar" value="<fmt:message key="boton.actualizar"/>">
                        </td>
                    </tr>
                </table>
            </form>
     
            <br>

            <table>
                <tr>
                    <th colspan="4"><fmt:message key="cliente.gestion.cabecera.hijos"/></th>
                </tr>
                <tr>
                    <th><fmt:message key="cliente.gestion.hijo.nombre"/></th>
                    <th><fmt:message key="cliente.gestion.hijo.fecha"/></th>
                    <th><fmt:message key="cliente.gestion.hijo.observaciones"/></th>
                    <th><fmt:message key="cliente.gestion.hijo.acciones"/></th>
                </tr>
                <c:forEach var="hijo" items="${sessionScope.cliente.hijos}">  
                <tr>
                    <form method="post" action="${pageContext.request.contextPath}/babyplus/jsp/privado/cliente/accionesCliente">
                        <td><input type="text" id="nombre" name="nombre" required="true" value="${hijo.nombre}"/></td>
                        <td><input type="date" id="fechaNacimiento" name="fechaNacimiento" required="true" value="<fmt:formatDate pattern="${formatoFecha}" type="DATE" value="${hijo.fechaNacimiento}"/>"/></td>
                        <td><input type="text" id="observaciones" name="observaciones" maxlength="300" value="${hijo.observaciones}"/></td>
                        <td>
                            <input type="hidden" id="idPaciente" name="idPaciente" value="${hijo.id}">
                            <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                            <input type="submit" name="modificarPaciente" value="<fmt:message key="boton.actualizar"/>">
                            <input type="submit" name="eliminarPaciente" value="<fmt:message key="boton.eliminar"/>">
                        </td>
                    </form>
                </tr>
                </c:forEach>
                <tr>
                    <form method="post" action="${pageContext.request.contextPath}/babyplus/jsp/privado/cliente/accionesCliente">
                        <td><input type="text" id="nombre" name="nombre" required="true" value="${hijo.nombre}"/></td>
                        <td><input type="date" id="fechaNacimiento" name="fechaNacimiento" required="true" value="<fmt:formatDate pattern="${formatoFecha}" type="DATE" value="${hijo.fechaNacimiento}"/>"/></td>
                        <td><input type="text" id="observaciones" name="observaciones" maxlength="300" value="${hijo.observaciones}"/></td>
                        <td>
                            <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                            <input type="submit" class="botonUnico" name="crearPaciente" value="<fmt:message key="boton.añadir"/>">
                        </td>
                    </form>
                </tr>
            </table>
            <% session.removeAttribute("cliente"); %>
        </p>
    </c:if>
    <jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/mensajes.jsp"/>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>


