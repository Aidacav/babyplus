<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<div class="contenedor">
    <c:set var = "formatoFecha" scope = "session">
        <fmt:message key="configuracion.formato.fecha.combo"/>
    </c:set>
    <h1 class="titleIndex"><fmt:message key="principal.bienvenido"/> ${sessionScope.usuario.usuario}</h1>
    <div class="carousel slide" data-ride="carousel">
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img class="d-block" src="${pageContext.request.contextPath}/babyplus/imagenes/carrusel/carrusel_0.jpg">
                <div class="carousel-caption d-none d-md-block">
                    <h5><fmt:message key="carrusel.post.cabecera"/></h5>
                    <p><fmt:message key="carrusel.post.mensaje"/></p>
                </div>
            </div>
            <c:forEach var="entrada" items="${sessionScope.posts}">  
                <div class="carousel-item">
                    <img class="d-block" src="${pageContext.request.contextPath}/babyplus/imagenes/carrusel/carrusel_${entrada.value}.jpg">
                    <div class="carousel-caption">
                        <h5><fmt:formatDate pattern="${formatoFecha}" type="DATE" value="${entrada.key.fechaCreacion}"/></h5>
                        <p>${entrada.key.post}</p>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <script>
        $(document).ready(function () {
            $('.carousel').carousel({
                interval: 2500
            });
        });
    </script>
    <jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/mensajes.jsp"/>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>


