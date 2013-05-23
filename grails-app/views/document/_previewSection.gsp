<!-- Project afpers remarks and body section start here -->
<g:if test="${documentSubSection=='1' }">
	<g:if test="${fourSideBorderClass != '' }">
		<g:if test="${documentItemValueMap.get(Listing).size()>0}">
    		<div class="pdfOuter fourSideBorder marginTopBottom fl">
    			<div class="width33Percent fl">
    				<g:each in="${documentItemValueMap.get(Listing) }" var="headListValues">
    					
    					<g:if test="${headListValues.type=='Image' && headListValues.alignment=='Left' }">
    					<g:if test="${headListValues.isSignature }" >${headListValues.formElementName } :</g:if> 
    						<g:if test="${showImage }">
    						<img src="${resource(dir:'DocumentImages',file:headListValues.formElementName) }" height="40px" width="100px" />
    						</g:if>
    					</g:if>
    					<g:elseif test="${headListValues.type=='Text' && headListValues.alignment=='Left'}">
    						
								<g:if test="${headListValues.isHeader }" >
    							<div class="fullWidth fl">
									<h4>${headListValues.formElementName }</h4> </div>
									</g:if>
									<g:else>
									<div class="fullWidth fl">
									<div class="widthHalf fl">
									${headListValues.formElementName } </div>:
									</div>
									</g:else>
						</g:elseif>
					</g:each>
    			</div>
    			
    			<div class="width33Percent fl">
    				<g:each in="${documentItemValueMap.get(Listing) }" var="headListValues">
    					<g:if test="${headListValues.isHeader && headListValues.alignment=='Center'}">
							<h4>${headListValues.formElementName}</h4>
     					</g:if>
    					<g:elseif test="${headListValues.type=='Image' && headListValues.alignment=='Center' }">
    					<g:if test="${headListValues.isSignature }" >${headListValues.formElementName } :</g:if> 
    						<g:if test="${showImage }">
    						<img src="${resource(dir:'DocumentImages',file:headListValues.formElementName) }" height="40px" width="100px" />
    						</g:if>
    					</g:elseif>
    					<g:elseif test="${headListValues.type=='Text' && headListValues.alignment=='Center'}">
    						
								<g:if test="${headListValues.isHeader }" >
    							<div class="fullWidth fl">
									<h4>${headListValues.formElementName }</h4> </div>
									</g:if>
									<g:else>
									<div class="fullWidth fl">
									<div class="widthHalf fl">
									${headListValues.formElementName } </div>:
									</div>
									</g:else>
						</g:elseif>
					</g:each>
    			</div>
    			
    			<div class="width33Percent fr">
    				<g:each in="${documentItemValueMap.get(Listing) }" var="headListValues">
    					<g:if test="${headListValues.isHeader && headListValues.alignment=='Right'}">
							<h4>${headListValues.formElementName}</h4>
     					</g:if>
    					<g:elseif test="${headListValues.type=='Image' && headListValues.alignment=='Right' }">
    					<g:if test="${headListValues.isSignature }" >${headListValues.formElementName } :</g:if> 
    						<g:if test="${showImage }">
    						<img src="${resource(dir:'DocumentImages',file:headListValues.formElementName) }" height="40px" width="100px"  />
    						</g:if>
    					</g:elseif>
    					<g:elseif test="${headListValues.type=='Text' && headListValues.alignment=='Right' }">
    						
    							<g:if test="${headListValues.isHeader }" >
    							<div class="fullWidth fl">
									<h4>${headListValues.formElementName }</h4> </div>
									</g:if>
									<g:else>
									<div class="fullWidth fl">
									<div class="widthHalf fl">
									${headListValues.formElementName } </div>:
									</div>
									</g:else>
						</g:elseif>
					</g:each>
    			</div>
    		</div>
    	</g:if>
    </g:if>
</g:if>
    			
    			
    			
    			
    			
    			
<g:if test="${documentSubSection=='2' }">			
   	<g:each status="itemCount" in="${documentItemValueMap.get(Listing) }" var="headListValues">
		<g:if test="${headListValues.type=='Table' }">
			<g:set var="rowCounter"  value="${1 }"></g:set>
				<tr>
    				<td>
    				<table width="100%"  border="1" >
      					<thead>
							<tr>
								<g:each status="tableHeader" in="${headListValues.headers.sort{it.id} }" var="header">
										<td style="min-width:20px;">
											${header.name }
										</td>
								</g:each>
							</tr>
						</thead>
						<tbody>
							<g:each status="number" in="${headListValues.questions.sort{it.id} }" var="question">
								<tr>
									<td width="25px">
										<div style="min-height:10px">
											<g:if test="${question.question!='' }">
												${question.serialNo }
											</g:if>
										</div>
									</td>
									<td> 
										<g:if test="${question.isHeader==true }">
											<h4>${question.question }</h4>
										</g:if>
										<g:else>
											${question.question }
										</g:else>
									</td>
									<g:each status="count" in="${headListValues.headers.sort{it.id} }" var="answer">
										<g:if test="${count<headListValues.headers.size()-2}">
											<td></td>
										</g:if>
									</g:each>
								</tr>
							</g:each>
						</tbody>
					</table>
					</td>
					</tr>
				</g:if>
				<g:if test="${headListValues.type=='CheckPoint' }">
					<tr><td>
					<div class="pdfOuter fl"> 
						<div class="fl marginRight"><h4>${headListValues.formElementName }</h4></div>
						<g:each status="bodyCount" in="${headListValues.questions.sort{it.id}  }" var="questionAnswer">
							<g:if test="${headListValues.isDeleted==false }">
					 			<g:if test="${headListValues.checkpointType=='CheckBox'  }">
					 				<span name="${questionAnswer.question }"><img src="${resource(dir:'images',file:'checkbox.png') }" /><div class="fr marginRight"> ${questionAnswer.question }</div></span>
					 			</g:if>
					 			<g:elseif test="${headListValues.checkpointType=='RadioButton'  }">
					 				<span name="${questionAnswer.question }" value="${questionAnswer.question }"  class="fl"><img src="${resource(dir:'images',file:'radioButton.png') }" /><div class="fr marginRight">${questionAnswer.question }</div></span>
					 			</g:elseif>
					 			<g:elseif test="${headListValues.checkpointType=='TextBox'  }">
					 				<div class="marginRight fl">
					 					${questionAnswer.question } : 
					 				</div>
					 			</g:elseif>
       						</g:if>
       					</g:each>
       				</div>
       			</td>
       		</tr>
       	</g:if>
    </g:each>
</g:if>
<!-- Project afpers remarks and body section end here -->
