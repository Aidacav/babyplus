<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<div class="contenedor">
    <center>
        <p>
            <a id="links" href="${pageContext.request.contextPath}/babyplus/jsp/registro/registroCliente.jsp"><fmt:message key="registro.registrar.cliente"/></a>
            <a id="links" href="${pageContext.request.contextPath}/babyplus/jsp/registro/registroProveedor.jsp"><fmt:message key="registro.registrar.proveedor"/></a>
        </p>
    </center>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>


