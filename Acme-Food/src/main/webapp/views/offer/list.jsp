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

<security:authorize access="hasRole('RESTAURANT')">
<display:table pagesize="5" name="offers" id="row"
requestURI="offer/restaurant/list.do" >

<display:column>
	<a href="offer/restaurant/edit.do?offerId=${row.id}"><spring:message code="edit" /></a>
</display:column>
<display:column>
	<a href="offer/restaurant/show.do?offerId=${row.id}"><spring:message code="moreDetails" /></a>
</display:column>
<display:column property="title" titleKey="offer.title"/>
<display:column property="totalPrice" titleKey="offer.totalPrice"/>
</display:table>
<input type="button" name="create" value="<spring:message code="create" />"
			onclick="javascript: relativeRedir('offer/restaurant/create.do');" /><br>
</security:authorize>




<security:authorize access="isAnonymous()">
<display:table pagesize="5" name="offers" id="row"
requestURI="offer/list.do" >
<display:column>
	<a href="offer/show.do?offerId=${row.id}&restaurantId=${restaurant.id}"><spring:message code="moreDetails" /></a>
</display:column>
<display:column property="title" titleKey="offer.title"/>
<display:column property="totalPrice" titleKey="offer.totalPrice"/>
</display:table>
<acme:cancel url="restaurant/list.do" code="cancel"/>
</security:authorize>




<security:authorize access="hasRole('CUSTOMER')">
<display:table pagesize="5" name="offers" id="row"
requestURI="offer/customer/list.do" >
<display:column>
	<a href="offer/customer/show.do?offerId=${row.id}&restaurantId=${restaurant.id}"><spring:message code="moreDetails" /></a>
</display:column>
<display:column property="title" titleKey="offer.title"/>
<display:column property="totalPrice" titleKey="offer.totalPrice"/>
</display:table>
<acme:cancel url="restaurant/customer/list.do" code="cancel"/>
<acme:cancel url="finder/customer/show.do" code="finder.back.results"/>
</security:authorize>




