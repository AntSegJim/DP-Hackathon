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

<b><spring:message code="cashOrder.customer" /> : </b><jstl:out value="${cashOrder.customer.name}"></jstl:out> <br/>
<b><spring:message code="cashOrder.ticker" /> : </b> <jstl:out value="${cashOrder.ticker}"></jstl:out> <br/>
<b><spring:message code="cashOrder.moment" /> : </b> <fmt:formatDate value="${cashOrder.moment }" pattern="yyyy-MM-dd HH:mm" /><br/>
<b><spring:message code="cashOrder.SenderMoment" /> : </b> <fmt:formatDate value="${cashOrder.senderMoment }" pattern="yyyy-MM-dd HH:mm" /><br/>

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

<form:form action="cashOrder/restaurant/edit.do" modelAttribute="cashOrder">

<jstl:if test="${not empty exception}">
		<p style="color:red"> <spring:message code="cashOrder.error" /> </p>
</jstl:if>

<form:hidden path="id"/>
<form:hidden path="version"/>


<form:label path="status"><spring:message code="cashOrder.status" />:</form:label>
<form:select path="status" id="status" onchange="javascript: reloadDealers()">
		<form:option value="1" label="Rejected" />	
		<form:option value="3" label="Acceptance" />	
	</form:select>
	<form:errors path="status"/>
<br/>
<jstl:if test="${cashOrder.choice eq 1 }">
<form:label path="dealer">
	<spring:message code="cashOrder.dealer" />
</form:label>
<form:select id="dealers" path="dealer">
		<form:options items="${dealers}" itemValue="id"
			itemLabel="name" />
	</form:select>
<form:errors cssClass="error" path="dealer"/>
</jstl:if>

<br/>
<input type="submit" name="save" 
	value="<spring:message code="cashOrder.save" />" />
	
<input type="button" name="cancel" value="<spring:message code="cashOrder.cancel" />"
			onclick="javascript: relativeRedir('cashOrder/restaurant/list.do');" />


</form:form>
</security:authorize>

<security:authorize access="hasRole('DEALER')">

<b><spring:message code="cashOrder.customer" /> : </b><jstl:out value="${cashOrder.customer.name}"></jstl:out> <br/>
<b><spring:message code="cashOrder.ticker" /> : </b> <jstl:out value="${cashOrder.ticker}"></jstl:out> <br/>
<b><spring:message code="cashOrder.moment" /> : </b> <fmt:formatDate value="${cashOrder.moment }" pattern="yyyy-MM-dd HH:mm" /><br/>
<b><spring:message code="cashOrder.SenderMoment" /> : </b> <fmt:formatDate value="${cashOrder.senderMoment }" pattern="yyyy-MM-dd HH:mm" /><br/>

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

<form:form action="cashOrder/dealer/edit.do" modelAttribute="cashOrder">

<jstl:if test="${not empty exception}">
		<p style="color:red"> <spring:message code="cashOrder.error" /> </p>
</jstl:if>

<form:hidden path="id"/>
<form:hidden path="version"/>


<form:label path="status"><spring:message code="cashOrder.status" />:</form:label>
<form:select path="status" id="status" onchange="javascript: reloadDealers()">
		<form:option value="3" label="Acceptance" />
		<form:option value="2" label="Delivered" />		
	</form:select>
	<form:errors path="status"/>

<br/>
<input type="submit" name="save" 
	value="<spring:message code="cashOrder.save" />" />
	
<input type="button" name="cancel" value="<spring:message code="cashOrder.cancel" />"
			onclick="javascript: relativeRedir('cashOrder/dealer/list.do');" />


</form:form>

<script type="text/javascript">
		function reloadDealers() {
			var status = $('select#status').val();
			
			//var placeholder = $('select#dealers');
			//placeholder.load("cashOrder/restaurant/reloadDealers.do?statusValue="+status);	
			
		}
</script>


</security:authorize>

