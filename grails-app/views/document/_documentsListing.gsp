<!-- project data came from here --> 

	<g:each in="${docList }" var="document">
	<g:if test="${!document.isDeleted }" >
  		<div class="fullWidth  thirdGridColor" id="${document.id }">
    		<ul class="adminHead fullWidth list ">
        		<li class="width10 docList fl">${document.project.projectName }</li>
            	<li  class="userName docList  fl"><g:link controller="document" action="viewDocument" id="${document.id }">${document.name }</g:link></li>
            	<li  class="userName docList  fl">${document.documentNumber }</li>
            	<li class="width10 docList fl">${document.discipline }</li>
            	
            	<li class="width12Half textCenter  fl ">
            	<g:if test="${document.status=='Not Ready' }">
            		<span class="statusIcon notreadyIcon"><g:message code="document.list.status.notready" /></span>
            	</g:if>
            	<g:elseif test="${document.status=='Afgekeurd' }">
            		<span class="statusIcon notreadyIcon"><g:message code="messages.status.afgekeurd" /></span>
            	</g:elseif>
            	<g:elseif test="${document.status=='Goedgekeurd' }">
            		<span class="statusIcon nvtIcon"><g:message code="messages.status.goedgekeurd" /></span>
            	</g:elseif>
            	<g:elseif test="${document.status=='Akkoord na herkeuring' }">
            		<span class="statusIcon intreatmentIcon"><g:message code="messages.status.Akkoordnaherkeuring" /></span>
            	</g:elseif>
            	<g:elseif test="${document.status=='NVT' }">
            		<span class="statusIcon readyIcon "><g:message code="document.list.status.nvt" /></span>
            	</g:elseif>
            	<g:else>
            		<span class="statusIcon notreadyIcon"><g:message code="document.list.status.notready" /></span>
            	</g:else>
            	</li>
            	<li class="userName docList fl">
            	<g:formatDate format="dd/MM/yyyy" date="${document.lastUpdated}"/></li>
           		<li class="width10 docList fl"><g:formatDate format="dd/MM/yyyy" date="${document.deadLine }"/></li> 
           		<li class=" width9Half textCenter fl ">
           	 	<g:remoteLink controller="document" action="deleteDocument" id="${document.id }" beforeSend="comfirmDocumentDelete('${document.name }')" before="if(comfirmDocumentDelete('${document.name }')){" after="}" onSuccess="deleteDocument(data,'${document.id  }');" class="marginLeft disableButton activeEditBtn fl"><g:message code="list.synchronize" /></g:remoteLink>
           		</li> 
      		</ul>
     	</div>
     </g:if>
     </g:each>
 
<!-- project data end  here -->           