<!doctype html>
<%@page import="com.kam.User"%>
<%@page import="com.kam.UserRole"%>


<html>
<head>
<meta name='layout' content='main' />
<title><g:message code="title.message"></g:message></title>

</head>

<body>
<g:render template="/layouts/navigation" model="[loggedInUserRole:role,loggedinUser:loggedinUser]"></g:render>
<ul class="backUl">
	<li class="fr paddingRight"><a href="javascript:history.go(-1)" class="back"><span><g:message code="login.customer.back"/></span></a></li>
 </ul>
<div class="customerList">
<div id="main_box"  class="fl"><!--main_box-->
        <g:render template="/layouts/userNavigation" model="[projectList:projectList,flow:loggedinUser.customer.flow]"></g:render>
    
     <div id="rhtBoxtext" ><!--start of rht_boxtext-->
     	<h1 class="userHeading paddingTop"><g:message code="show.document.document"/> : ${document.name }</h1>
     	<div class="fullWidth  gridColor"> <!--start of Status_navi-->
          <ul class="adminHead fullWidth gridColor">
            <li class="width25percent viewDocPadding  liDivider"><g:message code="list.project"/> :${document.project.projectName }</li>
            <li  class=" userName viewDocPadding   liDivider"><g:message code="view.document.building"/> :${documentBuilding }</li>
            <li  class="userName viewDocPadding   liDivider"><g:message code="view.document.floor"/> :${documentFloor }</li>
            <li class="userName viewDocPadding   liDivider"><g:message code="view.document.room"/> :${documentRoom }</li>
            <li class="width25percent viewDocPadding  fl "><g:message code="view.document.discipline"/> :${document.discipline }</li>
            
          </ul>
          </div>
          <div id="documentsList">
        <div class="fullWidth thirdGridColor">
 
          <ul class="adminHead fullWidth">
            <li class="width32 viewDocPadding fl"><g:message code="list.documentname"/> :${document.name }</li>
            <li  class="width32 viewDocPadding fl"><g:message code="view.document.id"/> :${document.documentNumber }</li>
            <li  class="width32 viewDocPadding fl"><g:message code="users.user.status"/> :${document.status }</li>
            
          </ul>
    		<ul class="adminHead fullWidth">
            <li class="width32 viewDocPadding fl"><g:message code="view.document.created"/> :<g:formatDate format="dd/MM/yyyy" date="${document.dateCreated}"/></li>
            <li  class="width32 viewDocPadding fl"><g:message code="view.document.updated"/> :${document.updatedBy  }</li>
            
            
          </ul>
          
         
    <!-- version code start form here -->        
          <ul class="adminHead fullWidth gridColor">
              <li class="width32 viewDocPadding fl"><g:message code="view.document.version"/></li>
          </ul>
          <ul class="adminHead fullWidth">
            <li class="width32 viewDocPadding fl"><g:message code="view.document.version"/> :${document.documentVersion }</li>
            <li  class="width32 viewDocPadding fl"><g:message code="view.document.last.updated"/> :<g:formatDate format="dd/MM/yyyy" date="${document.lastUpdated}"/></li>
            <li  class="width32 viewDocPadding fl"></li>
         </ul>
    <!-- version code start form here -->        
        </div>
        <div class="fullWidth  gridColor"> 
        <!--start of Status_navi-->
        <ul class="adminHead fullWidth gridColor boderTopBottom fl">
          	<li  class="width10 paddingTop fl"><g:message code="view.document.pdf"/> :</li>
            <li class=" fl marginRight"><g:pdfLink  pdfController="document" pdfAction="showDocument" pdfId="${document.id }" class="pdfIcon activeEditBtn fl"><g:message code="view.document.synchronize"/></g:pdfLink></li>
          	<li  class="serialNo paddingTop fl marginLeft"><g:message code="template.view"/> :</li>
          	<li class=" fl "><g:link controller="document" action="showDocument" class="viewTemplate activeEditBtn fl" ><g:message code="users.user.edit"/></g:link></li>
          	<li  class=" paddingTop fl marginLeft"><g:message code="users.user.edit"/> :</li>
          	<li class=" fl "><g:link controller="document" action="changeDocument" id="${document.id }" class="editButton activeEditBtn fl" ><g:message code="users.user.edit"/></g:link></li>
        </ul>
        <ul class="adminHead fullWidth gridColor boderTopBottom fl">
          	<li  class="width18 paddingTop fl"><g:message code="view.document.image"/> :</li>
          	<g:each in="${documentImagesList }" var="documentImage">
            	<li class="fl marginRight"> <a href="#" class=" fl "><img src="${resource(dir:'DocumentImages',file:documentImage.formElementValue) }" class="activeEditBtn "></a></li>
            </g:each>
            <g:each in="${documentAttachments }" var="attachedImages">
            	<li class="fl marginRight"> <a href="#" class=" fl "><img src="${resource(dir:'DocumentImages',file:attachedImages.name) }" class="activeEditBtn "></a></li>
            </g:each>
        </ul>
        </div>
	</div>
</div> 
</div>      
</div>
<script src="${resource(dir:'js',file:'document.js') }"></script>
<script type='text/javascript'>
	$(document).ready(function(){
	     $('#project').click(function() {
	     	$('#projectDropdown').slideToggle();
	     });

	});
		</script>
</body>
</html>