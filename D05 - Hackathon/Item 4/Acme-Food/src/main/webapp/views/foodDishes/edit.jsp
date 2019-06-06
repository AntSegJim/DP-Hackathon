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


<security:authorize access="hasRole('RESTAURANT')">
<form:form action="foodDishes/restaurant/edit.do" modelAttribute="foodDishes">

<jstl:if test="${not empty exception}">
		<p style="color:red"> <spring:message code="foodDishes.error" /> </p>
</jstl:if>

<form:hidden path="id"/>
<form:hidden path="version"/>

<acme:textbox code="foodDishes.name" path="name"/>
<acme:textarea code="foodDishes.description" path="description"/>
<acme:textbox code="foodDishes.picture" path="pictures"/>
<acme:textbox code="foodDishes.price" path="price"/>
<form:label path="type"><spring:message code="foodDishes.type" />:</form:label>
<form:select path="type">
		<form:option value="0" label="Breakfast" />	
		<form:option value="1" label="Lunch" />	
		<form:option value="2" label="Dinner" />	
		<form:option value="3" label="Dessert" />	
	</form:select>
	<form:errors path="type"/>
<br/>
<acme:textarea code="foodDishes.ingredients" path="ingredients"/>
<br/>
<input type="submit" name="save" 
	value="<spring:message code="foodDishes.save" />" />
<jstl:if test="${foodDishes.id ne 0 }">	
<input type="submit" name="delete" 
	value="<spring:message code="foodDishes.delete" />" />
</jstl:if>	
<input type="button" name="cancel" value="<spring:message code="foodDishes.cancel" />"
			onclick="javascript: relativeRedir('foodDishes/restaurant/list.do');" />
</form:form>

</security:authorize>
