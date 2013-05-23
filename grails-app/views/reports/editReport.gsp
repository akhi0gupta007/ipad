<!doctype html>

<%@page import="com.kam.EquipmentCheckpoint"%>
<%@page import="com.kam.User"%>
<%@page import="com.kam.UserRole"%>


<html>
<head>
<meta name='layout' content='main' />
<title><g:message code="title.message"></g:message></title>
<style type="text/css">
.commonBox {
	width: 100%;
	float: left;
	padding: 20px 0;
	background: #fff;
}
.width33Percent{
	width: 33%;

}

.tableFontOne{
font:bold 18px/30px Verdana, Geneva, sans-serif;
}
.tableFontTwo{
font:bold 14px/20px Verdana, Geneva, sans-serif;
}
.tableFontThree{
font:bold 12px Verdana, Geneva, sans-serif;
}
.addressMargin{
margin:0 0 0 400px;
}
.paddingTop {
	padding: 10px 0 !important;
}
.widthEighteen{
	width:18%
}
.width82 {
	width: 82%;
}
.spanPadding{
	padding:0 5px 0 0;
}
.textPadding{
padding:5px;
}
.bottomBorder{
	border-bottom:2px solid #000;
}
.marginLeftRight{
margin:0 20px;
}
.marginRight{
	margin-right:15px;
}
.infoBox{
	width:30px;
	height: 15px;
	padding:5px;
	background:#666;
}
.textUnderLine{
text-decoration: underline;
}
 ul.reportUl{
 
 list-style:disc;
 }
table.mainTable{
	width:98%;
	padding:0 1%;
}
</style>
</head>

<body>
<g:render template="/layouts/navigation" model="[loggedInUserRole:role,loggedinUser:loggedinUser]"></g:render>
<ul class="backUl">
	<li class="fr paddingRight"><a href="javascript:history.go(-1)" class="back"><span><g:message code="login.customer.back"/></span></a></li>
 </ul>
	<div class="customerList">
		<div class="commonBox">
			<div class="docDetails">        
			<g:form url="[controller:'reports',action:'saveReportEdits']"> 
			<input type="hidden" value="${report.id }" name="reportId" />  
				<table class="fourSideBorder mainTable">
					<tr><td class="fullWidth ">
							<div class="fr width20Percent marginBottomReport">
								<p class="textPadding"><g:message code="messages.report.project.number"/> :${report.projectNumber } </p>
								<p class="textPadding"><g:message code="messages.report.date"/> : ${reviewDate }</p>
							</div>
							</td>
							</tr>
							<tr>
						<td class="fullWidth">
							
							<p class="tableFontOne"> ${report.building.project.projectTitle } </p>
							<p class="paddingTop"><g:message code="messages.report.headpage.title.3"/></p>
							<p class="paddingTop">${report.building.project.projectName }</p>
							<p class="paddingTop">${report.building.project.address }</p>
							<p class="paddingTop"><g:message code="messages.report.headpage.title.6"/></p>
							<p class="paddingTop">${report.building.project.city }</p>
						</td>
					</tr>
					<tr>
						<td class="fullWidth">
							<p class="addressMargin"><span class="tableFontTwo">${report.building.project.customer.name }</span></p>
							<!-- <p class="addressMargin">Labradordreef 18</p>-->
							<p class="addressMargin">${report.building.project.customer.address }</p>
							<p class="addressMargin"><g:message code="messages.report.contact"/>: ${report.building.project.customer.contact }</p>
							<p class="addressMargin"><g:message code="messages.report.fax"/>: </p>
						</td>
					</tr>
					<tr>
						<td><div class="page-break"></div></td>
					</tr>
					<tr>
						<td class="fullWidth bottomBorder paddingTop">
							<div class="fullWidth">
								<div class="width20Percent fl tableFontThree textPadding">
								
									<g:message code="list.project" /><span class="fr spanPadding">:</span>
								</div>
								<div class="width75Percent textPadding">
									 ${report.projectName },${report.building.project.address }
								</div>
							</div>
							<div class="fullWidth">
								<div class="width20Percent fl tableFontThree textPadding">
									<g:message code="messages.report.project.number.code" /><span class="fr spanPadding">:</span>
								</div>
								<div class="width75Percent textPadding">
									 ${report.projectNumber }
								</div>
							</div>
							<div class="fullWidth">
								<div class="width20Percent fl tableFontThree textPadding">
									<g:message code="messages.report.project.title.code" /><span class="fr spanPadding">:</span>
								</div>
								<div class="width75Percent textPadding">
									 ${report.building.project.projectTitle }
								</div>
							</div>
							<div class="fullWidth">
								<div class="width20Percent fl tableFontThree textPadding">
									<g:message code="messages.document.pdf.date"/><span class="fr spanPadding">:</span>
								</div>
								<div class="width75Percent textPadding">
									 <g:formatDate format="dd/MM/yyyy" date="${report.dateCreated }"/>
								</div>
							</div>
							<div class="fullWidth">
								<div class="width20Percent fl tableFontThree textPadding">
									<g:message code="messages.report.doc.name" /> <span class="fr spanPadding">:</span>
								</div>
								<div class="width75Percent textPadding">
									${report.reportName }
								</div>
							</div>
							<div class="fullWidth">
								<div class="width20Percent fl tableFontThree textPadding">
									<g:message code="messages.report.reviewedby.name" /> <span class="fr spanPadding">:</span>
								</div>
								<div class="width75Percent textPadding">
									${report.reviewedBy }
								</div>
							</div>
						</td>
					</tr>
					
					<tr>
						<td class="fullWidth textPadding">
							<g:message code="messages.report.introduction.text2" />
						</td>
					</tr>
					<tr>
						<td class="fullWidth textPadding">
							<g:message code="messages.report.introduction.text3" />
						</td>
					</tr>
					<tr>
						<td class="fullWidth textPadding">
							<g:message code="messages.report.introduction.text4" />
							<g:each in="${report.building.floors }" var="floor">
								<p><div  class="width10 fl ">${floor.floorNumber }</div>:<g:formatDate date="${floor.revisionDate }" format="dd/MM/yyyy"/></p>
							</g:each>
						</td>
					</tr>
					
					
					<tr>
						<td class="fullWidth ">
							<p class="tableFontThree"><g:message code="messages.report.end.result" /></p>
								<div class="textPadding marginLeftRight fl fullWidth">
									<div class="fl textPadding"><g:message code="messages.report.number.verified" /> </div><div class="fl textPadding">${totalEquipments.size() }</div>
								</div>
								<div class="textPadding marginLeftRight fl fullWidth">
									<div class="fl textPadding"><g:message code="messages.report.number.rejected" /> </div><div class="fl textPadding">${totalEquipmentsRejected.size() }</div>
								</div>
								
								
								
								<div class="textPadding marginLeftRight fl fullWidth">
									<div class="fl textPadding"><g:message code="messages.report.waarvan" />: </div>
								</div>
								<div class="textPadding marginLeftRight fl fullWidth">
									<div class="fl textPadding"><g:message code="messages.report.indication.armatures" />: </div><div class="fl textPadding">${totalEquipmentsIndication.size() }</div>
								</div>
								<div class="textPadding marginLeftRight fl fullWidth">
									<div class="fl textPadding"><g:message code="messages.report.lighting.armatures" />: </div><div class="fl textPadding">${totalEquipmentsLighting.size() }</div>
								</div>
					
						</td>
					</tr>
					
					<tr>	
							<td class="fullWidth ">	
									<p class="tableFontThree fullWidth block"><g:message code="message.report.result" /></p>
									<p class="tableFontThree fullWidth block"><g:message code="indication.armature.status" /></p>
								
								
								<div class="fullWidth clear">
									<div class="fl textPadding width25percent"><g:message code="messages.report.indication.armatures.approved" />: </div>
									<div class="fl textPadding width70Percent">${totalEquipmentsIndication.size()  - totalIndicationRejected.size() }</div>
								</div>
								<div class="fullWidth clear">
									<div class="fl textPadding width25percent"><g:message code="messages.report.indication.armatures.rejected" />: </div>
									<div class="fl textPadding width70Percent">${totalIndicationRejected.size() }</div>
								</div>
								
								</td>
							</tr>
							<tr>	
							<td class="fullWidth ">	
								<p class="tableFontThree fullWidth block"><g:message code="lighting.armature.status" /></p>
								
								
								<div class="fullWidth clear">
									<div class="fl textPadding  width25percent"><g:message code="messages.report.lighting.armatures.approved" />: </div>
									<div class="fl textPadding width70Percent">${totalEquipmentsLighting.size() - totalLightingRejected.size() }</div>
								</div>
								<div class="fullWidth clear">
									<div class="fl textPadding  width25percent"><g:message code="messages.report.lighting.armatures.rejected" />: </div>
									<div class="fl textPadding width70Percent">${totalLightingRejected.size() }</div>
								</div>
						</td>
					</tr>
					
					<tr>
						<td><div class="page-break"></div></td>
					</tr>
					<tr>
						<td class="fullWidth ">	
						
							<p class="tableFontThree fullWidth"><g:message code="pie.indication.armature.status" /></p>
						
      
								
								<div class="fullWidth clear">
									<div class="fl textPadding width25percent"><g:message code="messages.report.indication.armatures.approved" />: </div>
									<div class="fl textPadding width70Percent">${totalEquipmentsIndication.size()  - totalIndicationRejected.size() }</div>
								</div>
								<div class="fullWidth clear">
									<div class="fl textPadding width25percent"><g:message code="messages.report.indication.armatures.rejected" />: </div>
									<div class="fl textPadding width70Percent">${totalIndicationRejected.size() }</div>
								</div>
						</td>
						</tr>
					
					<tr>
						<td>
							<g:if test="${totalEquipmentsIndication.size()>0 }">
								<img height="280px" width="380px" src="${resource(dir:'PieChart',file:'piechart1'+'-'+report.id+'.png') }" />
							</g:if>
						</td>
						</tr>
						<tr>
						<td>
							<g:if test="${totalEquipmentsLighting.size()>0 }">
								<img height="280px" width="380px"  src="${resource(dir:'PieChart',file:'piechart2'+'-'+report.id+'.png') }" />
							</g:if>
						</td>
					</tr>
					
					
					<tr>
						<td class="fullWidth ">
							<div class="fullWidth paddingTop">
								<table border="1"  cellspacing="0" cellpadding="0" width="100%">
									<tr>
										<th class="width20Percent">
										<g:message code="message.report.heading" />
										</th>
									<td colspan="2">
									</td>
								</tr>
								<g:each status="functionale" in="${reportCheckpoints.sort{x,y -> x.category<=>y.category ?: x.sno<=>y.sno } }" var="reportCheckpoint">
								<g:if test="${reportCheckpoint.category=='Functional' }" >
									<tr>
									<g:if test="${functionale==0 }" >
										<td class="textCenter" rowspan="${functionalCheckpoints.size() }">
											<g:message code="messages.equipment.functional" />
										</td>
			
			
										</g:if>
										<td class="width5Percent textCenter" >
											${reportCheckpoint.sno }
										</td>
										<td>
											${reportCheckpoint.value }
										</td>
									</tr>
								</g:if>
								</g:each>
								<g:each status="visueleStatus" in="${reportCheckpoints.sort{x,y -> x.category<=>y.category ?: x.sno<=>y.sno } }" var="reportCheckpoint">
								<g:if test="${reportCheckpoint.category=='Visual' }" >
									<tr>
									
										<g:if test="${visueleStatus==functionalCheckpoints.size() }" >
										<td class="textCenter" rowspan="${visualCheckpoints.size() }">
											<g:message code="messages.equipment.visual" />
										</td>
										</g:if>
										<td class="width5Percent textCenter" >
											${reportCheckpoint.sno }
										</td>
										<td>
											${reportCheckpoint.value }
										</td>
									</tr>
								</g:if>
								</g:each>
								</table>
								</div>
								</td>
					<tr>
					
						<td class="fullWidth ">
							<table class="fullWidth textCenter" border="1" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<th></th>
									<th></th>
									<th colspan="${functionalCheckpoints.size() }"><g:message code="messages.equipment.functional" /></th>
									<th colspan="${visualCheckpoints.size() }"><g:message code="messages.equipment.visual" /></th>
								</tr>
								<tr>
									<th><g:message code="component.equipment" /> </th>
									<th><g:message code="view.document.floor" /></th>
									<g:each in="${reportCheckpoints.sort{x,y -> x.category<=>y.category ?: x.value<=>y.value } }" var="checkpoints">
										<th>${checkpoints.sno }</th>
									</g:each>
								</tr>
								<g:each  in="${report.building.floors }" var="floor">
									<g:each status="floorCount" in="${floor.equipments.sort{ it.id } }" var="equipment">
										<g:if test="${equipmentCheckpoints!=null && equipmentCheckpoints.equipment.id.contains(equipment.id) }">
											<tr>
												<td>${equipment.name }</td>
												<td>${equipment.floor.floorNumber }</td>
												<g:each in="${reportCheckpoints.sort{x,y -> x.category<=>y.category ?: x.value<=>y.value } }" var="reportCheckpoint">
												<td>
													<g:each in="${equipmentCheckpoints }" var="checkpoint">
														<g:if test="${checkpoint.equipment.id==equipment.id && checkpoint.checkpointDescription==reportCheckpoint.value }">
															<g:if test="${checkpoint.status=='Afgekeurd' }">
																<img src="${resource(dir:'images',file:'checkboxcross.png') }" />
															</g:if>
															<g:elseif test="${checkpoint.status!='' }">
																<img src="${resource(dir:'images',file:'checkboxTick.png') }" />
															</g:elseif>
															<g:else>
																${checkpoint.status}
															</g:else>
														</g:if>
													</g:each>
												</td>
												</g:each>
											</tr>
										</g:if>
									</g:each>
								</g:each>
							</table>
						</td>
					</tr>
				
				
					<!-- <tr>
						<td class="fullWidth ">
							<p class="tableFontThree paddingTop"><g:message code="messages.report.final.results" /> :</p>
							<p class="tableFontThree "><g:message code="messages.report.functional.result" />:</p>
							<g:each in="${report.building.floors }" var="floor">
								<g:if test="${EquipmentCheckpoint.findAllWhere(floorId:floor.id.toString(),reportId:report.id.toString(),checkpointType:'Functional').size>0 }">
									<p class="textUnderLine paddingTop marginLeftRight">${floor.floorNumber }</p>
								</g:if>
								<g:each in="${floor.equipments }" var="equipment">
									<ul type="circle" class="marginLeftRight reportUl">
										<g:each in="${equipmentCheckpoints }" var="checkpoint">
											<g:if test="${checkpoint && checkpoint.equipment.id==equipment.id && reportCheckpoints.value.contains(checkpoint.checkpointDescription) && checkpoint.comment!=null && checkpoint.comment!='' && checkpoint.checkpointType=='Functional' }">
												<li class="marginLeftRight"> <input type="text" class="field" name="equipmentCheckpoint${checkpoint.id }" value="${checkpoint.comment }" /> </li>
												<g:if test="${checkpoint.equipmentImages.size()>0 }">
													<li class="marginLeftRight">
														<g:each in="${checkpoint.equipmentImages }" var="equipmentImage">
															 <img src="${resource(dir:'ReportImages',file:equipmentImage.imageName) }"  width="150px" height="100px"/>
														</g:each>
													</li>
												</g:if>
											</g:if>
										</g:each>
									</ul>
								</g:each>
								
							
							</g:each>
							
									
						</td>
					</tr>
					<tr>
						<td class="fullWidth ">
							<p class="tableFontThree "><g:message code="messages.report.visual.result" />:</p>
							<g:each in="${report.building.floors }" var="floor">
								<g:if test="${EquipmentCheckpoint.findAllWhere(floorId:floor.id.toString(),reportId:report.id.toString(),checkpointType:'Visual').size>0 }">
									<p class="textUnderLine paddingTop marginLeftRight">${floor.floorNumber }</p>
								</g:if>
								<g:each in="${floor.equipments }" var="equipment">
									<ul type="circle" class="marginLeftRight reportUl">
										<g:each in="${equipmentCheckpoints }" var="checkpoint">
											<g:if test="${checkpoint && checkpoint.equipment.id==equipment.id  && reportCheckpoints.value.contains(checkpoint.checkpointDescription) && checkpoint.comment!=null && checkpoint.comment!='' && checkpoint.checkpointType=='Visual' }">
												<li class="marginLeftRight"><input type="text" class="field" name="equipmentCheckpoint${checkpoint.id }" value="${checkpoint.comment }" /> </li>
												<g:if test="${checkpoint.equipmentImages.size()>0 }">
													<li class="marginLeftRight">
														<g:each in="${checkpoint.equipmentImages }" var="equipmentImage">
															 <img src="${resource(dir:'ReportImages',file:equipmentImage.imageName) }"  width="150px" height="100px"/>
														</g:each>
													</li>
												</g:if>
											</g:if>
										</g:each>
									</ul>
								</g:each>
							</g:each>
						</td>
					</tr>
					<tr>
						<td class="fullWidth ">
							<p class="tableFontThree paddingTop"><g:message code="messages.report.floor.analysis" />:</p>
							<g:each in="${floorComments }" var="floorComment">
									<p class="textUnderLine paddingTop marginLeftRight">${floorComment.floor.floorNumber }</p>
									<ul type="circle" class="marginLeftRight reportUl">
									<g:each in="${floorComment.value.toString().split('[.]') }" var="comment">
										<g:if test="${comment.trim()!='' }">
											<li class="marginLeftRight"><input type="text" class="field" name="floorComment${floorComment.floor.id }" value="${comment.trim() }" /> </li>
										</g:if>
									</g:each>
								</ul>
							</g:each>
						</td>
					</tr>
					<tr>
						<td class="fullWidth ">
							<p class="tableFontThree paddingTop"><g:message code="messages.report.conclusion" />:</p>
								<ul type="circle" class="marginLeftRight reportUl">
									<g:each in="${conlusionComments }" var="conlusionComment">
										<g:if test="${conlusionComment.trim()!='' }">
											<li class="marginLeftRight">
											<input type="text" class="field" name="conlusionComment" value="${conlusionComment }" /></li>
										</g:if>	
									</g:each>
								</ul>
							
						</td>
					</tr> -->
					<tr>
						<tr>
						<td class="fullWidth ">
							<p class="tableFontThree paddingTop"><g:message code="messages.report.advice" />:</p>
								<ul type="circle" class="marginLeftRight reportUl">
									<g:each in="${aduiesComments }" var="aduiesComment">
										<g:if test="${aduiesComment.trim()!='' }">
											<li class="marginLeftRight">
											<input type="text" class="field" name="aduiesComment" value="${aduiesComment }" /></li>
										</g:if>	
									</g:each>
								</ul>
						</td>
					</tr>
					<tr>
							<td class="fullWidth ">
					<g:if test="${userSignature!='' || userSignature!=null }">
						
								<div class="widthHalf fl paddingTop">
									<div class="textPadding fl tableFontThree "><g:message code="messages.report.signature2" /></div>
									<div class=" textPadding"><img src="${resource(dir:'ReportImages',file:userSignature) }" height="100px" width="100px"/></div>
								</div>
						
					</g:if>
					<g:if test="${customerSignature!='' || customerSignature!=null }">
						
								<div class="widthHalf fl paddingTop">
									<div class="textPadding fl tableFontThree "><g:message code="messages.report.signature1" /></div>
									<div class="textPadding textPadding"><img src="${resource(dir:'ReportImages',file:customerSignature) }" height="100px" width="100px"/></div>
								</div>
							
					</g:if>
						</td>
						</tr>
				</table>
				<div class="width300 fl marginLeft ">
				<div class="cancelCommonBg fl">
				<g:link controller="reports" action="reportList" >
        			<span class="inputButton fl"><g:message code='users.cancel'/> </span>
        		</g:link>
       			</div>
				<div class="submitCommonBg fr ">
					<input type="submit" class="inputButton fl" value="<g:message code='users.submit'/>" >
				</div>
			</div>
				</g:form>
			</div>
		</div>
	</div>

</body>
</html>