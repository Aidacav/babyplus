<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<div class="contenedor">
    <jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/privado/admin/buscadorProveedores.jsp"/>
    <br>
    <c:if test="${!empty sessionScope.proveedores}">
        <c:set var = "formatoFecha" scope = "session">
            <fmt:message key="configuracion.formato.fecha"/>
        </c:set>
        <p>
        <table class="table">
            <thead class="table-info">
                <tr>
                    <th><fmt:message key="administrador.gestion.proveedores.cabecera.id"/></th>
                    <th><fmt:message key="administrador.gestion.proveedores.cabecera.usuario"/></th>
                    <th><fmt:message key="administrador.gestion.proveedores.cabecera.razon"/></th>
                    <th><fmt:message key="administrador.gestion.proveedores.cabecera.cif"/></th>
                    <th><fmt:message key="administrador.gestion.proveedores.cabecera.fecha.registro"/></th>
                    <th><fmt:message key="administrador.gestion.proveedores.cabecera.activo"/></th>
                    <th><fmt:message key="administrador.gestion.proveedores.cabecera.acciones"/></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="proveedor" items="${sessionScope.proveedores}">
                    <tr>
                        <td>${proveedor.usuario1.id}</td>
                        <td>${proveedor.usuario1.usuario}</td>
                        <td>${proveedor.razonSocial}</td>
                        <td>${proveedor.cif}</td>
                        <td>
                            <fmt:formatDate pattern="${formatoFecha}" type="DATE" value="${proveedor.usuario1.fechaAlta}"/>
                        </td>
                        <td>
                <center>
                    <c:choose>
                        <c:when test="${proveedor.usuario1.activo == true}">
                            <img class="icono" src="${pageContext.request.contextPath}/babyplus/imagenes/ok.png">
                        </c:when>
                        <c:otherwise>
                            <img class="icono" src="${pageContext.request.contextPath}/babyplus/imagenes/ko.png">
                        </c:otherwise>
                    </c:choose>
                </center>
                </td>
                <td>
                    <div>
                        <form method="post" action="${pageContext.request.contextPath}/babyplus/jsp/privado/admin/administrarProveedor">
                            <input type="hidden" id="id" name="id" value="${proveedor.usuario}">
                            <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                            <input class="btn btn-outline-primary" type="submit" name="verDetalle" value="<fmt:message key="administrador.gestion.clientes.boton.ver.detalle"/>">
                            <c:choose>
                                <c:when test="${proveedor.usuario1.activo == true}">
                                    <input class="btn btn-outline-primary" type="submit" name="cambiarEstado" value="<fmt:message key="administrador.gestion.clientes.boton.desactivar"/>">
                                </c:when>
                                <c:otherwise>
                                    <input class="btn btn-outline-primary" type="submit" name="cambiarEstado" value="<fmt:message key="administrador.gestion.clientes.boton.activar"/>">
                                </c:otherwise>
                            </c:choose>
                        </form>
                    </div>
                </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <% session.removeAttribute("proveedores");%>
    </p>
</c:if>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/mensajes.jsp"/>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>


