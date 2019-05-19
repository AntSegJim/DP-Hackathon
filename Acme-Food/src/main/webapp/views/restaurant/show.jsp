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

<security:authorize access="isAuthenticated()">

<b><spring:message code="notification.subject" /> : </b> <jstl:out value="${notification.subject}"></jstl:out> <br/>
<b><spring:message code="notification.body" /> : </b> <jstl:out value="${notification.body}"></jstl:out>

<br/>
<br/>
<input type="button" name="cancel" value="<spring:message code="notification.cancel" />"
			onclick="javascript: relativeRedir('notification/actor/list.do');" />

</security:authorize>
