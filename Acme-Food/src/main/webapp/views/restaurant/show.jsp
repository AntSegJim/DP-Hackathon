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

<security:authorize access="hasRole('ADMIN')">
<b><spring:message code="profile.action.2.name" /> </b> <jstl:out value="${restaurant.name }"/> <br/>
<b><spring:message code="profile.action.2.surname" /></b> <jstl:out value="${restaurant.surnames}"/> <br/>
<b><spring:message code="profile.action.2.vatNumber" /></b> <jstl:out value="${restaurant.vatNumber }"/> <br/>
<b><spring:message code="profile.action.2.email" /></b> <jstl:out value="${restaurant.email }"/> <br/>
<b><spring:message code="profile.action.2.phone" /></b> <jstl:out value="${restaurant.phone }"/> <br/>
<b><spring:message code="profile.action.2.address" /></b> <jstl:out value="${restaurant.address }"/> <br/>
<b><spring:message code="profile.restaurant.comercialName" /></b> <jstl:out value="${restaurant.comercialName}"/> <br/>
<b><spring:message code="profile.restaurant.speciality" /></b> <jstl:out value="${restaurant.speciality}"/> <br/>
<b><spring:message code="profile.restaurant.mediumScore" /></b> <jstl:out value="${restaurant.mediumScore}"/> <br/>
<b><spring:message code="profile.restaurant.orderTime" /></b> <jstl:out value="${restaurant.orderTime}"/> <br/>

<form:form action="restaurant/administrator/show.do" modelAttribute="restaurant">
	<form:hidden path="id"/>
	<form:hidden path="version" />
<form:label path="isBanned"><spring:message code="restaurant.isBanned" />:</form:label>
	<form:select path="isBanned">
		<form:option value="0" label=" ----"/>
	
		<form:option value="1" label="yes"/>
	
			
	</form:select>
	<form:errors path="isBanned"/>
	
	<input type="submit" name="save"  value="<spring:message code="restaurant.save" />" />
	
<input type="button" name="cancel" value="<spring:message code="restaurant.cancel" />"
			onclick="javascript: relativeRedir('restaurant/administrator/list.do');" />
	
	
	</form:form>



</security:authorize>
