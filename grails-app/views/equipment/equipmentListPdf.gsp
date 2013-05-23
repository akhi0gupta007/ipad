<!DOCTYPE html>
<html>
<head>
<title><g:message code="title.message"></g:message></title>
<style>

.marginTopBottom{
	margin:10px 0;
}
thead {
	display: table-header-group;
	}
table {
            -fs-table-paginate: paginate;
            
        }
.width25{
width:25%;
}

</style>
</head>

<body>
	<g:if test="${customerLogo!='' }">
		<img src="${resource(dir:'ThemeImages',file:customerLogo) }" height="86px" width="121px"  />
	</g:if><!-- first table start from here -->

<h1> <g:message code="messages.equipment.list" /></h1>
	<table width="100%" border="1" cellspacing="0" class="marginTopBottom" cellpadding="0">
   		<tr>
    		<td colspan="2"><g:message code="messages.equipment.pdf" /></td>
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
    		<td class="width25"></td>
    		<td class="width25"></td>
    	</tr>
  	</table>
 <!-- first table end from here --> 	
 
 
 <!-- second table start from here -->	
  	<table  width="100%" border="1"  cellspacing="0" cellpadding="0">
  		<thead>
  			<tr>
  				<td><g:message code="component.equipment" /></td>
  				<td><g:message code="list.project" /></td>
  				<td><g:message code="view.document.building" /></td>
  				<td><g:message code="view.document.floor" /></td>
  				<td><g:message code="list.status" /></td>
  				<td><g:message code="list.changeddate" /></td>
  			</tr>
  			</thead>
  		<g:if test="${!defaultAction }" >
  			<g:each in="${equipmentsList }" var="equipment">
  				<g:if test="${!equipment.isDeleted }" >
  				<tbody>
  					<tr>
  						<td>${equipment.name }</td>
  						<td>${equipment.floor.building.project.projectName }</td>
  						<td>${equipment.floor.building.buildingNumber }</td>
  						<td>${equipment.floor.floorNumber }</td>
  						<td><span class="statusIcon readyIcon">${equipment.status }</span></td>
  						<td><g:formatDate format="dd/MM/yyyy" date="${equipment.lastUpdated }"/></td>	
  					</tr>
  						</tbody>
  				</g:if>
  		 	</g:each>
  		 </g:if>
  		 <g:else>
  		 	<g:each in="${equipmentsList }" var="equipment">
  				<g:if test="${!equipment[7] }" >
  					<tbody>
  					<tr>
  						<td>${equipment[0] }</td>
  						<td>${equipment[1] }</td>
  						<td>${equipment[2] }</td>
  						<td>${equipment[3] }</td>
  						<td><span class="statusIcon readyIcon">${equipment[4] }</span></td>
  						<td><g:formatDate format="dd/MM/yyyy" date="${equipment[5] }"/></td>	
  					</tr>
  					</tbody>
  				</g:if>
  		 	</g:each>
  		 </g:else>
  	</table>
 
 <!-- second table start from here -->
    
    

</body>


</html>