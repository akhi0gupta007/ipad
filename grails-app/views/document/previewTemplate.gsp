<!DOCTYPE html>
    <%@page import="com.kam.QuestionValue"%>
<%@page import="com.kam.Question"%>
<%@ page contentType="text/html;charset=UTF-8"%>
    <%@ page import="com.kam.Customer" %>
    <%@ page import="com.kam.User" %>
<html>
<head> 
<meta name="layout" content="main" />

<link rel="stylesheet" media="screen" href="${resource(dir:'css',file:'kam.css')}" />
<g:javascript library="jquery" plugin="jquery"></g:javascript>
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
</head>
<!-- The form below takes input from user and saves it -->
<body>
<g:if test="${!isPdf }">
	<g:render template="/layouts/navigation" model="[loggedInUserRole:role]"></g:render>  
   	<ul class="backUl">
	   	<li class="fr paddingRight"><g:link controller="document" action="templateList" class="back"><span><g:message code="login.customer.back"/></span></g:link></li>
   	</ul>
</g:if>
		<div class="${customerListClass }">
		
			
			<div class="formHeadingBg fullWidth marginBottom">
			<g:if test="${!isPdf }">
				<h2 class="marginLeft">
					<g:if test="${role!='ROLE_ADMIN' }">
						<g:link controller="document" action="documentInput" class="fontColor" id="${template.id }"><g:message code="template.use.template" /></g:link>
					</g:if>
				</h2>
			</g:if>
			<div class="${docDetailsClass }">
					<g:if test="${!isPdf }">
						
						
						<g:pdfLink pdfController="document" pdfAction="previewTemplate" id="${template.id }">
						<div class=" fr marginRight exportPdf fl userHeading">
						<font color="#000"><g:message code="document.list.export" /> :</font>
						</div>
						</g:pdfLink>
						
						
					</g:if>
			
 <table width="100%" class="fourSideBorder">
     <thead>
     <tr><th>
     
			<!-- Title Element display starts here -->
<g:if test="${documentItemValueMap.get("titleLeftList").size()>0 || documentItemValueMap.get("titleCenterList").size()>0 || documentItemValueMap.get("titleRightList").size()>0}">
      <div class=" pdfOuter fourSideBorder marginTopBottom fl">

    			<div class="fl width33Percent textLeft">
    				<g:each in="${documentItemValueMap.get("titleLeftList") }" var="leftValues">
    					<g:if test="${leftValues.type=='Image' && leftValues.alignment=='Left'}">
    					<g:if test="${showImage }">
    						<img src="${resource(dir:'DocumentImages',file:leftValues.formElementName) }" height="40px" width="100px"/>
    						</g:if>
    					</g:if>
    					<g:elseif test="${leftValues.type=='Text' && leftValues.alignment=='Left' }">
							<g:if test="${leftValues.isHeader }" >
								<p ><h4>${leftValues.formElementName }</h4> </p>
							</g:if>
							<g:else>
								<p>${leftValues.formElementName } :
									</p>
							</g:else>
						</g:elseif>
    				</g:each>
    			</div>
    		
    			<div class="fl width33Percent textCenter">
    				<g:each in="${documentItemValueMap.get("titleCenterList") }" var="centerValues">
    					<g:if test="${centerValues.type=='Image' && centerValues.alignment=='Center'}">
    					<g:if test="${showImage }">
    						<img src="${resource(dir:'DocumentImages',file:centerValues.formElementName) }" height="40px" width="100px" />
    						</g:if>
    					</g:if>
    					<g:elseif test="${centerValues.type=='Text' && centerValues.alignment=='Center'}">
							<g:if test="${centerValues.isHeader }" >
								<p ><h4>${centerValues.formElementName }</h4> </p>
							</g:if>
							<g:else>
								<p>${centerValues.formElementName } :
									</p>
							</g:else>
						</g:elseif>
    				</g:each>
    			</div>
    		
    			<div class="fr width33Percent textRight">
    				<g:each in="${documentItemValueMap.get("titleRightList") }" var="rightValues">
    					<g:if test="${rightValues.type=='Image' && rightValues.alignment=='Right'}">
    					<g:if test="${showImage }">
    						<img src="${resource(dir:'DocumentImages',file:rightValues.formElementName) }" height="40px" width="100px"  />
    						</g:if>
    					</g:if>
    					<g:elseif test="${rightValues.type=='Text'  && rightValues.alignment=='Right'}">
							<g:if test="${rightValues.isHeader }" >
								<p ><h4>${rightValues.formElementName }</h4> </p>
							</g:if>
							<g:else>
								<p>${rightValues.formElementName } :
									</p>
							</g:else>
						</g:elseif>
    				</g:each>
    			</div>
    	  </div>		
   		</g:if>        
    </th>
    </tr>
    </thead>
    <tbody>
     <!-- Title Element display ends here -->
     
     <!-- Logo Element starts here -->
     
    	<g:if test="${documentItemValueMap.get('logoList').size()>0}">
     		<tr>
     		<td>
     		
     		<div class="pdfOuter marginTopBottom fl">
     			<g:each in="${documentItemValueMap.get('logoList') }" var="logoListValues">
     				<g:if test="${logoListValues.type=='Image' }">
    					<img src="${resource(dir:'DocumentImages',file:logoListValues.formElementName) }" height="100px" width="180px" />
    				</g:if>
    				<g:elseif test="${logoListValues.isHeader }">
    					<div class="fullWidth" style="font:bold 12px;">
     						<h1> ${logoListValues.formElementName}</h1>
     					</div>
						</g:elseif>
    				<g:elseif test="${logoListValues.type=='Text'}">
						<h3>${logoListValues.formElementName } : </h3>
					</g:elseif>
     			</g:each>
     		</div>
     		</td>
     		</tr>
     	</g:if>
     <!-- Logo Element ends here -->
    
     <!-- project Element display starts here -->
     <tr>
     	<td>
     		<g:render template="previewSection" model="[Listing:'details1List',width:'widthHalf',elementWidth:'fullWidth',documentSubSection:'1',fourSideBorderClass:documentItemValueMap.get('details11BorderClass')]"></g:render>
     	</td>
     </tr>
     <g:render template="previewSection" model="[Listing:'details1List',width:'widthHalf',elementWidth:'fullWidth',documentSubSection:'2',fourSideBorderClass:documentItemValueMap.get('details12BorderClass')]"></g:render>
     <!-- Project Element display ends here -->
     
     <!-- Details2 Element display starts here -->
      <tr>
     	<td>
     	<g:if test="${documentItemValueMap.get('details21BorderClass') != '' }">
     	
     	<div class=" pdfOuter fourSideBorder marginTopBottom fl">
    			<div class="fullWidth fl">
    				
    		
    				<g:each in="${documentItemValueMap.get("details2List") }" var="headListValues">
    					<g:if test="${headListValues.type=='Image' }">
    					
    					
    						<p >
    						<g:if test="${headListValues.isSignature }" >${headListValues.formElementName } :</g:if> 
    						<g:if test="${showImage }">
    						<img src="${resource(dir:'DocumentImages',file:headListValues.formElementName) }" height="40px" width="100px" />
    						</g:if>
    						</p>
    					</g:if>
    					<g:elseif test="${headListValues.type=='Text' }">
    						<div class="fullWidth fl">
    							<g:if test="${headListValues.isHeader }" >
    							<div class="fullWidth fl">
									<h4>${headListValues.formElementName }</h4> </div>
									</g:if>
									<g:else>
									<div class="fullWidth fl">
									${headListValues.formElementName } </div>
									</g:else>
							</div>
						</g:elseif>
					</g:each>
    			</div>
    			
    			
    		</div>
    	</g:if>
	</td>
</tr>
  	 <g:render template="previewSection" model="[Listing:'details2List',width:'fullWidth',elementWidth:'fullWidth',documentSubSection:'2',fourSideBorderClass:documentItemValueMap.get('details22BorderClass')]"></g:render>
<!-- Details2 Element display ends here -->
     
<!-- Other Element display starts here -->
<tr>
   	<td>
   		<g:render template="previewSection" model="[Listing:'otherList',width:'widthHalf',elementWidth:'fullWidth',documentSubSection:'1',fourSideBorderClass:documentItemValueMap.get('other1BorderClass')]"></g:render>
   	</td>
</tr>
		<g:render template="previewSection" model="[Listing:'otherList',width:'widthHalf',elementWidth:'fullWidth',documentSubSection:'2',fourSideBorderClass:documentItemValueMap.get('other2BorderClass')]"></g:render>
<!-- Other Element display ends here -->
     
<!-- Remarks Element display starts here -->
<tr>
   	<td>
   	<g:if test="${documentItemValueMap.get('remarks1BorderClass') != '' }">
	     <div class=" pdfOuter fourSideBorder marginTopBottom fl">
    			<div class="widthHalf fl">
    				<g:each in="${documentItemValueMap.get("remarksHeaderList")}" var="header">
    					<g:if test="${header.alignment=='Left' }">
     						<h4>${header.formElementName}</h4>	
     					</g:if>
     				</g:each>
    		
    				<g:each in="${documentItemValueMap.get("remarksList") }" var="headListValues">
    					<g:if test="${headListValues.type=='Image' && headListValues.alignment=='Left' }">
    						<p >
    						<g:if test="${headListValues.isSignature }" >${headListValues.formElementName } :</g:if> 
    						<g:if test="${showImage }">
    						<img src="${resource(dir:'DocumentImages',file:headListValues.formElementName) }" height="40px" width="100px" />
    						</g:if>
    						</p>
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
    			
    			<div class="widthHalf  fr">
    				<g:each in="${documentItemValueMap.get("remarksHeaderList")}" var="header">
    					<g:if test="${header.alignment=='Right' }">
     						<h4>${header.formElementName}</h4>
     					</g:if>
     				</g:each>
    				<g:each in="${documentItemValueMap.get("remarksList") }" var="headListValues">
    					<g:if test="${headListValues.type=='Image' && headListValues.alignment=='Right' }">
    						<p >
    						<g:if test="${headListValues.isSignature }" >${headListValues.formElementName } :</g:if> 
    						<g:if test="${showImage }">
    						<img src="${resource(dir:'DocumentImages',file:headListValues.formElementName) }" height="40px" width="100px"  />
    						</g:if>
    						</p>
    					</g:if>
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
     </td>
</tr>
    <g:render template="previewSection" model="[Listing:'remarksList',width:'widthHalf',elementWidth:'fullWidth',documentSubSection:'2',fourSideBorderClass:documentItemValueMap.get('remarks2BorderClass')]"></g:render>
     <!-- Remarks Element display ends here -->
     
<!-- Body Element display starts here -->
<tr>
  	<td>
   	   	<g:if test="${documentItemValueMap.get('body1BorderClass') != '' }">
		    <div class=" pdfOuter fourSideBorder marginTopBottom fl">
    			<div class="widthHalf fl">
    				<g:each in="${documentItemValueMap.get("bodyList") }" var="headListValues">
    					<g:if test="${headListValues.type=='Image' && headListValues.alignment=='Left' }">
    						<p >
    						<g:if test="${headListValues.isSignature }" >${headListValues.formElementName } :</g:if> 
    						<g:if test="${showImage }">
    						<img src="${resource(dir:'DocumentImages',file:headListValues.formElementName) }" height="40px" width="100px" />
    						</g:if>
    						</p>
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
    			
    			<div class="widthHalf  fr">
    				
    				<g:each in="${documentItemValueMap.get("bodyList") }" var="headListValues">
    					<g:if test="${headListValues.type=='Image' && headListValues.alignment=='Right' }">
    						<p >
    						<g:if test="${headListValues.isSignature }" >${headListValues.formElementName } :</g:if> 
    						<g:if test="${showImage }">
    						<img src="${resource(dir:'DocumentImages',file:headListValues.formElementName) }" height="40px" width="100px"  />
    						</g:if>
    						</p>
    					</g:if>
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
   </td>
</tr>
   <g:render template="previewSection" model="[Listing:'bodyList',width:'widthHalf',elementWidth:'fullWidth',documentSubSection:'2',fourSideBorderClass:documentItemValueMap.get('body2BorderClass')]"></g:render>
     <!-- Body Element display ends here -->
     
      <!-- Footer Element display starts here -->
      <g:if test="${documentItemValueMap.get("footList").size()>0}">
         <g:render template="previewSection" model="[Listing:'footList',width:'widthHalf',elementWidth:'fullWidth',documentSubSection:'2',fourSideBorderClass:'']"></g:render>
      </g:if>
      
      
      <g:if test="${documentItemValueMap.get("footList").size()>0}">
  
    <tr><td>
     <div class=" pdfOuter fourSideBorder marginTopBottom fl">

    			<div class="fl ${footerClass }">
    				<g:each in="${documentItemValueMap.get("footList") }" var="leftValues">
    					<g:if test="${leftValues.type=='Image' && leftValues.alignment=='Left'}">
    						<div class="fullWidth fl">
    						<g:if test="${leftValues.isSignature }" ><div class="widthHalf fl">${leftValues.formElementName } </div>:</g:if>
    						<g:if test="${showImage }"> 
    							<img src="${resource(dir:'DocumentImages',file:leftValues.formElementName) }" height="40px" width="100px"/>
    							</g:if>
    							</div>    					
    					</g:if>
    					<g:elseif test="${leftValues.type=='Text' && leftValues.alignment=='Left' }">
							<g:if test="${leftValues.isHeader }" >
							<div class="fullWidth fl">
									<h4>${leftValues.formElementName }</h4> 
									</div>
							</g:if>
							<g:else>
							<div class="fullWidth fl">
								<div class="widthHalf fl"> ${leftValues.formElementName } 
									</div>:
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
    						<g:if test="${centerValues.isSignature }" ><div class="widthHalf fl">${centerValues.formElementName } </div>:</g:if> 
    						<g:if test="${showImage }">
    						<img src="${resource(dir:'DocumentImages',file:centerValues.formElementName) }" height="40px" width="100px" />
    						</g:if>
    						</div>
    					</g:if>
    					<g:elseif test="${centerValues.type=='Text' && centerValues.alignment=='Center'}">
							<g:if test="${centerValues.isHeader }" >
							<div class="fullWidth fl">
									<h4>${centerValues.formElementName }</h4> 
									</div>
							</g:if>
							<g:else>
							<div class="fullWidth fl">
								<div class="widthHalf fl">${centerValues.formElementName } 
									</div>:
									</div>
							</g:else>
						</g:elseif>
    				</g:each>
    			</div>
    		</g:if>
    			<div class="fr ${footerClass }">
    				<g:each in="${documentItemValueMap.get("footList") }" var="rightValues">
    					<g:if test="${rightValues.type=='Image' && rightValues.alignment=='Right'}">
    						<div class="fullWidth fl"><g:if test="${rightValues.isSignature }" ><div class="widthHalf fl">${rightValues.formElementName } </div>: </g:if>
    							<g:if test="${showImage }">
    								<img src="${resource(dir:'DocumentImages',file:rightValues.formElementName) }" height="40px" width="100px"  />
    							</g:if>
    						</div>
    					</g:if>
    					<g:elseif test="${rightValues.type=='Text'  && rightValues.alignment=='Right'}">
							<g:if test="${rightValues.isHeader }" >
							<div class="fullWidth fl">
									<h4>${rightValues.formElementName }</h4> 
									</div>
							</g:if>
							<g:else>
							<div class="fullWidth fl">
								<div class="widthHalf fl">${rightValues.formElementName } 
									</div>:
									</div>
							</g:else>
						</g:elseif>
    				</g:each>
    			</div>
    	  </div>
   </td>
   </tr>
    </g:if>
     <!-- Footer Element display ends here -->
     
     <!-- Notes element display starts here -->
      <g:if test="${documentItemValueMap.get("notesList").size()>0}">
      
    <tr><td>
      <div class="pdfOuter fourSideBorder marginTopBottom fl"> 
     	<g:each in="${documentItemValueMap.get("notesList") }" var="notesListValues">
     		<g:if test="${notesListValues.type=='Text' }">
			    <g:if test="${notesListValues.isHeader }" >
					<div class="fullWidth"><h4>${notesListValues.formElementName }</h4> </div>
				</g:if>
				<g:else>
					<div class="fullWidth">${notesListValues.formElementName } :
					</div>
				</g:else>
			</g:if>
			<g:elseif test="${notesListValues.type=='Image' }">
				<img src="${resource(dir:'DocumentImages',file:notesListValues.formElementName) }" height="40px" width="100px" />
			</g:elseif>
     	</g:each>
     </div>
     </td>
     </tr>
     </g:if> 
     
     <!-- Notes element display ends here -->
     </tbody>
     </table>
     </div>
    </div>
    </div>
   
    </body>
    </html>
