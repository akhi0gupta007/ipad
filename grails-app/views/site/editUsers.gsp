<!doctype html>
<%@page import="com.kam.User"%>
<html>
<head>
<meta name='layout' content='main' />
<title><g:message code="title.message"></g:message></title>

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
			<h2>
			<g:if test="${middleName }">
				<g:message code="users.update.details"/> ${firstName +' '+middleName +' '+lastName }
				</g:if>
				<g:else>
				<g:message code="users.update.details"/> ${firstName +' '+lastName }
				</g:else>
			</h2>


			<g:form id="updateUserForm" name="updateUserForm" class="cmxform" url="[controller:'site',action:'updateUsers']" autocomplete='off'>
					<h2 class="loginDetails"><g:message code="customer.update.details"/></h2>
				<div class="formInput">
					<label class="fl inputHeading"><g:message code="users.first.name"/>:</label>
					<input type="text" class="field validName" placeholder="<g:message code="users.first.name"/>" name='firstName' id='firstName' value="${firstName }" />
				</div>
				
				<div class="formInput">
					<label class="fl inputHeading"><g:message code="users.middle.name"/> :</label>
					<input type="text" class="field" placeholder="<g:message code="users.middle.name"/>" name='middleName' id='middleName' value="${middleName }" />
				</div>
				<div class="formInput">
					<label class="fl inputHeading"><g:message code="users.last.name"/> :</label>
					<input type="text" class="field validName" placeholder="<g:message code="users.last.name"/>" name='lastName' id='lastName' value="${lastName }"  />
				</div>
				<div class="formInput">
					<label class="fl inputHeading"><g:message code="users.user.name"/>:</label>
					<input type="text" class="field" placeholder="<g:message code="users.user.name"/>" name='username' id='username' onblur="validateUsernameRegistered();" value="${username }" />
				
					<div id="serverUsernameMessage" class="width300 "></div>
				</div>
				<div class="formInput">
					<label class="fl inputHeading"><g:message code="users.insert.email"/>:</label>
					
						<input type="text" class="field" placeholder="<g:message code="users.insert.email"/>" name='email'
							id='email' onblur="validateEmailRegistered();" value="${email }" />
					
					<div id="serverEmailMessage" class="width300 "></div>
				</div>

				<div class="formInput">
					<label class="fl inputHeading"><g:message code="users.Contact.Number"/> :</label>
					
						<input type="text" class="field contact" placeholder="<g:message code="users.Contact.Number"/>" onblur="phone_validate(this.value);" name='mobile'
							id='mobile' value="${mobile }" /> 
							<div id="validPhoneMessage" class="width300 "></div>
							<input type="hidden" class="text" name='oldUsername' id='oldUsername' value="${username }" />
							<input type="hidden" class="text" name='oldEmail' id='oldEmail' value="${email }" />
							<input type="hidden" class="text" name='forwardTo' id='forwardTo' value="${forwardTo }" />
							
							<div class="fr width300">
							<input type="hidden" name="customername" value="${customername }"> 
							<div class="cancelCommonBg">
								<g:link controller="site" action="customers">
								<span class="inputButton fl"><g:message code='users.cancel'/> </span>
								</g:link> 
							</div> 
							<div class="submitCommonBg fr">
								<input type="submit" class="inputButton fl" value="<g:message code='users.submit'/>"/>
							</div>
					
				
			</div>
			</div>
		
			</g:form>

		</div>
	</div>
</div>
</div>
	
	<script type="text/javascript">
        function validateEmailRegistered(){ 
            var url="${createLink(controller:'register',action:'checkIfEmailRegistered')}";
            var email=document.getElementById("email").value;
            if(email=="" || email==document.getElementById("oldEmail").value) 
            {
            	jQuery('#serverEmailMessage').show().addClass('sucessfulEnter').removeClass('error')
            	jQuery('#serverEmailMessage').html('');
                return};
            //loading();
            jQuery.ajax({
                 type: 'POST',
                 url: url,
                 data:'email='+email,
                 //alert('email');
                 success: function(response,textStatus){
                  if(response.sucess==true){
                      
                   jQuery('#serverEmailMessage').show().addClass('sucessfulEnter').removeClass('error');
                  }else{
                   jQuery('#serverEmailMessage').show().removeClass('sucessfulEnter').addClass('error');
                   }
                  jQuery('#serverEmailMessage').html(response.message);

                  },
                 error:function(XMLHttpRequest,textStatus,errorThrown){}
             })
         }

        function validateUsernameRegistered(){ 
            var url="${createLink(controller:'register',action:'checkIfUsernameRegistered')}";
			var username = document.getElementById("username").value;
			if (username == "" || username==document.getElementById("oldUsername").value){
				jQuery('#serverUsernameMessage').show().addClass('sucessfulEnter').removeClass('error');
				jQuery('#serverEmailMessage').html('');
				return;
			}
			//loading();
			jQuery.ajax({
				type : 'POST',
				url : url,
				data : 'username=' + username,
				//alert('email');
				success : function(response, textStatus) {
					if (response.sucess == true) {

						jQuery('#serverUsernameMessage').show().addClass('sucessfulEnter').removeClass('error');
					} else {
						jQuery('#serverUsernameMessage').show().removeClass('sucessfulEnter').addClass('error');
					}
					jQuery('#serverUsernameMessage').html(response.message);

				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					
				}
			})
		}
       
        $(document).ready(function(){
        	$('#updateUserForm').submit(function(){	
        		if($('#serverUsernameMessage').hasClass('error')){
        			return false;
        		}
        		else if($('#serverEmailMessage').hasClass('error')){
        			return false;
        		}
        		else if($('#validPhoneMessage').hasClass('error')){
        			return false;
        		}
        		else{
        			return true;
        		}
        	});
        });

        
	</script>
	
</body>
</html>
