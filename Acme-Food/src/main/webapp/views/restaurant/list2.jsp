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

<security:authorize access="hasRole('ADMIN')">

<display:table pagesize="5" name="restaurants" id="row"
requestURI="restaurant/administrator/list.do" >

<display:column property="comercialName" titleKey="restaurant.comercialName"/>
<display:column property="speciality" titleKey="restaurant.speciality"/>
<display:column property="mediumScore" titleKey="restaurant.mediumScore"/>

<display:column>
	<a href="restaurant/administrator/show.do?restaurantId=${row.id}"><spring:message code="restaurant.show"/></a>
</display:column>
</display:table>
<a href="restaurant/administrator/list.do"><spring:message code="restaurant.back"/></a>
</security:authorize>


