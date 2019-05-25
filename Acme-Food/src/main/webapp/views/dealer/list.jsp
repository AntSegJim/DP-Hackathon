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


<security:authorize access="hasRole('RESTAURANT')">

<display:table pagesize="5" name="dealers" id="row" requestURI="dealer/restaurant/list.do" >


<display:column property="name" titleKey="dealer.name"/>
<display:column property="surnames" titleKey="dealer.apellidos"/>
<display:column property="phone" titleKey="dealer.telefono" />
<display:column titleKey="dealer.imagen" >
	<img width="100" height="100" src="${row.photo}">
</display:column> 

<display:column>
	<a href="dealer/restaurant/edit.do?dealerId=${row.id}"><spring:message code="rating.edit" /></a>
</display:column>		
</display:table>

<input type="button" name="create" value="<spring:message code="dealer.create" />"
			onclick="javascript: relativeRedir('dealer/restaurant/create.do');" />

</security:authorize>
