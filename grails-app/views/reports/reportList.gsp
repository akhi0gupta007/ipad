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
   <li class="fl upperDownPadding"><g:message code="messages.report.list"/></li>
   <li class="fr paddingRight "><g:link controller="component" action="projectList" class="back"><span><g:message code="login.customer.back"/></span></g:link></li>
   </ul>
   
   	<div class="customerList">
   	<div class="customerCompany gridColor">
   	<ul class="adminHead fullWidth ">
   		<li class="fl serialNo paddingTop"><g:message code="template.serial.no"/></li>
   		<li class="fl widthEighteen paddingTop"><g:message code="new.customer.name"/></li>
		<li class="fl templateNameHead paddingTop"><g:message code="messages.reports.reviewedby"/></li>
    	<li class="fl userName paddingTop"><g:message code="template.created.date"/></li>
     	<li class="fl userName paddingTop"><g:message code="template.updated.date"/></li>
   		<li class="fl width12Half paddingTop"><g:message code="template.view"/></li>
   		<li class="fl paddingTop  width10"><g:message code="template.edit"/></li>
   		<li class="fr paddingTop textRight delete"><g:message code="template.delete"/></li>
   </ul>
   </div>
   <g:if test="${building.reports.size()>0 }">
   		<div id="ReportsList">
   			<g:render template="reports" model="[building:building,loggedInUserRole:role]"></g:render>
   		</div>
   	</g:if>
   	<g:else>
   		<div class=" paddingTop customerCompany thirdGridColor" >
           	<g:message code="messages.reports.emptylist"/>
        </div>
   	</g:else>
   </div>

   <div class="overlay" id="openViewTemplate">
   		<div class="overlayMain">
   			<div class="overlayStart">
   				<div id="TemplateDetails"> </div>
   			</div>
   		</div>
   </div>
   <script src="${resource(dir:'js',file:'easypaginate.js') }"></script>
   <script type='text/javascript'>
	function deleteReport(data,id){
		if(data.sucess){
			$('#'+id).remove();
			var reportCount=1;
			$('#ReportsList').find('div.customerCompany > ul > li.serialNo ').each(function(){
				$(this).html(reportCount);
				reportCount=reportCount+1;
				});
			$('#pagination').remove();
			$('#ReportsList').easyPaginate({
			  });
			if(reportCount==1)
				$('#ReportsList').html('<div class="paddingTop customerCompany thirdGridColor "><g:message code="messages.reports.emptylist" /></div>');
		}
	}

	$(document).ready(function(){
		  $('#ReportsList').easyPaginate({
		  });
		}); 

	function confirmReportDelete(name){
		var selection=confirm(deletetionConfirm+" "+name+"?");
		if(selection==false)
			return false;
		else
			return true;
	}
		</script>
   
   
</body>
</html>