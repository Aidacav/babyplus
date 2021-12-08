<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<div class="contenedor">
    <p>
        <c:if test="${!empty sessionScope.citas}">
        <c:set var = "formatoFecha" scope = "session">
            <fmt:message key="configuracion.formato.fecha.combo"/>
        </c:set>
        <table>
            <tr>
                <th colspan="7"><fmt:message key="cita.solicitud.cabecera.citas"/></th>
            </tr>
            <tr>
                <th><fmt:message key="cita.solicitud.cabecera.proveedor.fecha"/></th>
                <th><fmt:message key="cita.solicitud.cabecera.proveedor.estado"/></th>
                <th><fmt:message key="cita.solicitud.cabecera.proveedor.cliente"/></th>
                <th><fmt:message key="cita.solicitud.cabecera.proveedor.paciente"/></th>
                <th><fmt:message key="cita.solicitud.cabecera.proveedor.servicio"/></th>
                <th><fmt:message key="cita.solicitud.cabecera.proveedor.notas"/></th>
                <th><fmt:message key="cita.solicitud.cabecera.proveedor.acciones"/></th>
            </tr>
        <c:forEach var="cita" items="${sessionScope.citas}">
            <tr>
                <td><fmt:formatDate pattern="${formatoFecha}" type="DATE" value="${cita.fecha}"/></td>
                <td><fmt:message key="cita.cita.estado.${cita.estado.nombre}"/></td>
                <td>${cita.solicitud.cliente.nombre} ${cita.solicitud.cliente.apellidos}</td>
                <td>${cita.solicitud.paciente.nombre}</td>
                <td><fmt:message key="servicio.nombre.${cita.solicitud.servicio.servicio.descripcion}"/> (${cita.solicitud.servicio.precio} &euro;)</td>
                <c:choose>
                    <c:when test="${cita.estado.nombre eq 'PENDIENTE'}">
                        <form method="post" action="${pageContext.request.contextPath}/babyplus/jsp/privado/proveedor/gestionCitasProveedor">
                            <td>
                                <input type="text" id="notas" name="notas" maxlength="255">
                            </td>
                            <td>
                                <input type="hidden" id="idCita" name="idCita" value="${cita.id}">
                                <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                                <input type="submit" name="cerrarCita" value="<fmt:message key="cita.cita.cerrar.boton"/>">
                                <input type="submit" name="cancelarCita" value="<fmt:message key="cita.cita.cancelar.boton"/>">
                            </td>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <td>${cita.notas}</td>
                        <td></td>
                    </c:otherwise>
                </c:choose>
            </tr>
        </c:forEach>
        </table>
        <% session.removeAttribute("citas"); %>    
        </c:if>
    </p>
    <jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/mensajes.jsp"/>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>


