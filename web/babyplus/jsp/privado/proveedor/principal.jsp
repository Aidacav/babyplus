<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="recursos.mensajes"/>
<div class="contenedor">
    <h1>PROVEEDOR Index</h1>
</div>
<jsp:include page="${pageContext.request.contextPath}/jsp/plantillaInferior.jsp"/>


