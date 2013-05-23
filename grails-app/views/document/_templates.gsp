<g:each status="count" in="${documentTemplateList}" var="documentTemplate" >
	<div class="customerCompany thirdGridColor" id="${documentTemplate.id }">
   		<ul class="commonCss adminHead fullWidth list">
   		<g:if test="${loggedInUserRole=='ROLE_USER' }">
   			<li class="fl serialNo docList">${sno++ }</li>
   			<li class="fl width20 docList">${documentTemplate.name }</li>
  			<li class="fl width20 docList">${documentTemplate.createdBy }</li>
  			  <li class="fl width20 docList"> <g:formatDate format="dd/MM/yyyy" date="${documentTemplate.dateCreated }"/></li>
    		<li class="fl widthEighteen docList"> <g:formatDate format="dd/MM/yyyy" date="${documentTemplate.lastUpdated }"/></li>
   			<li class="fl enable  "><g:link controller="document" action="previewTemplate" id="${documentTemplate.id}" class="viewTemplate activeEditBtn fl" title="View ${documentTemplate.name}">View</g:link></li>
   			<!-- <li class="fl   delete"><g:link  controller="document" action="editTemplate" id="${documentTemplate.id}" class="editButton activeEditBtn fl" title="Edit ${documentTemplate.name}">Edit</g:link></li>-->
   			<li class="fl  role">
   				<g:link controller="document" action="documentInput"  id="${documentTemplate.id }" class="useIcon activeEditBtn fr " title="Use Template" >use</g:link>
   			</li>
   			<!-- <li class="fr deleteButton  delete"><g:remoteLink controller="document" action="deleteTemplate" id="${documentTemplate.id }" onSuccess="deleteTemplate(data,'${documentTemplate.id  }');" class="disableButton activeEditBtn fr marginRight" title="Delete ${documentTemplate.name}">delete</g:remoteLink></li>-->
   		</g:if>
   		<g:else test="${loggedInUserRole=='ROLE_ADMIN' }">
   			<li class="fl serialNo paddingTop">${sno++ }</li>
   			<li class="fl widthEighteen paddingTop">${documentTemplate.name }</li>
  			<li class="fl templateNameHead paddingTop">${documentTemplate.createdBy }</li>
    		<li class="fl userName paddingTop">${documentTemplate.dateCreated.dateString}</li>
     		<li class="fl userName paddingTop">${documentTemplate.lastUpdated.dateString }</li>
   			<li class="fl width12Half"><g:link controller="document" action="previewTemplate" id="${documentTemplate.id}" class="viewTemplate activeEditBtn fl" title="View ${documentTemplate.name}">View</g:link></li>
   			<li class="fl   width10"><g:link  controller="document" action="editTemplate" id="${documentTemplate.id}" class="editButton activeEditBtn fl" title="Edit ${documentTemplate.name}">Edit</g:link></li>
   			
   			<li class="fr deleteButton  delete"><g:remoteLink controller="document" action="deleteTemplate" id="${documentTemplate.id }" onSuccess="deleteTemplate(data,'${documentTemplate.id  }');" class="disableButton activeEditBtn fr marginRight" title="Delete ${documentTemplate.name}">delete</g:remoteLink></li>
   		
   		</g:else>
  	 	</ul>
   	</div>
</g:each>

 <!-- pagination start -->  
   	<div id="pagination">
   		<util:remotePaginate controller="document" action="templateList" id="paginate" total="${totalSize }" update="Templates"  max="10" />
   	</div>
<!-- pagination end -->  