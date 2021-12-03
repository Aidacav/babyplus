<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}//babyplus/jsp/plantillaSuperior.jsp"/>
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
                                <form method="post" action="${pageContext.request.contextPath}/babyplus/jsp/privado/admin/accionesProveedor">
                                    <input type="hidden" id="idCliente" name="idCliente" value="${proveedor.usuario}">
                                    <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                                    <input type="submit" name="verDetalle" value="<fmt:message key="administrador.gestion.clientes.boton.ver.detalle"/>">
                                    <c:choose>
                                        <c:when test="${proveedor.usuario1.activo == true}">
                                            <input type="submit" name="cambiarEstado" value="<fmt:message key="administrador.gestion.clientes.boton.desactivar"/>">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="submit" name="cambiarEstado" value="<fmt:message key="administrador.gestion.clientes.boton.activar"/>">
                                        </c:otherwise>
                                    </c:choose>
                                </form>
                            </div>
                            <div>
                                <form method="post" action="${pageContext.request.contextPath}/babyplus/jsp/privado/admin/accionesProveedor">
                                    <input type="hidden" id="idCliente" name="idCliente" value="${proveedor.usuario}">
                                    <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                                    <input type="submit" name="verDetalle" value="<fmt:message key="administrador.gestion.clientes.boton.ver.detalle"/>">
                                    <c:choose>
                                        <c:when test="${proveedor.usuario1.activo == true}">
                                            <input type="submit" name="cambiarEstado" value="<fmt:message key="administrador.gestion.clientes.boton.desactivar"/>">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="submit" name="cambiarEstado" value="<fmt:message key="administrador.gestion.clientes.boton.activar"/>">
                                        </c:otherwise>
                                    </c:choose>
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


