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
			<li class="fr paddingRight"><g:link controller="site" action="customers" class="back"><span><g:message code="login.customer.back"/></span></g:link></li>
		</ul>
		<div class="customerList">
		<div class="marginTopBottom fullWidth">
		<div class="formBox">
  			<div class="createUser formHeadingBg">
    			<h2><g:message code="customer.new.customer"/></h2>
    			<g:form id="CustomerForm" class="cmxform" name="CustomerForm" update="updateMessage" url="[controller:'site',action:'saveCustomer']" >
                	<div class="formInput">
  						<label class="fl inputHeading"><g:message code="new.customer.name"/> :</label>
 						<input type="text" class="field validCustomerName" placeholder="<g:message code="new.customer.name"/>" onblur="validateCustomerRegistered();"  name="name" id="name"/>
 						<div id="uniqueCustomerName" class="width300 error "></div>
                   	</div>
           			<div class="formInput">
   						<label class="fl inputHeading"><g:message code="new.customer.address"/>:</label>
 						<input type="text" class="field" placeholder="<g:message code="new.customer.address"/>" name='address' id='address'/>
                    </div>
                    <div class="formInput">
   						<label class="fl inputHeading"><g:message code="new.customer.city"/> :</label>
   						<input type="text" class="field validCity" placeholder="<g:message code="new.customer.city"/>" name='city' id='city'/>
                    </div>
                    <div class="formInput">
  						<label class="fl inputHeading "><g:message code="new.customer.website"/> :</label>
						<input type="text" class="field" placeholder="<g:message code="new.customer.website" />" onblur="url_validate(this.value);" name='website' id='website'/>
						<div id="validWebsiteMessage" class="width300 "></div>
                    </div>
                    <div class="formInput">
   						<label class="fl inputHeading"><g:message code="users.Contact.Number"/> :</label>
 						<input type="text" class="field contact" placeholder="<g:message code="users.Contact.Number"/>" onblur="phone_validate(this.value);" name='contact' id='contact'/>
 						<div id="validPhoneMessage" class="width300 "></div>
 						</div>
 					<div class="formInput">
						<label class="fl inputHeading"><g:message code="component.customer.flow"/></label>
						<div class="paddingTop marginRight fl">
 							<g:radio name="customerFlow" id="customerFlow"  value="document" checked="true"/><g:message code="show.document.document"/>
 						</div>
 						<div class="paddingTop marginRight fl">
  							<g:radio name="customerFlow" id="customerFlow" value="equipment" /><g:message code="component.equipment"/>
  						</div>
  					</div>
 					<div class="fr width300">
 						  <g:link controller="site" action="customers">
 						<div class="cancelCommonBg">
						<span class="inputButton fl"><g:message code='users.cancel'/> </span>
						</div>
						</g:link>
						<div class="submitCommonBg fr">
 						<input type="submit" class="inputButton fl" value="<g:message code="users.submit"/>"/>
 						</div>
 						</div>
                                                                 
				</g:form>
 			</div>
        </div>
     </div>
   </div>

<script type="text/javascript">
$(document).ready(function(){
	$('#CustomerForm').find('input[type=text]').val('');
	
	$('#CustomerForm').submit(function(){	
		if($('#validWebsiteMessage').hasClass('error')){
			return false;
		}
		else if($('#validPhoneMessage').hasClass('error')){
			$('#validPhoneMessage').html(contactValidation);
			return false;
		}
		else if($('#uniqueCustomerName').hasClass('error')){
			return false;
		}
		else{
			return true;
		}
	});
});



function validateCustomerRegistered(){ 
    var customerUrl="${createLink(controller:'register',action:'checkIfCustomerExists')}";
    var customerName=document.getElementById("name").value;
    if(customerName=="") return;
    //loading();
    jQuery.ajax({
         type: 'POST',
         url: customerUrl,
         data:'customerName='+customerName,
         //alert('email');
         success: function(response,textStatus){
          if(response.sucess==true){
              
           jQuery('#uniqueCustomerName').show().addClass('sucessfulEnter').removeClass('error');
          }else{
           jQuery('#uniqueCustomerName').show().removeClass('sucessfulEnter').addClass('error');
           }
          jQuery('#uniqueCustomerName').html(response.message);

          },
         error:function(XMLHttpRequest,textStatus,errorThrown){}
     })
 }
</script>

</body>
</html>
