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
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<security:authorize access="hasRole('CUSTOMER')">
<b><spring:message code="cashOrder.ticker" /> : </b> <jstl:out value="${cashOrder.ticker}"></jstl:out> <br/>
<b><spring:message code="cashOrder.moment" /> : </b> <fmt:formatDate value="${cashOrder.moment }" pattern="yyyy-MM-dd HH:mm" /><br/>
<b><spring:message code="cashOrder.SenderMoment" /> : </b> <fmt:formatDate value="${cashOrder.senderMoment }" pattern="yyyy-MM-dd HH:mm" /><br/>
<b><spring:message code="cashOrder.draftMode" /> : </b> 
<jstl:choose>
		<jstl:when test="${row.draftMode eq 1}">
			<spring:message code="cashOrder.YesDraftMode" /> 
		</jstl:when>
		<jstl:otherwise>
			<spring:message code="cashOrder.NoDraftMode" />
		</jstl:otherwise>
	</jstl:choose>
<br/>
<b><spring:message code="cashOrder.status" /> : </b>
		<jstl:choose>
			<jstl:when test="${cashOrder.status eq 0}">
				<spring:message code="cashOrder.pending" /> 
			</jstl:when>
		
			<jstl:when test="${cashOrder.status eq 1}">
				<spring:message code="cashOrder.rejected" />
			</jstl:when>
		
			<jstl:when test="${cashOrder.status eq 2}">
				<spring:message code="cashOrder.delivered" />
			</jstl:when>
		
			<jstl:otherwise>
				<spring:message code="cashOrder.acceptance" />
			</jstl:otherwise>
		</jstl:choose>
	<br/>
<b><spring:message code="cashOrder.restaurant" /> : </b> <jstl:out value="${cashOrder.restaurant.comercialName}"></jstl:out>, <jstl:out value="${cashOrder.restaurant.speciality}"></jstl:out> <br/>
<b><spring:message code="cashOrder.dealer" /> : </b> 
<jstl:if test="${row.dealer.name eq null }">
-
</jstl:if>
<jstl:if test="${row.dealer.name ne null }">
	<spring:message code="cashOrder.draftMode" /> 
</jstl:if>
 <br/>
<b><spring:message code="cashOrder.foodDishes" /> : </b> 
<jstl:if test="${fn:length(cashOrder.foodDisheses) ne 0}">
<jstl:forEach var="item" items="${cashOrder.foodDisheses}">
-<jstl:out value="${item.name}"></jstl:out>
<br/>
</jstl:forEach>
</jstl:if>
<b><spring:message code="cashOrder.choice" /> : </b> 
<jstl:choose>
		<jstl:when test="${cashOrder.choice eq 0}">
			<spring:message code="cashOrder.takeAway" /> 
		</jstl:when>
		
		<jstl:otherwise>
			<spring:message code="cashOrder.send" />
		</jstl:otherwise>
	</jstl:choose>
<br/>
<b><spring:message code="cashOrder.price" /> : </b> <jstl:out value="${cashOrder.totalPrice}"></jstl:out> <br/>
<br/>
<input type="button" name="cancel" value="<spring:message code="cashOrder.cancel" />"
			onclick="javascript: relativeRedir('cashOrder/customer/list.do');" />

</security:authorize>
