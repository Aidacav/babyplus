<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<div class="contenedor">
    <p>
        <c:if test="${!empty sessionScope.solicitudes}">
        <c:set var = "formatoFecha" scope = "session">
            <fmt:message key="configuracion.formato.fecha.combo"/>
        </c:set>
        <table>
            <tr>
                <th><fmt:message key="cita.solicitud.cabecera.proveedor.cliente"/></th>
                <th><fmt:message key="cita.solicitud.cabecera.proveedor.paciente"/></th>
                <th><fmt:message key="cita.solicitud.cabecera.proveedor.fecha"/></th>
                <th><fmt:message key="cita.solicitud.cabecera.proveedor.servicio"/></th>
                <th><fmt:message key="cita.solicitud.cabecera.proveedor.notas"/></th>
                <th><fmt:message key="cita.solicitud.cabecera.proveedor.estado"/></th>
                <th><fmt:message key="cita.solicitud.cabecera.proveedor.acciones"/></th>
            </tr>
        <c:forEach var="solicitud" items="${sessionScope.solicitudes}">
            <tr>
                <td>${solicitud.cliente.nombre}</td>
                <td>${solicitud.paciente.nombre}</td>
                <td><fmt:formatDate pattern="${formatoFecha}" type="DATE" value="${solicitud.fecha}"/></td>
                <td><fmt:message key="servicio.nombre.${solicitud.servicio.servicio.descripcion}"/> (${solicitud.servicio.precio} &euro;)</td>
                <td>${solicitud.notas}</td>
                <td><fmt:message key="cita.solicitud.estado.${solicitud.estado.nombre}"/></td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/babyplus/jsp/privado/proveedor/gestionCitasProveedor">
                        <input type="hidden" id="idSolicitud" name="idSolicitud" value="${solicitud.id}">
                        <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                        <input type="submit" name="aceptarSolicitud" value="<fmt:message key="cita.solicitud.aceptar.boton"/>">
                        <input type="submit" name="rechazarSolicitud" value="<fmt:message key="cita.solicitud.rechazar.boton"/>">
                    </form>
                </td>
                
                
            </tr>
        </c:forEach>
        </table>
        <% session.removeAttribute("solicitudes"); %>
        </c:if>
    </p>
    <jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/mensajes.jsp"/>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>


