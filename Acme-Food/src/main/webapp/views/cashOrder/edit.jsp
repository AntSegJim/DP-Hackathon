<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<security:authorize access="hasRole('CUSTOMER')">
<form:form action="cashOrder/customer/edit.do?restaurantId=${restaurant.id }" modelAttribute="cashOrder">

<jstl:if test="${not empty exception}">
		<p style="color:red"> <spring:message code="notification.error" /> </p>
</jstl:if>

<form:hidden path="id"/>
<form:hidden path="version"/>

<acme:textbox code="cashOrder.SenderMoment" path="senderMoment"/>
<acme:selectWithoutNullOption id="platos" items="${foodDishes}" itemLabel="name" code="cashOrder.foodDishes" path="foodDisheses" onchange="myFunction()"/>
<form:label path="choice"><spring:message code="cashOrder.choice" />:</form:label>
<form:select path="choice">
		<form:option value="0" label="Take away" />	
		<form:option value="1" label="For send" />	
	</form:select>
	<form:errors path="choice"/>
<br/>
<form:label path="draftMode"><spring:message code="cashOrder.draftMode" />:</form:label>
<form:select path="draftMode">
		<form:option value="1" label="Yes" />	
		<form:option value="0" label="No" />	
	</form:select>
	<form:errors path="draftMode"/>
<br/>
<br/>
<input type="submit" name="save" 
	value="<spring:message code="cashOrder.save" />" />
	
<input type="button" name="cancel" value="<spring:message code="cashOrder.cancel" />"
			onclick="javascript: relativeRedir('restaurant/customer/list.do');" />

</form:form>
<h1 id="precio"></h1>
<script>
	function myFunction() {
		var platos = $('select#platos').val();
		//var precio = 0;
		var precio = "";
		for (var x=0;x<platos.length;x++){
			precio += platos[x] + "<br>";
		}
		document.getElementById("precio").innerHTML = precio;


	}
</script>

</security:authorize>
