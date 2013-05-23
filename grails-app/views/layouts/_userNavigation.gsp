<!--navigation-->	
	<div id="navigation"  class="fl">
    	<h1 class="userHeading paddingTop"><g:message code="view.document.search" /></h1>
      	<ul>
        	<li class="project" id=""><a href="#"><g:message code="list.project" /></a>
        		<ul id="projectDropdown">
        			<g:each in="${projectList }" var="project">
        				<li><a href="#" onclick="updateSelect('project','${project.id }','${project.projectName }');">${project.projectName }, ${project.address }, ${project.city }</a></li>
       				</g:each>
        		</ul>
        	</li>
         	<li class="project" id="Buildings"><a href="#" ><g:message code="view.document.building" /></a><ul id="buildingsDropdown"></ul></li>
       	 	<li class="project" id="Floor"><a href="#" ><g:message code="view.document.floor" /></a><ul id="floorsDropdown"></ul></li>
       	 	
       	 	<g:if test="${flow=='document' }">
         		<li class="project" id="Room"><a href="#" ><g:message code="view.document.room" /></a><ul id="roomsDropdown"></ul></li>
         		<li class="project" id="Discipline"><a href="#"><g:message code="view.document.discipline" /></a><ul id="disciplinesDropdown"></ul></li>
         	</g:if>
      </ul>
   </div>
<!--end of navigation-->
    
<script type='text/javascript'>
	$(document).ready(function(){
		 $('.project').click(function() {
	      	$(this).find('ul').slideToggle();
	     });
	});

	var projectSelected='';
	function updateSelect(type,project,projectName){ 
		var url='';
		if(${flow=='document'})
			url="${createLink(controller:'document',action:'populateSelect')}";
		else if(${flow=='equipment'})
			url="${createLink(controller:'equipment',action:'populateSelect')}";
			
		var value='',id;
	    jQuery.ajax({
	         type: 'POST',
	         url: url,
	         data:'type='+type+'&value='+project,
	         success: function(response,textStatus){
		         if(projectName!='')
		        	 projectSelected=projectName;
	        	 $('#'+response.update).html('');
	        	 if(type1='room' && type!='discipline')
		         $('#disciplinesDropdown').html('');
		        $.each(response.list, function() {
			        if(!this.isDeleted){
		        	  if(response.next=='building'){
				         value=this.buildingName;
				         id=this.id;
		        	  }
				      else if(response.next=='floor'){
				         value=this.floorNumber;
				         id=this.id;
				      }
				      else if(response.next=='room'){
					     value=this.roomId;
					     id=this.id;
				      }
					  else if(response.next=='discipline'){
						 value=this;
						 id=this;
					  }
		        	  
		        	  $('#'+response.update).append('<li><a href="#" onclick="updateSelect(\''+response.next+'\',\''+id+'\',\'\');">'+value+'</a></li>');
			        }
		        	});
		        	if(${flow=='document'}){
		        		ShowDocumentslisting(response,projectSelected);
	          		}
		        	else if(${flow=='equipment'}){
		        		ShowEquipmentslisting(response,projectSelected);
	          		} 
	         },
	         error:function(XMLHttpRequest,textStatus,errorThrown){ }
	     });
	 }


	function ShowDocumentslisting(response){
		$('#documentsList').html('');
        var documentsCount=0;
        var link="${createLink(controller:'document',action:'viewDocument')}";
        var status='';
        $.each(response.newList, function() {
	        if(!this.isDeleted){
	        	var myDate=formatDate(this.lastUpdated);
	        	if(this.status=='Not Ready')
					 status='<span class="statusIcon notreadyIcon"><g:message code="document.list.status.notready" /></span>';
				else if(this.status=='Afgekeurd')
					 status='<span class="statusIcon notreadyIcon"><g:message code="messages.status.afgekeurd" /></span>';
				else if(this.status=='Goedgekeurd')
					 status='<span class="statusIcon nvtIcon"><g:message code="messages.status.goedgekeurd" /></span>';
				else if(this.status=='Akkoord na herkeuring')
					 status='<span class="statusIcon intreatmentIcon"><g:message code="messages.status.Akkoordnaherkeuring" /></span>';
				else if(this.status=='NVT')
					 status='<span class="statusIcon readyIcon"><g:message code="document.list.status.nvt" /></span>';
				documentsCount=1;
				var deadLine = '';
				if(this.deadLine!='null' && this.deadLine!=null)
					deadLine=formatDate(this.deadLine)
        	  	$('#documentsList').append('<div class="fullWidth  thirdGridColor" id="'+this.id+'"><ul class="adminHead list "><li class="width10  docList  fl">'+projectSelected+'</li><li  class="userName docList  fl"><a href="'+link+'/'+this.id+'" > '+this.name +'</a></li><li  class="userName docList  fl">'+this.documentNumber+'</li><li class="width10  docList  fl">'+this.discipline+'</li><li class="width12Half textCenter  fl ">'+status+'</li><li class="userName docList fl">'+myDate +'</li><li class="width10 docList fl">'+deadLine+'</li><li class=" width9Half textCenter fl "><a href="#" onclick="documentDelete(\''+this.id+'\',\''+this.name+'\')" class="synchronize disableButton activeEditBtn fl">synchronize</a></li></ul></div');
	        }
        	});
    	
        	$('#pagination').remove();
    	 	$('#documentsList').easyPaginate({
   		  	});
    	if(documentsCount==0)
			 $('#documentsList').append('<div class="fullWidth paddingTop  thirdGridColor"><g:message code="documents.not.available"/></div>');
    	
	}
	
	function ShowEquipmentslisting(response){
		$('#equipmentsList').html('');
        var documentsCount=0;
        var link="${createLink(controller:'equipment',action:'viewEquipment')}";
        var status='<span class="statusIcon notreadyIcon"><g:message code="document.list.status.notready" /></span>';
        
        $.each(response.newList, function() {
	        if(!this.isDeleted){
        	var myDate=formatDate(this.lastUpdated);
        	if(this.status=='Not Ready')
				 status='<span class="statusIcon notreadyIcon"><g:message code="document.list.status.notready" /></span>';
			else if(this.status=='Afgekeurd')
				 status='<span class="statusIcon notreadyIcon"><g:message code="messages.status.afgekeurd" /></span>';
			else if(this.status=='Goedgekeurd')
				 status='<span class="statusIcon nvtIcon"><g:message code="messages.status.goedgekeurd" /></span>';
			else if(this.status=='Akkoord na herkeuring')
				 status='<span class="statusIcon intreatmentIcon"><g:message code="messages.status.Akkoordnaherkeuring" /></span>';
			else if(this.status=='NVT')
				 status='<span class="statusIcon readyIcon"><g:message code="document.list.status.nvt" /></span>';
				 var deadLine = '';
					if(this.deadLine!='null' && this.deadLine!=null)
						deadLine=formatDate(this.deadLine)
        		documentsCount=1;
        	  	$('#equipmentsList').append('<div class="fullWidth  thirdGridColor" id="equipmentNumber'+this.id+'"><ul class="adminHead list "><li class="width10  docList  fl"><a href="'+link+'/'+this.id+'" > '+this.name+'</a></li><li  class="userName docList  fl">'+ response.projectList[this.id]+'</li><li  class="userName docList  fl">'+response.buildingList[this.id]+'</li><li class="width10  docList  fl">'+response.floorList[this.id]+'</li><li class="width12Half textCenter  fl ">'+status+'</li><li class="userName docList fl">'+myDate +'</li><li class="width10 docList fl">'+deadLine+'</li><li class=" width9Half textCenter fl "><a href="#" onclick="deleteRequest(\''+this.id+'\',\'Equipment\',\''+this.name+'\')" class="synchronize disableButton activeEditBtn fl">synchronize</a></li></ul></div');
	        }
        	});
    	
        	$('#pagination').remove();
    	 	$('#equipmentsList').easyPaginate({
   		  	});
    	if(documentsCount==0)
			 $('#equipmentsList').append('<div class="fullWidth paddingTop  thirdGridColor"><g:message code="equipments.not.available"/></div>');
    	
	}
    </script>