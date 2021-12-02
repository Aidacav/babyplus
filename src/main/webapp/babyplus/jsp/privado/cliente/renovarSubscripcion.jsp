<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaSuperior.jsp"/>
<fmt:setLocale value="${sessionScope.idioma}"/>
<fmt:setBundle basename="mensajes"/>
<div class="contenedor">
    <p>
        <form method="post" action="${pageContext.request.contextPath}/babyplus/jsp/privado/cliente/accionesCliente/renovarSubscripcion">
            <table>
                <tr>
                    <td><fmt:message key="subscricion.tarjeta"/></td>
                    <td><input type="number" id="tarjeta" name="tarjeta" min="1000000000000000" max="9999999999999999" required="true"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="subscricion.mes"/></td>
                    <td>
                        <select id="mes" name="mes" required="true">
                            <option value="1" selected="selected">01</option>
                            <option value="2">02</option>
                            <option value="3">03</option>
                            <option value="4">04</option>
                            <option value="5">05</option>
                            <option value="6">06</option>
                            <option value="7">07</option>
                            <option value="8">08</option>
                            <option value="9">09</option>
                            <option value="10">10</option>
                            <option value="11">11</option>
                            <option value="12">12</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="subscricion.año"/></td>
                    <td>
                        <select id="año" name="año" required="true">
                            <option value="2021" selected="selected">2021</option>
                            <option value="2022">2022</option>
                            <option value="2023">2023</option>
                            <option value="2024">2024</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="subscricion.cvv"/></td>
                    <td><input type="number" id="cvv" name="cvv" min="000" max="999" required="true"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="subscricion.tipo"/></td>
                    <td>
                        <select id="tipo" name="tipo" required="true">
                            <option value="MENSUAL" selected="selected"><fmt:message key="subscricion.tipo.mensual"/></option>
                            <option value="ANUAL"><fmt:message key="subscricion.tipo.anual"/></option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="hidden" id="PENDIENTE" name="PENDIENTE" value="true">
                        <input type="hidden" id="idCliente" name="idCliente" value="${sessionScope.usuario.id}">
                        <input type="hidden" id="origen" name="origen" value="${pageContext.request.requestURI}">
                        <input type="submit" value="<fmt:message key="subscricion.contratar"/>">
                    </td>
                </tr>
            </table>
        </form>
    </p>
    <jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/mensajes.jsp"/>
</div>
<jsp:include page="${pageContext.request.contextPath}/babyplus/jsp/plantillaInferior.jsp"/>


