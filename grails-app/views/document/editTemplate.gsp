<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.kam.Customer" %>
<%@ page import="com.kam.User" %>
<html>
<head>

<meta name="layout" content="main" />

</head>
<!-- The form below takes input from user and saves it -->
<body>
	<g:render template="/layouts/navigation" model="[loggedInUserRole:role]"></g:render>
	
 	<!-- back Navigation start from here --> 
   		<ul class="backUl">
   			<li class="fr paddingRight"><g:link controller="document" action="templateList" class="back"><span><g:message code="login.customer.back"/></span></g:link></li>
   		</ul>
 	<!-- back Navigation end here -->  
 	
	<div class="customerList">
  		<div class="newTemplate formHeadingBg">
   			<h2 class="formHead"><g:message code="template.edit"/> : ${template.name} </h2>
  			<div class="inputHeading fullWidth">
     			<g:form id="TemplateForm" name="TemplateForm" controller="document" action="updateTemplate">
					<input type="hidden" value="${userId}" name="userId">
       				<div class="formInput">
         				<label class="fl paddingTop width12"><g:message code="template.template.name"/>:</label>
         				<input type="text" name="name" id="name" value="${template.name}" class="fl field"/>
           			</div>
      		<div class="Elements" id="Elements">
      			<g:set var="elementsCount" value="1"></g:set>
      			<g:each in="${ documentItemList}" var="templateItem">
      			<!-- If the element type is text start -->
					<div class="formInput" id="${templateItem.id }">
						<label class="fl paddingTop width12"><g:message code="template.element.name"/>:</label> 
						<input type="text" class="fl field" name="${templateItem.id }" value="${templateItem.formElementName }">
						<select class="templateSelect" name="typeOld${templateItem.id }" id="typeOld${templateItem.id }" onchange="selectOptions('typeOld${templateItem.id }','Old${templateItem.id }')">
							<g:if test="${templateItem.type=='Text' }">
								<option value="Text"  selected>
									<g:message code="template.element.text"/>
								</option>
							</g:if>
							<g:else>
								<option value="Text"  >
									<g:message code="template.element.text"/>
								</option>
							</g:else>
							<g:if test="${templateItem.type=='Image' }">
								<option value="Image"  selected>
									<g:message code="template.element.image"/>
								</option>
							</g:if>
							<g:else>
								<option value="Image"  >
									<g:message code="template.element.image"/>
								</option>
							</g:else>
							<g:if test="${templateItem.type=='Table' }">
								<option value="Table"  selected>
									<g:message code="template.element.table"/>
								</option>
							</g:if>
							<g:else>
								<option value="Table" >
									<g:message code="template.element.table"/>
								</option>
							</g:else>
							<g:if test="${templateItem.type=='CheckPoint' }">
								<option value="CheckPoint"  selected="selected" >
									<g:message code="template.element.checkpoint"/>
								</option>
							</g:if>
							<g:else>
								<option value="CheckPoint"   >
									<g:message code="template.element.checkpoint"/>
								</option>
							</g:else>
						</select>
						
						<select class="templateSelect" name="alignOld${templateItem.id }" id="alignOld${templateItem.id }">
							<g:if test="${templateItem.alignment=='Left' }">
								<option value="Left" selected>
									<g:message code="template.element.left"/>
								</option>
							</g:if>
							<g:else>
								<option value="Left">
									<g:message code="template.element.left"/>
								</option>
							</g:else>
							<g:if test="${templateItem.alignment=='Right' }">
								<option value="Right" selected>
									<g:message code="template.element.right"/>
								</option>
							</g:if>
							<g:else>
								<option value="Right" >
									<g:message code="template.element.right"/>
								</option>
							</g:else>
							<g:if test="${templateItem.alignment=='Center' }">
								<option value="Center" selected>
									<g:message code="template.element.center"/>
								</option>
							</g:if>
							<g:else>
								<option value="Center" >
									<g:message code="template.element.center"/>
								</option>
							</g:else>
						</select>
						
						<select class="templateSelect" name="positionOld${templateItem.id }" id="positionOld${templateItem.id }" onchange="showAlignOptions(this.value,'Old${templateItem.id }')">
							<g:if test="${templateItem.position=='Title' }">
								<option value="Title" selected>
									<g:message code="template.element.title"/>
								</option>
							</g:if>
							<g:else>
								<option value="Title">
									<g:message code="template.element.title"/>
								</option>
							</g:else>
							<g:if test="${templateItem.position=='Logo' }">
								<option value="Logo" selected>
									<g:message code="template.logo.section"/>
								</option>
							</g:if>
							<g:else>
								<option value="Logo">
									<g:message code="template.logo.section"/>
								</option>
							</g:else>
							<g:if test="${templateItem.position=='Details1' }">
								<option value="Details1" selected>
									<g:message code="template.project.section"/>
								</option>
							</g:if>
							<g:else>
								<option value="Details1">
									<g:message code="template.project.section"/>
								</option>
							</g:else>
							<g:if test="${templateItem.position=='Details2' }">
								<option value="Details2" selected>
									<g:message code="template.other.section"/>
								</option>
							</g:if>
							<g:else>
								<option value="Details2">
									<g:message code="template.other.section"/>
								</option>
							</g:else>
							<g:if test="${templateItem.position=='Other' }">
								<option value="Other" selected>
									<g:message code="template.afpers.section"/>
								</option>
							</g:if>
							<g:else>
								<option value="Other">
									<g:message code="template.afpers.section"/>
								</option>
							</g:else>
							<g:if test="${templateItem.position=='Remark' }">
								<option value="Remark" selected>
									<g:message code="template.remark.section"/>
								</option>
							</g:if>
							<g:else>
								<option value="Remark">
									<g:message code="template.remark.section"/>
								</option>
							</g:else>
							<g:if test="${templateItem.position=='Body' }">
								<option value="Body" selected>
									<g:message code="template.element.body"/>
								</option>
							</g:if>
							<g:else>
								<option value="Body">
									<g:message code="template.element.body"/>
								</option>
							</g:else>
							<g:if test="${templateItem.position=='Notes' }">
								<option value="Notes" selected>
									<g:message code="template.element.notes"/>
								</option>
							</g:if>
							<g:else>
								<option value="Notes">
									<g:message code="template.element.notes"/>
								</option>
							</g:else>
							<g:if test="${templateItem.position=='Footer' }">
								<option value="Footer" selected>
									<g:message code="template.element.footer"/>
								</option>
							</g:if>
							<g:else>
								<option value="Footer">
									<g:message code="template.element.footer"/>
								</option>
							</g:else>
						</select>
						
						<g:if test="${templateItem.isHeader }">
							<div class="fl paddingLeft">
								<g:checkBox name="isHeaderOld${templateItem.id }" value="true" checked="true"/><g:message code="template.is.header"/>
							</div>
						</g:if>
						<g:else>
							<div class="fl paddingLeft">
								<g:checkBox name="isHeaderOld${templateItem.id }" value="false" checked="false"/><g:message code="template.is.header"/>
							</div>
						</g:else>
						
						<div class="removeBox">
							<a href="#" onclick="removeField('${templateItem.id}');" id="removeButton" class="click">
								<g:message code="show.template.remove.field"/>
							</a>
						</div>
						<div id="ElementsAddButtonsOld${templateItem.id }"></div>
						<div id="TableHeaderOld${templateItem.id }">
							<g:if test="${templateItem.type=='Table' }">
								<div class="formInput">
									<input type="button" class="moveSelectLeft checkPointBtn" value="<g:message code="template.add.header"/>" onclick="addTableHeader('Old${templateItem.id}')" />
									<input type="button" class="checkPointBtn" value="<g:message code="template.add.checkpoint"/>" onclick="addCheckpointQuestions('Old${templateItem.id}',true)" />
								</div>
								<g:each in="${templateItem.headers.sort{ [it.id]} }" var="headerName">
									<div id="tableHeader" >
										<div class="formInput">
											<label class="fl paddingTop width12">Header :</label>
											<input type="text" class="fl field" name="tableHeaderOld${templateItem.id }" value="${headerName.name }">
											<select class=" templateSelect" name="checkpointTypeOld${templateItem.id}"> 
												<g:if test="${headerName.checkpointType=='TextBox' }">
													<option value="TextBox" selected>
														<g:message code="template.checkpoint.type.textbox"/>
													</option>
												</g:if>
												<g:else>
													<option value="TextBox">
														<g:message code="template.checkpoint.type.textbox"/>
													</option>
												</g:else>
												<g:if test="${headerName.checkpointType=='CheckBox' }">
													<option value="CheckBox" selected>
														<g:message code="template.checkpoint.type.checkbox"/>
													</option>
												</g:if>
												<g:else>
													<option value="CheckBox" >
														<g:message code="template.checkpoint.type.checkbox"/>
													</option>
												</g:else>
												<g:if test="${headerName.checkpointType=='RadioButton' }">
													<option value="RadioButton" selected>
														<g:message code="template.checkpoint.type.radiobutton"/>
													</option>
												</g:if>
												<g:else>
													<option value="RadioButton" >
														<g:message code="template.checkpoint.type.radiobutton"/>
													</option>
												</g:else>
											</select>
										</div>
									</div>
								</g:each>
							</g:if>
						</div>
						<div id="CheckpointQuestionsOld${templateItem.id }">
							<g:if test="${templateItem.type=='Table' }">
  								<g:each status="count" in="${templateItem.questions.sort{ [it.id]} }" var="headerName">
     								<div id="tableHeader" >
     									<div class="formInput">
     										<label class="fl paddingTop width12">Checkpoint :</label>
     										<input type="text" class="fl serialNoField" name="checkpointSnoOld${templateItem.id }" value="${headerName.serialNo }">
     										<input type="text" class="fl field" name="checkpointOld${templateItem.id }" value="${headerName.question }">
     											<g:if test="${headerName.isHeader }">
     												<div class="fl paddingLeft">
     													<g:checkBox name="${templateItem.id }isHeader${count}" value="true" checked="true"/><g:message code="template.is.header"/>
     												</div>
     											</g:if>
     											<g:else>
     												<div class="fl paddingLeft">
     													<g:checkBox name="${templateItem.id }isHeader${count }" value="true" checked="false"/><g:message code="template.is.header"/>
     												</div>
     											</g:else>
     										</div>
     									</div>
    								</g:each>
  								</g:if>
			<g:if test="${templateItem.type=='CheckPoint' }">
				<div class="formInput">
			<input type="button" class="checkPointBtn" value="<g:message code="template.add.checkpoint"/>" onclick="addCheckpointQuestions('Old${templateItem.id}',false)" />
			<select class="moveSelectLeft templateSelect " name="checkpointTypeOld${templateItem.id }"> 
			<g:if test="${templateItem.checkpointType=='TextBox' }"><option value="TextBox" selected><g:message code="template.checkpoint.type.textbox"/></option></g:if>
			<g:else><option value="TextBox"><g:message code="template.checkpoint.type.textbox"/></option></g:else>
			<g:if test="${templateItem.checkpointType=='CheckBox' }"><option value="CheckBox" selected><g:message code="template.checkpoint.type.checkbox"/></option></g:if>
			<g:else><option value="CheckBox" ><g:message code="template.checkpoint.type.checkbox"/></option></g:else>
			<g:if test="${templateItem.checkpointType=='RadioButton' }"><option value="RadioButton" selected><g:message code="template.checkpoint.type.radiobutton"/></option></g:if>
			<g:else><option value="RadioButton" ><g:message code="template.checkpoint.type.radiobutton"/></option></g:else>
			</select>
			</div>
			
				<g:each in="${templateItem.questions.sort{ [it.id]} }" var="question">
					<div id="tableHeader" >
					<div class="formInput">
					<label class="fl paddingTop width12"><g:message code="template.element.checkpoint"/> :</label>
					<input type="text" class="fl field" name="checkpointOld${templateItem.id }" value="${question.question }">
					</div>
					</div>
				</g:each>
			</g:if>
		</div>
		<div id="ImageUploaderOld${templateItem.id }">
		<g:if test="${templateItem.type=='Image' }">
		<div class="formInput">
			<iframe src="/AppUBuild/document/imageUploader?elementName=imageOld${templateItem.id }" class="moveSelectLeft iframeCss fl" > </iframe>
					<img src="${resource(dir:'DocumentImages',file:templateItem.formElementName) }" class=" fl" height="40px" width="100px"/>
			<input type="hidden"  id="imageOld${templateItem.id }" name="imageOld${templateItem.id }" value="${templateItem.formElementName }"/>
			<div class="fl paddingLeft">
				<g:if test="${templateItem.isSignature }">
					<g:checkBox name="isSignatureOld${templateItem.id }" value="true" checked="true" /><g:message code="document.is.signature" />
				</g:if>
				<g:else>
					<g:checkBox name="isSignatureOld${templateItem.id }" value="false" checked="" /><g:message code="document.is.signature" />
				</g:else>
			</div>
			</div>
		</g:if>
		</div>
		</div>
		<!-- If the element type is Table end -->
	</g:each>
      
        </div>
       <div class="formInput">
         <a href="#" class="addField moveSelectLeft    click"  onclick="addMoreField();"><g:message code="show.template.add.field"/></a>  
         </div>
         
         <div class=" width300 moveSelectLeft">
         <div class="cancelCommonBg">
         <g:link controller="document" action="templateList">
          <span class="inputButton fl"><g:message code='users.cancel'/> </span>
          </g:link>
          </div>
          <div class="submitCommonBg fr">
       <input type="submit" class="inputButton fl" value="<g:message code='users.submit'/>" />
       </div>
       <input type="hidden" name="templateId" value="${template.id} ">
       <input type="hidden" id="totalElements" name="totalElements" value="0" >
      </div>
     
    </g:form>
    </div>
   </div> 
   
  </div>


<script type="text/javascript">
var num=2;
var serialNoI18="<g:message code='template.serial.no' />";
function addMoreField()
{
$('#Elements').append('<div class="formInput" id="'+num+'"><label class="fl paddingTop width12"><g:message code="template.element.name"/></label> <input type="text" class="fl field" name="formElementName'+num+'" placeholder="<g:message code="template.element.name"/>"><select class="templateSelect" onchange="selectOptions(\'type'+num+'\',\''+num+'\')" id="type'+num+'" name="type'+num+'" ><option value="Text" ><g:message code="template.element.text"/></option><option value="Image" ><g:message code="template.element.image"/></option><option value="Table"><g:message code="template.element.table"/></option><option value="CheckPoint"><g:message code="template.element.checkpoint"/></option></select><select class="templateSelect" name="align'+num+'" id="align'+num+'"><option value="Left"><g:message code="template.element.left"/></option><option value="Right"><g:message code="template.element.right"/></option><option value="Center"><g:message code="template.element.center"/></option></select><select class="templateSelect" name="position'+num+'" id="position'+num+'" onchange="showAlignOptions(this.value,\''+num+'\')"><option value="Title"><g:message code="template.element.title"/></option><option value="Logo"><g:message code="template.logo.section"/></option><option value="Details1"><g:message code="template.project.section"/></option><option value="Details2"><g:message code="template.other.section"/></option><option value="Other"><g:message code="template.afpers.section"/></option><option value="Remark"><g:message code="template.remark.section"/></option><option value="Body"><g:message code="template.element.body"/></option><option value="Footer"><g:message code="template.element.footer"/></option><option value="Notes"><g:message code="template.element.notes"/></option></select><div class="fl paddingLeft"><g:checkBox name="isHeader'+num+'" value="true" checked="false"/><g:message code="template.is.header" /></div><div class="removeBox"><a href="#" onclick="removeField(\''+num+'\');"><g:message code="show.template.remove.field"/></a></div><div id="ElementsAddButtons'+num+'"></div><div id="TableHeader'+num+'"></div><div id="CheckpointQuestions'+num+'"></div><div id="ImageUploader'+num+'"></div></div>');
$("#totalElements").val(num);
num++;
 }
 
function selectOptions(selectId,idNum){ 
	if(document.getElementById(selectId).value=="Table")
	{
		showTableOptions(idNum);
	}
	else if(document.getElementById(selectId).value=="CheckPoint"){
		showCheckpointOptions(idNum);
	}
	else if(document.getElementById(selectId).value=="Image"){
		addImageUploader(idNum);
	}
	else{
		hideTableOptions(idNum)
		}
}



function showTableOptions(tableNum)
{
	hideTableOptions(tableNum);
	$('#ElementsAddButtons'+tableNum).html('<div class="formInput"><input type="button" value="<g:message code="template.add.header" />" class="checkPointBtn moveSelectLeft" onclick="addTableHeader(\''+tableNum+'\')" /><input type="button" class="checkPointBtn" value="<g:message code="template.add.checkpoint" />" onclick="addCheckpointQuestions(\''+tableNum+'\',true)" /></div>');
	$('#TableHeader'+tableNum).append('<div id="tableHeader" ><div class="formInput"><label class="fl paddingTop width12"><g:message code="template.header" /> :</label><input type="text" class="fl field" name="tableHeader'+tableNum+'" value="S.No"><select class="templateSelect marginLeft"  name="checkpointType'+tableNum+'"> <option value="TextBox"><g:message code="template.checkpoint.type.textbox"/></option><option value="CheckBox" ><g:message code="template.checkpoint.type.checkbox"/></option><option value="RadioButton" ><g:message code="template.checkpoint.type.radiobutton"/></option></select></div></div>');
 
 }

function showCheckpointOptions(tableNum)
{
	hideTableOptions(tableNum);
 $('#ElementsAddButtons'+tableNum).append('<div class="formInput"><input type="button" value="<g:message code="template.add.checkpoint" />" class="checkPointBtn " onclick="addCheckpointQuestions(\''+tableNum+'\',false)" /><select class="moveSelectLeft templateSelect " name="checkpointType'+tableNum+'"> <option value="TextBox"><g:message code="template.checkpoint.type.textbox"/></option><option value="CheckBox" ><g:message code="template.checkpoint.type.checkbox"/></option><option value="RadioButton" ><g:message code="template.checkpoint.type.radiobutton"/></option></select></div>');
 
 }


function addTableHeader(tableNum)
{
	$('#TableHeader'+tableNum).append('<div id="tableHeader" ><div class="formInput"><label class="fl paddingTop width12"><g:message code="template.header" /> :</label><input type="text" class="fl field" name="tableHeader'+tableNum+'"><select class="templateSelect marginLeft"  name="checkpointType'+tableNum+'"> <option value="TextBox"><g:message code="template.checkpoint.type.textbox"/></option><option value="CheckBox" ><g:message code="template.checkpoint.type.checkbox"/></option><option value="RadioButton" ><g:message code="template.checkpoint.type.radiobutton"/></option></select></div></div>');
}

function addCheckpointQuestions(tableNum,isHeader)
{
	if(isHeader==true){
  		var size=$('#CheckpointQuestions'+tableNum).find('.formInput').size()
 		$('#CheckpointQuestions'+tableNum).append('<div id="checkpointquestions" ><div class="formInput"><label class="fl paddingTop width12"><g:message code="template.checkpoint" /> :</label><input type="text" class="fl serialNoField " placeHolder="'+serialNoI18+'" name="checkpointSno'+tableNum+'"><input type="text" class="fl field" name="checkpoint'+tableNum+'"><div class="fl paddingLeft"><g:checkBox name="'+tableNum+'isHeader'+size+'" value="true" checked="false"/><g:message code="template.is.header" /></div></div></div>');
 	}
 	else
  		$('#CheckpointQuestions'+tableNum).append('<div id="checkpointquestions" ><div class="formInput"><label class="fl paddingTop width12"><g:message code="template.checkpoint" /> :</label><input type="text" class="fl field" name="checkpoint'+tableNum+'"></div></div>');
 }

function addImageUploader(tableNum)
{
	hideTableOptions(tableNum);
 $('#ImageUploader'+tableNum).append('<div class="formInput"><iframe src="'+applicationContext+'/document/imageUploader?elementName=image'+tableNum+'" class="moveSelectLeft iframeCss fl" > </iframe><input type="hidden"  id="image'+tableNum+'" name="image'+tableNum+'" value=""/><div class="fl paddingLeft"><g:checkBox name="isSignature'+tableNum+'" value="true" checked="false" /><g:message code="document.is.signature" /></div></div>');
 }

function hideTableOptions(tableNum)
{
	$('#TableHeader'+tableNum).html('');
	$('#ElementsAddButtons'+tableNum).html('');	
	$('#CheckpointQuestions'+tableNum).html('');
	$('#ImageUploader'+tableNum).html('');
	}

function removeField(idNum)
{
	$('#'+idNum).remove();
	
	}

function showAlignOptions(value,tableNum){
	if(value=='Title'){
		$('#align'+tableNum).html('<option value="Left"><g:message code="template.element.left"/></option><option value="Right" ><g:message code="template.element.right"/></option><option value="Center"><g:message code="template.element.center"/></option>')
	}
	else if(value=='Logo'){
		$('#align'+tableNum).html('<option value="Left"><g:message code="template.element.left"/></option>')
	}
	else if(value=='Details1'){
		$('#align'+tableNum).html('<option value="Left"><g:message code="template.element.left"/></option><option value="Right" ><g:message code="template.element.right"/></option><option value="Center"><g:message code="template.element.center"/></option>')
	}
	else if(value=='Details2'){
		$('#align'+tableNum).html('<option value="Left"><g:message code="template.element.left"/></option>')
	}
	else if(value=='Other'){
		$('#align'+tableNum).html('<option value="Left"><g:message code="template.element.left"/></option><option value="Right" ><g:message code="template.element.right"/></option><option value="Center"><g:message code="template.element.center"/></option>')
	}
	else if(value=='Remark'){
		$('#align'+tableNum).html('<option value="Left"><g:message code="template.element.left"/></option><option value="Right" ><g:message code="template.element.right"/></option>')
	}
	else if(value=='Body'){
		$('#align'+tableNum).html('<option value="Left"><g:message code="template.element.left"/></option><option value="Right" ><g:message code="template.element.right"/></option>')
	}
	else if(value=='Footer'){
		$('#align'+tableNum).html('<option value="Left"><g:message code="template.element.left"/></option><option value="Right" ><g:message code="template.element.right"/></option><option value="Center"><g:message code="template.element.center"/></option>')
	}
	else if(value=='Notes'){
		$('#align'+tableNum).html('<option value="Left"><g:message code="template.element.left"/></option>')
	}
	else{
		$('#align'+tableNum).html('')
		
		}
}
</script>

</body>
</html>