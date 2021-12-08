<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<div class="contenedor">
    <p>
    <c:set var = "formatoFecha" scope = "session">
        <fmt:message key="configuracion.formato.fecha.combo"/>
    </c:set>
        
    
    <c:if test="${!empty sessionScope.citas}">
        <table>
            <tr>
                <c:choose>
                    <c:when test="${cita.estado.nombre == 'REALIZADA'}">
                        <th colspan="6"><fmt:message key="cita.solicitud.cabecera.citas"/></th>
                    </c:when>
                    <c:otherwise>
                        <th colspan="5"><fmt:message key="cita.solicitud.cabecera.citas"/></th>
                    </c:otherwise>
                </c:choose>
            </tr>
        <c:forEach var="cita" items="${sessionScope.citas}">
            <tr>
                <td><fmt:formatDate pattern="${formatoFecha}" type="DATE" value="${cita.fecha}"/></td>
                <td>${cita.solicitud.paciente.nombre}</td>
                <td>${cita.solicitud.proveedor.razonSocial}</td>
                <td>${cita.notas}</td>
                <td><fmt:message key="cita.cita.estado.${cita.estado.nombre}"/></td>
                <c:if test="${cita.estado.nombre == 'REALIZADA'}">
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/babyplus/jsp/privado/cliente/gestionCitasCliente">
                            <select id="chupetes" name="chupetes" required="true">
                                <option value="0" selected="selected">0</option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                            </select>
                            <input type="text" id="mensaje" name="mensaje" maxlength="255">
                            <input type="hidden" id="idCita" name="idCita" value="${cita.id}">
                            <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                            <input type="submit" value="<fmt:message key="cita.valoracion.boton"/>">
                        </form>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </table>
        <% session.removeAttribute("citas"); %>    
    </c:if>
    
    <c:if test="${!empty sessionScope.solicitudes}">
        <table>
            <tr>
                <th colspan="5"><fmt:message key="cita.solicitud.cabecera.solicitudes"/></th>
            </tr>
        <c:forEach var="solicitud" items="${sessionScope.solicitudes}">
            <tr>
                <td>${solicitud.paciente.nombre}</td>
                <td>${solicitud.proveedor.razonSocial}</td>
                <td>${solicitud.servicio.servicio.descripcion} (${solicitud.servicio.precio} &euro;)</td>
                <td><fmt:formatDate pattern="${formatoFecha}" type="DATE" value="${solicitud.fecha}"/></td>
                <td><fmt:message key="cita.solicitud.estado.${solicitud.estado.nombre}"/></td>
            </tr>
        </c:forEach>
        </table>
        <% session.removeAttribute("solicitudes"); %>
    </c:if>
    </p>
    <jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/mensajes.jsp"/>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>


