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
  			
     			<h2 class="formHead"><g:message code="template.edit"/> <g:message code="component.equipment"/></h2>
  			<div class="inputHeading fullWidth">
      			<g:form name="editEquipments" id="editEquipments" controller="equipment" action="saveEquipmentChanges" autocomplete='off'>
        				<input type="hidden" id="currentEquipmentId" name="currentEquipmentId" value="${equipment.id }"/>        				
      				    <div class="formInput">
							<label class="fl  width32"><g:message code="messages.equipment.type"/></label>
							<div class="paddingTop marginRight fl">
								<g:if test="${equipment.equipmentType=='Inbuilt' }">
 									<g:radio name="equipmentType" id="equipmentTypeInbuilt"  value="Inbuilt" checked="true"/><g:message code="messages.equipment.type.inbuilt"/>
 								</g:if>
 								<g:else>
 									<g:radio name="equipmentType" id="equipmentTypeInbuilt"  value="Inbuilt" /><g:message code="messages.equipment.type.inbuilt"/>
 								</g:else>
 							</div>
 							<div class="paddingTop marginRight fl">
 								<g:if test="${equipment.equipmentType=='External' }">
  									<g:radio name="equipmentType" id="equipmentTypeExternal" value="External" checked="true" /><g:message code="messages.equipment.type.external"/>
  								</g:if>
  								<g:else>
  									<g:radio name="equipmentType" id="equipmentTypeExternal" value="External" /><g:message code="messages.equipment.type.external"/>
  								</g:else>
  							</div>
  						</div>
  						
  						
  						<div class="formInput">
							<label class="fl  width32"><g:message code="armatur.emergency.exit.type"/></label>
							<div class="paddingTop marginRight fl">
								<g:if test="${equipment.equipmentType2=='Indication' }">
 									<g:radio name="equipmentType2" id="equipmentTypeIndication"  value="Indication" checked="true"/><g:message code="messages.equipment.type.indication"/>
 								</g:if>
 								<g:else>
 									<g:radio name="equipmentType2" id="equipmentTypeIndication"  value="Indication" /><g:message code="messages.equipment.type.indication"/>
 								</g:else>
 							</div>
 							<div class="paddingTop marginRight fl">
 								<g:if test="${equipment.equipmentType2=='Lighting' }">
  									<g:radio name="equipmentType2" id="equipmentTypeLighting" value="Lighting" checked="true" /><g:message code="messages.equipment.type.lighting"/>
  								</g:if>
  								<g:else>
  									<g:radio name="equipmentType2" id="equipmentTypeLighting" value="Lighting" /><g:message code="messages.equipment.type.lighting"/>
  								</g:else>
  							</div>
  						</div>
  						
  						<div class="formInput">
							<label class="fl width32"><g:message code='component.equipment.name'/></label>
							<input type="text" class="overlayInput "   name='equipmentName' id='equipmentName' value="${equipment.name }" />
							<div id="equipmentNameError" class="error"></div>
  						</div>
						<div class="formInput">
							<label class="fl width32"><g:message code='component.equipment.description'/></label>
							<input type="text" class="overlayInput "   name='equipmentDescription' id='equipmentDescription' value="${equipment.description }" />
						</div>
  						
      				    <div class="formInput">
							<label class="fl width32"><g:message code='component.brand'/></label>
							<select class="overlaySelect" id="brand" name="brand" onchange="showOptions(this.value,'brand','oldBrand');" >
								<g:each in="${brands }" var="brand">
									<g:if test="${equipment.brand!=null && equipment.brand.brandName==brand.brandName }">
										<option value="${brand.brandName }" selected>${brand.brandName }</option>
									</g:if>
									<g:else>
										<option value="${brand.brandName }" >${brand.brandName }</option>
									</g:else>
								</g:each>
								<g:if test="${equipment.brand!=null }">
									<option value="Other">Other</option>
								</g:if>
								<g:else>
									<option value="Other" selected>Other</option>
								</g:else>
							</select>
							<g:if test="${equipment.brand!=null }">
								<input type="text" class="overlayInput "   name='brandName' id='brandName' value="${equipment.brand.brandName }" />
								<input type="hidden" class="overlayInput "   name='oldBrandName' id='oldBrandName' value="${equipment.brand.brandName }"/>
							</g:if>
							<g:else>
								<input type="text" class="overlayInput "   name='brandName' id='brandName' value=""/>
								<input type="hidden" class="overlayInput "   name='oldBrandName' id='oldBrandName' value="" />
							</g:else>
							<div id="brandNameError" class="error"></div>
						</div>
						
						<div class="formInput">
							<label class="fl width32"><g:message code='component.armature'/></label>
							<select class="overlaySelect" id="armature"  name="armature" onchange="showOptions(this.value,'armature','oldArmature');">
								<g:each in="${armatures }" var="armature">
									<g:if test="${equipment.armatuur!=null && equipment.armatuur.armatuurType == armature.armatuurType }">
										<option value="${armature.armatuurType }" selected>${armature.armatuurType }</option>
										</g:if>
									<g:else>
										<option value="${armature.armatuurType }">${armature.armatuurType }</option>
									</g:else>
								</g:each>
								<g:if test="${equipment.armatuur!=null }">
									<option value="Other">Other</option>
								</g:if>
								<g:else>
									<option value="Other" selected>Other</option>
								</g:else>
								</select>
								<g:if test="${equipment.armatuur!=null }">
									<input type="text" class="overlayInput "   name='armatureName' id='armatureName' value="${equipment.armatuur.armatuurType }" />
									<input type="hidden" class="overlayInput "   name='oldArmatureName' id='oldArmatureName' value="${equipment.armatuur.armatuurType }"/>
								</g:if>
								<g:else>
									<input type="text" class="overlayInput "   name='armatureName' id='armatureName' value=""/>
									<input type="hidden" class="overlayInput "   name='oldArmatureName' id='oldArmatureName' value=""/>
								</g:else>
							<div id="armatureNameError" class="error"></div>
						</div>
						<div class="formInput">
							<label class="fl width32"><g:message code='component.buildyear.armature'/></label>
							<input type="text" class="overlayInput "   name='buildYearOfArmature' id='buildYearOfArmature' value="${equipment.buildYearOfArmature }" />
							<div id="buildYearOfArmatureError" class="error"></div>
						</div>
						
						<div class="formInput">
							<label class="fl width32"><g:message code='component.emergency.unit.print'/></label>
							<select class="overlaySelect" id="emergencyUnitOfPrint" name="emergencyUnitOfPrint" onchange="showOptions(this.value,'emergencyUnitOfPrint','oldEmergencyUnitOfPrint');"> 
								<g:each in="${emergencyUnit }" var="emergencyUnitData">
									<g:if test="${equipment.emergencyUnitOfPrint!=null && equipment.emergencyUnitOfPrint.unitName == emergencyUnitData.unitName }">
										<option value="${emergencyUnitData.unitName }" selected>${emergencyUnitData.unitName }</option>
										</g:if>
									<g:else>
										<option value="${emergencyUnitData.unitName }" >${emergencyUnitData.unitName }</option>
									</g:else>
								</g:each>
								<g:if test="${equipment.emergencyUnitOfPrint!=null }">
									<option value="Other">Other</option>
								</g:if>
								<g:else>
									<option value="Other" selected>Other</option>
								</g:else>
								</select>
								<g:if test="${equipment.emergencyUnitOfPrint!=null }">
									<input type="text" class="overlayInput "   name='emergencyUnitOfPrintName' id='emergencyUnitOfPrintName' value="${equipment.emergencyUnitOfPrint.unitName }" />
									<input type="hidden" class="overlayInput "   name='oldEmergencyUnitOfPrintName' id='oldEmergencyUnitOfPrintName' value="${equipment.emergencyUnitOfPrint.unitName }" />
								</g:if>
								<g:else>
									<input type="text" class="overlayInput "   name='emergencyUnitOfPrintName' id='emergencyUnitOfPrintName' value=""/>
									<input type="hidden" class="overlayInput "   name='oldEmergencyUnitOfPrintName' id='oldEmergencyUnitOfPrintName' value="" />
								</g:else>
							<div id="emergencyUnitOfPrintNameError" class="error"></div>
						</div>
						<div class="formInput">
							<label class="fl width32"><g:message code='component.buildyear.emergencyunit'/></label>
							<input type="text" class="overlayInput "   name='buildYearOfEmergencyUnit' id='buildYearOfEmergencyUnit' value="${equipment.buildYearOfEmergencyUnit }" />
							<div id="buildYearOfEmergencyUnitError" class="error"></div>
						</div>
						
						<div class="formInput">
							<label class="fl width32"><g:message code='component.light'/></label>
							<select class="overlaySelect" id="light" name="light" onchange="showOptions(this.value,'light','oldLight');">
								<g:each in="${light }" var="lightData">
									<g:if test="${equipment.light!=null && equipment.light.name == lightData.name }">
										<option value="${lightData.name }" selected>${lightData.name }</option>
										</g:if>
									<g:else>
										<option value="${lightData.name }" >${lightData.name }</option>
									</g:else>
								</g:each>
								<g:if test="${equipment.light!=null }">
									<option value="Other">Other</option>
								</g:if>	
								<g:else>
									<option value="Other" selected>Other</option>
								</g:else>
								</select>
								<g:if test="${equipment.light!=null }">
									<input type="text" class="overlayInput "   name='lightName' id='lightName' value="${equipment.light.name }" />
									<input type="hidden" class="overlayInput "   name='oldLightName' id='oldLightName' value="${equipment.light.name }"/>
								</g:if>
								<g:else>
									<input type="text" class="overlayInput "   name='lightName' id='lightName' value=""/>
									<input type="hidden" class="overlayInput "   name='oldLightName' id='oldLightName' value="" />
								</g:else>
							<div id="lightNameError" class="error"></div>
						</div>
						
						
						
						
						
						
						<div class="formInput">
							<label class="fl width32"><g:message code='component.battery'/></label>
							<select class="overlaySelect" id="battery" name="battery" onchange="showOptions(this.value,'battery','oldBattery');">
								<g:each in="${battery }" var="batteryData">
									<g:if test="${equipment.battery!=null && equipment.battery.batteryType == batteryData.batteryType }">
										<option value="${batteryData.batteryType}" selected>${batteryData.batteryType }</option>
										</g:if>
									<g:else>
										<option value="${batteryData.batteryType}">${batteryData.batteryType }</option>
									</g:else>
								</g:each>
								<g:if test="${equipment.battery!=null }">
									<option value="Other">Other</option>
								</g:if>
								<g:else>
									<option value="Other" selected>Other</option>
								</g:else>
								</select>
								<g:if test="${equipment.battery!=null }">
									<input type="text" class="overlayInput "   name='batteryName' id='batteryName' value="${equipment.battery.batteryType }" />
									<input type="hidden" class="overlayInput "   name='oldBatteryName' id='oldBatteryName' value="${equipment.battery.batteryType }" />
								</g:if>
								<g:else>
									<input type="text" class="overlayInput "   name='batteryName' id='batteryName' value=""/>
									<input type="hidden" class="overlayInput "   name='oldBatteryName' id='oldBatteryName' value="" />
								</g:else>
							<div id="batteryNameError" class="error"></div>
						</div>
						<div class="formInput">
							<label class="fl width32"><g:message code='component.buildyear.battery'/></label>
							<input type="text" class="overlayInput "   name='buildYearOfBattery' id='buildYearOfBattery' value="${equipment.buildYearOfBattery }"/>
							<div id="buildYearOfBatteryError" class="error"></div>
						</div>
						<div class="formInput">
							<label class="fl width32"><g:message code='messages.equipment.group.number'/></label>
							<select class="overlaySelect" id="group" name="group" onchange="showOptions(this.value,'group','oldGroup');" >
								<g:each in="${groupNos }" var="groupNo">
									<g:if test="${equipment.groupNo!=null && equipment.groupNo.groupNumber==groupNo.groupNumber }">
										<option value="${groupNo.groupNumber }" selected>${groupNo.groupNumber }</option>
									</g:if>
									<g:else>
										<option value="${groupNo.groupNumber }" >${groupNo.groupNumber }</option>
									</g:else>
								</g:each>
								<g:if test="${equipment.groupNo!=null }">
									<option value="Other">Other</option>
								</g:if>
								<g:else>
									<option value="Other" selected>Other</option>
								</g:else>
							</select>
							<g:if test="${equipment.groupNo!=null }">
								<input type="text" class="overlayInput "   name='groupName' id='groupName' value="${equipment.groupNo.groupNumber }" />
								<input type="hidden" class="overlayInput "   name='oldGroupName' id='oldGroupName' value="${equipment.groupNo.groupNumber }" />
							</g:if>
							<g:else>
								<input type="text" class="overlayInput "   name='groupName' id='groupName' value=""/>
								<input type="hidden" class="overlayInput "   name='oldGroupName' id='oldGroupName' value="" />
							</g:else>
							<div id="brandNameError" class="error"></div>
						</div>
						<div class="formInput">
							<label class="fl width32"><g:message code='messages.equipment.kast'/></label>
							<select class="overlaySelect" id="kast" name="kast" onchange="showOptions(this.value,'kast','oldKast');" >
								<g:each in="${kasts }" var="kast">
									<g:if test="${equipment.kast!=null && equipment.kast.kastName==kast.kastName }">
										<option value="${kast.kastName }" selected>${kast.kastName }</option>
									</g:if>
									<g:else>
										<option value="${kast.kastName }" >${kast.kastName }</option>
									</g:else>
								</g:each>
								<g:if test="${equipment.kast!=null }">
									<option value="Other">Other</option>
								</g:if>
								<g:else>
									<option value="Other" selected>Other</option>
								</g:else>
							</select>
							<g:if test="${equipment.kast!=null }">
								<input type="text" class="overlayInput "   name='kastName' id='kastName' value="${equipment.kast.kastName }" />
								<input type="hidden" class="overlayInput" name='oldKastName' id='oldKastName' value="${equipment.kast.kastName }" />
							</g:if>
							<g:else>
								<input type="text" class="overlayInput "   name='kastName' id='kastName' value=""/>
								<input type="hidden" class="overlayInput" name='oldKastName' id='oldKastName' value="" />
							</g:else>
							<div id="brandNameError" class="error"></div>
						</div>
						
						
						<!-- Equipment checkpoint editing starts here -->
						
						<div class="formInput">
							<label class="fl width32"><g:message code='messages.equipment.checkpoints'/></label>
						</div>
						<g:if test="${equipmentCheckpoints!=null && equipmentCheckpoints.size()>0 }">
						<g:each in="${equipmentCheckpoints }" var="equipmentCheckpoint">
							<div class="checkpointDiv">
						
								<label class="fl width60">${equipmentCheckpoint.checkpointDescription }: &nbsp; ${equipmentCheckpoint.comment}</label><label class="fl">${equipmentCheckpoint.status }</label>
								
							</div>
						
						</g:each>
						</g:if>
						<g:elseif test="${equipmentCheckpoints.size()<=0 }">
						<%--<g:if test="${reportCheckpoints.size()>0 }">
							<g:each in="${reportCheckpoints }" var="equipmentCheckpoint">
								<div class="formInput">
						
								<label class="fl width32">${equipmentCheckpoint.value }:</label>
								<select class=" selectTextCommon" name="equipment${equipment.id }checkpoint${equipmentCheckpoint.id }">
									<option value="Afgekeurd"  >
										<g:message code="messages.status.afgekeurd"/>
									</option>
									<option value="Goedgekeurd"  >
										<g:message code="messages.status.goedgekeurd"/>
									</option>
									<option value="Akkoord na herkeuring" >
										<g:message code="messages.status.Akkoordnaherkeuring"/>
									</option>
									<option value="NVT" >
										<g:message code="document.list.status.nvt"/>
									</option>
								</select>
								</div>
							</g:each>
						</g:if>
						
							--%><div class="formInput">
								<label class="fl width32"><g:message code="messages.checkpoints.not.created"/> </label>
							</div>
						
						</g:elseif>
						<div class="fl width300 editEquipmentSubmit">
							<div class="cancelCommonBg">
								<g:link controller="equipment" action="viewEquipment" id="${equipment.id }">
        							<span class="inputButton fl"><g:message code='users.cancel'/> </span>
        						</g:link>
        					</div> 
							<div class="submitCommonBg fr">
								<input type="submit" class="inputButton fl" value="<g:message code='users.submit'/>"/>
							</div>
						</div>
      				</g:form>
  	</div>
   </div>
   <script type="text/javascript">
function showOptions(value,id1,id2){
	if(value=='Other')
	{
		//$('#'+id+'Name').removeAttr('readonly');
		$('#'+id1+'Name').val('');
		$('#'+id2+'Name').val('');
	}
	else{
		$('#'+id1+'Name').val(value);
		$('#'+id2+'Name').val(value);
		//$('#'+id+'Name').attr('readonly','readonly');
	}
}

$(document).ready(function(){
	
	$('#editEquipments').find('select').each(function(){
		if($(this).val()=='Other'){
			$('#'+this.id+'Name').val('');
			//$('#'+this.id+'Name').removeAttr('readonly');
		}
		else{
			//$('#'+this.id+'Name').attr('readonly','readonly');
			$('#'+this.id+'Name').val($(this).val());
		}
	});	
})



 </script>
    
  </div>
</body>
</html>