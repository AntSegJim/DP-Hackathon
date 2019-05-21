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

<form:form action="customer/edit.do" modelAttribute="registrationForm">
<jstl:if test="${not empty exception}">
		<p style="color:red"> <spring:message code="customer.error" /> </p>
</jstl:if>
	<form:hidden path="id"/>
	<form:hidden path="version" />
	<form:hidden path="patternPhone"/>
	
	
	
	<fieldset>
	<legend><spring:message code="customer.personalDatas" /></legend>
	
	<acme:textbox code="customer.name" path="name"/>
		
	
	<acme:textbox code="customer.surnames" path="surnames"/>
	
	
	<acme:textbox code="customer.vatNumber" path="vatNumber"/>
	
		
	<acme:textbox code="customer.photo" path="photo"/>	
		
	<acme:textbox code="customer.email" path="email"/>	
	
	
	<acme:textbox code="customer.phone" path="phone"/>	

	
	<acme:textbox code="customer.address" path="address"/>	
	
	
	<br />
	<p><spring:message code="customer.information" /></p>
	</fieldset>
	<br />
	
	
	
	<fieldset>
	 <legend><spring:message code="creditCard.Data" /></legend>
	<acme:textbox code="creditCard.registro.brandName" path="brandName"/>
	<acme:textbox code="creditCard.registro.holderName" path="holderName"/>		
	<acme:textbox code="creditCard.registro.number" path="number"/>
	<acme:textbox code="creditCard.registro.expirationMonth" path="expirationMonth"/>
	<acme:textbox code="creditCard.registro.expirationYear" path="ExpirationYear"/>
	<acme:textbox code="creditCard.registro.CW" path="CW"/>
	<br />
	</fieldset>
	
	<fieldset>
	 <legend><spring:message code="customer.userAccount" /></legend>
	<acme:textbox code="customer.username" path="userAccount.username"/>	
	<acme:password code="customer.password" path="userAccount.password"/>
	<acme:password code="customer.confirmation.password" path="password"/>
	</fieldset>
	<br />
	
	<acme:checkbox code="Terminos.Condiciones" path="check" />
	
	<input type="submit" name="save" onclick=" return validar(); "
	value="<spring:message code="customer.save" />" />
	
	<acme:cancel url="welcome/index.do" code="customer.cancel"/>
	
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