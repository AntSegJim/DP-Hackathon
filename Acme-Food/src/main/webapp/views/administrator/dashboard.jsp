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

<security:authorize access="hasRole('ADMIN')">


<fieldset>
<b><spring:message code="administrator.restaurant.more.medium.score" /></b>:
<br/>
<jstl:if test="${fn:length(getRestaurantWithMoreScore) ne 0}">
<jstl:forEach var="item" items="${getRestaurantWithMoreScore}">
<jstl:out value="${item}"></jstl:out>
<br/>
</jstl:forEach>
</jstl:if>
</fieldset>

<fieldset>
<b><spring:message code="administrator.restaurant.less.medium.score" /></b>:
<br/>
<jstl:if test="${fn:length(getRestaurantWithLessScore) ne 0}">
<jstl:forEach var="item" items="${getRestaurantWithLessScore}">
<jstl:out value="${item}"></jstl:out>
<br/>
</jstl:forEach>
</jstl:if>
</fieldset>

<fieldset>
<b><spring:message code="administrator.restaurant.cashOrder" /></b>:
<br/>
<jstl:if test="${fn:length(getTop5RestaurantsWithMoreOrders) ne 0}">
<jstl:forEach var="item" items="${getTop5RestaurantsWithMoreOrders}">
<jstl:out value="${item}"></jstl:out>
<br/>
</jstl:forEach>
</jstl:if>
</fieldset>

</security:authorize>
