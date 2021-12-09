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
        <table class="table">
            <thead class="table-info">
                <tr>
                    <th colspan="4"><fmt:message key="cita.solicitud.cabecera.citas"/></th>
                </tr>
                <tr>
                    <th><fmt:message key="cita.solicitud.cabecera.cliente.fecha"/></th>
                    <th><fmt:message key="cita.solicitud.cabecera.cliente.para"/></th>
                    <th><fmt:message key="cita.solicitud.cabecera.cliente.en"/></th>
                    <th><fmt:message key="cita.solicitud.cabecera.cliente.estado"/></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="cita" items="${sessionScope.citas}">
                    <tr>
                        <td><fmt:formatDate pattern="${formatoFecha}" type="DATE" value="${cita.fecha}"/></td>
                        <td>${cita.solicitud.paciente.nombre}</td>
                        <td>${cita.solicitud.proveedor.razonSocial}</td>
                        <c:choose>
                            <c:when test="${cita.estado.nombre eq 'REALIZADA'}">
                                <td>
                                    <form method="post" action="${pageContext.request.contextPath}/babyplus/jsp/privado/cliente/gestionCitasCliente">
                                        <select id="chupetes" name="chupetes" required="true" class="form-select">
                                            <option value="0" selected="selected">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                            <option value="4">4</option>
                                            <option value="5">5</option>
                                        </select>
                                        <input type="text" id="mensaje" name="mensaje" maxlength="255" class="form-control">
                                        <input type="hidden" id="idCita" name="idCita" value="${cita.id}">
                                        <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                                        <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="cita.valoracion.boton"/>" class="form-control btn btn-outline-primary">
                                    </form>
                                </td>
                            </c:when>
                            <c:when test="${cita.estado.nombre eq 'REALIZADA'}">
                                <td class="bg-success"><fmt:message key="cita.cita.estado.${cita.estado.nombre}"/></td>                        
                            </c:when>
                            <c:when test="${cita.estado.nombre eq 'NO_REALIZADA'}">
                                <td class="bg-danger"><fmt:message key="cita.cita.estado.${cita.estado.nombre}"/></td>
                            </c:when>
                            <c:when test="${cita.estado.nombre eq 'PENDIENTE'}">
                                <td class="bg-warning"><fmt:message key="cita.cita.estado.${cita.estado.nombre}"/></td>
                            </c:when>
                            <c:otherwise>
                                <td class="bg-info"><fmt:message key="cita.cita.estado.${cita.estado.nombre}"/></td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <% session.removeAttribute("citas"); %>
        <br>
    </c:if>

    <c:if test="${!empty sessionScope.solicitudes}">
        <table class="table">
            <thead class="table-info">
                <tr>
                    <th colspan="5"><fmt:message key="cita.solicitud.cabecera.solicitudes"/></th>
                </tr>
                <tr>
                    <th><fmt:message key="cita.solicitud.cabecera.cliente.para"/></th>
                    <th><fmt:message key="cita.solicitud.cabecera.cliente.en"/></th>
                    <th><fmt:message key="cita.solicitud.cabecera.cliente.servicio"/></th>
                    <th><fmt:message key="cita.solicitud.cabecera.cliente.fecha.solicitada"/></th>
                    <th><fmt:message key="cita.solicitud.cabecera.cliente.estado"/></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="solicitud" items="${sessionScope.solicitudes}">
                    <tr>
                        <td>${solicitud.paciente.nombre}</td>
                        <td>${solicitud.proveedor.razonSocial}</td>
                        <td><fmt:message key="servicio.nombre.${solicitud.servicio.servicio.descripcion}"/> (${solicitud.servicio.precio} &euro;)</td>
                        <td><fmt:formatDate pattern="${formatoFecha}" type="DATE" value="${solicitud.fecha}"/></td>
                        <c:choose>
                            <c:when test="${solicitud.estado.nombre eq 'ENVIADA'}">
                                <td class="bg-warning"><fmt:message key="cita.solicitud.estado.${solicitud.estado.nombre}"/></td>
                            </c:when>
                            <c:when test="${solicitud.estado.nombre eq 'ACEPTADA'}">
                                <td class="bg-success"><fmt:message key="cita.solicitud.estado.${solicitud.estado.nombre}"/></td>
                            </c:when>
                            <c:otherwise>
                                <td class="bg-danger"><fmt:message key="cita.solicitud.estado.${solicitud.estado.nombre}"/></td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <% session.removeAttribute("solicitudes");%>
    </c:if>
</p>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/mensajes.jsp"/>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>


