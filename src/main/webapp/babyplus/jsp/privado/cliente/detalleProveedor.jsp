<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<div class="contenedor">
    <c:if test="${!empty sessionScope.proveedor}">
        <p>
        <form method="post" action="${pageContext.request.contextPath}/babyplus/jsp/privado/cliente/accionesCliente">
            <table class="table">
                <thead class="table-info"></thead>
                <tbody>
                    <tr>
                        <td rowspan="8">
                <center><img alt="img" src="data:image/jpeg;base64,${sessionScope.logo}"/></center>
                </td>
                </tr>
                <tr>
                    <td><fmt:message key="proveedor.razon"/></td>
                    <td>${sessionScope.proveedor.razonSocial}</td>
                </tr>
                <tr>
                    <td><fmt:message key="proveedor.direccion"/></td>
                    <td>${sessionScope.proveedor.direccion}</td>
                </tr>
                <tr>
                    <td><fmt:message key="proveedor.localidad"/></td>
                    <td>${sessionScope.proveedor.localidad}</td>
                </tr>
                <tr>
                    <td><fmt:message key="proveedor.cp"/></td>
                    <td>${sessionScope.proveedor.cp}</td>
                </tr>
                <tr>
                    <td><fmt:message key="proveedor.responsable"/></td>
                    <td>${sessionScope.proveedor.responsable}</td>
                </tr>
                <tr>
                    <td><fmt:message key="proveedor.servicios"/></td>
                    <td>
                        <c:forEach var="servicio" items="${sessionScope.proveedor.servicios}">
                            <fmt:message key="servicio.nombre.${servicio.servicio.descripcion}"/> (${servicio.precio} &euro;)<br>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="hidden" id="idOrigen" name="idOrigen" value="${sessionScope.usuario.id}">
                        <input type="hidden" id="idDestino" name="idDestino" value="${sessionScope.proveedor.usuario}">
                        <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                        <input class="btn btn-outline-primary" type="submit" name="pedirCita" value="<fmt:message key="cliente.gestion.boton.cita"/>">
                        <input class="btn btn-outline-primary" type="submit" name="verConversacion" value="<fmt:message key="mensajes.boton.enviar"/>">
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
        <% session.removeAttribute("proveedor"); %>
        <% session.removeAttribute("logo");%>
    </p>
</c:if>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/mensajes.jsp"/>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>


