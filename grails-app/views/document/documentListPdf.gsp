<!DOCTYPE html>
<html>
<head>
<title><g:message code="title.message"></g:message></title>
<style>

.marginTopBottom{
	margin:15px 0;
}
.width25{
width:25%;
}

</style>
</head>

<body>
	<g:if test="${customerLogo!='' }">
		<img src="${resource(dir:'ThemeImages',file:customerLogo) }" height="86px" width="121px"  />
	</g:if>
<!-- first table start from here -->
<h1> <g:message code="messages.documents.list" /></h1>
	<table width="100%" border="1" cellspacing="0" cellpadding="0">
   		<tr>
    		<td colspan="2"><g:message code="messages.document.pdf" /></td>
    		<td class="width25"><g:message code="messages.document.pdf.date" /></td>
    		<td class="width25"><g:formatDate format="dd/MM/yyyy" date="${date }"/></td>
    	</tr>
    	<tr>
    		<td class="width25"><g:message code="list.project" /></td>
    		<td class="width25">${project }</td>
    		<td class="width25"><g:message code="view.document.building" /> </td>
    		<td class="width25">${building }</td>
    	</tr>
    	<tr>
    		<td class="width25"><g:message code="view.document.floor" /></td>
    		<td class="width25">${floor }</td>
    		<td class="width25"><g:message code="view.document.room" /></td>
    		<td class="width25">${room }</td>
    	</tr>
  	</table>
 <!-- first table end from here --> 	
 
 
 <!-- second table start from here -->	
  	<table  width="100%" border="1" class="marginTopBottom" cellspacing="0" cellpadding="0">
  		<tr>
  			<td><g:message code="list.project" /></td>
  			<td><g:message code="list.documentname" /></td>
  			<td><g:message code="list.documentno" /></td>
  			<td><g:message code="list.discipline" /></td>
  			<td><g:message code="list.status" /></td>
  			<td><g:message code="list.changeddate" /></td>
  		</tr>
  		<g:if test="${!defaultAction }">
  			<g:each in="${docList }" var="document">
  				<g:if test="${!document.isDeleted }" >
  					<tr>
  						<td>${document.project.projectName }</td>
  						<td>${document.name }</td>
  						<td>${document.documentNumber }</td>
  						<td><g:message code="list.discipline" /></td>
  						<td><span class="statusIcon readyIcon">${document.status }</span></td>
  						<td>${document.lastUpdated.dateString }</td>	
  					</tr>
  				</g:if>
  		 	</g:each>
  		 </g:if>
  		 <g:else>
  		 	<g:each in="${docList }" var="document">
  				<g:if test="${!document[9] }" >
  					<tr>
  						<td>${document[0] }</td>
  						<td>${document[1] }</td>
  						<td>${document[2] }</td>
  						<td>${document[8] }</td>
  						<td>${document[3] }</td>
  						<td><g:formatDate format="dd/MM/yyyy" date="${document[4] }"/></td>	
  					</tr>
  				</g:if>
  		 	</g:each>
  		 </g:else>
  	</table>
 
 <!-- second table start from here -->
    
    

</body>


</html>