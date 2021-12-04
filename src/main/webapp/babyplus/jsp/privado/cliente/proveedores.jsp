<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<div class="contenedor">
    <jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/privado/cliente/buscadorProveedores.jsp"/>
    <br>
    <c:if test="${!empty sessionScope.proveedores}">
        <p>
            <table>
                <tr>
                    <th><fmt:message key="cliente.buscador.cabecera.nombre"/></th>
                    <th><fmt:message key="cliente.buscador.cabecera.direccion"/></th>
                    <th><fmt:message key="cliente.buscador.cabecera.acciones"/></th>
                </tr>
                <c:forEach var="proveedor" items="${sessionScope.proveedores}">
                    <tr>
                        <td>${proveedor.razonSocial}</td>
                        <td>${proveedor.direccion}, ${proveedor.cp}, ${proveedor.localidad}</td>
                        <td>
                            <div>
                                <form method="post" action="${pageContext.request.contextPath}/babyplus/jsp/privado/cliente/accionesCliente">
                                    <input type="hidden" id="idOrigen" name="idOrigen" value="${sessionScope.usuario.id}">
                                    <input type="hidden" id="idDestino" name="idDestino" value="${proveedor.usuario}">
                                    <input type="hidden" id="nombreDestino" name="nombreDestino" value="${proveedor.razonSocial}">
                                    <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                                    <input type="submit" name="verDetalle" value="<fmt:message key="cliente.gestion.boton.detalle"/>">
                                    <input type="submit" name="pedirCita" value="<fmt:message key="cliente.gestion.boton.cita"/>">
                                    <input type="submit" name="redactarMensaje" value="<fmt:message key="cliente.gestion.boton.mensaje"/>">
                                </form>
                            </div>
                        </td>
                    </tr>
               </c:forEach>
            </table>
            <% session.removeAttribute("proveedores"); %>
        </p>
    </c:if>
    <jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/mensajes.jsp"/>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>


