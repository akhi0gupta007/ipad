
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

<!-- back Navigation start from here -->
	<ul class="backUl">
		
		<li class="fr paddingRight"><g:link controller="document" action="documentList" id="${templateId}" class="back"><span><g:message code="login.customer.back"/></span></g:link></li>
		
	</ul>
<!-- back Navigation end from here -->
<!-- main content start from here -->
<div class="customerList">		
		<div class="formHeadingBg fullWidth marginBottom">
			<h2 class="marginLeft"><g:message code="document.new.document"/></h2>
			<g:form id="DocumentInputForm" name="DocumentInputForm" controller="document" action="documentSave" autocomplete='off'>
				<div class="docuWidth fl" >	
					<label class="fl inputHeading"><g:message code="document.Document.name"/></label>
					<input type="hidden" value="${templateId}" name="templateId">
					<input type="text" name="DocumentName" id="DocumentName" placeholder="<g:message code="document.Document.name"/>"  class="field"  onblur="validateDocumentNameExists();" />
						<div id="serverDocumentNameMessage" class="width300 ">
							<g:if test="${flash.error }">
							 ${flash.error }
							</g:if>
						</div>
				</div>
				<div class=" docuWidth fl">	
					<label class="fl inputHeading"><g:message code="document.documentnumber"/></label>
					<input type="text" name="DocumentNumber" id="DocumentNumber" placeholder="<g:message code="document.documentnumber"/>"  class="field"  onblur="validateDocumentNumberExists();" />
						<div id="serverDocumentNumberMessage" class="width300 error"><g:if test="${flash.error }">
					 		${flash.error }</g:if>
						</div>
					
				</div>
				
				
	<div>
	<div class=" docuWidth fl">	
	<label class="fl inputHeading"><g:message code="list.project"/></label>
      <select onchange="updateSelect('project',this.value);" class=" selectTextCommon "  name="documentProject" id="documentProject">
      	<option value="select" selected> <g:message code="messages.select.project"/> </option>
       	<g:each in="${projectList }" var="project">
       		<option value="${project.id}">${project.projectName }</option>
      	</g:each>
      </select>
      <div id="documentProjectMessage" class="width300 error"></div>
      </div>
      <div class="displayNone docuWidth fl">	
	<label class="fl inputHeading"><g:message code="view.document.building"/></label>
      <select onchange="updateSelect('building',this.value);" name="buildingsDropdown"  id="buildingsDropdown" class=" selectTextCommon ">
      </select>
      </div>
      <div class="displayNone docuWidth fl">	
	<label class="fl inputHeading"><g:message code="view.document.floor"/></label>
      <select onchange="updateSelect('floor',this.value);" name="floorsDropdown" id="floorsDropdown" class=" selectTextCommon">
      </select>
      </div>
      <div class="displayNone docuWidth fl">	
	<label class="fl inputHeading"><g:message code="view.document.room"/></label>
      <select onchange="updateSelect('room',this.value);" name="roomsDropdown" id="roomsDropdown" class=" selectTextCommon">
      </select>
      </div>
   </div>
		
		
				
				
			<g:each in="${documentItemList}" var="documentItem" >
				<div class="formInput" id="Elements">	
				<g:if test="${documentItem.type!='Image' && documentItem.type!='Table' }">
					<label class="fl inputHeading">${documentItem.formElementName}</label>
					</g:if>
					<g:if test="${documentItem.type=='Text' }">
						<g:if test="${documentItem.position=='Notes' }">
							<textarea rows="5" name="${documentItem.id}"></textarea>
						</g:if>
						<g:else>
							<input type="text" class="field" name="${documentItem.id}" placeholder="Element's value">
						</g:else>
					</g:if>
					<g:elseif test="${documentItem.type=='CheckPoint' }">
						<div id="${documentItem.id}" class="width300  fl">
							<g:each in="${documentItem.questions.sort{it.id} }" var="question">
								<g:if test="${documentItem.checkpointType=='TextBox' }">
									<input type="text" class="field fr marginTop" name="${documentItem.id}" placeholder="${question.question}">
								</g:if>
								<g:elseif test="${documentItem.checkpointType=='RadioButton' }">
									<div class="paddingTop fl marginRight"><g:radio name="${documentItem.id}" value="${question.question}"/>${question.question}</div>
								</g:elseif>
								<g:elseif test="${documentItem.checkpointType=='CheckBox' }">
									<div class="paddingTop fl marginRight"><g:checkBox name="${documentItem.id}" value="${question.question}" checked="false"/>${question.question}</div>
								</g:elseif>
							</g:each>
						</div>
					</g:elseif>
					<g:elseif test="${documentItem.type=='Table' }">
						<div id="${documentItem.id}" >
							<ul class="documentInputUl">
								<g:each status="count" in="${documentItem.headers.sort{it.id} }" var="header">
									<li class="fl ">${header.name }</li>
								</g:each>
							</ul>
							<g:set var="sno" value="${1 }" />
							<g:each in="${documentItem.questions.sort{it.id} }" var="question">
							
								<g:if test="${question.isHeader==false }">
									<ul class="documentInputUl"><li class="fl ">${sno++ }</li><li class="fl ">${question.question }</li>
										<g:each status="count" in="${documentItem.headers.sort{it.id} }" var="header">
											<g:if test="${count!=0 && count!=1 }">
												<g:if test="${header.checkpointType=='TextBox' }">
													<li class="fl ">	<input type="text" class="  " name="${documentItem.id }doc${question.id}TableCheckpoint${header.id }" placeholder="${question.question}"></li>
												</g:if>
												<g:elseif test="${header.checkpointType=='RadioButton' }">
													<li class="fl "><g:radio name="${documentItem.id }doc${question.id}TableCheckpoint" value="${header.name}"/></li>
												</g:elseif>
												<g:elseif test="${header.checkpointType=='CheckBox' }">
													<li class="fl "><g:checkBox name="${documentItem.id }doc${question.id}TableCheckpoint${header.id }" value="${header.name}" checked="false"/></li>
												</g:elseif>
											</g:if>
										</g:each>
									</ul>
								</g:if>
							</g:each>
						</div>
					</g:elseif>
					<g:elseif test="${documentItem.type=='Image' && documentItem.isSignature==true }">
						<div class="formInput">
							<iframe src="/AppUBuild/document/imageUploader?elementName=${documentItem.id }" class="moveSelectLeft iframeCss fl" > </iframe>
							<input type="hidden"  id="${documentItem.id }" name="${documentItem.id }" />
						</div>
					</g:elseif>
				</div>
			</g:each>
			<div class="width300 fl marginLeft ">
				<div class="cancelCommonBg fl">
				<g:link controller="document" action="templateList" >
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
  <!-- main content end here -->
  	
	
	<script type="text/javascript">
	$(document).ready(function(){
		
		$('#DocumentInputForm').find('input[type=text]').val('');
		$('#documentProject').change(function(){
			if($('#documentProject').val()!='select' || $('#documentProject').val()!='kiezen'){
				$('#documentProjectMessage').html('');
			}
		});
		$('#DocumentInputForm').submit(function(){	
			var error=0;
			if($('#documentProject').val()=='select' || $('#documentProject').val()=='kiezen'){
				$('#documentProjectMessage').html('<g:message code="messages.project.mandatory" />');
				error=1;
				
			}
			else
				$('#documentProjectMessage').html('');
			if($('#DocumentNumber').val()==''){
				$('#serverDocumentNumberMessage').html('<g:message code="messages.document.number.mandatory" />');
				error=1;
				
			}
			if($('#serverDocumentNameMessage').hasClass('error') || $('#serverDocumentNumberMessage').hasClass('error')){
				error=1;
				
			}
			if(error==1)
				return false;
			else{
				return true;
			}
		});	

		
	});
		function validateDocumentNameExists(){ 
    		var url="${createLink(controller:'document',action:'checkIfDocumentCreated')}";
   			var documentName=document.getElementById("DocumentName").value;
   			if(documentName=="") return;
    //loading();
    	jQuery.ajax({
         	type: 'POST',
         	url: url,
         	data:'documentName='+documentName,
         	success: function(response,textStatus){
          	if(response.sucess==true){
           		jQuery('#serverDocumentNameMessage').show().addClass('sucessfulEnter').removeClass('error');
          	}else{
           		jQuery('#serverDocumentNameMessage').show().removeClass('sucessfulEnter').addClass('error');
           	}
          	jQuery('#serverDocumentNameMessage').html(response.message);
			},
         	error:function(XMLHttpRequest,textStatus,errorThrown){}
     	})
	 	}

	function validateDocumentNumberExists(){ 
    	var url="${createLink(controller:'document',action:'checkIfDocumentCreated')}";
    	var documentNumber=document.getElementById("DocumentNumber").value;
    	if(documentNumber==""){ 
    		$('#serverDocumentNumberMessage').addClass('error');
        	return;
    	}
    //loading();
    	jQuery.ajax({
         	type: 'POST',
        	 url: url,
         	data:'documentNumber='+documentNumber,
         	success: function(response,textStatus){
          if(response.sucess==true){
           		jQuery('#serverDocumentNumberMessage').show().addClass('sucessfulEnter').removeClass('error');
          }else{
          	 jQuery('#serverDocumentNumberMessage').show().removeClass('sucessfulEnter').addClass('error');
          }
          	jQuery('#serverDocumentNumberMessage').html(response.message);

          },
         error:function(XMLHttpRequest,textStatus,errorThrown){}
     	})
 }
	function updateSelect(type,project){
		if(project=='select')
			return
		var url="${createLink(controller:'document',action:'populateSelect')}";
		var value='';
	    jQuery.ajax({
	         type: 'POST',
	         url: url,
	         data:'type='+type+'&value='+project,
	         success: function(response,textStatus){
	        	 $('#'+response.update).html('');
	        	 $('#'+response.update).parent('div.docuWidth').removeClass('displayNone');
	        	 $('#'+response.update).append('<option value="select"><g:message code="messages.select" /> '+response.next+'</option>');
		        $.each(response.list, function() {
		        	if(!this.isDeleted){
		        	  if(response.next=='building')
				         value=this.buildingName
				      else if(response.next=='floor')
				         value=this.floorNumber
				      else if(response.next=='room')
					     value=this.roomId
					     
					     $('#'+response.update).append('<option value="'+this.id+'">'+value+'</option>');
		        	}
		        	});
	          },
	         error:function(XMLHttpRequest,textStatus,errorThrown){ }
	     });
	 }
</script>
</body>

</html>
