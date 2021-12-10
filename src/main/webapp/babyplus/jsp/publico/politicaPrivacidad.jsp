<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<div class="contenedor">
    <p class="fs-1 fw-bold" style="text-align:center"><fmt:message key="index.footer.politica"/></p>
    <p class="lh-sm" style="text-align:justify; margin: 40px 30px"><fmt:message key="index.footer.privacidad.texto"/></p>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>


