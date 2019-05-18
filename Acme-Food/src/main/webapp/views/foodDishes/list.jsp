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


<style type="text/css">
.BREAKFAST{
  background-color: red;
}
.LUNCH{
  background-color: green;
}
.DINNER{
  background-color: aqua;
}
.DESSERT{
  background-color: orange;
}
</style>


<security:authorize access="hasRole('RESTAURANT')">

<display:table pagesize="5" name="foodDishes" id="row"
requestURI="foodDishes/restaurant/list.do" >

<jstl:choose>
	<jstl:when test="${row.type eq 0}">
		<jstl:set var="css" value="BREAKFAST"></jstl:set>
	</jstl:when>
	
	<jstl:when test="${row.type eq 1}">
			<jstl:set var="css" value="LUNCH"></jstl:set>
	</jstl:when>
	
	<jstl:when test="${row.type eq 2}">
			<jstl:set var="css" value="DINNER"></jstl:set>
	</jstl:when>
	
	<jstl:when test="${row.type eq 3}">
			<jstl:set var="css" value="DESSERT"></jstl:set>
	</jstl:when>
</jstl:choose>


<display:column class="${css}">
	<a href="foodDishes/restaurant/show.do?foodDishesId=${row.id}"><spring:message code="foodDishes.moreDetails" /></a>
</display:column>
<display:column property="name" titleKey="foodDishes.name" class="${css}"/>/>
<display:column property="description" titleKey="foodDishes.description" class="${css}"/>/>
<display:column property="price" titleKey="foodDishes.price" class="${css}"/>/>
<display:column titleKey="foodDishes.type" class="${css}" >

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
	
</display:column>
<display:column class="${css}">
	<a href="foodDishes/restaurant/edit.do?foodDishesId=${row.id}"><spring:message code="foodDishes.edit" /></a>
</display:column>		
</display:table>

<input type="button" name="create" value="<spring:message code="foodDishes.create" />"
			onclick="javascript: relativeRedir('foodDishes/restaurant/create.do');" />

</security:authorize>



