<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="recursos.mensajes"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/babyplus/css/estilos.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/babyplus/imagenes/favicon.ico?" rel="shortcut icon" type="image/x-icon"/>
        <title>Baby+</title>
    </head>

    <div class="header">
        <h1>Baby+</h1>

        <div class="topnav">
            <div class="izquierda">
                <c:choose>
                    <c:when test="${sessionScope.login == null}">
                        <a href="${pageContext.request.contextPath}/index.jsp"><fmt:message key="index.nav.index"/></a>
                        <a href="${pageContext.request.contextPath}/babyplus/jsp/paginaLogin.jsp"><fmt:message key="index.nav.login"/></a>
                    </c:when>    
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/babyplus/jsp/privado/principal.jsp"><fmt:message key="index.nav.principal"/></a>
                        <a href="${pageContext.request.contextPath}/logout"><fmt:message key="index.nav.salir"/></a>
                    </c:otherwise>
                </c:choose>
            </div>        
            <div class="derecha">
                <a href="${pageContext.request.contextPath}/cambioLenguaje?idioma=es&origen=${pageContext.request.requestURI}">
                    <img class="bandera" src="${pageContext.request.contextPath}/babyplus/imagenes/ES.png">
                </a>
                <a href="${pageContext.request.contextPath}/cambioLenguaje?idioma=en&origen=${pageContext.request.requestURI}">
                    <img class="bandera" src="${pageContext.request.contextPath}/babyplus/imagenes/EN.png">
                </a>
            </div>
        </div>
    </div>
</html>
