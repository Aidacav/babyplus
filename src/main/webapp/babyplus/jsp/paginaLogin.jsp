<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<div class="contenedor">
    <br>
    <center>
        <p>
            <form method="post" action="${pageContext.request.contextPath}/login">
                <label for="login"><fmt:message key="login.label.usuario"/></label><br>
                <input type="text" id="login" name="login"><br>
                <label for="password"><fmt:message key="login.label.password"/></label><br>
                <input type="password" id="password" name="password"><br><br>
                <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="login.button"/>">
            </form>
        </p>
        <p>
            <center>
                <a class="links" href="${pageContext.request.contextPath}/babyplus/jsp/registro/seleccionRegistro.jsp"><fmt:message key="registro.registrar"/></a>
            </center>
        </p>
        <jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/mensajes.jsp"/>
    </center>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>


