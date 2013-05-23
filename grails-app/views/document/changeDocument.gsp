<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.kam.Customer" %>
<%@ page import="com.kam.User" %>
<html>
<head>
<!--  <meta name="layout" content="main" /> -->
</head>
<!-- The form below takes input from user and saves it -->
<body>
	<g:render template="/layouts/navigation" model="[loggedInUserRole:role]"></g:render>
		<!-- back navigation start from here -->
			<ul class="backUl">
				<li class="fr paddingRight"><g:link controller="document" action="backAfterEdit" id="${docId }" class="back"><span><g:message code="login.customer.back"/></span></g:link></li>
			</ul>
		<!-- back navigation end  here -->	
	
	<!-- main start from here ----->
	<div class="customerList">		
		<div id="testing"></div>
			<div class="formHeadingBg fullWidth marginBottom">
				<h2 class="marginLeft">Edit Document : ${document.name }</h2>
				<g:form id="DocumentInputForm" name="DocumentInputForm" controller="document" action="documentChangesSave" id="${document.id }">
					<div class="formInput">	
						<label class="fl inputHeading"><g:message code="document.Document.name"/></label>
						<input type="text" name="DocumentName" id="DocumentName" value="${document.name }" disabled="disabled" class="field" onblur="validateDocumentExists();" />
					</div>
				
					<!-- <div class="formInput">	
						<label class="fl inputHeading"><g:message code="users.user.status"/></label>
						<select class=" selectTextCommon" name="DocumentStatus" id="documentStatus"  > 
							<g:if test="${document.status=='Not Ready' }"><option value="Not Ready" selected><g:message code="document.list.status.notready" /></option></g:if>
							<g:else><option value="Not Ready" ><g:message code="document.list.status.notready" /></option></g:else>
							<g:if test="${document.status=='In Treatment' }"><option value="In Treatment" selected><g:message code="document.list.status.intreatment" /></option></g:if>
							<g:else><option value="In Treatment" ><g:message code="document.list.status.intreatment" /></option></g:else>
							<g:if test="${document.status=='Ready' }"><option value="Ready" selected><g:message code="document.list.status.ready" /></option></g:if>
							<g:else><option value="Ready"><g:message code="document.list.status.ready" /></option></g:else>
							<g:if test="${document.status=='NVT' }"><option value="NVT" selected><g:message code="document.list.status.nvt" /></option></g:if>
							<g:else><option value="NVT" ><g:message code="document.list.status.nvt" /></option></g:else>						
						</select>
					</div>-->
				
					<g:each in="${documentItemList}" var="documentItemValue" >
					
				<g:if test="${!documentItemValue.documentItem.isHeader }">
					<div class="formInput" id="Elements">
					<g:if test="${documentItemValue.type!='Image' && documentItemValue.type!='Table' }">
						<label class="fl inputHeading">${documentItemValue.documentItem.formElementName}</label>
					</g:if>
						<g:if test="${documentItemValue.type=='Text' }">
							<g:if test="${documentItemValue.position=='Notes' }">
								<textarea rows="5" name="${documentItemValue.id}" >${documentItemValue.formElementValue }</textarea>
							</g:if>
							<g:else>
								<input type="text" class="field" name="${documentItemValue.id}" value="${documentItemValue.formElementValue }">
							</g:else>
						</g:if>
						<g:elseif test="${documentItemValue.type=='CheckPoint' }">
							<div id="${documentItemValue.id}" class="paddingTop width300 fl">
								<g:each in="${documentItemValue.questionValues.sort{it.id} }" var="answer">
									<g:if test="${documentItemValue.documentItem.checkpointType=='TextBox' }">
										<input type="text" class="field fr marginTop" name="${documentItemValue.id}" value="${answer.questionValue }">
									</g:if>
									<g:elseif test="${documentItemValue.documentItem.checkpointType=='RadioButton' }">
									 	<g:if test="${answer.questionValue!='' }">
											<g:radio name="${documentItemValue.id}" value="${answer.question.question}" checked="true"/>${answer.question.question} 
										</g:if>
										<g:else>
											<g:radio name="${documentItemValue.id}" value="${answer.question.question}" />${answer.question.question}									
										</g:else>
									</g:elseif>
									<g:elseif test="${documentItemValue.documentItem.checkpointType=='CheckBox' }">
										<g:if test="${answer.questionValue!='' }">
											<g:checkBox name="${documentItemValue.id}" value="${answer.question.question}" checked="true"/>${answer.question.question}
										</g:if>
										<g:else>
											<g:checkBox name="${documentItemValue.id}" value="${answer.question.question}"/>${answer.question.question}									
										</g:else>
									</g:elseif>
								</g:each>
							</div>
						</g:elseif>
						<g:elseif test="${documentItemValue.type=='Table' }">
							<div id="${documentItemValue.id}" >
								<ul class="documentInputUl">
									<g:each status="count" in="${documentItemValue.documentItem.headers.sort{it.id} }" var="header">
										<li class="fl ">${header.name }</li>
									</g:each>
								</ul>
								<g:set var="snumber" value="${1 }"></g:set>
								<g:each status="sno" in="${documentItemValue.documentItem.questions.sort{it.id} }" var="answer">
									<g:if test="${answer.question.trim()!='' && answer.isHeader==false }">
										<ul class="documentInputUl">
											<li class="fl ">${snumber }</li>
											<li class="fl ">${answer.question }</li>
											<g:each status="count" in="${documentItemValue.questionValues.sort{it.id} }" var="header">
												<g:if test="${header.question.id==answer.id }">
													<g:if test="${header.checkpointType=='TextBox' }">
														<li class="fl "><input type="text" class="" name="${documentItemValue.id}TableCheckpoint${header.id }" value="${header.questionValue }"/></li>
													</g:if>
													<g:elseif test="${header.checkpointType=='RadioButton' }">
														<g:if test="${header.questionValue!='' }">
															<li class="fl "><g:radio name="${documentItemValue.id}TableCheckpoint${header.question.id }" value="${header.id }" checked="true"/></li>
														</g:if>
														<g:else>
															<li class="fl "><g:radio name="${documentItemValue.id}TableCheckpoint${header.question.id }" value="${header.id }" /></li>						
														</g:else>										
													</g:elseif>
													<g:elseif test="${header.checkpointType=='CheckBox' }">
														<g:if test="${header.questionValue!='' }">
															<li class="fl "><g:checkBox name="${documentItemValue.id}TableCheckpoint${header.id }" value="${header.id }" checked="true"/></li>
														</g:if>
														<g:else>
															<li class="fl "><g:checkBox name="${documentItemValue.id}TableCheckpoint${header.id }" value="${header.id }" /></li>									
														</g:else>										
													</g:elseif>
												</g:if>
											</g:each>
											</ul>
										</g:if>
								</g:each>
							</div>
						</g:elseif>
						<g:elseif test="${documentItemValue.documentItem.type=='Image' && documentItemValue.documentItem.isSignature==true }">
							<div class="formInput">
								<label class="fl inputHeading">${documentItemValue.documentItem.formElementName}</label>
								<iframe src="/AppUBuild/document/imageUploader?elementName=${documentItemValue.id }" class=" iframeCss fl" > </iframe>
									<img src="${resource(dir:'DocumentImages', file:documentItemValue.formElementValue ) }" width='100px' height="40px" />
									<input type="hidden"  id="${documentItemValue.id }" name="${documentItemValue.id }" value="${documentItemValue.formElementValue }" />
							</div>
						</g:elseif>
					</div>
					</g:if>
				</g:each>
				<div class="width300 fl marginLeft ">
				<div class="cancelCommonBg fl">
				<g:link controller="document" action="showDocument" >
        			<span class="inputButton fl"><g:message code='users.cancel'/> </span>
        		</g:link>
       			</div>
				<div class="submitCommonBg fr ">
					<input type="submit" class="inputButton fl" value="<g:message code='users.submit'/>" >
				</div>
			</div>
			</g:form>
		</div>
		
	</div>
<script src="${resource(dir: 'js', file: 'fileuploader.js') }"></script>
<script type="text/javascript">

function validateDocumentExists(){ 
    var url="${createLink(controller:'document',action:'checkIfDocumentCreated')}";
    var documentName=document.getElementById("DocumentName").value;
    if(documentName=="") return;
    //loading();
    jQuery.ajax({
         type: 'POST',
         url: url,
         data:'documentName='+documentName,
         //alert('email');
         success: function(response,textStatus){
          if(response.sucess==true){
           jQuery('#serverDocumentMessage').show().addClass('sucessfulEnter').removeClass('error');
          }else{
           jQuery('#serverDocumentMessage').show().removeClass('sucessfulEnter').addClass('error');
           }
          jQuery('#serverDocumentMessage').html(response.message);

          },
         error:function(XMLHttpRequest,textStatus,errorThrown){}
     })
 }

$(document).ready(function(){
	$('#TestButton').click(function(){
		var content = $(this).parents('div.width300 fl').find('#content').html();
		$(this).parents('div. fl').append(content)
	});

	$('#DocumentInputForm').submit(function(){	
		if($('#serverDocumentMessage').hasClass('error')){
			return false;
		}
		else{
			return true;
		}		
	});

	
})


</script>
</body>

</html>
