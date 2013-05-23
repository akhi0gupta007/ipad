<!DOCTYPE html>
<%@page import="com.kam.Disciplines"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.kam.Customer" %>
<%@ page import="com.kam.User" %>
<html>
<head>
<g:javascript library='jquery' plugin='jquery'></g:javascript>
<meta name="layout" content="main" />
<script type="text/javascript">
function usersForm(data,formName,param) {
	document.getElementById(param).value=data;
	document.forms[formName].submit();
}
</script>
</head>
<!-- The form below takes input from user and saves it -->
<body>

  
  <g:render template="/layouts/navigation" model="[loggedInUserRole:role,loggedinUser:loggedInUser]"></g:render>
  
  
   <ul class="backUl">
   <li class="fr paddingRight"><g:link controller="site" class="back"><span><g:message code="login.customer.back"/></span></g:link></li>
   </ul>
   <div class="customerList">
   <div class="newTemplate  formHeadingBg">
   
   <h2 class="formHead"><g:message code="template.new.template"/> <g:message code="show.template.for"/> ${customerName }</h2>
    <div class="inputHeading fullWidth">
     <g:form id="TemplateForm" name="TemplateForm" controller="document" action="createTemplate">
<input type="hidden" value="${userId}" name="userId">
       <div class="formInput" >
         <label class="fl paddingTop width12"><g:message code="template.template.name"/>:</label>
         <input type="text" name="name" id="name" placeholder="<g:message code="template.template.name"/>" class="fl field"/>
       </div>
       <div class="formInput" >
       		<label class="fl paddingTop width12"><g:message code="list.discipline"/>:</label>
            <select class="selectTextCommon fl " name="templateDiscipline">
				<g:each in="${Disciplines.list() }"  var="discipline">
					<option value="${discipline.disciplineName }">${discipline.disciplineName }</option>
				</g:each>
			</select>
		</div>
      <div id="Elements">
      <div class="formInput" id='1'>
       <label class="fl paddingTop width12"><g:message code="template.element.name"/>:</label>
         <input type="text" name="formElementName1" placeholder="<g:message code="template.element.name"/>"  class="fl field" />
         
         
         <select class="templateSelect" name="type1" onchange="selectOptions('type1','1')" id="type1" > <option value="Text" ><g:message code="template.element.text"/></option>
<option value="Image" ><g:message code="template.element.image"/></option>
<option value="Table"   ><g:message code="template.element.table"/></option>
<option value="CheckPoint"  ><g:message code="template.element.checkpoint"/></option>
</select>


         <select class="templateSelect" name="align1" id="align1" > <option value="Left"><g:message code="template.element.left"/></option>
<option value="Right" ><g:message code="template.element.right"/></option>
<option value="Center"><g:message code="template.element.center"/></option></select>

           <select class="templateSelect" name="position1" id="position1" onchange="showAlignOptions(this.value,'1')"> 
           
           <option value="Title" ><g:message code="template.element.title"/></option>
           <option value="Logo"><g:message code="template.logo.section"/></option>
<option value="Details1"><g:message code="template.project.section"/></option>
<option value="Details2"><g:message code="template.other.section"/></option>
<option value="Other"><g:message code="template.afpers.section"/></option>
<option value="Remark"><g:message code="template.remark.section"/></option>
<option value="Body"><g:message code="template.element.body"/></option>
<option value="Footer"><g:message code="template.element.footer"/></option>
<option value="Notes"><g:message code="template.element.notes"/></option>
</select> 
<div class="fl paddingLeft">
<g:checkBox name="isHeader1" value="true" checked="false" /><g:message code="template.is.header" />
</div>
           <div class="removeBox " >
            <a href="#" onclick="removeField('1');" id="removeButton" class="click">
           <g:message code="show.template.remove.field"/></a>  
           </div>
           <div id="ElementsAddButtons1"></div>
          <div id="TableHeader1"></div>
          <div id="CheckpointQuestions1"></div>
          <div id="ImageUploader1"></div>
        </div>
       
        </div>
       <div class="formInput">
         <a href="#" class="addField moveSelectLeft   click"  onclick="addMoreField();"> <g:message code="show.template.add.field"/></a> 
         </div>
         
         <div class="width300 moveSelectLeft">
         <div class="cancelCommonBg">
          <g:link controller="site" ><span class="inputButton fl"><g:message code='users.cancel'/> </span></g:link>
          </div>
          <div class="submitCommonBg fr">
       <input type="submit" class="inputButton fl" value="<g:message code='users.submit'/>"/>
       </div>
       <input type="hidden" id="totalElements" name="totalElements" value="1" >
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
		$('#align'+tableNum).html('<option value="Left"><g:message code="template.element.left"/></option>')
		
		}
}
</script>

</body>
</html>