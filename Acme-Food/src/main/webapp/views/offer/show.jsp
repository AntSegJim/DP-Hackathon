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
<%@ page import="java.util.ArrayList"%>

<security:authorize access="hasRole('RESTAURANT')">

<display:table pagesize="5" name="foodDishes" id="row"
requestURI="finder/rookie/show.do" >

<display:column property="ticker" titleKey="position.ticker" />
<display:column property="title" titleKey="position.title" />
<display:column property="deadLine" titleKey="position.deadline" />
<display:column property="salary" titleKey="position.salary" />

</display:table>

	<input type="button" name="create" value="<spring:message code="finder.back" />"
			onclick="javascript: relativeRedir('finder/rookie/edit.do');" />

</security:authorize>
