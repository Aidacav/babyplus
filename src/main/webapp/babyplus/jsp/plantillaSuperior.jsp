<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<!DOCTYPE html>
<html>
    <head>
        <mvc:resources mapping="/webjars/**" location="/webjars/"/>
        <script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
        <script src="/webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/webjars/bootstrap/5.1.3/css/bootstrap.min.css" />
        <link href="${pageContext.request.contextPath}/babyplus/css/estilos.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/babyplus/imagenes/favicon.ico?" rel="shortcut icon" type="image/x-icon"/>
        
        
        <title>Baby+</title>
    </head>

    <div class="header">
        <h1>Baby+</h1>
    
        <nav class="navbar navbar-expand-lg navbar-light bg-dark">
            <div class="izquierda">
                <c:if test="${sessionScope.usuario != null && sessionScope.usuario.rol != null}">
                    <a href="${pageContext.request.contextPath}/babyplus/jsp/privado/${fn:toLowerCase(sessionScope.usuario.rol.descripcion)}/principal.jsp"><fmt:message key="index.nav.principal"/></a>
                    <c:choose>
                        <c:when test="${sessionScope.usuario.rol.descripcion == 'ADMIN'}">
                            <a href="${pageContext.request.contextPath}/babyplus/jsp/privado/admin/clientes.jsp"><fmt:message key="index.nav.admin.clientes"/></a>
                            <a href="${pageContext.request.contextPath}/babyplus/jsp/privado/admin/proveedores.jsp"><fmt:message key="index.nav.admin.proveedores"/></a>
                            <a href="${pageContext.request.contextPath}/babyplus/jsp/privado/admin/administrarPosts?origen=${pageContext.request.requestURI}"><fmt:message key="index.nav.admin.posts"/></a>
                        </c:when>
                        <c:when test="${sessionScope.usuario.rol.descripcion == 'PROVEEDOR'}">
                            <a href="${pageContext.request.contextPath}/babyplus/jsp/privado/proveedor/accionesProveedor?origen=${pageContext.request.requestURI}"><fmt:message key="index.nav.admin.perfil"/></a>
                            <a href="${pageContext.request.contextPath}/babyplus/jsp/privado/proveedor/gestionCitasProveedor?origen=${pageContext.request.requestURI}&procesar=solicitudes"><fmt:message key="index.nav.admin.solicitudes"/></a>
                            <a href="${pageContext.request.contextPath}/babyplus/jsp/privado/proveedor/gestionCitasProveedor?origen=${pageContext.request.requestURI}&procesar=citas"><fmt:message key="index.nav.admin.citas"/></a>
                            <a href="${pageContext.request.contextPath}/babyplus/jsp/privado/gestorMensajes?origen=${pageContext.request.requestURI}"><fmt:message key="index.nav.admin.mensajes"/></a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/babyplus/jsp/privado/cliente/accionesCliente?origen=${pageContext.request.requestURI}"><fmt:message key="index.nav.admin.perfil"/></a>
                            <a href="${pageContext.request.contextPath}/babyplus/jsp/privado/cliente/gestionCitasCliente?origen=${pageContext.request.requestURI}"><fmt:message key="index.nav.admin.citas"/></a>
                            <a href="${pageContext.request.contextPath}/babyplus/jsp/privado/cliente/proveedores.jsp"><fmt:message key="index.nav.admin.proveedores"/></a>
                            <a href="${pageContext.request.contextPath}/babyplus/jsp/privado/gestorMensajes?origen=${pageContext.request.requestURI}"><fmt:message key="index.nav.admin.mensajes"/></a>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </div>        
            <div class="derecha">
                <c:choose>
                    <c:when test="${sessionScope.usuario != null}">
                        <a href="${pageContext.request.contextPath}/logout"><fmt:message key="index.nav.logout"/></a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/babyplus/jsp/paginaLogin.jsp"><fmt:message key="index.nav.login"/></a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</html>
