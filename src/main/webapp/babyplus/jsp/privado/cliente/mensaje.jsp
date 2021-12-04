<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<div class="contenedor">
    <p>
        <form method="post" action="${pageContext.request.contextPath}/babyplus/jsp/privado/cliente/accionesCliente">
            <table>
                <tr>
                    <td><fmt:message key="cliente.gestion.boton.mensaje"/></td>
                    <td>${sessionScope.nombreDestino}</td>
                </tr>
                <tr>
                    <td colspan="2"><textarea id="mensaje" name="mensaje" maxlength="500" minlength="1" required="true"></textarea></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="hidden" id="idOrigen" name="idOrigen" value="${sessionScope.usuario.id}">
                        <input type="hidden" id="idDestino" name="idDestino" value="${sessionScope.idDestino}">
                        <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                        <input type="submit" name="enviarMensaje" value="<fmt:message key="cliente.gestion.boton.mensaje"/>">
                    </td>
                </tr>
            </table>
        </form>
    </p>
    <jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/mensajes.jsp"/>
    <script>
        function resizeTextarea(ev) {
            this.style.height = '24px';
            this.style.height = this.scrollHeight + 12 + 'px';
        }

        var te = document.querySelector('textarea');
        te.addEventListener('input', resizeTextarea);
    </script>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>


