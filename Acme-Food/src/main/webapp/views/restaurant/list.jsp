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

<security:authorize access="hasRole('CUSTOMER')">

<display:table pagesize="5" name="restaurants" id="row"
requestURI="restaurant/customer/list.do" >
<h1><spring:message code="restaurant.title" /></h1>


<display:column class="${css}">
	<a href="offer/customer/list.do?restaurantId=${row.id}"><spring:message code="restaurant.offer" /></a>
</display:column>
<display:column class="${css}">
	<a href="foodDishes/customer/list.do?idRestaurant=${row.id}"><spring:message code="restaurant.foodDishes" /></a>
</display:column>
<display:column property="comercialName" titleKey="restaurant.comercialName"/>
<display:column property="speciality" titleKey="restaurant.speciality"/>
<display:column property="mediumScore" titleKey="restaurant.mediumScore"/>
<display:column property="phone" titleKey="restaurant.phone.list"/>

<display:column>
	<a href="cashOrder/customer/create.do?restaurantId=${row.id}"><spring:message code="cashOrder.do" /></a>
</display:column>
</display:table>
</security:authorize>


<security:authorize access="isAnonymous()">

<display:table pagesize="5" name="restaurants" id="row"
requestURI="restaurant/list.do" >
<display:column>
	<a href="offer/list.do?restaurantId=${row.id}"><spring:message code="restaurant.offer" /></a>
</display:column>

<display:column>
	<a href="foodDishes/list.do?idRestaurant=${row.id}"><spring:message code="restaurant.foodDishes" /></a>
</display:column>
<display:column property="comercialName" titleKey="restaurant.comercialName"/>
<display:column property="speciality" titleKey="restaurant.speciality"/>
<display:column property="mediumScore" titleKey="restaurant.mediumScore"/>
</display:table>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">

<h1><spring:message code="restaurant.title.baneo" /></h1>

<display:table pagesize="5" name="restaurants" id="row"
requestURI="restaurant/administrator/list.do" >

<display:column property="comercialName" titleKey="restaurant.comercialName"/>
<display:column property="speciality" titleKey="restaurant.speciality"/>
<display:column property="mediumScore" titleKey="restaurant.mediumScore"/>

<display:column>
<jstl:if test="${row.mediumScore<=3 }">
	<a href="restaurant/administrator/show.do?restaurantId=${row.id}"><spring:message code="restaurant.show"/></a>
	</jstl:if>
</display:column>

</display:table>
<a href="restaurant/administrator/list2.do"><spring:message code="restaurant.banned"/></a>
</security:authorize>


