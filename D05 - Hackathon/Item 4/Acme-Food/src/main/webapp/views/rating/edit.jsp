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
<form:form action="rating/customer/edit.do" modelAttribute="rating">

<form:hidden path="id"/>
<form:hidden path="version"/>
<acme:textbox code="rating.valoration" path="valoration"/>
<acme:textarea code="rating.comment" path="comment"/>
<jstl:if test="${rating.id eq 0}">
	<acme:select items="${restaurants}" itemLabel="comercialName" code="finder.nameRestaurante" path="restaurant" />
</jstl:if>

		<input type="submit" name="save" value="<spring:message code="foodDishes.save" />" />

<input type="button" name="cancel" value="<spring:message code="foodDishes.cancel" />"
			onclick="javascript: relativeRedir('rating/customer/list.do');" />
</form:form>

</security:authorize>