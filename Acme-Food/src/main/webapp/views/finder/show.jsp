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
<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<security:authorize access="hasRole('CUSTOMER')">
	<jstl:if test="${fn:length(restaurants) eq 0 }">
		<table>
			<thead>
				<tr>
					<th><spring:message code="finder.empty.title" /></th>
				</tr>
			</thead>
			<tbody>
				<tr style="background-color: #f2f2f2">
					<td><spring:message code="finder.empty.message" /></td>

				</tr>
			</tbody>
		</table>
	</jstl:if>
	
	<table>
		<thead>
			<tr>
				<th><spring:message code="restaurant.nameRestaurant" /></th>
				<th><spring:message code="restaurant.speciality" /></th>
				<th><spring:message code="restaurant.mediumScore" /></th>
			</tr>
		</thead>
		<tbody>
			<jstl:forEach begin="0" end="${ ñapa }" step="1"
				varStatus="loopCounter" items="${restaurants}" var="r">
				<jstl:if test="${loopCounter.count%2 eq 0 }">
					<tr style="background-color: #f2f2f2">
						<td><jstl:out value="${r.comercialName}" /></td>
						<td><jstl:out value="${r.speciality}" /></td>
						<td><jstl:out value="${r.mediumScore}" /></td>
					</tr>
				</jstl:if>
				<jstl:if test="${loopCounter.count%2 != 0 }">
					<tr style="background-color: skyblue">
						<td><jstl:out value="${r.comercialName}" /></td>
						<td><jstl:out value="${r.speciality}" /></td>
						<td><jstl:out value="${r.mediumScore}" /></td>
					</tr>
				</jstl:if>
			</jstl:forEach>
		</tbody>
	</table>
	
	<input type="button" name="create" value="<spring:message code="finder.back" />"
			onclick="javascript: relativeRedir('finder/customer/edit.do');" />

</security:authorize>
