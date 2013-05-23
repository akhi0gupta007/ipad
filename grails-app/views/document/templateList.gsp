<!DOCTYPE html>
<%@page import="com.kam.DocumentItem"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.kam.Customer" %>
<%@ page import="com.kam.User" %>
<html>
<head>
<meta name="layout" content="main" />
</head>
<!-- The form below takes input from user and saves it -->
<body>
<g:render template="/layouts/navigation" model="[loggedInUserRole:role]"></g:render>
  
  
   <ul class="backUl">
   <li class="fl upperDownPadding"><g:message code="template.list"/></li>
   <li class="fr paddingRight "><g:link controller="site" class="back"><span><g:message code="login.customer.back"/></span></g:link></li>
   </ul>
   
  <div class="customerList">
  <div class="customerCompany gridColor">
   <ul class="adminHead fullWidth ">
   <g:if test="${role=='ROLE_USER' }">
   	<li class="fl serialNo docList"><g:message code="template.serial.no"/></li>
   	<li class="fl width20 docList"><g:message code="template.template.name"/></li>
	<li class="fl width20 docList"><g:message code="template.created.by"/></li>
    <li class="fl width20 docList"><g:message code="template.created.date"/></li>
    <li class="fl widthEighteen docList"><g:message code="template.updated.date"/></li>
   	<li class="fl enable paddingTop"><g:message code="template.view"/></li>
   	<!-- <li class="fl paddingTop  delete"><g:message code="template.edit"/></li> -->
   	<li class="paddingTop role fr textRight" ><g:message code="use.template"/></li>
   	<!-- <li class="fr paddingTop textRight delete"><g:message code="template.delete"/></li> -->
   	</g:if>
   	<g:else test="${role=='ROLE_ADMIN' }">
   		<li class="fl serialNo paddingTop"><g:message code="template.serial.no"/></li>
   <li class="fl widthEighteen paddingTop"><g:message code="template.template.name"/></li>
	<li class="fl templateNameHead paddingTop"><g:message code="template.created.by"/></li>
    <li class="fl userName paddingTop"><g:message code="template.created.date"/></li>
     <li class="fl userName paddingTop"><g:message code="template.updated.date"/></li>
   <li class="fl width12Half paddingTop"><g:message code="template.view"/></li>
   <li class="fl paddingTop  width10"><g:message code="template.edit"/></li>
   
   <li class="fr paddingTop textRight delete"><g:message code="template.delete"/></li>
   	</g:else>
   </ul>
   </div>
   <div id="Templates">
   		<g:if test="${documentTemplateList.size()>0 }">
   			<g:render template="templates" model="[documentTemplateList:documentTemplateList,loggedInUserRole:role,sno:sno]"></g:render>
   		</g:if>
   		<g:else>
   			<div class="customerCompany thirdGridColor "><ul class="adminHead fullWidth "><li class="company paddingTop fl"><g:message code="messages.templates.emptylist" /> </li></ul></div>
   		</g:else>
   	</div>
   </div>

   <div class="overlay" id="openViewTemplate">
   
   <div class="overlayMain">
  
   <div class="overlayStart">
    
   <div id="TemplateDetails"> </div>
   
   </div>
   
   </div>
   
   
   </div>
   
   
   <script type='text/javascript'>
	

	function deleteTemplate(data,id){
		if(data.sucess){
			$('#'+id).remove();
			var templateCount=1;
			$('#Templates').find('div.customerCompany > ul > li.serialNo ').each(function(){
				$(this).html(templateCount);
				templateCount=templateCount+1;
				});
			if(templateCount==1)
				$('#Templates').html('<div class="customerCompany thirdGridColor "><ul class="adminHead fullWidth "><li class="company paddingTop fl"><g:message code="messages.templates.emptylist" /> </li></ul></div>')
			
		}
	}
		</script>

</body>
</html>