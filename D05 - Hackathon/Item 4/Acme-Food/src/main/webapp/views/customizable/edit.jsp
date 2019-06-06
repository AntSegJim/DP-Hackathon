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
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<security:authorize access="hasRole('ADMIN')">

<form:form action="customizableSystem/administrator/edit.do" modelAttribute="customizable">

<form:hidden path="id"/>
<form:hidden path="version"/>

<acme:textbox code="customizable.nameApp" path="nameSystem"/>
<acme:textarea code="customizable.banner" path="banner"/>
<acme:textarea code="customizable.messageEn" path="messageWelcomePage"/>
<acme:textarea code="customizable.messageEs" path="spanishMessageWelcomePage"/>
<acme:textbox code="customizable.code" path="telephoneCode"/>
<acme:textbox code="customizable.timeCache" path="timeCache"/>
<acme:textbox code="customizable.maxResults" path="maxResults"/>


<br/>
<acme:submit name="save" code="customizable.save"/>

</form:form>
</security:authorize>

