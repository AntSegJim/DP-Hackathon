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
.POCO_TIEMPO{
  background-color: red;
}
.RECIEN_HECHO{
  background-color: SpringGreen ;
}
.AUN_QUEDA{
  background-color: orange;
}
</style>


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
		<jstl:when test="${row.choice eq 0}">
			<spring:message code="cashOrder.takeAway" /> 
		</jstl:when>
		
		<jstl:otherwise>
			<spring:message code="cashOrder.send" />
		</jstl:otherwise>
	</jstl:choose>
	
</display:column>
<display:column>
	<jstl:if test="${(row.draftMode eq 1) and (row.status eq 0)}">
		<a href="cashOrder/customer/edit.do?cashOrderId=${row.id}"><spring:message code="cashOrder.edit" /></a>
	</jstl:if>
</display:column>
</display:table>

</security:authorize>



<security:authorize access="hasRole('RESTAURANT')">

<display:table pagesize="5" name="cashOrders" id="row"
requestURI="cashOrder/restaurant/list.do" >

<jstl:choose>
	<jstl:when test="${row.minutes < 20}">
		<jstl:set var="css" value="POCO_TIEMPO"></jstl:set>
	</jstl:when>
	
	<jstl:when test="${row.minutes < 30 and row.minutes >= 20}">
			<jstl:set var="css" value="AUN_QUEDA"></jstl:set>
	</jstl:when>
	
	<jstl:when test="${row.minutes >= 30}">
			<jstl:set var="css" value="RECIEN_HECHO"></jstl:set>
	</jstl:when>
	
</jstl:choose>

<display:column property="moment" titleKey="cashOrder.moment" format="{0,date,dd/MM/yyyy hh:mm}"  class="${css}"/>

<display:column titleKey="cashOrder.status" class="${css}">

	<jstl:choose>
		<jstl:when test="${row.status eq 0}">
			<spring:message code="cashOrder.pending" /> 
		</jstl:when>
		
		<jstl:when test="${row.status eq 1}">
			<spring:message code="cashOrder.rejected" />
		</jstl:when>
		
		<jstl:when test="${row.status eq 2}">
			<spring:message code="cashOrder.delivered" />
		</jstl:when>
		
		<jstl:otherwise>
			<spring:message code="cashOrder.acceptance" />
		</jstl:otherwise>
	</jstl:choose>
	
</display:column>

<display:column property="totalPrice" titleKey="cashOrder.price"  class="${css}"/>

<display:column titleKey="cashOrder.choice" class="${css}">

	<jstl:choose>
		<jstl:when test="${row.choice eq 0}">
			<spring:message code="cashOrder.takeAway" /> 
		</jstl:when>
		
		<jstl:otherwise>
			<spring:message code="cashOrder.send" />
		</jstl:otherwise>
	</jstl:choose>
	
</display:column>

<display:column class="${css}">
	<jstl:if test="${row.status eq 0 }">
	<a href="cashOrder/restaurant/edit.do?cashOrderId=${row.id}"><spring:message code="cashOrder.edit" /></a>
	</jstl:if>
</display:column>

</display:table>
</security:authorize>

<security:authorize access="hasRole('DEALER')">

<display:table pagesize="5" name="cashOrders" id="row"
requestURI="cashOrder/dealer/list.do" >

<jstl:choose>
	<jstl:when test="${row.minutes < 20}">
		<jstl:set var="css" value="POCO_TIEMPO"></jstl:set>
	</jstl:when>
	
	<jstl:when test="${row.minutes < 30 and row.minutes >= 20}">
			<jstl:set var="css" value="AUN_QUEDA"></jstl:set>
	</jstl:when>
	
	<jstl:when test="${row.minutes >= 30}">
			<jstl:set var="css" value="RECIEN_HECHO"></jstl:set>
	</jstl:when>
	
</jstl:choose>

<display:column property="moment" titleKey="cashOrder.moment" format="{0,date,dd/MM/yyyy hh:mm}"  class="${css}"/>

<display:column titleKey="cashOrder.status" class="${css}">

	<jstl:choose>
		<jstl:when test="${row.status eq 0}">
			<spring:message code="cashOrder.pending" /> 
		</jstl:when>
		
		<jstl:when test="${row.status eq 1}">
			<spring:message code="cashOrder.rejected" />
		</jstl:when>
		
		<jstl:when test="${row.status eq 2}">
			<spring:message code="cashOrder.delivered" />
		</jstl:when>
		
		<jstl:otherwise>
			<spring:message code="cashOrder.acceptance" />
		</jstl:otherwise>
	</jstl:choose>
	
</display:column>

<display:column property="totalPrice" titleKey="cashOrder.price"  class="${css}"/>

<display:column titleKey="cashOrder.choice" class="${css}">

	<jstl:choose>
		<jstl:when test="${row.choice eq 0}">
			<spring:message code="cashOrder.takeAway" /> 
		</jstl:when>
		
		<jstl:otherwise>
			<spring:message code="cashOrder.send" />
		</jstl:otherwise>
	</jstl:choose>
	
</display:column>

<display:column class="${css}">	
	<a href="cashOrder/dealer/edit.do?cashOrderId=${row.id}"><spring:message code="cashOrder.edit" /></a>
</display:column>

</display:table>


</security:authorize>
