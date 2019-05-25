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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('CUSTOMER')">
<display:table pagesize="5" name="complains" id="row"
requestURI="complain/customer/list.do" >

<display:column property="description" titleKey="complain.description"/>
</display:table>
<input type="button" name="create" value="<spring:message code="create" />"
			onclick="javascript: relativeRedir('complain/customer/create.do?cashOrderId=${cashOrder.id}');" />
<input type="button" name="cancel" value="<spring:message code="cancel" />"
			onclick="javascript: relativeRedir('cashOrder/customer/list.do');" />
</security:authorize>





