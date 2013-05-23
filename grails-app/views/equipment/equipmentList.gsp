<!DOCTYPE html>
<html>
<head>
<meta name='layout' content='main' />
<title><g:message code="title.message"></g:message></title>
</head>
<body>
<g:render template="/layouts/navigation" model="[loggedInUserRole:role,loggedinUser:loggedinUser,page:'equipmentList']"></g:render>
<!-- back Navigation start from here -->
	<ul class="backUl">
 		<li class="fl">
 			<g:formRemote name="searchForm" url="[controller:'equipment',action:'equipmentSearch']" onSuccess="updateEquipmentList(data);" update="equipmentsList" >
 				<input type="text" name="query" id="equipmentsSearchQuery" class="searchField fl" placeholder='<g:message code="messages.search.armature" />' />
				<input type="submit" value="" name="searchField" class="searchButtton fl" />
			</g:formRemote>
		</li> 
		<sec:ifNotGranted roles="ROLE_USER">
			<li class="fr paddingRight"><a href="javascript:history.go(-1)" class="back"><span><g:message code="login.customer.back"/></span></a></li>
		</sec:ifNotGranted>
	</ul>
<!-- back Navigation end here -->

<!-- main content start from here -->
	<div class="customerList">
		<div id="main_box" class="fl"><!--main_box-->
        	<g:render template="/layouts/userNavigation" model="[projectList:projectList,flow:loggedinUser.customer.flow]"></g:render>
   			 <div id="rhtBoxtext"><!--start of rht_boxtext-->
				<ul class="adminHead fullWidth">
					<li class=" fl marginLeft"><h1 class="userHeading paddingTop fl"> <g:message code="messages.equipment.list" /></h1></li>
		  		 	<li class=" fr marginRight "><g:pdfLink  pdfController="equipment" pdfAction="equipmentListPdf" pdfId="pdf" class="exportPdf fl userHeading" ><g:message code="document.list.export" /> :</g:pdfLink></li>
				</ul>
     	
				<div class="fullWidth  gridColor"> <!--start of Status_navi-->
          			<ul class="adminHead fullWidth gridColor list">
           				<li class="width10 docList liDivider"><a href="#" onclick="sortEquipmentList('name','equipmentName');" class="alink"><g:message code="component.equipment" /><span class="downArrow" id="equipmentName"></span></a></li>
            			<li  class="userName docList liDivider"><a href="#" onclick="sortEquipmentList('floor.building.project.projectName','equipmentProject');" class="alink"><g:message code="list.project" /><span class="downArrow" id="equipmentProject"></span></a></li>
            			<li  class="userName docList  liDivider"><a href="#" onclick="sortEquipmentList('floor.building.buildingName','equipmentBuilding');" class="alink"><g:message code="view.document.building" /><span class="downArrow" id="equipmentBuilding"></span></a></li>
            			<li class="width10 docList liDivider"><a href="#" onclick="sortEquipmentList('floor.floorNumber','equipmentFloor');" class="alink"><g:message code="view.document.floor" /><span class="downArrow" id="equipmentFloor"></span></a></li>
            			<li class="width12 docList liDivider"><a href="#" onclick="sortEquipmentList('status','equipmentStatus');" class="alink"><g:message code="list.status" /><span class="downArrow" id="equipmentStatus"></span></a></li>
            			<li class="userName docList liDivider"><a href="#" onclick="sortEquipmentList('lastUpdated','changedDate');" class="alink"><g:message code="list.changeddate" /><span class="downArrow" id="changedDate"></span></a> </li>
           				<li class="width10 docList liDivider"><g:message code="list.deadline" /></li>
           				<li class="fl docList delete"><g:message code="template.delete"/></li>
          			</ul>
          			</div>
          			<div id="equipmentsList">
          			
          				<g:if test="${equipmentsList.size()==0 }">
           					<div class="paddingTop fullWidth thirdGridColor" >
           						<g:message code="equipments.not.available"/>
           					</div>
          				</g:if>
          				<g:else>
          					<g:render template="equipments" model="[equipmentsList:equipmentsList,page:'equipmentList']"></g:render>
          				</g:else>
    				</div>
      			</div>
   			</div>
		</div>
	
  <!-- main content end here -->
  <script src="${resource(dir:'js',file:'easypaginate.js') }"></script>
  <script type="text/javascript">
  	var sortUrl="${createLink(controller:'equipment',action:'sortEquipmentList')}";
	var link="${createLink(controller:'equipment',action:'viewEquipment')}";
	var deleteEquipmentUrl="${createLink(controller:'component',action:'deleteComponent')}";
  </script>
  <script type="text/javascript">
  $(document).ready(function(){
	  $('#equipmentsList').easyPaginate({
	  });
	}); 
  </script>
<script src="${resource(dir:'js',file:'equipmentList.js') }"></script>
</body>
</html>