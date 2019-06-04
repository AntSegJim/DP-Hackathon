<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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

<form:form action="restaurant/edit.do" modelAttribute="registrationForm">
<jstl:if test="${not empty exception}">
		<p style="color:red"> <spring:message code="restaurant.error" /> </p>
</jstl:if>
	<form:hidden path="id"/>
	<form:hidden path="version" />
	<form:hidden path="patternPhone"/>
	
	
	
	<fieldset>
	<legend><spring:message code="restaurant.personalDatas" /></legend>
	
	<acme:textbox code="restaurant.name" path="name"/>
		
	
	<acme:textbox code="restaurant.surnames" path="surnames"/>
	
	
	<acme:textbox code="restaurant.vatNumber" path="vatNumber"/>
	
		
	<acme:textbox code="restaurant.photo" path="photo"/>	
		
	<acme:textbox code="restaurant.email" path="email"/>	
	
	
	<acme:textbox code="restaurant.phone" path="phone"/>	

	
	<acme:textbox code="restaurant.address" path="address"/>	
	
	
	<br />
	<p><spring:message code="restaurant.information" /></p>
	</fieldset>
	<br />
	
	<fieldset>
	 <legend><spring:message code="restaurant.Data" /></legend>
	<acme:textbox code="restaurant.registro.comercialName" path="comercialName"/>	
	<acme:textbox code="restaurant.registro.speciality" path="speciality"/>
	<acme:textbox code="restaurant.orderTime" path="orderTime"/>
	<p><spring:message code="restaurant.explicacion" /></p>
	</fieldset>


	<fieldset>
	 <legend><spring:message code="restaurant.userAccount" /></legend>
	<acme:textbox code="restaurant.username" path="userAccount.username"/>	
	<acme:password code="restaurant.password" path="userAccount.password"/>
	<acme:password code="restaurant.confirmation.password" path="password"/>
	</fieldset>
	<br />
	
	<acme:checkbox code="Terminos.Condiciones" path="check" />
	
	<input type="submit" name="save" onclick=" return validar(); "
	value="<spring:message code="restaurant.save" />" />
	
	<acme:cancel url="welcome/index.do" code="restaurant.cancel"/>
	
</form:form>

<script>
$( document ).ready(function() {
	document.getElementById("checkbox").value='false';
	document.getElementById("checkbox").checked=false;
});
$( '#checkbox' ).on( 'click', function() {
    if( $(this).is(':checked') ){
        document.getElementById("checkbox").value='true';
    } else {
    	document.getElementById("checkbox").value='false';
    }
});

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