<g:each status="count" in="${building.reports.sort{a,b -> (a.lastUpdated > b.lastUpdated)? -1:1 } }" var="report" >
	<div class="customerCompany thirdGridColor" id="${report.id }">
   		<ul class="commonCss adminHead fullWidth list">
   			<li class="fl serialNo paddingTop">${count+1 }</li>
   			<li class="fl widthEighteen paddingTop">${report.reportName }</li>
  			<li class="fl templateNameHead paddingTop">${report.reviewedBy }</li>
    		<li class="fl userName paddingTop">${report.dateCreated.dateString}</li>
     		<li class="fl userName paddingTop">${report.lastUpdated.dateString }</li>
   			<li class="fl width12Half"><g:link controller="reports" action="equipmentReport" id="${report.id}" class="viewTemplate activeEditBtn fl" title="View ${report.reportName}">View</g:link></li>
   			<li class="fl   width10"><g:link  controller="reports" action="editReport" id="${report.id}" class="editButton activeEditBtn fl" title="Edit ${report.reportName}">Edit</g:link></li>
   			
   			<li class="fr deleteButton  delete"><g:remoteLink controller="reports" action="deleteReport" id="${report.id }" beforeSend="confirmReportDelete('${report.reportName }')" before="if(confirmReportDelete('${report.reportName }')){" after="}" onSuccess="deleteReport(data,'${report.id  }');" class="disableButton activeEditBtn fr marginRight" title="Delete ${report.reportName}">delete</g:remoteLink></li>
  	 	</ul>
   	</div>
</g:each>