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

<display:table pagesize="5" name="cashOrders" id="row"
requestURI="cashOrder/customer/list.do" >
<display:column>
	<a href="cashOrder/customer/show.do?idCashOrder=${row.id}"><spring:message code="cashOrder.moreDetails" /></a>
</display:column>

<display:column property="moment" titleKey="cashOrder.moment" format="{0,date,dd/MM/yyyy hh:mm}"  />

<display:column titleKey="cashOrder.status" >

	<jstl:choose>
		<jstl:when test="${row.status eq 0}">
			<spring:message code="cashOrder.pending" /> 
		</jstl:when>
		
		<jstl:when test="${row.status eq 1}">
			<spring:message code="cashOrder.rejected" />
		</jstl:when>
		
		<jstl:when test="${row.status eq 2}">
			<spring:message code="cashOrder.inProcess" />
		</jstl:when>
		
		<jstl:when test="${row.status eq 3}">
			<spring:message code="cashOrder.delivered" />
		</jstl:when>
		
		<jstl:otherwise>
			<spring:message code="cashOrder.acceptance" />
		</jstl:otherwise>
	</jstl:choose>
	
</display:column>
<display:column titleKey="cashOrder.restaurant" >
${row.restaurant.comercialName}, ${row.restaurant.speciality}
</display:column>
<display:column property="totalPrice" titleKey="cashOrder.price"  />

<display:column titleKey="cashOrder.choice" >

	<jstl:choose>
		<jstl:when test="${row.status eq 0}">
			<spring:message code="cashOrder.takeAway" /> 
		</jstl:when>
		
		<jstl:otherwise>
			<spring:message code="cashOrder.send" />
		</jstl:otherwise>
	</jstl:choose>
	
</display:column>
<display:column>
	<a href="cashOrder/customer/edit.do?cashOrderId=${row.id}"><spring:message code="cashOrder.edit" /></a>
</display:column>
</display:table>

</security:authorize>
