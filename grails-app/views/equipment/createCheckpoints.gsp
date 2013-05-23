<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.kam.Customer" %>
<%@ page import="com.kam.User" %>
<%@page import="com.kam.Disciplines"%>
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
		<li class="fr paddingRight"><a href="javascript:history.go(-1)" class="back"><span><g:message code="login.customer.back"/></span></a></li>
   </ul>
   <div class="customerList">
   <div class="newTemplate  formHeadingBg">
   
   <h2 class="formHead"><g:message code="messages.equipment.checkpoints"/> <g:message code="show.template.for"/> ${customerName }</h2>
    <div class="inputHeading fullWidth">
     <g:form id="TemplateForm" name="TemplateForm" controller="equipment" action="saveEquipmentCheckpoints">

		<!-- New checkpoint code starts here -->
		<div id="Elements">
			<g:each in="${checkpoints }" var="equipmentcheckpoint">
				<div id="checkpointquestions" >
					<div class="formInput">
						<label class="fl paddingTop width12"><g:message code="template.checkpoint" /> :</label>
						<input type="text" class="fl serialNoField " name="checkpointSno${equipmentcheckpoint.id }" value="${equipmentcheckpoint.sno }">
						<input type="text" class="fl field" name="checkpoint${equipmentcheckpoint.id }" value="${equipmentcheckpoint.value }">
						<select class="templateSelect marginLeft"  name="checkpointType${equipmentcheckpoint.id }">
							<g:if test="${equipmentcheckpoint.category=='Functional' }">
								<option value="Functional" selected="selected"><g:message code="messages.equipment.functional"/></option>
							</g:if>
							<g:else>
								<option value="Functional"><g:message code="messages.equipment.functional"/></option>
							</g:else>
							<g:if test="${equipmentcheckpoint.category=='Visual' }">
								<option value="Visual" selected="selected"><g:message code="messages.equipment.visual"/></option>
							</g:if>
							<g:else>
								<option value="Visual" ><g:message code="messages.equipment.visual"/></option>
							</g:else>
						</select>
					</div>
				</div>
			</g:each>
			<g:if test="${checkpoints==null || checkpoints.size()==0 }">
				<div id="checkpointquestions" >
					<div class="formInput">
						<label class="fl paddingTop width12"><g:message code="template.checkpoint" /> :</label>
						<input type="text" class="fl serialNoField " name="checkpointSnoNew1" placeholder="<g:message code='template.serial.no' />">
						<input type="text" class="fl field" name="checkpointNew1">
						<select class="templateSelect marginLeft"  name="checkpointTypeNew1"> 
								<option value="Functional"><g:message code="messages.equipment.functional"/></option>
								<option value="Visual" ><g:message code="messages.equipment.visual"/></option>
						</select>
					</div>
				</div>
			</g:if>
		</div>
		<!-- New checkpoint code ends here -->
		
       <div class="formInput">
         <a href="#" class="addField moveSelectLeft   click"  onclick="addMoreCheckpoints();"> <g:message code="show.template.add.field"/></a> 
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
function addMoreCheckpoints()
{
$('#Elements').append('<div class="formInput"><label class="fl paddingTop width12"><g:message code="template.checkpoint" /> :</label><input type="text" class="fl serialNoField " name="checkpointSnoNew'+num+'" placeholder="'+serialNoI18+'"><input type="text" class="fl field" name="checkpointNew'+num+'"><select class="templateSelect marginLeft"  name="checkpointTypeNew'+num+'"> <option value="Functional"><g:message code="messages.equipment.functional"/></option><option value="Visual" ><g:message code="messages.equipment.visual"/></option></select></div>');
$('#totalElements').val(num);
num++;
 }

</script>

</body>
</html>