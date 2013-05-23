<!DOCTYPE html>
    <%@page import="com.kam.QuestionValue"%>
<%@page import="com.kam.Question"%>
<%@ page contentType="text/html;charset=UTF-8"%>
    <%@ page import="com.kam.Customer" %>
    <%@ page import="com.kam.User" %>
<html>
<head>
<style>

 .pdfOuter{
 	width:98.4%; 
 	padding: 5px 5px; 
 }   			
.fullWidth{
	width:100%;
	float:left;
}
.width33Percent{
	width: 33%;

}
.fl {
	float: left;
}

.fr {
	float: right;
}
.textCenter{
	text-align: center;
}
.textRight{
	text-align: right;
}
.textLeft{
text-align:left;
}
.fourSideBorder{
	border: 1px solid #666;
}

.marginTopBottom{
	margin:5px 0;
}

.widthHalf{
	width: 48.4%;
}

.checkboxRadio{
	width:16px;
	height:16px;
	margin: 4px 0;
	text-indent: -999px;
	overflow: hidden;
}
.checkBox{
	background: url(../images/css_sprite.png) -239px -84px no-repeat;
}

.checkBoxChecked{
	background: url(../images/css_sprite.png) -218px -84px no-repeat;
}
.radioButton{
	background: url(../images/css_sprite.png) -260px -84px no-repeat;
}
.radioButtonChecked{
	background: url(../images/css_sprite.png) -282px -84px no-repeat;
}
.displayNone{
	display:none;
}
.commonBox {
	width: 100%;
	float: left;
	padding: 20px 0;
	background: #fff;
}
.marginRight{
	margin-right:15px;
}
p, h4{
margin:0;
}
thead {display: table-header-group;}
table {
            -fs-table-paginate: paginate;
            
        }

</style>
<r:layoutResources />
</head>
<!-- The form below takes input from user and saves it -->
<body>

		<div class="${customerListClass}">
			<div class="commonBox">

				<div class="${docDetailsClass}">
				
     <!-- Title Element display starts here -->
     <table width="100%" class="fourSideBorder">
     <thead>
     <tr><th>
     
     
     <!-- Title Element display starts here -->
     
     <g:if test="${documentItemValueMap.get("titleLeftList").size()>0 || documentItemValueMap.get("titleCenterList").size()>0 || documentItemValueMap.get("titleRightList").size()>0}">
      <div class=" pdfOuter fourSideBorder marginTopBottom fl">

    			<div class="fl width33Percent textLeft">
    				<g:each in="${documentItemValueMap.get("titleLeftList") }" var="leftValues">
    					<g:if test="${leftValues.type=='Image' && leftValues.alignment=='Left'}">
    						<img src="${resource(dir:'DocumentImages',file:leftValues.formElementValue) }" height="40px" width="100px"/>
    					</g:if>
    					<g:elseif test="${leftValues.type=='Text' && leftValues.alignment=='Left' }">
							<g:if test="${leftValues.documentItem.isHeader }" >
								<p ><h4>${leftValues.documentItem.formElementName }</h4> </p>
							</g:if>
							<g:else>
								<p>${leftValues.documentItem.formElementName } :
									${leftValues.formElementValue }</p>
							</g:else>
						</g:elseif>
    				</g:each>
    			</div>
    		
    			<div class="fl width33Percent textCenter">
    				<g:each in="${documentItemValueMap.get("titleCenterList") }" var="centerValues">
    					<g:if test="${centerValues.type=='Image' && centerValues.alignment=='Center'}">
    						<img src="${resource(dir:'DocumentImages',file:centerValues.formElementValue) }" height="40px" width="100px" />
    					</g:if>
    					<g:elseif test="${centerValues.type=='Text' && centerValues.alignment=='Center'}">
							<g:if test="${centerValues.documentItem.isHeader }" >
    							
									<p ><h4>${centerValues.documentItem.formElementName }</h4> </p>
									</g:if>
									<g:else>
									
									<p>${centerValues.documentItem.formElementName } :
									${centerValues.formElementValue }</p>
									</g:else>
						</g:elseif>
    				</g:each>
    			</div>
    		
    			<div class="fr width33Percent textRight">
    				<g:each in="${documentItemValueMap.get("titleRightList") }" var="rightValues">
    					<g:if test="${rightValues.type=='Image' && rightValues.alignment=='Right'}">
    						<img src="${resource(dir:'DocumentImages',file:rightValues.formElementValue) }" height="40px" width="100px"  />
    					</g:if>
    					<g:elseif test="${rightValues.type=='Text'  && rightValues.alignment=='Right'}">
							<g:if test="${rightValues.documentItem.isHeader }" >
    							
									<p ><h4>${rightValues.documentItem.formElementName }</h4> </p>
									</g:if>
									<g:else>
									
									<p>${rightValues.documentItem.formElementName } :
									${rightValues.formElementValue }</p>
									</g:else>
						</g:elseif>
    				</g:each>
    			</div>
    	  </div>		
   		</g:if>        
       

    </th>
    </tr>
    </thead>
    
    
     <!-- Title Element display ends here -->
  <tbody>
  
  <!-- Logo Element starts here -->
     <g:if test="${documentItemValueMap.get('logoList').size()>0}">
     <tr>
     	<td>
     	<div class="pdfOuter marginTopBottom fl">
     	<g:each in="${documentItemValueMap.get('logoHeaderList')}" var="header">
     		<div class="fullWidth">
     		<h2> ${header.formElementName}</h2>
     		</div>
     	</g:each>
     	<g:each in="${documentItemValueMap.get('logoList') }" var="logoListValues">
     		<g:if test="${logoListValues.type=='Image' }">
     		<g:if test="${showImage }">
    			<img src="${resource(dir:'DocumentImages',file:logoListValues.formElementValue) }" height="100px" width="180px" />
    			</g:if>
    		</g:if>
    		<g:elseif test="${logoListValues.type=='Text'}">
				<h2>${logoListValues.documentItem.formElementName } : ${logoListValues.formElementValue }</h2>
			</g:elseif>
     	</g:each>
     	</div>
     	</td>
     	</tr>
     </g:if>
     <!-- Logo Element ends here -->
  
     
     <!-- Project details 1 section start here -->
<g:if test="${documentItemValueMap.get('details1List').size()>0  }">

<tr><td>
    		 <g:render template="/document/documentSection" model="[documentItemValueMap:documentItemValueMap,Listing:'details1List',headerList:'details1HeaderList',documentSubSection:'1',fourSideBorderClass:documentItemValueMap.get('details11BorderClass')]"></g:render>
    		</td>
    		</tr>
    		<g:render template="/document/documentSection" model="[documentItemValueMap:documentItemValueMap,Listing:'details1List',headerList:'details1HeaderList',documentSubSection:'2',fourSideBorderClass:documentItemValueMap.get('details12BorderClass')]"></g:render>
    		
    		<!-- Table and checkpoints g:each code starts here -->
    		 
    </g:if>
   
<!-- Project details 1 section end here -->
     
      <!-- Project details 2 section start here -->
<g:if test="${documentItemValueMap.get('details2List').size()>0}">
<g:if test="${documentItemValueMap.get('details21BorderClass') != '' }">
<tr><td>
    		 <div class=" pdfOuter fourSideBorder marginTopBottom fl">
    			<div class="fullWidth fl">
    				<g:each in="${documentItemValueMap.get('details2List') }" var="headListValues">
    					<g:if test="${headListValues.type=='Image' }">
    					
    					
    						<p >
    						<g:if test="${headListValues.documentItem.isSignature }" >${headListValues.documentItem.formElementName } :</g:if> 
    						<g:if test="${showImage }">
    						<img src="${resource(dir:'DocumentImages',file:headListValues.formElementValue) }" height="40px" width="100px" />
    						</g:if>
    						</p>
    					</g:if>
    					<g:elseif test="${headListValues.type=='Text' }">
    						<div class="fullWidth fl">
    						<g:if test="${headListValues.documentItem.isHeader }" >
    							<div class="fl">
									<h4>${headListValues.documentItem.formElementName }</h4> </div>
									</g:if>
									<g:else>
									<div class="fl">
									${headListValues.documentItem.formElementName } </div>
									${headListValues.formElementValue }
									</g:else>
								</div>
						</g:elseif>
					</g:each>
    			</div>
    			
    			
    		</div>
   		</td>
	</tr>
</g:if>
 		<!-- Table and checkpoints g:each code starts here -->
    		<g:render template="/document/documentSection" model="[documentItemValueMap:documentItemValueMap,Listing:'details2List',headerList:'details2HeaderList',documentSubSection:'2',fourSideBorderClass:documentItemValueMap.get('details22BorderClass')]"></g:render>

    </g:if>
   
<!--Project details 1 section end here -->     
    
     
<!-- Other section start here -->
<g:if test="${ documentItemValueMap.get('otherList').size()>0}">

		<tr><td>
    		 <g:render template="/document/documentSection" model="[documentItemValueMap:documentItemValueMap,Listing:'otherList',headerList:'otherHeaderList',documentSubSection:'1',fourSideBorderClass:documentItemValueMap.get('other1BorderClass')]"></g:render>
   		</td>
    		</tr>
    		
    		<!-- Table and checkpoints g:each code starts here -->
    		<g:render template="/document/documentSection" model="[documentItemValueMap:documentItemValueMap,Listing:'otherList',headerList:'otherHeaderList',documentSubSection:'2',fourSideBorderClass:documentItemValueMap.get('other2BorderClass')]"></g:render>
    		 
    </g:if>
   
<!-- Other section end here -->
<!-- remarks section start here -->
<g:if test="${ documentItemValueMap.get('remarksList').size()>0}">
<g:if test="${documentItemValueMap.get('remarks1BorderClass') != '' }">
<tr>
	<td>
    	 <div class=" pdfOuter fourSideBorder marginTopBottom fl">
    			<div class="widthHalf fl">
    				<g:each in="${documentItemValueMap.get('remarksList') }" var="headListValues">
    					<g:if test="${headListValues.type=='Image' && headListValues.alignment=='Left' }">
    						<p >
    						<g:if test="${headListValues.documentItem.isSignature }" >${headListValues.documentItem.formElementName } :</g:if> 
    						<g:if test="${showImage }">
    						<img src="${resource(dir:'DocumentImages',file:headListValues.formElementValue) }" height="40px" width="100px" />
    						</g:if>
    						</p>
    					</g:if>
    					<g:elseif test="${headListValues.type=='Text' && headListValues.alignment=='Left'}">
    						
    						<g:if test="${headListValues.documentItem.isHeader }" >
    							<div class="fullWidth fl">
									<h4>${headListValues.documentItem.formElementName }</h4> 
								</div>
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
    			
    			<div class="widthHalf  fr">
    				<g:each in="${documentItemValueMap.get('remarksList') }" var="headListValues">
    					<g:if test="${headListValues.type=='Image' && headListValues.alignment=='Right' }">
    						<p >
    						<g:if test="${headListValues.documentItem.isSignature }" >${headListValues.documentItem.formElementName } :</g:if> 
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
   		</td>
	</tr>
</g:if> 		
    		<!-- Table and checkpoints g:each code starts here -->
        		<g:render template="/document/documentSection" model="[documentItemValueMap:documentItemValueMap,Listing:'remarksList',headerList:'remarksHeaderList',documentSubSection:'2',fourSideBorderClass:documentItemValueMap.get('remarks2BorderClass')]"></g:render>
    
    </g:if>
   <!-- remarks section end here -->

     
     
     <!-- Body Element display starts here -->

<g:if test="${documentItemValueMap.get('bodyList').size()>0}">
<g:if test="${documentItemValueMap.get('body1BorderClass') != '' }">
	<tr>
		<td>
    		 <div class=" pdfOuter fourSideBorder marginTopBottom fl">
    			<div class="widthHalf fl">
    				<g:each in="${documentItemValueMap.get('bodyList') }" var="headListValues">
    					<g:if test="${headListValues.type=='Image' && headListValues.alignment=='Left' }">
    					
    					
    						<p >
    						<g:if test="${headListValues.documentItem.isSignature }" >${headListValues.documentItem.formElementName } :</g:if> 
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
    			
    			<div class="widthHalf  fr">
    				<g:each in="${documentItemValueMap.get('bodyList') }" var="headListValues">
    					<g:if test="${headListValues.type=='Image' && headListValues.alignment=='Right' }">
    						<p >
    						<g:if test="${headListValues.documentItem.isSignature }" >${headListValues.documentItem.formElementName } :</g:if> 
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
   		</td>
	</tr>
</g:if>
    		<!-- Table and checkpoints g:each code starts here -->
    		  <g:render template="/document/documentSection" model="[documentItemValueMap:documentItemValueMap,Listing:'bodyList',headerList:'bodyHeaderList',documentSubSection:'2',fourSideBorderClass:documentItemValueMap.get('body2BorderClass')]"></g:render>
       			<!-- Table and checkpoints g:each code ends here -->
 
    </g:if>
  
     <!-- Body Element display ends here -->
     
      <!-- Footer Element display starts here -->
      	<g:if test="${documentItemValueMap.get("footList").size()>0}">
        	<g:render template="/document/documentSection" model="[documentItemValueMap:documentItemValueMap,Listing:'footList',headerList:'footHeaderList',documentSubSection:'2',fourSideBorderClass:'']"></g:render>
        </g:if>
     
      	<g:if test="${documentItemValueMap.get("footList").size()>0}">
     	<tr><td> 
     		<div class=" pdfOuter fourSideBorder marginTopBottom fl">

    			<div class="fl ${footerClass }">
    				<g:each in="${documentItemValueMap.get("footList") }" var="leftValues">
    					<g:if test="${leftValues.type=='Image' && leftValues.alignment=='Left'}">
    						<div class="fullWidth fl">
    							<g:if test="${leftValues.documentItem.isSignature }" ><div class="widthHalf fl"> ${leftValues.documentItem.formElementName } </div>:</g:if>
    							<g:if test="${showImage }"> 
    								<img src="${resource(dir:'DocumentImages',file:leftValues.formElementValue) }" height="40px" width="100px"/>
    							</g:if>
    						</div>
    					</g:if>
    					<g:elseif test="${leftValues.type=='Text' && leftValues.alignment=='Left' }">
    					<g:if test="${leftValues.documentItem.isHeader }" >
    							<div class="fullWidth fl">
									<h4>${leftValues.documentItem.formElementName }</h4> </div>
									</g:if>
									<g:else>
									<div class="fullWidth fl">
									<div class="widthHalf fl">
									${leftValues.documentItem.formElementName } </div>:
									${leftValues.formElementValue }
									</div>
									</g:else>
							
						</g:elseif>
    				</g:each>
    			</div>
    		
    		<g:if test="${documentItemValueMap.get("footerCenterList")!=null }">
    			<div class="fl ${footerClass } textLeft">
    				<g:each in="${documentItemValueMap.get("footList") }" var="centerValues">
    					<g:if test="${centerValues.type=='Image' && centerValues.alignment=='Center'}">
    						<div class="fullWidth fl">
    						<g:if test="${leftValues.documentItem.isSignature }" ><div class="widthHalf fl"> ${leftValues.documentItem.formElementName } </div>:</g:if> 
    						<g:if test="${showImage }">
    						<img src="${resource(dir:'DocumentImages',file:centerValues.formElementValue) }" height="40px" width="100px" />
    						</g:if>
    						</div>
    					</g:if>
    					<g:elseif test="${centerValues.type=='Text' && centerValues.alignment=='Center'}">
							<g:if test="${centerValues.documentItem.isHeader }" >
    							<div class="fullWidth fl">
									<h4>${centerValues.documentItem.formElementName }</h4> </div>
									</g:if>
									<g:else>
									<div class="fullWidth fl">
									<div class="widthHalf fl">
									${centerValues.documentItem.formElementName } </div>:
									${centerValues.formElementValue }
									</div>
									</g:else>
						</g:elseif>
    				</g:each>
    			</div>
    		</g:if>
    			<div class="fr ${footerClass }">
    				<g:each in="${documentItemValueMap.get("footList") }" var="rightValues">
    					<g:if test="${rightValues.type=='Image' && rightValues.alignment=='Right'}">
    						<div class="fullWidth fl">
    						<g:if test="${rightValues.documentItem.isSignature }" > <div class="widthHalf fl"> ${rightValues.documentItem.formElementName } </div>:</g:if> 
    							<g:if test="${showImage }">
    								<img src="${resource(dir:'DocumentImages',file:rightValues.formElementValue) }" height="40px" width="100px"  />
    							</g:if>
    						</div>
    					</g:if>
    					<g:elseif test="${rightValues.type=='Text'  && rightValues.alignment=='Right'}">
							<g:if test="${rightValues.documentItem.isHeader }" >
    							<div class="fullWidth fl">
									<h4>${rightValues.documentItem.formElementName }</h4> </div>
									</g:if>
									<g:else>
									<div class="fullWidth fl">
									<div class="widthHalf fl">
									${rightValues.documentItem.formElementName } </div>:
									${rightValues.formElementValue }
									</div>
									</g:else>
						</g:elseif>
    				</g:each>
    			</div>
    	  </div>
    	  
    	  </td></tr>
   		</g:if>    
     
     <!-- Footer Element display ends here -->
     
     <!-- Notes element display starts here -->
     <g:if test="${documentItemValueMap.get("notesList").size()>0}">
      
     <tr><td> <div class="pdfOuter fourSideBorder marginTopBottom fl"> 
      <g:each in="${documentItemValueMap.get("notesList") }" var="notesListValues">
       <g:if test="${notesListValues.type=='Text' }">
       <g:if test="${notesListValues.documentItem.isHeader }" >
    							<div class="fullWidth fl">
									<p ><h4>${notesListValues.documentItem.formElementName }</h4> </p></div>
									</g:if>
									<g:else>
									<div class="fullWidth fl">
									${notesListValues.documentItem.formElementName } :
									
									${notesListValues.formElementValue }
									</div>
									</g:else>
   </g:if>
   <g:elseif test="${notesListValues.type=='Image' }">
   <g:if test="${showImage }">
    <img src="${resource(dir:'DocumentImages',file:notesListValues.formElementValue) }" height="40px" width="100px" />
    </g:if>
   </g:elseif>
      </g:each>
     </div></td></tr>
     </g:if>
     <g:each in="${attachments }" var="attachment">
     	<g:if test="${showImage }">
     		<tr>
     			<td>
     				<img src="${resource(dir:'DocumentImages',file:attachment.name) }" height="400px" width="400px" />
     			</td>
     		</tr>
     	</g:if>
     </g:each>
      </tbody>
     <!-- Notes element display ends here -->
     <!-- Static footer starts here -->
     <tfoot>
     <tr><td>
     <div class="fullWidth fl">
     <div class="widthHalf fl">
     <div class="fullWidth fl">
    	${document.name } 
	 </div>
     </div>
     <div class="widthHalf fr">
     <div class="fullWidth textRight">
    	${document.documentNumber } 
	 </div>
     </div>
     </div>
     </td></tr>
     </tfoot>
     <!-- Static footer ends here -->
   
      </table>
     </div>
    
    </div>
    </div>


   </body>
    </html>