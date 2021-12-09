<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<!DOCTYPE html>
<html>    
    <footer>
        <p class="text-center">Made with <img class="icono" src="${pageContext.request.contextPath}/babyplus/imagenes/heart.png"/> by WonderWeb &copy; 2021</p>
        <a href="mailto:wonderwebcontacto@wonder.com" class="links"><fmt:message key="index.footer.contacto"/></a>
        <a href="${pageContext.request.contextPath}/babyplus/jsp/publico/sobreNosotros.jsp" class="links"><fmt:message key="index.footer.nosotros"/></a>
        <a href="${pageContext.request.contextPath}/babyplus/jsp/publico/politicaPrivacidad.jsp" class="links"><fmt:message key="index.footer.politica"/></a>
    </footer>
</html>