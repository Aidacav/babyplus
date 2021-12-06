<%@ page import="java.util.Date" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<div class="contenedor">
        <c:if test="${!empty sessionScope.mensajes}">
            <p>
                <c:set var = "formatoFecha" scope = "session">
                    <fmt:message key="configuracion.formato.fecha"/>
                </c:set>
                <fmt:formatDate pattern="${formatoFecha}" type="DATE" value="<%=new Date(1988,6,3)%>" var="fechaInicio"/>
                <c:forEach var="mensaje" items="${sessionScope.mensajes}">
                    <fmt:formatDate pattern="${formatoFecha}" type="DATE" value="${mensaje.fecha}" var="fechaMensaje"/>
                    <c:if test="${fechaInicio ne fechaMensaje}">
                        <c:set var="fechaInicio" value="${fechaMensaje}"/>
                        <div class="mensajeCentro">
                            <c:out value="${fechaInicio}"/>
                            <hr>
                        </div>
                    </c:if>
                    <c:choose>
                        <c:when test="${sessionScope.usuario.id != mensaje.origen}">
                            <div class="mensajeIzquierda">${mensaje.mensaje}</div>
                        </c:when>
                        <c:otherwise>
                            <div class="mensajeDerecha">${mensaje.mensaje}</div>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <% session.removeAttribute("mensajes"); %>
            </p>
        </c:if>
            <p>
                <div class="mensajeCentro">
                    <fmt:formatDate pattern="${formatoFecha}" type="DATE" value="<%=new Date()%>"/>
                    <hr>
                </div>
                <form method="post" action="${pageContext.request.contextPath}/babyplus/jsp/privado/gestorMensajes">
                    <table>
                        <tr>
                            <td><textarea id="mensaje" name="mensaje" minlength="1" maxlength="255" required="true"></textarea></td>
                        </tr>
                        <tr>
                            <td>
                                <input type="hidden" id="idOrigen" name="idOrigen" value="${sessionScope.usuario.id}">
                                <input type="hidden" id="idDestino" name="idDestino" value="${sessionScope.idDestino}">
                                <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                                <input type="submit" class="botonUnico" name="enviarMensaje" value="<fmt:message key="mensajes.boton.enviar"/>">
                            </td>
                        </tr>
                    </table>
                </form>
                <script>
                    
                    window.onload = focoEnEnviar;
                    
                    function focoEnEnviar() {
                        document.getElementById("mensaje").focus();
                    }
                    
                    function cambiarEnviar(ev) {
                        this.style.height = '24px';
                        this.style.height = this.scrollHeight + 12 + 'px';
                    }

                    var mensaje = document.querySelector('textarea');
                    mensaje.addEventListener('input', cambiarEnviar);
                </script>
            </p>
            <% session.removeAttribute("idDestino"); %>
            <jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/mensajes.jsp"/>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>


