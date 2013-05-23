<g:if test="${documentSubSection=='1' }">
	<g:if test="${fourSideBorderClass != '' }">
		<div class=" pdfOuter ${fourSideBorderClass } marginTopBottom fl">
    			<div class="width33Percent fl">
    				<g:each in="${documentItemValueMap.get(Listing) }" var="headListValues">
    					<g:if test="${headListValues.type=='Image' && headListValues.alignment=='Left' }">
    						<p >
    						<g:if test="${leftValues.documentItem.isSignature }" >${leftValues.documentItem.formElementName } :</g:if> 
    						<g:if test="${showImage }">
    						<img src="${resource(dir:'DocumentImages',file:headListValues.formElementValue) }" height="40px" width="100px" />
    						</g:if>
    						</p>
    					</g:if>
    					<g:elseif test="${headListValues.type=='Text' && headListValues.alignment=='Left'}">
    						
    							<g:if test="${headListValues.documentItem.isHeader }" >
    							<div class="fullWidth fl">
									<h4>${headListValues.documentItem.formElementName }</h4> </div>
									</g:if>
									<g:else>
									<div class="fullWidth fl">
									<div class="widthHalf fl">
									${headListValues.documentItem.formElementName } </div>:
									${headListValues.formElementValue }
									</div>
									</g:else>
						</g:elseif>
					</g:each>
    			</div>
    			<div class="width33Percent fl">
    				<g:each in="${documentItemValueMap.get(Listing) }" var="headListValues">
    					<g:if test="${headListValues.type=='Image' && headListValues.alignment=='Center' }">
    						<p >
    						<g:if test="${leftValues.documentItem.isSignature }" >${leftValues.documentItem.formElementName } :</g:if> 
    						<g:if test="${showImage }">
    						<img src="${resource(dir:'DocumentImages',file:headListValues.formElementValue) }" height="40px" width="100px" />
    						</g:if>
    						</p>
    					</g:if>
    					<g:elseif test="${headListValues.type=='Text' && headListValues.alignment=='Center'}">
    							<g:if test="${headListValues.documentItem.isHeader }" >
    							    <div class="fullWidth fl">
									<h4>${headListValues.documentItem.formElementName }</h4> </div>
									</g:if>
									<g:else>
									<div class="fullWidth fl">
									<div class="widthHalf fl">
									${headListValues.documentItem.formElementName } </div>:
									${headListValues.formElementValue }
									</div>
									</g:else>
						</g:elseif>
					</g:each>
    			</div>
    			
    			<div class="width33Percent  fr">
    				<g:each in="${documentItemValueMap.get(Listing) }" var="headListValues">
    					<g:if test="${headListValues.type=='Image' && headListValues.alignment=='Right' }">
    						<p >
    						<g:if test="${leftValues.documentItem.isSignature }" >${leftValues.documentItem.formElementName } :</g:if> 
    						<g:if test="${showImage }">
    						<img src="${resource(dir:'DocumentImages',file:headListValues.formElementValue) }" height="40px" width="100px"  />
    						</g:if>
    						</p>
    					</g:if>
    					<g:elseif test="${headListValues.type=='Text' && headListValues.alignment=='Right' }">
    						
    							<g:if test="${headListValues.documentItem.isHeader }" >
    							<div class="fullWidth fl">
									<h4>${headListValues.documentItem.formElementName }</h4> </div>
									</g:if>
									<g:else>
									<div class="fullWidth fl">
									<div class="widthHalf fl">
									${headListValues.documentItem.formElementName } </div>:
									${headListValues.formElementValue }
									</div>
									</g:else>
						</g:elseif>
				</g:each>
    		</div>
    	</div>
    </g:if>
</g:if>
    		
    		
    		
    		
   		

<g:if test="${documentSubSection=='2' }">
	<g:each status="itemCount" in="${documentItemValueMap.get(Listing) }" var="headListValues">
		<g:if test="${headListValues.type=='Table' }">
		<g:set var="rowCounter"  value="${1 }"></g:set>
			<tr>
    			<td>
      				<table width="100%"  border="1">
      					<thead>
							<tr>
								<g:each status="tableHeader" in="${headListValues.documentItem.headers.sort{it.id} }" var="header">
									
										<td style="min-width:20px;">${header.name }</td>
								</g:each>
							</tr>
						</thead>
					<tbody>
					<g:each status="number" in="${headListValues.documentItem.questions.sort{it.id} }" var="question">
					<tr >
						<td width="25px">
							<div style="min-height:12px">
								<g:if test="${question.question!='' }">
									${question.serialNo }
								</g:if>
							</div>
						</td>
						<td><g:if test="${question.isHeader==true }">
								<h4>${question.question }</h4>
							</g:if>
							<g:else>
								${question.question }
							</g:else> </td>
						<g:if test="${question.isHeader==false && question.question!='' }">
							<g:each status="count" in="${headListValues.questionValues.sort{it.id} }" var="answer">
								<g:if test="${answer.question==question }">
									<g:if test="${answer.checkpointType=='CheckBox'  }">
					 					<g:if test="${answer.questionValue!= ''}">
					 						<td><img src="${resource(dir:'images',file:'checkboxTick.png') }" /> ${question.question }</td>
										</g:if>
										<g:else>
					 						<td><img src="${resource(dir:'images',file:'checkbox.png') }" /> ${question.question }</td>
										</g:else>
					 				</g:if>
					 				<g:elseif test="${answer.checkpointType=='RadioButton'  }">
										<g:if test="${answer.questionValue!='' }">
					 						<td><img src="${resource(dir:'images',file:'radiobuttonCheck.png') }" /> ${question.question }</td>
										</g:if>
										<g:else>
					 						<td><img src="${resource(dir:'images',file:'radioButton.png') }" /> ${question.question }</td>
										</g:else>					 
									</g:elseif>
					 				<g:elseif test="${answer.checkpointType=='TextBox'  }">
					 					<td>${answer.questionValue }</td>
					 				</g:elseif>
								</g:if>	
							</g:each>
						</g:if>
						<g:else>
							<g:each status="headerCount" in="${headListValues.documentItem.headers.sort{it.id} }" var="header">
								<g:if test="${headerCount< headListValues.documentItem.headers.size()-2}">
									<td ></td>
								</g:if>

							</g:each>
						</g:else>
					</tr>
					</g:each>
				</tbody>
			</table>
		</td>
	</tr>
</g:if>

		<g:if test="${headListValues.type=='CheckPoint' }">
			<tr><td><div class="pdfOuter  fl"> 
				<div class="fl marginRight"><h4>${headListValues.documentItem.formElementName}:</h4></div>
					<g:each status="bodyCount" in="${headListValues.questionValues.sort{it.id}  }" var="questionAnswer">
						<g:if test="${headListValues.documentItem.isDeleted==false }">
							 <g:if test="${headListValues.documentItem.checkpointType=='CheckBox'  }">
					 			<g:if test="${questionAnswer.questionValue!= '' }">
					 				<span class="fl"><img src="${resource(dir:'images',file:'checkboxTick.png') }" /><div class="fr marginRight"> ${questionAnswer.question.question }</div></span>
								</g:if>
								<g:else>
					 				<span  class="fl"><img src="${resource(dir:'images',file:'checkbox.png') }" /><div class="fr marginRight"> ${questionAnswer.question.question }</div></span>
								</g:else>
					 		</g:if>
					 		<g:elseif test="${headListValues.documentItem.checkpointType=='RadioButton'  }">
								<g:if test="${questionAnswer.questionValue!= ''}">
					 				<span  class="fl"><img src="${resource(dir:'images',file:'radiobuttonCheck.png') }" /> <div class="fr marginRight">${questionAnswer.question.question }</div></span>
								</g:if>
								<g:else>
					 				<span  class="fl"><img src="${resource(dir:'images',file:'radioButton.png') }" /><div class="fr marginRight"> ${questionAnswer.question.question }</div></span>
								</g:else>					 
							</g:elseif>
					 		<g:else>
					 				<div class="marginRight fl">   ${questionAnswer.question.question } : ${questionAnswer.questionValue }</div>
					 		</g:else>
       					</g:if>
       				</g:each>
       		</div></td></tr>
       	</g:if>
	</g:each>
</g:if>