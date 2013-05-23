<!doctype html>
<%@page import="com.kam.User"%>
<%@page import="com.kam.UserRole"%>
<%@page import="com.kam.Role"%>
<%@page import="com.kam.Customer"%>
<html>
<head>
<meta name='layout' content='main' />
<title><g:message code="title.message"></g:message></title>
<g:javascript library="jquery" plugin="jquery" />
</head>
<body>
	<g:render template="/layouts/navigation" model="[loggedInUserRole:role,loggedinUser:loggedinUser]"></g:render>

  		<ul class="backUl">
   			<li class="fl upperDownPadding"><g:message code="projects.label" /></li>
   			<li class="fr paddingRight"><a href="javascript:history.go(-1)" class="back"><span><g:message code="login.customer.back"/></span></a></li>
 		</ul>
 		
<!-- main content start -->		
 		<div class="customerList">
 			<div class="customerCompany gridColor" >
    			<ul class="fullWidth">
    				<li class="fl serialNo  paddingTop"><g:message code="template.serial.no"/></li>
     				<li class=" paddingTop fl width60"><g:message code="project.name"/></li>
     				<li class="widthEighteen paddingTop  fl"><g:message code="add.label" /></li>
     					<li class="role paddingTop fl"><g:message code="users.user.edit" /></li>
     				<li class="delete paddingTop fr textRight"><g:message code="account.delete" /></li>
     			</ul>
     		</div>
 			<div id="projectsList">
 			
 					<g:if test="${projectsList.size()>0 }">
 						<g:render template="projects" model="[projectList:projectList,loggedinUser:loggedinUser]"></g:render>
 						<div id="addProjects"></div>
          			</g:if>
          			<g:else>
           				<div class=" paddingTop customerCompany thirdGridColor" >
           					<g:message code="messages.projects.emptylist"/>
           				</div>
 					</g:else>
 			</div>
			
			
     	</div>
<g:render template="componentOverlay"></g:render>
<script src="${resource(dir:'js',file:'jquery-ui.min.js')}"></script>
<link href="${resource(dir:'css',file:'jquery-ui.css') }" rel="stylesheet" type="text/css"/>
<script src="${resource(dir:'js',file:'easypaginate.js') }"></script>
<script type="text/javascript">
	var equipmentComponentUrl='${createLink(controller:'component',action:'equipmentComponentList')}';
	
</script>
<script src="${resource(dir:'js',file:'equipment.js') }"></script>	
<script type="text/javascript">
$(document).ready(function() {
	$( "#revisionDate" ).datepicker({ dateFormat: 'dd/mm/yy' });
});

	
	function addBuildings(data){
		if(data.success){
		if(data.oldBuildingId==''){
			var viewReport='';
			var reportLink="${createLink(controller:'reports',action:'reportList')}";
			if(data.currentCustomer.flow=='equipment')
				viewReport = '<li class=" fl"><a href="'+reportLink+'/'+data.currentId.id+'" id="'+data.currentId.id+'" ><g:message code="messages.component.viewreports" /></a></li>';
			$('#addBuilding'+data.parentProject).append('<div class="fullWidth borderBottom fl" id="buildingNumber'+data.currentId.id+'" ><div class="liWidth  fl buildings"><ul><li class="fl paddingRight widthHalf"><a href="#" onclick="editComponent(\''+data.currentId.id+'\',\'building\')"  class="fontColor">'+data.currentId.buildingName+'</a></li><li class="hidden shortDelete fr" onclick="deleteRequest(\''+data.currentId.id+'\',\'Building\')"><a >Delete</a></li>'+viewReport+'</ul> </div>	<div class=" fl innerDiv"><div id="addFloor'+data.currentId.id+'"></div><div class=" fl liWidth"><div class="cancelCommonBg  AddCommonBg"><input type="button" class="inputButton fl" value=\'<g:message code="view.document.floor" />\' onclick="openOverlay(\'floorOverlay\',\''+data.currentId.id+'\',\'parentBuilding\')" /></div></div></div></div>');
		}
		else
			$('#buildingNumber'+data.currentId.id).find('div.buildings > ul > li > a.fontColor').html($('#buildingName').val());
		}
		closeOverlay('buildingOverlay');
	}		

	function addFloors(data){
		if(data.success){
		if(data.oldFloorId==''){
			if(data.parentBuilding.flow=='document')
				$('#addFloor'+data.parentBuilding.id).append('<div class=" fl fullWidth borderBottom" id="floorNumber'+data.currentId.id+'"><div class=" fl liWidth floors"><ul><li class="fl paddingRight widthHalf"><a href="#" onclick="editComponent(\''+data.currentId.id+'\',\'floor\')"  class="fontColor" >'+data.currentId.floorNumber+'</a></li><li class="hidden shortDelete fr" onclick="deleteRequest(\''+data.currentId.id+'\',\'Floor\')"><a>Delete</a></li><li class=" fl" onclick="showFloorMapImage(\''+data.currentId.id+'\')"><a href="#" ><g:message code="messages.component.viewfloorplan" /></a></li></ul> </div><div class=" fl liWidth borderLeft" ><div id="addRoom'+data.currentId.id+'"></div><div class="cancelCommonBg  AddCommonBg"><input type="button" class="inputButton fl" onclick="openOverlay(\'roomOverlay\',\''+data.currentId.id+'\',\'parentFloor\')" value=\'<g:message code="view.document.room" />\' /></div></div></div>');
			else
				$('#addFloor'+data.parentBuilding.id).append('<div class=" fl fullWidth borderBottom" id="floorNumber'+data.currentId.id+'"><div class=" fl liWidth floors"><ul><li class="fl paddingRight widthHalf"><a href="#" onclick="editComponent(\''+data.currentId.id+'\',\'floor\')"  class="fontColor" >'+data.currentId.floorNumber+'</a></li><li class="hidden shortDelete fr" onclick="deleteRequest(\''+data.currentId.id+'\',\'Floor\')"><a>Delete</a></li><li class=" fl" onclick="showFloorMapImage(\''+data.currentId.id+'\')"><a href="#" ><g:message code="messages.component.viewfloorplan" /></a></li></ul> </div><div class=" fl liWidth borderLeft" ><div id="addEquipment'+data.currentId.id+'"></div><div class="cancelCommonBg  AddCommonBg"><input type="button" class="inputButton fl" onclick="openOverlay(\'equipmentOverlay\',\''+data.currentId.id+'\',\'parentEquipmentFloor\')" value=\'<g:message code="component.equipment" />\' /></div></div></div>');
		}
		else
			$('#floorNumber'+data.currentId.id).find('div.floors > ul > li > a.fontColor').html($('#floorNumber').val());
		}
			closeOverlay('floorOverlay');
	}

	function addRooms(data){
		if(data.success){
		if(data.oldRoomId=='')
			$('#addRoom'+data.parentFloor).append('<ul id="roomNumber'+data.currentId.id+'" class="fullWidth marginBottom rooms"><li class="fl paddingRight"><span class="fullWidth" ><a href="#" onclick="editComponent(\''+data.currentId.id+'\',\'room\')"  class="fontColor" >'+data.currentId.roomId+'</a></span></li><li class="hidden shortDelete fr" onclick="deleteRequest(\''+data.currentId.id+'\',\'Room\')"><a>Delete</a></li></ul> ');
		else
			$('#roomNumber'+data.currentId.id).find('a.fontColor').html($('#roomId').val());
		}
		closeOverlay('roomOverlay');
	}

	function addEquipments(data){
		if(data.success){
		if(data.oldEquipmentId=='')
			$('#addEquipment'+data.parentFloor).append('<ul  id="equipmentNumber'+data.currentId.id+'" class="fullWidth marginBottom equipments"><li class="fl paddingRight"><span class="fullWidth"><a href="#" onclick="editComponent(\''+data.currentId.id+'\',\'equipment\')"  class="fontColor" >'+data.currentId.name+'</a></span></li><li class="hidden shortDelete fr" onclick="deleteRequest(\''+data.currentId.id+'\',\'Equipment\')"><a>Delete</a></li></ul> ');
		else
			$('#equipmentNumber'+data.currentId.id).find('a.fontColor').html($('#equipmentName').val());
		}
		closeOverlay('equipmentOverlay');
		getequipmentComponentList();
	}

	function loadProjectValues(data){
		$('#projectNumber').val(data.oldProject.projectNumber);
		$('#projectName').val(data.oldProject.projectName);
		$('#projectAddress').val(data.oldProject.address);
		$('#projectCity').val(data.oldProject.city);
		$('#projectTitle').val(data.oldProject.projectTitle);
		$('#projectDocnaam').val(data.oldProject.docname);
		$('#oldProjectId').val(data.oldProject.id);
		openOverlay('projectOverlay','','');
	}

	function loadBuildingValues(data){
		$('#buildingName').val(data.oldBuilding.buildingName);
		$('#buildingNumber').val(data.oldBuilding.buildingNumber);
		$('#buildingAddress').val(data.oldBuilding.address);
		$('#buildingZip').val(data.oldBuilding.zipCode);
		$('#buildingCity').val(data.oldBuilding.city);
		
		$('#oldBuildingId').val(data.oldBuilding.id);
		openOverlay('buildingOverlay','','');
	}

	function loadFloorValues(data){
		$('#qq-upload-list').empty();
		$('#floorNumber').val(data.oldFloor.floorNumber);
		$('#floorDescription').val(data.oldFloor.floorDescription);
		$('#floorMap').val(data.oldFloor.floorMap);
		$('#floorOverlay').find('iframe').contents().find('ul.qq-upload-list').html('');
		var revisionDate='';
		if(data.oldFloorRevisionDate!='')
			revisionDate= formatDate(data.oldFloorRevisionDate);
		$('#revisionDate').val(revisionDate);
		$('#oldFloorId').val(data.oldFloor.id);
		openOverlay('floorOverlay','','');
	}

	function loadRoomValues(data){
		$('#roomId').val(data.oldRoom.roomId);
		$('#oldRoomId').val(data.oldRoom.id);
		openOverlay('roomOverlay','','');
	}

	function loadEquipmentValues(data){
		var equipmentBrandName='',equipmentBatteryType='',equipmentArmatuurType='',equipmentName='',equipmentUnitName='',equipmentGroup='',equipmentKast='';
		$('#equipmentName').val(data.oldEquipment.name);
		$('#equipmentDescription').val(data.oldEquipment.description);
		$('#buildYearOfBattery').val(data.oldEquipment.buildYearOfBattery);
		$('#buildYearOfArmature').val(data.oldEquipment.buildYearOfArmature);
		$('#buildYearOfEmergencyUnit').val(data.oldEquipment.buildYearOfEmergencyUnit);
		$('#equipmentType'+data.oldEquipment.equipmentType).attr('checked','true');
		$('#equipmentType'+data.oldEquipment.equipmentType2).attr('checked','true');
		if(data.equipmentBrand!=null)
			equipmentBrandName=data.equipmentBrand.brandName;
		if(data.equipmentBattery!=null)
			equipmentBatteryType=data.equipmentBattery.batteryType;
		if(data.equipmentArmatuur!=null)
			equipmentArmatuurType=data.equipmentArmatuur.armatuurType;
		if(data.equipmentLight!=null)
			equipmentName=data.equipmentLight.name;
		if(data.equipmentEmergencyUnitOfPrint!=null)
			equipmentUnitName=data.equipmentEmergencyUnitOfPrint.unitName;
		if(data.groupNumber!=null)
			equipmentGroup=data.groupNumber.groupNumber;
		if(data.kast!=null)
			equipmentKast=data.kast.kastName;
		
		updateMasterData(equipmentBrandName,equipmentBatteryType,equipmentArmatuurType,equipmentName,equipmentUnitName,equipmentGroup,equipmentKast);
		
		$('#oldEquipmentId').val(data.oldEquipment.id);
		openOverlay('equipmentOverlay','','');
	}

	function updateMasterData(equipmentBrandName,equipmentBatteryType,equipmentArmatuurType,equipmentName,equipmentUnitName,groupNumber,kast){
		if(equipmentBrandName!=''){
			$('#brand').val(equipmentBrandName);
			$('#brandName').val(equipmentBrandName);
			$('#oldBrandName').val(equipmentBrandName);
			//$('#brandName').attr('readonly','readonly');
		}
		else{
			$('#brand').val('Other');
			$('#brandName').val('');
			$('#oldBrandName').val('');
			//$('#brandName').removeAttr('readonly');
			}
		
		if(equipmentBatteryType!=''){
			$('#battery').val(equipmentBatteryType);
			$('#batteryName').val(equipmentBatteryType);
			$('#oldBatteryName').val(equipmentBatteryType);
			//$('#batteryName').attr('readonly','readonly');
		}
		else{
			$('#battery').val('Other');
			$('#batteryName').val('');
			$('#oldBatteryName').val('');
			//$('#batteryName').removeAttr('readonly');
			}
		
		if(equipmentArmatuurType!=''){
			$('#armature').val(equipmentArmatuurType);
			$('#armatureName').val(equipmentArmatuurType);
			$('#oldArmatureName').val(equipmentArmatuurType);
			//$('#armatureName').attr('readonly','readonly');
		}
		else{
			$('#armature').val('Other');
			$('#armatureName').val('');
			$('#oldArmatureName').val('');
			//$('#armatureName').removeAttr('readonly');
			}
		
		if(equipmentName!=''){
			$('#light').val(equipmentName);
			$('#lightName').val(equipmentName);
			$('#oldLightName').val(equipmentName);
			//$('#lightName').attr('readonly','readonly');
		}
		else{
			$('#light').val('Other');
			$('#lightName').val('');
			$('#oldLightName').val('');
			//$('#lightName').removeAttr('readonly');
			}
		
		if(equipmentUnitName!=''){
			$('#emergencyUnitOfPrint').val(equipmentUnitName);
			$('#emergencyUnitOfPrintName').val(equipmentUnitName);
			$('#oldEmergencyUnitOfPrintName').val(equipmentUnitName);
			//$('#emergencyUnitOfPrintName').attr('readonly','readonly');
		}
		else{
			$('#emergencyUnitOfPrint').val('Other');
			$('#emergencyUnitOfPrintName').val('');
			$('#oldEmergencyUnitOfPrintName').val('');
			//$('#emergencyUnitOfPrintName').removeAttr('readonly');
			}

		if(groupNumber!=''){
			$('#group').val(groupNumber);
			$('#groupName').val(groupNumber);
			$('#oldGroupName').val(groupNumber);
			//$('#emergencyUnitOfPrintName').attr('readonly','readonly');
		}
		else{
			$('#emergencyUnitOfPrint').val('Other');
			$('#emergencyUnitOfPrintName').val('');
			$('#oldEmergencyUnitOfPrintName').val('');
			//$('#emergencyUnitOfPrintName').removeAttr('readonly');
			}

		if(kast!=''){
			$('#kast').val(kast);
			$('#kastName').val(kast);
			$('#oldKastName').val(kast);
			//$('#emergencyUnitOfPrintName').attr('readonly','readonly');
		}
		else{
			$('#emergencyUnitOfPrint').val('Other');
			$('#emergencyUnitOfPrintName').val('');
			$('#oldEmergencyUnitOfPrintName').val('');
			//$('#emergencyUnitOfPrintName').removeAttr('readonly');
			}
	}

	function editComponent(id,type){
		var url
		if(type=='project')
			url="${createLink(controller:'component',action:'editProject')}";
		else if(type=='building')
			url="${createLink(controller:'component',action:'editBuilding')}";
		else if(type=='floor')
			url="${createLink(controller:'component',action:'editFloor')}";
		else if(type=='room')
			url="${createLink(controller:'component',action:'editRoom')}";
		else if(type=='equipment')
			url="${createLink(controller:'component',action:'editEquipment')}";
		else if(type=='floorMap')
			url="${createLink(controller:'component',action:'editFloor')}";
	    jQuery.ajax({
	         type: 'POST',
	         url: url,
	         data:'id='+id,
	         success: function(response,textStatus){
	        	 if(type=='project')
	        		 loadProjectValues(response);
	     		else if(type=='building')
	     			loadBuildingValues(response);
	     		else if(type=='floor')
	     			loadFloorValues(response);
	     		else if(type=='room')
	     			loadRoomValues(response);
	     		else if(type=='equipment')
	     			loadEquipmentValues(response);
	     		else if(type=='floorMap'){
		     		var FloorMapImagePath="${application.contextPath }";
		     		$('#floorMapImage').attr('src',FloorMapImagePath+'/FloorMap/'+response.oldFloor.floorMap);
		     		if(response.oldFloor.floorMap=='' || response.oldFloor.floorMap=='null' || response.oldFloor.floorMap==null)
		     			$('#FloorMapImages').html('<div class="formInput textCenter"><g:message code="messages.nofloormap" /></div>');
		     		else
		     			$('#FloorMapImages').html('');
		     		
	     		}
	         
	          },
	         error:function(XMLHttpRequest,textStatus,errorThrown){}
	     });
	}

	function deleteRequest(id,type){
		var url="${createLink(controller:'component',action:'deleteComponent')}";
		if(confirmComponentDelete(id,type)){
	    	jQuery.ajax({
	        	type: 'POST',
	         	url: url,
	         	data:'id='+id+'&type='+type,
	         	success: function(response,textStatus){
	        		 deleteComponent(response);
	          	},
	         	error:function(XMLHttpRequest,textStatus,errorThrown){}
	     	});
		}
	}

	function confirmComponentDelete(Id,Type){
		var name = '';
		if(Type=='Project')
			name = $('#projectNumber'+Id).find('ul > li > p > a').html();
		if(Type=='Building')
			name = $('#buildingNumber'+Id).find('ul > li > a').html();
		if(Type=='Floor')
			name = $('#floorNumber'+Id).find('ul > li > a').html();
		if(Type=='Room')
			name = $('#roomNumber'+Id).find('span > a').html();
		if(Type=='Equipment')
			name = $('#equipmentNumber'+Id).find('span > a').html();
		var selection=confirm(deletetionConfirm+" "+name+"?");
		if(selection==false)
			return false;
		else
			return true;
	}

	function deleteComponent(data){
		if(data.deletedType=="Project"){
			$('#ProjectsListing'+data.deletedId.id).remove();
			var projectCount=1;
			$('#projectsList').find('div.customerCompany > ul > li.serialNo ').each(function(){
				$(this).html(projectCount);
				projectCount=projectCount+1;
				});
			if(projectCount==1)
				$('#projectsList').html('<div class=" paddingTop customerCompany thirdGridColor" >'+projectsListingEmpty+'</div>');
		
			$('#pagination').remove();
			$('#projectsList').easyPaginate({
			  });
		}
		else if(data.deletedType=="Building")
			$('#buildingNumber'+data.deletedId.id).remove();
		else if(data.deletedType=="Floor")
			$('#floorNumber'+data.deletedId.id).remove();
		else if(data.deletedType=="Room")
			$('#roomNumber'+data.deletedId.id).remove();
		else if(data.deletedType=="Equipment")
			$('#equipmentNumber'+data.deletedId.id).remove();
		
	}

	function showFloorMapImage(id){
		editComponent(id,'floorMap');
		openOverlay('viewFloorPlanOverlay','','');
	}
</script>

<script type="text/javascript">
  $(document).ready(function(){
	  $('#projectsList').easyPaginate({
	  });
	}); 
  </script>
</body>
</html>