<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="recursos.mensajes"/>
<div class="contenedor">
    <center><p class="error"><fmt:message key="error.generico"/></p></center>
</div>
<jsp:include page="jsp/plantillaInferior.jsp"/>
