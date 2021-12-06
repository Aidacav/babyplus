<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<div class="contenedor">
    <c:if test="${!empty sessionScope.historico}">
        <p>
            <table>
                <c:forEach var="mensaje" items="${sessionScope.historico}">
                    <tr>
                        <td>${mensaje.value}</td>
                        <td>
                            <form method="post" action="${pageContext.request.contextPath}/babyplus/jsp/privado/gestorMensajes">
                                <input type="hidden" id="idDestino" name="idDestino" value="${mensaje.key}">
                                <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                                <input type="submit" class="botonUnico" name="verConversacion" value="<fmt:message key="mensajes.boton.ver.conversacion"/>">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <% session.removeAttribute("historico"); %>
        </p>
    </c:if>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>


