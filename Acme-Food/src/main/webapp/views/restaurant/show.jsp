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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('ADMINISTRATOR')">
<b><spring:message code="profile.restaurant.comercialName" /></b> <jstl:out value="${actor.comercialName}"/> <br/>
<b><spring:message code="profile.restaurant.speciality" /></b> <jstl:out value="${actor.speciality}"/> <br/>
<b><spring:message code="profile.restaurant.mediumScore" /></b> <jstl:out value="${actor.mediumScore}"/> <br/>
<b><spring:message code="profile.restaurant.orderTime" /></b> <jstl:out value="${actor.orderTime}"/> <br/>
<b><spring:message code="profile.restaurant.orderTime" /></b> <jstl:out value="${actor.orderTime}"/> <br/>

<form:label path="isBanned"><spring:message code="restaurant.isBanned" />:</form:label>
	<form:select path="isBanned">
		<form:option value="0" label=<spring:message code="No.isBanned" /> />	
		<form:option value="1" label=<spring:message code="Yes.isBanned" />/>	
	</form:select>
	<form:errors path="isBanned"/>
	<



</security:authorize>
