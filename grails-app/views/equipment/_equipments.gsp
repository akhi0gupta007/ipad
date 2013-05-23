<!-- project data came from here --> 

	<g:each in="${equipmentsList }" var="equipment">
	<g:if test="${!equipment.isDeleted }" >
  		<div class="fullWidth  thirdGridColor" id="equipmentNumber${equipment.id }">
    		<ul class="adminHead fullWidth list ">
        		<li class="width10 docList fl"><g:link controller="equipment" action="viewEquipment" id="${equipment.id}"> ${equipment.name }</g:link></li>
            	<li  class="userName docList  fl">${equipment.floor.building.project.projectName }</li>
            	<li  class="userName docList  fl">${equipment.floor.building.buildingName }</li>
            	<li class="width10 docList fl" title="${equipment.floor.floorComment}">${equipment.floor.floorNumber}</li>
            	<li class="width12Half textCenter  fl ">
            	<g:if test="${equipment.status=='Not Ready' }">
            		<span class="statusIcon notreadyIcon"><g:message code="document.list.status.notready" /></span>
            	</g:if>
            	<g:elseif test="${equipment.status=='Afgekeurd' }">
            		<span class="statusIcon notreadyIcon"><g:message code="messages.status.afgekeurd" /></span>
            	</g:elseif>
            	<g:elseif test="${equipment.status=='Goedgekeurd' }">
            		<span class="statusIcon nvtIcon"><g:message code="messages.status.goedgekeurd" /></span>
            	</g:elseif>
            	<g:elseif test="${equipment.status=='Akkoord na herkeuring' }">
            		<span class="statusIcon intreatmentIcon"><g:message code="messages.status.Akkoordnaherkeuring" /></span>
            	</g:elseif>
            	<g:elseif test="${equipment.status=='NVT' }">
            		<span class="statusIcon readyIcon "><g:message code="document.list.status.nvt" /></span>
            	</g:elseif>
            	<g:else>
            		<span class="statusIcon notreadyIcon"><g:message code="document.list.status.notready" /></span>
            	</g:else>
            	</li>
            	<li class="userName docList fl">
            	<g:formatDate format="dd/MM/yyyy" date="${equipment.lastUpdated}"/></li>
           		<li class="width10 docList fl"><g:formatDate format="dd/MM/yyyy" date="${equipment.deadLine}"/></li> 
           		<li class=" width9Half textCenter fl ">
           	 	<a href="#" onclick="deleteRequest('${equipment.id }','Equipment','${equipment.name }')" class="marginLeft disableButton activeEditBtn fl"><g:message code="list.synchronize" /></a>
           		</li> 
      		</ul>
     	</div>
     </g:if>
     </g:each>
   
<!-- pagination start -->  
   	
<!-- pagination end --> 
<!-- project data end  here -->           