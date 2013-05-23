<%@page import="com.kam.StringArraySort"%>
<%@page import="com.kam.*" %>
<g:set var="sn" value="${sno }"></g:set>

<g:each status="count" in="${projectsList }" var="project">
<div id="ProjectsListing${project.id }" class="projectsListing">
 		<!-- project header content start -->
 	<g:if test="${project.isDeleted==false }">
   			<div class="customerCompany <g:if test="${count%2==0 }" >secondGridColor</g:if><g:else>thirdGridColor</g:else> " id="projectNumber${project.id }">
    			<ul class="fullWidth">
    				<li class="fl serialNo  paddingTop">${++sn }</li>
     				<li class=" paddingTop fl width60"><p><g:remoteLink url="[controller:'component',action:'editProject']" params="[id:project.id]"  onSuccess="loadProjectValues(data);" class="fontColor">${project.projectName }</g:remoteLink></p></li>
     				<li class="widthEighteen fl"><a href="#" class="plusIcon activeEditBtn fl" id="addField" onclick="addComponents('addMore${project.id }');"><g:message code="add.label" /></a></li>
     				<li class="role fl"><g:remoteLink url="[controller:'component',action:'editProject']" params="[id:project.id]"  onSuccess="loadProjectValues(data);" class="fontColor editButton activeEditBtn"><g:message code="users.user.edit" /></g:remoteLink></li>
     				<li class="delete fr"><g:remoteLink url="[controller:'component',action:'deleteComponent']" params="[id:project.id,type:'Project']" class="disableButton activeEditBtn fr marginRight" beforeSend="confirmComponentDelete('${project.id }','Project')" before="if(confirmComponentDelete('${project.id }','Project')){" after="}" onSuccess="deleteComponent(data);"><g:message code="account.delete" /></g:remoteLink></li>
     			</ul>
     		</div>
     		<!-- project header content end -->	
     		
     		<div class="customerCompany displayNone" id="addMore${project.id }">
     			<h2 class="fl fontColor projectName"><g:message code="project.name"/> : ${project.projectName }</h2>
				
					<div class="fullWidth gridColor">
    					<ul class="adminHead fullWidth">
     						<li class="liWidth  fl borderRight"><g:message code="view.document.building"/> </li>
     						<li class="liWidth  fl borderRight"><g:message code="view.document.floor"/> </li>
     						<li class="liWidth  fl">
     						<g:if test="${loggedinUser.customer.flow=='document' }"><g:message code="view.document.room"/></g:if><g:elseif test="${loggedinUser.customer.flow=='equipment' }"> <g:message code="component.equipment" /></g:elseif></li>
     					</ul>
     				</div>
     				
     				<div class="fourSideBorder secondGridColor fl marginBottom">
     					<g:each in="${project.buildings.sort{ it.id } }" var="building">
     					<g:if test="${building.isDeleted==false }">
     						<div class="fullWidth borderBottom fl" id="buildingNumber${building.id }">
     							<div class="liWidth  fl buildings">
     								<ul>
              							<li class="fl paddingRight widthHalf">
              								<g:remoteLink url="[controller:'component',action:'editBuilding']" params="[id:building.id]"  onSuccess="loadBuildingValues(data);" class="fontColor">${building.buildingName }</g:remoteLink>
              							</li>
              							<li class="hidden shortDelete fr" onclick="deleteRequest('${building.id}','Building')"><a >Delete</a></li>
              							<g:if test="${loggedinUser.customer.flow=='equipment' }">
              								<li class=" fl"><g:link controller="reports" action="reportList" id="${building.id }" ><g:message code="messages.component.viewreports" /></g:link></li>
              							</g:if>
       								</ul> 
     							</div>	
     							<div class=" fl innerDiv">
     							
     							<g:each in="${building.floors.sort{ it.id } }" var="floor">
     								<g:if test="${floor.isDeleted==false }">
     									<div class=" fl fullWidth borderBottom" id="floorNumber${floor.id }">
     										<div class=" fl liWidth floors">
     											<ul>
                									<li class="fl paddingRight widthHalf">
                										<g:remoteLink url="[controller:'component',action:'editFloor']" params="[id:floor.id]"  onSuccess="loadFloorValues(data);" class="fontColor">${floor.floorNumber }</g:remoteLink>
                									</li>
                									<li class="hidden shortDelete fr" onclick="deleteRequest('${floor.id}','Floor')"><a href="#" >Delete</a></li>
                									<li class=" fl" onclick="showFloorMapImage('${floor.id}')"><a href="#" ><g:message code="messages.component.viewfloorplan" /></a></li>
       											</ul> 
     										</div>
     										<div class=" fl liWidth borderLeft" >
     											<g:if test="${loggedinUser.customer.flow=='document' }">
     												
     											
     													<g:each in="${floor.rooms.sort{ it.id } }" var="room">
     														<g:if test="${room.isDeleted==false }">
     														<ul id="roomNumber${room.id }" class="fullWidth marginBottom rooms">
                												<li class="fl paddingRight">
     																<span class="fullWidth" ><g:remoteLink url="[controller:'component',action:'editRoom']" params="[id:room.id]"  onSuccess="loadRoomValues(data);" class="fontColor">${room.roomId }</g:remoteLink></span>
     															</li>
     															<li class="hidden shortDelete fr"  onclick="deleteRequest('${room.id}','Room')"><a>Delete</a></li>
       														</ul> 
       														</g:if>
     													</g:each>
     													<div id="addRoom${floor.id }"></div>
     												
     											
     												<div class="cancelCommonBg AddCommonBg">
														<input type="button" class="inputButton fl" onclick="openOverlay('roomOverlay','${floor.id }','parentFloor')" value='<g:message code="view.document.room" />' />
     												</div>
     											</g:if>
     											<g:else test="${loggedinUser.customer.flow=='equipment' }">
     												<% def equipmentListNames = [] %>
     												<%def equipmentscalculated = Equipment.findAllWhere(floor:floor,isDeleted:false) %>
     													<% for(equip in equipmentscalculated){equipmentListNames.add(equip.name)} %>
     											<%Collections.sort(equipmentListNames,new StringArraySort<String>()) %>
     											<%
												 def sortedList = []
												 for(int i=0;i<equipmentListNames.size();i++){
													 def equipment = Equipment.findWhere(name:equipmentListNames[i],floor:floor,isDeleted:false)
													 sortedList.add(equipment)
														 }
												  %>
     													<g:each in="${sortedList}" var="equipment">
     													<g:if test="${equipment.isDeleted==false }">
     														<ul id="equipmentNumber${equipment.id }"  class="fullWidth marginBottom equipments">
                												<li class="fl paddingRight">
     																<span class="fullWidth" ><g:remoteLink url="[controller:'component',action:'editEquipment']" params="[id:equipment.id]"  onSuccess="loadEquipmentValues(data);" class="fontColor">${equipment.name }</g:remoteLink></span>
     															</li>
     															<li class="hidden shortDelete fr"  onclick="deleteRequest('${equipment.id}','Equipment')"><a>Delete</a></li>
       														</ul> 
       														</g:if>
     													</g:each>
     													<div id="addEquipment${floor.id }"></div>
     												
     											
     												<div class="cancelCommonBg AddCommonBg">
														<input type="button" class="inputButton fl" onclick="openOverlay('equipmentOverlay','${floor.id }','parentEquipmentFloor')" value='<g:message code="component.equipment" />' />
     												</div>
     											</g:else>
     										</div>
     									</div>
     								</g:if>
     							</g:each>
     							
     							
     								<div id="addFloor${building.id }"></div>
     								<div class=" fl liWidth  borderRight">
     									<div class="cancelCommonBg  AddCommonBg">
     										<input type="button" class="inputButton fl" value='<g:message code="view.document.floor" />' onclick="openOverlay('floorOverlay','${building.id }','parentBuilding')" />
     									</div>
     								</div>
     							</div>
     						</div>
     						</g:if>
     					</g:each>
     					
     					
     					<div id="addBuilding${project.id }">
     					</div>
     					<div class="liWidth liPadding fl borderRight">
     						<div class="cancelCommonBg  AddCommonBg">
								<input type="button" class="inputButton fl" value='<g:message code="view.document.building" />' onclick="openOverlay('buildingOverlay','${project.id}','parentProject')" />
							</div>
     					</div>
     				</div>
     			</div>
		
		</g:if>
		</div>
	</g:each>
			
