<!DOCTYPE html>
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
	
 	<!-- back Navigation start from here --> 
   		<ul class="backUl">
   			<li class="fr paddingRight"><a href="javascript:history.go(-1)" class="back"><span> <g:message code="login.customer.back"/></span></a></li>
   		</ul>
 	<!-- back Navigation end here -->  
 	
	<div class="customerList">
  		<div class="newTemplate formHeadingBg">
  			
     			<h2 class="formHead"><g:message code="messages.equipment.view.details"/></h2>
  			<div class="inputHeading fullWidth">
      			<g:form name="editEquipments" id="editEquipments" controller="equipment" action="saveEquipmentChanges" autocomplete='off'>
        				<input type="hidden" id="currentEquipmentId" name="currentEquipmentId" value="${equipment.id }"/>        				
      				    <div class="formInput">
							<label class="fl  widthHalf"><g:message code="messages.equipment.type"/></label>
							<div class="paddingTop marginRight fl">
							<g:if test="${equipment.equipmentType=='Inbuilt'}">
							<g:message code="messages.equipment.type.inbuilt"/></g:if>
							<g:else>
							<g:message code="messages.equipment.type.external"/></g:else>
							<%--<div class="paddingTop marginRight fl">${equipment.equipmentType }
 							</div>
 							--%></div>
 							
  						</div>
  						
  						<div class="formInput">
							<label class="fl  widthHalf"><g:message code="armatur.emergency.exit.type"/></label>
							<div class="paddingTop marginRight fl">
							<g:if test="${equipment.equipmentType2=='Indication'}">
							<g:message code="messages.equipment.type.indication"/></g:if>
							<g:else>
							<g:message code="messages.equipment.type.lighting"/></g:else>
							<%--${equipment.equipmentType2 }--%>
 							</div>
 							</div>
  						<div class="formInput">
							<label class="fl widthHalf"><g:message code='component.equipment.name'/></label>
							${equipment.name }
						</div>
						<div class="formInput">
							<label class="fl widthHalf"><g:message code='component.equipment.description'/></label>
							${equipment.description }
						</div>
						
						
      				    <div class="formInput">
							<label class="fl widthHalf"><g:message code='component.brand'/></label>
							<g:if test="${equipment.brand!=null }">
								${equipment.brand.brandName }
							</g:if>
						</div>
						<div class="formInput">
							<label class="fl widthHalf"><g:message code='component.armature'/></label>
							<g:if test="${equipment.armatuur!=null }">
							${equipment.armatuur.armatuurType }
								</g:if>
						</div>
						<div class="formInput">
							<label class="fl widthHalf"><g:message code='component.buildyear.armature'/></label>
							${equipment.buildYearOfArmature }
						</div>
						<div class="formInput">
							<label class="fl widthHalf"><g:message code='component.emergency.unit.print'/></label>
							<g:if test="${equipment.emergencyUnitOfPrint!=null }">
							${equipment.emergencyUnitOfPrint.unitName }
							</g:if>
						</div>
						<div class="formInput">
							<label class="fl widthHalf"><g:message code='component.buildyear.emergencyunit'/></label>
							${equipment.buildYearOfEmergencyUnit }
						</div>
						<div class="formInput">
							<label class="fl widthHalf"><g:message code='component.light'/></label>
							<g:if test="${equipment.light!=null }">
								${equipment.light.name }
							</g:if>
						</div>
						
						<div class="formInput">
							<label class="fl widthHalf"><g:message code='component.battery'/></label>
							<g:if test="${equipment.battery!=null }">
									${equipment.battery.batteryType }
								</g:if>
						</div>
						<div class="formInput">
							<label class="fl widthHalf"><g:message code='component.buildyear.battery'/></label>
							${equipment.buildYearOfBattery }
						</div>
						
						
						
						<div class="formInput">
							<label class="fl widthHalf"><g:message code='messages.equipment.group.number'/></label>
							<g:if test="${equipment.groupNo!=null }">
								${equipment.groupNo.groupNumber }
							</g:if>
						</div>
						<div class="formInput">
							<label class="fl widthHalf"><g:message code='messages.equipment.kast'/></label>
							<g:if test="${equipment.kast!=null }">
								${equipment.kast.kastName }
							</g:if>
						</div>
						
						
						<!-- Equipment checkpoint editing starts here -->
						
						<div class="formInput">
							<label class="fl width32"><g:message code='messages.equipment.checkpoints'/></label>
						</div>
						<g:if test="${equipmentCheckpoints!=null && equipmentCheckpoints.size()>0 }">
						<g:each in="${equipmentCheckpoints }" var="equipmentCheckpoint">
							
							<div class="formInput">
						
								<div  class="fl fullWidth"><label class="widthHalf fl">${equipmentCheckpoint.checkpointDescription }: &nbsp; ${equipmentCheckpoint.comment}</label><label class="fl">${equipmentCheckpoint.status }</label></label></div>
								<span class="fullWidth viewDocPadding fl">
								<g:each in="${equipmentCheckpoint.equipmentImages }" var="equipmentImage">
            						<img src="${resource(dir:'ReportImages',file:equipmentImage.imageName) }"  width="150px" height="100px" />
            					</g:each>
            					
            					</span>
							</div>
						</g:each>
						</g:if>
						
						<!-- <div class="fl width300 moveSelectLeft">
							<div class="cancelCommonBg">
								<g:link controller="equipment" action="viewEquipment" id="${equipment.id }">
        							<span class="inputButton fl"><g:message code='users.cancel'/> </span>
        						</g:link>
        					</div> 
							<div class="submitCommonBg fr">
								<input type="submit" class="inputButton fl" value="<g:message code='users.submit'/>"/>
							</div>
						</div>-->
      				</g:form>
  	</div>
   </div>
   <script type="text/javascript">
function showOptions(value,id){
	if(value=='Other')
	{
		$('#'+id+'Name').removeAttr('readonly');
		$('#'+id+'Name').val('');
	}
	else{
		$('#'+id+'Name').val(value);
		$('#'+id+'Name').attr('readonly','readonly');
	}
}

$(document).ready(function(){
	
	$('#editEquipments').find('select').each(function(){
		if($(this).val()=='Other'){
			$('#'+this.id+'Name').val('');
			$('#'+this.id+'Name').removeAttr('readonly');
		}
		else{
			$('#'+this.id+'Name').attr('readonly','readonly');
			$('#'+this.id+'Name').val($(this).val());
		}
	});	
})



 </script>
    
  </div>
</body>
</html>