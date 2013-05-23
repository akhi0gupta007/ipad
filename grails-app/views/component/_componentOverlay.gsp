<!--overlay for building Start Here-->
<div class="overlay" id="buildingOverlay">
	<div class="ovelayPostion">
   		<div class="overlayWidth formHeadingBg overlayTopMargin">
     		<h2 class="marginLeft"><g:message code="new.building" /><a href="#" class="closeButton activeEditBtn" onclick="closeOverlay('buildingOverlay')">Close</a></h2>
      				<g:formRemote id="createBuilding" name="createBuilding" url="[controller:'component',action:'saveBuilding']" beforeSend="checkComponentInput('buildingOverlay')" before="if(checkComponentInput('buildingOverlay')){" after="}"  onSuccess="addBuildings(data);">
      				<input type="hidden" id="parentProject" name="parentProject" />
      				<input type="hidden" id="oldBuildingId" name="oldBuildingId" />
      					<div class="formInput">
							<label class="fl overlayInputHeading width36"> <g:message code="component.building.name"/></label>
							<input type="text" class="overlayInput "   name='buildingName' id='buildingName' />
							<div id="buildingNameError" class="error"></div>
						</div>
						<div class="formInput">
							<label class="fl overlayInputHeading width36"><g:message code="component.building.number"/></label>
							<input type="text" class="overlayInput "   name='buildingNumber' id='buildingNumber' />
							<div id="buildingNumberError" class="error"></div>
						</div>
						<div class="formInput">
							<label class="fl overlayInputHeading width36"><g:message code="new.customer.address"/></label>
							<input type="text" class="overlayInput "   name='buildingAddress' id='buildingAddress' />
							<div id="buildingAddressError" class="error"></div>
						</div>
						<div class="formInput">
							<label class="fl overlayInputHeading width36"><g:message code="component.zip.code"/></label>
							<input type="text" class="overlayInput "   name='buildingZip' id='buildingZip' />
							<div id="buildingZipError" class="error"></div>
						</div>
						<div class="formInput">
							<label class="fl overlayInputHeading width36"><g:message code="new.customer.city"/></label>
							<input type="text" class="overlayInput "   name='buildingCity' id='buildingCity' onblur="city_Validate(this.value,'buildingCityError');" />
							<div id="buildingCityError" class="error"></div>
						</div>
						
  						<div class="formInput">
						<div class="fr width60">
							<div class="cancelCommonBg">
								<input type="button" class="inputButton fl" onclick="closeOverlay('buildingOverlay')" value="<g:message code='users.cancel'/>" />
							</div> 
							<div class="submitCommonBg fr">
								<input type="submit" class="inputButton fl" value="<g:message code='users.submit'/>"/>
							</div>
						</div>
						</div>
      				</g:formRemote>
         	</div>
       	</div>
  	</div>

 <!--overlay end Here-->
 

<!--overlay for floor Start Here-->
<div class="overlay" id="floorOverlay">
	<div class="ovelayPostion">
   		<div class="overlayWidth formHeadingBg overlayTopMargin">
     		<h2 class="marginLeft"><g:message code="new.floor" /><a href="#" class="closeButton activeEditBtn" onclick="closeOverlay('floorOverlay')">Close</a></h2>
      				<g:formRemote name="createFloor" url="[controller:'component',action:'saveFloor']" beforeSend="checkComponentInput('floorOverlay')" before="if(checkComponentInput('floorOverlay')){" after="}"  onSuccess="addFloors(data);">
      				    <input type="hidden" id="parentBuilding" name="parentBuilding"/>
      				    <input type="hidden" id="oldFloorId" name="oldFloorId" />
      					<div class="formInput">
							<label class="fl overlayInputHeading width36"><g:message code='component.floor.number'/>	 :</label>
							<input type="text" class="overlayInput "   name='floorNumber' id='floorNumber' />
							<div id="floorNumberError" class="error"></div>
						</div>
						<div class="formInput">
							<label class="fl overlayInputHeading width36"><g:message code='component.floor.description'/> :</label>
							<input type="text" class="overlayInput "   name='floorDescription' id='floorDescription' />
						</div>
						<div class="formInput">
							<label class="fl overlayInputHeading width36"><g:message code='component.upload.floormap'/> :</label>
							<iframe src="${createLink(controller:'document',action:'imageUploader') }?elementName=floorMap"  class=" overlayIframe fl" > </iframe>
							<input type="hidden"  id="floorMap" name="floorMap"/>
			
						</div>
						<div class="formInput">
							<label class="fl overlayInputHeading width36"><g:message code='component.revision.date'/> :</label>
							<input type="text" class="overlayInput" id="revisionDate" name="revisionDate">
						</div>
						<div class="fr width60">
							<div class="cancelCommonBg">
								<input type="button" class="inputButton fl" onclick="closeOverlay('floorOverlay')" value="<g:message code='users.cancel'/>" />
							</div> 
							<div class="submitCommonBg fr">
								<input type="submit" class="inputButton fl" value="<g:message code='users.submit'/>"/>
							</div>
						</div>
      				</g:formRemote>
         	</div>
       	</div>
  	</div>

 <!--overlay end Here-->
 
 
 <!--overlay for room Start Here-->
<div class="overlay" id="roomOverlay">
	<div class="ovelayPostion">
   		<div class="overlayWidth formHeadingBg overlayTopMargin">
     		<h2 class="marginLeft"><g:message code="new.room" /><a href="#" class="closeButton activeEditBtn" onclick="closeOverlay('roomOverlay')">Close</a></h2>
      				<g:formRemote name="createRoom" url="[controller:'component',action:'saveRoom']" beforeSend="checkComponentInput('roomOverlay')" before="if(checkComponentInput('roomOverlay')){" after="}"  onSuccess="addRooms(data);">
      				    <input type="hidden" id="parentFloor" name="parentFloor"/>
      				    <input type="hidden" id="oldRoomId" name="oldRoomId" />
      					<div class="formInput">
							<label class="fl overlayInputHeading width36"><g:message code='component.room.id'/> :</label>
							<input type="text" class="overlayInput "   name='roomId' id='roomId' />
							<div id="roomIdError" class="error"></div>
						</div>
						
						<div class="fr width60">
							<div class="cancelCommonBg">
								<input type="button" class="inputButton fl" onclick="closeOverlay('roomOverlay')" value="<g:message code='users.cancel'/>" />
							</div> 
							<div class="submitCommonBg fr">
								<input type="submit" class="inputButton fl" value="<g:message code='users.submit'/>"/>
							</div>
						</div>
      				</g:formRemote>
         	</div>
       	</div>
  	</div>

 <!--overlay end Here-->
 

 
 <!-- equipment overlay start from here-->

<div class="overlay" id="equipmentOverlay">
   <div class="ovelayEquipmentBox">
   		<div class="equipmentOverlay formHeadingBg overlayEquipmentOverlay">
     	<h2 class="marginLeft"><g:message code="new.equipment" /><a href="#" class="closeButton activeEditBtn" onclick="closeOverlay('equipmentOverlay');">Close</a></h2>
    		<div class="scrolling">
    			 <g:formRemote id="createEquipment" name="createRoom" url="[controller:'component',action:'saveEquipment']" beforeSend="checkComponentInput('equipmentOverlay')" before="if(checkComponentInput('equipmentOverlay')){" after="}"  onSuccess="addEquipments(data);">
        				<input type="hidden" id="parentEquipmentFloor" name="parentEquipmentFloor"/>        				
      				    <input type="hidden" id="oldEquipmentId" name="oldEquipmentId" />
      				    <div class="formInput">
							<label class="fl overlayInputHeading width32"><g:message code="messages.equipment.type"/></label>
							<div class="paddingTop marginRight fl">
 								<g:radio name="equipmentType" id="equipmentTypeInbuilt"  value="Inbuilt" checked="true"/><g:message code="messages.equipment.type.inbuilt"/>
 							</div>
 							<div class="paddingTop marginRight fl">
  								<g:radio name="equipmentType" id="equipmentTypeExternal" value="External" /><g:message code="messages.equipment.type.external"/>
  							</div>
  						</div>
  						
  	
  						<div class="formInput">
							<label class="fl overlayInputHeading width32"><g:message code="armatur.emergency.exit.type"/></label>
							<div class="paddingTop marginRight fl">
 								<g:radio name="equipmentType2" id="equipmentTypeIndication"  value="Indication" checked="true"/><g:message code="messages.equipment.type.indication"/>
 							</div>
 							<div class="paddingTop marginRight fl">
  								<g:radio name="equipmentType2" id="equipmentTypeLighting" value="Lighting" /><g:message code="messages.equipment.type.lighting"/>
  							</div>
  						</div>
  						
  						
  		
  						<div class="formInput">
							<label class="fl overlayInputHeading width32"><g:message code='component.equipment.name'/></label>
							<input type="text" class="overlayInput "   name='equipmentName' id='equipmentName' />
							<div id="equipmentNameError" class="error marginLeftOverlay"></div>
						</div>
  						<div class="formInput">
							<label class="fl overlayInputHeading width32"><g:message code='component.equipment.description'/></label>
							<input type="text" class="overlayInput "   name='equipmentDescription' id='equipmentDescription' />
						</div>
  						
      				    <div class="formInput">
							<label class="fl overlayInputHeading width32"><g:message code='component.brand'/></label>
							<select class="overlaySelect" id="brand" name="brand" onchange="showOptions(this.value,'brand','oldBrand');" ><option></option></select>
							<input type="text" class="overlayInput "   name='brandName' id='brandName' />
							<input type="hidden" class="overlayInput "   name='oldBrandName' id='oldBrandName' />
							<div id="brandNameError" class="error marginLeftOverlay"></div>
						</div>
						
						<div class="formInput">
							<label class="fl overlayInputHeading width32"><g:message code='component.armature'/></label>
							<select class="overlaySelect" id="armature"  name="armature" onchange="showOptions(this.value,'armature','oldArmature');"><option></option></select>
							<input type="text" class="overlayInput "   name='armatureName' id='armatureName' />
							<input type="hidden" class="overlayInput "   name='oldArmatureName' id='oldArmatureName' />
							<div id="armatureNameError" class="error marginLeftOverlay"></div>
						</div>
						<div class="formInput">
							<label class="fl overlayInputHeading width32"><g:message code='component.buildyear.armature'/></label>
							<input type="text" class="overlayInput monthPicker"   name='buildYearOfArmature' id='buildYearOfArmature' />
							<div id="buildYearOfArmatureError" class="error marginLeftOverlay"></div>
						</div>
						<div class="formInput">
							<label class="fl overlayInputHeading width32"><g:message code='component.emergency.unit.print'/></label>
							<select class="overlaySelect" id="emergencyUnitOfPrint" name="emergencyUnitOfPrint" onchange="showOptions(this.value,'emergencyUnitOfPrint','oldEmergencyUnitOfPrint');"> <option></option></select>
							<input type="text" class="overlayInput "   name='emergencyUnitOfPrintName' id='emergencyUnitOfPrintName' />
							<input type="hidden" class="overlayInput "   name='oldEmergencyUnitOfPrintName' id='oldEmergencyUnitOfPrintName' />
							<div id="emergencyUnitOfPrintNameError" class="error marginLeftOverlay"></div>
						</div>
						<div class="formInput">
							<label class="fl overlayInputHeading width32"><g:message code='component.buildyear.emergencyunit'/></label>
							<input type="text" class="overlayInput monthPicker"   name='buildYearOfEmergencyUnit' id='buildYearOfEmergencyUnit' />
							<div id="buildYearOfEmergencyUnitError" class="error marginLeftOverlay"></div>
						</div>
						<div class="formInput">
							<label class="fl overlayInputHeading width32"><g:message code='component.light'/></label>
							<select class="overlaySelect" id="light" name="light" onchange="showOptions(this.value,'light','oldLight');"><option></option></select>
							<input type="text" class="overlayInput "   name='lightName' id='lightName' />
							<input type="hidden" class="overlayInput "   name='oldLightName' id='oldLightName' />
							<div id="lightNameError" class="error marginLeftOverlay"></div>
						</div>
						<div class="formInput">
							<label class="fl overlayInputHeading width32"><g:message code='component.battery'/></label>
							<select class="overlaySelect" id="battery" name="battery" onchange="showOptions(this.value,'battery','oldBattery');"><option></option></select>
							<input type="text" class="overlayInput "   name='batteryName' id='batteryName' />
							<input type="hidden" class="overlayInput "   name='oldBatteryName' id='oldBatteryName' />
							<div id="batteryNameError" class="error marginLeftOverlay"></div>
						</div>
						<div class="formInput">
							<label class="fl overlayInputHeading width32"><g:message code='component.buildyear.battery'/></label>
							<input type="text" class="overlayInput monthPicker"   name='buildYearOfBattery' id='buildYearOfBattery' />
							<div id="buildYearOfBatteryError" class="error marginLeftOverlay"></div>
						</div>
						
						
						
						
						
						<!-- Group number and kast start -->
						<div class="formInput">
							<label class="fl overlayInputHeading width32"><g:message code='messages.equipment.group.number'/></label>
							<select class="overlaySelect" id="group" name="group" onchange="showOptions(this.value,'group','oldGroup');" ><option></option></select>
							<input type="text" class="overlayInput "   name='groupName' id='groupName' />
							<input type="hidden" class="overlayInput "   name='oldGroupName' id='oldGroupName' />
							<div id="groupNameError" class="error marginLeftOverlay"></div>
						</div>
						
						<div class="formInput">
							<label class="fl overlayInputHeading width32"><g:message code='messages.equipment.kast'/></label>
							<select class="overlaySelect" id="kast" name="kast" onchange="showOptions(this.value,'kast','oldKast');" ><option></option></select>
							<input type="text" class="overlayInput" name='kastName' id='kastName' />
							<input type="hidden" class="overlayInput" name='oldKastName' id='oldKastName' />
							<div id="kastNameError" class="error marginLeftOverlay"></div>
						</div>
						
						<!-- Group number and kast end -->
						
						
						
						
						<div class="scrollingSubmit">
							<div class="cancelCommonBg">
								<input type="button" class="inputButton fl" onclick="closeOverlay('equipmentOverlay')" value="<g:message code='users.cancel'/>" />
							</div> 
							<div class="submitCommonBg fr">
								<input type="submit" class="inputButton fl" value="<g:message code='users.submit'/>"/>
							</div>
						</div>
      				</g:formRemote>
      			</div>
         </div>
       </div>
  	</div>

 <!-- equipment overlay end here-->
 

 <!-- view floor plan overlay start from here-->
<div class="overlay" id="viewFloorPlanOverlay">
	<div class="overlayMain">
		<div class="fourSideBorder overlayTopMargin fl floorMapWidth">
			<div class="formHeadingBg marginBottom fullWidth ">
				<h2 class="marginLeft"><g:message code='component.floor.plan'/>
					<a href="#" class="closeButton activeEditBtn" onclick="closeOverlay('viewFloorPlanOverlay');">Close</a></h2>
					<div class="formInput textCenter">
						<img alt="" height="300px" width="600px" id="floorMapImage">
      				    <div id="FloorMapImages"></div>
					</div>
					<div class="fr width60">
						<div class="submitCommonBg fr">
							<input type="submit" class="inputButton fl" onclick="closeOverlay('viewFloorPlanOverlay')" value="OK"/>
						</div>
					</div>
         		</div>
       		</div>
  		</div>
	</div>
	
 <!-- view floor plan overlay end here-->
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
		$('#'+id1+'NameError').html('');
		//$('#'+id+'Name').attr('readonly','readonly');
	}
}

$(document).ready(function()
{   
    $(".monthPicker").datepicker({
        dateFormat: 'dd/mm/yy',
	});

	   
		});
 </script>
