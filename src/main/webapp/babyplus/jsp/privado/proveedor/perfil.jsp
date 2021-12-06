<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<div class="contenedor">
    <c:if test="${!empty sessionScope.proveedor}">
        <c:set var = "formatoFecha" scope = "session">
            <fmt:message key="configuracion.formato.fecha.combo"/>
        </c:set>
        <p>
            <form method="post" action="${pageContext.request.contextPath}/babyplus/jsp/privado/proveedor/accionesProveedor">
                <table>
                    <tr>
                        <th colspan="2"><fmt:message key="proveedor.gestion.cabecera.datos"/></th>
                    </tr>
                    <tr>
                        <td><fmt:message key="proveedor.password"/></td>
                        <td><input type="password" id="password" name="password" required="true" value="${sessionScope.proveedor.usuario1.password}"/></td>
                    </tr>
                    <tr>
                        <td><fmt:message key="proveedor.razon"/></td>
                        <td><input type="text" id="razon" name="razon" required="true" value="${sessionScope.proveedor.razonSocial}"/></td>
                    </tr>
                    <tr>
                        <td><fmt:message key="proveedor.cif"/></td>
                        <td><input type="text" minlength="8" maxlength="8" id="cif" name="cif" required="true" value="${sessionScope.proveedor.cif}"/></td>
                    </tr>
                    <tr>
                        <td><fmt:message key="proveedor.direccion"/></td>
                        <td><input type="text" id="direccion" name="direccion" required="true" value="${sessionScope.proveedor.direccion}"/></td>
                    </tr>
                    <tr>
                        <td><fmt:message key="proveedor.localidad"/></td>
                        <td><input type="text" id="localidad" name="localidad" required="true" value="${sessionScope.proveedor.localidad}"/></td>
                    </tr>
                    <tr>
                        <td><fmt:message key="proveedor.cp"/></td>
                        <td><input type="number" min="10000" max="99999" id="cp" name="cp" required="true" value="${sessionScope.proveedor.cp}"/></td>
                    </tr>
                    <tr>
                        <td><fmt:message key="proveedor.responsable"/></td>
                        <td><input type="text" id="responsable" name="responsable" required="true" value="${sessionScope.proveedor.responsable}"/></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="hidden" id="id" name="id" value="${sessionScope.proveedor.usuario}">
                            <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                            <input type="submit" class="botonUnico" name="actualizar" value="<fmt:message key="boton.actualizar"/>">
                        </td>
                    </tr>
                </table>
            </form>
     
            <br>

            <table>
                <tr>
                    <th colspan="4"><fmt:message key="proveedor.gestion.cabecera.servicios"/></th>
                </tr>
                <tr>
                    <th><fmt:message key="proveedor.gestion.servicio.descripcion.generica"/></th>
                    <th><fmt:message key="proveedor.gestion.servicio.precio"/></th>
                    <th><fmt:message key="proveedor.gestion.servicio.descripcion"/></th>
                    <th><fmt:message key="proveedor.gestion.servicio.acciones"/></th>
                </tr>
                <c:forEach var="servicio" items="${sessionScope.proveedor.servicios}">  
                <tr>
                    <form method="post" action="${pageContext.request.contextPath}/babyplus/jsp/privado/proveedor/accionesProveedor">
                        <td>${servicio.servicio.descripcion}</td>
                        <td><input type="number" min="0" id="precio" name="precio" required="true" value="${servicio.precio}"/></td>
                        <td><input type="text" id="descripcion" name="descripcion" maxlength="255" value="${servicio.descripcion}"/></td>
                        <td>
                            <input type="hidden" id="idServicio" name="idServicio" value="${servicio.id}">
                            <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                            <input type="submit" name="modificarServicio" value="<fmt:message key="boton.actualizar"/>">
                            <input type="submit" name="eliminarServicio" value="<fmt:message key="boton.eliminar"/>">
                        </td>
                    </form>
                </tr>
                </c:forEach>
                <tr>
                    <form method="post" action="${pageContext.request.contextPath}/babyplus/jsp/privado/proveedor/accionesProveedor">
                        <td>
                            <select id="tipo" name="tipo">
                                <c:forEach var="tipo" items="${sessionScope.catalogoServicios}">
                                    <option value="${tipo.id}">${tipo.descripcion}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td><input type="number" min="0" id="precio" name="precio" required="true"/></td>
                        <td><input type="text" id="descripcion" name="descripcion" maxlength="255"/></td>
                        <td>
                            <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                            <input type="submit" class="botonUnico" name="crearServicio" value="<fmt:message key="boton.a�adir"/>">
                        </td>
                    </form>
                </tr>
            </table>
            <% session.removeAttribute("proveedor"); %>
            <% session.removeAttribute("catalogoServicios"); %>
        </p>
    </c:if>
    <jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/mensajes.jsp"/>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>

