<%--
 * action-2.jsp
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>



<security:authorize access="isAuthenticated()">
<form:form  modelAttribute="actor" action="${action}">

<jstl:if test="${not empty exception}">
		<p style="color:red"> <spring:message code="profile.member.error" /> </p>
</jstl:if>

<form:label path="name"><spring:message code="profile.action.3.changePersonalData" />:</form:label><br /><br />
	<form:hidden path="id"/>
	<form:hidden path="version" />
	<form:hidden path = "patternPhone" />
	
	<fieldset>
	<legend><spring:message code="edit.personalDatas" /></legend>
	<acme:textbox code="profile.action.3.name" path="name"/>
	

	<acme:textbox code="profile.action.3.surname" path="surnames"/>
	
	<acme:textbox code="profile.action.3.vatNumber" path="vatNumber"/>	
	
	<acme:textbox code="profile.action.3.email" path="email"/>	

	<acme:textbox code="profile.action.3.photo" path="photo"/>

	<acme:textbox code="profile.action.3.phone" path="phone"/>
	
	<acme:textbox code="profile.action.3.address" path="address"/>
	
	
	<security:authorize access="hasRole('COMPANY')">
	

	<acme:textbox code="company.nameCompany" path="nameCompany"/>
	
	</security:authorize>
	
	<security:authorize access="hasRole('PROVIDER')">
	

	<acme:textbox code="provider.make" path="make"/>
	
	</security:authorize>
	
		<security:authorize access="hasRole('AUDITOR')">
	
	<acme:multipleSelect items="${positions}" itemLabel="title" code="auditor.positions" path="positions"/>
	</security:authorize>
	</fieldset>
	<br />
	
	<fieldset>
	 <legend><spring:message code="profile.creditCard.Data" /></legend>
	<acme:textbox code="creditCard.brandName" path="brandName"/>
	<acme:textbox code="creditCard.holderName" path="holderName"/>		
	<acme:textbox code="creditCard.number" path="number"/>
	<acme:textbox code="creditCard.expirationMonth" path="expirationMonth"/>
	<acme:textbox code="creditCard.expirationYear" path="ExpirationYear"/>
	<acme:textbox code="creditCard.CW" path="CW"/>
	<br />
	</fieldset>
	
	<fieldset>
	 <legend><spring:message code="actor.userAccount" /></legend>
	<acme:textbox code="actor.username" path="userAccount.username"/>	
	<acme:password code="profile.pass1" path="userAccount.password"/>
	<acme:password code="profile.pass2" path="password"/>
	</fieldset>
	<br />
	
	<input type="submit" name="save" onclick=" return validar(); "
	value="<spring:message code="profile.action.3.save" />" />
	<acme:cancel url="profile/personal-datas.do" code="administrator.cancel"/>
</form:form>

</security:authorize>

<script>
function validar(){
	return validar_phone();
}

 function validar_phone(){
  var numeroTelefono=document.getElementById('phone');
   var expresionRegular1=/^\+[0-9]{0,3}\ \([0-9]{0,3}\)\ [0-9]{4,}$|^\+[1-9][0-9]{0,2}\ [0-9]{4,}$|^[0-9]{4,}|^\+[0-9]\ $|^$|^\+$/gm;//<-- hay que cambiar el pattern
 

	 if(!expresionRegular1.test(numeroTelefono.value)){
	 var confirmarTelefono= confirm('Are you sure you want to register that phone number?');
	 
	 
	 if(confirmarTelefono==true){
	 
		 document.getElementById('patternPhone').value=true;
		 
	 }
 }
	 return confirmarTelefono ;

	}
</script>

