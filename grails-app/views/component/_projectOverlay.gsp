<!--overlay Start Here-->
<div class="overlay" id="projectOverlay" onload="" >
	<div class="ovelayPostion">
   		<div class="overlayWidth formHeadingBg overlayTopMargin">
    			<h2 class="marginLeft"><g:message code="new.project"/><a href="#" class="closeButton activeEditBtn" onclick="closeOverlay('projectOverlay')">Close</a></h2>
      			     <g:formRemote name="createProject"  url="[controller:'component',action:'saveProject']" beforeSend="checkComponentInput('projectOverlay')" before="if(checkComponentInput('projectOverlay')){" after="}" onSuccess="addProjects(data)">
      			     <div id="createProject">
      			     <input type="hidden" id="oldProjectId" name="oldProjectId" />
      			     
      					<div class="formInput">
							<label class="fl overlayInputHeading width36"><g:message code="project.number"/> :</label>
							<input type="text" class="overlayInput "  name='projectNumber' id='projectNumber' />
							<div id="projectNumberError" class="error"></div>
						</div>
						<div class="formInput">
							<label class="fl overlayInputHeading width36"><g:message code="project.name"/> :</label>
							<input type="text" class="overlayInput "   name='projectName' id='projectName' />
							<div id="projectNameError" class="error"></div>
						</div>
						<div class="formInput">
							<label class="fl overlayInputHeading width36"><g:message code="project.title"/> :</label>
							<input type="text" class="overlayInput "   name='projectTitle' id='projectTitle' />
							<div id="projectTitleError" class="error"></div>
						</div>
						<div class="formInput">
							<label class="fl overlayInputHeading width36"><g:message code="new.customer.address"/> :</label>
							<input type="text" class="overlayInput "   name='projectAddress' id='projectAddress' />
							<div id="projectAddressError" class="error"></div>
						</div>
						<div class="formInput">
							<label class="fl overlayInputHeading width36"><g:message code="new.customer.city"/> :</label>
							<input type="text" class="overlayInput " onblur="city_Validate(this.value,'projectCityError');"  name='projectCity' id='projectCity' />
							<div id="projectCityError" class="error"></div>
						</div>
						<div class="formInput">
							<label class="fl overlayInputHeading width36"><g:message code="project.doc.name"/> :</label>
							<input type="text" class="overlayInput "   name='projectDocnaam' id='projectDocnaam' />
							<div id="projectDocnaamError" class="error"></div>
						</div>
						
						<div class="fr width60">
							<div class="cancelCommonBg">
								<input type="button" class="inputButton fl" onclick="closeOverlay('projectOverlay')" value="<g:message code='users.cancel'/>" />
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
<script type="text/javascript">

function checkComponentInput(id){
	var error=0;
	var emptyInputId = ['projectTitle','projectDocnaam', 'buildingZip','floorDescription','floorMap','revisionDate','equipmentDescription','buildYearOfBattery','buildYearOfArmature','buildYearOfEmergencyUnit']
	$('#'+id).find('input[type=text]').each(function(){
		if(this.value=='' && jQuery.inArray(this.id, emptyInputId)==-1){
			$('#'+this.id+'Error').html('<g:message code="messages.requiredInput" />');
			error=1;
		}
		if(jQuery.inArray(this.id, emptyInputId)==-1 && $('#'+this.id+'Error').html()!='')
			error=1;
		
		$(this).focus(function(){
			   $('#'+this.id+'Error').html('');
			   });
			 
			  $(this).blur(function(){
			   if(this.value=='' && jQuery.inArray(this.id, emptyInputId)==-1){
			   $('#'+this.id+'Error').html('<g:message code="messages.requiredInput" />');
			   }
			   });
	});
	if(error==1)
		return false;
	else
		return true;
}

function validNumber(value,id){
	if(value==''){
		$('#'+id+'Error').html('');
		return true;
	}
	var regNumber = /^[0-9]+$/;
	if(!regNumber.test(value)){
		$('#'+id+'Error').html('<g:message code="messages.numberValid" />');
		return false;
	}
	else
		$('#'+id+'Error').html('');
	return true;
}

function addProjects(data){
		if(data.oldProjectId=='' || data.oldProjectId==null){
			var projectCount = $('#projectsList').find('div.projectsListing').size();
			var gridClass='';
			if(projectCount%2==0)
				gridClass='secondGridColor';
			else
				gridClass='thirdGridColor';
			if(projectCount==0)
				$('#projectsList').html('');
			$('#projectsList').prepend('<div id="ProjectsListing'+data.currentId.id+'"  class="projectsListing"><div class="customerCompany '+gridClass+' " id="projectNumber'+data.currentId.id+'"><ul class="fullWidth"><li class="fl serialNo  paddingTop">1</li><li class=" paddingTop fl width60"><p><a href="#" onclick="editComponent(\''+data.currentId.id+'\',\'project\')"  class="fontColor " >'+data.currentId.projectName+'</a></p></li><li class="widthEighteen fl"><a href="#" class="plusIcon activeEditBtn fl" id="addField" onclick="addComponents(\'addMore'+data.currentId.id+'\');"><g:message code="add.label" /></a></li><li class="role fl"><a href="#" onclick="editComponent(\''+data.currentId.id+'\',\'project\')"  class="fontColor editButton activeEditBtn" ><g:message code="users.user.edit" /></a></li><li class="delete fr"><a href="#" onclick="deleteRequest(\''+data.currentId.id+'\',\'Project\')" class="disableButton activeEditBtn fr marginRight"><g:message code="account.delete" /></a></li></ul></div>		     		<div class="customerCompany displayNone" id="addMore'+data.currentId.id+'"><h2 class="fl fontColor projectName"><g:message code="project.name"/> : '+data.currentId.projectName+'</h2><div class="fullWidth gridColor"><ul class="adminHead fullWidth"><li class="liWidth  fl borderRight"><g:message code="view.document.building"/> </li><li class="liWidth  fl borderRight"><g:message code="view.document.floor"/> </li><li class="liWidth  fl"><g:message code="view.document.room"/>/ <g:message code="component.equipment" /></li></ul></div><div class="fourSideBorder secondGridColor fl marginBottom"><div id="addBuilding'+data.currentId.id+'"></div><div class="liWidth liPadding fl borderRight"><div class="cancelCommonBg  AddCommonBg"><input type="button" class="inputButton fl" value=\'<g:message code="view.document.building" />\' onclick="openOverlay(\'buildingOverlay\',\''+data.currentId.id+'\',\'parentProject\')" /></div></div></div></div></div>');
			projectCount=1;
			$('#projectsList').find('div.customerCompany > ul > li.serialNo ').each(function(){
				$(this).html(projectCount);
				projectCount=projectCount+1;
				});
			try{
			$('#projectsList').easyPaginate({
			  });
			}
			catch(ex){}
		}
		else
			$('#projectNumber'+data.currentId.id).find('li.paddingTop > p > a.fontColor').html($('#projectName').val());
		closeOverlay('projectOverlay');
}

</script>