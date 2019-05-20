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
<form:form action="offer/restaurant/edit.do" modelAttribute="offer">
 
<jstl:if test="${not empty exception}">
		<p style="color:red"> <spring:message code="offer.error" /> </p>
</jstl:if>


<form:hidden path="id"/>
<form:hidden path="version"/>

<acme:textbox code="offer.title" path="title"/>
<acme:textbox code="offer.totalPrice" path="totalPrice"/>
<acme:selectWithoutNullOption items="${foodDisheses}" itemLabel="description" code="offer.foodDishes.name" path="foodDisheses"/>

<br/>
<input type="submit" name="save" value="<spring:message code="save" />" />
<jstl:if test="${offer.id ne 0 }">	
	<input type="submit" name="delete" value="<spring:message code="delete" />" />
</jstl:if>
<input type="button" name="cancel" value="<spring:message code="cancel" />"
			onclick="javascript: relativeRedir('offer/restaurant/list.do');" />
</form:form>

</security:authorize>
