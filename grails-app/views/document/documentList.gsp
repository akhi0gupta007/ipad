<!DOCTYPE html>
<html>
<head>
<meta name='layout' content='main' />
<title><g:message code="title.message"></g:message></title>
</head>
<body>

<g:render template="/layouts/navigation" model="[loggedInUserRole:role,loggedinUser:loggedinUser,page:'documentList']"></g:render>
<!-- back Navigation start from here -->
	<ul class="backUl">
 		<li class="fl">
 			<g:formRemote name="searchForm" url="[controller:'document',action:'documentSearch']" onSuccess="updateDocumentList(data);" update="documentsList" >
 				<input type="text" name="query" id="documentsSearchQuery" class="searchField fl" placeholder="<g:message code="messages.search.documents" />" />
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
					<li class=" fl marginLeft"><h1 class="userHeading paddingTop fl"> <g:message code="messages.documents.list" /></h1></li>
		  		 	<li class=" fr marginRight "><g:pdfLink  pdfController="document" pdfAction="documentListPdf" pdfId="pdf" class="exportPdf fl userHeading" ><g:message code="document.list.export" /> :</g:pdfLink></li>
				</ul>
     	
				<div class="fullWidth  gridColor"> <!--start of Status_navi-->
          			<ul class="adminHead fullWidth gridColor list">
           				<li class="width10 docList liDivider"><a href="#" onclick="sortDocumentList('project.projectName','projectId');" class="alink"><g:message code="list.project" /><span class="downArrow" id="projectId"></span></a></li>
            			<li  class="userName docList liDivider"><a href="#" onclick="sortDocumentList('name','docname');" class="alink"><g:message code="list.documentname" /><span class="downArrow" id="docname"></span></a></li>
            			<li  class="userName docList  liDivider"><a href="#" onclick="sortDocumentList('documentNumber','documentNumber');" class="alink"><g:message code="list.documentno" /><span class="downArrow" id="documentNumber"></span></a></li>
            			<li class="width10 docList liDivider"><a href="#" onclick="sortDocumentList('discipline','docDiscipline');" class="alink"><g:message code="list.discipline" /><span class="downArrow" id="docDiscipline"></span></a></li>
            			<li class="width12 docList liDivider"><a href="#" onclick="sortDocumentList('status','docStatus');" class="alink"><g:message code="list.status" /><span class="downArrow" id="docStatus"></span></a></li>
            			<li class="userName docList liDivider"><a href="#" onclick="sortDocumentList('lastUpdated','changedDate');" class="alink"><g:message code="list.changeddate" /><span class="downArrow" id="changedDate"></span></a> </li>
           				<li class="width10 docList liDivider"><g:message code="list.deadline" /></li>
           				<li class="fl docList delete"><g:message code="template.delete"/></li>
          			</ul>
          			</div>
          			<div id="documentsList">
          			
          				<g:if test="${docList.size()==0 }">
           					<div class=" paddingTop fullWidth thirdGridColor" >
           						<g:message code="documents.not.available"/>
           					</div>
          				</g:if>
          				<g:else>
          					<g:render template="documentsListing" model="[docList:docList,page:'documentList']"></g:render>
          				</g:else>
    				</div>
      			</div>
   			</div>
		</div>
	
  <!-- main content end here -->
  <script src="${resource(dir:'js',file:'easypaginate.js') }"></script>
  <script type="text/javascript">
  	var sortUrl="${createLink(controller:'document',action:'sortDocumentList')}";
	var link="${createLink(controller:'document',action:'viewDocument')}";
	var deleteDocumentUrl="${createLink(controller:'document',action:'deleteDocument')}";
  </script>
  <script type="text/javascript">
  $(document).ready(function(){
	  $('#documentsList').easyPaginate({
	  });
	}); 
  </script>
<script src="${resource(dir:'js',file:'document.js') }"></script>
</body>
</html>