<!doctype html>
<%@page import="com.kam.User"%>
<%@page import="com.kam.UserRole"%>


<html>
<head>
<meta name='layout' content='main' />
<title><g:message code="title.message"></g:message></title>

</head>

<body>
<g:render template="/layouts/navigation" model="[loggedInUserRole:role,loggedinUser:loggedinUser,page:'equipmentList']"></g:render>
<ul class="backUl">
	<li class="fr paddingRight"><a href="javascript:history.go(-1)" class="back"><span><g:message code="login.customer.back"/></span></a></li>
 </ul>
<div class="customerList">
<div id="main_box"  class="fl"><!--main_box-->
       <g:render template="/layouts/userNavigation" model="[projectList:projectList,flow:loggedinUser.customer.flow]"></g:render>
    
     <div id="rhtBoxtext" ><!--start of rht_boxtext-->
     	<h1 class="userHeading paddingTop"><g:message code="component.equipment"/> : ${equipment.name }</h1>
     	<div class="fullWidth  gridColor"> <!--start of Status_navi-->
          <ul class="adminHead fullWidth gridColor">
            <li class="width25percent viewDocPadding  liDivider"><g:message code="list.project"/> :${equipment.floor.building.project.projectName }</li>
            <li  class=" userName viewDocPadding   liDivider"><g:message code="view.document.building"/> :${equipment.floor.building.buildingNumber }</li>
            <li  class="userName viewDocPadding   liDivider"><g:message code="view.document.floor"/> :${equipment.floor.floorNumber }</li>
            
          </ul>
          </div>
          <div id="equipmentsList">
        <div class="fullWidth thirdGridColor">
 
          <ul class="adminHead fullWidth">
          <li  class="width32 viewDocPadding fl"><g:message code="component.equipment.name"/> :${equipment.name }</li>
            <li class="width32 viewDocPadding fl"><g:message code="component.brand"/> :<g:if test="${equipment.brand !=null }">${equipment.brand.brandName }</g:if></li>
            <li  class="width32 viewDocPadding fl"><g:message code="component.battery"/> :<g:if test="${equipment.battery !=null }">${equipment.battery.batteryType }</g:if></li>
           
          </ul>
    		<ul class="adminHead fullWidth">
    		 <li  class="width32 viewDocPadding fl"><g:message code="component.armature"/> :<g:if test="${equipment.armatuur !=null }">${equipment.armatuur.armatuurType }</g:if></li>
            <li class="width32 viewDocPadding fl"><g:message code="component.light"/> :<g:if test="${equipment.light !=null }">${equipment.light.name}</g:if></li>
            <li  class="width32 viewDocPadding fl"><g:message code="component.emergency.unit.print"/> :<g:if test="${equipment.emergencyUnitOfPrint !=null }">${equipment.emergencyUnitOfPrint.unitName  }</g:if></li>
            
          </ul>
          <ul class="adminHead fullWidth">
          	<li  class="width32 viewDocPadding fl"><g:message code="component.buildyear.armature"/> :${equipment.buildYearOfArmature }</li>
            <li class="width32 viewDocPadding fl"><g:message code="component.buildyear.battery"/> :${equipment.buildYearOfBattery }</li>
            <li  class="width32 viewDocPadding fl"><g:message code="component.buildyear.emergencyunit"/> :${equipment.buildYearOfEmergencyUnit  }</li>
            
          </ul>
          <ul class="adminHead fullWidth">
          	<li  class="width32 viewDocPadding fl"><g:message code="view.document.created"/> :<g:formatDate format="dd/MM/yyyy" date="${equipment.dateCreated}"/></li>
            <li class="width32 viewDocPadding fl"><g:message code="view.document.last.updated"/> :<g:formatDate format="dd/MM/yyyy" date="${equipment.lastUpdated}"/></li>
            <li  class="width32 viewDocPadding fl"><g:message code="users.user.status"/> :${equipment.status  }</li>
            
          </ul>
          
         
         
         <ul class="adminHead fullWidth gridColor">
              <li class="width32 viewDocPadding fl"><g:message code="messages.equipment.checkpoints"/></li>
          </ul>
          <ul class="adminHead fullWidth">
          	<g:if test="${equipmentCheckpoints!=null && equipmentCheckpoints.size()>0 }">
          		<g:each in="${equipmentCheckpoints }" var="equipmentCheckpoint">
          			
            			<li class="fullWidth viewDocPadding fl"><div class="fl company">${equipmentCheckpoint.checkpointDescription } :    &nbsp; &nbsp;  ${equipmentCheckpoint.comment} </div><div class="fr marginRight">${equipmentCheckpoint.status }</div></li>
            		
            	</g:each>
            </g:if>
            <g:elseif test="${equipmentCheckpoints.size()<=0 }">
            	<g:if test="${reportCheckpoints.size()>0 }">
            		<g:each in="${reportCheckpoints }" var="reportCheckpoint">
            			<li class="fullWidth viewDocPadding fl">${reportCheckpoint.value } <div class="fr marginRight"><g:message code="document.list.status.notready"/></div></li>
            		</g:each>
            	</g:if>
            	<g:else>
            		<li class="fullWidth viewDocPadding fl"><g:message code="messages.checkpoints.not.created"/> </li>
            	</g:else>
            </g:elseif>
         </ul>
            
        </div>
        <div class="fullWidth  gridColor"> 
        <!--start of Status_navi-->
         <ul class="adminHead fullWidth gridColor boderTopBottom fl">
         	<li  class="enable paddingTop fl marginLeft"><g:message code="template.view"/> :</li>
          	<li class="fl  "><g:link controller="equipment" action="equipmentOverview" id="${equipment.id }" class="viewTemplate activeEditBtn fl" ><g:message code="users.user.edit"/></g:link></li>
          	
          	<li  class=" paddingTop fl marginLeft"><g:message code="users.user.edit"/> :</li>
          	<li class=" fl "><g:link controller="equipment" action="editEquipment" id="${equipment.id }"  class="editButton activeEditBtn fl" ><g:message code="users.user.edit"/></g:link></li>
         </ul>
          <ul class="adminHead fullWidth gridColor boderTopBottom fl">
          	<li  class="marginLeft width18 paddingTop fl"><g:message code="view.document.image"/> :</li>
          	<g:if test="${equipment.status.equals("Afgekeurd")}">
          	<g:if test="${equipment.checkpoints!=null && equipment.checkpoints.size()>0 }">
          		<g:each in="${equipment.checkpoints}" var="equipmentCheckpoint">
          				<g:each in="${equipmentCheckpoint.equipmentImages }" var="equipmentImage">
            				<li class="fl marginRight"><img src="${resource(dir:'ReportImages',file:equipmentImage.imageName) }"  class="activeEditBtn "/></li>
            			</g:each>
            	</g:each>
            </g:if>
            </g:if>
            <g:if test="${equipment.status=="Goedgekeurd" || equipment.status=="NVT"||equipment.status=="Akkoord na herkeuring"}">
            <g:if test="${equipmentCheckpoints!=null && equipmentCheckpoints.size()>0 }">
          		<g:each in="${equipmentCheckpoints }" var="equipmentCheckpoint">
          			<g:if test="${reportCheckpoints.value.contains(equipmentCheckpoint.checkpointDescription) }">
          				<g:each in="${equipmentCheckpoint.equipmentImages }" var="equipmentImage">
            				<li class="fl marginRight"><img src="${resource(dir:'ReportImages',file:equipmentImage.imageName) }"  class="activeEditBtn "/></li>
            			</g:each>
            		</g:if>
            	</g:each>
            </g:if>
            </g:if>
        </ul>
          
          </div>
       </div>
         </div> 
          
        </div>
       
   
      
    </div>
 
<script src="${resource(dir:'js',file:'easypaginate.js') }"></script>
<script type='text/javascript'>
	$(document).ready(function(){
	     $('#project').click(function() {
	      $('#projectDropdown').slideToggle();
	       });

	});
		</script>
</body>


</html>