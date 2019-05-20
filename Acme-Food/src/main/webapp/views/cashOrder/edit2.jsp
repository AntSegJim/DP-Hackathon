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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>


<security:authorize access="hasRole('CUSTOMER')">
<form:form action="cashOrder/customer/edit2.do" modelAttribute="cashOrder">

<jstl:if test="${not empty exception}">
		<p style="color:red"> <spring:message code="cashOrder.error" /> </p>
</jstl:if>

<form:hidden path="id"/>
<form:hidden path="version"/>

<acme:textbox code="cashOrder.SenderMoment" path="senderMoment"/>
<acme:selectWithoutNullOption items="${foodDishes}" itemLabel="name" code="cashOrder.foodDishes" path="foodDisheses"/>
<form:label path="choice"><spring:message code="cashOrder.choice" />:</form:label>
<form:select path="choice">
		<form:option value="0" label="Take away" />	
		<form:option value="1" label="For send" />	
	</form:select>
	<form:errors path="choice"/>
<br/>
<form:label path="draftMode"><spring:message code="cashOrder.draftMode" />:</form:label>
<form:select path="draftMode">
		<form:option value="1" label="Yes" />	
		<form:option value="0" label="No" />	
	</form:select>
	<form:errors path="draftMode"/>
<br/>
<br/>
<input type="submit" name="save" 
	value="<spring:message code="cashOrder.save" />" />
	
<input type="button" name="cancel" value="<spring:message code="cashOrder.cancel" />"
			onclick="javascript: relativeRedir('cashOrder/customer/list.do');" />


</form:form>


</security:authorize>

<security:authorize access="hasRole('RESTAURANT')">

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
<b><spring:message code="cashOrder.status" /> : </b> <jstl:out value="${cashOrder.status}"></jstl:out> <br/>
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
<br/>
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
<b><spring:message code="cashOrder.price" /> : </b> <jstl:out value="${cashOrder.totalPrice}"></jstl:out> <br/>
<br/>

<form:form action="cashOrder/restaurant/edit2.do" modelAttribute="cashOrder">

<jstl:if test="${not empty exception}">
		<p style="color:red"> <spring:message code="cashOrder.error" /> </p>
</jstl:if>

<form:hidden path="id"/>
<form:hidden path="version"/>


<form:label path="status"><spring:message code="cashOrder.status" />:</form:label>
<form:select path="status">
		<form:option value="1" label="Rejected" />	
		<form:option value="3" label="Acceptance" />	
	</form:select>
	<form:errors path="status"/>
<br/>

<br/>
<input type="submit" name="save" 
	value="<spring:message code="cashOrder.save" />" />
	
<input type="button" name="cancel" value="<spring:message code="cashOrder.cancel" />"
			onclick="javascript: relativeRedir('cashOrder/restaurant/list.do');" />


</form:form>


</security:authorize>

