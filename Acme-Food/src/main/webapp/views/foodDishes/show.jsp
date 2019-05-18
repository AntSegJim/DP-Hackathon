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
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<security:authorize access="hasRole('RESTAURANT')">

<img width="400px" height="200px" src="<jstl:out value='${foodDish.pictures }'/> "><br/>
<br/>
<b><spring:message code="foodDishes.name" /> : </b> <jstl:out value="${foodDish.name}"></jstl:out> <br/>
<b><spring:message code="foodDishes.description" /> : </b> <jstl:out value="${foodDish.description}"></jstl:out><br/>
<b><spring:message code="foodDishes.price" /> : </b> <jstl:out value="${foodDish.price}"></jstl:out><br/>
<b><spring:message code="foodDishes.type" /> : </b> 

<jstl:choose>
		<jstl:when test="${row.type eq 0}">
			<spring:message code="foodDishes.breakfast" />
		</jstl:when>
		
		<jstl:when test="${row.type eq 1}">
			<spring:message code="foodDishes.lunch" />
		</jstl:when>
		
		<jstl:when test="${row.type eq 2}">
			<spring:message code="foodDishes.dinner" />
		</jstl:when>
		
		<jstl:otherwise>
			<spring:message code="foodDishes.dessert" />
		</jstl:otherwise>
	</jstl:choose>
<br/>
<b><spring:message code="foodDishes.ingredients" /> : </b> 
<jstl:if test="${fn:length(foodDish.ingredients) ne 0}">
<jstl:forEach var="item" items="${foodDish.ingredients}">
-<jstl:out value="${item}"></jstl:out>
<br/>
</jstl:forEach>
</jstl:if>

<br/>
<input type="button" name="cancel" value="<spring:message code="foodDishes.cancel" />"
			onclick="javascript: relativeRedir('foodDishes/restaurant/list.do');" />

</security:authorize>