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


<display:column property="name" titleKey="foodDishes.name" class="${css}"/>/>
<display:column property="description" titleKey="foodDishes.description" class="${css}"/>/>
<display:column property="price" titleKey="foodDishes.price" class="${css}"/>/>

<display:column>
	<a href="rating/customer/edit.do?ratingId=${row.id}"><spring:message code="rating.edit" /></a>
</display:column>		
</display:table>

<!-- PENSAR EN COMO PONER ESTA PARTE AQUI CON UN SELECT O EN PEDIDO O EN RESTAURANTES O EN EL FINDER -->
<input type="button" name="create" value="<spring:message code="rating.create" />"
			onclick="javascript: relativeRedir('foodDishes/restaurant/create.do');" />

</security:authorize>



