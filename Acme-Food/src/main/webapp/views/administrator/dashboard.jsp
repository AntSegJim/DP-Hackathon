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
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<security:authorize access="hasRole('ADMIN')">

<fieldset>
<legend><spring:message code="administrator.position.company" /></legend>
<b><spring:message code="administrator.avg" /></b>: <fmt:formatNumber type="number" maxIntegerDigits = "3" value ="${getAvgPositionByCompany}"></fmt:formatNumber><br/>
<b><spring:message code="administrator.min" /></b>: <jstl:out value="${getMinPositionByCompany}"></jstl:out><br/>
<b><spring:message code="administrator.max" /></b>: <jstl:out value="${getMaxPositionByCompany}"></jstl:out><br/>
<b><spring:message code="administrator.desv" /></b>: <fmt:formatNumber type="number" maxIntegerDigits = "3" value ="${getDesvPositionByCompany}"></fmt:formatNumber>
</fieldset>

<fieldset>
<legend><spring:message code="administrator.application.hacker" /></legend>
<b><spring:message code="administrator.avg" /></b>: <fmt:formatNumber type="number" maxIntegerDigits = "3" value ="${getAvgAppByHackers}"></fmt:formatNumber> <br/>
<b><spring:message code="administrator.min" /></b>: <jstl:out value="${getMinAppByHackers}"></jstl:out><br/>
<b><spring:message code="administrator.max" /></b>: <jstl:out value="${getMaxAppByHackers}"></jstl:out><br/>
<b><spring:message code="administrator.desv" /></b>: <fmt:formatNumber type="number" maxIntegerDigits = "3" value ="${getDesvAppByHackers}"></fmt:formatNumber>
</fieldset>

<fieldset>
<b><spring:message code="administrator.company.positions" /></b>:
<br/>
<jstl:if test="${fn:length(getCompaniesWithMorePositions) ne 0}">
<jstl:forEach var="item" items="${getCompaniesWithMorePositions}">
<jstl:out value="${item}"></jstl:out>
<br/>
</jstl:forEach>
</jstl:if>
</fieldset>

<fieldset>
<b><spring:message code="administrator.hacker.applications" /></b>:
<br/>
<jstl:if test="${fn:length(getHackersWithMoreApplications) ne 0}">
<jstl:forEach var="item" items="${getHackersWithMoreApplications}">
<jstl:out value="${item}"></jstl:out>
<br/>
</jstl:forEach>
</jstl:if>
</fieldset>

<fieldset>
<legend><spring:message code="administrator.position.salary" /></legend>
<b><spring:message code="administrator.avg" /></b>: <fmt:formatNumber type="number" maxIntegerDigits = "3" value ="${getAvgSalaryOfPositions}"></fmt:formatNumber> <br/>
<b><spring:message code="administrator.min" /></b>: <jstl:out value="${getMinSalaryOfPositions}"></jstl:out><br/>
<b><spring:message code="administrator.max" /></b>: <jstl:out value="${getMaxSalaryOfPositions}"></jstl:out><br/>
<b><spring:message code="administrator.desv" /></b>: <fmt:formatNumber type="number" maxIntegerDigits = "3" value ="${getDesvSalaryOfPositions}"></fmt:formatNumber>
</fieldset>

<fieldset>
<legend><spring:message code="administrator.salary" /></legend>
<b><spring:message code="administrator.best" /></b>: 
<br/>
<jstl:forEach var="item" items="${getPositionWithBestSalary}">
<jstl:out value="${item}"></jstl:out>
<br/>
</jstl:forEach>

<b><spring:message code="administrator.worst" /></b>: <br/>
<jstl:forEach var="item" items="${getPositionWithWorstSalary}">
<jstl:out value="${item}"></jstl:out>
<br/>
</jstl:forEach>
</fieldset>

<fieldset>
<legend><spring:message code="administrator.curricula-finder" /></legend>
<b><spring:message code="administrator.curricula-finder.curricula" /></b>: <jstl:out value="Min: ${curricula[0][0]}, Max: ${curricula[0][1]}, Avg: ${curricula[0][2]}, Desv: ${curricula[0][3]}"></jstl:out><br/>
<b><spring:message code="administrator.curricula-finder.finder1" /></b>: <jstl:out value="Min: ${resultsFinder[0][0]}, Max: ${resultsFinder[0][1]}, Avg: ${resultsFinder[0][2]}, Desv: ${resultsFinder[0][3]}"></jstl:out><br/>
<b><spring:message code="administrator.curricula-finder.finder2" /></b>: <jstl:out value="Ratio: ${emptyVSnotEmpty}"></jstl:out><br/>
</fieldset>

<fieldset>
<legend><spring:message code="administrator.audit.score" /></legend>
<b><spring:message code="administrator.avg" /></b>: <fmt:formatNumber type="number" maxIntegerDigits = "3" value ="${getAvgScoreOfAudit}"></fmt:formatNumber> <br/>
<b><spring:message code="administrator.min" /></b>: <jstl:out value="${getMinScoreOfAudit}"></jstl:out><br/>
<b><spring:message code="administrator.max" /></b>: <jstl:out value="${getMaxScoreOfAudit}"></jstl:out><br/>
<b><spring:message code="administrator.desv" /></b>: <fmt:formatNumber type="number" maxIntegerDigits = "3" value ="${getDesvScoreOfAudit}"></fmt:formatNumber>
</fieldset>

<fieldset>
<legend><spring:message code="administrator.audit.score.company" /></legend>
<b><spring:message code="administrator.avg" /></b>: <fmt:formatNumber type="number" maxIntegerDigits = "3" value ="${getAvgScoreOfAuditByCompany}"></fmt:formatNumber> <br/>
<b><spring:message code="administrator.min" /></b>: <jstl:out value="${getMinScoreOfAuditByCompany}"></jstl:out><br/>
<b><spring:message code="administrator.max" /></b>: <jstl:out value="${getMaxScoreOfAuditByCompany}"></jstl:out><br/>
<b><spring:message code="administrator.desv" /></b>: <fmt:formatNumber type="number" maxIntegerDigits = "3" value ="${getDesvScoreOfAuditByCompany}"></fmt:formatNumber>
</fieldset>

<fieldset>
<b><spring:message code="administrator.company.score" /></b>:
<br/>
<jstl:if test="${fn:length(getCompaniesWithHighestScore) ne 0}">
<jstl:forEach var="item" items="${getCompaniesWithHighestScore}">
<jstl:out value="${item}"></jstl:out>
<br/>
</jstl:forEach>
</jstl:if>
</fieldset>

<fieldset>
<b><spring:message code="administrator.company.highScore" /></b>:
<br/>
<jstl:if test="${fn:length(getAvgSalaryOfCompaniesWithHighScore) ne 0}">
<jstl:forEach var="item" items="${getAvgSalaryOfCompaniesWithHighScore}">
<jstl:out value="${item}"></jstl:out>
<br/>
</jstl:forEach>
</jstl:if>
</fieldset>

<fieldset>
<legend><spring:message code="administrator.item.provider" /></legend>
<b><spring:message code="administrator.avg" /></b>: <fmt:formatNumber type="number" maxIntegerDigits = "3" value ="${getAvgItemProvider}"></fmt:formatNumber> <br/>
<b><spring:message code="administrator.min" /></b>: <jstl:out value="${getMinItemProvider}"></jstl:out><br/>
<b><spring:message code="administrator.max" /></b>: <jstl:out value="${getMaxItemProvider}"></jstl:out><br/>
<b><spring:message code="administrator.desv" /></b>: <fmt:formatNumber type="number" maxIntegerDigits = "3" value ="${getDesvItemProvider}"></fmt:formatNumber>
</fieldset>

<fieldset>
<b><spring:message code="administrator.top5.providers" /></b>:
<br/>
<jstl:if test="${fn:length(top5Providers) ne 0}">
<jstl:forEach var="item" items="${top5Providers}">
<jstl:out value="${item}"></jstl:out>
<br/>
</jstl:forEach>
</jstl:if>
</fieldset>

</security:authorize>
