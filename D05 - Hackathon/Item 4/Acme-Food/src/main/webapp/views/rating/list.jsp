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

<display:table pagesize="5" name="ratings" id="row" requestURI="rating/customer/list.do" >


<display:column property="restaurant.comercialName" titleKey="finder.nameRestaurante"/>
<display:column property="valoration" titleKey="rating.valoration"/>
<display:column property="comment" titleKey="rating.comment" />

<display:column>
	<a href="rating/customer/edit.do?ratingId=${row.id}"><spring:message code="rating.edit" /></a>
</display:column>		
</display:table>

<input type="button" name="create" value="<spring:message code="rating.create" />"
			onclick="javascript: relativeRedir('rating/customer/create.do');" />

</security:authorize>


<security:authorize access="hasRole('RESTAURANT')">

<display:table pagesize="5" name="ratings" id="row" requestURI="rating/restaurant/list.do" >

<display:column property="valoration" titleKey="rating.valoration"/>
<display:column property="comment" titleKey="rating.comment" />
	
</display:table>

</security:authorize>


