<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<div class="contenedor">
    <c:set var = "formatoFecha" scope = "session">
        <fmt:message key="configuracion.formato.fecha.combo"/>
    </c:set>
    <p>
    <table class="table">
        <thead class="table-info">
            <tr>
                <th><fmt:message key="administrador.gestion.posts.cabecera.ambito"/></th>
                <th><fmt:message key="administrador.gestion.posts.cabecera.fecha.creacion"/></th>
                <th><fmt:message key="administrador.gestion.posts.cabecera.fecha.expiracion"/></th>
                <th><fmt:message key="administrador.gestion.posts.cabecera.post"/></th>
                <th><fmt:message key="administrador.gestion.posts.cabecera.acciones"/></th>
            </tr>
        </thead>
        <tbody>
            <tr>
        <form method="post" action="${pageContext.request.contextPath}/babyplus/jsp/privado/admin/administrarPosts">
            <td>
                <select id="ambito" name="ambito" class="form-select">
                    <option value=""></option>
                    <c:forEach var="ambitoTipo" items="${sessionScope.ambitos}">
                        <option value="${ambitoTipo}">${ambitoTipo}</option>
                    </c:forEach>
                </select>
            </td>
            <td></td>
            <td>
                <input type="date" id="fechaExpiracion" name="fechaExpiracion">
            </td>
            <td>
                <input type="text" id="post" name="post" minlength="1" maxlength="50" required="true">
            </td>
            <td>
                <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                <input class="form-control btn btn-outline-primary" type="submit" value="<fmt:message key="administrador.gestion.posts.boton.publicar"/>">
            </td>
        </form>
        </tr>
        <c:forEach var="post" items="${sessionScope.posts}">
            <tr>
                <td>${post.ambito}</td>
                <td><fmt:formatDate pattern="${formatoFecha}" type="DATE" value="${post.fechaCreacion}"/></td>
                <td><fmt:formatDate pattern="${formatoFecha}" type="DATE" value="${post.fechaExpiracion}"/></td>
                <td>${post.post}</td>
                <td></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <% session.removeAttribute("posts"); %>
    <% session.removeAttribute("ambito");%>
</p>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/mensajes.jsp"/>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>


