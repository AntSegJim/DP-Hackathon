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

<div> <b> CONDITIONS OF SERVICE OF ACME-ROOKIES </b> <br>
Date of entry into force: January 22, 2019 <br>
Welcome to ACME-ROOKIES <br>
Let the public know that the name of the company offering the service is ACME-ROOKIES and the name of the service is ACME-PARADE.<br>
If at any time the user would like to unsubscribe from the information system, he should send an email to <strong> cristian@hotmail.com </strong> to let them know that he want to be removed from the system. <br>
We thank you for using the products and services of ACME-ROOKIES (hereinafter, the "Services"). <br>
The use of our Services implies the acceptance of these conditions. We recommend that you read them carefully. <br>

Use of our Services <br>
You must follow the policies available through the Services. <br> <br>

You must not use our Services inappropriately. For example, you should not interfere with such Services or attempt to access them using a method other than the interface and instructions provided by Google. You will only be able to use the Services to the extent permitted by law, including laws and regulations on export control and re-exports that are in effect. We can suspend or cancel our Services if you do not comply with our policies or conditions or if we consider that your behavior may be malicious.
<br> <br>
The use of our Services does not make you the owner of any of the intellectual property rights of the same or of the content you access. You may only use the content of our Services if authorized by the owner or if permitted by law. These conditions do not give you the right to use the trademarks or logos used in our Services. Do not delete, hide or alter the legal notices shown in our Services.
<br> <br>
In relation to the use of the Services, we can send you notices of service, administrative messages and other information.
<br> <br>
Some of our Services are available on mobile devices. Do not use those Services in a way that may distract you and prevent you from complying with traffic or security laws.
<br> <br>
Your ACME-ROOKIES account <br>
You may need an ACME-ROOKIES account to use some of our Services. You can create your own ACME-ROOKIES account.
<br> <br>
To protect your ACME-ROOKIES account, keep your password confidential. You are responsible for the activity that takes place in your ACME-ROOKIES account or through it. Try not to reuse the password of your ACME-ROOKIES account in third-party applications. If you detect an unauthorized use of your ACME-ROOKIES account or your password, follow these instructions.
<br> <br>
Protection of privacy and copyright <br>
Our Privacy Policy explains how we use your personal information and protect your privacy when you use our Services.
<br> <br>
We respond to notifications of alleged infractions of copyright and cancel the accounts of users who commit repeated infractions in accordance with the process established in the United States Digital Millennium Copyright Act (DMCA).
<br> <br>
Your content in our Services <br>
Some of our Services allow you to upload, send, store or receive content. If you do so, you will remain the owner of the intellectual property rights you have over that content. In a few words, what belongs to you is yours.
<br> <br>
When uploading, storing or receiving content or sending it to our Services or through them, you grant ACME-ROOKIES (and its collaborators) a worldwide license to use, host, store, reproduce, modify, create derivative works (for example, that result from translation, adaptation or other changes we make to make your content better suited to our Services), communicate, publish, execute or publicly display and distribute said content. ACME-ROOKIES will use the rights conferred by this license solely for the purpose of providing, promoting and improving the Services and developing new services. This license will remain in effect even when you stop using our Services. Some Services allow you to access the content you have provided and delete it. In addition, some of our Services include conditions or adjustments that limit our use of the content that has been sent to them. Make sure you have the necessary rights to grant us this license on any content you submit to our Services.
<br> <br>
If you have an ACME-ROOKIES account, we can show your profile name, profile picture and the actions you take in ACME-ROOKIES.
<br> <br>
About the software of our Services <br>
If a Service requires or includes downloadable software, this software can be automatically updated on your device whenever there are new versions or features available. Some Services may allow you to define automatic update settings.
<br> <br>
ACME-ROOKIES grants you a worldwide personal license, free of royalties, non-transferable and non-exclusive to use the software that is provided to you as part of the Services. The sole purpose of this license is to allow you to use the Services offered by ACME-ROOKIES and benefit from them, as stipulated in these conditions. You may not copy, modify, distribute, sell or lend any part of our Services or the included software or you may apply reverse engineering techniques or attempt to extract the source code of such software, unless the law prohibits such restrictions or if you have ACME's consent -MADRUGA in writing.
<br> <br>
ACME-ROOKIES attaches great importance to open source software. Some of the software used by our Services is offered with an open source software license that we will make available to you. Some of the provisions set forth in the open source software license may expressly nullify some of these conditions.
<br> <br>
How to modify and cancel our Services <br>
ACME-ROOKIES changes and improves its Services constantly. Therefore, it is possible that we add or remove some functions or features, or suspend or cancel a Service completely.
<br> <br>
You can stop using the Services at any time, although we would regret it.
<br> <br>
We consider that you are the owner of your data and that it is important to preserve your access to them. If we interrupt a Service, in cases where it is reasonable, we will inform you in advance and allow you to extract the information from the Service.
<br> <br>
Our guarantees and disclaimers of responsibility <br>
ACME-ROOKIES offers its Services with a level of competence and reasonable diligence from the commercial point of view, and we hope you enjoy using them. However we can not offer guarantees in relation to some aspects of our Services.
<br> <br>
Responsibility for our Services <br>
In the cases permitted by law, neither ACME-ROOKIES nor its suppliers or distributors will be responsible for the loss of profits, income, data, financial losses or for indirect, special, derivative, exemplary or punitive damages.
<br> <br>
To the extent permitted by law, the entire liability of ACME-ROOKIES, as well as that of its suppliers and distributors, for any claim made under these conditions, included by any implied warranty, is limited to the amount you have paid to use the Services (or, if ACME-ROOKIES so decides, upon the resumption of the Services).
<br> <br>
In no case, neither ACME-ROOKIES nor its suppliers and distributors will be responsible for any loss or damage that is not reasonably foreseeable.
<br> <br>
ACME-ROOKIES recognizes that you may have legal rights as a consumer in some countries. If you use the Services for personal purposes, none of the provisions set forth in these conditions or in any of the additional conditions will limit the legal rights of the consumer that can not be waived contractually.
<br> <br>
Use of our Services by companies <br>
If you use the Services on behalf of a company, the company accepts these conditions. ACME-ROOKIES and its affiliates, officers, agents and employees shall be exempted from liability for claims, claims or legal actions that may arise from the use of the Services or the infraction of these conditions or that are related to them, including any liability or expense arising from claims, losses, damages, claims, lawsuits, procedural costs and attorneys' fees.
<br> <br>
About these conditions <br>
ACME-ROOKIES can modify these conditions or the additional conditions that apply to a Service to, for example, reflect legislative changes or in the Services. We recommend that you check the conditions periodically. ACME-ROOKIES will publish notices related to the modifications of these conditions on this page. Likewise, it will publish notices related to the modifications that are made in the additional conditions of the corresponding Service. The modifications will not be applied retroactively and will take effect no later than 14 days from the date of their publication. However, modifications that affect new functions of a Service or changes made due to legal issues will take effect immediately. If you do not accept the modified conditions of a Service, you must stop using the Service.
<br> <br>
In case of conflict between these conditions and the additional conditions, these conditions will prevail over the additional conditions.
<br> <br>

These conditions govern the relationship between ACME-ROOKIES and you, and do not generate any rights from which a third party may be a beneficiary.
<br> <br>
If you do not comply with these conditions and ACME-ROOKIES does not take any action in this regard immediately, it will not be understood that ACME-ROOKIES waives any rights that may be available (such as, for example, carrying out actions in the future).
<br> <br>
In the event that a certain condition is not mandatory, the rest of the conditions will not be affected.
<br> <br>
For more information on how to get in touch with ACME-ROOKIES by mail: crilorbre@alumn.us.es
<br> <br>


<b> ATTENTION </b>: The content of this page has been extracted from the conditions of Google and to which some modifications have been made to adapt them to our policies
with which we are not the owners of the wording of these conditions. The author of much of the previous text is Google. </div>