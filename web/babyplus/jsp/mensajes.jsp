<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="recursos.mensajes"/>
<p>
    <c:if test="${!empty error}">
        <p class="error" name="error">
            <fmt:message><c:out value="${error}"/></fmt:message>
            <% session.removeAttribute("error"); %>
        </p>
    </c:if>
    
    <c:if test="${!empty mensaje}">
        <p class="mensaje" name="mensaje">
            <fmt:message><c:out value="${mensaje}"/></fmt:message>
            <% session.removeAttribute("mensaje"); %>
        </p>
    </c:if>
</p>
