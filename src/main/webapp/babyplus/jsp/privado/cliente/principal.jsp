<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<div class="contenedor">
    <h1 class="titleIndex"><fmt:message key="principal.bienvenido"/> ${sessionScope.usuario.usuario}</h1>

    <div class="card-group">
        <c:forEach var="post" items="${sessionScope.posts}">
            <div class="card" style="width: 18rem;">
                <img src="${pageContext.request.contextPath}/babyplus/imagenes/heart.png" class="card-img-top" alt="No Disponible">
                <div class="card-body">
                    <h5 class="card-title">Nueva Notificación</h5>
                    <p class="card-text">${post.post}</p>
                </div>
            </div>
        </c:forEach>
    </div>

    <jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/mensajes.jsp"/>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>


