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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('RESTAURANT')">
<b><spring:message code="offer.title" />:</b><jstl:out value="${offer.title}"></jstl:out><br/>
<b><spring:message code="offer.totalPrice" />:</b><jstl:out value="${offer.totalPrice}"></jstl:out><br/>

<display:table pagesize="5" name="foodDisheses" id="row"
requestURI="offer/restaurant/show.do?offerId=${offer.id}" >

<display:column property="name" titleKey="offer.foodDishes.name" />
<display:column property="description" titleKey="offer.foodDishes.description" />
<display:column property="price" titleKey="offer.foodDishes.price" />
<display:column titleKey="offer.foodDishes.picture"> <img src="${row.pictures}" width="130px" height="80px"></display:column>
</display:table>

<acme:cancel url="offer/restaurant/list.do" code="cancel"/>
</security:authorize>



<security:authorize access="isAnonymous()">
<b><spring:message code="offer.title" />:</b><jstl:out value="${offer.title}"></jstl:out><br/>
<b><spring:message code="offer.totalPrice" />:</b><jstl:out value="${offer.totalPrice}"></jstl:out><br/>

<display:table pagesize="5" name="foodDisheses" id="row"
requestURI="offer/show.do?offerId=${offer.id }" >

<display:column property="name" titleKey="offer.foodDishes.name" />
<display:column property="description" titleKey="offer.foodDishes.description" />
<display:column property="price" titleKey="offer.foodDishes.price" />
<display:column titleKey="offer.foodDishes.picture"> <img src="${row.pictures}" width="130px" height="80px"></display:column>
</display:table>

<acme:cancel url="offer/list.do?restaurantId=${restaurant.id}" code="cancel"/>



</security:authorize>
