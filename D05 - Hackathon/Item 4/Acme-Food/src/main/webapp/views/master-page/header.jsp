<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="${urlBanner }"  width="400px" height="200px" alt="Acme Food Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a href="customizableSystem/administrator/edit.do"><spring:message code="master.page.customizable" /></a></li>		
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/dashboard.do"><spring:message code="master.page.administrator.action.1" /></a></li>
					<li><a href="administrator/create.do"><spring:message code="master.page.administrator.Create" /></a></li>
					<li><a href="restaurant/administrator/list.do"><spring:message code="master.page.administrator.ban" /></a></li>				
				</ul>
			</li>						
		</security:authorize>
		
		<security:authorize access="hasRole('RESTAURANT')">
			<li><a href="foodDishes/restaurant/list.do"><spring:message code="master.page.restaurant.foodDishes" /></a></li>
			<li><a href="cashOrder/restaurant/list.do"><spring:message code="master.page.cashOrder" /></a></li>
			<li><a href="offer/restaurant/list.do"><spring:message code="master.page.restaurant.offer" /></a></li>
			<li><a href="rating/restaurant/list.do"><spring:message code="master.page.rating" /></a></li>
			<li><a href="dealer/restaurant/list.do"><spring:message code="master.page.restaurant.dealer" /></a></li>
			<li><a href="socialProfile/restaurant/list.do"><spring:message code="master.page.restaurant.socialProfile" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a href="restaurant/customer/list.do"><spring:message code="master.page.restaurant" /></a></li>
			
			<li><a href="cashOrder/customer/list.do"><spring:message code="master.page.cashOrder" /></a></li>
			<li><a href="finder/customer/edit.do"><spring:message code="master.page.finder" /></a></li>
			<li><a href="rating/customer/list.do"><spring:message code="master.page.rating" /></a></li>
			<li><a href="complain/customer/list.do"><spring:message code="master.page.complain" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('DEALER')">
			<li><a href="cashOrder/dealer/list.do"><spring:message code="master.page.cashOrder" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/create.do"><spring:message code="master.page.customer.register" /></a></li>
					<li><a href="restaurant/create.do"><spring:message code="master.page.restaurant.register" /></a></li>
					
				</ul>
			<li><a href="restaurant/list.do"><spring:message code="master.page.restaurant" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li><a href="notification/actor/list.do"><spring:message code="master.page.notification" /></a>
		
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="profile/personal-datas.do"><spring:message code="master.page.profile.see" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
			
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

