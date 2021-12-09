<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<div class="contenedor">
    <p>
    <form method="post" action="${pageContext.request.contextPath}/babyplus/jsp/privado/cliente/accionesCliente">
        <table class="table">
            <thead class="table-info"></thead>
            <tbody>
                <tr>
                    <td><fmt:message key="cita.solicitud.hijo"/></td>
                    <td>
                        <select class="form-select" id="idPaciente" name="idPaciente" required="true">
                            <c:forEach var="paciente" items="${sessionScope.cliente.hijos}">
                                <option value="${paciente.id}">${paciente.nombre}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="cita.solicitud.servicio"/></td>
                    <td>
                        <select class="form-select" id="idServicio" name="idServicio" required="true">
                            <c:forEach var="servicio" items="${sessionScope.proveedor.servicios}">
                                <option value="${servicio.id}"><fmt:message key="servicio.nombre.${servicio.servicio.descripcion}"/> (${servicio.precio} &euro;)</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="cita.solicitud.fecha"/></td>
                    <td><input type="date" id="fecha" name="fecha" required="true"></td>
                </tr>
                <tr>
                    <td><fmt:message key="cita.solicitud.notas"/></td>
                    <td><input type="text" id="notas" name="notas"></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="hidden" id="idCliente" name="idCliente" value="${sessionScope.cliente.usuario}">
                        <input type="hidden" id="idProveedor" name="idProveedor" value="${sessionScope.proveedor.usuario}">
                        <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                        <input class="form-control btn btn-outline-primary" type="submit" name="confirmarCita" value="<fmt:message key="boton.solicitar"/>">
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
    <jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/mensajes.jsp"/>
</p>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>


