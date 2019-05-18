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
<form:form action="finder/customer/edit.do" modelAttribute="finder">

<jstl:if test="${not empty exception}">
		<p style="color:red"> <spring:message code="notification.error" /> </p>
</jstl:if>

<form:hidden path="id"/>
<form:hidden path="version"/>

<acme:textbox code="finder.keyWord" path="keyWord"/>
<acme:textbox code="restaurant.nameRestaurant" path="nameRestaurant"/>
<acme:textbox code="restaurant.speciality" path="speciality"/>

<input type="submit" name="save" 
	value="<spring:message code="notification.save" />" />
	

</form:form>

<input type="button" name="cancel" value="<spring:message code="finder.show" />"
			onclick="javascript: relativeRedir('finder/customer/show.do');" />

</security:authorize>
