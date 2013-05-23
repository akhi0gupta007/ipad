<!doctype html>
<%@page import="com.kam.User"%>
<%@page import="com.kam.UserRole"%>
<%@page import="com.kam.Role"%>
<%@page import="com.kam.Customer"%>
<html>
<head>
<title><g:message code="title.message"></g:message></title>

<meta name='layout' content='main' />
<script type="text/javascript">
 function showFields() {
  document.getElementById('createNewCustomer').style.display = 'block';
 }
</script>

<script type="text/javascript">
 function updateList() {
  document.getElementById('CustomerList').innerHTML += document
    .getElementById('updateMessage').innerHTML;
  document.getElementById('updateMessage').innerHTML = '';

 }
</script>


</head>

<body>
 
  <g:render template="/layouts/navigation" model="[loggedInUserRole:role,loggedinUser:loggedinUser]"></g:render>
 
		<ul class="backUl">
			<li class="fr paddingRight"><g:link controller="site" class="back"><span><g:message code="login.customer.back"/></span></g:link></li>
		</ul>
		
	 <div class="customerList">	
  <div class="marginTopBottom fullWidth">
		<div class="formBox">
  			<div class="createUser formHeadingBg">
    <h2><g:message code="update.customer.details"/></h2>
    	<g:form id="CustomerForm" class="cmxform" name="CustomerForm" update="updateMessage" url="[controller:'site',action:'UpdateCustomer']" autocomplete='off'>
                     
   				<div class="formInput">
   					<label class="fl inputHeading"><g:message code="new.customer.name"/> :</label>
   					<input type="text" class="field validCustomerName" placeholder="<g:message code="new.customer.name"/>" name='name' id='name' value="${name }"/>
                </div>
                       
            	<div class="formInput">
   				 	<label class="fl inputHeading"><g:message code="new.customer.address"/> :</label>
   				 	<input type="text" class="field" placeholder="<g:message code="new.customer.address"/>" name='address' id='address' value="${address }"/>
               </div>
                        
                        
             	<div class="formInput">
   					<label class="fl inputHeading"><g:message code="new.customer.city"/> :</label>
 					<input type="text" class="field validCity" placeholder="<g:message code="new.customer.city"/>" name='city' id='city' value="${city }"/>
   				</div>
                <div class="formInput">
   					<label class="fl inputHeading"><g:message code="new.customer.website"/> :</label>
   					<input type="text" class="field" placeholder="<g:message code="new.customer.website"/>" name='website' onblur="url_validate(this.value);" id='website' value="${website }"/>
   					<div id="validWebsiteMessage" class="width300 "></div>
                </div>
                
                 <div class="formInput">
    				<label class="fl inputHeading"><g:message code="users.Contact.Number"/>:</label>
   					<input type="text" class="field contact" placeholder="<g:message code="users.Contact.Number"/>" onblur="phone_validate(this.value);" name='contact' id='contact' value="${contact }"/>
                     <input type="hidden" class="field" name='oldCustomer' id='oldCustomer' value="${name }"/>
                     <div id="validPhoneMessage" class="width300 "></div>
                 </div>
                 <div class="formInput">
						<label class="fl inputHeading"><g:message code="component.customer.flow"/></label>
						<div class="paddingTop marginRight fl">
						<g:if test="${flow=='document' }">
						 	<g:radio name="customerFlow" id="customerFlow"  value="document" checked="true"/><g:message code="show.document.document"/>
						</g:if>
						<g:else>
							<g:radio name="customerFlow" id="customerFlow"  value="document" /><g:message code="show.document.document"/>
						</g:else>
 						</div>
 						<div class="paddingTop marginRight fl">
 						<g:if test="${flow=='equipment' }">
  							<g:radio name="customerFlow" id="customerFlow" value="equipment" checked="true" /><g:message code="component.equipment"/>
						</g:if>
						<g:else>
  							<g:radio name="customerFlow" id="customerFlow" value="equipment" /><g:message code="component.equipment"/>
						</g:else>
  						</div>
  					</div>
               <div class="fr width300">
                    <div class="cancelCommonBg">
                        <g:link controller="site" action="customers">
                      
                        <span class="inputButton fl"><g:message code='users.cancel'/> </span></g:link>
                        </div>
                        <div class="submitCommonBg fr">
                          <input type="submit" class="inputButton fl" value="<g:message code='users.submit'/>"  />
                          </div>
                        </div>
</g:form>
 </div>
        </div>
        </div>
        

<script type="text/javascript">
$(document).ready(function(){
	$('#CustomerForm').submit(function(){	
		if($('#validWebsiteMessage').hasClass('error')){
			return false;
		}
		else{
			return true;
		}
	});
});
</script>
</div>
</body>
</html>
