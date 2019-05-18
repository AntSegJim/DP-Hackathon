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

<security:authorize access="hasRole('RESTAURANT')">
<display:table pagesize="5" name="socialProfiles" id="row"
requestURI="socialProfile/restaurant/list.do" >

<display:column>
	<a href="socialProfile/restaurant/edit.do?socialProfileId=${row.id}"><spring:message code="edit" /></a>
</display:column>
<display:column titleKey="socialProfile.nickName">
	<jstl:out value="${row.nickName}"></jstl:out>
</display:column>
<display:column titleKey="socialProfile.nameSocialNetwork">
	<jstl:out value="${row.nameSocialNetwork}"></jstl:out>
</display:column>
<display:column titleKey="socialProfile.url">
	<jstl:out value="${row.url}"></jstl:out>
</display:column>
</display:table>
<input type="button" name="create" value="<spring:message code="create" />"
			onclick="javascript: relativeRedir('socialProfile/restaurant/create.do');" /><br>

</security:authorize>



