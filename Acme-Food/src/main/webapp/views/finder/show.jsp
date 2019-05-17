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

<security:authorize access="hasRole('ROOKIE')">

<!--<display:table pagesize="5" name="positions" id="row"
requestURI="finder/rookie/show.do" >

<display:column property="ticker" titleKey="position.ticker" />
<display:column property="title" titleKey="position.title" />
<display:column property="deadLine" titleKey="position.deadline" />
<display:column property="salary" titleKey="position.salary" />

</display:table>-->
<table >
    <thead>
      <tr>
        <th><spring:message code="position.ticker" /></th>
        <th><spring:message code="position.title" /></th>
        <th><spring:message code="position.deadline" /></th>
        <th><spring:message code="position.salary" /></th>
        <th><spring:message code="position.skillsRequired"/></th>
        <th><spring:message code="position.description"/></th>
      </tr>
    </thead>
    <tbody>
      <jstl:forEach begin="0" end="${ ñapa }" step="1" varStatus="loopCounter" items="${positions}" var="p">
	      <jstl:if test="${loopCounter.count%2 eq 0 }">
		   <tr style=" background-color: #f2f2f2">
		        <td>
		            <jstl:out value="${p.ticker}" />
		        </td>
		        <td>
		            <jstl:out value="${p.title}" />
		        </td>
		        <td>
		            <jstl:out value="${p.description}" />
		        </td>
		        <td>
		            <jstl:out value="${p.deadLine}" />
		        </td>
		        <td>
		            <jstl:out value="${p.salary}" />
		        </td>
		        <td>
		            <jstl:out value="${p.skillsRequired}" />
		        </td>
		    </tr>
		  </jstl:if>
		  <jstl:if test="${loopCounter.count%2 != 0 }">
		   <tr style=" background-color: skyblue">
		        <td>
		            <jstl:out value="${p.ticker}" />
		        </td>
		        <td>
		            <jstl:out value="${p.title}" />
		        </td>
		        <td>
		            <jstl:out value="${p.description}" />
		        </td>
		        <td>
		            <jstl:out value="${p.deadLine}" />
		        </td>
		        <td>
		            <jstl:out value="${p.salary}" />
		        </td>
		        <td>
		            <jstl:out value="${p.skillsRequired}" />
		        </td>
		    </tr>
		  </jstl:if>
      </jstl:forEach>
    </tbody>
  </table>
	<input type="button" name="create" value="<spring:message code="finder.back" />"
			onclick="javascript: relativeRedir('finder/rookie/edit.do');" />

</security:authorize>
