<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="recursos.mensajes"/>
<div class="contenedor">
    <jsp:include page="${pageContext.request.contextPath}/jsp/privado/admin/buscadorClientes.jsp"/>
    <br>
    <c:if test="${!empty sessionScope.clientes}">
        <c:set var = "formatoFecha" scope = "session">
            <fmt:message key="configuracion.formato.fecha"/>
        </c:set>
        <p>
            <table>
                <tr>
                    <th><fmt:message key="administrador.gestion.clientes.cabecera.id"/></th>
                    <th><fmt:message key="administrador.gestion.clientes.cabecera.usuario"/></th>
                    <th><fmt:message key="administrador.gestion.clientes.cabecera.nombre"/></th>
                    <th><fmt:message key="administrador.gestion.clientes.cabecera.apellidos"/></th>
                    <th><fmt:message key="administrador.gestion.clientes.cabecera.fecha.registro"/></th>
                    <th><fmt:message key="administrador.gestion.clientes.cabecera.activo"/></th>
                    <th><fmt:message key="administrador.gestion.clientes.cabecera.acciones"/></th>
                </tr>
                <c:forEach var="cliente" items="${sessionScope.clientes}">
                    <tr>
                        <td>${cliente.usuario1.id}</td>
                        <td>${cliente.usuario1.usuario}</td>
                        <td>${cliente.nombre}</td>
                        <td>${cliente.apellidos}</td>
                        <td>
                            <fmt:formatDate pattern="${formatoFecha}" type="DATE" value="${cliente.usuario1.fechaAlta}"/>
                        </td>
                        <td>
                            <center>
                                <c:choose>
                                    <c:when test="${cliente.usuario1.activo == true}">
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
                                <form method="post" action="${pageContext.request.contextPath}/babyplus/jsp/privado/admin/accionesCliente">
                                    <input type="hidden" id="idCliente" name="idCliente" value="${cliente.usuario}">
                                    <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                                    <input type="submit" name="verDetalle" value="<fmt:message key="administrador.gestion.clientes.boton.ver.detalle"/>">
                                    <c:choose>
                                        <c:when test="${cliente.usuario1.activo == true}">
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
            <% session.removeAttribute("clientes"); %>
        </p>
    </c:if>
    <jsp:include page="${pageContext.request.contextPath}/jsp/mensajes.jsp"/>
</div>
<jsp:include page="${pageContext.request.contextPath}/jsp/plantillaInferior.jsp"/>


